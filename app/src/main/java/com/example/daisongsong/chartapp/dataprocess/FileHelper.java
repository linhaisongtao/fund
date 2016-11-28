package com.example.daisongsong.chartapp.dataprocess;

import android.text.TextUtils;

import com.example.daisongsong.chartapp.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class FileHelper {

    public static ArrayList<FundData> fromFile(String assetName) {
        ArrayList<FundData> datas = new ArrayList<>();
        try {
            InputStream inputStream = App.getApp().getAssets().open(assetName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();
            while (!TextUtils.isEmpty(line)) {
                String[] array = line.split("\t");
                FundData d = new FundData();
                d.setDate(array[0]);
                d.setPrice(Float.parseFloat(array[1]));
                datas.add(d);

                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }
}
