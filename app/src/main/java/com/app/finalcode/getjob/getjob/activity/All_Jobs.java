package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.finalcode.getjob.getjob.utility.MyAppliedAdapter;
import com.app.finalcode.getjob.getjob.utility.MyUserClassAdapter;
import com.app.finalcode.getjob.getjob.utility.Myjob_PostAdapter;

import java.util.ArrayList;


public class All_Jobs extends ActionBarActivity implements FetchDataListener
{
    ListView job_list;

    MyAppliedAdapter adapter;

    ArrayList<String> jl;
    ArrayList<User_applied> j2;


    TextView tool_text;

    ArrayList<String> arl = new ArrayList<>();
    ArrayList<Job_Post_class> fav_job_list=new ArrayList<Job_Post_class>();
    ArrayList<Job_Post_class> main_list=new ArrayList<Job_Post_class>();
    ArrayList<Job_Post_class> job1_list=new ArrayList<Job_Post_class>();
    ArrayList<Job_Post_class> search_list=new ArrayList<Job_Post_class>();
    ArrayList<Fav_Job> fav_list=new ArrayList<Fav_Job>();
    ArrayList<User_Apply> user_apply=new ArrayList<User_Apply>();
    ArrayList<User_Apply> user_apply1=new ArrayList<User_Apply>();
    User_Apply user=new User_Apply();
    LoginClass l;
    ProgressDialog p,p_search;
    int location_flag=-1,skill_flag=-1,industry_flag=-1,exp_flag=-1,type_flag=-1,salto_flag=-1,company_flag=-1,field_user_flag=-1,My_job_post_flag=-1,appliers_flag=-1,applied_jobs_flag=-1;
    String location,skill,industry,exp,type,salto,company,field_user,my_job_posts;
    int star_flag;
    Job_Post_class job1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_jobs);

        job_list=(ListView)findViewById(R.id.job_post);

        tool_text=(TextView)findViewById(R.id.tool_text);
        tool_text.setText("Job Mafiaa");
        p=new ProgressDialog(All_Jobs.this);

        Intent in1=getIntent();
        l=(LoginClass)in1.getSerializableExtra("loginclass");

        location=""; skill=""; industry=""; exp=""; type=""; salto=""; company=""; field_user=""; my_job_posts="";

        applied_jobs_flag=in1.getIntExtra("applied_jobs", 0);
        Log.e("applied_jobs_flag:","-"+applied_jobs_flag);
        appliers_flag=in1.getIntExtra("appliers", 0);
        Log.e("appliers_flag:","-"+appliers_flag);
        My_job_post_flag=in1.getIntExtra("my_job_posts", 0);
        Log.e("My_job_post_flag:","-"+My_job_post_flag);
        star_flag = in1.getIntExtra("starred", -1);
        Log.e("star_flag :","-"+star_flag );
        field_user=in1.getStringExtra("field_users");
        Log.e("field_user:","-"+field_user);
        company=in1.getStringExtra("company");
        Log.e("company:","-"+company);

        if(company==null)
            company_flag=0;
        else
            company_flag=1;

        if(field_user==null)
            field_user_flag=0;
        else
            field_user_flag=1;

        if(appliers_flag==1)
        {
            Log.e("appliers_flag:","-"+appliers_flag);
            p.setTitle("Fetching Required Details..");
            p.setCancelable(false);
            p.show();
            AsyncTaskClass appliedTask=new AsyncTaskClass(All_Jobs.this);
            appliedTask.execute("user_applied", l.getEmail());
        }
        else if(My_job_post_flag==1)
        {
            Log.e("My_job_post_flag:","-"+My_job_post_flag);
            AsyncTaskClass task=new AsyncTaskClass(this);
            task.execute("fav_jobs", l.getEmail());
            p.setTitle("Fetching Required Details..");
            p.setCancelable(false);
            p.show();
            AsyncTaskClass task21=new AsyncTaskClass(this);
            task21.execute("company_job_posts", l.getEmail());
        }//
        else if(applied_jobs_flag==1)
        {
            Log.e("applied_jobs_flag:","-"+applied_jobs_flag);
            p.setTitle("Fetching Required Details..");
            p.setCancelable(false);
            p.show();

            AsyncTaskClass task2=new AsyncTaskClass(this);
            task2.execute("job_applied", l.getEmail());


        }//star_flag
        else if(star_flag==1)
        {
            Log.e("star_flag :","-"+star_flag );
            p.setTitle("Fetching Required Details..");
            p.setCancelable(false);
            p.show();
            AsyncTaskClass task=new AsyncTaskClass(this);
            AsyncTaskClass task1=new AsyncTaskClass(this);
            AsyncTaskClass task2=new AsyncTaskClass(this);
            task2.execute("job_applied", l.getEmail());
            task.execute("fav_jobs", l.getEmail());
            task1.execute("all_jobs");
        }
        else if(field_user_flag==1)
        {
            Log.e("field_user:","-"+field_user);
            AsyncTaskClass task=new AsyncTaskClass(this);
            task.execute("fav_jobs", l.getEmail());
            p.setTitle("Fetching Required Details..");
            p.setCancelable(false);
            p.show();
            AsyncTaskClass appliedTask=new AsyncTaskClass(All_Jobs.this);
            appliedTask.execute("all_seekers", l.getEmail());
        }
        else
        {
            p.setTitle("Fetching Required Details..");
            p.setCancelable(false);
            p.show();
            AsyncTaskClass task=new AsyncTaskClass(this);
            AsyncTaskClass task1=new AsyncTaskClass(this);
            AsyncTaskClass task2=new AsyncTaskClass(this);
            if(l!=null)
            {
                task2.execute("job_applied", l.getEmail());
                task.execute("fav_jobs", l.getEmail());
                task1.execute("all_jobs");
            }
            else
                task1.execute("all_jobs");
        }

        location=in1.getStringExtra("location");
        Log.e("location=>",""+location);
        if(location==null)
            location_flag=0;
        else if(location.equals("Select Location"))
            location_flag=0;
        else
            location_flag=1;

        skill=in1.getStringExtra("skill");
        Log.e("skill=>",""+skill+",!");
        if(skill==null)
            skill_flag=0;
        else if(skill.equals("Select Skill"))
            skill_flag=0;
        else
            skill_flag=1;

        industry=in1.getStringExtra("field");
        Log.e("industry=>",""+industry+",!");
        if(industry==null)
            industry_flag=0;
        else if(industry.equals("Select Field"))
            industry_flag=0;
        else
            industry_flag=1;

        exp=in1.getStringExtra("exp");
        Log.e("exp=>",""+exp+",!");
        if(exp==null)
            exp_flag=0;
        else if(exp.equals(""))
            exp_flag=0;
        else
            exp_flag=1;

        type=in1.getStringExtra("type");
        Log.e("type=>",""+type+",!");
        if(type==null)
            type_flag=0;
        else if(type.equals(""))
            type_flag = 0;
        else
            type_flag=1;

        salto=in1.getStringExtra("salto");
        Log.e("salto=>", "" + salto + ",!");
        if(salto==null)
            salto_flag=0;
        else if(salto.equals(""))
            salto_flag=0;
        else
            salto_flag = 1;


        //user=(User_Apply)in1.getSerializableExtra("user_apply");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
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
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId() == android.R.id.home)
        {
            Log.e("selected :","Home");
        }
        return super.onOptionsItemSelected(menuItem);
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
    public void onFetchComplete_jobs_posted(ArrayList<Job_Post_class> j)
    {
        Log.e("Result=>",""+j);
        p.dismiss();
        Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, j, l, fav_list, user_apply);
        //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
        job_list.setAdapter(search_adapter);
        main_list=j;
        job_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (arl.size() > 0)
                {

                }
                else
                {
                    Log.e("position=>", "" + position + ", ");
                    Log.e("job at position=>", "" + main_list.get(position).getJob_title()+ ", ");
                    Intent in1 = new Intent(All_Jobs.this, Job_Activity.class);
                    if(l!=null)
                        if (check_fav(main_list.get(position).getJob_id(), l.getEmail()))
                        {
                            in1.putExtra("fav_flag", 1);
                        }
                        else
                        {
                            in1.putExtra("fav_flag", 0);
                        }

                    job1 = new Job_Post_class();
                    job1 = main_list.get(position);

                    in1.putExtra("loginclass", l);
                    in1.putExtra("job", job1);
                    if(l!=null)
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
                    } else
                        apply_flag = 0;
                    if(l!=null)
                        in1.putExtra("applied_flag", apply_flag);
                    startActivity(in1);
                }
            }
        });
    }

    @Override
    public void onFetchComplete_field(ArrayList<String> al) {

    }

    @Override
    public void onFetchComplete_user_applied(ArrayList<User_applied> j)
    {
        p.dismiss();
        jl=new ArrayList<>();
        j2=new ArrayList<User_applied>();
        j2=j;
        int same_flag=-1;
        for(int count=0;count<j.size();count++)
        {
            String temp=j.get(count).getJob_title();
            if(jl.size()>0)
            {
                for(int count1=0;count1<jl.size();count1++)
                {
                    if(jl.get(count1).equals(temp))
                    {
                        same_flag=0;
                        break;
                    }
                    else
                    {
                        same_flag=1;
                    }
                }
                if(same_flag==1)
                    jl.add(temp);
            }
            else
                jl.add(temp);
        }
        adapter=new MyAppliedAdapter(All_Jobs.this,R.layout.user_applied_item,jl,j,l);
        job_list.setAdapter(adapter);

        setListViewHeightBasedOnChildren(job_list);

    }
    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
        user_apply=data;
        if(applied_jobs_flag==1)
        {
            Log.e("in comp apply","if check");
            p.dismiss();
            ArrayList<Job_Post_class> t=new ArrayList<>();
            for(int count=0;count<user_apply.size();count++)
                t.add(user_apply.get(count).getJ());

            Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, t, l, fav_list, user_apply);
            //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
            job_list.setAdapter(search_adapter);
            search_adapter.notifyDataSetChanged();
