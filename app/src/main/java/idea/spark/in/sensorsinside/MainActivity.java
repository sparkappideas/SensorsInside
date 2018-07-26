package idea.spark.in.sensorsinside;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorFragment.OnListFragmentInteractionListener, SensorsFragment.OnFragmentInteractionListener {

    InterstitialAd mInterstitialAd;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4283164631469341~8235437313");


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        SharedPreferences sharedPreferences = getSharedPreferences("AppFirstLaunch", 0);
//        if (sharedPreferences.getBoolean("AppFirstLaunch", true)) {
//            sharedPreferences.edit().putBoolean("AppFirstLaunch", false).commit();
//
//            Calendar instance = Calendar.getInstance();
//            System.out.println("Current time =&gt; " + instance.getTime());
//            String installDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(instance.getTime());
//
//            Bundle bundle = new Bundle();
//            bundle.putString("email",Utils.getPrimaryEmail(this));
//            bundle.putString("installDate",installDate );
//            mFirebaseAnalytics.logEvent("freshInstall", bundle);
//
//        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragcontainer, SensorFragment.newInstance(1))
                    .commit();
        }

        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_result_ad_unit_id));

        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
    }

    private void requestNewInterstitial() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    public void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_v_search) {
            if(Utils.internetStatus(this)){
                showInterstitial();
            }

//            Bundle bundle = new Bundle();
//            bundle.putString("email",Utils.getPrimaryEmail(this));
//            bundle.putString("ad",Utils.internetStatus(this) ? "yes":"no");
//            mFirebaseAnalytics.logEvent("navDrawerSearch", bundle);

            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.fragcontainer)).commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragcontainer, SensorFragment.newInstance(1))
                    .commit();

        }else if (id == R.id.nav_sensors) {
            if(Utils.internetStatus(this)){
                showInterstitial();
            }

//            Bundle bundle = new Bundle();
//            bundle.putString("email",Utils.getPrimaryEmail(this));
//            bundle.putString("ad",Utils.internetStatus(this) ? "yes":"no");
//            mFirebaseAnalytics.logEvent("sensordescriptionpage", bundle);

            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.fragcontainer)).commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragcontainer, SensorsFragment.newInstance("",""))
                    .commit();

        }else if (id == R.id.nav_share) {

//            Bundle bundle = new Bundle();
//            bundle.putString("email",Utils.getPrimaryEmail(this));
//            mFirebaseAnalytics.logEvent("appShare", bundle);

            String str = "Hey Buddy Install this app, It is Very handy to know about your mobile sensors! " + ("https://play.google.com/store/apps/details?id=" + getPackageName());
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", str);
            startActivity(Intent.createChooser(intent, "Share Via"));
        }else if (id == R.id.nav_rateus) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=idea.spark.in.sensorsinside"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Sensor item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
