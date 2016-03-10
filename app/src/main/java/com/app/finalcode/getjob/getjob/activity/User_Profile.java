package com.app.finalcode.getjob.getjob.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
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
import java.util.Calendar;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class User_Profile extends AppCompatActivity implements FetchDataListener
{
    int field_flag=-1;
    String exp_from_month="",exp_from_year="",exp_to_month="",exp_to_year="";
    AutoCompleteTextView add_skill_name;

    CheckBox edit_exp_current;

    Bitmap bmp;

    String complete_resume_name;
    String certi_title;

    Button edit_exp_from_btn,edit_exp_till_btn;

    ArrayList<String> cert_list=new ArrayList<String>();
    ArrayList<String> exp_list_str=new ArrayList<String>();
    ArrayList<String> edu_list_str=new ArrayList<String>();
    ArrayList<String> skill=new ArrayList<String>();

    private static final int REQUEST_PATH = 1;
    ArrayList<String> certi_list=new ArrayList<String>();

    String curFileName,curFilePath;

    MaterialDialog mMaterialDialog,mMaterialDialog_edit_edu,mMaterialDialog_select_class,mMaterialDialog_add_edu,mMaterialDialog_add_skill,mMaterialDialog_delete_edu,mMaterialDialog_delete_skill,mMaterialDialog_exp_edit,mMaterialDialog_edit_work,mMaterialDialog_delete_exp ,mMaterialDialog_exp_add;

    TextView user_p_name,user_p_work_stat,user_p_city,user_p_exp,user_p_exp_sal,user_p_email,user_p_phone,user_p_about;
    TextView user_p_skill;
    TextView user_p_pref_desig,user_p_pref_ind,user_p_pref_loc,user_p_pref_field,user_p_dob,user_p_gender;
    TextView user_p_hometown,user_p_pin,user_p_address,user_p_resume;

    AutoCompleteTextView edit_user_pref_loc,edit_user_pref_field,edit_user_gender;

    TextView edit_exp_email,edit_exp_company,edit_exp_post,edit_exp_month,edit_exp_from;
    TextView edit_exp_to,edit_exp_year;
    AutoCompleteTextView edit_exp_ind,edit_user_educ;

    EditText edit_user_name,edit_user_dob,edit_user_address,edit_user_zip,edit_user_city,edit_user_state,edit_user_country,edit_user_contact,edit_user_tot_exp,edit_edu_quali,edit_edu_board,edit_edu_passyr,edit_edu_perc;

    ImageView edit_profile_basic,verify,delete_skill,edit_exp,edit_edu,edit_work,edit_personal,edit_resume,delete_edu,add_edu,add_skill,add_creti,add_exp,delete_exp,delete_certi;

    ListView exp_list,edu_list,user_p_cert_list;
    Spinner edit_edu_select,edit_exp_select;

    LoginClass l;
    User u=new User();

    ArrayList<User> user= new ArrayList<User>();
    ArrayList<String> fields= new ArrayList<String>();
    ArrayList<String> locations= new ArrayList<String>();
    ArrayList<Education> education= new ArrayList<Education>();
    ArrayList<Experience> experience= new ArrayList<Experience>();
    ArrayList<Skills> skills= new ArrayList<Skills>();
    ArrayList<Certificate> certificate= new ArrayList<Certificate>();

    ProgressDialog p,p1,p_edit_edu,p_delete_edu,p_add_edu,p_add_skill,p_delete_skill,p_edit_exp,p_edit_work,p_edit_work1,p_cert,p_delete_exp,p_add_exp;

    FetchDataListener obj=this;

    int file_flag=-1;

    @Override
    protected void onStart()
    {
        super.onStart();
        p=new ProgressDialog(User_Profile.this);
        p.show();

        Intent in=getIntent();
        l=(LoginClass)in.getSerializableExtra("loginclass");
        Log.e("email=>", "" + l.getEmail());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        user_p_name=(TextView)findViewById(R.id.user_p_name);
        user_p_work_stat=(TextView)findViewById(R.id.user_p_work_stat);
        user_p_city=(TextView)findViewById(R.id.user_p_city);
        user_p_exp=(TextView)findViewById(R.id.user_p_exp);
        user_p_email=(TextView)findViewById(R.id.user_p_email);
        user_p_phone=(TextView)findViewById(R.id.user_p_phone);
        user_p_skill=(TextView)findViewById(R.id.user_p_skill);
        user_p_pref_ind=(TextView)findViewById(R.id.user_p_pref_ind);
        user_p_pref_loc=(TextView)findViewById(R.id.user_p_pref_loc);
        user_p_dob=(TextView)findViewById(R.id.user_p_dob);
        user_p_gender=(TextView)findViewById(R.id.user_p_gender);
        user_p_hometown=(TextView)findViewById(R.id.user_p_hometown);
        user_p_pin=(TextView)findViewById(R.id.user_p_pin);
        user_p_address=(TextView)findViewById(R.id.user_p_address);
        user_p_resume=(TextView)findViewById(R.id.user_p_resume);

        delete_skill=(ImageView)findViewById(R.id.delete_skill);
        add_edu=(ImageView)findViewById(R.id.add_edu);
        edit_profile_basic=(ImageView)findViewById(R.id.edit_profile_basic);
        verify=(ImageView)findViewById(R.id.verify);
        add_skill=(ImageView)findViewById(R.id.add_skill);
        edit_exp=(ImageView)findViewById(R.id.edit_exp);
        edit_edu=(ImageView)findViewById(R.id.edit_edu);
        edit_work=(ImageView)findViewById(R.id.edit_work);
        edit_personal=(ImageView)findViewById(R.id.edit_personal);
        edit_resume=(ImageView)findViewById(R.id.edit_resume);
        delete_edu=(ImageView)findViewById(R.id.delete_edu);
        add_creti=(ImageView)findViewById(R.id.add_creti);
        add_exp=(ImageView)findViewById(R.id.add_exp);
        delete_exp=(ImageView)findViewById(R.id.delete_exp);
        delete_certi=(ImageView)findViewById(R.id.delete_certi);

        exp_list=(ListView)findViewById(R.id.user_p_exp_list);
        edu_list=(ListView)findViewById(R.id.user_p_edu_list);
        user_p_cert_list=(ListView)findViewById(R.id.user_p_cert_list);



        add_creti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getfile(v);
                file_flag = 1;

            }
        });

        delete_certi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String[] skill_id_delete = new String[1];
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.delete_skill_dialog, null);
                final Spinner delete_skill_select;
                delete_skill_select= (Spinner) itemView_select.findViewById(R.id.delete_skill_select);

                ArrayList<String> certi_delete = new ArrayList<String>();
                certi_delete.add("Select");
                for (int count = 0; count < certificate.size(); count++)
                {
                    certi_delete.add(certificate.get(count).getName());
                    //Log.e("Qualification=>", "" + skills.get(count).getSkill_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(User_Profile.this, android.R.layout.simple_list_item_1, certi_delete);
                delete_skill_select.setAdapter(adapter);


                mMaterialDialog_delete_skill = new MaterialDialog(User_Profile.this)
                        .setTitle("Edit Basic Details")
                        .setView(itemView_select)
                        .setPositiveButton("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                p_delete_skill = new ProgressDialog(User_Profile.this);
                                p_delete_skill.show();
                                p_delete_skill.setCancelable(false);
                                skill_id_delete[0] = certificate.get(delete_skill_select.getSelectedItemPosition() - 1).getCertificate_id();
                                Log.e("selected", "" + (delete_skill_select.getSelectedItemPosition() - 1));
                                Toast.makeText(User_Profile.this, "" + (delete_skill_select.getSelectedItemPosition() - 1), Toast.LENGTH_SHORT).show();
//                                p_delete_edu.dismiss();
                                AsyncTaskClass delete_skill = new AsyncTaskClass(obj);
                                //user_email,edu_id,edit_class,edit_board,edit_year,edit_per
                                Log.e("certi id deleting",""+skill_id_delete[0]);
                                delete_skill.execute("delete_certi", skill_id_delete[0]);
                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                mMaterialDialog_delete_skill.dismiss();

                            }
                        });
                mMaterialDialog_delete_skill.show();
            }
        });


        delete_skill.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String[] skill_id_delete = new String[1];
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.delete_skill_dialog, null);
                final Spinner delete_skill_select;
                delete_skill_select= (Spinner) itemView_select.findViewById(R.id.delete_skill_select);

                ArrayList<String> skill_delete = new ArrayList<String>();
                skill_delete.add("Select");
                for (int count = 0; count < skills.size(); count++)
                {
                    skill_delete.add(skills.get(count).getSkill_name());
                    Log.e("Qualification=>", "" + skills.get(count).getSkill_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(User_Profile.this, android.R.layout.simple_list_item_1, skill_delete);
                delete_skill_select.setAdapter(adapter);

                mMaterialDialog_delete_skill = new MaterialDialog(User_Profile.this)
                        .setTitle("Edit Basic Details")
                        .setView(itemView_select)
                        .setPositiveButton("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                p_delete_skill = new ProgressDialog(User_Profile.this);
                                p_delete_skill.show();
                                p_delete_skill.setCancelable(false);
                                skill_id_delete[0] = skills.get(delete_skill_select.getSelectedItemPosition() - 1).getSkill_id();
                                Toast.makeText(User_Profile.this, "" + (delete_skill_select.getSelectedItemPosition() - 1), Toast.LENGTH_SHORT).show();
                                AsyncTaskClass delete_skill = new AsyncTaskClass(obj);
                                delete_skill.execute("delete_skill", skill_id_delete[0]);
                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                mMaterialDialog_delete_skill.dismiss();
                            }
                        });
                mMaterialDialog_delete_skill.show();
            }
        });

        delete_exp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String[] exp_id_delete = new String[1];
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.delete_exp_dialog, null);
                final Spinner delete_exp_select;
                delete_exp_select= (Spinner) itemView_select.findViewById(R.id.delete_exp_select);

                if(experience.size()<=0)
                    Toast.makeText(User_Profile.this,"Add Experience First!",Toast.LENGTH_LONG).show();
                else
                {
                    ArrayList<String> exp_delete = new ArrayList<String>();
                    exp_delete.add("Select");

                    for (int count = 0; count < experience.size(); count++)
                        exp_delete.add(experience.get(count).getCompany_name());

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(User_Profile.this, android.R.layout.simple_list_item_1, exp_delete);
                    delete_exp_select.setAdapter(adapter);

                    mMaterialDialog_delete_exp = new MaterialDialog(User_Profile.this)
                            .setTitle("Edit Basic Details")
                            .setView(itemView_select)
                            .setPositiveButton("OK", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    p_delete_exp = new ProgressDialog(User_Profile.this);
                                    p_delete_exp.show();
                                    p_delete_exp.setCancelable(false);
                                    exp_id_delete[0] = experience.get(delete_exp_select.getSelectedItemPosition() - 1).getExp_id();
                                    Toast.makeText(User_Profile.this, "" + (delete_exp_select.getSelectedItemPosition() - 1) + "," + experience.get(delete_exp_select.getSelectedItemPosition() - 1).getExp_id(), Toast.LENGTH_SHORT).show();
                                    AsyncTaskClass delete_exp = new AsyncTaskClass(obj);
                                    delete_exp.execute("delete_exp", l.getEmail(), exp_id_delete[0]);
                                }
                            })
                            .setNegativeButton("CANCEL", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    mMaterialDialog_delete_exp.dismiss();
                                }
                            });
                    mMaterialDialog_delete_exp.show();
                }
            }
        });

        add_skill.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_delete= inflater.inflate(R.layout.add_skill_dialog, null);

                add_skill_name=(AutoCompleteTextView)itemView_delete.findViewById(R.id.add_skill_name);

                AsyncTaskClass location_task=new AsyncTaskClass(obj);
                location_task.execute("skills");
                mMaterialDialog_add_skill= new MaterialDialog(User_Profile.this)
                        .setTitle("Edit Basic Details")
                        .setView(itemView_delete)
                        .setPositiveButton("ADD", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if(add_skill_name.getText().toString().equals(""))
                                    Toast.makeText(User_Profile.this,"Enter Valid Skill!",Toast.LENGTH_LONG).show();
                                else
                                {
                                    p_add_skill=new ProgressDialog(User_Profile.this);
                                    p_add_skill.show();
                                    p_add_skill.setCancelable(false);

                                    AsyncTaskClass add_edu= new AsyncTaskClass(obj);
                                    add_edu.execute("add_skill", user.get(0).getEmail(),add_skill_name.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                mMaterialDialog_add_skill.dismiss();
                            }
                        });
                mMaterialDialog_add_skill.show();
            }
        });

        edit_profile_basic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView = inflater.inflate(R.layout.edit_basic_profile, null);
                Button edit_user_dob_pick=(Button)itemView.findViewById(R.id.edit_user_dob_pick);
                edit_user_name = (EditText) itemView.findViewById(R.id.edit_user_name);
                edit_user_gender = (AutoCompleteTextView) itemView.findViewById(R.id.edit_user_gender);
                edit_user_dob = (EditText) itemView.findViewById(R.id.edit_user_dob);
                edit_user_address = (EditText) itemView.findViewById(R.id.edit_user_address);
                edit_user_zip = (EditText) itemView.findViewById(R.id.edit_user_zip);
                edit_user_city = (EditText) itemView.findViewById(R.id.edit_user_city);
                edit_user_state = (EditText) itemView.findViewById(R.id.edit_user_state);
                edit_user_country = (EditText) itemView.findViewById(R.id.edit_user_country);
                edit_user_contact = (EditText) itemView.findViewById(R.id.edit_user_contact);
                edit_user_educ = (AutoCompleteTextView) itemView.findViewById(R.id.edit_user_educ);
                edit_user_tot_exp = (EditText) itemView.findViewById(R.id.edit_user_tot_exp);
                edit_user_tot_exp.setText(user.get(0).getTotal_exp());
                edit_user_name.setText(user.get(0).getName());
                edit_user_gender.setText(user.get(0).getGender());
                edit_user_dob.setText(user.get(0).getDob());
                edit_user_address.setText(user.get(0).getAddress());
                edit_user_zip.setText(user.get(0).getZip());
                edit_user_city.setText(user.get(0).getCity());
                edit_user_state.setText(user.get(0).getState());
                edit_user_country.setText(user.get(0).getCountry());
                edit_user_contact.setText(user.get(0).getContact());
                edit_user_educ.setText(user.get(0).getEducation());

                edit_user_zip.setOnFocusChangeListener(new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (edit_user_zip.getText().toString().equals("") || edit_user_zip.getText().toString().equals(null))
                        {

                        }
                        else
                        {
                            String c = edit_user_zip.getText().toString();
                            String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + c + "&region=in";
                            GetLocation task = new GetLocation();
                            task.execute(url);
                        }
                    }
                });

                ArrayList<String> edu_list_str=new ArrayList<String>();

                edu_list_str.add("Graduation");
                edu_list_str.add("Post Graduation");
                edu_list_str.add("High School");
                edu_list_str.add("High Secondary");
                edu_list_str.add("Senior Secondary");

                ArrayAdapter<String> edu_adapter = new ArrayAdapter<String>(User_Profile.this, android.R.layout.simple_list_item_1, edu_list_str);

                edit_user_educ.setAdapter(edu_adapter);

                edit_user_dob_pick.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dpd = new DatePickerDialog(User_Profile.this,
                                new DatePickerDialog.OnDateSetListener()
                                {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                        // Display Selected date in textbox
                                        edit_user_dob.setText(dayOfMonth + "-"
                                                + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        dpd.show();
                    }
                });

                ArrayList<String> gender_list=new ArrayList<String>();
                gender_list.add("Male");
                gender_list.add("Female");
                ArrayAdapter<String> ad=new ArrayAdapter<String>(User_Profile.this,android.R.layout.simple_list_item_1,gender_list);
                edit_user_gender.setAdapter(ad);

                mMaterialDialog = new MaterialDialog(User_Profile.this)
                        .setTitle("Edit Basic Details")
                        .setView(itemView)
                        .setPositiveButton("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                p1 = new ProgressDialog(User_Profile.this);
                                p1.show();
                                p1.setCancelable(false);
                                AsyncTaskClass profile = new AsyncTaskClass(obj);
                                profile.execute("edit_profile", user.get(0).getEmail(), edit_user_name.getText().toString(), edit_user_gender.getText().toString(), edit_user_dob.getText().toString(), edit_user_address.getText().toString(), edit_user_zip.getText().toString(), edit_user_city.getText().toString(), edit_user_state.getText().toString(), edit_user_country.getText().toString(), edit_user_contact.getText().toString(), edit_user_educ.getText().toString(), edit_user_tot_exp.getText().toString());

                                mMaterialDialog.dismiss();
                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();

                            }
                        });

                mMaterialDialog.show();

            }
        });

        add_edu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_delete= inflater.inflate(R.layout.add_edu_dialog, null);

                final EditText add_edu_quali,add_edu_board,add_edu_passyr,add_edu_perc;

                add_edu_quali=(EditText)itemView_delete.findViewById(R.id.add_edu_quali);
                add_edu_board=(EditText)itemView_delete.findViewById(R.id.add_edu_board);
                add_edu_passyr=(EditText)itemView_delete.findViewById(R.id.add_edu_passyr);
                add_edu_perc=(EditText)itemView_delete.findViewById(R.id.add_edu_perc);


                mMaterialDialog_add_edu= new MaterialDialog(User_Profile.this)
                        .setTitle("Edit Basic Details")
                        .setView(itemView_delete)
                        .setPositiveButton("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                p_add_edu=new ProgressDialog(User_Profile.this);
                                p_add_edu.show();
                                p_add_edu.setCancelable(false);



                                AsyncTaskClass add_edu= new AsyncTaskClass(obj);
                                //user_email,edu_id,edit_class,edit_board,edit_year,edit_per
                                add_edu.execute("add_edu", user.get(0).getEmail(),add_edu_quali.getText().toString(),add_edu_board.getText().toString(),add_edu_passyr.getText().toString(),add_edu_perc.getText().toString());




                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog_add_edu.dismiss();

                            }
                        });

                mMaterialDialog_add_edu.show();

            }
        });

        delete_edu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String[] edu_id_delete = new String[1];
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.delete_edu_dialog, null);
                final Spinner delete_edu_select;
                delete_edu_select=(Spinner)itemView_select.findViewById(R.id.delete_edu_select);

                if(education.size()<=0)
                {
                    Toast.makeText(User_Profile.this,"Add Education First!",Toast.LENGTH_LONG).show();
                }
                else
                {

                    ArrayList<String> edu_delete = new ArrayList<String>();
                    edu_delete.add("Select");
                    for (int count = 0; count < education.size(); count++)
                    {
                        edu_delete.add(education.get(count).getQualification());
                        Log.e("Qualification=>", "" + education.get(count).getQualification());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(User_Profile.this, android.R.layout.simple_list_item_1, edu_delete);
                    delete_edu_select.setAdapter(adapter);


                    mMaterialDialog_delete_edu = new MaterialDialog(User_Profile.this)
                            .setTitle("Edit Basic Details")
                            .setView(itemView_select)
                            .setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    p_delete_edu = new ProgressDialog(User_Profile.this);
                                    p_delete_edu.show();
                                    p_delete_edu.setCancelable(false);
                                    edu_id_delete[0] = education.get(delete_edu_select.getSelectedItemPosition() - 1).getEdu_id();
                                    Log.e("selected", "" + (delete_edu_select.getSelectedItemPosition() - 1));
                                    Toast.makeText(User_Profile.this, "" + (delete_edu_select.getSelectedItemPosition() - 1), Toast.LENGTH_SHORT).show();
                                    p_delete_edu.dismiss();
                                    AsyncTaskClass delete_edu = new AsyncTaskClass(obj);
                                    //user_email,edu_id,edit_class,edit_board,edit_year,edit_per
                                    delete_edu.execute("delete_edu", user.get(0).getEmail(), edu_id_delete[0]);
                                }
                            })
                            .setNegativeButton("CANCEL", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog_delete_edu.dismiss();

                                }
                            });

                    mMaterialDialog_delete_edu.show();
                }

            }
        });

        edit_exp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String[] exp_id_edit = new String[1];
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.edit_exp_dialog, null);

                edit_exp_company=(TextView)itemView_select.findViewById(R.id.edit_exp_company);
                edit_exp_post=(TextView)itemView_select.findViewById(R.id.edit_exp_post);
                edit_exp_month=(TextView)itemView_select.findViewById(R.id.edit_exp_month);
                edit_exp_from=(TextView)itemView_select.findViewById(R.id.edit_exp_from);
                edit_exp_to=(TextView)itemView_select.findViewById(R.id.edit_exp_to);
                edit_exp_ind=(AutoCompleteTextView)itemView_select.findViewById(R.id.edit_exp_ind);
                edit_exp_year=(TextView)itemView_select.findViewById(R.id.edit_exp_year);
                edit_exp_current=(CheckBox)itemView_select.findViewById(R.id.edit_exp_current);

                edit_exp_from_btn=(Button)itemView_select.findViewById(R.id.edit_exp_from_btn);
                edit_exp_till_btn=(Button)itemView_select.findViewById(R.id.edit_exp_till_btn);

                edit_exp_select=(Spinner)itemView_select.findViewById(R.id.edit_exp_select);

                if(experience.size()<=0)
                {
                    Toast.makeText(User_Profile.this,"Add Experience First!",Toast.LENGTH_LONG).show();
                }
                else
                {

                    ArrayList<String> exp_edit = new ArrayList<String>();
                    exp_edit.add("Select");
                    for (int count = 0; count < experience.size(); count++)
                    {
                        exp_edit.add(experience.get(count).getCompany_name());
                        Log.e("Qualification=>", "" + experience.get(count).getCompany_name());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(User_Profile.this, android.R.layout.simple_list_item_1, exp_edit);
                    edit_exp_select.setAdapter(adapter);
                    edit_exp_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            if (education.size() <= 0)
                            {
                                Toast.makeText(User_Profile.this, "Add Education First!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Log.e("Qualfication selected=>", "" + education.get(0).getQualification());
                                if (position == 0)
                                {

                                }
                                else
                                {
                                    edit_exp_company.setText(experience.get(position - 1).getCompany_name());
                                    edit_exp_post.setText(experience.get(position - 1).getDesignation());
                                    edit_exp_month.setText(experience.get(position - 1).getExp_month());
                                    edit_exp_from.setText(experience.get(position - 1).getExp_from_month() + ", " + experience.get(position - 1).getExp_from_year());
                                    edit_exp_to.setText(experience.get(position - 1).getExp_to_month() + ", " + experience.get(position - 1).getExp_to_year());
                                    edit_exp_ind.setText(experience.get(position - 1).getIndustry());
                                    edit_exp_year.setText(experience.get(position - 1).getExp_year());

                                    if(experience.get(position-1).getCurrent_work().equals("yes"))
                                        edit_exp_current.setSelected(true);
                                    else
                                        edit_exp_current.setSelected(false);

                                    exp_id_edit[0] = experience.get(position - 1).getExp_id();
                                }
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    edit_exp_till_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);

                            // Launch Date Picker Dialog

                            DatePickerDialog dpd = new DatePickerDialog(User_Profile.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    // Display Selected date in textbox
                                    exp_to_year = "" + year;
                                    switch (monthOfYear + 1) {
                                        case 1: {
                                            exp_to_month = "January";
                                            break;
                                        }
                                        case 2: {
                                            exp_to_month = "February";
                                            break;
                                        }
                                        case 3: {
                                            exp_to_month = "March";
                                            break;
                                        }
                                        case 4: {
                                            exp_to_month = "April";
                                            break;
                                        }
                                        case 5: {
                                            exp_to_month = "May";
                                            break;
                                        }
                                        case 6: {
                                            exp_to_month = "June";
                                            break;
                                        }
                                        case 7: {
                                            exp_to_month = "July";
                                            break;
                                        }
                                        case 8: {
                                            exp_to_month = "August";
                                            break;
                                        }
                                        case 9: {
                                            exp_to_month = "September";
                                            break;
                                        }
                                        case 10: {
                                            exp_to_month = "October";
                                            break;
                                        }
                                        case 11: {
                                            exp_to_month = "November";
                                            break;
                                        }
                                        case 12: {
                                            exp_to_month = "December";
                                            break;
                                        }
                                    }
                                    edit_exp_to.setText(exp_to_month + ", " + exp_to_year);
                                }
                            }, mYear, mMonth, mDay);
                            dpd.show();
                        }
                    });

                    edit_exp_from_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);

                            // Launch Date Picker Dialog

                            DatePickerDialog dpd = new DatePickerDialog(User_Profile.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    // Display Selected date in textbox
                                    exp_from_year = "" + year;
                                    switch (monthOfYear + 1) {
                                        case 1: {
                                            exp_from_month = "January";
                                            break;
                                        }
                                        case 2: {
                                            exp_from_month = "February";
                                            break;
                                        }
                                        case 3: {
                                            exp_from_month = "March";
                                            break;
                                        }
                                        case 4: {
                                            exp_from_month = "April";
                                            break;
                                        }
                                        case 5: {
                                            exp_from_month = "May";
                                            break;
                                        }
                                        case 6: {
                                            exp_from_month = "June";
                                            break;
                                        }
                                        case 7: {
                                            exp_from_month = "July";
                                            break;
                                        }
                                        case 8: {
                                            exp_from_month = "August";
                                            break;
                                        }
                                        case 9: {
                                            exp_from_month = "September";
                                            break;
                                        }
                                        case 10: {
                                            exp_from_month = "October";
                                            break;
                                        }
                                        case 11: {
                                            exp_from_month = "November";
                                            break;
                                        }
                                        case 12: {
                                            exp_from_month = "December";
                                            break;
                                        }
                                    }
                                    edit_exp_from.setText(exp_from_month + ", " + exp_from_year);
                                }
                            }, mYear, mMonth, mDay);
                            dpd.show();
                        }
                    });

                    mMaterialDialog_exp_edit = new MaterialDialog(User_Profile.this)
                            .setTitle("Edit Basic Details")
                            .setView(itemView_select)
                            .setPositiveButton("OK", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    p_edit_exp = new ProgressDialog(User_Profile.this);
                                    p_edit_exp.show();
                                    p_edit_exp.setCancelable(false);

                                    String current_string="";
                                    if(edit_exp_current.isSelected())
                                        current_string="yes";
                                    else
                                        current_string="no";
                                    AsyncTaskClass edit_exp = new AsyncTaskClass(obj);
                                    edit_exp.execute("edit_exp", user.get(0).getEmail(), exp_id_edit[0], edit_exp_company.getText().toString(), edit_exp_post.getText().toString(), edit_exp_month.getText().toString()+" months", exp_from_month+" ", exp_from_year, exp_to_month+" ", exp_to_year, edit_exp_ind.getText().toString(), edit_exp_year.getText().toString()+" year",current_string);
                                }
                            })
                            .setNegativeButton("CANCEL", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    mMaterialDialog_exp_edit.dismiss();
                                }
                            });
                    mMaterialDialog_exp_edit.show();
                }
            }
        });

        add_exp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                field_flag=2;
                AsyncTaskClass ind_task=new AsyncTaskClass(obj);
                ind_task.execute("Field");
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.edit_exp_dialog, null);
                edit_exp_company=(TextView)itemView_select.findViewById(R.id.edit_exp_company);
                edit_exp_post=(TextView)itemView_select.findViewById(R.id.edit_exp_post);
                edit_exp_month=(TextView)itemView_select.findViewById(R.id.edit_exp_month);
                edit_exp_from=(TextView)itemView_select.findViewById(R.id.edit_exp_from);
                edit_exp_to=(TextView)itemView_select.findViewById(R.id.edit_exp_to);
                edit_exp_ind=(AutoCompleteTextView)itemView_select.findViewById(R.id.edit_exp_ind);
                edit_exp_year=(TextView)itemView_select.findViewById(R.id.edit_exp_year);
                edit_exp_current=(CheckBox)itemView_select.findViewById(R.id.edit_exp_current);

                edit_exp_from_btn=(Button)itemView_select.findViewById(R.id.edit_exp_from_btn);
                edit_exp_till_btn=(Button)itemView_select.findViewById(R.id.edit_exp_till_btn);

                edit_exp_select=(Spinner)itemView_select.findViewById(R.id.edit_exp_select);
                edit_exp_select.setVisibility(View.INVISIBLE);

                edit_exp_till_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dpd = new DatePickerDialog(User_Profile.this, new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                exp_to_year = "" + year;
                                switch (monthOfYear + 1)
                                {
                                    case 1:
                                    {
                                        exp_to_month = "January";
                                        break;
                                    }
                                    case 2:
                                    {
                                        exp_to_month = "February";
                                        break;
                                    }
                                    case 3:
                                    {
                                        exp_to_month = "March";
                                        break;
                                    }
                                    case 4:
                                    {
                                        exp_to_month = "April";
                                        break;
                                    }
                                    case 5:
                                    {
                                        exp_to_month = "May";
                                        break;
                                    }
                                    case 6:
                                    {
                                        exp_to_month = "June";
                                        break;
                                    }
                                    case 7:
                                    {
                                        exp_to_month = "July";
                                        break;
                                    }
                                    case 8:
                                    {
                                        exp_to_month = "August";
                                        break;
                                    }
                                    case 9:
                                    {
                                        exp_to_month = "September";
                                        break;
                                    }
                                    case 10:
                                    {
                                        exp_to_month = "October";
                                        break;
                                    }
                                    case 11:
                                    {
                                        exp_to_month = "November";
                                        break;
                                    }
                                    case 12:
                                    {
                                        exp_to_month = "December";
                                        break;
                                    }
                                }
                                edit_exp_to.setText(exp_to_month + ", " + exp_to_year);
                            }
                        }, mYear, mMonth, mDay);
                        dpd.show();
                    }
                });

                edit_exp_from_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dpd = new DatePickerDialog(User_Profile.this, new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                exp_from_year = "" + year;
                                switch (monthOfYear + 1) {
                                    case 1: {
                                        exp_from_month = "January";
                                        break;
                                    }
                                    case 2: {
                                        exp_from_month = "February";
                                        break;
                                    }
                                    case 3: {
                                        exp_from_month = "March";
                                        break;
                                    }
                                    case 4: {
                                        exp_from_month = "April";
                                        break;
                                    }
                                    case 5: {
                                        exp_from_month = "May";
                                        break;
                                    }
                                    case 6: {
                                        exp_from_month = "June";
                                        break;
                                    }
                                    case 7: {
                                        exp_from_month = "July";
                                        break;
                                    }
                                    case 8: {
                                        exp_from_month = "August";
                                        break;
                                    }
                                    case 9: {
                                        exp_from_month = "September";
                                        break;
                                    }
                                    case 10: {
                                        exp_from_month = "October";
                                        break;
                                    }
                                    case 11: {
                                        exp_from_month = "November";
                                        break;
                                    }
                                    case 12: {
                                        exp_from_month = "December";
                                        break;
                                    }
                                }
                                edit_exp_from.setText(exp_from_month + ", " + exp_from_year);
                            }
                        }, mYear, mMonth, mDay);
                        dpd.show();
                    }
                });


                mMaterialDialog_exp_add = new MaterialDialog(User_Profile.this)
                        .setTitle("Edit Basic Details")
                        .setView(itemView_select)
                        .setPositiveButton("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                p_add_exp=new ProgressDialog(User_Profile.this);
                                p_add_exp.show();
                                p_add_exp.setCancelable(false);

                                String current_string="";
                                if(edit_exp_current.isSelected())
                                    current_string="yes";
                                else
                                    current_string="no";

                                AsyncTaskClass add_exp= new AsyncTaskClass(obj);
                                add_exp.execute("add_exp", user.get(0).getEmail(),edit_exp_company.getText().toString(),edit_exp_post.getText().toString(),edit_exp_month.getText().toString()+" months",exp_from_month+" ",exp_from_year,exp_to_month+" ",exp_to_year,edit_exp_ind.getText().toString(),edit_exp_year.getText().toString()+" year",current_string);
                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog_exp_add.dismiss();
                            }
                        });
                mMaterialDialog_exp_add.show();
            }
        });

        edit_edu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String[] edu_id_edit = new String[1];
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.edit_edu_dialog, null);

                edit_edu_select=(Spinner)itemView_select.findViewById(R.id.edit_edu_select);
                edit_edu_quali=(EditText)itemView_select.findViewById(R.id.edit_edu_quali);
                edit_edu_board=(EditText)itemView_select.findViewById(R.id.edit_edu_board);
                edit_edu_passyr=(EditText)itemView_select.findViewById(R.id.edit_edu_passyr);
                edit_edu_perc=(EditText)itemView_select.findViewById(R.id.edit_edu_perc);

                if(education.size()<=0)
                {
                    Toast.makeText(User_Profile.this,"Add Education First!",Toast.LENGTH_LONG).show();
                }
                else
                {

                    ArrayList<String> class_edit = new ArrayList<String>();
                    class_edit.add("Select");
                    for (int count = 0; count < education.size(); count++)
                    {
                        class_edit.add(education.get(count).getQualification());
                        Log.e("Qualification=>", "" + education.get(count).getQualification());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(User_Profile.this, android.R.layout.simple_list_item_1, class_edit);
                    edit_edu_select.setAdapter(adapter);
                    edit_edu_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            Log.e("Qualfication selected=>", "" + education.get(0).getQualification());
                            if (position == 0)
                            {

                            }
                            else
                            {
                                edit_edu_quali.setText(education.get(position - 1).getQualification());
                                edit_edu_board.setText(education.get(position - 1).getBoard());
                                edit_edu_passyr.setText(education.get(position - 1).getPassing_year());
                                edit_edu_perc.setText(education.get(position - 1).getPercentage());
                                edu_id_edit[0] = education.get(position - 1).getEdu_id();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent)
                        {

                        }
                    });

