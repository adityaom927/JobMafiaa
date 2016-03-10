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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.app.finalcode.getjob.getjob.utility.MyUserAdapter;
import com.app.finalcode.getjob.getjob.utility.Myjob_PostAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.drakeet.materialdialog.MaterialDialog;

public class Welcome_Company extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener,FetchDataListener, FetchImageDataListener
{
    int field_flag=-1;
    Context context=this;
    ArrayList<String> location=new ArrayList<String>();//skill
    ArrayList<String> skill=new ArrayList<String>();//
    ArrayList<String> fields=new ArrayList<String>();//
    ArrayList<String> comps=new ArrayList<String>();
    View itemView_select;
    FetchDataListener listen=this;
    LoginClass logged=new LoginClass();
    ProgressDialog p_fetch;
    Spinner search_only_loc;
    MaterialDialog mMaterialDialog_logout,mMaterialDialog_search_loc;

    ProgressDialog p;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    User u=new User();
    Button morebtn;
    ImageView im1,im11;
    ListView company_job_list;
    Job_Post_class job1;
    ArrayList<User> list;
    MyUserAdapter adapter;
    ArrayList<Fav_Job> fav_list=new ArrayList<>();
    ArrayList<Job_Post_class> job1_list=new ArrayList<>();
    ArrayList<User_Apply> user_apply =new ArrayList<>();
    private AutoScrollViewPager viewPager1;
    private List<Integer> imageIdList;

