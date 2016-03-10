package com.app.finalcode.getjob.getjob.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;

/**
 * Created by abc123 on 17/12/15.
 */
public class Add_Job_Post_Activity extends ActionBarActivity implements View.OnClickListener,FetchDataListener
{
    ProgressDialog p;
    FetchDataListener listen=this;
    EditText addp_design,addp_company,addp_exp,addp_date,addp_terms,addp_vacancy,addp_sal_to,addp_sal_from;
    AutoCompleteTextView addp_skill,addp_ind,addp_loc;
    Button addp_datebtn,addp_postbtn;
    LoginClass l=new LoginClass();
    ArrayList<String> skill_list=new ArrayList<>();
    ArrayList<String> field_list=new ArrayList<>();
    ArrayList<String> loc_list=new ArrayList<>();
    TextView tool_text;

    private int mYear, mMonth, mDay;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_post);

        tool_text=(TextView)findViewById(R.id.tool_text);
        addp_design=(EditText)findViewById(R.id.addp_design);
        addp_company=(EditText)findViewById(R.id.addp_company);
        addp_exp=(EditText)findViewById(R.id.addp_exp);
        addp_skill=(AutoCompleteTextView)findViewById(R.id.addp_skill);
        addp_ind=(AutoCompleteTextView)findViewById(R.id.addp_ind);
        addp_loc=(AutoCompleteTextView)findViewById(R.id.addp_loc);
        addp_date=(EditText)findViewById(R.id.addp_date);
        addp_terms=(EditText)findViewById(R.id.addp_terms);
        addp_vacancy=(EditText)findViewById(R.id.addp_vacancy);
        addp_sal_to=(EditText)findViewById(R.id.addp_sal_to);
        addp_sal_from=(EditText)findViewById(R.id.addp_sal_from);

        addp_postbtn=(Button)findViewById(R.id.addp_postbtn);
        addp_datebtn=(Button)findViewById(R.id.addp_datebtn);

        Get_Field_Task field_task=new Get_Field_Task();
        field_task.execute();

        Get_Loc_Task loc_task=new Get_Loc_Task();
        loc_task.execute();

        Get_Skill_Task skill_task=new Get_Skill_Task ();
        skill_task.execute();

        Intent in=getIntent();
        l=(LoginClass)in.getSerializableExtra("loginclass");

        addp_datebtn.setOnClickListener(this);
        addp_postbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == addp_datebtn) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(Add_Job_Post_Activity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            addp_date.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }

        if(v==addp_postbtn)
        {
            String design = addp_design.getText().toString();
            String mini_quali = addp_company.getText().toString();
            String exp = addp_exp.getText().toString();
            String skills = addp_skill.getText().toString();
            String ind = addp_ind.getText().toString();
            String loc = addp_loc.getText().toString();
            String date = addp_date.getText().toString();
            String terms = addp_terms.getText().toString();
            String vacancy = addp_vacancy.getText().toString();
            String sal_to = addp_sal_to.getText().toString();
            String sal_from = addp_sal_from.getText().toString();
            if (design.equals(""))
            {
                addp_design.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this, "Designation is a required field!", Toast.LENGTH_SHORT).show();
            }
            else if (design.contains("0")||design.contains("1")||design.contains("2")||design.contains("3")||design.contains("4")||design.contains("5")||design.contains("6")||design.contains("7")||design.contains("8")||design.contains("9"))
            {
                addp_design.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this, "Enter a Valid Designation!", Toast.LENGTH_SHORT).show();
            }
            else if (mini_quali.equals(""))
            {
                addp_company.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this,"minimum qualification is required to mention",Toast.LENGTH_SHORT).show();
            }
            else if (mini_quali.contains("0")||mini_quali.contains("1")||mini_quali.contains("2")||mini_quali.contains("3")||mini_quali.contains("4")||mini_quali.contains("5")||mini_quali.contains("6")||mini_quali.contains("7")||mini_quali.contains("8")||mini_quali.contains("9"))
            {
                addp_company.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this, "Enter a Valid Qualification!", Toast.LENGTH_SHORT).show();
            }
            else if (exp.equals(""))
            {
                addp_exp.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this,"in case no experience required insert 0",Toast.LENGTH_SHORT).show();
            }
            else if (skills.equals(""))
            {
                addp_skill.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this,"Skill field is required!",Toast.LENGTH_SHORT).show();
            }
            else if (ind.equals(""))
            {
                addp_ind.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this,"industry is required to mention!",Toast.LENGTH_SHORT).show();
            }
            else if (loc.equals(""))
            {
                addp_loc.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this,"location of job is required to mention!",Toast.LENGTH_SHORT).show();
            }
            else if (date.equals(""))
            {
                addp_date.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this,"last date is required to mention!",Toast.LENGTH_SHORT).show();
            }
            else if (vacancy.equals(""))
            {
                addp_vacancy.requestFocus();
                Toast.makeText(Add_Job_Post_Activity.this,"number of vacancy for job is required to mention!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                p=new ProgressDialog(Add_Job_Post_Activity.this);
                p.show();
                p.setCancelable(false);
                AsyncTaskClass add_job_task=new AsyncTaskClass(listen);
                add_job_task.execute("add_job_post",l.getEmail() , l.getType(), design, loc, skills, ind, vacancy , exp , terms , date , mini_quali , sal_to ,  sal_from);
            }
            return ;
        }

    }

    @Override
    public void onFetchComplete_resume_check(int result1) {

    }

    @Override
    public void onFetchComplete_fav(ArrayList<Fav_Job> data) {

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
    public void onFetchComplete_add_post(String s)
    {
        p.dismiss();
        Log.e("result=",""+s);
        if(s.equals("1"))
        {
            Toast.makeText(Add_Job_Post_Activity.this,"Job Posted Successfully!",Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(Add_Job_Post_Activity.this,"Error While Posting Job, Please Try After Sometime!",Toast.LENGTH_SHORT).show();
        }

    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
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
    public void onFetchComplete_apply(ArrayList<User_Apply> data) {

    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<Skills> data3, ArrayList<Certificate> data4) {

    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data) {

    }

    @Override
    public void onFetchFailure(String msg) {

    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }

    private class Get_Skill_Task extends AsyncTask<String, Void, String>
    {
        ProgressDialog p1;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            p1=new ProgressDialog(Add_Job_Post_Activity.this);
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
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Add_Job_Post_Activity.this,R.layout.autocompletetextview,skill_list);
                    addp_skill.setAdapter(adapter);
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
            p1=new ProgressDialog(Add_Job_Post_Activity.this);
            p1.setTitle("getting Locations");
            p1.show();
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
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Add_Job_Post_Activity.this,R.layout.autocompletetextview,loc_list);

                    addp_loc.setAdapter(adapter);
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
            p1=new ProgressDialog(Add_Job_Post_Activity.this);
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
                    ArrayAdapter<String>   adapter= new ArrayAdapter<String>(Add_Job_Post_Activity.this,R.layout.autocompletetextview,field_list);
                    addp_ind.setAdapter(adapter);
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
