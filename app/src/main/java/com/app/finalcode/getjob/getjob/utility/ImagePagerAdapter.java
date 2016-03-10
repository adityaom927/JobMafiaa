package com.app.finalcode.getjob.getjob.utility;

/**
 * Created by 5 on 22-Jan-16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.finalcode.getjob.getjob.classes.Adds;

import java.util.ArrayList;

/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context       context;
    private ArrayList<Adds> imageIdList;

    private int           size;
    private boolean       isInfiniteLoop;

    public ImagePagerAdapter(Context context, ArrayList<Adds> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;

        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return  imageIdList.size();
    }




    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        System.out.println("path pos "+position);

        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        Adds  a=imageIdList.get(position);
        System.out.println("path "+a.getImagePath());
        BitmapFactory.Options options=null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bmp = BitmapFactory.decodeFile(a.getImagePath(),options);
        holder.imageView.setImageBitmap(bmp);

        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }


}