    ArrayList<Adds> adds=new ArrayList<Adds>();
    TextView tool_text;
    ArrayList<Adds> addsImagePathList=new ArrayList<Adds>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_company);

        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        tool_text=(TextView)findViewById(R.id.tool_text);
        viewPager1 = (AutoScrollViewPager)findViewById(R.id.view_pager1);
        company_job_list=(ListView)findViewById(R.id.company_job_list);
        morebtn=(Button)findViewById(R.id.morebtn);

        Intent in =getIntent();
        logged=(LoginClass)in.getSerializableExtra("loginclass");

        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Welcome_Company.this, All_Jobs.class);
                in.putExtra("loginclass", logged);
                startActivity(in);
            }
        });

        company_job_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent in1 = new Intent(Welcome_Company.this, Job_Activity.class);
                if (check_fav(job1_list.get(position).getJob_id(), logged.getEmail()))
                    in1.putExtra("fav_flag", 1);
                else
                    in1.putExtra("fav_flag", 0);

                job1 = new Job_Post_class();
                job1 = job1_list.get(position);
                in1.putExtra("loginclass", logged);
                in1.putExtra("job", job1);
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
                in1.putExtra("applied_flag", apply_flag);
                startActivity(in1);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.comp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.comp_drawer_layout);
        toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.comp_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hideSoftKeyboard();
    }

    public void hideSoftKeyboard()
    {
        if(getCurrentFocus()!=null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
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

    protected void onStart()
    {
        super.onStart();
        p=new ProgressDialog(Welcome_Company.this);
        Intent in =getIntent();
        logged=(LoginClass)in.getSerializableExtra("loginclass");


        p.show();
        p.setCancelable(false);
        p.setMessage("Fetching Required Details..");
        AsyncTaskClass task=new AsyncTaskClass(this);
        AsyncTaskClass task1=new AsyncTaskClass(this);
        task.execute("fav_jobs",logged.getEmail());
        task1.execute("all_jobs", "welcome_company");
        AsyncTaskClass task3=new AsyncTaskClass(this);
        task3.execute("adds");
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.comp_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            mMaterialDialog_logout= new MaterialDialog(Welcome_Company.this)
                    .setTitle("Logout")
                    .setMessage("Are You Sure You Want to Logout??")
                    .setPositiveButton("OK", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent in1 = new Intent(Welcome_Company.this, FullScreenAd.class);
                            startActivity(in1);
                            mMaterialDialog_logout.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mMaterialDialog_logout.dismiss();

                        }
                    });

            mMaterialDialog_logout.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
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


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.comp_drawer_layout);
        menuItem.setChecked(true);
        menuItem.getItemId();
        drawer.closeDrawers();
        if (menuItem.getTitle().equals("New Job Posts"))
        {
            Intent in=new Intent(Welcome_Company.this,Add_Job_Post_Activity.class);
            in.putExtra("loginclass",logged);
            startActivity(in);

        }
        else if (menuItem.getItemId()==R.id.nav_wel_allseeker)
        {
            LayoutInflater inflater = (LayoutInflater) Welcome_Company.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView_select = inflater.inflate(R.layout.search_location, null);

            search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);
            Log.e("showing progress", "p_fetch searchind");
            p_fetch=new ProgressDialog(Welcome_Company.this);
            p_fetch.setMessage("Fetching Required details");
            p_fetch.show();

            field_flag=1;
            AsyncTaskClass ind_task=new AsyncTaskClass(listen);
            ind_task.execute("field");
        }
        else if (menuItem.getItemId()==R.id.nav_wel_allposts)
        {
            Intent in=new Intent(Welcome_Company.this,All_Jobs.class);
            in.putExtra("loginclass",logged);
            startActivity(in);
        }
        else if (menuItem.getItemId()==R.id.nav_wel_logout)
        {
            mMaterialDialog_logout= new MaterialDialog(Welcome_Company.this)
                    .setTitle("Logout").setMessage("Are You Sure You Want to Logout??")
                    .setPositiveButton("OK", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent in = new Intent(Welcome_Company.this, FullScreenAd.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            mMaterialDialog_logout.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog_logout.dismiss();

                        }
                    });

            mMaterialDialog_logout.show();
        }
        else if (menuItem.getItemId()==R.id.nav_wel_companyprofile)
        {
            Intent in=new Intent(Welcome_Company.this,Comapny_Profile.class);
            in.putExtra("loginclass",logged);
            startActivity(in);

        }//nav_wel_myposts
        else if (menuItem.getItemId()==R.id.nav_wel_myposts)
        {
            Intent in=new Intent(Welcome_Company.this,All_Jobs.class);
            in.putExtra("loginclass",logged);
            in.putExtra("my_job_posts",1);
            startActivity(in);

        }//nav_wel_starredjobs
        else if (menuItem.getItemId()==R.id.nav_wel_starredjobs)
        {
            Intent in = new Intent(Welcome_Company.this, All_Jobs.class);
            in.putExtra("loginclass", logged);
            in.putExtra("starred", 1);
            startActivity(in);

        }
        else if (menuItem.getItemId()==R.id.nav_wel_appliers)
        {
            Intent in=new Intent(Welcome_Company.this,All_Jobs.class);
            in.putExtra("loginclass",logged);
            in.putExtra("appliers",1);

            startActivity(in);
        }
        else if (menuItem.getItemId()==R.id.nav_wel_search)
        {
            Intent in = new Intent(Welcome_Company.this, Search_Activity.class);
            in.putExtra("loginclass", logged);
            Log.e("sending=>", "" + logged.getEmail());
            startActivity(in);
        }
        else if (menuItem.getItemId()==R.id.nav_wel_advsearch)
        {
            Intent in = new Intent(Welcome_Company.this, Advance_Search.class);
            in.putExtra("loginclass", logged);
            startActivity(in);
        }
        else if (menuItem.getItemId()==R.id.nav_wel_searchloc)
        {
            LayoutInflater inflater = (LayoutInflater) Welcome_Company.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView_select = inflater.inflate(R.layout.search_location, null);

            search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);
            Log.e("showing progress", "p_fetch searchloc");
            p_fetch=new ProgressDialog(Welcome_Company.this);
            p_fetch.setTitle("Fetching required details");
            p_fetch.show();

            AsyncTaskClass skill_task=new AsyncTaskClass(listen);
            skill_task.execute("Location");
        }
        else if (menuItem.getItemId()==R.id.nav_wel_searchskills)
        {
            LayoutInflater inflater = (LayoutInflater) Welcome_Company.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView_select = inflater.inflate(R.layout.search_location, null);

            search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);

            p_fetch=new ProgressDialog(Welcome_Company.this);
            Log.e("showing progress", "p_fetch search skill");
            p_fetch.show();

            AsyncTaskClass location_task=new AsyncTaskClass(listen);
            location_task.execute("skills");
        }
        else if (menuItem.getItemId()==R.id.nav_wel_searchind)
        {
            LayoutInflater inflater = (LayoutInflater) Welcome_Company.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView_select = inflater.inflate(R.layout.search_location, null);

            search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);
            Log.e("showing progress","p_fetch searchind");
            p_fetch=new ProgressDialog(Welcome_Company.this);
            p_fetch.setMessage("Fetching Required details");
            p_fetch.show();
            field_flag=2;
            AsyncTaskClass ind_task=new AsyncTaskClass(listen);
            ind_task.execute("field");
        }
        else if (menuItem.getItemId()==R.id.nav_wel_search_comp)
        {
            LayoutInflater inflater = (LayoutInflater) Welcome_Company.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView_select = inflater.inflate(R.layout.search_location, null);

            search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);
            Log.e("showing progress", "p_fetch searchcomp");
            p_fetch=new ProgressDialog(Welcome_Company.this);
            p_fetch.setTitle("Fetching Required Details");
            p_fetch.show();

            AsyncTaskClass skill_task=new AsyncTaskClass(listen);
            skill_task.execute("companies");
        }
        else if (menuItem.getItemId()==R.id.na_wel_share)
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Checkout this Awesome App.... =>link to app..";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Application u must try!");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }//
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
                Toast.makeText(Welcome_Company.this, " unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    public void intimate(String msg) {

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
    public void onFetchComplete_skill(ArrayList<String> s)
    {
        skill=s;
        Log.e("dismisses progress","p_fetch complete skill");
        p_fetch.dismiss();
        ArrayAdapter<String> skill_adapter=new ArrayAdapter<String>(Welcome_Company.this,android.R.layout.simple_list_item_1,skill);
        search_only_loc.setAdapter(skill_adapter);
        Log.e("set adpater", "done");
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_Company.this)
                .setTitle("Edit Basic Details")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in=new Intent(Welcome_Company.this,All_Jobs.class);
                        in.putExtra("skill",search_only_loc.getSelectedItem().toString());
                        in.putExtra("loginclass", logged);
                        startActivity(in);
                        mMaterialDialog_search_loc.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog_search_loc.dismiss();
                    }
                });

        mMaterialDialog_search_loc.show();
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

        FatchAddsImage task1 = new FatchAddsImage(this,context);
        task1.execute(adds);
    }

    @Override
    public void onFetchComplete_delete_certi(String s) {

    }

    @Override
    public void onFetchComplete_companies(ArrayList<String> companies)
    {
        comps=companies;
        Log.e("dismisses progress","p_fetch complete companies");
        p_fetch.dismiss();
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome_Company.this,android.R.layout.simple_list_item_1,comps);
        search_only_loc.setAdapter(loc_adapter);
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_Company.this)
                .setTitle("Edit Basic Details")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in=new Intent(Welcome_Company.this,All_Jobs.class);
                        in.putExtra("company",search_only_loc.getSelectedItem().toString());
                        in.putExtra("loginclass", logged);
                        startActivity(in);

                        mMaterialDialog_search_loc.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog_search_loc.dismiss();
                    }
                });
        mMaterialDialog_search_loc.show();
    }

    @Override
    public void onFetchComplete_company_details(Company s) {

    }

    @Override
    public void onFetchComplete_location(ArrayList<String> al)
    {
        location=al;
        Log.e("dismisses progress","p_fetch complete location");
        p_fetch.dismiss();
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome_Company.this,android.R.layout.simple_list_item_1,location);
        search_only_loc.setAdapter(loc_adapter);
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_Company.this)
                .setTitle("Edit Basic Details")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in=new Intent(Welcome_Company.this,All_Jobs.class);
                        in.putExtra("location",search_only_loc.getSelectedItem().toString());
                        in.putExtra("loginclass", logged);
                        startActivity(in);
                        mMaterialDialog_search_loc.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog_search_loc.dismiss();

                    }
                });

        mMaterialDialog_search_loc.show();
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
        Log.e("dismisses progress","p_fetch complete field");
        p_fetch.dismiss();
        fields=al;
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome_Company.this,android.R.layout.simple_list_item_1,fields);
        search_only_loc.setAdapter(loc_adapter);
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_Company.this)
                .setMessage("Select Industry")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Welcome_Company.this, All_Jobs.class);
                        if (field_flag == 2)
                        {
                            in.putExtra("field", search_only_loc.getSelectedItem().toString());
                        }
                        else if(field_flag==1)
                        {
                            in.putExtra("field_users",search_only_loc.getSelectedItem().toString());
                        }

                        in.putExtra("loginclass", logged);
                        startActivity(in);
                        mMaterialDialog_search_loc.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog_search_loc.dismiss();

                    }
                });

        mMaterialDialog_search_loc.show();
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
        user_apply=data;

    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<Skills> data3, ArrayList<Certificate> data4) {

    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data)
    {
        job1_list=data;

        Myjob_PostAdapter adapter = new Myjob_PostAdapter(Welcome_Company.this, R.layout.jobs_item, job1_list, logged,fav_list,user_apply);
        company_job_list.setAdapter(adapter);
        p.dismiss();

    }

    @Override
    public void onFetchFailure(String msg) {

    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }

    @Override
    public void onFetchComplete_add(ArrayList<Adds> addsList)
    {
        addsImagePathList=addsList;

        System.out.println("list " + addsImagePathList.size());

        viewPager1.setAdapter(new ImagePagerAdapter(Welcome_Company.this, addsImagePathList));
        // viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        viewPager1.setInterval(3000);
        viewPager1.startAutoScroll();
        viewPager1.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % addsImagePathList.size());
    }
}
