package com.app.finalcode.getjob.getjob.classes;

import java.io.Serializable;

/**
 * Created by abc123 on 10/12/15.
 */
public class User implements Serializable
{
    String uid;

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    String skills;
    String password;
    String name;
    String gender;
    String address;
    String contact;
    String resume;
    String email;
    String dob;
    String pref_field;
    String pref_location;
    String education;
    String total_exp;
    String industry;
    String zip;
    String city;
    String state;
    String country;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPref_field() {
        return pref_field;
    }

    public void setPref_field(String pref_field) {
        this.pref_field = pref_field;
    }

    public String getPref_location() {
        return pref_location;
    }

    public void setPref_location(String pref_location) {
        this.pref_location = pref_location;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTotal_exp() {
        return total_exp;
    }

    public void setTotal_exp(String total_exp) {
        this.total_exp = total_exp;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
