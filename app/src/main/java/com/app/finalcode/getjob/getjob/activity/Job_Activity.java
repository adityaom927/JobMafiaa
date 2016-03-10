package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.finalcode.getjob.getjob.R;
import com.app.finalcode.getjob.getjob.classes.Fav_Job;
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
import com.app.finalcode.getjob.getjob.classes.User_Apply;
import com.app.finalcode.getjob.getjob.utility.AsyncTaskClass;
import com.app.finalcode.getjob.getjob.utility.ImageLoader;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class Job_Activity extends AppCompatActivity
{
    TextView job_title,comp_name,job_status,job_exp,job_vacancy,job_location,job_sal,job_posted,job_about,job_ind,job_type,job_skills,job_qual,job_last_date,comp_abt,comp_name1,comp_link,comp_ind,tool_text;
    ImageView comp_logo,job_fav;
    LoginClass l;
    Job_Post_class j;

    LinearLayout job_line3,status_lay;

    User_Apply user=new User_Apply();

    Button apply_btn;
    int fav_flag,flag,apply_flag;
    MaterialDialog mMaterial_Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_activity);

        //job_title,comp_name,job_exp,job_vacancy,job_location,job_sal,job_posted,job_about,job_ind,job_type,job_skills,job_qual,job_last_date,comp_abt,comp_name1,comp_link,comp_ind
        int loader = R.drawable.mcd_logo;
        comp_logo=(ImageView)findViewById(R.id.comp_logo);
        job_fav=(ImageView)findViewById(R.id.job_fav);

        apply_btn=(Button)findViewById(R.id.apply_btn);

        tool_text=(TextView)findViewById(R.id.tool_text);
        job_title=(TextView)findViewById(R.id.job_title);
        comp_name=(TextView)findViewById(R.id.comp_name);
        job_exp=(TextView)findViewById(R.id.job_exp);
        job_vacancy=(TextView)findViewById(R.id.job_vacancy);
        job_location=(TextView)findViewById(R.id.job_location);
        job_sal=(TextView)findViewById(R.id.job_sal);
        job_posted=(TextView)findViewById(R.id.job_posted);
        job_about=(TextView)findViewById(R.id.job_about);
        job_ind=(TextView)findViewById(R.id.job_ind);
        job_type=(TextView)findViewById(R.id.job_type);
        job_skills=(TextView)findViewById(R.id.job_skills);
        job_qual=(TextView)findViewById(R.id.job_qual);
        job_last_date=(TextView)findViewById(R.id.job_last_date);
        comp_abt=(TextView)findViewById(R.id.comp_abt);
        comp_name1=(TextView)findViewById(R.id.comp_name1);
        comp_link=(TextView)findViewById(R.id.comp_link);
        comp_ind=(TextView)findViewById(R.id.comp_ind);
        job_status=(TextView)findViewById(R.id.job_status);

        job_line3=(LinearLayout)findViewById(R.id.job_line3);
        status_lay=(LinearLayout)findViewById(R.id.status_lay);

        Intent in=getIntent();
        l=new LoginClass();

        l=(LoginClass)in.getSerializableExtra("loginclass");
        if(l!=null)
        {
            if(l.getType().equals("company"))
            {
                job_line3.setVisibility(View.INVISIBLE);
                status_lay.setVisibility(View.INVISIBLE);
                apply_btn.setVisibility(View.INVISIBLE);
            }

        }
        else
        {
            job_line3.setVisibility(View.INVISIBLE);
            status_lay.setVisibility(View.INVISIBLE);
        }


        user=(User_Apply)in.getSerializableExtra("user_apply");


        j=(Job_Post_class)in.getSerializableExtra("job");

        Log.e("job type",""+j.getJob_type());
        apply_flag=in.getIntExtra("applied_flag",-1);

        flag=in.getIntExtra("fav_flag",-1);


        job_title.setText(j.getJob_title());
        comp_name.setText(j.c.getCompany_name());
        job_exp.setText(j.getExperience());
        job_vacancy.setText(j.getVacancy());
        job_location.setText(j.getJob_location());
        String sal="INR "+j.getSalary_from()+" - "+j.getSalary_to()+" P.A";
        job_sal.setText(sal);
        job_posted.setText(j.getPosted_date());
        job_about.setText(j.getJob_desc());
        job_ind.setText(j.getCategory());
        job_type.setText(j.getJob_type());
        job_skills.setText(j.getSkill_required());
        job_qual.setText(j.getMin_qual());
        job_last_date.setText(j.getLast_date());
        comp_abt.setText(j.c.getCompany_about());
        comp_name1.setText(j.c.getCompany_name());
        comp_link.setText(j.c.getCompany_link());
        comp_ind.setText(j.c.getIndustry());
        if(apply_flag==1)
            job_status.setText(user.getCurr_status());
        else
            if(l!=null)
                if(l.getType().equals("user"))
                    job_status.setText("Not Applied");
            else
                    job_status.setText("Login First");

        String image_url = "http://jobmafiaa.com//assets/uploads/"+j.c.getCompany_logo();
        ImageLoader imgLoader = new ImageLoader(Job_Activity.this);
        imgLoader.DisplayImage(image_url, loader, comp_logo);
        ArrayList<Fav_Job> fl=new ArrayList<Fav_Job>();
        AsyncTaskClass a=new AsyncTaskClass();

        fl=a.fav_list;

        if(apply_flag==1)
        {
            //apply_logo.setVisibility(View.VISIBLE);
            apply_btn.setText("Applied!");
            apply_btn.setEnabled(false);
        }
        else
        {
            //apply_logo.setVisibility(View.INVISIBLE);
            apply_btn.setEnabled(true);
            apply_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (l == null)
                    {
                        Intent i = new Intent(Job_Activity.this, Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        mMaterial_Dialog = new MaterialDialog(Job_Activity.this)
                                .setTitle("Confirmation Dialog")
                                .setMessage("Are You Sure you want to Apply?")
                                .setPositiveButton("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Apply_job_task task = new Apply_job_task();
                                        task.execute(l.getEmail(), j.getJob_id());

                                        mMaterial_Dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("CANCEL", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterial_Dialog.dismiss();

                                    }
                                });

                        mMaterial_Dialog.show();

                    }
                }
            });

        }
        //Toast.makeText(Job_Activity.this,j.getJob_title()+","+l.getEmail()+","+flag+","+l,Toast.LENGTH_LONG).show();
        if(l==null)
        {
            //Toast.makeText(Job_Activity.this,l.getEmail()+",l=null",Toast.LENGTH_LONG).show();
            job_fav.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(flag==1)
            {
                job_fav.setImageResource(R.drawable.christmasstar26);
                fav_flag =1;
            }
            else
            {
                job_fav.setImageResource(R.drawable.star26);
                fav_flag =0;
            }
            //(fav_comp.getId())
//===========================================================================================================================
            final ArrayList<Fav_Job> finalFl = fl;
            job_fav.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (fav_flag == 0)
                    {
                       // Log.e("my job post adapter", "in set job fav!");
                            Add_Fav_Task fav_task=new Add_Fav_Task();
                            fav_task.execute(j.getJob_id(), l.getEmail());
                            job_fav.setImageResource(R.drawable.christmasstar26);
                            fav_flag = 1;
                    }
                    else
                    {
                        //Log.e("my job post adapter","in reset job fav!");
                            Del_Fav_Task del_fav_task=new Del_Fav_Task();
                            del_fav_task.execute(j.getJob_id(), l.getEmail());
                            job_fav.setImageResource(R.drawable.star26);
                            fav_flag = 0;
                    }
                }
            });
        }

        //Toast.makeText(Job_Activity.this, ""+j.getCompany(),Toast.LENGTH_LONG).show();


        //terms.setText(j.getTerms());


        comp_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(Job_Activity.this,Comapny_Profile.class);
                in.putExtra("loginclass",l);
                in.putExtra("comp_email",j.c.getCompany_email());
                startActivity(in);

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //apply_logo.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    private class Apply_job_task extends AsyncTask<String,Void,String>
    {
        ProgressDialog p=new ProgressDialog(Job_Activity.this);
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            p.show();
            p.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params)
        {
            String result = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/apply");

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_email", params[0]));
            nameValuePairs.add(new BasicNameValuePair("jobid", params[1]));

            //Log.e("doin function for fav:", "for="+email);
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);

                InputStream is = response.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);
                while (true)
                {
                    String str = br.readLine();
                    if (str == null)
                        break;
                    result = result + str;
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            p.dismiss();
            Toast.makeText(Job_Activity.this,"Successfully Applied!!", Toast.LENGTH_LONG).show();
            Log.e("apply result=>", "" + s);
            //job_status.setText("Pending");

            //apply_logo.setVisibility(View.VISIBLE);
            apply_btn.setText("Applied!");
            apply_btn.setEnabled(false);

        }
    }

    class Add_Fav_Task extends AsyncTask<String, Void, String>
    {
        String result="";
        @Override
        protected String doInBackground(String... params)
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/addtofav");
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", params[1]));
            nameValuePairs.add(new BasicNameValuePair("id", params[0]));
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                HttpResponse response = httpclient.execute(httppost);

                InputStream is = response.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);
                while (true)
                {
                    String str = br.readLine();
                    if (str == null)
                        break;
                    result = result + str;
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result)
        {

            try {
                Log.e("fav set result=",""+result);



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


    class Del_Fav_Task extends AsyncTask<String, Void, String>
    {
        String result="";
        @Override
        protected String doInBackground(String... params)
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/deletesaved");
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", params[1]));
            nameValuePairs.add(new BasicNameValuePair("job_id", params[0]));
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                HttpResponse response = httpclient.execute(httppost);

                InputStream is = response.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);
                while (true)
                {
                    String str = br.readLine();
                    if (str == null)
                        break;
                    result = result + str;
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result)
        {

            try {
                Log.e("fav set result=",""+result);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

}
