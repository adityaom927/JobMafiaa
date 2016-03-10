package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.app.finalcode.getjob.getjob.utility.Myjob_PostAdapter;

import java.util.ArrayList;

public class Jobs_for_u extends AppCompatActivity implements FetchDataListener
{
    ArrayList<Job_Post_class> jobs_matched;
    LoginClass l;
    Job_Post_class job1;
    ArrayList<String> temp_list;
    ArrayList<Fav_Job> fav_list=new ArrayList<>();
    ArrayList<User_Apply> applied_list=new ArrayList<>();
    TextView for_u_field,for_u_loc,tool_text;
    ListView for_u_list;
    String field,loc;
    ProgressDialog p;
    int flag=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobs_for_u);

        tool_text=(TextView)findViewById(R.id.tool_text);
        for_u_field=(TextView)findViewById(R.id.for_u_field);
        for_u_loc=(TextView)findViewById(R.id.for_u_loc);
        for_u_list=(ListView)findViewById(R.id.for_u_list);

        Intent in=getIntent();
        l=(LoginClass)in.getSerializableExtra("loginclass");

        p=new ProgressDialog(Jobs_for_u.this);
        p.setTitle("fetching required details");
        p.show();
        AsyncTaskClass task=new AsyncTaskClass(this);
        AsyncTaskClass task2=new AsyncTaskClass(this);
        task2.execute("job_applied", l.getEmail());
        task.execute("fav_jobs", l.getEmail());

        field=in.getStringExtra("user_pref_field");
        loc=in.getStringExtra("user_pref_loc");

        for_u_field.setText(field);
        for_u_loc.setText(loc);

        if(in.getStringExtra("flag").equals("1"))
        {
            flag = 1;
        }
        else if(in.getStringExtra("flag").equals("2"))
        {
            flag = 2;
        }
        AsyncTaskClass jobs_task=new AsyncTaskClass(this);
        jobs_task.execute("all_jobs");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        for_u_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (temp_list!=null) {

                } else {
                    Log.e("position=>", "" + position + ", ");
//                        Log.e("id at position=>",""+search_list.get(position).getJob_id());
                    Intent in1 = new Intent(Jobs_for_u.this, Job_Activity.class);
                    if (check_fav(jobs_matched.get(position).getJob_id(), l.getEmail())) {
                        in1.putExtra("fav_flag", 1);
                    } else {
                        in1.putExtra("fav_flag", 0);
                    }

                    job1 = new Job_Post_class();
                    job1 = jobs_matched.get(position);

                    in1.putExtra("loginclass", l);
                    in1.putExtra("job", job1);
                    //Log.e("user_apply=>",""+user_apply.size()+", ");
                    for (int count = 0; count < applied_list.size(); count++) {
                        if (applied_list.get(count).j.getJob_id().equals(job1.getJob_id())) {
                            in1.putExtra("user_apply", applied_list.get(count));
                            break;
                        }

                        //}
                    }

                    int apply_flag = -1;

                    if (applied_list.size() != 0) {
                        for (int count = 0; count < applied_list.size(); count++) {
                            if (applied_list.get(count).getJ().getJob_id().equals(job1.getJob_id())) {
                                apply_flag = 1;
                                break;
                            } else
                                apply_flag = 0;
                        }
                    } else
                        apply_flag = 0;

                    in1.putExtra("applied_flag", apply_flag);
                    startActivity(in1);
                }
            }
        });

    }

    public boolean check_fav(String jid,String email)
    {
        for(int count=0;count<fav_list.size();count++ )
        {
            if(fav_list.get(count).getJob_id().equals(jid))
            {
                return true;
            }
        }
        return false;
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onFetchComplete_resume_check(int result1) {

    }

    @Override
    public void onFetchComplete_fav(ArrayList<Fav_Job> data)
    {
        fav_list=data;

    }

    @Override
    public void onFetchComplete_profile_edit(String s) {

    }

    @Override
    public void onFetchComplete_skill_add(String s) {

    }

    @Override
    public void onFetchComplete_skill(ArrayList<String> s) {

    }

    @Override
    public void onFetchComplete_edit_exp(String str) {

    }

    @Override
    public void onFetchComplete_skill_delete(String s) {

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
    public void onFetchComplete_company_details(Company c) {

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
    public void onFetchComplete_edu_edit(String s) {

    }

    @Override
    public void onFetchComplete_edu_delete(String s) {

    }

    @Override
    public void onFetchComplete_edu_add(String s) {

    }

    @Override
    public void onFetchComplete_apply(ArrayList<User_Apply> data)
    {
        applied_list=data;

    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<Skills> data3, ArrayList<Certificate> data4) {

    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data)
    {
        p.dismiss();
        jobs_matched=new ArrayList<Job_Post_class>();
        if(flag==2)
        {
            for (int count = 0; count < data.size(); count++)
            {
                Log.e("job_id=>",""+data.get(count).getJob_id());
                if (loc.equals(data.get(count).getJob_location()) && field.equals(data.get(count).c.getIndustry()))
                {
                    jobs_matched.add(data.get(count));
                    Log.e("matched=>",loc+","+field);
                }
            }
        }
        if(flag==1)
        {
            for (int count = 0; count < data.size(); count++)
            {
                Log.e("job_id=>",""+data.get(count).getJob_id());
                if (loc.equals(data.get(count).getJob_location()) || field.equals(data.get(count).c.getIndustry()))
                {
                    jobs_matched.add(data.get(count));
                    Log.e("matched=>", loc + " or " + field);
                }
            }
        }

        if(jobs_matched.size()<=0)
        {
            temp_list=new ArrayList<String>();
            temp_list.add("Try Completing your Profile for best results!");
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(Jobs_for_u.this,android.R.layout.simple_list_item_1,temp_list);
            for_u_list.setAdapter(adapter);
        }
        else
        {
            Myjob_PostAdapter adapter=new Myjob_PostAdapter(Jobs_for_u.this,R.layout.jobs_item,jobs_matched,l,fav_list,applied_list);
            for_u_list.setAdapter(adapter);
        }

    }

    @Override
    public void onFetchFailure(String msg)
    {
        if(msg.equals("jobs"))
        {
            Log.e("dismisses progress", "p complete failure");
            p.dismiss();
            ArrayList<String> arl=new ArrayList<>();
            arl.add("                         No Jobs Posted!");
            ArrayAdapter<String> aadapter = new ArrayAdapter<String>(Jobs_for_u.this, android.R.layout.simple_list_item_1, arl);
            for_u_list.setAdapter(aadapter);
        }
        if(msg.equals("fav"))
            fav_list=null;
        if(msg.equals("apply"))
            applied_list=null;
    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }
}