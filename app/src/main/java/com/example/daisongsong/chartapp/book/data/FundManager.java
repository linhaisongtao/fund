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
            json = JSON.toJSONString(fundInfos);
        }
        FileUtils.write(FUND_FILE_NAME, json);
    }

    public static List<FundInfo.FundPrice> getAllFundPrice(String fundCode) {
        String json = FileUtils.read(makeFundPriceFile(fundCode));
        List<FundInfo.FundPrice> prices = JSON.parseArray(json, FundInfo.FundPrice.class);
        if (prices == null) {
            prices = new ArrayList<>(0);
        }
        return prices;
    }

    public static void writeAllFundPrice(String fundCode, List<FundInfo.FundPrice> prices) {
        String json = null;
        if (prices != null) {
            json = JSON.toJSONString(prices);
        }
        FileUtils.write(makeFundPriceFile(fundCode), json);
    }

    public static FundInfo.FundPrice findFundPrice(String fundCode, long time) {
        List<FundInfo.FundPrice> allFundPrice = getAllFundPrice(fundCode);
        for (FundInfo.FundPrice fundPrice : allFundPrice) {
            if (time == fundPrice.getTime()) {
                return fundPrice;
            }
        }
        return null;
    }

    private static String makeFundPriceFile(String fundCode) {
        return "FUND_PRICE_OF_" + fundCode;
    }
}


