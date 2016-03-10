package com.app.finalcode.getjob.getjob.classes;

import java.io.Serializable;

/**
 * Created by 5 on 19-Jan-16.
 */
public class Education implements Serializable
{
    String edu_id,user_email,qualification,percentage,board,passing_year;

    public String getEdu_id() {
        return edu_id;
    }

    public void setEdu_id(String edu_id) {
        this.edu_id = edu_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getPassing_year() {
        return passing_year;
    }

    public void setPassing_year(String passing_year) {
        this.passing_year = passing_year;
    }
}
