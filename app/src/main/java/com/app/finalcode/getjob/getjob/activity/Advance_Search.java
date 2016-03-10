package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.finalcode.getjob.getjob.R;
import com.app.finalcode.getjob.getjob.classes.Fav_Job;
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
import com.app.finalcode.getjob.getjob.classes.User_Apply;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Advance_Search extends AppCompatActivity
{
    EditText adv_srch_job_exp,adv_srch_job_type,adv_srch_job_salto;

    ListView search_result;

    String location,skill,industry,designation;

    ProgressDialog p_search;

    Spinner adv_srch_job_title,adv_srch_job_skill,adv_srch_job_loc;

    LoginClass l=new LoginClass();

    ArrayList<Job_Post_class> job_search=new ArrayList<Job_Post_class>();
    ArrayList<String> skill_list=new ArrayList<>();
    ArrayList<String> field_list=new ArrayList<>();
    ArrayList<String> loc_list=new ArrayList<>();
    ArrayList<Job_Post_class> job1_list=new ArrayList<Job_Post_class>();
    ArrayList<Fav_Job> fav_list=new ArrayList<Fav_Job>();
    ArrayList<User_Apply> user_apply=new ArrayList<User_Apply>();
    TextView tool_text;

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advance_search);

        tool_text=(TextView)findViewById(R.id.tool_text);
        adv_srch_job_title=(Spinner)findViewById(R.id.adv_srch_job_title);
        adv_srch_job_skill=(Spinner)findViewById(R.id.adv_srch_job_skill);
        adv_srch_job_exp=(EditText)findViewById(R.id.adv_srch_job_exp);
        adv_srch_job_loc=(Spinner)findViewById(R.id.adv_srch_job_loc);
        adv_srch_job_type=(EditText)findViewById(R.id.adv_srch_job_type);
        adv_srch_job_salto=(EditText)findViewById(R.id.adv_srch_job_salto);

        Intent in=getIntent();
        l=(LoginClass)in.getSerializableExtra("loginclass");



        Get_Field_Task field_task=new Get_Field_Task();
        field_task.execute();

        Get_Loc_Task loc_task=new Get_Loc_Task();
        loc_task.execute();

        Get_Skill_Task skill_task=new Get_Skill_Task ();
        skill_task.execute();


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
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                if(adv_srch_job_title.getSelectedItem().toString().equals("Select Field")&&adv_srch_job_loc.getSelectedItem().toString().equals("Select Location"))
                {
                    Toast.makeText(Advance_Search.this,"Select atlest one field out of location and Field!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent in = new Intent(Advance_Search.this, All_Jobs.class);
                    in.putExtra("loginclass", l);
                    in.putExtra("field", adv_srch_job_title.getSelectedItem().toString());
                    in.putExtra("skill", adv_srch_job_skill.getSelectedItem().toString());
                    in.putExtra("exp", adv_srch_job_exp.getText().toString());
                    in.putExtra("location", adv_srch_job_loc.getSelectedItem().toString());
                    in.putExtra("type", adv_srch_job_type.getText().toString());
                    in.putExtra("salto", adv_srch_job_salto.getText().toString());
                    startActivity(in);
                }
                //p_search.dismiss();

            }
        });
    }

    private class Get_Skill_Task extends AsyncTask<String, Void, String>
    {
        ProgressDialog p1;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            p1=new ProgressDialog(Advance_Search.this);
            p1.setTitle("getting skills");
            p1.show();
            //Toast.makeText(Advance_Search.this, "Sending email=" + email + ",pass=" + pass, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... urls)
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


        @Override
        protected void onPostExecute(String result)
        {
            //Toast.makeText(getBaseContext(), "skill reply from server! response="+result, Toast.LENGTH_LONG).show();
            Log.e("skill reply from server", "response=" + result);
            try
            {
                JSONArray arr1 = new JSONArray(result);
                JSONObject obj;
                skill_list.add("Select Skill");
                for (int count = 0; count < arr1.length(); count++)
                {
                    obj = arr1.getJSONObject(count);
                    String tmp=obj.getString("skill_name");
                    skill_list.add(tmp);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Advance_Search.this,R.layout.autocompletetextview,skill_list);

                    adv_srch_job_skill.setAdapter(adapter);
                }
                p1.dismiss();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

    private class Get_Loc_Task extends AsyncTask<String, Void, String>
    {
        ProgressDialog p1;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            p1=new ProgressDialog(Advance_Search.this);
            p1.setTitle("getting Locations");
            p1.show();
            //Toast.makeText(Advance_Search.this, "Sending email=" + email + ",pass=" + pass, Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... urls)
        {
            String result="";
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/all_location");

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


        @Override
        protected void onPostExecute(String result)
        {
            //Toast.makeText(getBaseContext(), "location reply from server! response="+result, Toast.LENGTH_LONG).show();
            Log.e("Location reply ", "response=" + result);
            try
            {
                JSONArray arr1 = new JSONArray(result);
                JSONObject obj;
                loc_list.add("Select Location");
                for (int count = 0; count < arr1.length(); count++)
                {
                    obj = arr1.getJSONObject(count);
                    String tmp=obj.getString("loc_name");
                    loc_list.add(tmp);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Advance_Search.this,R.layout.autocompletetextview,loc_list);

                    adv_srch_job_loc.setAdapter(adapter);
                }
                p1.dismiss();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            p1.dismiss();

        }
    }

    private class Get_Field_Task extends AsyncTask<String, Void, String>
    {
        ProgressDialog p1;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            p1=new ProgressDialog(Advance_Search.this);
            p1.setTitle("getting Fields");
            p1.show();
            //Toast.makeText(Advance_Search.this, "Sending email=" + email + ",pass=" + pass, Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... urls)
        {
            String result="";
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/allfield");

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


        @Override
        protected void onPostExecute(String result)
        {
            //Toast.makeText(getBaseContext(), "field reply from server! response="+result, Toast.LENGTH_LONG).show();
            Log.e("field reply from server", "response=" + result);
            try
            {
                JSONArray arr1 = new JSONArray(result);
                JSONObject obj;
                field_list.add("Select Field");
                for (int count = 0; count < arr1.length(); count++)
                {
                    obj = arr1.getJSONObject(count);
                    String tmp=obj.getString("field_name");
                    field_list.add(tmp);

                    //ArrayAdapter<String> adapter=new ArrayAdapter<String>(Advance_Search.this,android.R.layout.simple_dropdown_item_1line,field_list);
                    ArrayAdapter<String>   adapter= new ArrayAdapter<String>(Advance_Search.this,R.layout.autocompletetextview,field_list);
                    adv_srch_job_title.setAdapter(adapter);
                }
                p1.dismiss();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            p1.dismiss();
        }
    }
}
