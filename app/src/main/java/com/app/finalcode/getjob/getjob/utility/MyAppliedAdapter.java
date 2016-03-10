package com.app.finalcode.getjob.getjob.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.finalcode.getjob.getjob.R;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
import com.app.finalcode.getjob.getjob.classes.User_applied;

import java.util.ArrayList;
import java.util.List;

public class MyAppliedAdapter extends ArrayAdapter<String>
{
    ListView job_applied_list;
    ArrayList<User_applied> t;
    Context context;
    ArrayList<String> j=new ArrayList<>();
    MyUserAdapter adapter;
    List<User_applied> list;
    LoginClass l;
    public MyAppliedAdapter(Context context1, int resource,ArrayList<String> jl, List<User_applied> objects, LoginClass l1)
    {
        super(context1, resource,jl);
        context=context1;
        list=objects;
        l=l1;
        j=jl;
    }
    //override getViewMethod
    public View getView(final int position,View convertView, ViewGroup parent)
    {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.user_applied_job_item, null);

        TextView job_title=(TextView)itemView.findViewById(R.id.job_title);
        job_applied_list=(ListView)itemView.findViewById(R.id.job_applied_list);
        job_title.setText(j.get(position));

        t=new ArrayList<>();
        for (int count = 0; count < list.size(); count++)
        {

            if (list.get(count).getJob_title().equals(j.get(position)))
            {
                t.add(list.get(count));
            }
        }
        adapter=new MyUserAdapter(getContext(),R.layout.user_applied_item,t,l,job_applied_list);

        job_applied_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(job_applied_list);
        return itemView;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}