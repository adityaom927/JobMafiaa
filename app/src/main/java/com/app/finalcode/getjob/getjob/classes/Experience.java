package com.app.finalcode.getjob.getjob.classes;

import java.io.Serializable;

/**
 * Created by 5 on 19-Jan-16.
 */
public class Experience implements Serializable
{
    String exp_id,user_email,company_name,designation,industry,exp_year,exp_month,exp_from_month,exp_from_year,exp_to_month,exp_to_year,current_work;

    public String getExp_id() {
        return exp_id;
    }

    public void setExp_id(String exp_id) {
        this.exp_id = exp_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getExp_from_month() {
        return exp_from_month;
    }

    public void setExp_from_month(String exp_from_month) {
        this.exp_from_month = exp_from_month;
    }

    public String getExp_from_year() {
        return exp_from_year;
    }

    public void setExp_from_year(String exp_from_year) {
        this.exp_from_year = exp_from_year;
    }

    public String getExp_to_month() {
        return exp_to_month;
    }

    public void setExp_to_month(String exp_to_month) {
        this.exp_to_month = exp_to_month;
    }

    public String getExp_to_year() {
        return exp_to_year;
    }

    public void setExp_to_year(String exp_to_year) {
        this.exp_to_year = exp_to_year;
    }

    public String getCurrent_work() {
        return current_work;
    }

    public void setCurrent_work(String current_work) {
        this.current_work = current_work;
    }
}
