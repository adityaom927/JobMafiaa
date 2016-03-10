package com.app.finalcode.getjob.getjob.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.finalcode.getjob.getjob.R;
import com.app.finalcode.getjob.getjob.classes.LoginClass;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener
{
    EditText email_id, password;
    View itemView_select;
    MaterialDialog mMaterialDialog;
    int back_count=0;

    TextView skip,tool_text;
    ProgressDialog p;
    int RC_SIGN_IN = 9001;
    Button login_button, register;
    LoginClass loged;
    String res;
    private LoginButton fb_loginButton;
    private CallbackManager callbackManager;
    Toolbar toolbar;
    GoogleApiClient mGoogleApiClient;
    String TAG = "SignInActivity";
    SignInButton g_signInButton;
    String name;
    String email,pass;
    private static final long delay = 2000L;
    private boolean mRecentlyBackPressed = false;
    private Handler mExitHandler = new Handler();
    private Runnable mExitRunnable = new Runnable()
    {

        @Override
        public void run() {
            mRecentlyBackPressed=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        /*
        * keytool -exportcert -list -v \-alias abckey -keystore /.android/debug.keystore*/
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.start);


        tool_text=(TextView)findViewById(R.id.tool_text);
        skip=(TextView)findViewById(R.id.skip);
//        g_signInButton = (SignInButton) findViewById(R.id.g_sign_in_button);
//        fb_loginButton = (LoginButton) findViewById(R.id.fb_login_button);

        email_id = (EditText) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.password);

        login_button = (Button) findViewById(R.id.loginbutton);
        register = (Button) findViewById(R.id.registerbutton);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Log.e("message from jedi:",System.currentTimeMillis()+".jpg");

//        g_signInButton.setOnClickListener(this);
        login_button.setOnClickListener(this);
        register.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
//        setSupportActionBar(toolbar);



//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

  /*      fb_loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        fb_loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(Login.this,"success",Toast.LENGTH_LONG).show();
                //Toast.makeText(Login.this,"Login attempt success"+loginResult.getAccessToken().getToken().toString(),Toast.LENGTH_LONG).show();
                res = loginResult.getAccessToken().getToken().toString();

                String url = "https://graph.facebook.com/me/?access_token=" + res;


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,GraphResponse response)
                            {
                                // Application code

                                //Log.e("LoginActivity","" +object);
                                try
                                {
                                    Log.e("FB",object+"  "+response);
                                    email=object.getString("email");
                                    //new HttpAsyncTask().execute("1");
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel()
            {
                Toast.makeText(Login.this, "Login attempt canceled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {

                Toast.makeText(Login.this, "Login attempt failed.", Toast.LENGTH_LONG).show();

            }
        });*/

        skip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginClass l = new LoginClass();
                Intent skip_intent = new Intent(Login.this, Welcome.class);
                l = null;
                skip_intent.putExtra("loginclass", l);
                startActivity(skip_intent);
                finish();

            }
        });
        hideSoftKeyboard();
    }

    public void onBackPressed()
    {
        //super.onBackPressed();
        if (mRecentlyBackPressed)
        {
            mExitHandler.removeCallbacks(mExitRunnable);
            mExitHandler = null;
            System.exit(1);
        }
        else
        {
            mRecentlyBackPressed = true;
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
            mExitHandler.postDelayed(mExitRunnable, delay);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftKeyboard();

    }

    public void hideSoftKeyboard()
    {
        if(getCurrentFocus()!=null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
    //        case R.id.g_sign_in_button:
    //            signIn();
    //            break;
            case R.id.loginbutton:
            {
                p=new ProgressDialog(Login.this);
                p.show();
                p.setTitle("Login");
                p.setMessage("Logging in..");
                p.setCancelable(false);
                email = email_id.getText().toString();
                pass = password.getText().toString();

                if(email.equals("")||pass.equals(""))
                {
                    RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);
                    Snackbar.make(rel,"Email id and Password Required",Snackbar.LENGTH_LONG).show();
                    p.dismiss();
                }
                else
                {
                    new HttpAsyncTask().execute();
                }
                break;
            }
            case R.id.registerbutton:
            {
                LayoutInflater inflater = (LayoutInflater) Login.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView_select = inflater.inflate(R.layout.select_dialog, null);
                RadioGroup rg=(RadioGroup)itemView_select.findViewById(R.id.radiogroup);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {

                        switch (checkedId)
                        {
                            case R.id.userradio:
                            {
                                Intent registration_intent=new Intent(Login.this,Registration_User.class);
                                startActivity(registration_intent);
                                break;
                            }
                            case R.id.companyradio:
                            {
                                Intent registration_intent=new Intent(Login.this,Registration_Company.class);
                                startActivity(registration_intent);
                                break;
                            }
                        }
                    }
                });
                mMaterialDialog= new MaterialDialog(Login.this)
                        .setTitle("Logout").setMessage("Are You Sure You Want to Logout??")
                        .setView(itemView_select)
                        .setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();

                            }
                        });

                mMaterialDialog.show();
