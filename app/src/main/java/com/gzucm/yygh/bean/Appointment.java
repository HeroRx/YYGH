package com.gzucm.yygh.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class Appointment extends BmobObject {
    private Doctor a_doctor;//	选择科室和医院
    private Patient a_name;//		患者名字
    private Integer a_queueno;//		挂号流水号
    private String a_type;//		挂号类型

    public Doctor getA_doctor() {
        return a_doctor;
    }

    public void setA_doctor(Doctor a_doctor) {
        this.a_doctor = a_doctor;
    }

    public Patient getA_name() {
        return a_name;
    }

    public void setA_name(Patient a_name) {
        this.a_name = a_name;
    }

    public Integer getA_queueno() {
        return a_queueno;
    }

    public void setA_queueno(Integer a_queueno) {
        this.a_queueno = a_queueno;
    }

    public String getA_type() {
        return a_type;
    }

    public void setA_type(String a_type) {
        this.a_type = a_type;
    }
}
