package in.futuretrucks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.AdvertiseTruckApiResponse;
import in.futuretrucks.model.Device;
import in.futuretrucks.model.LoginModel;
import in.futuretrucks.response.LoginAPIResponse;
import in.futuretrucks.services.AdvertiseTruckService;
import in.futuretrucks.services.LoginApiService;
import in.futuretrucks.services.LoginService;
import in.futuretrucks.utility.CommonGCMUtility;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import in.futuretrucks.utility.WakeLocker;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by mahadev on 11/28/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Context appContext;
    ProgressHUD progress;
    ConnectionDetector cd;
    private String registrationId="";
    TelephonyManager telephone_manager;
    LoginModel model_login=new LoginModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appContext=this;
        initComponent();
    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        telephone_manager= (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        ((CustomButton)findViewById(R.id.btnLogin)).setOnClickListener(this);
        ((CustomButton)findViewById(R.id.btnJoinNow)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtForgotPassword)).setOnClickListener(this);

        if(cd.isConnectingToInternet()){
            serverData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnJoinNow:
                startActivity(new Intent(appContext,UserSelectionActivity.class));
            break;
            case R.id.txtForgotPassword:
                startActivity(new Intent(appContext, ForgotPasswordActivity.class));
                break;
            case R.id.btnLogin:
                final String strMobile=((CustomEditText)findViewById(R.id.edtMobile)).getText().toString().trim();
                final String strPassword=((CustomEditText)findViewById(R.id.edtPassword)).getText().toString().trim();

                if(strMobile.length()==0){
                    Toast.makeText(appContext, R.string.mobile_blank, Toast.LENGTH_LONG).show();
                }else if(strPassword.length()==0){
                    Toast.makeText(appContext,R.string.password_blank,Toast.LENGTH_LONG).show();
                }else{
                    if(cd.isConnectingToInternet()){
                        if(PreferenceClass.getStringPreferences(appContext,Constant.GCM_REGISTER_KEY).trim().length()>10){
                            loginUser(strMobile,strPassword);
                        }else{
                            Toast.makeText(appContext,"GCM is not register.Please wait",Toast.LENGTH_LONG).show();
                            serverData();
                        }

                    }else{
                        Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void loginUser(final String mobile,final String password) {

        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        try{
            final AQuery aq = new AQuery(appContext);
            String url = Constant.BASE_URL+Constant.LOGIN;
            JSONObject jObjCreate=new JSONObject();
            jObjCreate.putOpt("mobile", mobile);
            jObjCreate.putOpt("password", password);

            JSONArray jArray=new JSONArray();
            JSONObject jObj=new JSONObject();
            jObj.putOpt("dtype", Constant.ANDROID);
            jObj.putOpt("token", PreferenceClass.getStringPreferences(appContext, Constant.GCM_REGISTER_KEY));
            jObj.putOpt("IMEI", telephone_manager.getDeviceId());

            jArray.put(jObj);
            System.out.println("jArray for info: " + jArray);
            jObjCreate.putOpt("devices", jObj);
            System.out.println("Object making is: "+jObjCreate);

            aq.post(url, jObjCreate, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject jObj, AjaxStatus status) {
                    try {
                        if (progress.isShowing() && progress != null) {
                            progress.dismiss();
                        }

                        System.out.println("Status Code: "+status+"jObject is: "+jObj);

                        if (status.getCode() == 200) {
                            if (jObj != null) {
                                String status_response = jObj.getString("status");
                                if(status_response.equalsIgnoreCase("OK")){
                                    if(jObj.has("token")){
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_TOKEN,"JWT "+jObj.getString("token"));
                                        jObj=jObj.getJSONObject("user");
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_ID,jObj.getString("_id"));
                                        PreferenceClass.setStringPreference(appContext,Constant.OWNER_NAME,jObj.getString("owner_name"));
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_MOBILE,jObj.getString("mobile"));
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_EMAIl,jObj.getString("email"));
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_TYPE,jObj.getString("type"));
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_CITY,jObj.getJSONObject("city").toString());
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_NON_CUST_VALUES,jObj.getJSONObject("noncust_details").toString());
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_COMPANY_NAME,jObj.getString("company_name"));

                                        if(jObj.has("profile_img")){
                                            PreferenceClass.setStringPreference(appContext,Constant.USER_PROFILE_PIC,jObj.getString("profile_img"));
                                        }

                                        if(jObj.has("company_log")){
                                            PreferenceClass.setStringPreference(appContext,Constant.USER_COMAPNY_LOGO_PIC,jObj.getString("company_log"));
                                        }

                                        if(jObj.has("id_proof")){
                                            PreferenceClass.setStringPreference(appContext,Constant.USER_PHOTO_ID_PROOF_PIC,jObj.getString("id_proof"));
                                        }

                                        if(jObj.has("pan_card_proof")){
                                            PreferenceClass.setStringPreference(appContext,Constant.USER_PAN_CARD_PIC,jObj.getString("pan_card_proof"));
                                        }

                                        if(jObj.has("service_tax_proof")){
                                            PreferenceClass.setStringPreference(appContext,Constant.USER_SERVICE_TAX_NUM_PIC,jObj.getString("service_tax_proof"));
                                        }

                                        if(jObj.has("tin_number_proof")){
                                            PreferenceClass.setStringPreference(appContext,Constant.USER_TIN_NUM_PROOF_PIC,jObj.getString("tin_number_proof"));
                                        }

                                        if(jObj.has("addr_proof")){
                                            PreferenceClass.setStringPreference(appContext,Constant.USER_ADDRESS_PROOF_PIC,jObj.getString("addr_proof"));
                                        }

                                        Intent intent = new Intent(appContext, DrawerHomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }else{
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_MOBILE,mobile);
                                        PreferenceClass.setStringPreference(appContext,Constant.USER_PASSWORD,password);
                                        Intent intent = new Intent(appContext, VerificationActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            }
                        } else {
                            JSONObject jObject = new JSONObject(status.getError());
                            String error_message = "";
                            if (jObject.has("error_message")) {
                                error_message = jObject.getString("error_message");
                            } else if (jObject.has("error_messages")) {
                                error_message = jObject.getString("error_messages");
                            }
                            Toast.makeText(appContext, error_message, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.method(AQuery.METHOD_POST).header("Content-Type", "application/json"));

        }catch (Exception e){
            e.printStackTrace();
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
