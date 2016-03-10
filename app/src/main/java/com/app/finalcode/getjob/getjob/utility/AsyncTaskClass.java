package com.app.finalcode.getjob.getjob.utility;

import android.os.AsyncTask;
import android.util.Log;

import com.app.finalcode.getjob.getjob.classes.Adds;
import com.app.finalcode.getjob.getjob.classes.Certificate;
import com.app.finalcode.getjob.getjob.classes.Company;
import com.app.finalcode.getjob.getjob.classes.Education;
import com.app.finalcode.getjob.getjob.classes.Experience;
import com.app.finalcode.getjob.getjob.classes.Fav_Job;
import com.app.finalcode.getjob.getjob.classes.FetchDataListener;
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.Skills;
import com.app.finalcode.getjob.getjob.classes.User;
import com.app.finalcode.getjob.getjob.classes.User_Apply;
import com.app.finalcode.getjob.getjob.classes.User_applied;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 5 on 16-Jan-16.
 */
public class AsyncTaskClass extends AsyncTask<String, Void, String>
{
    /*
    * */
    public ArrayList<Job_Post_class> job_list = new ArrayList<Job_Post_class>();
    public ArrayList<Adds> adds = new ArrayList<Adds>();
    public ArrayList<Fav_Job> fav_list = new ArrayList<Fav_Job>();
    public ArrayList<User_Apply> job_applied = new ArrayList<User_Apply>();
    public ArrayList<User> user= new ArrayList<User>();
    public ArrayList<Education> education= new ArrayList<Education>();
    public ArrayList<Experience> experience= new ArrayList<Experience>();
    public ArrayList<Skills> skills= new ArrayList<Skills>();
    public ArrayList<String> skills_str= new ArrayList<String>();
    public ArrayList<Certificate> certificate= new ArrayList<Certificate>();
    public ArrayList<String> location= new ArrayList<String>();//
    public ArrayList<String> companies= new ArrayList<String>();

    int flag=-1,list_flag=0;
    public FetchDataListener listener;

