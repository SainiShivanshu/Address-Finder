package com.example.address;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5,textView6,textView7;
    FusedLocationProviderClient fusedLocationProviderClient;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLocation = findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        textView6 = findViewById(R.id.text_view6);
        textView7 = findViewById(R.id.text_view7);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
//                Toast.makeText(MainActivity.this, "Add loaded", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });



        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MainActivity.this,
                            Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        textView1.setText(Html.fromHtml(
                                "<font color ='#83B12E'><b>City :</b><br></font> "
                                        + addresses.get(0).getLocality()
                        ));

                        textView2.setText(Html.fromHtml(
                                "<font color ='#83B12E'><b>Postal Code :</b><br></font> "
                                        + addresses.get(0).getPostalCode()
                        ));

                        textView3.setText(Html.fromHtml(
                                "<font color ='#83B12E'><b>Country :</b><br></font> "
                                        + addresses.get(0).getCountryName()
                        ));

                        textView4.setText(Html.fromHtml(
                                "<font color ='#83B12E'><b>State :</b><br></font> "
                                        + addresses.get(0).getAdminArea()
                        ));

                        textView5.setText(Html.fromHtml(
                                "<font color ='#83B12E'><b>Address :</b><br></font> "
                                        + addresses.get(0).getAddressLine(0)
                        ));
                        textView6.setText(Html.fromHtml(
                                "<font color ='#83B12E'><b>Latitude :</b><br></font> "
                                        + addresses.get(0).getLatitude()
                        ));
                        textView7.setText(Html.fromHtml(
                                "<font color ='#83B12E'><b>Longitude :</b><br></font> "
                                        + addresses.get(0).getLongitude()
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}

