package in.futuretrucks;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.CommonGCMUtility;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.WakeLocker;

public class SplashActivity extends AppCompatActivity {

    private Context appContext;
    private String registrationId="";
    private ConnectionDetector cd;
    boolean isGPSEnabled;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appContext=this;
        PreferenceClass.setIntegerPreference(appContext,"TAB_REQ",0);
        //hideActionBar();

        cd=new ConnectionDetector(appContext);
        /*if (cd.isConnectingToInternet()) {
            locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!isGPSEnabled) {
                gpsActivation(appContext);
            }else{
                setSplashData();
            }
        }else{
            Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
        }*/

        setSplashData();
    }

    /*public void hideActionBar() {
        ActionBar actionBar= getSupportActionBar();
        if (actionBar != null)
        actionBar.hide();
    }*/

    private void setSplashData(){

        cd=new ConnectionDetector(appContext);
        if(cd.isConnectingToInternet()){
            serverData();
        }else{

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(PreferenceClass.getBooleanPreferences(appContext, Constant.isLogin)){
                    startActivity(new Intent(appContext, DrawerHomeActivity.class));
                }else{
                    startActivity(new Intent(appContext, AppIntroSlideActivity.class));
                }
                finish();
            }
        },2000);
    }

    public  void gpsActivation(final Context context) {

        final Dialog dialogGPS=new Dialog(context);
        dialogGPS.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogGPS.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogGPS.setContentView(R.layout.dialog_gpd_setting);
        dialogGPS.setCancelable(false);

        // On pressing Settings button
        CustomTextView dontAllow = (CustomTextView)dialogGPS.findViewById(R.id.txtDontAllow);
        CustomTextView allow = (CustomTextView)dialogGPS.findViewById(R.id.txtAllow);
        allow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialogGPS.cancel();
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),1);
            }
        });
        dontAllow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialogGPS.cancel();
                finish();
            }
        });

        dialogGPS.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            if (requestCode == 1) {
                setSplashData();
            }
        }else{
            gpsActivation(appContext);
        }
    }

    private void serverData() {
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonGCMUtility.DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        registrationId = GCMRegistrar.getRegistrationId(this);

        System.out.println("Reg Id is  " + registrationId);
        PreferenceClass.setStringPreference(appContext, Constant.GCM_REGISTER_KEY, registrationId);
        //Toast.makeText(appContext, registrationId, Toast.LENGTH_SHORT).show();

        // Check if regid already presents
        if (registrationId.equals("")) {
            GCMRegistrar.register(this, CommonGCMUtility.SENDER_ID);
        }
    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(CommonGCMUtility.EXTRA_MESSAGE);
            System.out.println("" + newMessage);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());
            WakeLocker.release();
        }
    };

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }

}
