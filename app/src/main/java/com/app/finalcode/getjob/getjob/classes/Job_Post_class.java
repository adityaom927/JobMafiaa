package com.app.finalcode.getjob.getjob.classes;

import java.io.Serializable;

/**
 * Created by we on 21/12/15.
 */
public class Job_Post_class implements Serializable
{
    String skill_required;
    String experience;
    String job_desc;
    String job_location;
    String last_date;
    String min_qual;
    String vacancy;
    String category;
    String job_type;
    String salary_to;
    String salary_from;
    String posted_date;
    String job_id;
    String job_title;


    public Company c=new Company();


    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getSkill_required() {
        return skill_required;
    }

    public void setSkill_required(String skill_required) {
        this.skill_required = skill_required;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getMin_qual() {
        return min_qual;
    }

    public void setMin_qual(String min_qual) {
        this.min_qual = min_qual;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getSalary_to() {
        return salary_to;
    }

    public void setSalary_to(String salary_to) {
        this.salary_to = salary_to;
    }

    public String getSalary_from() {
        return salary_from;
    }

    public void setSalary_from(String salary_from) {
        this.salary_from = salary_from;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }


}
