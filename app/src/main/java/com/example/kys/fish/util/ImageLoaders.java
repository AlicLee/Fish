package com.example.kys.fish.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.kys.fish.MyApplication;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Lee on 2017/9/11.
 */

public class ImageLoaders {
    private static final String TAG = "ImageLoaders";
    /**
     *  图片硬盘缓存核心类。 
     */
    private DiskLruCache mDiskLruCache;
    /**
     * 记录所有正在下载或等待下载的任务。 
     */
    private Set<BitmapWokerTask> taskCollection;
    /**
     *  图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。 
     */
    private LruCache<String, Bitmap> mMemoryCache;
    private Context mContext;
    private ImageView mCurrentImageView;

    /**
     * 构造方法
     *
     * @param context
     */
    public ImageLoaders(Context context) {
        this.mContext = context;
        taskCollection = new HashSet<>();
        //获取程序最大的可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        //设置图片缓存大小为程序最大可用内存的1/8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        try {
            //获取图片缓存路径
            File cacheDir = getDiskCacheDir(context, "fish");
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            //创建DiskLruCache实例,初始化缓存对象。
            mDiskLruCache = DiskLruCache.open(cacheDir, MyApplication.getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (Exception e) {
            Log.e(TAG, "e:" + e);
        }
    }

    /**
     * 将一张图片缓存到LruCache中
     *
     * @param key    LruCache的键,这里传入图片的URL地址
     * @param bitmap LruCache的键,这里传入网络上下载的bitmap对象
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从内存中获取一张图片,如果不存在就返回null。
     *
     * @param key LruCache的键,这里传入图片的URL地址
     * @return 对应传入键的Bitmap对象，或者Null;
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 加载图片的方法
     *
     * @param mImageView 要显示的imageView
     * @param imageUrl   图片地址
     * @param width      图片的宽(如果想要为默认,请使用0)
     * @param height     图片的高(如果想要为默认.请使用0)
     */
    public void loadBitmaps(ImageView mImageView, String imageUrl, int width, int height) {
        try {
            mCurrentImageView = mImageView;
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
            if (bitmap == null) {
                BitmapWokerTask task = new BitmapWokerTask();
                taskCollection.add(task);
                if (width == 0 || height == 0) {
                    task.execute(imageUrl);
                } else {
                    task.execute(imageUrl, String.valueOf(width), String.valueOf(height));
                }
            } else {
                if (mImageView != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "e:" + e);
        }
    }

    /**
     * 取消所有任务
     */
    public void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWokerTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }

    /**
     * 从网上下载图片
     *
     * @param urlString    下载路径
     * @param outputStream 输出流
     * @return 是否成功下载图片
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection connection = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(connection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "网络请求错误:" + e);
        } finally {
            if (connection != null)
                connection.disconnect();

            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private class BitmapWokerTask extends AsyncTask<String, Void, Bitmap> {
        private String imageUrl;//图片url地址
        private int width;//图片的宽
        private int height;//图片的高

        @Override
        protected Bitmap doInBackground(String... strings) {
            imageUrl = strings[0];
            if (strings.length == 3) {
                width = Integer.valueOf(strings[1]);
                height = Integer.valueOf(strings[2]);
            }
            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            DiskLruCache.Snapshot snapShot = null;
            try {
                //生成图片URL对应的key;
                final String key = hashKeyForDisk(imageUrl);//生成图片的url对应的key;
                //查找key对应的缓存
                snapShot = mDiskLruCache.get(key);
                if (snapShot == null) {
                    //如果没有找到对应的缓存,则准备从网上请求数据，并写入缓存。
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    // 缓存被写入后，再次查找key对应的缓存  
                    snapShot = mDiskLruCache.get(key);
                }
                if (snapShot != null) {
                    fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }
                //将缓存对象解析成bitmap对象
                Bitmap bitmap = null;
                if (fileDescriptor != null) {
                    if (strings.length == 3) {
                        //压缩并显示
                        bitmap = compressDescriptor(fileDescriptor, DensityUtil.dip2px(mContext, width), DensityUtil.dip2px(mContext, height));
                    } else {
                        //不压缩,如果不压缩,bitmap对象过大,可能直接oom;
                        bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                    }
                }
                if (bitmap != null) {
                    //将Bitmap对象添加到内存缓存中
                    addBitmapToMemoryCache(strings[0], bitmap);
                }
                return bitmap;
            } catch (Exception e) {
                Log.e(TAG, "e:" + e);
            } finally {
                if (fileDescriptor == null && fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e) {
                        Log.e(TAG, "e:" + e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mCurrentImageView != null && bitmap != null) {
                mCurrentImageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }
    }

    /**
     * 存到本地的缓存(默认存到sd卡，如果sd卡不存在，存到本地)
     *
     * @param context    上下文
     * @param uniqueName 要保存的文件名
     * @return 返回新建的文件名
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 把key通过md5加密
     *
     * @param key 需要加密的key
     * @return 加密后的密文
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (Exception e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * byte 转16进制.用于md5加密
     *
     * @param bytes byte数组
     * @return 返回的16进制字符串
     */
    private String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Compress image by size, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public Bitmap compressBitmap(FileDescriptor descriptor, Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//	    return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress image by pixel, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param descriptor 要解析的文件
     * @param pixelW     target pixel of width
     * @param pixelH     target pixel of height
     * @return
     */
    public Bitmap compressDescriptor(FileDescriptor descriptor, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        //Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        return BitmapFactory.decodeFileDescriptor(descriptor, null, newOpts);
    }

}
