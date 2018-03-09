package com.gongyunhaoyyy.wustweschool.LitePal;

import org.litepal.crud.DataSupport;


/**
 * Created by acer on 2017/10/14.
 */

public class Course extends DataSupport{
    private int id;
    private String dwmc,jsmc,kcxzmc,kcsj,ktmc,kcsxm,jsxm,xkjd,zxs,kkzc,kcmc,xf;

    public String getKkzc() {
        return kkzc;
    }

    public void setKkzc(String kkzc) {
        this.kkzc = kkzc;
    }

    public String getKcsj() {
        return kcsj;
    }

    public void setKcsj(String kcsj) {
        this.kcsj = kcsj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public String getKcxzmc() {
        return kcxzmc;
    }

    public void setKcxzmc(String kcxzmc) {
        this.kcxzmc = kcxzmc;
    }


    public String getKtmc() {
        return ktmc;
    }

    public void setKtmc(String ktmc) {
        this.ktmc = ktmc;
    }

    public String getKcsxm() {
        return kcsxm;
    }

    public void setKcsxm(String kcsxm) {
        this.kcsxm = kcsxm;
    }

    public String getJsxm() {
        return jsxm;
    }

    public void setJsxm(String jsxm) {
        this.jsxm = jsxm;
    }

    public String getXkjd() {
        return xkjd;
    }

    public void setXkjd(String xkjd) {
        this.xkjd = xkjd;
    }

    public String getZxs() {
        return zxs;
    }

    public void setZxs(String zxs) {
        this.zxs = zxs;
    }


    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }
}