/*
                LayoutInflater factory = LayoutInflater.from(Login.this);
                final View dlg = factory.inflate(R.layout.select_dialog, null);
                final AlertDialog selectDialog = new AlertDialog.Builder(Login.this).create();
                selectDialog.setView(dlg);
                RadioGroup rg=(RadioGroup)dlg.findViewById(R.id.radiogroup);
                selectDialog.show();
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {

                        switch (checkedId)
                        {
                            case R.id.userradio:
                            {
                                Intent registration_intent=new Intent(Login.this,Registration_User.class);
                                startActivity(registration_intent);
                                break;
                            }
                            case R.id.companyradio:
                            {
                                Intent registration_intent=new Intent(Login.this,Registration_Company.class);
                                startActivity(registration_intent);
                                break;
                            }
                        }
                    }
                });*/
                break;
            }
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        hideSoftKeyboard();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            //Toast.makeText(Login.this,"Sending email="+email+",pass="+pass,Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... urls)
        {
            String result="";
            try
            {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.jobmafiaa.com/index.php/Main_api/user_login");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", email));
            // Add your data
            //if(urls[0].equals("1"))
            //{

//                nameValuePairs.add(new BasicNameValuePair("password", "FB"));
  //          }
    //        else
       //     {
                nameValuePairs.add(new BasicNameValuePair("password", pass));
           // }

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
                Log.e("login result=>",""+result);
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
            p.dismiss();
            //Toast.makeText(getBaseContext(), "Data Sent! response="+result, Toast.LENGTH_LONG).show();
            //edittext.setText(result);
            //Toast.makeText(Login.this,""+result,Toast.LENGTH_LONG).show();
            //Log.e("login result=>",""+result);
            if(result.equals("[]")||result.equals(""))
            {
                Toast.makeText(Login.this,"Email and password doesn't match!",Toast.LENGTH_SHORT).show();

            }
            else
            {
                try
                {
                    JSONArray arr=new JSONArray(result);
                    JSONObject obj=arr.getJSONObject(0);
                    String log_id=obj.getString("log_id");
                    String email=obj.getString("email");
                    String p=obj.getString("password");
                    String user_type=obj.getString("user_type");
                    String code=obj.getString("code");
                    String user_status=obj.getString("user_status");

                    loged = new LoginClass();

                    loged.setLid(log_id);
                    loged.setEmail(email);
                    loged.setPassword(p);
                    loged.setType(user_type);
                    loged.setCode(code);
                    loged.setStatus(user_status);

//                    p1.dismiss();

                    Intent in = null;

                    Log.e("email=>",""+loged.getEmail());
                    Log.e("type=>",""+loged.getType());
                    if (loged.getType().equals("user"))
                    {
                        in = new Intent(Login.this, Welcome_User.class);
                        in.putExtra("loginclass", loged);
                        finish();
                    }
                    else if (loged.getType().equals("company"))
                    {
                        in = new Intent(Login.this, Welcome_Company.class);
                        in.putExtra("loginclass", loged);
                        finish();
                    }

                    email_id.setText("");
                    password.setText("");
                    startActivity(in);
                    finish();

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }


        }
    }




    public void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void handleSignInResult(GoogleSignInResult result) {
        Log.e(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(Login.this, ""+acct.getDisplayName(),Toast.LENGTH_LONG).show();

        } else {
            // Signed out, show unauthenticated UI.

        }


    }
}
