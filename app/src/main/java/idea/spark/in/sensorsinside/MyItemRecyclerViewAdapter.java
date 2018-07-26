package idea.spark.in.sensorsinside;

import android.hardware.Sensor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;

import idea.spark.in.sensorsinside.sensordata.SensorDetails.SensorItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SensorItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    AdRequest adBottomRequest;
    private final List<Sensor> mValues;
    private final SensorFragment.OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Sensor> items, SensorFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        adBottomRequest= new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Sensor item = mValues.get(position);

        holder.mItem = item;

        String sensorListText="Generic Type : " +  holder.mItem.getType()
                + "\nType : " + sensorTypeToString( holder.mItem.getType())
                + "\nVendor : " +  holder.mItem.getVendor()
                + "\nVersion : " +  holder.mItem.getVersion()
                + "\nResolution : " +  holder.mItem.getResolution()
                + "\nPower : " +  holder.mItem.getPower() + " mA"
                + "\nMaximum Range : " +  holder.mItem.getMaximumRange();


        holder.mBottomAdView.loadAd(adBottomRequest);

        holder.mIdView.setText(holder.mItem.getName());
        holder.mContentView.setText(sensorListText);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public AdView mBottomAdView;
        public Sensor mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mBottomAdView=(AdView)view.findViewById(R.id.bottomBanner);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public static String sensorTypeToString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "Ambient Temperature";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geomagnetic Rotation Vector";
            case Sensor.TYPE_GRAVITY:
                return "Gravity";
            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyroscope Uncalibrated";
            case Sensor.TYPE_HEART_RATE:
                return "Heart Rate Monitor";
            case Sensor.TYPE_LIGHT:
                return "Light";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";
            case Sensor.TYPE_ORIENTATION:
                return "Orientation";
            case Sensor.TYPE_PRESSURE:
                return "Orientation";
            case Sensor.TYPE_PROXIMITY:
                return "Proximity";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";
            default:
                return "Unknown";
        }
    }
}
