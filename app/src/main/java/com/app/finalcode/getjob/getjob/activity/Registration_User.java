package com.app.finalcode.getjob.getjob.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.User;
import com.app.finalcode.getjob.getjob.classes.User_Apply;
import com.app.finalcode.getjob.getjob.classes.User_applied;
import com.app.finalcode.getjob.getjob.utility.AsyncTaskClass;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.drakeet.materialdialog.MaterialDialog;

public class Registration_User extends Activity implements FetchDataListener {

    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    MaterialDialog mMaterialDialog_search_loc;

    String upLoadServerUri = null;
    int file_upload_flag=-1;

    /**********
     * File Path
     *************/
    String Skills="";
    final String uploadFilePath = "/mnt/sdcard/";
    final String uploadFileName = "mysdfile.txt";
    private File file;
    private static final int REQUEST_PATH = 1;
    String curFileName;
    int edu_flag = -1;

    Button user_browse_button_cert;
    EditText user_cert_upload;

    String curFilePath, complete_resume_name,complete_cert_name,cert_name;

    GridLayout lay_exp3;
    LinearLayout lay_exp2;
    ArrayList<String> exp_list = new ArrayList<String>();
    ArrayList<String> edu_list = new ArrayList<String>();
    ArrayList<String> ind_list = new ArrayList<String>();
    Flag f = new Flag();
    ArrayAdapter<String> exp_adapter;
    ArrayAdapter<String> edu_adapter;
    ArrayAdapter<String> ind_adapter;

    FetchDataListener listen=this;

    CheckBox user_conditions;

    EditText user_email, user_password, user_cpassword, user_name, user_zip, user_country, user_state, user_city, user_phone, user_exp, user_resume_upload, edu_other, user_skills;

    Spinner spinner_exp;

    AutoCompleteTextView spinner_industry,spinner_edu;

