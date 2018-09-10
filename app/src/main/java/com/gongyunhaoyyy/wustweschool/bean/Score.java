package com.gongyunhaoyyy.wustweschool.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by acer on 2017/10/23.
 */

public class Score extends DataSupport{

    private String jd,zxs,kclbmc,zcj,kkxq,kcxzmc,ksxzmc,kcmc,xf;

    public Score() {}

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getZxs() {
        return zxs;
    }

    public void setZxs(String zxs) {
        this.zxs = zxs;
    }

    public String getKclbmc() {
        return kclbmc;
    }

    public void setKclbmc(String kclbmc) {
        this.kclbmc = kclbmc;
    }

    public String getZcj() {
        return zcj;
    }

    public void setZcj(String zcj) {
        this.zcj = zcj;
    }

    public String getKkxq() {
        return kkxq;
    }

    public void setKkxq(String kkxq) {
        this.kkxq = kkxq;
    }

    public String getKcxzmc() {
        return kcxzmc;
    }

    public void setKcxzmc(String kcxzmc) {
        this.kcxzmc = kcxzmc;
    }

    public String getKsxzmc() {
        return ksxzmc;
    }

    public void setKsxzmc(String ksxzmc) {
        this.ksxzmc = ksxzmc;
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
