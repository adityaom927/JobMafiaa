package com.app.finalcode.getjob.getjob.classes;

import java.util.ArrayList;

/**
 * Created by 5 on 16-Jan-16.
 */
public interface FetchDataListener
{
    void onFetchComplete_resume_check(int result1);
    public void onFetchComplete_fav(ArrayList<Fav_Job> data);
    void onFetchComplete_profile_edit(String s);
    void onFetchComplete_skill_add(String s);
    void onFetchComplete_skill(ArrayList<String> s);
    void onFetchComplete_edit_exp(String str);
    void onFetchComplete_skill_delete(String s);
    void onFetchComplete_edit_work(String s);
    void onFetchComplete_delete_exp(String result);
    void onFetchComplete_add_exp(String s);
    void onFetchComplete_adds(ArrayList<Adds> ads);
    void onFetchComplete_delete_certi(String s);
    void onFetchComplete_companies(ArrayList<String> companies);
    void onFetchComplete_company_details(Company c);
    void onFetchComplete_location(ArrayList<String> al);
    void onFetchComplete_add_post(String s);
    void onFetchComplete_jobs_posted(ArrayList<Job_Post_class> j );
    void onFetchComplete_field(ArrayList<String> al);
    void onFetchComplete_user_applied(ArrayList<User_applied> j );
    void onFetchComplete_edu_edit(String s);//onFetchComplete_edu_delete
    void onFetchComplete_edu_delete(String s);
    void onFetchComplete_edu_add(String s);
    public void onFetchComplete_apply(ArrayList<User_Apply> data);
    public void onFetchComplete_profile(ArrayList<User> data,ArrayList<Experience> data1,ArrayList<Education> data2,ArrayList<Skills> data3,ArrayList<Certificate> data4);
    public void onFetchComplete_job(ArrayList<Job_Post_class> data);
    void onFetchFailure(String msg);
    void onFetchComplete_all_seekers(ArrayList<User> result);
}