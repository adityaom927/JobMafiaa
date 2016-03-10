package com.app.finalcode.getjob.getjob.utility;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyUserAdapter extends ArrayAdapter<User_applied> implements FetchDataListener
{
    View v;
    String path = null;
            //;
    FetchDataListener listen=this;
    Context context;
    int count_flag=-1;
    File folder;
    int resume_flag=-1;
    User_applied u;
    ProgressDialog p;
    List<User_applied> list;
    LoginClass l;
    ListView job_applied_list_org;
    public MyUserAdapter(Context context1,int resource, List<User_applied> objects,LoginClass l1,ListView job_applied_list)
    {
        super(context1, resource, objects);
        context=context1;
        list=objects;
        l=l1;
        job_applied_list_org=job_applied_list;
    }
    //override getViewMethod
    public View getView(final int position,View convertView, ViewGroup parent)
    {
        u=list.get(position);

        Log.e("user position:",""+position);
        Log.e("for=>",""+u.getEmail());
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.user_applied_item, null);
        v=itemView;
        TextView user_name=(TextView)itemView.findViewById(R.id.user_name);
        TextView user_skills=(TextView)itemView.findViewById(R.id.user_skills);
        TextView user_exp=(TextView)itemView.findViewById(R.id.user_exp);
        TextView user_edu=(TextView)itemView.findViewById(R.id.user_edu);

        LinearLayout dnld_rsm=(LinearLayout)itemView.findViewById(R.id.dnld_rsm);



        user_name.setText(u.getName());
        user_skills.setText(u.getSkills());
        user_exp.setText(u.getExperience());
        user_edu.setText(u.getEducation());



        dnld_rsm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                p = new ProgressDialog(getContext());
                p.show();
                p.setCancelable(false);
                p.setMessage("checking for Download..");
                Log.e("calling resume", "AsyncTask");
                Log.e("sending email:", "" + l.getEmail());
                File file1[] = new File[0];
                String path1 = Environment.getExternalStorageDirectory().toString() + "/Job_Mafiaa";
                folder = new File(Environment.getExternalStorageDirectory().toString() + "/Job_Mafiaa");
                if (!folder.exists())
                {
                    folder.mkdir();
                }
                else
                {
                    File f = new File(path1);
                    file1 = f.listFiles();
                    Log.d("Files", "Size: " + file1.length);

                    for (int i = 0; i < file1.length; i++)
                    {
                        Log.e("check:", "in for");
                        if (u.getResume().equals(file1[i].getName()))
                        {
                            Log.e("check:", "in if");
                            path = file1[i].getPath().toString();
                            count_flag = 1;
                            break;
                        }
                        Log.d("Files", "FileName:" + file1[i].getName());
                    }
                }
                if (count_flag == 1)
                {
                    Log.e("status:", "Not Downloading");
                    File file4 = new File(path);
                    Uri path2 = Uri.fromFile(file4);
                    Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                    pdfIntent.setDataAndType(path2, "application/pdf");
                    pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try
                    {
                        p.dismiss();
                        context.startActivity(pdfIntent);
                    }
                    catch (ActivityNotFoundException e)
                    {

                    }
                }
                else
                {
                    AsyncTaskClass task1 = new AsyncTaskClass(listen);
                    task1.execute("resume_check", l.getEmail());
                }
            }
        });
        return itemView;
    }



    @Override
    public void onFetchComplete_resume_check(int result1)
    {
        Log.e("result from adapter", "" + result1);
        if(result1<10)
        {
            resume_flag=1;
            ResumeTask task=new ResumeTask();
            task.execute();
        }
        else
        {
            p.dismiss();
            Toast.makeText(getContext(),"Limit Of Downloading Resume reached! kindly pay, in-order to use further services!!",Toast.LENGTH_LONG).show();
        }


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


    class ResumeTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            final String file_url = "http://jobmafiaa.com//assets/uploads/" + u.getResume();
            final String ResumeName = u.getResume();
            System.out.println("image " + file_url);
            Log.e("status:","Downloading now");
            try
            {
                call();
                URL url = null;
                try
                {
                    url = new URL(file_url);
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }

                //create the new connection
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //set up some things on the connection
                try {
                    urlConnection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                urlConnection.setDoOutput(true);

                //and connect!
                try {
                    urlConnection.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //set the path where we want to save the file
                //in this case, going to save it on the root directory of the
                //sd card.


                if (!folder.exists())
                {
                    folder.mkdir();
                }
                //create a new file, specifying the path, and the filename
                //which we want to save the file as.
                File file = new File(folder, ResumeName);


                //this will be used to write the downloaded data into the file we created
                FileOutputStream fileOutput = new FileOutputStream(file);

                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();

                //this is the total size of the file
                int totalSize = urlConnection.getContentLength();
                //variable to store total downloaded bytes
                int downloadedSize = 0;

                //create a buffer...
                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer

                //now, read through the input buffer and write the contents to the file
                while ((bufferLength = inputStream.read(buffer)) > 0)
                {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                    //this is where you would do something to report the prgress, like this maybe
                    //updateProgress(downloadedSize, totalSize);

                }
                path = file.getPath().toString();
                System.out.println("image " + path);


                //close the output stream when done
                fileOutput.close();




            } catch (Exception e) {
                e.printStackTrace();
            }
            return path;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            p.dismiss();
            Log.e("opening file:", "file=>" + s);
            File file4=new File(s);
            Uri path2 = Uri.fromFile(file4);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path2, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try
            {
                context.startActivity(pdfIntent);
            }
            catch (ActivityNotFoundException e)
            {

            }
        }
    }

    void call()
    {
        AsyncTaskClass t=new AsyncTaskClass(listen);
        t.execute("resume_count_update",l.getEmail(),u.getEmail());
    }
}