//user,name,gender,dob,address,zip,city,state,country,contact,edu

                    mMaterialDialog_select_class = new MaterialDialog(User_Profile.this)
                            .setTitle("Edit Basic Details")
                            .setView(itemView_select)
                            .setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    p_edit_edu = new ProgressDialog(User_Profile.this);
                                    p_edit_edu.show();
                                    p_edit_edu.setCancelable(false);

                                    AsyncTaskClass edit_edu = new AsyncTaskClass(obj);
                                    //user_email,edu_id,edit_class,edit_board,edit_year,edit_per
                                    edit_edu.execute("edit_edu", user.get(0).getEmail(), edu_id_edit[0], edit_edu_quali.getText().toString(), edit_edu_board.getText().toString(), edit_edu_passyr.getText().toString(), edit_edu_perc.getText().toString());


                                }
                            })
                            .setNegativeButton("CANCEL", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog_select_class.dismiss();

                                }
                            });

                    mMaterialDialog_select_class.show();
                }

            }
        });

        edit_work.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View itemView_select = inflater.inflate(R.layout.edit_user_preference, null);

                edit_user_pref_loc=(AutoCompleteTextView)itemView_select.findViewById(R.id.edit_user_pref_loc);
                edit_user_pref_field=(AutoCompleteTextView)itemView_select.findViewById(R.id.edit_user_pref_field);
                edit_user_pref_loc.setText(user_p_pref_loc.getText().toString());
                edit_user_pref_field.setText(user_p_pref_ind.getText().toString());
                p_edit_work=new ProgressDialog(User_Profile.this);
                p_edit_work.show();

                field_flag=1;

                AsyncTaskClass ind_task=new AsyncTaskClass(obj);
                ind_task.execute("Field");

                AsyncTaskClass loc_task=new AsyncTaskClass(obj);
                loc_task.execute("Location");

                //user,name,gender,dob,address,zip,city,state,country,contact,edu

                mMaterialDialog_edit_work = new MaterialDialog(User_Profile.this)
                        .setTitle("Edit Basic Details")
                        .setView(itemView_select)
                        .setPositiveButton("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                p_edit_work1=new ProgressDialog(User_Profile.this);
                                p_edit_work1.show();
                                p_edit_work1.setCancelable(false);
                                p_cert=new ProgressDialog(User_Profile.this);
                                p_cert.show();
                                AsyncTaskClass edit_edu= new AsyncTaskClass(obj);
                                //user_email,edu_id,edit_class,edit_board,edit_year,edit_per
                                edit_edu.execute("edit_work", user.get(0).getEmail(),edit_user_pref_loc.getText().toString(),edit_user_pref_field.getText().toString());




                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog_edit_work.dismiss();

                            }
                        });

                mMaterialDialog_edit_work.show();


            }
        });



        edit_resume.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getfile(v);
                file_flag=2;

            }
        });





    }


    public void getfile(View view){
        Intent intent1 = new Intent(this, com.app.finalcode.getjob.getjob.utility.FileChooser.class);
        startActivityForResult(intent1, REQUEST_PATH);
    }





    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
                curFileName = data.getStringExtra("GetFileName");
                curFilePath=data.getStringExtra("GetPath");
                if(file_flag==1) {
                    cert_list.add(curFileName);

                    LayoutInflater inflater = (LayoutInflater) User_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View itemView_select = inflater.inflate(R.layout.alert_certi, null);

                    ImageView image = (ImageView) itemView_select.findViewById(R.id.certi_image);
                    final EditText cert_title = (EditText) itemView_select.findViewById(R.id.cert_title);

                    bmp = BitmapFactory.decodeFile(curFilePath + "/" + curFileName);
                    image.setImageBitmap(bmp);

                    mMaterialDialog_edit_work = new MaterialDialog(User_Profile.this)
                            .setTitle("Edit Basic Details")
                            .setView(itemView_select)
                            .setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    certi_title = cert_title.getText().toString();
                                    p_cert = new ProgressDialog(User_Profile.this);
                                    p_cert.show();
                                    HttpAsyncTask upload_task = new HttpAsyncTask(bmp, curFileName);
                                    upload_task.execute();


                                }
                            })
                            .setNegativeButton("CANCEL", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog_edit_work.dismiss();

                                }
                            });

                    mMaterialDialog_edit_work.show();
                }
                if(file_flag==2)
                {
                    complete_resume_name=curFilePath + "/" + curFileName;
                    p_cert = new ProgressDialog(User_Profile.this);
                    p_cert.show();
                    FileInputStream fstrm = null;
                    try {
                        fstrm = new FileInputStream(complete_resume_name);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // Set your server page url (and the file title/description)
                    HttpFileUpload hfu = new HttpFileUpload("http://www.jobmafiaa.com/index.php/Main_api/user_register", "my file title", "my file description");
                    hfu.Send_Now(fstrm);
                }

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

        void Send_Now(FileInputStream fStream)
        {
            fileInputStream = fStream;
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

            ProgressDialog dialog=new ProgressDialog(User_Profile.this);
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
                        multipart.addFormField("resumename", u.getResume());
                        multipart.addFormField("user_email", l.getEmail());
                        List<String> response = multipart.finish();
                        Log.e("response",""+response);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

            dialog.dismiss();
        }

        public void run() {
            // TODO Auto-generated method stub
        }
    }

    public class MultipartUtility {
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
        public void addFilePart(String fieldName, File uploadFile)
                throws IOException {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
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
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }

            return response;
        }
    }




    private class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
        Bitmap image;
        String name;

        long totalSize = 0;
        @Override
        protected void onPreExecute()
        {
            mMaterialDialog_edit_work.dismiss();
            super.onPreExecute();

            //Toast.makeText(User_Profile.this,"Sending details of company="+comp_name.getText().toString(),Toast.LENGTH_LONG).show();

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
            String encodedimage= Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
            ArrayList<NameValuePair> entity = new ArrayList<>();
            entity.add(new BasicNameValuePair("user_email", user.get(0).getEmail()));
            entity.add(new BasicNameValuePair("certificate", certi_title));
            entity.add(new BasicNameValuePair("name", curFileName));

            entity.add(new BasicNameValuePair("image",encodedimage));

            HttpParams httpRequestParams=getHttpRequestParams();
            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/certificates");

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
            //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
            Log.e("response=",""+result);
            p_cert.dismiss();
            //edittext.setText(result);
        }
    }

    private HttpParams getHttpRequestParams()
    {
        HttpParams httpRequestParams=new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams,1000*30);
        return httpRequestParams;
    }

    @Override
    public void onFetchComplete_resume_check(int result1) {

    }

    @Override
    public void onFetchComplete_fav(ArrayList<Fav_Job> data)
    {

    }

    @Override
    public void onFetchComplete_profile_edit(String s)
    {
        p1.dismiss();
        p.show();
        user_p_name.setText(edit_user_name.getText().toString());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());
        //Log.e("after basic edit=>",""+user_p_email.getText().toString());

    }

    @Override
    public void onFetchComplete_skill_add(String s)
    {
        mMaterialDialog_add_skill.dismiss();
        p_add_skill.dismiss();
        p.show();
        //user_p_name.setText(edit_user_name.getText().toString());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());

    }

    @Override
    public void onFetchComplete_skill(ArrayList<String> s)
    {
        skill=s;
        Log.e("dismisses progress", "p_fetch complete skill");

        ArrayAdapter<String> skill_adapter=new ArrayAdapter<String>(User_Profile.this,android.R.layout.simple_list_item_1,skill);
        add_skill_name.setAdapter(skill_adapter);

    }

    @Override
    public void onFetchComplete_edit_exp(String str)
    {
        mMaterialDialog_exp_edit.dismiss();
        p_edit_exp.dismiss();

        p.show();
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());
    }

    @Override
    public void onFetchComplete_skill_delete(String s)
    {
        mMaterialDialog_delete_skill.dismiss();
        p_delete_skill.dismiss();
        p.show();
        //user_p_name.setText(edit_user_name.getText().toString());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());
    }

    @Override
    public void onFetchComplete_edit_work(String s)
    {
        mMaterialDialog_edit_work.dismiss();
        p_edit_work.dismiss();
        p_edit_work1.dismiss();
        p.show();
        //user_p_name.setText(edit_user_name.getText().toString());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());

    }

    @Override
    public void onFetchComplete_delete_exp(String result)
    {
        mMaterialDialog_delete_exp.dismiss();
        p_delete_exp.dismiss();
        p.show();
        //user_p_name.setText(edit_user_name.getText().toString());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());

    }

    @Override
    public void onFetchComplete_add_exp(String s)
    {
        mMaterialDialog_exp_add.dismiss();
        p_add_exp.dismiss();
        p.show();
        //user_p_name.setText(edit_user_name.getText().toString());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());

    }

    @Override
    public void onFetchComplete_adds(ArrayList<Adds> ads) {

    }

    @Override
    public void onFetchComplete_delete_certi(String s)
    {
        p_delete_skill.dismiss();
        mMaterialDialog_delete_skill.dismiss();

        p.show();
        //user_p_name.setText(edit_user_name.getText().toString());
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());
    }

    @Override
    public void onFetchComplete_companies(ArrayList<String> companies) {

    }

    @Override
    public void onFetchComplete_company_details(Company s) {

    }

    @Override
    public void onFetchComplete_location(ArrayList<String> al)
    {
        locations=al;
        p_edit_work.dismiss();
        ArrayAdapter<String> loc_adapter=new ArrayAdapter<String>(User_Profile.this,android.R.layout.simple_list_item_1,locations);

        edit_user_pref_loc.setAdapter(loc_adapter);
    }

    @Override
    public void onFetchComplete_add_post(String s) {

    }

    @Override
    public void onFetchComplete_jobs_posted(ArrayList<Job_Post_class>j) {

    }

    @Override
    public void onFetchComplete_field(ArrayList<String> al)
    {
//        p_edit_work.dismiss();
        Log.e("list=>", "" + al);
        fields=al;
        ArrayAdapter<String> field_adapter=new ArrayAdapter<String>(User_Profile.this,android.R.layout.simple_list_item_1,fields);
        if(field_flag==1)
            edit_user_pref_field.setAdapter(field_adapter);
        else
            edit_exp_ind.setAdapter(field_adapter);
       // edit_exp_ind.setAdapter(loc_adapter);

    }

    @Override
    public void onFetchComplete_user_applied(ArrayList<User_applied> j) {

    }


    @Override
    public void onFetchComplete_edu_edit(String s)
    {
        mMaterialDialog_select_class.dismiss();
        p_edit_edu.dismiss();
        Log.e("complete edit edu=>", "" + s);
        p.show();
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());
    }

    @Override
    public void onFetchComplete_edu_delete(String s)
    {
        mMaterialDialog_delete_edu.dismiss();
        p_delete_edu.dismiss();
        Log.e("edu delete fetch comp=>",""+s);
        p.show();
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());

    }

    @Override
    public void onFetchComplete_edu_add(String s)
    {
        p_add_edu.dismiss();
        mMaterialDialog_add_edu.dismiss();

        Log.e("edu add fetch comp=>",""+s);
        p.show();
        AsyncTaskClass profile_task=new AsyncTaskClass(this);
        profile_task.execute("user_details", l.getEmail());
    }

    @Override
    public void onFetchComplete_apply(ArrayList<User_Apply> data)
    {

    }

    @Override
    public void onFetchComplete_profile(ArrayList<User> u, ArrayList<Experience> ex, ArrayList<Education> ed, ArrayList<Skills> sk, ArrayList<Certificate> ct)
    {
        p.dismiss();
        user=u;
        education=ed;
        certificate=ct;
        experience=ex;
        skills=sk;
        user_p_name.setText(u.get(0).getName());
        int current_flag=-1;



        if(ex.size()>0)
        {
            for (int count = 0; count < ex.size(); count++)
            {
                if (ex.get(count).getCurrent_work().equals("yes"))
                {
                    Log.e("in for and if for=>", "" + ex.get(count).getDesignation());
                    user_p_work_stat.setText(ex.get(count).getDesignation());
                    break;
                }
                else
                    current_flag=0;
            }
            if(current_flag==0)
                user_p_work_stat.setText(ex.get(ex.size()-1).getDesignation());
        }
        else if(education.size()>0)
        {
            user_p_work_stat.setText(ed.get(ed.size()-1).getQualification());
        }
        else
            user_p_work_stat.setText("Fresher");

        String t=u.get(0).getCity()+", "+u.get(0).getState();
        user_p_city.setText(t);
        user_p_exp.setText(u.get(0).getTotal_exp());//"null");//u.get(0).getTotal_exp());

        user_p_email.setText(u.get(0).getEmail());
        user_p_phone.setText(u.get(0).getContact());

        String skills="";
        //Log.e("Skill size=>",""+sk.size());
        for(int count=0;count<sk.size();count++)
        {
            skills=skills+sk.get(count).getSkill_name();
            if(count!=sk.size()-1)
            {
                skills=skills+", ";
            }
        }
        //Log.e("skill size=>",""+sk.size());

        if(skills.equals("")||sk.size()==0)
            user_p_skill.setText("No Skills Added");
        else
            user_p_skill.setText(skills);

        exp_list_str.clear();
        if(experience.size()==0)
        {
            exp_list_str.add("Add Experiences");
            ArrayAdapter<String> ad=new ArrayAdapter<String>(User_Profile.this,android.R.layout.simple_list_item_1,exp_list_str);
            exp_list.setAdapter(ad);
            setListViewHeightBasedOnChildren(exp_list);
        }
        else
        {
            ExpListAdapter exp_adapter=new ExpListAdapter(User_Profile.this,R.layout.exp_list_item,experience);
            exp_list.setAdapter(exp_adapter);
            setListViewHeightBasedOnChildren(exp_list);
        }
        edu_list_str.clear();
        if(education.size()==0)
        {
            edu_list_str.add("Add Education");
            ArrayAdapter<String > ad=new ArrayAdapter<String>(User_Profile.this,android.R.layout.simple_list_item_1,edu_list_str);
            edu_list.setAdapter(ad);
            setListViewHeightBasedOnChildren(edu_list);
        }
        else
        {
            EduListAdapter edu_adapter = new EduListAdapter(User_Profile.this, R.layout.edu_list_item, education);
            edu_list.setAdapter(edu_adapter);
            setListViewHeightBasedOnChildren(edu_list);
        }

        if(u.get(0).getPref_field().equals(""))
            user_p_pref_ind.setText("Add preffered Industry");
        else
            user_p_pref_ind.setText(u.get(0).getPref_field());
        if(u.get(0).getPref_location().equals(""))
            user_p_pref_loc.setText("Add Preferred Location");
        else
            user_p_pref_loc.setText(u.get(0).getPref_location());
        if(u.get(0).getDob().equals("0000-00-00"))
            user_p_dob.setText("Add Birthday");
        else
            user_p_dob.setText(u.get(0).getDob());
        if(u.get(0).getGender().equals(""))
            user_p_gender.setText("Add Gender");
        else
            user_p_gender.setText(u.get(0).getGender());
        if(u.get(0).getCity().equals("")||u.get(0).getState().equals(""))
            user_p_hometown.setText("Add Home Town");
        else
            user_p_hometown.setText(u.get(0).getCity()+", "+u.get(0).getState());
        if(u.get(0).getZip().equals(""))
            user_p_pin.setText("Add Pin");
        else
            user_p_pin.setText(u.get(0).getZip());
        if (u.get(0).getAddress().equals(""))
            user_p_address.setText("Add Address");
        else
            user_p_address.setText(u.get(0).getAddress());
        user_p_resume.setText(u.get(0).getResume());
        cert_list.clear();
        if(certificate.size()==0)
        {
            cert_list.add("Add Certificates");
        }
        else
            for(int count=0;count<certificate.size();count++)
                cert_list.add(certificate.get(count).getName());
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(User_Profile.this,android.R.layout.simple_list_item_1,cert_list);
        user_p_cert_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(user_p_cert_list);



    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onFetchComplete_job(ArrayList<Job_Post_class> data)
    {

    }

    @Override
    public void onFetchFailure(String msg)
    {

    }

    @Override
    public void onFetchComplete_all_seekers(ArrayList<User> result) {

    }

    public class ExpListAdapter extends ArrayAdapter<Experience>
    {
        ArrayList<Experience> exp_arr_list=new ArrayList<Experience>();
        Context context;

        public ExpListAdapter(Context context1,int resource, ArrayList<Experience> fl)
        {
            super(context1, resource, fl);
            context=context1;
            exp_arr_list=fl;
        }

        public View getView(final int position,View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View itemView=inflater.inflate(R.layout.exp_list_item, null);

            TextView design=(TextView)itemView.findViewById(R.id.user_p_designation);
            TextView comp_name=(TextView)itemView.findViewById(R.id.user_p_company);
            TextView to_from=(TextView)itemView.findViewById(R.id.user_p_to_from);
            TextView industry=(TextView)itemView.findViewById(R.id.user_p_industry);

            design.setText(exp_arr_list.get(position).getDesignation());
            comp_name.setText(exp_arr_list.get(position).getCompany_name());
            String t=exp_arr_list.get(position).getExp_from_month()+","+exp_arr_list.get(position).getExp_from_year()+"-"+exp_arr_list.get(position).getExp_to_month()+","+exp_arr_list.get(position).getExp_to_year()+" ("+exp_arr_list.get(position).getExp_year()+" "+exp_arr_list.get(position).getExp_month()+")";
            to_from.setText(t);
            industry.setText(exp_arr_list.get(position).getIndustry());


            return itemView;
        }
    }

    public class EduListAdapter extends ArrayAdapter<Education>
    {
        ArrayList<Education> edu_arr_list=new ArrayList<Education>();
        Context context;

        public EduListAdapter(Context context1,int resource, ArrayList<Education> fl)
        {
            super(context1, resource, fl);
            context=context1;
            edu_arr_list=fl;
        }

        public View getView(final int position,View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View itemView=inflater.inflate(R.layout.edu_list_item, null);

            TextView user_p_qualification=(TextView)itemView.findViewById(R.id.user_p_qualification);
            TextView user_p_institute=(TextView)itemView.findViewById(R.id.user_p_institute);
            TextView user_p_passingyr=(TextView)itemView.findViewById(R.id.user_p_passingyr);
            TextView user_p_perc=(TextView)itemView.findViewById(R.id.user_p_percentage);

            user_p_qualification.setText(edu_arr_list.get(position).getQualification());
            user_p_institute.setText(edu_arr_list.get(position).getBoard());
            //String t=edu_arr_list.get(position).getExp_from_month()+","+exp_arr_list.get(position).getExp_from_year()+"-"+exp_arr_list.get(position).getExp_to_month()+","+exp_arr_list.get(position).getExp_to_year()+" ("+exp_arr_list.get(position).getExp_year()+" "+exp_arr_list.get(position).getExp_month()+")";
            user_p_passingyr.setText(edu_arr_list.get(position).getPassing_year());
            user_p_perc.setText(edu_arr_list.get(position).getPercentage());

            return itemView;
        }
    }

    class GetLocation extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            edit_user_city.setText("");
            edit_user_state.setText("");
            edit_user_country.setText("");
            pDialog = new ProgressDialog(User_Profile.this);
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
                        edit_user_city.setText("" + obj2.getString("long_name"));
                    }
                    else if (temp.equals("administrative_area_level_1"))
                    {
                        edit_user_state.setText("" + obj2.getString("long_name"));
                    }
                    else if (temp.equals("country"))
                    {
                        edit_user_country.setText("" + obj2.getString("long_name"));
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

}
