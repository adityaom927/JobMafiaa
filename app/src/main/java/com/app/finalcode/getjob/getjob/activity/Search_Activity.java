package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Search_Activity extends AppCompatActivity implements FetchDataListener
{
    Spinner search_loc, search_job;

    LoginClass l = new LoginClass();

    ListView joblist;

    Job_Post_class job1;

    ArrayList<String> search_str;

    Myjob_PostAdapter adapter;

    ProgressDialog p_location, p_search;

    ArrayList<Job_Post_class> search_list = new ArrayList<Job_Post_class>();
    ArrayList<Job_Post_class> main_list= new ArrayList<Job_Post_class>();
    ArrayList<Fav_Job> fav_list = new ArrayList<Fav_Job>();
    ArrayList<User_Apply> user_apply = new ArrayList<User_Apply>();
    ArrayList<String> location = new ArrayList<String>();
    ArrayList<String> field = new ArrayList<String>();

    TextView tool_text;
    FetchDataListener listen = this;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        tool_text=(TextView)findViewById(R.id.tool_text);
        search_job = (Spinner) findViewById(R.id.search_job);
        search_loc = (Spinner) findViewById(R.id.search_loc);
        joblist = (ListView) findViewById(R.id.search_list);

        Intent in = getIntent();
        l = (LoginClass) in.getSerializableExtra("loginclass");
        //getalljob_posts(db);
        p_location = new ProgressDialog(Search_Activity.this);
        p_location.setMessage("Fetching Required Details..");
        p_location.show();
        AsyncTaskClass location_task = new AsyncTaskClass(listen);
        location_task.execute("Location");
        AsyncTaskClass field_task = new AsyncTaskClass(listen);
        field_task.execute("Field");
        Log.e("calling:","field task");
        AsyncTaskClass task = new AsyncTaskClass(this);
        AsyncTaskClass task1 = new AsyncTaskClass(this);
        AsyncTaskClass task2 = new AsyncTaskClass(this);
        if(l!=null)
        {
            task2.execute("job_applied", l.getEmail());
            task.execute("fav_jobs", l.getEmail());
            task1.execute("all_jobs");
        }
        else
            task1.execute("all_jobs");
        search_str=new ArrayList<String>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                p_search = new ProgressDialog(Search_Activity.this);
                p_search.setMessage("Fetching Required Details..");
                p_search.show();

                String lc,f;
                lc=search_loc.getSelectedItem().toString();
                f=search_job.getSelectedItem().toString();
                search_str.clear();
                search_list.clear();
                Log.e("field=>", "" + f);
                Log.e("lc=>",""+lc);
                Log.e("size of joblist:",""+main_list.size()+",before");
                search_list = search_job(f, lc,main_list);
                Log.e("size of joblist:",""+main_list.size()+"after");
                if(search_list.size()==0 )
                {
                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(Search_Activity.this,android.R.layout.simple_list_item_1,search_str);
                    joblist.setAdapter(search_adapter);
                    search_adapter.notifyDataSetChanged();
                    p_search.dismiss();
                }
                else
                {
                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(Search_Activity.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                    joblist.setAdapter(search_adapter);
                    search_adapter.notifyDataSetChanged();
                    p_search.dismiss();
                }
            }
        });


        joblist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (search_str.size() > 0)
                {

                }
                else
                {
                    Log.e("position=>", "" + position + ", ");
//                    Log.e("job at positions=>",""+search_list.get(position).getJob_title()+"!");
                    Log.e("job at positions=>",""+search_list.get(position).getJob_title()+"!");
                    Intent in1 = new Intent(Search_Activity.this, Job_Activity.class);
                    if(l!=null)
                    {
                        if(search_list.size()<=0)
                            if (check_fav(search_list.get(position).getJob_id(), l.getEmail()))
                            {
                                in1.putExtra("fav_flag", 1);
                            }
                            else
                            {
                                in1.putExtra("fav_flag", 0);
                            }
                        else
                            if (check_fav(search_list.get(position).getJob_id(), l.getEmail()))
                            {
                                in1.putExtra("fav_flag", 1);
                            }
                            else
                            {
                                in1.putExtra("fav_flag", 0);
                            }
                        in1.putExtra("loginclass", l);
                    }


                    job1 = new Job_Post_class();
                    job1 = search_list.get(position);


                    in1.putExtra("job", job1);
                    //Log.e("user_apply=>",""+user_apply.size()+", ");
                    if(l!=null)
                    {
                        for (int count = 0; count < user_apply.size(); count++)
                        {
                            if (user_apply.get(count).j.getJob_id().equals(job1.getJob_id()))
                            {
                                in1.putExtra("user_apply", user_apply.get(count));
                                break;
                            }
                        }

                        int apply_flag = -1;

                        if (user_apply.size() != 0)
                        {
                            for (int count = 0; count < user_apply.size(); count++)
                            {
                                if (user_apply.get(count).getJ().getJob_id().equals(job1.getJob_id()))
                                {
                                    apply_flag = 1;
                                    break;
                                }
                                else
                                    apply_flag = 0;
                            }
                        }
                        else
                            apply_flag = 0;

                        in1.putExtra("applied_flag", apply_flag);
                    }

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


    @Override
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
        fav_list = data;
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
    public void onFetchComplete_skill(ArrayList<String> s)
    {

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
    public void onFetchComplete_location(ArrayList<String> l)
    {
        p_location.dismiss();
        location.add("All");
        for(int count=0;count<l.size();count++)
            location.add(l.get(count));

        Collections.sort(location, new Comparator<String>()
        {
            @Override
            public int compare(String s1, String s2)
            {
                return s1.compareToIgnoreCase(s2);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search_Activity.this, android.R.layout.simple_list_item_1, location);
        search_loc.setAdapter(adapter);
        search_loc.setSelection(4);
    }

    @Override
    public void onFetchComplete_add_post(String s) {

    }

    @Override
    public void onFetchComplete_jobs_posted(ArrayList<Job_Post_class> j)
    {

    }

    @Override
    public void onFetchComplete_field(ArrayList<String> al)
    {
        Log.e("completed:","field task");
        Log.e("result:",""+al.size());
        field.add("All");
        for(int count=0;count<al.size();count++)
            field.add(al.get(count));

        Collections.sort(field, new Comparator<String>()
        {
            @Override
            public int compare(String s1, String s2)
            {
                return s1.compareToIgnoreCase(s2);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search_Activity.this, android.R.layout.simple_list_item_1, field);
        search_job.setAdapter(adapter);
        search_job.setSelection(2);
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
    public void onFetchComplete_apply(ArrayList<User_Apply> data) {
        user_apply = data;

    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<Skills> data3, ArrayList<Certificate> data4) {

    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data )
    {
        for(int count=0;count<data.size();count++)
        {
            search_list.add(data.get(count));
            main_list.add(data.get(count));
        }

        Myjob_PostAdapter adapter = new Myjob_PostAdapter(Search_Activity.this, R.layout.jobs_item, main_list, l, fav_list, user_apply);
        joblist.setAdapter(adapter);
        Log.e("got job list size=>", "" + main_list.size());
    }
    @Override
    public void onFetchFailure(String msg)
    {

    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }

    public ArrayList<Job_Post_class> search_job(String field, String loc,ArrayList<Job_Post_class> jobl)
    {
        //search_list.clear();
        Log.e("in search function", "hello");
        Log.e("job size s=>", "" + jobl.size() + "!");

        for (int count = 0; count < jobl.size(); count++)
        {
            Log.e("in for","hello");
            if(field.equals("All")||field.equals("Any"))
            {
                if(loc.equals("All"))
                {
                    for(int count1=0;count1<jobl.size();count1++)
                        search_list.add(jobl.get(count1));
                    break;
                }
                else
                {
                    if (jobl.get(count).getJob_location().equals(loc))
                    {
                        Log.e("found location",""+jobl.get(count).getJob_location());
                        search_list.add(jobl.get(count));
                    }

                }
            }
            else
            {
                if(loc.equals("All"))
                {
                    if (jobl.get(count).c.getIndustry().equals(field))
                    {
                        Log.e("found field",""+jobl.get(count).c.getIndustry());
                        search_list.add(jobl.get(count));
                    }
                }
                else
                {
                    if (jobl.get(count).getJob_location().equals(loc)&& jobl.get(count).c.getIndustry().equals(field))
                    {
                        Log.e("found field",""+jobl.get(count).c.getIndustry());
                        Log.e("found location",""+jobl.get(count).getJob_location());
                        search_list.add(jobl.get(count));
                    }
                }

            }
        }
        return search_list;
    }
}