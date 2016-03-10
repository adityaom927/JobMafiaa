package com.app.finalcode.getjob.getjob.activity;

import android.content.Intent;
import android.os.Bundle;


import android.app.Activity;
import android.widget.TextView;

import com.app.finalcode.getjob.getjob.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class FullScreenAd extends Activity {
    /** Called when the activity is first created. */
    private InterstitialAd interstitial = null;
    private TextView textView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_ad);

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(FullScreenAd.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-3764645326864071/2506266749");

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView);

        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);

        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest);

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }


            @Override
            public void onAdOpened() {

                System.out.println("add open");
            }

            @Override
            public void onAdClosed() {
                System.out.println("add close");
                finish();
                Intent intent = new Intent(FullScreenAd.this, Login.class);
                startActivity(intent);
                // closeAllActivities();
            }

        });
    }
    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();

        }


    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}