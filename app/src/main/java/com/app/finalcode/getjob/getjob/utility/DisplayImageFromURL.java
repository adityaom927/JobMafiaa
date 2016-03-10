package com.app.finalcode.getjob.getjob.utility;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by 5 on 16-Jan-16.
 */
public class DisplayImageFromURL extends AsyncTask<String, Void, Bitmap>
{
    ImageView bmImage;

    @Override
    protected void onPreExecute() {

    }
    public DisplayImageFromURL(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    protected Bitmap doInBackground(String... urls)
    {
        Log.e("in Displayimage=>",""+urls[0]);
        String urldisplay = urls[0];
        Bitmap preview_bitmap = null;
        //BitmapFactory.Options options=new BitmapFactory.Options();
        //BitmapFactory options=new BitmapFactory;
        //options.inSampleSize = 1;

        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            preview_bitmap=BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            //  Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return preview_bitmap;

    }
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        //  pd.dismiss();
    }
}