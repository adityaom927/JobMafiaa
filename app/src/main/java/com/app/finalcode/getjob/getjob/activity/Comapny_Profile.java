package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finalcode.getjob.getjob.R;
import com.app.finalcode.getjob.getjob.classes.Adds;
import com.app.finalcode.getjob.getjob.classes.Certificate;
import com.app.finalcode.getjob.getjob.classes.Company;
import com.app.finalcode.getjob.getjob.classes.Education;
import com.app.finalcode.getjob.getjob.classes.Experience;
import com.app.finalcode.getjob.getjob.classes.Fav_Job;
import com.app.finalcode.getjob.getjob.classes.FetchDataListener;
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
import com.app.finalcode.getjob.getjob.classes.Skills;
import com.app.finalcode.getjob.getjob.classes.User;
import com.app.finalcode.getjob.getjob.classes.User_Apply;
import com.app.finalcode.getjob.getjob.classes.User_applied;
import com.app.finalcode.getjob.getjob.utility.AsyncTaskClass;
import com.app.finalcode.getjob.getjob.utility.ImageLoader;

import java.util.ArrayList;

public class Comapny_Profile extends AppCompatActivity implements FetchDataListener
{
    LoginClass l=new LoginClass();
    TextView company_p_name,user_p_city,company_p_email,company_p_phone,user_p_dob,company_p_hometown,user_p_pin,user_p_address,user_p_resume,tool_text;
    ImageView name_icon,edit_profile_basic,edit_personal,edit_resume;
    android.support.v7.widget.CardView logo_card;

    ProgressDialog p;

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company__profile);

        name_icon=(ImageView)findViewById(R.id.name_icon);
        edit_profile_basic=(ImageView)findViewById(R.id.edit_profile_basic);
        edit_personal=(ImageView)findViewById(R.id.edit_personal);
        edit_resume=(ImageView)findViewById(R.id.edit_resume);

        tool_text=(TextView)findViewById(R.id.tool_text);
        user_p_resume=(TextView)findViewById(R.id.user_p_resume);
        company_p_name=(TextView)findViewById(R.id.company_p_name);
        user_p_city=(TextView)findViewById(R.id.user_p_city);
        company_p_email=(TextView)findViewById(R.id.company_p_email);
        company_p_phone=(TextView)findViewById(R.id.company_p_phone);
        user_p_dob=(TextView)findViewById(R.id.user_p_dob);
        company_p_hometown=(TextView)findViewById(R.id.company_p_hometown);
        user_p_pin=(TextView)findViewById(R.id.user_p_pin);
        user_p_address=(TextView)findViewById(R.id.user_p_address);


        logo_card=(android.support.v7.widget.CardView)findViewById(R.id.logo_card);

        edit_profile_basic.setVisibility(View.INVISIBLE);
        edit_personal.setVisibility(View.INVISIBLE);
        edit_resume.setVisibility(View.INVISIBLE);
        Intent in=getIntent();
        l=(LoginClass)in.getSerializableExtra("loginclass");
        Log.e("email=>", "" + l.getEmail());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        p=new ProgressDialog(Comapny_Profile.this);
        p.show();
        p.setCancelable(false);
        String cpom_email=in.getStringExtra("comp_email");
        if(l.getType().equals("user"))
        {
            profile_task.execute("company_details", cpom_email);
            //name_icon.setVisibility(View.INVISIBLE);
            edit_profile_basic.setVisibility(View.INVISIBLE);
            edit_personal.setVisibility(View.INVISIBLE);
            edit_resume.setVisibility(View.INVISIBLE);
            logo_card.setVisibility(View.INVISIBLE);
        }
        else if(l.getType().equals("company"))
        {
            profile_task.execute("company_details", l.getEmail());
        }
    }

    @Override
    public void onFetchComplete_resume_check(int result1) {

    }

    @Override
    public void onFetchComplete_fav(ArrayList<Fav_Job> data)
    {

    }

    @Override
    public void onFetchComplete_profile_edit(String s)
    {


    }

    @Override
    public void onFetchComplete_skill_add(String s)
    {


    }

    @Override
    public void onFetchComplete_skill(ArrayList<String> s) {

    }

    @Override
    public void onFetchComplete_edit_exp(String str) {

    }

    @Override
    public void onFetchComplete_skill_delete(String s)
    {

    }

    @Override
    public void onFetchComplete_edit_work(String s) {

    }

    @Override
    public void onFetchComplete_delete_exp(String result) {

    }

    @Override
    public void onFetchComplete_add_exp(String s) {

    }

    @Override
    public void onFetchComplete_adds(ArrayList<Adds> ads) {

    }

    @Override
    public void onFetchComplete_delete_certi(String s) {

    }

    @Override
    public void onFetchComplete_companies(ArrayList<String> companies) {

    }

    @Override
    public void onFetchComplete_company_details(Company c)
    {
        Log.e("completed", "" + c);
        p.dismiss();
        //company_p_name,user_p_city,company_p_email,company_p_phone,user_p_dob,company_p_hometown,user_p_pin,user_p_address
        company_p_name.setText(c.getCompany_name());
        String t=c.getCompany_city()+", "+c.getCompany_state();
        user_p_city.setText(t);
        company_p_email.setText(c.getCompany_email());
        company_p_phone.setText(c.getCompany_contact());
        if(c.getCompany_start().equals("0000-00-00"))
            user_p_dob.setText("No Start Date Mentioned");
        else
            user_p_dob.setText(c.getCompany_start());
        company_p_hometown.setText(c.getCompany_city());
        user_p_pin.setText(c.getCompany_zip());
        user_p_address.setText(c.getCompany_address());
        user_p_resume.setText(c.getCompany_logo());
        int loader = R.drawable.mcd_logo;
        String image_url = "http://jobmafiaa.com//assets/uploads/"+c.getCompany_logo();
        ImageLoader imgLoader = new ImageLoader(Comapny_Profile.this);
        imgLoader.DisplayImage(image_url, loader, name_icon);

    }

    @Override
    public void onFetchComplete_location(ArrayList<String> al) {

    }

    @Override
    public void onFetchComplete_add_post(String s) {

    }

    @Override
    public void onFetchComplete_jobs_posted(ArrayList<Job_Post_class> j) {

    }

    @Override
    public void onFetchComplete_field(ArrayList<String> al) {

    }

    @Override
    public void onFetchComplete_user_applied(ArrayList<User_applied> j) {

    }



    @Override
    public void onFetchComplete_edu_edit(String s)
    {

    }

    @Override
    public void onFetchComplete_edu_delete(String s)
    {


    }

    @Override
    public void onFetchComplete_edu_add(String s)
    {

    }

    @Override
    public void onFetchComplete_apply(ArrayList<User_Apply> data)
    {

    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> u, ArrayList<Experience> ex, ArrayList<Education> ed, ArrayList<Skills> sk, ArrayList<Certificate> ct)
    {



    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data)
    {

    }

    @Override
    public void onFetchFailure(String msg)
    {

    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }


}
