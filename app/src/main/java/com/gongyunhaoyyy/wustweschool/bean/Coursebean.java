package com.gongyunhaoyyy.wustweschool.bean;

import java.io.Serializable;

/**
 * Created by acer on 2017/10/26.
 */

public class Coursebean implements Serializable {
    private int id;
    //以下用于json获取数据后填充
    //jsmc教室名称
    private String dwmc,jsmc,kcxzmc,kcsj,ktmc,kcsxm,jsxm,xkjd,zxs,kkzc,kcmc,xf;
    //以下用于课表适配：kkzc:开课周次，kcxq:课程星期，p_kcjc:课程节次(第几大节)
    private String[] p_kkzc_c;//开课周次粗略
    private int[] p_kcxq;


    //以下用于课表适配：kkzc:开课周次，kcxq:课程星期，kcjc:课程节次(第几大节)
    private String kcsj1,kcsj2,kcsj3,jsmc1,jsmc2,jsmc3;
    private int kkzc1s;
    private int kkzc1e;
    private int kkzc2s;
    private int kkzc2e;
    private int kkzc3s;
    private int kkzc3e;
    private int kcxq1;
    private int kcxq2;

    public int getKcxq3() {
        return kcxq3;
    }

    public void setKcxq3(int kcxq3) {
        this.kcxq3 = kcxq3;
    }

    private int kcxq3;
    private int kcjc1,kcjc2,kcjc3;

    public String getKcsj1() {
        return kcsj1;
    }

    public void setKcsj1(String kcsj1) {
        this.kcsj1 = kcsj1;
    }

    public String getKcsj2() {
        return kcsj2;
    }

    public void setKcsj2(String kcsj2) {
        this.kcsj2 = kcsj2;
    }

    public String getKcsj3() {
        return kcsj3;
    }

    public void setKcsj3(String kcsj3) {
        this.kcsj3 = kcsj3;
    }

    public String getJsmc1() {
        return jsmc1;
    }

    public void setJsmc1(String jsmc1) {
        this.jsmc1 = jsmc1;
    }

    public String getJsmc2() {
        return jsmc2;
    }

    public void setJsmc2(String jsmc2) {
        this.jsmc2 = jsmc2;
    }

    public String getJsmc3() {
        return jsmc3;
    }

    public void setJsmc3(String jsmc3) {
        this.jsmc3 = jsmc3;
    }

    public int getKkzc1s() {
        return kkzc1s;
    }

    public void setKkzc1s(int kkzc1s) {
        this.kkzc1s = kkzc1s;
    }

    public int getKkzc1e() {
        return kkzc1e;
    }

    public void setKkzc1e(int kkzc1e) {
        this.kkzc1e = kkzc1e;
    }

    public int getKkzc2s() {
        return kkzc2s;
    }

    public void setKkzc2s(int kkzc2s) {
        this.kkzc2s = kkzc2s;
    }

    public int getKkzc2e() {
        return kkzc2e;
    }

    public void setKkzc2e(int kkzc2e) {
        this.kkzc2e = kkzc2e;
    }

    public int getKkzc3s() {
        return kkzc3s;
    }

    public void setKkzc3s(int kkzc3s) {
        this.kkzc3s = kkzc3s;
    }

    public int getKkzc3e() {
        return kkzc3e;
    }

    public void setKkzc3e(int kkzc3e) {
        this.kkzc3e = kkzc3e;
    }

    public int getKcxq1() {
        return kcxq1;
    }

    public void setKcxq1(int kcxq1) {
        this.kcxq1 = kcxq1;
    }

    public int getKcxq2() {
        return kcxq2;
    }

    public void setKcxq2(int kcxq2) {
        this.kcxq2 = kcxq2;
    }

    public int getKcjc1() {
        return kcjc1;
    }

    public void setKcjc1(int kcjc1) {
        this.kcjc1 = kcjc1;
    }

    public int getKcjc2() {
        return kcjc2;
    }

    public void setKcjc2(int kcjc2) {
        this.kcjc2 = kcjc2;
    }

    public int getKcjc3() {
        return kcjc3;
    }

    public void setKcjc3(int kcjc3) {
        this.kcjc3 = kcjc3;
    }

    public Coursebean(int id, String dwmc, String jsmc, String kcxzmc, String kcsj, String ktmc, String kcsxm, String jsxm, String xkjd, String zxs, String kkzc, String kcmc, String xf) {
        this.id = id;
        this.dwmc = dwmc;
        this.jsmc = jsmc;
        this.kcxzmc = kcxzmc;
        this.kcsj = kcsj;
        this.ktmc = ktmc;
        this.kcsxm = kcsxm;
        this.jsxm = jsxm;
        this.xkjd = xkjd;
        this.zxs = zxs;
        this.kkzc = kkzc;
        this.kcmc = kcmc;
        this.xf = xf;
    }

    private String[] p_kcjc;
    private String[] p_jsmc;

    public String[] getP_jsmc() {
        return p_jsmc;
    }

    public void setP_jsmc(String[] p_jsmc) {
        this.p_jsmc = p_jsmc;
    }

    public String[] getP_kkzc_c() {
        return p_kkzc_c;
    }

    public void setP_kkzc_c(String[] p_kkzc_c) {
        this.p_kkzc_c = p_kkzc_c;
    }

    public int[] getP_kcxq() {
        return p_kcxq;
    }

    public void setP_kcxq(int[] p_kcxq) {
        this.p_kcxq = p_kcxq;
    }

    public String[] getP_kcjc() {
        return p_kcjc;
    }

    public void setP_kcjc(String[] p_kcjc) {
        this.p_kcjc = p_kcjc;
    }

    public Coursebean(int id, String dwmc, String jsmc, String kcxzmc, String kcsj, String ktmc, String kcsxm, String jsxm, String xkjd, String zxs, String kkzc, String kcmc, String xf, String[] p_kkzc_c, int[] p_kcxq, String[] p_kcjc, String[] p_jsmc) {
        this.id = id;
        this.dwmc = dwmc;
        this.jsmc = jsmc;
        this.kcxzmc = kcxzmc;
        this.kcsj = kcsj;
        this.ktmc = ktmc;
        this.kcsxm = kcsxm;
        this.jsxm = jsxm;
        this.xkjd = xkjd;
        this.zxs = zxs;
        this.kkzc = kkzc;
        this.kcmc = kcmc;
        this.xf = xf;
        this.p_kkzc_c = p_kkzc_c;
        this.p_kcxq = p_kcxq;
        this.p_kcjc = p_kcjc;
        this.p_jsmc=p_jsmc;
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

    public String getKcsj() {
        return kcsj;
    }

    public void setKcsj(String kcsj) {
        this.kcsj = kcsj;
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

    public String getKkzc() {
        return kkzc;
    }

    public void setKkzc(String kkzc) {
        this.kkzc = kkzc;
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
