package in.futuretrucks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

/**
 * Created by mahadev on 2/5/16.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private Context appContext;
    Toolbar mToolbar;
    ActionBar actionBar;
    private ProgressHUD progress;
    private ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        appContext=this;
        initComponent();
    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        setActionBar();
        ((CustomButton)findViewById(R.id.btnNext)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnNext){
            String strMobile=((CustomEditText)findViewById(R.id.edtMobile)).getText().toString().trim();
            if(strMobile.length()==0){
                Toast.makeText(appContext,R.string.mobile_blank,Toast.LENGTH_LONG).show();
            }else{
                if(cd.isConnectingToInternet()){
                    forgotPassword(strMobile);
                }else{
                    Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void forgotPassword(final String mobile) {

        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        try{
            final AQuery aq = new AQuery(appContext);
            String url = Constant.BASE_URL+Constant.FORGOT_PASSWORD;
            JSONObject jObjCreate=new JSONObject();
            if(mobile.length()>0){
                long mob_long=Long.parseLong(mobile);
                jObjCreate.putOpt("mobile", mob_long);
            }

            System.out.println("jObjCreate : "+jObjCreate);

            aq.post(url, jObjCreate, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject jObj, AjaxStatus status) {
                    try {
                        if (progress.isShowing() && progress != null) {
                            progress.dismiss();
                        }

                        System.out.println("Status Code: "+status.getCode()+"  "+"jObject is: "+jObj);

                        if (status.getCode() == 200) {
                            if (jObj != null) {
                                String status_response = jObj.getString("status");
                                if(status_response.equalsIgnoreCase("OK")){
                                    Toast.makeText(appContext,""+jObj.getString("message"),Toast.LENGTH_LONG).show();
                                    PreferenceClass.setStringPreference(appContext,Constant.USER_MOBILE,mobile);
                                    Intent intent = new Intent(appContext, VerificationFPActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(appContext,""+jObj.getString("error_message"),Toast.LENGTH_LONG).show();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setActionBar(){
        mToolbar = (Toolbar) findViewById(R.id.layoutHeader);
        setSupportActionBar(mToolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setVisibility(View.VISIBLE);
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setText("Forgot password");
    }
}

/*if(jObj.has("token")){
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
        }*/
