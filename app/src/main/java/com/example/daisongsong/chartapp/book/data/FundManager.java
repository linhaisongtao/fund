package com.example.daisongsong.chartapp.book.data;

import com.alibaba.fastjson.JSON;
import com.example.daisongsong.chartapp.book.model.BuyInfo;
import com.example.daisongsong.chartapp.book.model.FundInfo;
import com.example.daisongsong.chartapp.book.model.FundPrice;

import java.io.File;
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

    public static FundInfo getFundInfo(String fundCode) {
        List<FundInfo> allFunds = getAllFunds();
        for (FundInfo allFund : allFunds) {
            if (fundCode.equalsIgnoreCase(allFund.getFundCode())) {
                return allFund;
            }
        }
        return null;
    }

    public static void writeAllFunds(List<FundInfo> fundInfos) {
        String json = null;
        if (fundInfos != null) {
            json = JSON.toJSONString(fundInfos);
        }
        FileUtils.write(FUND_FILE_NAME, json);
    }

    public static List<FundPrice> getAllFundPrice(String fundCode) {
        String json = FileUtils.read(makeFundPriceFile(fundCode));
        List<FundPrice> prices = JSON.parseArray(json, FundPrice.class);
        if (prices == null) {
            prices = new ArrayList<>(0);
        }
        FundInfo fundInfo = getFundInfo(fundCode);
        for (FundPrice price : prices) {
            price.setFundInfo(fundInfo);
        }
        return prices;
    }

    public static void writeAllFundPrice(String fundCode, List<FundPrice> prices) {
        String json = null;
        if (prices != null) {
            json = JSON.toJSONString(prices);
        }
        FileUtils.write(makeFundPriceFile(fundCode), json);
    }

    public static FundPrice findFundPrice(String fundCode, long time) {
        List<FundPrice> allFundPrice = getAllFundPrice(fundCode);
        for (FundPrice fundPrice : allFundPrice) {
            if (time == fundPrice.getTime()) {
                return fundPrice;
            }
        }
        return null;
    }

    public static List<BuyInfo> getBuyInfos(String fundCode) {
        String json = FileUtils.read(makeBuyFile(fundCode));
        List<BuyInfo> buyInfos = JSON.parseArray(json, BuyInfo.class);
        if (buyInfos == null) {
            buyInfos = new ArrayList<>(0);
        }

        List<FundPrice> allFundPrice = getAllFundPrice(fundCode);
        for (BuyInfo buyInfo : buyInfos) {
            buyInfo.setFundPrice(findFundPrice(allFundPrice, buyInfo.getFundTime()));
        }

        return buyInfos;
    }

    private static FundPrice findFundPrice(List<FundPrice> allFundPrice, long time) {
        for (FundPrice price : allFundPrice) {
            if (time == price.getTime()) {
                return price;
            }
        }
        return null;
    }

    public static void saveBuyInfos(String fundCode, List<BuyInfo> infos) {
        String json = null;
        if (infos != null) {
            json = JSON.toJSONString(infos);
        }
        FileUtils.write(makeBuyFile(fundCode), json);
    }


    private static void deleteFile(String file) {
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
    }


    public static List<FundInfo> listBuyFunds() {
        List<FundInfo> fundInfos = new ArrayList<>();
        File[] files = FileUtils.listRootFiles();
        for (File file : files) {
            String name = file.getName();
            if (name.startsWith("BUY_INFO_OF_")) {
                String fundCode = name.replaceAll("BUY_INFO_OF_", "");
                FundInfo fundInfo = getFundInfo(fundCode);
                fundInfos.add(fundInfo);
            }
        }
        return fundInfos;
    }

    private static String makeFundPriceFile(String fundCode) {
        return "FUND_PRICE_OF_" + fundCode;
    }

    private static String makeBuyFile(String fundCode) {
        return "BUY_INFO_OF_" + fundCode;
    }
}



