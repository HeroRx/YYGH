package com.gzucm.yygh.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class Registrationtype extends BmobObject {

    private Integer rt_amount;//	费用
    private String rt_name;//	挂号类型

    public Integer getRt_amount() {
        return rt_amount;
    }

    public void setRt_amount(Integer rt_amount) {
        this.rt_amount = rt_amount;
    }

    public String getRt_name() {
        return rt_name;
    }

    public void setRt_name(String rt_name) {
        this.rt_name = rt_name;
    }
}