//            p_search.dismiss();
            if(t.size()>0)
            {
                main_list=t;
            }
            job_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Log.e("in","clicked item="+position);
                    if (arl.size() > 0)
                    {

                    }
                    else
                    {
                        Log.e("position=>", "" + position + ", ");
                        Log.e("job at position=>", "" + main_list.get(position).getJob_title()+ ", ");
                        Intent in1 = new Intent(All_Jobs.this, Job_Activity.class);
                        if(l!=null)
                            if (check_fav(main_list.get(position).getJob_id(), l.getEmail()))
                            {
                                in1.putExtra("fav_flag", 1);
                            }
                            else
                            {
                                in1.putExtra("fav_flag", 0);
                            }

                        job1 = new Job_Post_class();
                        job1 = main_list.get(position);

                        in1.putExtra("loginclass", l);
                        in1.putExtra("job", job1);
                        if(l!=null)
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
                        } else
                            apply_flag = 0;
                        if(l!=null)
                            in1.putExtra("applied_flag", apply_flag);
                        startActivity(in1);
                    }
                }
            });
        }


    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<Skills> data3, ArrayList<Certificate> data4)
    {
    }

    @Override
    public void onFetchFailure(String msg)
    {
        if(msg.equals("jobs"))
        {
            arl.add("                         No Jobs Posted!");
            ArrayAdapter<String> aadapter = new ArrayAdapter<String>(All_Jobs.this, android.R.layout.simple_list_item_1, arl);
            job_list.setAdapter(aadapter);
            //morebtn.setVisibility(View.INVISIBLE);
            p.dismiss();
        }
        else
        {
            Toast.makeText(All_Jobs.this,""+msg,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result)
    {
        p.dismiss();
        ArrayList<User> list=new ArrayList<>();
        for(int count=0;count<result.size();count++)
        {
            if(field_user.equals(result.get(count).getIndustry()))
            {
                list.add(result.get(count));
            }
        }
        if(list.size()>0)
        {
            MyUserClassAdapter adapter=new MyUserClassAdapter(All_Jobs.this,R.layout.user_applied_item,list,l);
            job_list.setAdapter(adapter);
        }
        else
        {
            ArrayList<String> search_str=new ArrayList<String>();
            search_str.add("No Users to show");
            ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
            job_list.setAdapter(search_adapter);
            search_adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data)
    {
        job1_list=data;
        p.dismiss();

        if(star_flag==1)
        {
            Log.e("in=>", "starred");
            if(fav_list.size()>0)
            {
                for (int count = 0; count < fav_list.size(); count++)
                {
                    for (int count1 = 0; count1 < job1_list.size(); count1++)
                    {
                        if (job1_list.get(count1).getJob_id().equals(fav_list.get(count).getJob_id()))
                        {
                            fav_job_list.add(job1_list.get(count1));
                        }
                    }
                }

                Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, fav_job_list, l,fav_list,user_apply);
                //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                job_list.setAdapter(search_adapter);
                search_adapter.notifyDataSetChanged();

                //p.dismiss();
            }
            else
            {
                ArrayList<String> search_str=new ArrayList<String>();
                search_str.add("No Fav Jobs to show, Add Jobs as Favourite first!");
                ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                job_list.setAdapter(search_adapter);
                search_adapter.notifyDataSetChanged();
                p_search.dismiss();
            }
            if(fav_job_list.size()>0)
                main_list=fav_job_list;
            Log.e("fav list size=>",""+fav_job_list.size());
            Log.e("main list size=>",""+main_list.size());
        }
        else if(company_flag==1)
        {
            Log.e("In Company flag=>", "hello");
            search_list = search_job_company(company);
            if (search_list==null)
            {
                Log.e("In null if=>", "adding no list");
                ArrayList<String> search_str = new ArrayList<String>();
                search_str.add("No Jobs Matches Searched Company");
                ArrayAdapter<String> search_adapter = new ArrayAdapter<String>(All_Jobs.this, android.R.layout.simple_list_item_1, search_str);
                job_list.setAdapter(search_adapter);
                search_adapter.notifyDataSetChanged();
            }
            else
            {
                Log.e("In else=>", "adding list");
                Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                job_list.setAdapter(search_adapter);
                search_adapter.notifyDataSetChanged();

            }
            if(search_list!=null)
                main_list=search_list;
        }
        else if(location_flag==1)
        {
            if(skill_flag==1)
            {
                if(industry_flag==1)
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if all");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_all(industry, location, skill, exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nosalto(industry, location, skill, exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_notype(industry, location, skill, exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nosaltotype(industry, location, skill, exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noexp(industry, location, skill, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noexpsalto(industry, location, skill, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noexptype(industry, location, skill, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no exp salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noexpsaltotype(industry, location, skill);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }
                    }

                }
                else
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no ind");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noind(location, skill, exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no ind salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noindsalto(location, skill, exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no ind type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noindtype(location, skill, exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no ind salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noindsaltotype(location, skill, exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no ind exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noindexp(location, skill, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no ind exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noindexpsalto(location, skill, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no ind exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noindexptype(location, skill, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no ind exp salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noindexpsaltotype(location, skill);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }
                    }

                }
            }
            else
            {
                if(industry_flag==1)
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskill(industry, location, exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no skill salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillsalto(industry, location, exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskilltype(industry, location, exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no skill salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillsaltotype(industry, location, exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillexp(industry, location, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no skill exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillexpsalto(industry, location, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillexptype(industry, location, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no skill exp salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillexpsaltotype(industry, location);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }
                    }

                }
                else
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill ind");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillind(location, exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no skill ind salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillindsalto(location, exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill ind type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillindtype(location, exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no skill ind salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillindsaltotype(location, exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill ind exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillindexp(location, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no skill ind exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillindexpsalto(location, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no skill ind exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillindexptype(location, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","else no skill ind exp salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noskillindexpsaltotype(location);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }
                    }

                }
            }
            if(search_list.size()>0)
                main_list=search_list;
        }
        else
        {
            if(skill_flag==1)
            {
                if(industry_flag==1)
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noloc(industry, skill, exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocsalto(industry, skill, exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_noloctype(industry, skill, exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocsaltotype(industry, skill, exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocexp(industry, skill, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocexpsalto(industry, skill, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocexptype(industry, skill, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc exp salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocexpsaltotype(industry, skill);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }
                    }

                }
                else
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc ind");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocind(skill, exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc ind salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocindsalto(skill, exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc ind type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocindtype(skill, exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc ind salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocindsaltotype(skill, exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc ind exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocindexp(skill, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc ind exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocindexpsalto(skill, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc ind exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocindexptype(skill, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc ind exp salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocindexpsaltotype(skill);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }
                    }

                }
            }
            else
            {
                if(industry_flag==1)
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc skill");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskill(industry, exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc skill salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillsalto(industry, exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if loc skill type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskilltype(industry, exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc skill salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillsaltotype(industry, exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc skill exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillexp(industry, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc skill exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillexpsalto(industry, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc skill exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillexptype(industry, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc skill exp salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillexpsaltotype(industry);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }
                    }

                }
                else
                {
                    if(exp_flag==1)
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc skill ind");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillind(exp, type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc skill ind salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillindsalto(exp, type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if loc skill ind type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillindtype(exp, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc skill ind salto type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillindsaltotype(exp);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }

                        }

                    }
                    else
                    {
                        if(type_flag==1)
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc skill ind exp");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillindexp(type, salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                            else
                            {
                                Log.e("in=>","if no loc skill ind exp salto");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillindexpsalto(type);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }

                            }
                        }
                        else
                        {
                            if(salto_flag==1)
                            {
                                Log.e("in=>","if no loc skill ind exp type");
                                p_search = new ProgressDialog(All_Jobs.this);
                                p_search.show();
                                search_list.clear();

                                search_list= search_job_nolocskillindexptype(salto);

                                if(search_list.size()==0 )
                                {
                                    ArrayList<String> search_str=new ArrayList<String>();
                                    search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
                                    ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                                else
                                {
                                    Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, search_list, l, fav_list, user_apply);
                                    //Toast.makeText(Welcome_User.this,""+list,Toast.LENGTH_LONG).show();
                                    job_list.setAdapter(search_adapter);
                                    search_adapter.notifyDataSetChanged();
                                    p_search.dismiss();
                                }
                            }
                            else
                            {
                                Myjob_PostAdapter search_adapter = new Myjob_PostAdapter(All_Jobs.this, R.layout.jobs_item, job1_list, l, fav_list, user_apply);
                                job_list.setAdapter(search_adapter);
                                search_adapter.notifyDataSetChanged();
                                if(job1_list.size()>0)
                                    main_list=job1_list;
                            }
                        }
                    }
                }
            }
            if(search_list.size()>0)
                main_list=search_list;
        }
        if(main_list.size()<0)
        {
            ArrayList<String> search_str=new ArrayList<String>();
            search_str.add("No Jobs Matches Search key, Kindly Consider Rechecking the Search Keys");
            ArrayAdapter<String> search_adapter =new ArrayAdapter<String>(All_Jobs.this,android.R.layout.simple_list_item_1,search_str);
            job_list.setAdapter(search_adapter);
            search_adapter.notifyDataSetChanged();
            //p.dismiss();
        }
        job_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arl.size() > 0) {

                } else {
                    Log.e("position=>", "" + position + ", ");
                    Log.e("job at position=>", "" + main_list.get(position).getJob_title() + ", ");
                    Intent in1 = new Intent(All_Jobs.this, Job_Activity.class);
                    if (l != null)
                        if (check_fav(main_list.get(position).getJob_id(), l.getEmail())) {
                            in1.putExtra("fav_flag", 1);
                        } else {
                            in1.putExtra("fav_flag", 0);
                        }

                    job1 = new Job_Post_class();
                    job1 = main_list.get(position);

                    in1.putExtra("loginclass", l);
                    in1.putExtra("job", job1);
                    if (l != null)
                        for (int count = 0; count < user_apply.size(); count++) {
                            if (user_apply.get(count).j.getJob_id().equals(job1.getJob_id())) {
                                in1.putExtra("user_apply", user_apply.get(count));
                                break;
                            }
                        }

                    int apply_flag = -1;

                    if (user_apply.size() != 0) {
                        for (int count = 0; count < user_apply.size(); count++) {
                            if (user_apply.get(count).getJ().getJob_id().equals(job1.getJob_id())) {
                                apply_flag = 1;
                                break;
                            } else
                                apply_flag = 0;
                        }
                    } else
                        apply_flag = 0;
                    if (l != null)
                        in1.putExtra("applied_flag", apply_flag);
                    startActivity(in1);
                }
            }
        });

    }

    public ArrayList<Job_Post_class> search_job_all(String field,String loc,String skill,String exp,String type,String sal_to)
    {//search_job_noexp  search_job_noexpsalto  search_job_noexptype  search_job_noexpsaltotype

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);

            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list,skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_company(String company)
    {
        p.dismiss();
        search_list.clear();
        int search_comp_flag=-1;
        for(int count=0;count<job1_list.size(); count++)
        {
            if(job1_list.get(count).c.getCompany_name().equals(company))
            {
                Log.e("searched in if",""+company+","+count);
                search_list.add(job1_list.get(count));
                search_comp_flag=1;
            }
            else
            {
                search_comp_flag=0;
            }
        }
//        p_search.dismiss();
        if(search_comp_flag==1)
            return search_list;
        else
        {
            return null;
        }
    }

    boolean check_skill(String[] skill_l,String s)
    {
        for(int count=0;count<skill_l.length;count++)
            if(skill_l[count].equals(skill))
            {
                return true;
            }

        return false;
    }


    public ArrayList<Job_Post_class> search_job_nosalto(String field,String loc,String skill,String exp,String type)
    {

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_notype(String field,String loc,String skill,String exp,String sal_to)
    {

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nosaltotype(String field,String loc,String skill,String exp)
    {

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noexp(String field,String loc,String skill,String type,String sal_to)
    {//  search_job_noexpsalto  search_job_noexptype  search_job_noexpsaltotype

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noexpsalto(String field,String loc,String skill,String type)
    {//    search_job_noexptype  search_job_noexpsaltotype

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noexptype(String field,String loc,String skill,String sal_to)
    {//

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noexpsaltotype(String field,String loc,String skill)
    {//search_job_noexp  search_job_noexpsalto  search_job_noexptype  search_job_noexpsaltotype

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noind(String location,String  skill,String  exp,String  type,String  sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(location));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(location)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noindsalto(String location,String  skill,String  exp,String  type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(location));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(location)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;

    }


    public ArrayList<Job_Post_class> search_job_noindtype(String location,String skill,String exp,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(location));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(location)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;

    }


    public ArrayList<Job_Post_class> search_job_noindsaltotype(String location,String skill,String exp)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(location));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(location)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noindexp(String location,String skill,String type,String salto)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(location));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(salto));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(location)&&check_skill(skill_list, skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(salto))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noindexpsalto(String location,String skill,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(location));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(location)&&check_skill(skill_list, skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noindexptype(String loc,String skill,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&check_skill(skill_list, skill)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noindexpsaltotype(String loc,String skill)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&check_skill(skill_list,skill))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;

    }


    public ArrayList<Job_Post_class> search_job_noskill(String field, String loc, String exp, String type, String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noskillsalto(String field,String loc,String exp,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskilltype(String field,String loc,String exp,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillsaltotype(String field,String loc,String exp)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillexp(String field,String loc,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noskillexpsalto(String field,String loc,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillexptype(String field,String loc,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

//
    public ArrayList<Job_Post_class> search_job_noskillexpsaltotype(String field,String loc)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillind(String loc,String exp,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillindsalto(String loc,String exp,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillindtype(String loc,String exp,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noskillindsaltotype(String loc,String exp)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillindexp(String loc,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillindexpsalto(String loc,String type)
    {

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noskillindexptype(String loc,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noskillindexpsaltotype(String loc)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_noloc(String field,String skill,String exp,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocsalto(String field,String skill,String exp,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_noloctype(String field,String skill,String exp,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocsaltotype(String field,String skill,String exp)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;

    }

    public ArrayList<Job_Post_class> search_job_nolocexp(String field,String skill,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocexpsalto(String field,String skill,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocexptype(String field,String skill,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list, skill)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocexpsaltotype(String field,String skill)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list,skill))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocind(String skill,String exp,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(check_skill(skill_list,skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocindsalto(String skill,String exp,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocindtype(String skill,String exp,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(check_skill(skill_list, skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocindsaltotype(String skill,String exp)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(check_skill(skill_list,skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocindexp(String skill,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(check_skill(skill_list, skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocindexpsalto(String skill,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(check_skill(skill_list,skill)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocindexptype(String skill,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(check_skill(skill_list,skill)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocindexpsaltotype(String skill)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++)
        {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            if(check_skill(skill_list,skill))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocskill(String field,String exp,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillsalto(String field,String exp,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocskilltype(String field,String exp,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillsaltotype(String field,String exp)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocskillexp(String field,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillexpsalto(String field,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;

    }

    public ArrayList<Job_Post_class> search_job_nolocskillexptype(String field,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillexpsaltotype(String field)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            if(job1_list.get(count).c.getIndustry().equalsIgnoreCase(field))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocskillind(String exp,String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }


    public ArrayList<Job_Post_class> search_job_nolocskillindsalto(String exp,String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillindtype(String exp,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;

    }

    public ArrayList<Job_Post_class> search_job_nolocskillindsaltotype(String exp)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            if(job1_list.get(count).getExperience().equalsIgnoreCase(exp))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillindexp(String type,String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillindexpsalto(String type)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            if(job1_list.get(count).getJob_type().equalsIgnoreCase(type))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }

    public ArrayList<Job_Post_class> search_job_nolocskillindexptype(String sal_to)
    {
        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }




    /*
    * public ArrayList<Job_Post_class> search_job_all(String field,String loc,String skill,String exp,String type,String sal_to)
    {//search_job_noexp  search_job_noexpsalto  search_job_noexptype  search_job_noexpsaltotype

        search_list.clear();
        for(int count=0;count<job1_list.size(); count++) {
            String t;
            t=job1_list.get(count).getSkill_required();
            String skill_list[]=t.split(", ");
            //for(int count1=0;count1<skill_list.length;count1++)
            //    Log.e("skills from split=>",""+skill_list[count1]);
            Log.e("skill=>",""+skill);
            Log.e("original skill in list",""+job1_list.get(count).getSkill_required());
            Log.e("loc check=>",""+job1_list.get(count).getJob_location().equals(loc));
            Log.e("Industry check=>",""+job1_list.get(count).c.getIndustry().equals(field));
            Log.e("skill check=>",""+check_skill(skill_list, skill));
            Log.e("exp check=>",""+job1_list.get(count).getExperience().equals(exp));
            Log.e("type check=>",""+job1_list.get(count).getJob_type().equals(type));
            Log.e("sal to check=>",""+job1_list.get(count).getSalary_to().equals(sal_to));
            if(job1_list.get(count).getJob_location().equalsIgnoreCase(loc)&&job1_list.get(count).c.getIndustry().equalsIgnoreCase(field)&&check_skill(skill_list,skill)&&job1_list.get(count).getExperience().equalsIgnoreCase(exp)&&job1_list.get(count).getJob_type().equalsIgnoreCase(type)&&job1_list.get(count).getSalary_to().equalsIgnoreCase(sal_to))
            {
                Log.e("searched in if",""+skill+","+count);
                search_list.add(job1_list.get(count));
            }
        }
        p_search.dismiss();
        return search_list;
    }*/


}
