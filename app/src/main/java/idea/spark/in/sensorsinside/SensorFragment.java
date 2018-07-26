package idea.spark.in.sensorsinside;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SensorFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    InterstitialAd mInterstitialAd;
//    Timer timer;
//    MyTimerTask myTimerTask;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SensorFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SensorFragment newInstance(int columnCount) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(sensors, mListener));
        }

        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(getActivity());
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_result_ad_unit_id));

        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

//        scheduleInterestitialAd();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Utils.internetStatus(getActivity())){
                    showInterstitial();
                }
            }
        }, 8000);

        return view;
    }


  /*  private void scheduleInterestitialAd(){
        if(timer != null){
            timer.cancel();
        }
        //re-schedule timer here
        //otherwise, IllegalStateException of
        //"TimerTask is scheduled already"
        //will be thrown
        timer = new Timer();
        myTimerTask = new MyTimerTask();

        timer.schedule(myTimerTask, 15000, 21000);
    }*/

   /* private void cancelInterestitialAd(){
        if (timer!=null){
            timer.cancel();
            timer = null;
        }
    }*/

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        cancelInterestitialAd();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Sensor item);
    }


   /* class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    showInterstitial();
                }});
        }
    }*/
}