    Button user_browse_button, user_register_button, user_addskill_button;//user_register_button
    TextView tool_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_user);

        tool_text=(TextView)findViewById(R.id.tool_text);
        edu_other=(EditText)findViewById(R.id.edu_other);
        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);
        user_cpassword = (EditText) findViewById(R.id.user_cpassword);
        user_name = (EditText) findViewById(R.id.user_name);
        user_zip = (EditText) findViewById(R.id.user_zip);
        user_country = (EditText) findViewById(R.id.user_country);
        user_state = (EditText) findViewById(R.id.user_state);
        user_city = (EditText) findViewById(R.id.user_city);
        user_phone = (EditText) findViewById(R.id.user_phone);
        user_exp = (EditText) findViewById(R.id.user_exp);
        user_resume_upload = (EditText) findViewById(R.id.user_resume_upload);
        user_skills = (EditText) findViewById(R.id.user_skills);

        user_conditions = (CheckBox) findViewById(R.id.user_conditions);

        user_browse_button = (Button) findViewById(R.id.user_browse_button);
        user_register_button = (Button) findViewById(R.id.user_register_button);
        user_addskill_button = (Button) findViewById(R.id.user_addskill_button);

        spinner_exp = (Spinner) findViewById(R.id.spinner_exp);
        spinner_industry = (AutoCompleteTextView) findViewById(R.id.spinner_industry);
        spinner_edu = (AutoCompleteTextView) findViewById(R.id.spinner_edu);

        lay_exp2 = (LinearLayout) findViewById(R.id.lay_exp2);
        lay_exp3 = (GridLayout) findViewById(R.id.lay_exp3);

        edu_other.setVisibility(View.GONE);

        user_register_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (user_email.getText().toString().equals("") || user_email.getText() == null)
                {
                    user_email.requestFocus();
                    Toast.makeText(Registration_User.this, "Email Id is required", Toast.LENGTH_SHORT).show();
                }
                else if(!user_email.getText().toString().contains("@")||!user_email.getText().toString().contains("."))
                {
                    user_email.requestFocus();
                    Toast.makeText(Registration_User.this, "Enter a Valid Email Id", Toast.LENGTH_SHORT).show();
                }
                else if (user_password.getText().toString().equals("") || user_password.getText() == null)
                {
                    user_password.requestFocus();
                    Toast.makeText(Registration_User.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else if (user_cpassword.getText().toString().equals("") || user_cpassword.getText() == null)
                {
                    user_cpassword.requestFocus();
                    Toast.makeText(Registration_User.this, "You need to confirm password", Toast.LENGTH_SHORT).show();
                }
                else if (user_name.getText().toString().equals("") || user_name.getText() == null)
                {
                    user_name.requestFocus();
                    Toast.makeText(Registration_User.this, "Name field is Required", Toast.LENGTH_SHORT).show();
                }
                else if(user_name.getText().toString().contains("0")||user_name.getText().toString().contains("1")||user_name.getText().toString().contains("2")||user_name.getText().toString().contains("3")||user_name.getText().toString().contains("4")||user_name.getText().toString().contains("5")||user_name.getText().toString().contains("6")||user_name.getText().toString().contains("7")||user_name.getText().toString().contains("8")||user_name.getText().toString().contains("9"))
                {
                    user_name.requestFocus();
                    Toast.makeText(Registration_User.this, "Enter a Valid Name", Toast.LENGTH_SHORT).show();
                }
                else if (user_zip.getText().toString().equals("") || user_zip.getText() == null)
                {
                    user_zip.requestFocus();
                    Toast.makeText(Registration_User.this, "Pin Code is a Required field", Toast.LENGTH_SHORT).show();
                }
                else if (user_city.getText().toString().equals("") || user_city.getText() == null)
                {
                    user_city.requestFocus();
                    Toast.makeText(Registration_User.this, "City is a Required field", Toast.LENGTH_SHORT).show();
                }
                else if(user_city.getText().toString().contains("0")||user_city.getText().toString().contains("1")||user_city.getText().toString().contains("2")||user_city.getText().toString().contains("3")||user_city.getText().toString().contains("4")||user_city.getText().toString().contains("5")||user_city.getText().toString().contains("6")||user_city.getText().toString().contains("7")||user_city.getText().toString().contains("8")||user_city.getText().toString().contains("9"))
                {
                    user_city.requestFocus();
                    Toast.makeText(Registration_User.this, "Enter a Valid city name", Toast.LENGTH_SHORT).show();
                }
                else if (user_state.getText().toString().equals("") || user_state.getText() == null)
                {
                    user_state.requestFocus();
                    Toast.makeText(Registration_User.this, "State is a Required field", Toast.LENGTH_SHORT).show();
                }
                else if(user_state.getText().toString().contains("0")||user_state.getText().toString().contains("1")||user_state.getText().toString().contains("2")||user_state.getText().toString().contains("3")||user_state.getText().toString().contains("4")||user_state.getText().toString().contains("5")||user_state.getText().toString().contains("6")||user_state.getText().toString().contains("7")||user_state.getText().toString().contains("8")||user_state.getText().toString().contains("9"))
                {
                    user_state.requestFocus();
                    Toast.makeText(Registration_User.this, "Enter a Valid state name", Toast.LENGTH_SHORT).show();
                }
                else if (user_country.getText().toString().equals("") || user_country.getText() == null)
                {
                    user_country.requestFocus();
                    Toast.makeText(Registration_User.this, "Country is a Required field", Toast.LENGTH_SHORT).show();
                }
                else if(user_country.getText().toString().contains("0")||user_country.getText().toString().contains("1")||user_country.getText().toString().contains("2")||user_country.getText().toString().contains("3")||user_country.getText().toString().contains("4")||user_country.getText().toString().contains("5")||user_country.getText().toString().contains("6")||user_country.getText().toString().contains("7")||user_country.getText().toString().contains("8")||user_country.getText().toString().contains("9"))
                {
                    user_country.requestFocus();
                    Toast.makeText(Registration_User.this, "Enter a Valid country name", Toast.LENGTH_SHORT).show();
                }
                else if (user_phone.getText().toString().equals("") || user_phone.getText() == null)
                {
                    user_phone.requestFocus();
                    Toast.makeText(Registration_User.this, "Contact Number is a Required field", Toast.LENGTH_SHORT).show();
                }
                else if (user_phone.getText().toString().length()<10)
                {
                    user_phone.requestFocus();
                    Toast.makeText(Registration_User.this, "Enter a Valid Contact number", Toast.LENGTH_SHORT).show();
                }
                else if (user_exp.getText().toString().equals("") || user_exp.getText() == null)
                {
                    user_exp.requestFocus();
                    Toast.makeText(Registration_User.this, "Experience field is a Required field, In case of no experience enter 0", Toast.LENGTH_SHORT).show();
                }
                else if (spinner_edu.getText().toString().equals("") || spinner_edu.getText() == null)
                {
                    spinner_edu.requestFocus();
                    Toast.makeText(Registration_User.this, "Education field is a Required field", Toast.LENGTH_SHORT).show();
                }
                else if (user_resume_upload.getText().toString().equals("") || user_resume_upload.getText() == null)
                {
                    Toast.makeText(Registration_User.this, "uploading resume is Mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (!user_password.getText().toString().equals(user_cpassword.getText().toString()))
                {
                    user_cpassword.requestFocus();
                    Toast.makeText(Registration_User.this, "both Passwords should be same", Toast.LENGTH_SHORT).show();
                }
                else if (user_password.getText().toString().equals(user_cpassword.getText().toString()))
                {
                    if (user_conditions.isChecked())
                        register_user();
                    else
                        Toast.makeText(Registration_User.this, "A user must read and agree the JobMafiaa Terms and Conditions!", Toast.LENGTH_LONG).show();
                }
            }
        });

        user_addskill_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final LinearLayout l;
                final TextView t;
                TextView t2;
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(user_skills.getText().toString());
                boolean b = m.find();
                //Toast.makeText(Registration_User.this,""+user_skills.getText().toString(),Toast.LENGTH_LONG).show();
                if (user_skills.getText().toString().equals("") || user_skills.getText().toString().equals(null)||b)
                {
                    Toast.makeText(Registration_User.this, "Enter the valid Skills you possess!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    f.counter++;
                    //Toast.makeText(Registration_User.this,"counter="+f.counter,Toast.LENGTH_LONG).show();
                    if (f.counter > 5)
                    {
                        f.counter--;
                        Toast.makeText(Registration_User.this, "Only 5 skills are allowed here!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        if(Skills.equals(""))
                        {

                        }
                        else
                        {
                            Skills=Skills+", ";
                        }
                        l = new LinearLayout(Registration_User.this);
                        l.setBackgroundResource(R.drawable.skill_back1);
                        l.setOrientation(LinearLayout.HORIZONTAL);
                        l.setClickable(true);

                        t = new TextView(Registration_User.this);
                        Skills=Skills+user_skills.getText().toString();
                        String skill = " " + user_skills.getText().toString() + " ";
                        f.skills[f.counter - 1] = user_skills.getText().toString();
                        skill = skill.toUpperCase();
                        t.setText(skill);
                        t.setBackgroundResource(R.drawable.skill_back);


                        //t1 = new TextView(Registration_User.this);
                        //t1.setText(" ");


                        t2 = new TextView(Registration_User.this);
                        t2.setText(" X ");

                        t.setTextColor(Color.BLACK);
                        t2.setTextColor(Color.BLACK);
                        t2.setClickable(true);

                        l.addView(t);
                        l.addView(t2);

                        lay_exp3.addView(l);
                        //lay_exp3.addView(t1);

                        t2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                f.counter--;
                                lay_exp3.removeView(l);
                            }
                        });
                        //Toast.makeText(Registration_User.this,""+f.counter,Toast.LENGTH_LONG).show();
                        user_skills.setText("");
                    }
                }

            }
        });

        ind_list = new ArrayList<String>();

        AsyncTaskClass ind_task=new AsyncTaskClass(listen);
        ind_task.execute("Field");


        user_zip.setText("");

        //spinner_exp,spinner_industry,spinner_edu

        exp_list.add("Years");
        exp_list.add("Months");
        exp_adapter = new ArrayAdapter<String>(Registration_User.this, android.R.layout.simple_spinner_item, exp_list);

        edu_list.add("Graduation");
        edu_list.add("Post Graduation");
        edu_list.add("High School");
        edu_list.add("High Secondary");
        edu_list.add("Senior Secondary");
        edu_list.add("Other");

        edu_adapter = new ArrayAdapter<String>(Registration_User.this, android.R.layout.simple_spinner_dropdown_item, edu_list);

        spinner_edu.setAdapter(edu_adapter);
        spinner_exp.setAdapter(exp_adapter);

        spinner_edu.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(spinner_edu.getText().toString().equals("Other"))
                {
                    edu_other.setVisibility(View.VISIBLE);
                }
                else
                    edu_other.setVisibility(View.INVISIBLE);
            }
        });

        user_zip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (user_zip.getText().toString().equals("") || user_zip.getText().toString().equals(null)) {

                } else {

                    String c = user_zip.getText().toString();
                    String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + c + "&region=in";
                    GetLocation task = new GetLocation();
                    task.execute(url);
                }

            }
        });

        user_browse_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getfile(v);
                file_upload_flag=1;
            }
        });


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
    public void onFetchComplete_add_post(String s) {

    }

    @Override
    public void onFetchComplete_jobs_posted(ArrayList<Job_Post_class> j) {

    }

    @Override
    public void onFetchComplete_field(ArrayList<String> al)
    {
        ind_list=al;
        ind_adapter = new ArrayAdapter<String>(Registration_User.this, android.R.layout.simple_spinner_item, ind_list);
        spinner_industry.setAdapter(ind_adapter);

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
    public void onFetchComplete_profile(ArrayList<User> data, ArrayList<Experience> data1, ArrayList<Education> data2, ArrayList<com.app.finalcode.getjob.getjob.classes.Skills> data3, ArrayList<Certificate> data4) {

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

    public class Flag
    {
        int flag = 0;
        String skills[] = new String[5];

        public int getCounter()
        {
            return counter;
        }

        public void setCounter(int counter)
        {
            this.counter = counter;
        }

        int counter = 0;

        public int getFlag()
        {
            return flag;
        }

        public void setFlag(int flag)
        {
            this.flag = flag;
        }
    }

    public void getfile(View view)
    {
        Intent intent1 = new Intent(this, com.app.finalcode.getjob.getjob.utility.FileChooser.class);
        startActivityForResult(intent1, REQUEST_PATH);
    }

    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH)
        {
            if (resultCode == RESULT_OK)
            {
                curFileName = data.getStringExtra("GetFileName");
                curFilePath = data.getStringExtra("GetPath");
                if(file_upload_flag==1)
                {

                    user_resume_upload.setText(curFilePath + "/" + curFileName);
                    complete_resume_name = curFilePath + "/" + curFileName;
                }
                if(file_upload_flag==2)
                {
                    user_cert_upload.setText(curFilePath + "/" + curFileName);
                    cert_name=curFileName;
                    complete_cert_name= curFilePath + "/" + curFileName;
                }


                //   file.setText(curFileName);
            }
        }
    }

    private void register_user()
    {
        user_register_button.setEnabled(false);
        final User u = new User();
        if (spinner_edu.getText().toString().equals("Other"))
        {
            u.setEducation(edu_other.getText().toString());
            edu_flag = 1;
        }
        else
        {
            u.setEducation(spinner_edu.getText().toString());
            edu_flag = 0;
        }

        u.setIndustry(spinner_industry.getText().toString());
        u.setEmail(user_email.getText().toString());
        u.setResume(user_resume_upload.getText().toString());
        u.setTotal_exp("" + user_exp.getText().toString() + " " + spinner_exp.getSelectedItem().toString());
        u.setContact(user_phone.getText().toString());
        u.setCity(user_city.getText().toString());
        u.setState(user_state.getText().toString());
        u.setCountry(user_country.getText().toString());
        u.setZip(user_zip.getText().toString());
        u.setName(user_name.getText().toString());
        u.setEmail(user_email.getText().toString());
        u.setPassword(user_password.getText().toString());



        new Thread(new Runnable()
        {
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        uploadFile();
                    }
                });
            }

            private void uploadFile()
            {
                System.out.println("ftp" + "call upload file");

                try
                {
                    String root_sd = Environment.getExternalStorageDirectory().toString();
                    System.out.println("ftp" + root_sd);
                    File file = new File(root_sd);


                    // Set your file path here
                    FileInputStream fstrm = new FileInputStream(complete_resume_name);

                    // Set your server page url (and the file title/description)
                    HttpFileUpload hfu = new HttpFileUpload("http://www.jobmafiaa.com/index.php/Main_api/user_register", "my file title", "my file description");
                    hfu.Send_Now(fstrm,u);
                }
                catch (FileNotFoundException e)
                {
                    // Error: File not found
                    System.out.println("ftp" + e.toString());
                }
            }
        }).start();

    }

    class GetLocation extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            user_city.setText("");
            user_state.setText("");
            user_country.setText("");
            pDialog = new ProgressDialog(Registration_User.this);
            //pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        protected String doInBackground(String... params)
        {
            String url = params[0];
            HttpGet getReq = new HttpGet(url);
            HttpClient client = new DefaultHttpClient();
            String result = "";
            try
            {
                HttpResponse resp = client.execute(getReq);
                InputStream is = resp.getEntity().getContent();
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
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return result;
        }//eof doInback

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            pDialog.dismiss();
            try
            {
                JSONObject resobj = new JSONObject(result);
                JSONArray resArray = resobj.getJSONArray("results");
                JSONObject obj = resArray.getJSONObject(0);
                JSONArray addcomp_Array = obj.getJSONArray("address_components");
                for (int count = 0; count < addcomp_Array.length(); count++)
                {
                    JSONObject obj2 = addcomp_Array.getJSONObject(count);
                    JSONArray types_Array = obj2.getJSONArray("types");
                    String temp = types_Array.getString(0);
                    if (temp.equals("administrative_area_level_2"))
                    {
                        user_city.setText("" + obj2.getString("long_name"));
                    }
                    else if (temp.equals("administrative_area_level_1"))
                    {
                        user_state.setText("" + obj2.getString("long_name"));
                    }
                    else if (temp.equals("country"))
                    {
                        user_country.setText("" + obj2.getString("long_name"));
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }


    public class HttpFileUpload implements Runnable
    {
        URL connectURL;
        String responseString;
        String Title;
        User u=new User();
        String Description;
        byte[] dataToServer;
        FileInputStream fileInputStream = null;

        HttpFileUpload(String urlString, String vTitle, String vDesc)
        {
            try
            {
                connectURL = new URL(urlString);
                Title = vTitle;
                Description = vDesc;
            }
            catch (Exception ex)
            {
                Log.i("HttpFileUpload", "URL Malformatted");
            }
        }

        void Send_Now(FileInputStream fStream,User u1)
        {
            u=u1;
            fileInputStream = fStream;
            Log.e("name=>",""+u.getName());
            Log.e("email=>", "" + u.getEmail());
            Log.e("password=>", "" + u.getPassword());
            Sending();
        }

        void Sending()
        {
            System.out.println("ftp" + "call sending()");
            final String iFileName = curFileName;
            final String lineEnd = "\r\n";
            final String twoHyphens = "--";
            final String boundary = "*****";
            final String Tag = "fSnd";
            Log.e(Tag, "Starting Http File Sending to URL");

            ProgressDialog dialog=new ProgressDialog(Registration_User.this);
            dialog.show();

            Thread thread = new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        String charset = "UTF-8";
                        String requestURL = "http://www.jobmafiaa.com/index.php/Main_api/user_register";
                        MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                        multipart.addFilePart("resume", new File(complete_resume_name));
                        multipart.addFormField("skills", Skills);
                        multipart.addFormField("user_email", u.getEmail());
                        multipart.addFormField("name", u.getName());
                        multipart.addFormField("password", u.getPassword());
                        multipart.addFormField("zip", u.getZip());
                        multipart.addFormField("city", u.getCity());
                        multipart.addFormField("state", u.getState());
                        multipart.addFormField("country", u.getCountry());
                        multipart.addFormField("contact", u.getContact());
                        multipart.addFormField("exp_year", u.getTotal_exp());
                        multipart.addFormField("industry", u.getIndustry());
                        if(edu_flag==0)
                            multipart.addFormField("education", u.getEducation());
                        else
                        {
                            multipart.addFormField("education", "other");
                            multipart.addFormField("other_edu", u.getEducation());
                        }

                        multipart.addFormField("resumename", u.getResume());

                        List<String> response = multipart.finish();
                        Log.e("response", "" + response);



                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

            dialog.dismiss();
            mMaterialDialog_search_loc= new MaterialDialog(Registration_User.this)
                    .setTitle("Registration Success!")
                    .setMessage("A Confirmation mail has been sent to your registered email-id, kindly check and login!")
                    .setPositiveButton("OK", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent in = new Intent(Registration_User.this, Login.class);
                            startActivity(in);
                            mMaterialDialog_search_loc.dismiss();
                        }
                    });

            mMaterialDialog_search_loc.show();
        }

        public void run()
        {
            // TODO Auto-generated method stub
        }
    }

    public class MultipartUtility
    {
        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private HttpURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         *
         * @param requestURL
         * @param charset
         * @throws IOException
         */
        public MultipartUtility(String requestURL, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";

            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
            httpConn.setRequestProperty("Test", "Bonjour");
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         *
         * @param name  field name
         * @param value field value
         */
        public void addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a upload file section to the request
         *
         * @param fieldName  name attribute in <input type="file" name="..." />
         * @param uploadFile a File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, File uploadFile) throws IOException
        {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + fieldName
                    + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: "+ URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         *
         * @param name  - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         *
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public List<String> finish() throws IOException {
            List<String> response = new ArrayList<String>();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    response.add(line);
                }
                reader.close();
                httpConn.disconnect();
            }
            else
            {
                throw new IOException("Server returned non-OK status: " + status);
            }
            return response;
        }
    }
}