package com.app.finalcode.getjob.getjob.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.app.finalcode.getjob.getjob.classes.Skills;
import com.app.finalcode.getjob.getjob.classes.User;
import com.app.finalcode.getjob.getjob.classes.User_Apply;
import com.app.finalcode.getjob.getjob.classes.User_applied;
import com.app.finalcode.getjob.getjob.utility.AsyncTaskClass;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import me.drakeet.materialdialog.MaterialDialog;

public class Registration_Company extends AppCompatActivity implements View.OnClickListener,FetchDataListener
{
    private static final int REQUEST_PATH = 1;
    private int mYear, mMonth, mDay;
    MaterialDialog mMaterialDialog_search_loc;
    byte[] ba;
    Bitmap bmp;
    String curFileName,curFilePath,type,ba1;
    EditText comp_email,comp_password,comp_cpassword,comp_name,addp_date,comp_abt,comp_zip,comp_country,comp_state,comp_city,comp_phone,comp_contperson,comp_logo_upload,comp_add;
    AutoCompleteTextView comp_ind;
    Button comp_browse_button,comp_register_button,user_addskill_button;
    RadioGroup comp_type;
    CheckBox terms;
    Company c = new Company();
    ProgressDialog p;
    TextView tool_text;
    Button addp_datebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_company);

        tool_text=(TextView)findViewById(R.id.tool_text);
        comp_add=(EditText)findViewById(R.id.comp_add);
        terms=(CheckBox)findViewById(R.id.comp_conditions);
        comp_type=(RadioGroup)findViewById(R.id.comp_type1);
        comp_email=(EditText)findViewById(R.id.comp_email);
        comp_password=(EditText)findViewById(R.id.comp_password);
        comp_cpassword=(EditText)findViewById(R.id.comp_cpassword);
        comp_name=(EditText)findViewById(R.id.comp_name);
        comp_ind =(AutoCompleteTextView)findViewById(R.id.comp_ind);
        comp_abt=(EditText)findViewById(R.id.comp_abt);
        comp_zip=(EditText)findViewById(R.id.comp_zip);
        comp_country=(EditText)findViewById(R.id.comp_country);
        comp_state=(EditText)findViewById(R.id.comp_state);
        comp_city=(EditText)findViewById(R.id.comp_city);
        comp_phone=(EditText)findViewById(R.id.comp_phone);
        comp_contperson=(EditText)findViewById(R.id.comp_contperson);
        comp_logo_upload=(EditText)findViewById(R.id.comp_logo_upload);
        comp_browse_button=(Button)findViewById(R.id.comp_browse_button);
        comp_register_button=(Button)findViewById(R.id.comp_register_button);
        addp_date=(EditText)findViewById(R.id.addp_date);
        addp_datebtn=(Button)findViewById(R.id.addp_datebtn);

        p=new ProgressDialog(Registration_Company.this);
        AsyncTaskClass ind_task=new AsyncTaskClass(this);
        ind_task.execute("Field");

        addp_datebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(Registration_Company.this,new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
                    {
                        addp_date.setText(dayOfMonth + "-"+ (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        comp_zip.setText("");

        type="";
        int t=comp_type.getCheckedRadioButtonId();
        if(t==0)
            type="company";
        else
            type="consultancy";

        comp_browse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getfile(v);

            }
        });

        comp_zip.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (comp_zip.getText().toString().equals("") || comp_zip.getText().toString().equals(null))
                {

                }
                else
                {
                    String c = comp_zip.getText().toString();
                    String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + c + "&region=in";
                    GetLocation task = new GetLocation();
                    task.execute(url);
                }
            }
        });


        comp_register_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(terms.isChecked())
                {
                    type="";
                    int t=comp_type.getCheckedRadioButtonId();
                    if(t==0)
                        type="company";
                    else if(t==1)
                        type="consultancy";
                    if(type.equals(""))
                    {
                        Toast.makeText(Registration_Company.this, "Select a Company Type!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_email.getText().toString().equals("")||comp_email.getText()==null)
                    {
                        comp_email.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company Email can't be empty!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_password.getText().toString().equals("")||comp_password.getText()==null)
                    {
                        comp_password.requestFocus();
                        Toast.makeText(Registration_Company.this, "Password can't be empty!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_cpassword.getText().toString().equals("")||comp_cpassword.getText()==null)
                    {
                        comp_cpassword.requestFocus();
                        Toast.makeText(Registration_Company.this, "Password needed to be confirmed once!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_name.getText().toString().equals("")||comp_name.getText()==null)
                    {
                        comp_name.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company name is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(addp_date.getText().toString().equals("")||addp_date.getText()==null)
                    {
                        addp_date.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company Start date is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_ind.getText().toString().equals("")||comp_ind.getText()==null)
                    {
                        comp_ind.requestFocus();
                        Toast.makeText(Registration_Company.this, "Industry field is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_abt.getText().toString().equals("")||comp_abt.getText()==null)
                    {
                        comp_abt.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company Description is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_add.getText().toString().equals("")||comp_add.getText()==null)
                    {
                        comp_add.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company Address is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_zip.getText().toString().equals("")||comp_zip.getText()==null)
                    {
                        comp_zip.requestFocus();
                        Toast.makeText(Registration_Company.this, "pin code is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_city.getText().toString().equals("")||comp_city.getText()==null)
                    {
                        comp_city.requestFocus();
                        Toast.makeText(Registration_Company.this, "city is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_state.getText().toString().equals("")||comp_state.getText()==null)
                    {
                        comp_state.requestFocus();
                        Toast.makeText(Registration_Company.this, "State is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_country.getText().toString().equals("")||comp_country.getText()==null)
                    {
                        comp_country.requestFocus();
                        Toast.makeText(Registration_Company.this, "country is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_phone.getText().toString().equals("")||comp_phone.getText()==null)
                    {
                        comp_phone.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company Contact number is Mandatory!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_contperson.getText().toString().equals("")||comp_contperson.getText()==null)
                    {
                        comp_contperson.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company contact person is a Mandatory field!", Toast.LENGTH_SHORT).show();
                    }
                    else if(comp_logo_upload.getText().toString().equals("")||comp_logo_upload.getText()==null)
                    {
                        comp_logo_upload.requestFocus();
                        Toast.makeText(Registration_Company.this, "Company logo is a Mandatory to register as a company!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(comp_password.getText().toString().equals(comp_cpassword.getText().toString()))
                        {
                            register_company();
                            p.show();
                            p.setCancelable(false);
                        }
                        else
                        {
                            comp_cpassword.requestFocus();
                            Toast.makeText(Registration_Company.this, "Both Passwords should be matched!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(Registration_Company.this, "A user must read and agree the GetJobs Terms and Conditions!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void register_company()
    {
        c.setCompany_email(comp_email.getText().toString());
        c.setPass(comp_password.getText().toString());
        c.setCompany_name(comp_name.getText().toString());
        c.setIndustry(comp_ind.getText().toString());
        c.setCompany_about(comp_abt.getText().toString());
        c.setCompany_zip(comp_zip.getText().toString());
        c.setCompany_city(comp_city.getText().toString());
        c.setCompany_state(comp_state.getText().toString());
        c.setCompany_country(comp_country.getText().toString());
        c.setCompany_contact(comp_phone.getText().toString());
        c.setCompany_contact_person(comp_contperson.getText().toString());
        c.setCompany_type(type);
        c.setCompany_address(comp_name.getText().toString());

        comp_register_button.setEnabled(false);
        HttpAsyncTask task=new HttpAsyncTask(bmp,System.currentTimeMillis()+".jpeg");
        task.execute();
        return ;
    }

    public void getfile(View view){
        Intent intent1 = new Intent(this, com.app.finalcode.getjob.getjob.utility.FileChooser.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }

    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
                curFileName = data.getStringExtra("GetFileName");
                curFilePath=data.getStringExtra("GetPath");
                comp_logo_upload.setText(curFilePath+"/"+curFileName);
                showAlert(curFilePath+"/"+curFileName);
            }
        }
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    private void showAlert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle(message);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_image, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setBackgroundDrawable(null);

        dialog.show();
        ImageView image = (ImageView) dialog.findViewById(R.id.dialogimage);
        bmp = BitmapFactory.decodeFile(message);
        image.setImageBitmap(bmp);
    }

    @Override
    public void onClick(View v) {

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
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Registration_Company.this,android.R.layout.simple_dropdown_item_1line,al);

        comp_ind.setAdapter(adapter);
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

    class GetLocation extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();
            pDialog=new ProgressDialog(Registration_Company.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... params)
        {
            String url=params[0];
            HttpGet getReq=new HttpGet(url);
            HttpClient client=new DefaultHttpClient();
            String result="";
            try
            {
                HttpResponse resp=client.execute(getReq);
                InputStream is=resp.getEntity().getContent();
                InputStreamReader reader=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(reader);
                while(true)
                {
                    String str=br.readLine();
                    if(str==null)
                        break;
                    result=result+str;
                }
                br.close();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
            return result;
        }//eof doInback

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            pDialog.dismiss();
            try {
                JSONObject resobj = new JSONObject(result);

                JSONArray resArray = resobj.getJSONArray("results");

                JSONObject obj = resArray.getJSONObject(0);

                JSONArray addcomp_Array = obj.getJSONArray("address_components");

                for(int count=0;count<addcomp_Array.length();count++)
                {
                    JSONObject obj2 = addcomp_Array.getJSONObject(count);

                    JSONArray types_Array = obj2.getJSONArray("types");

                    String temp=types_Array.getString(0);

                    if(temp.equals("administrative_area_level_2"))
                    {
                        comp_city.setText(""+obj2.getString("long_name"));

                    }
                    else if(temp.equals("administrative_area_level_1"))
                    {
                        comp_state.setText("" +obj2.getString("long_name"));
                    }
                    else if(temp.equals("country"))
                    {
                        comp_country.setText("" +obj2.getString("long_name"));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }//eof asynctask


    private class HttpAsyncTask extends AsyncTask<String, Integer, String>
    {
        Bitmap image;
        String name;

        long totalSize = 0;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        public HttpAsyncTask(Bitmap image, String name)
        {
            this.image=image;
            this.name=name;

        }

        @Override
        protected String doInBackground(String... urls)
        {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile()
        {
            String result="";

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100,bos);
            String encodedimage=Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
            ArrayList<NameValuePair> entity = new ArrayList<>();
            entity.add(new BasicNameValuePair("cname", c.getCompany_name()));
            entity.add(new BasicNameValuePair("cpass", c.getPass()));
            entity.add(new BasicNameValuePair("cemail", c.getCompany_email()));
            entity.add(new BasicNameValuePair("ccontact", c.getCompany_contact()));
            entity.add(new BasicNameValuePair("ctype", c.getCompany_type()));
            entity.add(new BasicNameValuePair("caddress",c.getCompany_address()));
            entity.add(new BasicNameValuePair("czip", c.getCompany_zip()));
            entity.add(new BasicNameValuePair("ccity", c.getCompany_city()));
            entity.add(new BasicNameValuePair("cstate", c.getCompany_state()));
            entity.add(new BasicNameValuePair("ccountry",c.getCompany_country()));
            entity.add(new BasicNameValuePair("cont_person", c.getCompany_contact_person()));
            entity.add(new BasicNameValuePair("about_company",c.getCompany_about()));
            entity.add(new BasicNameValuePair("cindustry", "" + c.getIndustry()));
            entity.add(new BasicNameValuePair("image",encodedimage));
            entity.add(new BasicNameValuePair("name",name));

            HttpParams httpRequestParams=getHttpRequestParams();
            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/company_register");

            try
            {
                post.setEntity(new UrlEncodedFormEntity(entity));
                HttpResponse response=client.execute(post);

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
            catch (Exception e)
            {

            }
            return result;
        }


        protected void onPostExecute(String result)
        {
            p.dismiss();
            Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
            Log.e("response=",""+result);
            mMaterialDialog_search_loc= new MaterialDialog(Registration_Company.this)
                    .setTitle("Registration Success!")
                    .setMessage("Your request for the Company Registration is being registered. Once Approved, You will be notified through email")
                    .setPositiveButton("OK", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent in = new Intent(Registration_Company.this, Login.class);
                            startActivity(in);
                            mMaterialDialog_search_loc.dismiss();
                        }
                    });

            mMaterialDialog_search_loc.show();
            //edittext.setText(result);
        }
    }

    private HttpParams getHttpRequestParams()
    {
        HttpParams httpRequestParams=new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000*30);
        HttpConnectionParams.setSoTimeout(httpRequestParams,1000*30);
        return httpRequestParams;
    }

}
