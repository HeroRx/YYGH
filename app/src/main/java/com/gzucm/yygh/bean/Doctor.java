package com.gzucm.yygh.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class Doctor extends BmobObject implements Serializable {

    private Department d_dcode;//	所在科室
    private boolean d_isprofess;    //是否为教授
    //    private Hospital d_hosptial;	//所在医院 方便查询
    private String d_name;    //医生名字
    private String d_num;    //医生编号
    private BmobFile d_photo;    //医生照片
    private boolean d_sex;    //1男0女
    private String d_specialty;    //专长
    private Registrationtype d_registrationtype;    //为教授的挂号类型
    private String d_titles;    //职称
    private boolean is_visited;    //当天是否可以就诊
    private Integer d_mcount;//上午挂的号
    private Integer d_acount;//下午挂的号
    private Integer d_nowacount;//上午已经挂的号
    private Integer d_nowmcount;//下午已经挂的号
    private String d_room;


    public Integer getD_nowacount() {
        return d_nowacount;
    }

    public void setD_nowacount(Integer d_nowacount) {
        this.d_nowacount = d_nowacount;
    }

    public Integer getD_nowmcount() {
        return d_nowmcount;
    }

    public void setD_nowmcount(Integer d_nowmcount) {
        this.d_nowmcount = d_nowmcount;
    }

    public String getD_room() {
        return d_room;
    }

    public void setD_room(String d_room) {
        this.d_room = d_room;
    }

    public Integer getD_mcount() {
        return d_mcount;
    }

    public void setD_mcount(Integer d_mcount) {
        this.d_mcount = d_mcount;
    }

    public Integer getD_acount() {
        return d_acount;
    }

    public void setD_acount(Integer d_acount) {
        this.d_acount = d_acount;
    }

    public Department getD_dcode() {
        return d_dcode;
    }

    public void setD_dcode(Department d_dcode) {
        this.d_dcode = d_dcode;
    }

    public boolean isD_isprofess() {
        return d_isprofess;
    }

    public void setD_isprofess(boolean d_isprofess) {
        this.d_isprofess = d_isprofess;
    }

    //    public Hospital getD_hosptial() {
//        return d_hosptial;
//    }
//
//    public void setD_hosptial(Hospital d_hosptial) {
//        this.d_hosptial = d_hosptial;
//    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_num() {
        return d_num;
    }

    public void setD_num(String d_num) {
        this.d_num = d_num;
    }

    public BmobFile getD_photo() {
        return d_photo;
    }

    public void setD_photo(BmobFile d_photo) {
        this.d_photo = d_photo;
    }

    public boolean isD_sex() {
        return d_sex;
    }

    public void setD_sex(boolean d_sex) {
        this.d_sex = d_sex;
    }

    public String getD_specialty() {
        return d_specialty;
    }

    public void setD_specialty(String d_specialty) {
        this.d_specialty = d_specialty;
    }

    public Registrationtype getD_registrationtype() {
        return d_registrationtype;
    }

    public void setD_registrationtype(Registrationtype d_registrationtype) {
        this.d_registrationtype = d_registrationtype;
    }

    public String getD_titles() {
        return d_titles;
    }

    public void setD_titles(String d_titles) {
        this.d_titles = d_titles;
    }

    public boolean is_visited() {
        return is_visited;
    }

    public void setIs_visited(boolean is_visited) {
        this.is_visited = is_visited;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "d_dcode=" + d_dcode +
                ", d_isprofessor=" + d_isprofess +
//                ", d_hosptial=" + d_hosptial +
                ", d_name='" + d_name + '\'' +
                ", d_num='" + d_num + '\'' +
                ", d_photo=" + d_photo +
                ", d_sex=" + d_sex +
                ", d_specialty='" + d_specialty + '\'' +
                ", d_registrationtype=" + d_registrationtype +
                ", d_titles='" + d_titles + '\'' +
                ", is_visited=" + is_visited +
                '}';
    }
}
