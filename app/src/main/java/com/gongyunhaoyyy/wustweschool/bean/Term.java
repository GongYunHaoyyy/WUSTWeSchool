package com.gongyunhaoyyy.wustweschool.bean;

import org.litepal.crud.DataSupport;

/**
 * 学期
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/19
 * @github https://github.com/GongYunHaoyyy
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class Term extends DataSupport{
    /**
     * xnxq01id : 2018-2019-1
     */
    private String xnxq01id;

    public void setXnxq01id(String xnxq01id) {
        this.xnxq01id = xnxq01id;
    }

    public String getXnxq01id() {
        return xnxq01id;
    }
}