    public AsyncTaskClass(FetchDataListener listener)
    {
            this.listener = listener;

    }
    public AsyncTaskClass()
    {

    }


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)
    {
        String result = "";
        if(params[0].equals("all_jobs"))
        {
            if(params.length==2)
                if(params[1].equals("welcome_user")||params[1].equals("welcome_company"))
                    list_flag=1;
            result=all_jobs();
            flag=1;
        }
        else if(params[0].equals("fav_jobs"))
        {
            result=fav_jobs(params[1]);
            flag=2;
        }
        else if(params[0].equals("job_applied"))
        {
            result=job_applied(params[1]);
            flag=3;
        }
        else if(params[0].equals("user_details"))
        {
            result=user(params[1]);
            flag=4;
        }
        else if(params[0].equals("edit_profile"))
        {
            result=edit_profile(params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11],params[12]);
            flag=5;
        }
        else if(params[0].equals("edit_edu"))
        {
            result=edit_edu(params[1], params[2], params[3], params[4], params[5], params[6]);
            flag=6;
        }
        else if(params[0].equals("delete_edu"))
        {
            result=delete_edu(params[1], params[2]);
            flag=7;
        }
        else if(params[0].equals("add_edu"))
        {
            result=add_edu(params[1], params[2], params[3], params[4],params[5]);
            flag=8;
        }
        else if(params[0].equals("add_skill"))
        {
            result=add_skill(params[1], params[2]);
            flag=9;
        }
        else if(params[0].equals("delete_skill"))
        {
            result=delete_skill(params[1]);
            flag=10;
        }
        else if(params[0].equals("company_details"))
        {
            result=company_details(params[1]);
            flag=11;
        }
        else if(params[0].equals("company_job_posts"))
        {
            result=company_job_posts(params[1]);
            flag=12;
        }
        else if(params[0].equals("user_applied"))
        {
            result=user_applied(params[1]);
            flag=13;
        }
        else if(params[0].equals("Location"))
        {
            result=locations();
            flag=14;
        }
        else if(params[0].equals("field"))
        {
            result=fields();
            flag=15;
        }
        else if(params[0].equals("skills"))
        {
            result=skills();
            flag=16;
        }//
        else if(params[0].equals("edit_exp"))
        {
            result=edit_exp(params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9],params[10],params[11],params[12]);
            flag=17;
        }//edit_work
        else if(params[0].equals("edit_work"))
        {
            result=edit_work(params[1], params[2], params[3]);
            flag=18;
        }
        else if(params[0].equals("companies"))
        {
            result=companies();
            flag=19;
        }
        else if(params[0].equals("delete_exp"))
        {
            result=delete_exp(params[1],params[2]);
            flag=20;
        }//
        else if(params[0].equals("add_exp"))
        {
            result=add_exp(params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11]);
            flag=21;
        }
        else if(params[0].equals("adds"))
        {
            result=ads();
            flag=22;
        }
        else if(params[0].equals("social_login"))
        {
            result=ads();
            flag=22;
        }
        else if(params[0].equals("delete_certi"))
        {
            result=delete_certi(params[1]);
            flag=23;
        }
        else if(params[0].equals("add_job_post"))
        {
            result=add_job_post(params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11],params[12],params[13]);
            flag=24;
        }//
        else if(params[0].equals("all_seekers"))
        {
            result=all_seekers();
            flag=25;
        }
        else if(params[0].equals("resume_check"))
        {
            result=resume_check(params[1]);
            flag=26;
        }
        else if(params[0].equals("resume_count_update"))
        {
            result=resume_count_update(params[1], params[2]);
        }
        return result;
    }

    public String resume_count_update(String email,String user_email)
    {
        String result="";
        Log.e("email=>",""+email);
        Log.e("useremail=>",""+user_email);
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/resume_count");

            Log.e("resume update in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("useremail", user_email));

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
            Log.e("resume update do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }


    public String resume_check(String email)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/count_download");

            Log.e("count download in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("email", email));

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
            Log.e("count download do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }



    public String all_seekers()
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/all_jobseekers");

            Log.e("all seekers in=>", "do in");

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
            Log.e("all seekers do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String add_job_post(String email ,String user_type ,String title ,String location ,String skill ,String category ,String vacancy ,String exp ,String job_desc ,String last_date ,String mini_qualification ,String salary_to ,String  salary_from)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/newjobpost");

            Log.e("delete expin=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("user_type", user_type));
            nameValuePairs.add(new BasicNameValuePair("title", title));
            nameValuePairs.add(new BasicNameValuePair("location", location));
            nameValuePairs.add(new BasicNameValuePair("skill", skill));
            nameValuePairs.add(new BasicNameValuePair("category", category));
            nameValuePairs.add(new BasicNameValuePair("vacancy", vacancy));
            nameValuePairs.add(new BasicNameValuePair("exp", exp));
            nameValuePairs.add(new BasicNameValuePair("job_desc", job_desc));
            nameValuePairs.add(new BasicNameValuePair("last_date", last_date));
            nameValuePairs.add(new BasicNameValuePair("mini_qualification", mini_qualification));
            nameValuePairs.add(new BasicNameValuePair("salary_to", salary_to));
            nameValuePairs.add(new BasicNameValuePair("salary_from", salary_from));

            //email , user_type , title , location , skill , category , vacancy , exp , job_desc , last_date , mini_qualification , salary_to ,  salary_from

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
            Log.e("delete exp do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }



    public String delete_certi(String cert_id)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/delete_certificate");

            Log.e("delete expin=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("cer_id", cert_id));

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
            Log.e("delete exp do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }



    public String ads()
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/getads");

            Log.e("ads details in=>","do in");
            // Add your data
            //user_email, class, year, percent, board

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
            Log.e("ads do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }


                                            //edit_exp_company,edit_exp_post,edit_exp_month,edit_exp_from_month,edit_exp_from_year,edit_exp_to_month,edit_exp_to_year,edit_exp_ind,edit_exp_year,
    public String add_exp(String user_email,String company,String post,String exp_month,String exp_from_month,String exp_from_year,String exp_to_month,String exp_to_year,String industry,String exp_year,String current_string)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/add_experience");

            Log.e("add exp in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("user_email", user_email));
            nameValuePairs.add(new BasicNameValuePair("company", company));
            nameValuePairs.add(new BasicNameValuePair("post", post));
            nameValuePairs.add(new BasicNameValuePair("industry", industry));
            nameValuePairs.add(new BasicNameValuePair("exp_year", exp_year));
            nameValuePairs.add(new BasicNameValuePair("exp_to_year", exp_to_year));
            nameValuePairs.add(new BasicNameValuePair("exp_month", exp_month));
            nameValuePairs.add(new BasicNameValuePair("exp_from_month", exp_from_month));
            nameValuePairs.add(new BasicNameValuePair("exp_from_year", exp_from_year));
            nameValuePairs.add(new BasicNameValuePair("exp_to_month", exp_to_month));
            nameValuePairs.add(new BasicNameValuePair("curr_work", current_string));

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
            Log.e("add exp do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String delete_exp(String user_email,String exp_id)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/delete_experience");

            Log.e("delete expin=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_email", user_email));
            nameValuePairs.add(new BasicNameValuePair("exp_id", exp_id));

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
            Log.e("delete exp do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }




    public String companies()
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/allcompanylist");

            Log.e("companies details in=>","do in");
            // Add your data
            //user_email, class, year, percent, board

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
            Log.e("companies do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }



    public String edit_work(String email,String loc,String field)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/add_pref");

            Log.e("edit work details in=>","do in");
            // Add your data
            //email,exp_id,company,post,exp_month,exp_frm_month,exp_from_year,exp_to_month,exp_to_year,ind,exp_year
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("user_email", email));
            nameValuePairs.add(new BasicNameValuePair("pref_location", loc));
            nameValuePairs.add(new BasicNameValuePair("pref_field", field));


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
            Log.e("edit work do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String edit_exp(String email,String exp_id,String company,String post,String exp_month,String exp_frm_month,String exp_from_year,String exp_to_month,String exp_to_year,String ind,String exp_year,String current_string)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/update_experience");

            Log.e("edit exp details in=>","do in");
            // Add your data
            //email,exp_id,company,post,exp_month,exp_frm_month,exp_from_year,exp_to_month,exp_to_year,ind,exp_year
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(11);
            nameValuePairs.add(new BasicNameValuePair("user_email", email));
            nameValuePairs.add(new BasicNameValuePair("id", exp_id));
            nameValuePairs.add(new BasicNameValuePair("company", company));
            nameValuePairs.add(new BasicNameValuePair("post", post));
            nameValuePairs.add(new BasicNameValuePair("exp_month", exp_month));
            nameValuePairs.add(new BasicNameValuePair("exp_from_month", exp_frm_month));
            nameValuePairs.add(new BasicNameValuePair("exp_from_year", exp_from_year));
            nameValuePairs.add(new BasicNameValuePair("exp_to_month", exp_to_month));
            nameValuePairs.add(new BasicNameValuePair("exp_to_year", exp_to_year));
            nameValuePairs.add(new BasicNameValuePair("industry", ind));
            nameValuePairs.add(new BasicNameValuePair("exp_year", exp_year));
            nameValuePairs.add(new BasicNameValuePair("curr_work", current_string));


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
            Log.e("exp edit do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }



    public String skills()
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/allskills");

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
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }

        return result;
    }



    public String fields()
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/allfield");

            Log.e("field details in=>","do in");
            // Add your data
            //user_email, class, year, percent, board

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
            Log.e("field do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }


    public String locations()
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/all_location");

            Log.e("location details in=>","do in");
            // Add your data
            //user_email, class, year, percent, board

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
            Log.e("location do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }



    public String user_applied(String email)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/all_applier");

            Log.e("user app details in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair("user_email", email));

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
            Log.e("user app do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String company_job_posts(String email)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/job_posts");

            Log.e("job posts details in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user_email", email));

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
            Log.e("jobposts do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }


    public String company_details(String email)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/company_detail");

            Log.e("comapany details in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user_email", email));

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
            Log.e("delete skill do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }


    public String delete_skill(String skill_id)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/delete_myskill");

            Log.e("delete skill in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("skill_id", skill_id));

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
            Log.e("delete skill do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String add_skill(String user_email,String add_skill_name)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/add_skill");

            Log.e("add skill in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_email", user_email));
            nameValuePairs.add(new BasicNameValuePair("skill_name", add_skill_name));

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
            Log.e("add skill do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String add_edu(String user_email,String add_edu_quali,String add_edu_board,String add_edu_passyr,String add_edu_perc)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/add_education");

            Log.e("delete edu in=>","do in");
            // Add your data
            //user_email, class, year, percent, board
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("user_email", user_email));
            nameValuePairs.add(new BasicNameValuePair("class", add_edu_quali));
            nameValuePairs.add(new BasicNameValuePair("board", add_edu_board));
            nameValuePairs.add(new BasicNameValuePair("year", add_edu_passyr));
            nameValuePairs.add(new BasicNameValuePair("percent", add_edu_perc));

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
            Log.e("add edu do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String delete_edu(String user_email,String edu_id)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/delete_education");

            Log.e("delete edu in=>","do in");
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_email", user_email));
            nameValuePairs.add(new BasicNameValuePair("edu_id", edu_id));

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
            Log.e("delete edu do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }


    public String edit_edu(String user_email,String edu_id,String edit_class,String edit_board,String edit_year,String edit_per)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/edit_education");

            Log.e("edit edu in=>","do in");
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
            nameValuePairs.add(new BasicNameValuePair("user_email", user_email));
            nameValuePairs.add(new BasicNameValuePair("edu_id", edu_id));
            nameValuePairs.add(new BasicNameValuePair("edit_class", edit_class));
            nameValuePairs.add(new BasicNameValuePair("edit_board", edit_board));
            nameValuePairs.add(new BasicNameValuePair("edit_year", edit_year));
            nameValuePairs.add(new BasicNameValuePair("edit_per", edit_per));

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
            Log.e("edit edu result do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String user(String email)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/myprofile");

            Log.e("in=>", "do in");
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("email", email));

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
            Log.e("user result in do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public String edit_profile(String email,String name,String gender,String dob,String address,String zip,String city,String state,String country,String contact,String edu,String total_exp)
    {
        String result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/edit_profile");
//user,name,gender,dob,address,zip,city,state,country,contact,edu
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(12);
            nameValuePairs.add(new BasicNameValuePair("user_email", email));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("gender", gender));
            nameValuePairs.add(new BasicNameValuePair("dob", dob));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("zip", zip));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            nameValuePairs.add(new BasicNameValuePair("country", country));
            nameValuePairs.add(new BasicNameValuePair("contact", contact));
            nameValuePairs.add(new BasicNameValuePair("finaledu", edu));//
            nameValuePairs.add(new BasicNameValuePair("finalexp", total_exp));

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
            Log.e("result in do in=>", "" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }



    public String fav_jobs(String email)
    {
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/savedjobs");
        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("email", email));
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

    public String job_applied(String email)
    {
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/applied");
        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("user_email", email));
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

    public String all_jobs()
    {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/alljobs");

            HttpResponse response = httpclient.execute(httppost);

            InputStream is = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            while (true) {
                String str = br.readLine();
                if (str == null)
                    break;
                result = result + str;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(flag==1)
        {
            get_all_job(result);
        }
        else if(flag==2)
        {
            get_fav_jobs(result);
        }
        else if(flag==3)
        {
            get_job_applied(result);
        }
        else if(flag==4)
        {
            get_user(result);
        }
        else if(flag==5)
        {
            get_profile_edit(result);
        }
        else if(flag==6)
        {
            get_edu_edit(result);
        }
        else if(flag==7)
        {
            get_edu_delete(result);
        }
        else if(flag==8)
        {
            get_edu_add(result);
        }
        else if(flag==9)
        {
            get_edu_skill(result);
        }
        else if(flag==10)
        {
            get_delete_skill(result);
        }
        else if(flag==11)
        {
            get_company_details(result);
        }
        else if(flag==12)
        {
            get_jobs_posted(result);
        }
        else if(flag==13)
        {
            get_user_applied(result);
        }
        else if(flag==14)
        {
            get_all_locations(result);
        }
        else if(flag==15)
        {
            get_all_fields(result);
        }
        else if(flag==16)
        {
            get_skills(result);
        }
        else if(flag==17)
        {
            get_edit_exp(result);
        }
        else if(flag==18)
        {
            get_edit_work(result);
        }
        else if(flag==19)
        {
            get_companies(result);
        }
        else if(flag==20)
        {
            get_delete_exp(result);
        }
        else if(flag==21)
        {
            get_add_exp(result);
        }
        else if(flag==22)
        {
            get_adds(result);
        }//
        else if(flag==23)
        {
            get_delete_certi(result);
        }
        else if(flag==24)
        {
            get_add_post(result);
        }
        else if(flag==25)
        {
            get_all_seekers(result);
        }
        else if(flag==26)
        {
            get_resume_check(result);
        }

    }

    public void get_resume_check(String result)
    {
        ArrayList<User> user_list=new ArrayList<>();
        int res_count=-1;
        Log.e("count download", "response=" + result);
        try
        {
            JSONArray arr=new JSONArray(result);
            JSONObject obj;
            for(int count=0;count < arr.length();count++)
            {
                obj=arr.getJSONObject(count);
                String rcount=obj.getString("c");
                res_count=Integer.parseInt(rcount);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        String result1 = null;
        if (listener != null)
            listener.onFetchComplete_resume_check(res_count);
    }


    public void get_all_seekers(String result)
    {
        ArrayList<User> user_list=new ArrayList<>();

        Log.e("all seekers", "response=" + result);
        try
        {
            JSONArray arr=new JSONArray(result);
            JSONArray arr1=arr.getJSONArray(0);
            JSONObject obj;
            for(int count=0;count<arr1.length();count++)
            {
                obj=arr1.getJSONObject(count);
                User u=new User();
                u.setUid(obj.getString("user_id"));
                u.setName(obj.getString("user_name"));
                u.setGender(obj.getString("gender"));
                u.setAddress(obj.getString("user_address"));
                u.setContact(obj.getString("user_contact"));
                u.setResume(obj.getString("resume"));
                u.setEmail(obj.getString("user_email"));
                u.setDob(obj.getString("dob"));
                u.setPref_field(obj.getString("pref_field"));
                u.setPref_location(obj.getString("pref_location"));
                u.setEducation(obj.getString("education"));
                u.setTotal_exp(obj.getString("total_exp"));
                u.setIndustry(obj.getString("industry"));
                u.setZip(obj.getString("zip"));
                u.setCity(obj.getString("city"));
                u.setState(obj.getString("state"));
                u.setCountry(obj.getString("country"));
                u.setPassword(obj.getString("password"));
                user_list.add(u);
            }

            JSONArray arr2 = arr.getJSONArray(1);
            JSONObject obj1;
            ArrayList<String> user_email=new ArrayList<>();
            ArrayList<String> skill_name=new ArrayList<>();
            String p_user_email="",p_skill="";
            int list_size=0;
            for (int count1 = 0; count1 < arr2.length(); count1++)
            {
                JSONArray arr3=arr2.getJSONArray(count1);
                for (int count = 0; count < arr3.length(); count++)
                {
                    obj1 = arr3.getJSONObject(count);
                    if(p_user_email.equals(obj1.getString("user_email"))&&!p_user_email.equals(""))
                    {
                        skill_name.set(list_size-1,p_skill+", "+obj1.getString("skill_name"));
                        p_skill=skill_name.get(list_size-1);
                        p_user_email=obj1.getString("user_email");
                    }
                    else
                    {
                        user_email.add(obj1.getString("user_email"));
                        list_size++;
                        skill_name.add(obj1.getString("skill_name"));
                        p_skill=obj1.getString("skill_name");
                        p_user_email=obj1.getString("user_email");
                    }
                    Log.e("skill=>",""+obj1.getString("skill_name"));
                }
            }
            for(int count=0;count<user_email.size();count++)
            {
                for(int count1=0;count1<user_list.size();count1++)
                {
                    if(user_list.get(count1).getEmail().equals(user_email.get(count)))
                    {
                        user_list.get(count1).setSkills(skill_name.get(count));
                        Log.e("user skill=>", "" + user_list.get(count1).getSkills());
                    }
                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_all_seekers(user_list);
    }


    public void get_add_post(String result)
    {
        Log.e("Add post", "response=" + result);
        try
        {
            JSONObject obj=new JSONObject(result);
            String status=obj.getString("status");
            if(status.equals("1"))
            {
                if (listener != null)
                    listener.onFetchComplete_add_post(status);
                return;
            }
            else
            {
                if (listener != null)
                    listener.onFetchFailure("add_job_post");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void get_delete_certi(String result)
    {
        Log.e("delete certi", "response=" + result);
        try
        {


        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_delete_certi(result);
    }

    public void get_adds(String result)
    {
        Log.e("ads from server", "response=" + result);
        try
        {
            JSONArray arr1 = new JSONArray(result);
            JSONObject obj;
            for (int count = 0; count < arr1.length(); count++)
            {
                Adds ad=new Adds();
                obj = arr1.getJSONObject(count);
                ad.setHead(obj.getString("ad_head"));
                ad.setContent(obj.getString("ad_content"));
                ad.setContact(obj.getString("ad_contact"));
                ad.setImage(obj.getString("ad_image"));
                adds.add(ad);

            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_adds(adds);
    }



    public void get_add_exp(String result)
    {
        Log.e("add exp", "response=" + result);
        try
        {


        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_add_exp(result);
    }


    public void get_delete_exp(String result)
    {
        Log.e("delete exp", "response=" + result);
        try
        {


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_delete_exp(result);
    }



    public void get_companies(String result)
    {
        Log.e("companies from server", "response=" + result);
        try
        {
            JSONArray arr1 = new JSONArray(result);
            JSONObject obj;
            for (int count = 0; count < arr1.length(); count++)
            {
                obj = arr1.getJSONObject(count);
                String tmp=obj.getString("com_name");
                companies.add(tmp);

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_companies(companies);
    }


    public void get_edit_work(String result)
    {
        Log.e("editwork from server", "response=" + result);
        try
        {


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_edit_work(result);
    }


    public void get_edit_exp(String result)
    {
        Log.e("editexp from server", "response=" + result);
        try
        {


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_edit_exp(result);
    }

    public void get_skills(String result)
    {
        Log.e("skill reply from server", "response=" + result);
        try
        {
            JSONArray arr1 = new JSONArray(result);
            JSONObject obj;
            for (int count = 0; count < arr1.length(); count++)
            {
                obj = arr1.getJSONObject(count);
                String tmp=obj.getString("skill_name");
                skills_str.add(tmp);

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_skill(skills_str);
    }


    public void get_all_fields(String result)
    {
        Log.e("All fields result=>", "" + result);
        //Math.min()
        ArrayList<String> field=new ArrayList<String>();
        //Log.e("in=>","post3 =>"+result);
        try
        {
            //Log.e("Job get result=",""+res);
            JSONArray arr = new JSONArray(result);
            //Log.e("array length",""+arr.length());
            JSONObject obj;
            for(int count=0;count<arr.length();count++)
            {
                obj=arr.getJSONObject(count);
                field.add(obj.getString("field_name"));
                Log.e("count=>", "" + count);
            }
            Log.e("count=>",""+field.size());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
       // Log.e("finished","sending list");
        if (listener != null)
            listener.onFetchComplete_field(field);
    }


    public void get_all_locations(String result)
    {
        Log.e("All Location result=>", "" + result);
        //Math.min()
        ArrayList<String> location=new ArrayList<String>();
        //Log.e("in=>","post3 =>"+result);
        try
        {
            //Log.e("Job get result=",""+res);
            JSONArray arr = new JSONArray(result);
            Log.e("array length",""+arr.length());
            JSONObject obj;
            for(int count=0;count<arr.length();count++) {
                obj=arr.getJSONObject(count);
                location.add(obj.getString("loc_name"));
                Log.e("count=>",""+count);
            }
            Log.e("count=       >",""+location.size());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Log.e("finished","sending list");
        if (listener != null)
            listener.onFetchComplete_location(location);
    }


    public void get_user_applied(String result)
    {
        Log.e("users applied result=>", "" + result);
        //listener.onFetchFailure(result);
        ArrayList<User_applied> user_applied=new ArrayList<User_applied>();
        try
        {
            JSONArray arr = new JSONArray(result);
            JSONArray arr1 = arr.getJSONArray(0);
            JSONObject obj;

            for (int count = 0; count < arr1.length(); count++)
            {
                obj = arr1.getJSONObject(count);
                User_applied u=new User_applied();
                u.setApp_id(obj.getString("app_id"));
                u.setEmail(obj.getString("user_email"));
                u.setJob_id(obj.getString("job_id"));
                u.setApplydate(obj.getString("applydate"));
                u.setCurr_status(obj.getString("curr_status"));
                u.setCom_email(obj.getString("com_email"));
                String t2=obj.getString("job_title");
                t2=t2.substring(0, 1).toUpperCase() + t2.substring(1);
                u.setJob_title(t2);
                u.setSkill_required(obj.getString("skills_required"));
                u.setExperience(obj.getString("experience"));
                u.setJob_desc(obj.getString("job_description"));
                u.setJob_location(obj.getString("job_location"));
                u.setLast_date(obj.getString("last_date"));
                u.setMin_qual(obj.getString("min_qualification"));
                u.setVacancy(obj.getString("vacancy"));
                u.setCategory(obj.getString("category"));
                u.setJob_type(obj.getString("job_type"));
                u.setSalary_to(obj.getString("salary_to"));
                u.setSalary_from(obj.getString("salary_from"));
                u.setPosted_date(obj.getString("posted_date"));
                u.setUid(obj.getString("user_id"));
                u.setName(obj.getString("user_name"));
                u.setGender(obj.getString("gender"));
                u.setAddress(obj.getString("user_address"));
                u.setContact(obj.getString("user_contact"));
                u.setResume(obj.getString("resume"));
                u.setDob(obj.getString("dob"));
                u.setPref_field(obj.getString("pref_field"));
                u.setPref_location(obj.getString("pref_location"));
                u.setEducation(obj.getString("education"));
                u.setTotal_exp(obj.getString("total_exp"));
                u.setIndustry(obj.getString("industry"));
                u.setZip(obj.getString("zip"));
                u.setCity(obj.getString("city"));
                u.setState(obj.getString("state"));
                u.setCountry(obj.getString("country"));
                user_applied.add(u);
            }

            Log.e("user_applied size=>",""+user_applied.size());
            JSONArray arr2 = arr.getJSONArray(1);
            JSONObject obj1;
            ArrayList<String> user_email=new ArrayList<>();
            ArrayList<String> skill_name=new ArrayList<>();
            String p_user_email="",p_skill="";
            int list_size=0;
            for(int count1=0;count1<arr2.length();count1++)
            {
                JSONArray arr3=arr2.getJSONArray(count1);
                Log.e("skill array size:",""+arr3.length());
                for (int count = 0; count < arr3.length(); count++)
                {
                    obj1 = arr3.getJSONObject(count);
                    Log.e("skill=>",""+obj1.getString("skill_name"));
                    if(p_user_email.equals(obj1.getString("user_email"))&&!p_user_email.equals(""))
                    {
                        skill_name.set(list_size-1,p_skill+", "+obj1.getString("skill_name"));
                        p_skill=skill_name.get(list_size-1);
                        p_user_email=obj1.getString("user_email");
                    }
                    else
                    {
                        user_email.add(obj1.getString("user_email"));
                        list_size++;
                        skill_name.add(obj1.getString("skill_name"));
                        p_skill=obj1.getString("skill_name");
                        p_user_email=obj1.getString("user_email");
                    }
                    //Log.e("skill=>",""+obj1.getString("skill_name"));
                }
            }

            for(int count=0;count<user_email.size();count++)
            {
                for(int count1=0;count1<user_applied.size();count1++)
                {
                    if(user_applied.get(count1).getEmail().equals(user_email.get(count)))
                    {
                        user_applied.get(count1).setSkills(skill_name.get(count));
                        Log.e("user skill=>", "" + user_applied.get(count1).getSkills());
                    }
                }

            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (listener != null)
            listener.onFetchComplete_user_applied(user_applied);
    }

    public void get_jobs_posted(String result)
    {
        Log.e("jobs posted result=>", "" + result);
        //Math.min()
        ArrayList<Job_Post_class> job_list1=new ArrayList<Job_Post_class>();
        //Log.e("in=>","post3 =>"+result);
        try {
            //Log.e("Job get result=",""+res);
            JSONArray arr = new JSONArray(result);
            JSONObject obj;
            int size;
            if (list_flag == 1)
                size = 4;
            else
                size = arr.length();
            for (int count = 0; count < size; count++) {
                obj = arr.getJSONObject(count);
                Job_Post_class j = new Job_Post_class();
                j.setJob_id(obj.getString("job_id"));
                j.c.setCompany_email(obj.getString("com_email"));
                String t2=obj.getString("job_title");
                t2=t2.substring(0, 1).toUpperCase() + t2.substring(1);
                j.setJob_title(t2);
                j.setSkill_required(obj.getString("skills_required"));
                j.setExperience(obj.getString("experience"));
                j.setJob_desc(obj.getString("job_description"));
                j.setJob_location(obj.getString("job_location"));
                j.setLast_date(obj.getString("last_date"));
                j.setMin_qual(obj.getString("min_qualification"));
                j.setVacancy(obj.getString("vacancy"));
                j.setCategory(obj.getString("category"));
                j.setJob_type(obj.getString("job_type"));
                j.setSalary_to(obj.getString("salary_to"));
                j.setSalary_from(obj.getString("salary_from"));
                j.setPosted_date(obj.getString("posted_date"));
                j.c.setCompany_name(obj.getString("com_name"));
                j.c.setCompany_logo(obj.getString("logo"));
                j.c.setCompany_start(obj.getString("start_year"));
                j.c.setCompany_address(obj.getString("com_address"));
                j.c.setCompany_contact(obj.getString("com_contact"));
                j.c.setCompany_type(obj.getString("type"));
                j.c.setCompany_link(obj.getString("com_link"));
                j.c.setCompany_zip(obj.getString("zip"));
                j.c.setCompany_city(obj.getString("city"));
                j.c.setCompany_state(obj.getString("state"));
                j.c.setCompany_country(obj.getString("country"));
                j.c.setCompany_contact_person(obj.getString("contact_person"));
                j.c.setCompany_about(obj.getString("about_company"));
                j.c.setIndustry(obj.getString("industry"));
                //Bitmap bm=DownloadImage("http://jobmafiaa.com//assets/uploads/"+j.c.getCompany_logo());
                //Bitmap bm = BitmapFactory.decodeStream((InputStream) new URL(").getContent());
                //j.c.setBm(bm);
                job_list1.add(j);
                //Log.e("count=" + count, "title" + j.getJob_title()+",id="+j.getJob_id());

            }

            } catch (JSONException e) {
            e.printStackTrace();
        }

            if (listener != null)
                listener.onFetchComplete_jobs_posted(job_list1);

            //3057007792



    }

    public void get_company_details(String result)
    {
        Log.e("company detailsresult=>", "" + result);
        //Math.min()
        //Log.e("in=>","post3 =>"+result);
        Company c=new Company();
        JSONArray arr1 = null;
        try {
            arr1 = new JSONArray(result);

        JSONObject obj;

        for (int count = 0; count < arr1.length(); count++)
        {
            obj = arr1.getJSONObject(count);

            c.setCompany_name(obj.getString("com_name"));
            c.setCompany_logo(obj.getString("logo"));
            c.setCompany_start(obj.getString("start_year"));
            c.setCompany_address(obj.getString("com_address"));
            c.setCompany_contact(obj.getString("com_contact"));
            c.setCompany_email(obj.getString("com_email"));
            c.setCompany_type(obj.getString("type"));
            c.setCompany_link(obj.getString("com_link"));
            c.setCompany_zip("zip");
            c.setCompany_city(obj.getString("city"));
            c.setCompany_state(obj.getString("state"));
            c.setCompany_country(obj.getString("country"));
            c.setCompany_contact_person(obj.getString("contact_person"));
            c.setCompany_about(obj.getString("about_company"));
            c.setIndustry(obj.getString("about_company"));
            c.setPay_status(obj.getString("pay_status"));
            //fj.setJob_id(obj.getString(""));
            //fj.setUser_email(obj.getString("user_email"));
            //Log.e("fav get result=", "" + fj.getJob_id() + ", " + fj.getUser_email());
            //fav_list.add(fj);
            if(listener != null)
                listener.onFetchComplete_company_details(c);
        }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(listener != null)
            listener.onFetchComplete_company_details(c);

        //3057007792


    }


    public void get_delete_skill(String result)
    {
        Log.e("skill delete result=>", "" + result);

        //Log.e("in=>","post3 =>"+result);


        if(listener != null)
            listener.onFetchComplete_skill_delete(result);

        //3057007792


    }

    public void get_edu_skill(String result)
    {
        //Log.e("in=>","post1");
        //Log.e("in=>", "post2");
        //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
        //edittext.setText(result);
        Log.e("skill add result=>", "" + result);

        //Log.e("in=>","post3 =>"+result);


        if(listener != null)
            listener.onFetchComplete_skill_add(result);

        //3057007792


    }

    public void get_edu_add(String result)
    {
        //Log.e("in=>","post1");
        //Log.e("in=>", "post2");
        //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
        //edittext.setText(result);
        Log.e("edu add result=>", "" + result);

        //Log.e("in=>","post3 =>"+result);


        if(listener != null)
            listener.onFetchComplete_edu_add(result);

        //3057007792


    }


    public void get_edu_delete(String result)
    {
        //Log.e("in=>","post1");
        //Log.e("in=>", "post2");
        //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
        //edittext.setText(result);
        Log.e("edu edit result=>", "" + result);

        //Log.e("in=>","post3 =>"+result);


        if(listener != null)
            listener.onFetchComplete_edu_delete(result);

        //3057007792


    }

    public void get_edu_edit(String result)
    {
        //Log.e("in=>","post1");
        //Log.e("in=>", "post2");
        //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
        //edittext.setText(result);
        Log.e("edu edit result=>", "" + result);

        //Log.e("in=>","post3 =>"+result);


        if(listener != null)
            listener.onFetchComplete_edu_edit(result);

        //3057007792


    }

    public void get_user(String result)
    {
        //Log.e("in=>","post1");
        //Log.e("in=>", "post2");
        //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
        //edittext.setText(result);
        //Log.e("Profile result=>", "" + result);

        //Log.e("in=>","post3 =>"+result);
        try{
            JSONObject mainobj=new JSONObject(result);

            JSONArray detail_array=mainobj.getJSONArray("detail");
            JSONArray education_array=mainobj.getJSONArray("education");
            JSONArray exp_array=mainobj.getJSONArray("experience");
            JSONArray skill_array=mainobj.getJSONArray("myskills");
            JSONArray certificate_array=mainobj.getJSONArray("certificate");

            JSONObject detail_obj=detail_array.getJSONObject(0);


            User u=new User();
            u.setUid(detail_obj.getString("user_id"));
            u.setName(detail_obj.getString("user_name"));
            u.setGender(detail_obj.getString("gender"));
            u.setAddress(detail_obj.getString("user_address"));
            u.setContact(detail_obj.getString("user_contact"));
            u.setResume(detail_obj.getString("resume"));
            u.setEmail(detail_obj.getString("user_email"));
            u.setDob(detail_obj.getString("dob"));
            u.setPref_field(detail_obj.getString("pref_field"));
            u.setPref_location(detail_obj.getString("pref_location"));
            u.setEducation(detail_obj.getString("education"));
            u.setTotal_exp(detail_obj.getString("total_exp"));
            u.setIndustry(detail_obj.getString("industry"));
            u.setZip(detail_obj.getString("zip"));
            u.setCity(detail_obj.getString("city"));
            u.setState(detail_obj.getString("state"));
            u.setCountry(detail_obj.getString("country"));

            user.add(u);
            Log.e("user=>", "" + user.get(0).getUid());
            JSONObject education_obj;
            Education e;
            Log.e("education length=>",""+education_array.length());
            for(int count=0;count<education_array.length();count++)
            {
                education_obj=education_array.getJSONObject(count);
                e = new Education();
                e.setEdu_id(education_obj.getString("edu_id"));
                e.setUser_email(education_obj.getString("user_email"));
                e.setQualification(education_obj.getString("class"));
                e.setPercentage(education_obj.getString("percentage"));
                e.setBoard(education_obj.getString("board"));
                e.setPassing_year(education_obj.getString("passing_year"));
                education.add(e);
                Log.e("Education=>", "" +education.get(count).getQualification());
            }

            JSONObject exp_obj;
            Experience exp;
            for(int count=0;count<exp_array.length();count++)
            {
                exp_obj=exp_array.getJSONObject(count);
                exp=new Experience();
                exp.setExp_id(exp_obj.getString("exp_id"));
                exp.setUser_email(exp_obj.getString("user_email"));
                exp.setCompany_name(exp_obj.getString("company_name"));
                exp.setDesignation(exp_obj.getString("post"));
                exp.setIndustry(exp_obj.getString("industry"));
                exp.setExp_year(exp_obj.getString("exp_year"));
                exp.setExp_month(exp_obj.getString("exp_month"));
                exp.setExp_from_month(exp_obj.getString("exp_from_month"));
                exp.setExp_from_year(exp_obj.getString("exp_from_year"));
                exp.setExp_to_month(exp_obj.getString("exp_to_month"));
                exp.setExp_to_year(exp_obj.getString("exp_to_year"));
                exp.setCurrent_work(exp_obj.getString("current_work"));
                experience.add(exp);
                Log.e("experience=>", "" + experience.get(count).getExp_id());
            }


            JSONObject skill_obj;
            Skills s;
            for(int count=0;count<skill_array.length();count++)
            {
                skill_obj=skill_array.getJSONObject(count);

                s=new Skills();
                s.setSkill_id(skill_obj.getString("skill_id"));
                s.setUser_email(skill_obj.getString("user_email"));
                s.setSkill_name(skill_obj.getString("skill_name"));
                skills.add(s);
                Log.e("skills=>", "" + skills.get(count).getSkill_id());
            }

            JSONObject certificate_obj;
            Certificate c;
            for(int count=0;count<certificate_array.length();count++)
            {
                certificate_obj=certificate_array.getJSONObject(count);
                c=new Certificate();
                c.setCertificate_id(certificate_obj.getString("cer_id"));
                c.setUser_email(certificate_obj.getString("user_email"));
                c.setName(certificate_obj.getString("name"));
                c.setFile_name(certificate_obj.getString("file"));
                certificate.add(c);
                Log.e("certificate=>", "" + certificate.get(count).getName());
            }

            if(listener != null)
                listener.onFetchComplete_profile(user,experience,education,skills,certificate);

            //3057007792
        }
        catch(Exception e)
        {

        }

    }

    public void get_profile_edit(String result)
    {
        //Log.e("in=>","post1");
        //Log.e("in=>", "post2");
        //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
        //edittext.setText(result);
        Log.e("Profile edit result=>", "" + result);

        //Log.e("in=>","post3 =>"+result);


            if(listener != null)
                listener.onFetchComplete_profile_edit(result);

            //3057007792


    }

    public void get_fav_jobs(String res)
    {
        try {
            //Log.e("fav get result=",""+res);
            JSONArray arr1 = new JSONArray(res);
            JSONObject obj;
            if(arr1.length()==0)
            {
                listener.onFetchFailure("fav");
                return;
            }
            else
            {
                for (int count = 0; count < arr1.length(); count++)
                {
                    obj = arr1.getJSONObject(count);
                    Fav_Job fj = new Fav_Job();
                    fj.setJob_id(obj.getString("job_id"));
                    fj.setUser_email(obj.getString("user_email"));
                    //Log.e("fav get result=", "" + fj.getJob_id() + ", " + fj.getUser_email());
                    fav_list.add(fj);
                    if (listener != null)
                        listener.onFetchComplete_fav(fav_list);
                }
            }
        }
        catch (Exception e)
        {
            listener.onFetchFailure("fav");
            e.printStackTrace();
        }
    }

    public void get_job_applied(String res)
    {
        try {
            Log.e("applied get result=",""+res);
            JSONArray arr1 = new JSONArray(res);
            JSONObject obj;
            if(arr1.length()==0)
            {
                //Log.e("status value","r="+r);
                //listener.onFetchFailure("apply");
                return;
            }
            else
            {
                for (int count = 0; count < arr1.length(); count++)
                {
                    obj = arr1.getJSONObject(count);
                    User_Apply u = new User_Apply();
                    u.setApp_id(obj.getString("app_id"));
                    u.setUser_email(obj.getString("user_email"));
                    u.setApply_date(obj.getString("applydate"));
                    u.setCurr_status(obj.getString("curr_status"));
                    u.j.setJob_id(obj.getString("job_id"));
                    u.j.c.setCompany_email(obj.getString("com_email"));
                    String t2=obj.getString("job_title");
                    t2=t2.substring(0, 1).toUpperCase() + t2.substring(1);
                    u.j.setJob_title(t2);
                    u.j.setSkill_required(obj.getString("skills_required"));
                    u.j.setExperience(obj.getString("experience"));
                    u.j.setJob_desc(obj.getString("job_description"));
                    u.j.setJob_location(obj.getString("job_location"));
                    u.j.setLast_date(obj.getString("last_date"));
                    u.j.setMin_qual(obj.getString("min_qualification"));
                    u.j.setVacancy(obj.getString("vacancy"));
                    u.j.setCategory(obj.getString("category"));
                    u.j.setJob_type(obj.getString("job_type"));
                    u.j.setSalary_to(obj.getString("salary_to"));
                    u.j.setSalary_from(obj.getString("salary_from"));
                    u.j.setPosted_date(obj.getString("posted_date"));
                    u.j.c.setCompany_id(obj.getString("com_id"));
                    u.j.c.setCompany_name(obj.getString("com_name"));
                    u.j.c.setCompany_start(obj.getString("start_year"));
                    u.j.c.setCompany_address(obj.getString("com_address"));
                    u.j.c.setCompany_contact(obj.getString("com_contact"));
                    u.j.c.setCompany_contact_person(obj.getString("contact_person"));
                    u.j.c.setCompany_type(obj.getString("type"));
                    u.j.c.setCompany_link(obj.getString("com_link"));
                    u.j.c.setCompany_zip(obj.getString("zip"));
                    u.j.c.setCompany_city(obj.getString("city"));
                    u.j.c.setCompany_state(obj.getString("state"));
                    u.j.c.setCompany_country(obj.getString("country"));
                    u.j.c.setCompany_about(obj.getString("about_company"));
                    u.j.c.setIndustry(obj.getString("industry"));
                    u.j.c.setPay_status(obj.getString("pay_status"));
                    u.j.c.setCompany_logo(obj.getString("logo"));
                    job_applied.add(u);

                }
                if (listener != null)
                    listener.onFetchComplete_apply(job_applied);
            }
        }
        catch (Exception e)
        {
            Log.e("in async catch","caught");
            //listener.onFetchFailure("apply");
            e.printStackTrace();
        }
    }

    public void get_all_job(String res)
    {
        JSONObject obj = null;
        try {
            Log.e("Job get result=",""+res);
            JSONArray arr = new JSONArray(res);

            int size;
            String msg="";
            if(list_flag==1&&arr.length()>=4)
                size=4;
            else
                size=arr.length();
            if(arr.length()==0)
            {
                listener.onFetchFailure("jobs");
                return;
            }
            for (int count = 0; count <size ; count++)
            {
                Log.e("arr.size=>", "" + arr.length());
                obj = arr.getJSONObject(count);

                Job_Post_class j = new Job_Post_class();
                j.setJob_id(obj.getString("job_id"));
                j.c.setCompany_email(obj.getString("com_email"));
                String t2=obj.getString("job_title");
                t2=t2.substring(0, 1).toUpperCase() + t2.substring(1);
                j.setJob_title(t2);
                String t1=obj.getString("skills_required");
                if(t1.charAt(t1.length()-1)==' ')
                    t1=t1.substring(0, t1.length()-1);
                if(t1.charAt(t1.length()-1)==',')
                    t1=t1.substring(0, t1.length()-1);
                Log.e("location", "" +t1);

                j.setSkill_required(t1);
                j.setExperience(obj.getString("experience"));
                j.setJob_desc(obj.getString("job_description"));
                String t=obj.getString("job_location");
                t=t.substring(0, t.length()-2);
                Log.e("location",""+t);
                j.setJob_location(t);
                j.setLast_date(obj.getString("last_date"));
                j.setMin_qual(obj.getString("min_qualification"));
                j.setVacancy(obj.getString("vacancy"));
                j.setCategory(obj.getString("category"));
                j.setJob_type(obj.getString("job_type"));
                j.setSalary_to(obj.getString("salary_to"));
                j.setSalary_from(obj.getString("salary_from"));
                j.setPosted_date(obj.getString("posted_date"));
                j.c.setCompany_id(obj.getString("com_id"));
                j.c.setCompany_name(obj.getString("com_name"));
                j.c.setCompany_start(obj.getString("start_year"));
                j.c.setCompany_address(obj.getString("com_address"));
                j.c.setCompany_contact(obj.getString("com_contact"));
                j.c.setCompany_contact_person(obj.getString("contact_person"));
                j.c.setCompany_type(obj.getString("type"));
                j.c.setCompany_link(obj.getString("com_link"));
                j.c.setCompany_zip(obj.getString("zip"));
                j.c.setCompany_city(obj.getString("city"));
                j.c.setCompany_state(obj.getString("state"));
                j.c.setCompany_country(obj.getString("country"));
                j.c.setCompany_about(obj.getString("about_company"));
                j.c.setIndustry(obj.getString("industry"));
                j.c.setPay_status(obj.getString("pay_status"));
                j.c.setCompany_logo(obj.getString("logo"));
                //Bitmap bm=DownloadImage("http://jobmafiaa.com//assets/uploads/"+j.c.getCompany_logo());
                //Bitmap bm = BitmapFactory.decodeStream((InputStream) new URL(").getContent());
                //j.c.setBm(bm);
                job_list.add(j);
                //Log.e("count=" + count, "title" + j.getJob_title()+",id="+j.getJob_id());

            }
            //all.job_list=list;
            //Log.e("where=>", "welcome user " + job_list);
            if(listener != null)
                listener.onFetchComplete_job(job_list);

        }
        catch (JSONException e)
        {
            listener.onFetchFailure("jobs");
            e.printStackTrace();
        }
    }

}