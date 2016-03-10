package com.app.finalcode.getjob.getjob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.app.finalcode.getjob.getjob.R;
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.User_applied;

import java.util.ArrayList;

/**
 * Created by 5 on 27-Jan-16.
 */
public class Job_Post_Activity_Company extends AppCompatActivity
{
    TextView job_company,job_location,job_title,job_industry,job_experience,job_Skills,job_designation,job_date;
    ListView applier_list;
    ArrayList<User_applied> user=new ArrayList<User_applied>();
    Job_Post_class j=new Job_Post_class();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_activity_company);

        job_title=(TextView)findViewById(R.id.job_title);
        job_industry=(TextView)findViewById(R.id.job_industry);
        job_experience=(TextView)findViewById(R.id.job_experience);
        job_Skills=(TextView)findViewById(R.id.job_Skills);
        job_designation=(TextView)findViewById(R.id.job_designation);
        job_date=(TextView)findViewById(R.id.job_date);

        applier_list=(ListView)findViewById(R.id.applier_list);



        Intent in=getIntent();
        j=(Job_Post_class)in.getSerializableExtra("job_post");
        job_title.setText(j.getJob_title());
        //job_industry.setText(j.get);

    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

}
