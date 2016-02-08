package in.futuretrucks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

/**
 * Created by mahadev on 12/1/15.
 */
public class VerificationFPActivity extends AppCompatActivity implements View.OnClickListener{
    private Context appContext;
    ProgressHUD progress;
    ActionBar actionBar;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        appContext=this;
        initComponent();
    }

    private void initComponent(){
        ((CustomButton)findViewById(R.id.btnSubmit)).setBackgroundColor(getResources().getColor(R.color.color_header));
        ((CustomButton)findViewById(R.id.btnSubmit)).setOnClickListener(this);
        setActionBar();
        setTimer();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit){
            String strVerificationCode=((CustomEditText)findViewById(R.id.edtVerificationCode)).getText().toString().trim();
            if(strVerificationCode.length()==0){
                Toast.makeText(appContext, R.string.verification_blank, Toast.LENGTH_LONG).show();
            }else{
                verifyUser(strVerificationCode);
            }
        }
    }

    private void setTimer(){
        new CountDownTimer(60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                ((CustomTextView)findViewById(R.id.txtCounter)).setText("" +String.format("%d Sec",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                ));
            }

            public void onFinish() {
                ((CustomTextView)findViewById(R.id.txtCounter)).setText("0 Sec");
                ((CustomTextView)findViewById(R.id.txtResend)).setAlpha(1);
                ((CustomTextView)findViewById(R.id.txtResend)).setEnabled(true);
                ((CustomTextView)findViewById(R.id.txtResend)).setClickable(true);
                ((CustomTextView)findViewById(R.id.txtResend)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resendCode();
                    }
                });
            }
        }.start();
    }

    public void verifyUser(String strVerificationCode) {

        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        final AQuery aq = new AQuery(appContext);
        String url = Constant.BASE_URL+Constant.VERIFY_OTP_FP;
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", PreferenceClass.getStringPreferences(appContext,Constant.USER_MOBILE));
        params.put("otp", strVerificationCode);

        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jObj, AjaxStatus status) {
                try {
                    if (progress.isShowing() && progress != null) {
                        progress.dismiss();
                    }
                    if (jObj != null) {
                        String status_response = jObj.getString("status");
                        if (status_response.equals("OK")) {
                            Toast.makeText(appContext, "" + jObj.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(appContext, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            String error_message = jObj.getString("error_message");
                            Toast.makeText(appContext, error_message, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(appContext, R.string.check_network, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setText("Mobile Verification");
    }

    public void resendCode() {

        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        final AQuery aq = new AQuery(appContext);
        String url = Constant.BASE_URL+Constant.RESEND_OTP;
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", PreferenceClass.getStringPreferences(appContext,Constant.USER_MOBILE));

        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jObj, AjaxStatus status) {
                try {
                    if (progress.isShowing() && progress != null) {
                        progress.dismiss();
                    }
                    if (jObj != null) {
                        String status_response = jObj.getString("status");
                        if (status_response.equals("OK")) {
                            String message=jObj.getString("message");
                            Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
                        } else {
                            String error_message=jObj.getString("error_message");
                            Toast.makeText(appContext, error_message, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(appContext, R.string.check_network, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}