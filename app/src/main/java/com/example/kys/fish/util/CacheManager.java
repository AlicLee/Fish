package com.example.kys.fish.util;

import android.content.Context;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.kys.fish.MyApplication.getAppVersion;

/**
 * Created by Lee on 2017/10/9.
 */
public class CacheManager {
    DiskLruCache mDiskLruCache = null;

    /**
     * 缓存管理
     *
     * @param context
     */
    public CacheManager(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, "fish");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存序列化对象
     *
     * @param nickName 保存对象的key
     * @param Data     序列化文件对象
     */
    public void SaveObject(final String nickName, final String Data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String key = hashKeyForDisk(nickName);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (writeJsonToStream(Data, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 读取序列化对象
     *
     * @param nickName 读取的值
     */
    public String readObject(String nickName) {
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(nickName);
            if (snapShot != null) {
                //snapShot.getInputStream(0);传比disklrucache版本号小 这里穿0
                InputStream is = snapShot.getInputStream(0);
                byte[] by = new byte[1024];
                int i = 0;
                StringBuilder sb = new StringBuilder();
                while ((i = is.read(by)) != -1) {
                    sb.append(new String(by, 0, by.length));
                }
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param Data         序列化数据
     * @param outputStream 输出流
     * @return 是否写成功
     */
    private boolean writeJsonToStream(String Data, OutputStream outputStream) {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            out.write(Data.getBytes());
            out.flush();
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * @param bytes byte数组转String
     * @return
     */
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * @param context    上下文
     * @param uniqueName 文件名
     * @return 返回file对象
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
