package com.app.finalcode.getjob.getjob.classes;

import java.io.Serializable;

/**
 * Created by 5 on 19-Jan-16.
 */
public class Certificate implements Serializable
{
    String certificate_id,user_email,name,file_name;

    public String getCertificate_id() {
        return certificate_id;
    }

    public void setCertificate_id(String certificate_id) {
        this.certificate_id = certificate_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
