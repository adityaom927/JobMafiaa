package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
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
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.drakeet.materialdialog.MaterialDialog;

public class Welcome_User extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener,FetchDataListener, FetchImageDataListener
{
    LoginClass logged=new LoginClass();
    View itemView_select;

    FetchImageDataListener listener=this;
    Context context=this;

    int for_u_flag=-1;

    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    ProgressDialog p_search;//=new ProgressDialog(Welcome_User.this);
    ProgressDialog p_start;//=new ProgressDialog(Welcome_User.this);

    TextView tool_text;

    Button morebtn;

    MaterialDialog mMaterialDialog_search_loc,mMaterialDialog_logout1;

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
    ArrayList<Adds> addsImagePathList=new ArrayList<Adds>();

    Spinner search_only_loc;

    MaterialDialog mMaterialDialog_logout;

    private AutoScrollViewPager viewPager;

    private List<Integer> imageIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_user);


        p_search=new ProgressDialog(Welcome_User.this);
        p_start=new ProgressDialog(Welcome_User.this);

        tool_text=(TextView)findViewById(R.id.tool_text);
        AdView adView = (AdView)this.findViewById(R.id.adView);
// Request for Ads
        AdRequest adRequest = new AdRequest.Builder().build();
// Load ads into Banner Ads
        adView.loadAd(adRequest);

        viewPager = (AutoScrollViewPager)findViewById(R.id.view_pager);

        //im1=(ImageView)findViewById(R.id.user_im1);
        joblist=(ListView)findViewById(R.id.user_user_list1);
        morebtn=(Button)findViewById(R.id.morebtn);
        //morebtn=(TextView)findViewById(R.id.morebtn);

        Intent in =getIntent();
        logged=(LoginClass)in.getSerializableExtra("loginclass");

        joblist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arl.size() > 0) {

                } else {
                    Log.e("position=>", "" + position + ", ");
//                        Log.e("id at position=>",""+search_list.get(position).getJob_id());
                    Intent in1 = new Intent(Welcome_User.this, Job_Activity.class);
                    if (check_fav(job1_list.get(position).getJob_id(), logged.getEmail())) {
                        in1.putExtra("fav_flag", 1);
                    } else {
                        in1.putExtra("fav_flag", 0);
                    }

                    job1 = new Job_Post_class();
                    job1 = job1_list.get(position);

                    in1.putExtra("loginclass", logged);
                    in1.putExtra("job", job1);
                    //Log.e("user_apply=>",""+user_apply.size()+", ");
                    for (int count = 0; count < user_apply.size(); count++) {
                        if (user_apply.get(count).j.getJob_id().equals(job1.getJob_id())) {
                            in1.putExtra("user_apply", user_apply.get(count));
                            break;
                        }

                        //}
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

                    in1.putExtra("applied_flag", apply_flag);
                    startActivity(in1);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.user_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                menuItem.getItemId();
                drawer.closeDrawers();
                //Toast.makeText(Welcome_User.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                if (menuItem.getItemId() == R.id.nav_wel_logout)
                {
                    mMaterialDialog_logout= new MaterialDialog(Welcome_User.this)
                            .setTitle("Logout").setMessage("Are You Sure You Want to Logout??")
                            .setPositiveButton("OK", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    Intent in = new Intent(Welcome_User.this, FullScreenAd.class);
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

                }//nav_wel_appliers
                else if(menuItem.getItemId()==R.id.nav_wel_sugested)
                {
                    for_u_flag=1;
                    p_search.show();

                    AsyncTaskClass profile_Task=new AsyncTaskClass(listen);
                    profile_Task.execute("user_details", logged.getEmail());
                }
                else if(menuItem.getItemId()==R.id.nav_wel_jobsmatched)
                {
                    p_search.show();
                    for_u_flag=2;
                    Log.e("showing progress", "p_for_u flag 2");

                    AsyncTaskClass profile_Task=new AsyncTaskClass(listen);
                    profile_Task.execute("user_details", logged.getEmail());
                }
                else if (menuItem.getItemId() == R.id.nav_wel_userprofile)
                {
                    Intent in = new Intent(Welcome_User.this, User_Profile.class);
                    in.putExtra("loginclass", logged);
                    startActivity(in);
                }
                else if (menuItem.getItemId() == R.id.nav_wel_alljobs)
                {
                    Intent in = new Intent(Welcome_User.this, All_Jobs.class);
                    in.putExtra("loginclass", logged);
                    startActivity(in);

                }
                else if (menuItem.getItemId() == R.id.nav_wel_fav)
                {
                    Intent in = new Intent(Welcome_User.this, All_Jobs.class);
                    in.putExtra("loginclass", logged);
                    in.putExtra("starred", 1);
                    startActivity(in);

                }
                else if (menuItem.getItemId() == R.id.nav_wel_search)
                {
                    Intent in = new Intent(Welcome_User.this, Search_Activity.class);
                    in.putExtra("loginclass", logged);
                    Log.e("sending=>", "" + logged.getEmail());
                    startActivity(in);

                }
                else if (menuItem.getItemId() == R.id.nav_wel_advsearch)
                {
                    Intent in = new Intent(Welcome_User.this, Advance_Search.class);
                    in.putExtra("loginclass", logged);
                    startActivity(in);

                }//nav_wel_search_comp
                else if (menuItem.getItemId() == R.id.nav_wel_searchloc)
                {
                    p_search.show();
                    p_search.setCancelable(true);
                    LayoutInflater inflater = (LayoutInflater) Welcome_User.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    itemView_select = inflater.inflate(R.layout.search_location, null);

                    search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);
                    Log.e("showing progress", "p_fetch searchloc");

                    AsyncTaskClass skill_task=new AsyncTaskClass(listen);
                    skill_task.execute("Location");
                }//nav_wel_search_comp
                else if (menuItem.getItemId() == R.id.nav_wel_searchskills)
                {
                    LayoutInflater inflater = (LayoutInflater) Welcome_User.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    itemView_select = inflater.inflate(R.layout.search_location, null);

                    search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);

                    p_search.show();
                    p_search.setCancelable(true);
                    AsyncTaskClass location_task=new AsyncTaskClass(listen);
                    location_task.execute("skills");
                }
                else if (menuItem.getItemId() == R.id.nav_wel_searchind)
                {
                    LayoutInflater inflater = (LayoutInflater) Welcome_User.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    itemView_select = inflater.inflate(R.layout.search_location, null);

                    search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);
                    Log.e("showing progress", "p_fetch searchind");

                    p_search.show();
                    p_search.setCancelable(true);
                    AsyncTaskClass ind_task=new AsyncTaskClass(listen);
                    ind_task.execute("Field");
                }
                else if (menuItem.getItemId() == R.id.nav_wel_search_comp)
                {
                    LayoutInflater inflater = (LayoutInflater) Welcome_User.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    itemView_select = inflater.inflate(R.layout.search_location, null);

                    search_only_loc=(Spinner)itemView_select.findViewById(R.id.search_only_loc);
                    Log.e("showing progress", "p_fetch searchcomp");

                    p_search.show();
                    p_search.setCancelable(true);
                    AsyncTaskClass skill_task=new AsyncTaskClass(listen);
                    skill_task.execute("companies");
                }//nav_wel_applied_jobs
                else if (menuItem.getItemId() == R.id.nav_wel_applied_jobs)
                {
                    Intent in=new Intent(Welcome_User.this,All_Jobs.class);
                    in.putExtra("applied_jobs",1);
                    in.putExtra("loginclass",logged);
                    startActivity(in);

                }//
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
                        Toast.makeText(Welcome_User.this, " unable to find market app", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });


        morebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(Welcome_User.this, All_Jobs.class);
                in.putExtra("loginclass", logged);
                startActivity(in);

            }
        });

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
        if(fav_list!=null)
        {
            for(int count=0;count<fav_list.size();count++ )
            {
                if(fav_list.get(count).getJob_id().equals(jid))
                {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        p_start.show();
        p_start.setCancelable(false);
        p_start.setMessage("Fetching Required Details..");
        AsyncTaskClass task=new AsyncTaskClass(this);
        AsyncTaskClass task2=new AsyncTaskClass(this);
        task2.execute("job_applied", logged.getEmail());
        task.execute("fav_jobs", logged.getEmail());
        AsyncTaskClass task1=new AsyncTaskClass(this);
        task1.execute("all_jobs", "welcome_user");
        AsyncTaskClass task3=new AsyncTaskClass(this);
        task3.execute("adds");
    }



    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            mMaterialDialog_logout1= new MaterialDialog(Welcome_User.this)
                    .setTitle("Logout").setMessage("Are You Sure You Want to Logout??")
                    .setPositiveButton("OK", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent in = new Intent(Welcome_User.this, FullScreenAd.class);
                            startActivity(in);
                            mMaterialDialog_logout1.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mMaterialDialog_logout1.dismiss();

                        }
                    });

            mMaterialDialog_logout1.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFetchComplete_resume_check(int result1) {

    }

    @Override
    public void onFetchComplete_fav(ArrayList<Fav_Job> data)
    {
        //Log.e("Got fav list!!!",""+data);
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
        Log.e("dismisses progress", "p_fetch complete skill");

        p_search.dismiss();

        ArrayAdapter<String> skill_adapter=new ArrayAdapter<String>(Welcome_User.this,android.R.layout.simple_list_item_1,skill);
        search_only_loc.setAdapter(skill_adapter);

        Log.e("set adpater", "done");
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_User.this)
                .setTitle("Edit Basic Details")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in=new Intent(Welcome_User.this,All_Jobs.class);
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
        FatchAddsImage task1 = new FatchAddsImage(listener,context);
        task1.execute(adds);


    }

    @Override
    public void onFetchComplete_delete_certi(String s)
    {

    }

    @Override
    public void onFetchComplete_companies(ArrayList<String> companies)
    {
        comps=companies;
        Log.e("dismisses progress", "p_fetch complete companies");
        p_search.dismiss();
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome_User.this,android.R.layout.simple_list_item_1,comps);
        search_only_loc.setAdapter(loc_adapter);
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_User.this)
                .setTitle("Edit Basic Details")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in=new Intent(Welcome_User.this,All_Jobs.class);
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
        Log.e("dismisses progress", "p_fetch complete location");
        p_search.dismiss();
        Collections.sort(location, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome_User.this,android.R.layout.simple_list_item_1,location);
        search_only_loc.setAdapter(loc_adapter);
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_User.this)
                .setTitle("Search By Location")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in=new Intent(Welcome_User.this,All_Jobs.class);
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
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(Welcome_User.this,android.R.layout.simple_list_item_1, fields);
        search_only_loc.setAdapter(loc_adapter);
        mMaterialDialog_search_loc= new MaterialDialog(Welcome_User.this)
                .setTitle("Edit Basic Details")
                .setView(itemView_select)
                .setPositiveButton("OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in=new Intent(Welcome_User.this,All_Jobs.class);
                        in.putExtra("field",search_only_loc.getSelectedItem().toString());
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
    public void onFetchComplete_job(ArrayList<Job_Post_class> data)
    {
        //Log.e("Got job list!!!",""+data);
        Log.e("dismisses progress", "p complete job");
        p_start.dismiss();
        job1_list=data;
        Myjob_PostAdapter adapter = new Myjob_PostAdapter(Welcome_User.this, R.layout.jobs_item, job1_list, logged,fav_list,user_apply);
        joblist.setAdapter(adapter);



    }
    @Override
    public void onFetchComplete_apply(ArrayList<User_Apply> data)
    {
        user_apply=data;


    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<Skills> data3, ArrayList<Certificate> data4)
    {
        p_search.dismiss();
        Intent in = new Intent(Welcome_User.this, Jobs_for_u.class);
        in.putExtra("loginclass", logged);
        if(for_u_flag==1)
            in.putExtra("flag","1");
        else if(for_u_flag==2)
            in.putExtra("flag","2");
        in.putExtra("user_pref_field",""+data.get(0).getPref_field());
        in.putExtra("user_pref_loc",""+data.get(0).getPref_location());
        startActivity(in);

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
            ArrayAdapter<String> aadapter = new ArrayAdapter<String>(Welcome_User.this, android.R.layout.simple_list_item_1, arl);
            joblist.setAdapter(aadapter);
            morebtn.setVisibility(View.INVISIBLE);

        }
        if(msg.equals("fav"))
            fav_list=null;
        if(msg.equals("apply"))
            user_apply=null;

    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }

    @Override
    public void onFetchComplete_add(ArrayList<Adds> addsList) {

        addsImagePathList=addsList;

        System.out.println("list " + addsImagePathList.size());


        viewPager.setAdapter(new ImagePagerAdapter(Welcome_User.this, addsImagePathList));
        // viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(3000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % addsImagePathList.size());


    }

}
