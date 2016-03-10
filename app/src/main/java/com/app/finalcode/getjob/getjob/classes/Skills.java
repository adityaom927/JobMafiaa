package com.app.finalcode.getjob.getjob.classes;

import java.io.Serializable;

/**
 * Created by 5 on 19-Jan-16.
 */
public class Skills implements Serializable
{
    String skill_id,skill_name,user_email;

    public String getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(String skill_id) {
        this.skill_id = skill_id;
    }

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
