package com.example.daisongsong.chartapp.book.data;

import com.alibaba.fastjson.JSON;
import com.example.daisongsong.chartapp.book.model.FundInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class FundManager {
    private static final String FUND_FILE_NAME = "FUND_FILE_NAME";

    public static List<FundInfo> getAllFunds() {
        String json = FileUtils.read(FUND_FILE_NAME);
        List<FundInfo> fundInfos = JSON.parseArray(json, FundInfo.class);
        if(fundInfos == null){
            fundInfos = new ArrayList<>(0);
        }
        return fundInfos;
    }

    public static void writeAllFunds(List<FundInfo> fundInfos) {
        String json = null;
        if (fundInfos != null) {
            for (FundInfo fundInfo : fundInfos) {
                fundInfo.setFundPrices(null);
            }
            json = JSON.toJSONString(fundInfos);
        }
        FileUtils.write(FUND_FILE_NAME, json);
    }
}
