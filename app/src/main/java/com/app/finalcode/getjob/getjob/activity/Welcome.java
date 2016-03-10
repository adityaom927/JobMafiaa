package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.app.finalcode.getjob.getjob.classes.FetchImageDataListener;
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
import com.app.finalcode.getjob.getjob.classes.Skills;
import com.app.finalcode.getjob.getjob.classes.User;
import com.app.finalcode.getjob.getjob.classes.User_Apply;
import com.app.finalcode.getjob.getjob.classes.User_applied;
import com.app.finalcode.getjob.getjob.utility.AsyncTaskClass;
import com.app.finalcode.getjob.getjob.utility.FatchAddsImage;
import com.app.finalcode.getjob.getjob.utility.ImagePagerAdapter;
import com.app.finalcode.getjob.getjob.utility.Myjob_PostAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class Welcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,FetchDataListener,FetchImageDataListener
{

    LoginClass l=new LoginClass();

    FetchImageDataListener listener=this;
    Context context=this;

    ProgressDialog p_search;
    ProgressDialog p_start;


    Button morebtn;

    TextView tool_text;

    ListView joblist;

    FetchDataListener listen=this;

    android.support.v7.app.ActionBarDrawerToggle toggle;

    Job_Post_class job1;

    ArrayList<String> arl = new ArrayList<>();
    ArrayList<String> fields=new ArrayList<String>();
    ArrayList<String> comps=new ArrayList<String>();
    ArrayList<String> skill=new ArrayList<String>();
    ArrayList<Fav_Job> fav_list=new ArrayList<>();
    ArrayList<String> location=new ArrayList<>();
    ArrayList<Job_Post_class> job1_list=new ArrayList<>();
    ArrayList<User_Apply> user_apply =new ArrayList<>();
    ArrayList<Adds> adds=new ArrayList<Adds>();

    Spinner search_only_loc;

    private AutoScrollViewPager viewPager;

@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        p_search=new ProgressDialog(Welcome.this);
        p_start=new ProgressDialog(Welcome.this);

        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        tool_text=(TextView)findViewById(R.id.tool_text);
        viewPager = (AutoScrollViewPager)findViewById(R.id.view_pager);

        joblist=(ListView)findViewById(R.id.list1);
        morebtn=(Button)findViewById(R.id.morebtn);

        Intent in =getIntent();
        l=(LoginClass)in.getSerializableExtra("loginclass");

        joblist.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
                    Intent in1 = new Intent(Welcome.this, Job_Activity.class);
                    job1 = new Job_Post_class();
                    job1 = job1_list.get(position);

                    in1.putExtra("loginclass", l);
                    in1.putExtra("job", job1);

                    startActivity(in1);
                }
            }
        });

        morebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(Welcome.this, All_Jobs.class);
                in.putExtra("loginclass", l);
                startActivity(in);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                menuItem.setChecked(true);
                menuItem.getItemId();
                drawer.closeDrawers();
                if(menuItem.getItemId()==R.id.nav_wel_alljobs)
                {
                    Intent in=new Intent(Welcome.this,All_Jobs.class);
                    in.putExtra("loginclass",l);
                    startActivity(in);
                }
                else if(menuItem.getItemId()==R.id.nav_wel_reg)
                {
                    Intent in=new Intent(Welcome.this,Registration_User.class);
                    startActivity(in);
                    finish();
                }
                else if(menuItem.getItemId()==R.id.nav_wel_login)
                {
                    Intent in=new Intent(Welcome.this,Login.class);
                    startActivity(in);
                    finish();
                }
                else if(menuItem.getItemId()==R.id.nav_wel_search)
                {
                    Intent in=new Intent(Welcome.this,Search_Activity.class);
                    in.putExtra("loginclass",l);
                    startActivity(in);
                }
                else if (menuItem.getItemId() == R.id.na_wel_share)
                {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Checkout this Awesome App.... =>link to app..";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Application u must try!");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
                else if (menuItem.getItemId()==R.id.na_wel_rate)
                {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try
                    {
                        startActivity(myAppLinkToMarket);
                    }
                    catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(Welcome.this, " unable to find market app", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
            Intent in = new Intent(Welcome.this, FullScreenAd.class);
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        p_start.show();
        p_start.setCancelable(false);
        p_start.setMessage("Fetching Required Details..");
        AsyncTaskClass task1=new AsyncTaskClass(this);
        task1.execute("all_jobs", "welcome_user");
        AsyncTaskClass task3=new AsyncTaskClass(this);
        task3.execute("adds");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFetchComplete_resume_check(int result1)
    {

    }

    @Override
    public void onFetchComplete_fav(ArrayList<Fav_Job> data)
    {

    }

    @Override
    public void onFetchComplete_profile_edit(String s) {

    }

    @Override
    public void onFetchComplete_skill_add(String s) {

    }

    @Override
    public void onFetchComplete_skill(ArrayList<String> s)
    {
        skill=s;
        Log.e("dismisses progress", "p_fetch complete skill");

        p_search.dismiss();

        ArrayAdapter<String> skill_adapter=new ArrayAdapter<String>(Welcome.this,android.R.layout.simple_list_item_1,skill);
        search_only_loc.setAdapter(skill_adapter);

        Log.e("set adpater","done");
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
    public void onFetchComplete_adds(ArrayList<Adds> ads)
    {
        adds=ads;
        FatchAddsImage task1 = new FatchAddsImage(listener,context);
        task1.execute(adds);
    }

    @Override
    public void onFetchComplete_delete_certi(String s) {

    }

    @Override
    public void onFetchComplete_companies(ArrayList<String> companies)
    {
        comps=companies;
        Log.e("dismisses progress", "p_fetch complete companies");
        p_search.dismiss();
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome.this,android.R.layout.simple_list_item_1,comps);
        search_only_loc.setAdapter(loc_adapter);
    }

    @Override
    public void onFetchComplete_company_details(Company c) {

    }

    @Override
    public void onFetchComplete_location(ArrayList<String> al)
    {
        location=al;
        Log.e("dismisses progress", "p_fetch complete location");
        p_search.dismiss();
        Collections.sort(location, new Comparator<String>()
        {
            @Override
            public int compare(String s1, String s2)
            {
                return s1.compareToIgnoreCase(s2);
            }
        });

        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome.this,android.R.layout.simple_list_item_1,location);
        search_only_loc.setAdapter(loc_adapter);
    }

    @Override
    public void onFetchComplete_add_post(String s) {

    }

    @Override
    public void onFetchComplete_jobs_posted(ArrayList<Job_Post_class> j) {

    }

    @Override
    public void onFetchComplete_field(ArrayList<String> al)
    {
        Log.e("dismisses progress", "p_fetch complete field");

        fields=al;
        p_search.dismiss();
        Collections.sort(fields, new Comparator<String>()
        {
            @Override
            public int compare(String s1, String s2)
            {
                return s1.compareToIgnoreCase(s2);
            }
        });
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome.this,android.R.layout.simple_list_item_1, fields);
        search_only_loc.setAdapter(loc_adapter);
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

    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<Skills> data3, ArrayList<Certificate> data4) {

    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data)
    {
        Log.e("dismisses progress", "p complete job");
        p_start.dismiss();
        job1_list=data;
        Myjob_PostAdapter adapter = new Myjob_PostAdapter(Welcome.this, R.layout.jobs_item, job1_list, l,fav_list,user_apply);
        joblist.setAdapter(adapter);
    }

    @Override
    public void onFetchFailure(String msg)
    {
        p_start.dismiss();
        p_search.dismiss();
        if(msg.equals("jobs"))
        {
            Log.e("dismisses progress", "p complete failure");

            arl.add("                         No Jobs Posted!");
            ArrayAdapter<String> aadapter = new ArrayAdapter<String>(Welcome.this, android.R.layout.simple_list_item_1, arl);
            joblist.setAdapter(aadapter);
            morebtn.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }

    @Override
    public void onFetchComplete_add(ArrayList<Adds> addsImagePathList)
    {
        System.out.println("list " + addsImagePathList.size());


        viewPager.setAdapter(new ImagePagerAdapter(Welcome.this, addsImagePathList));
        // viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(3000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % addsImagePathList.size());
    }
}