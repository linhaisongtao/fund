package com.example.daisongsong.chartapp.book.model;

import java.io.Serializable;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class FundInfo implements Serializable{
    private String mFundCode;
    private String name;

    public String getFundCode() {
        return mFundCode;
    }

    public void setFundCode(String fundCode) {
        mFundCode = fundCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
