package com.example.kys.fish.view.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.kys.fish.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

public class AddressActivity extends AppCompatActivity {
    TextView myAddress_tv,address_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
       myAddress_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    selectAddress();//调用CityPicker选取区域
                }

            }
        });
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(AddressActivity.this)
                .textSize(14)
                .title("地址选择")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        /**
         * 1.citySelected[0]：表示：省份信息
           2.citySelected[1]：表示：城市信息
           3.citySelected[2]：表示：区县信息
           4.citySelected[3]：表示：邮编信息
         */
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                address_tv.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }
    protected void initView() {
        myAddress_tv = (TextView) findViewById(R.id.myAddress_tv);
        address_tv=(TextView)findViewById(R.id.address_tv);
    }
}

