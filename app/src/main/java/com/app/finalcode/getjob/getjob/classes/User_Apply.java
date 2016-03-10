package com.app.finalcode.getjob.getjob.classes;

import java.io.Serializable;

/**
 * Created by 5 on 18-Jan-16.
 */
public class User_Apply implements Serializable
{
    String user_email;
    String apply_date;
    String curr_status;
    String app_id;

    public Job_Post_class j=new Job_Post_class();

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public Job_Post_class getJ() {
        return j;
    }

    public void setJ(Job_Post_class j) {
        this.j = j;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public String getCurr_status() {
        return curr_status;
    }

    public void setCurr_status(String curr_status) {
        this.curr_status = curr_status;
    }
}
