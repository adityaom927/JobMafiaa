package com.app.finalcode.getjob.getjob.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.app.finalcode.getjob.getjob.classes.Adds;
import com.app.finalcode.getjob.getjob.classes.FetchImageDataListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by we on 15/2/16.
 */
public class FatchAddsImage extends AsyncTask<ArrayList<Adds>, Void, String> {
    private String msg;
    Context context;
    ArrayList<Adds> addsImagePathList=new ArrayList<Adds>();
    public FetchImageDataListener listener;
    public FatchAddsImage(FetchImageDataListener listener, Context context) {
        this.listener = listener;
       this.context=context;
    }

    private ProgressDialog simpleWaitDialog;


    @Override
    protected void onPreExecute() {
        Log.i("Async-Example", "onPreExecute Called");
        simpleWaitDialog = ProgressDialog.show(context,"Wait", "Fetching Resources");

    }
    @Override
    protected String doInBackground(ArrayList<Adds>... params)
    {
        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/job_image");
        ArrayList<Adds>  adds= params[0];

        int count_flag=-1;
        File file1[] = new File[0];

        if(folder.exists())
        {
            String path1 = Environment.getExternalStorageDirectory().toString()+"/job_image";
            Log.d("Files", "Path: " + path1);
            File f = new File(path1);
            file1 = f.listFiles();
            Log.d("Files", "Size: "+ file1.length);

            for (int i=0; i < file1.length; i++)
            {
                Log.e("check:","in for");
                if(adds.get(i).getImage().equals(file1[i].getName()))
                {
                    Log.e("check:","in if");
                    count_flag=1;
                }
                else
                {
                    Log.e("check:","in else");
                    count_flag=0;
                    break;
                }

                Log.d("Files", "FileName:" + file1[i].getName());
            }
        }

        if(count_flag==1)
        {
            for(int count1=0;count1<file1.length;count1++)
            {
                String path = file1[count1].getPath().toString();
                //System.out.println("image " + path);
                Adds a = new Adds();
                a.setImagePath(path);
                System.out.println("list obj" + a.getImagePath());
                addsImagePathList.add(a);
            }

        }
        else
        {
            System.out.println("image " + "call method");
            for(int count=0;count<adds.size();count++)
            {
                final String file_url = "http://jobmafiaa.com//assets/uploads/" + adds.get(count).getImage();

                System.out.println("image " + file_url);
                //new DownloadFileFromURL().execute(file_url);

                //downloadFile(file_url, adds.get(count).getImage().toString());

                final String imageName = adds.get(count).getImage().toString();

                try {


                    URL url = null;
                    try {
                        url = new URL(file_url);
                    } catch (MalformedURLException e) {
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


                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    //create a new file, specifying the path, and the filename
                    //which we want to save the file as.
                    File file = new File(folder, imageName);


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
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        //add the data in the buffer to the file in the file output stream (the file on the sd card
                        fileOutput.write(buffer, 0, bufferLength);
                        //add up the size so we know how much is downloaded
                        downloadedSize += bufferLength;
                        //this is where you would do something to report the prgress, like this maybe
                        //updateProgress(downloadedSize, totalSize);

                    }
                    String path = file.getPath().toString();
                    System.out.println("image " + path);

                    Adds a = new Adds();
                    a.setImagePath(path);
                    System.out.println("list obj" + a.getImagePath());
                    addsImagePathList.add(a);
                    //close the output stream when done
                    fileOutput.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String sJson)
    {
        simpleWaitDialog.dismiss();
        if (listener != null)
            listener.onFetchComplete_add(addsImagePathList);
        System.out.println("list fetch" + addsImagePathList.size());
         //call listener for onFatchComplete_add;

    }

}
/*
*
* ArrayList<Adds>  adds= params[0];


        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/job_image");
        if (!folder.exists())
        {
            folder.mkdir();
        }
        String path1 = Environment.getExternalStorageDirectory().toString()+"/job_image";
        Log.d("Files", "Path: " + path1);
        File f = new File(path1);
        File file1[] = f.listFiles();
        Log.d("Files", "Size: "+ file1.length);
        int count=-1;
        for (int i=0; i < file1.length; i++)
        {
            Log.e("check:","in for");
            if(adds.get(i).getImage().equals(file1[i].getName()))
            {
                Log.e("check:","in if");
                count=1;
            }
            else
            {
                Log.e("check:","in else");
                count=0;
                break;
            }

            Log.d("Files", "FileName:" + file1[i].getName());
        }

        if(count==1)
        {
            for(int count1=0;count1<file1.length;count1++)
            {
                String path = file1[count1].getPath().toString();
                //System.out.println("image " + path);
                Adds a = new Adds();
                a.setImagePath(path);
                System.out.println("list obj" + a.getImagePath());
                addsImagePathList.add(a);
            }

        }
        else if(count==0)
        {
            System.out.println("image " + "call method");
            for (count = 0; count < adds.size(); count++)
            {
                final String file_url="http://jobmafiaa.com//assets/uploads/"+adds.get(count).getImage();

                System.out.println("image " + file_url);
                //new DownloadFileFromURL().execute(file_url);

                //downloadFile(file_url, adds.get(count).getImage().toString());

                final  String imageName=adds.get(count).getImage().toString();

                try {


                    URL url = null;
                    try {
                        url = new URL(file_url);
                    } catch (MalformedURLException e) {
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
                    //File folder1 = new File(Environment.getExternalStorageDirectory().toString()+"/job_image");

                    if(!folder.exists())
                    {
                        folder.mkdir();
                    }
                    //create a new file, specifying the path, and the filename
                    //which we want to save the file as.
                    File file = new File(folder,imageName);



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
                    while ((bufferLength = inputStream.read(buffer)) > 0 )
                    {
                        //add the data in the buffer to the file in the file output stream (the file on the sd card
                        fileOutput.write(buffer, 0, bufferLength);
                        //add up the size so we know how much is downloaded
                        downloadedSize += bufferLength;
                        //this is where you would do something to report the prgress, like this maybe
                        //updateProgress(downloadedSize, totalSize);

                    }
                    String path=file.getPath().toString();
                    System.out.println("image "+path);

                    Adds a=new Adds();
                    a.setImagePath(path);
                    System.out.println("list obj" +a.getImagePath());
                    addsImagePathList.add(a);
                    //close the output stream when done
                    fileOutput.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                //flag=1;
                //st();

            }
        }
* */
