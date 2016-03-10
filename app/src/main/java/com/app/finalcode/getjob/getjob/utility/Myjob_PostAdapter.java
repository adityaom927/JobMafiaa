package com.app.finalcode.getjob.getjob.utility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finalcode.getjob.getjob.R;
import com.app.finalcode.getjob.getjob.classes.Fav_Job;
import com.app.finalcode.getjob.getjob.classes.Job_Post_class;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
import com.app.finalcode.getjob.getjob.classes.User_Apply;

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

/**
 * Created by we on 22/12/15.
 */
public class Myjob_PostAdapter extends ArrayAdapter<Job_Post_class>
{
    private int lastPosition = -1;

    List<Job_Post_class> list;
    ArrayList<Fav_Job> fav_list=new ArrayList<Fav_Job>();
    ArrayList<User_Apply> user_apply=new ArrayList<>();
    Context context;
    LoginClass l1;
     public ArrayList<Fav_Job> fav_job = new ArrayList<Fav_Job>();

    public Myjob_PostAdapter(Context context1,int resource, List<Job_Post_class> objects,LoginClass l,ArrayList<Fav_Job> fl,ArrayList<User_Apply> ua)
    {
        super(context1, resource, objects);
        context=context1;
        l1=l;
        list=objects;
        fav_list=fl;
        user_apply=ua;
        //Log.e("got in adapter", "" + list);

    }



    //override getViewMethod
    public View getView(final int position,View convertView, ViewGroup parent)
    {

        final int[] fav_flag = new int[1];
        fav_flag[0]=0;

        //load item view xml and covert in View object
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemView=inflater.inflate(R.layout.jobs_item, null);

        //show user name in textivew of itemView
        //holder = new ViewHolder();
        int loader = R.drawable.mcd_logo;
        TextView Name=(TextView)itemView.findViewById(R.id.comp_name);
        ImageView comp_logo=(ImageView)itemView.findViewById(R.id.comp_logo);
        TextView skill=(TextView)itemView.findViewById(R.id.comp_skill);
        TextView exp=(TextView)itemView.findViewById(R.id.comp_exp);
        TextView loc=(TextView)itemView.findViewById(R.id.comp_loc);
        final ImageView fav_comp=(ImageView)itemView.findViewById(R.id.fav_comp);
        final ImageView apply_status=(ImageView)itemView.findViewById(R.id.apply_status);
/*
        helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
*/

        if (user_apply == null)
        {
            apply_status.setVisibility(View.INVISIBLE);
        }
        else
        {
            if (user_apply.size() <= 0)
            {
                apply_status.setVisibility(View.INVISIBLE);
            }
            if(check_apply(list.get(position).getJob_id()))
            {
                apply_status.setVisibility(View.VISIBLE);
            }
            else
            {
                apply_status.setVisibility(View.INVISIBLE);
            }
        }

        if(l1==null||l1.getType().equals(""))
        {
            fav_comp.setVisibility(View.INVISIBLE);
            apply_status.setVisibility(View.INVISIBLE);
        }
        else
        {
            //if(1)//helper.get_job_fav(db,j,l1))

            if(check_fav(list.get(position).getJob_id()))
            {
                fav_comp.setImageResource(R.drawable.christmasstar26);
                fav_flag[0] =1;
            }
            else
            {
                fav_comp.setImageResource(R.drawable.star26);
                fav_flag[0] =0;
            }
            //(fav_comp.getId())
//===========================================================================================================================
            fav_comp.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.e("in listener","");
                    if (fav_flag[0] == 0)
                    {
                        Log.e("in listener","");
                        Add_Fav_Task fav_task=new Add_Fav_Task();
                        fav_task.execute(list.get(position).getJob_id(), l1.getEmail());
                        fav_comp.setImageResource(R.drawable.christmasstar26);
                        fav_flag[0] = 1;

                    }
                    else
                    {
                        Log.e("in listener","");
                        //helper.reset_job_fav(db, j, l1))
                        Del_Fav_Task del_fav_task=new Del_Fav_Task();
                        del_fav_task.execute(list.get(position).getJob_id(), l1.getEmail());
                        fav_comp.setImageResource(R.drawable.star26);
                        fav_flag[0] = 0;

                    }
                }
            });


            //(fav_comp.getId())
//===========================================================================================================================


        }

        //itemView.setElevation(10);
        String t=list.get(position).getJob_title();
        t=t.substring(0, 1).toUpperCase() + t.substring(1);
        Name.setText(t);
        skill.setText(list.get(position).getSkill_required());
        exp.setText(list.get(position).getExperience());
        loc.setText(list.get(position).getJob_location());
        //new DisplayImageFromURL(comp_logo).execute("http://jobmafiaa.com//assets/uploads/"+list.get(position).c.getCompany_logo());
        //comp_logo.setImageBitmap(list.get(position).c.getBm());
        String image_url = "http://jobmafiaa.com//assets/uploads/"+list.get(position).c.getCompany_logo();
        ImageLoader imgLoader = new ImageLoader(context);
        imgLoader.DisplayImage(image_url, loader, comp_logo);
        //apply_status.setVisibility(View.INVISIBLE);

        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        itemView.startAnimation(animation);
        lastPosition = position;


        return itemView;
    }

    public boolean check_fav(String jid)
    {
        if(fav_list==null)
        {
            return false;
        }
        else
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

    }

    public boolean check_apply(String jid)
    {

        for(int count=0;count<user_apply.size();count++ )
        {
            if(user_apply.get(count).j.getJob_id().equals(jid))
            {
                return true;
            }
        }
        return false;
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