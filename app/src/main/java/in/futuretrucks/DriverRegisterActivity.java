package in.futuretrucks;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.AdvertiseTruckApiResponse;
import in.futuretrucks.model.Driver;
import in.futuretrucks.model.DriverRegistrationModel;
import in.futuretrucks.response.DriverRegApiResponse;
import in.futuretrucks.services.AdvertiseTruckService;
import in.futuretrucks.services.DriverApiService;
import in.futuretrucks.services.DriverRegisterService;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class    DriverRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Context appContext;
    private ProgressHUD progress;
    ConnectionDetector cd;
    Toolbar mToolbar;
    ActionBar actionBar;
    DriverRegistrationModel driver_reg_model=new DriverRegistrationModel();

    SimpleDateFormat mFormatter = new SimpleDateFormat("dd/MM/yy hh:mm aa");
    SimpleDateFormat mFormatter1 = new SimpleDateFormat("dd-MM-yyyy");
    int date_request=0;
    private SlideDateTimeListener listenerDateTimePicker = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            try {

                Calendar c = Calendar.getInstance();
                String formattedDate = mFormatter.format(c.getTime());
                Date mDateCurrent = mFormatter.parse(formattedDate);
                Date mDate = mFormatter.parse(mFormatter.format(date).toString());
                if(date_request==1){
                    if (mDate.compareTo(mDateCurrent)<0){
                        ((CustomEditText)findViewById(R.id.edtDOB)).setText("" + mFormatter1.format(mDate));
                        driver_reg_model.setDateOfBirth(mDate);
                    }else{
                        Toast.makeText(appContext,"Date of birth should not be greater than current date.",Toast.LENGTH_LONG).show();
                    }
                }else if(date_request==2){
                    if (mDate.compareTo(mDateCurrent)<0){
                        ((CustomEditText)findViewById(R.id.edtDLIssueDate)).setText("" + mFormatter1.format(mDate));
                        driver_reg_model.setDlIssueDate(mDate);
                    }else{
                        Toast.makeText(appContext,"DL issue date should not be greater than current date.",Toast.LENGTH_LONG).show();
                    }
                }else if(date_request==3){
                    if (mDate.compareTo(mDateCurrent)>0){
                        ((CustomEditText)findViewById(R.id.edtDLExpireDate)).setText("" + mFormatter1.format(mDate));
                        driver_reg_model.setDlExpiryDate(mDate);
                    }else{
                        Toast.makeText(appContext,"DL expire date should not be less than current date.",Toast.LENGTH_LONG).show();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(appContext,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);
        appContext = this;
        initComponent();
    }

    private void initComponent() {
        cd = new ConnectionDetector(appContext);
        setActionBar();

        findViewById(R.id.btnRegisterNow).setOnClickListener(this);
        findViewById(R.id.edtDOB).setOnClickListener(this);
        findViewById(R.id.edtDLExpireDate).setOnClickListener(this);
        findViewById(R.id.edtDLIssueDate).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edtDOB:
                date_request=1;
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listenerDateTimePicker)
                        .setInitialDate(new Date())
                        .build()
                        .show();

                break;

            case R.id.edtDLIssueDate:
                date_request=2;
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listenerDateTimePicker)
                        .setInitialDate(new Date())
                        .build()
                        .show();
                break;

            case R.id.edtDLExpireDate:
                date_request=3;
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listenerDateTimePicker)
                        .setInitialDate(new Date())
                        .build()
                        .show();
                break;
            case R.id.btnRegisterNow:

                String strFirstName = ((CustomEditText) findViewById(R.id.edtDriverFirstName)).getText().toString().trim();
                String strLastName = ((CustomEditText) findViewById(R.id.edtDriverLastName)).getText().toString().trim();
                String strLicense = ((CustomEditText) findViewById(R.id.edtLicense)).getText().toString().trim();
                String strMobile = ((CustomEditText) findViewById(R.id.edtDriverMobile)).getText().toString().trim();
                String strFatherName = ((CustomEditText) findViewById(R.id.edtFathername)).getText().toString().trim();
                String strAddress1 = ((CustomEditText) findViewById(R.id.edtAddress1)).getText().toString().trim();
                String strAddress2 = ((CustomEditText) findViewById(R.id.edtAddress2)).getText().toString().trim();
                String strArea = ((CustomEditText) findViewById(R.id.edtArea)).getText().toString().trim();
                String strCity = ((CustomEditText) findViewById(R.id.edtCity)).getText().toString().trim();
                String strDistrict = ((CustomEditText) findViewById(R.id.edtDistrict)).getText().toString().trim();
                String strPincode = ((CustomEditText) findViewById(R.id.edtPincode)).getText().toString().trim();
                long numMobile = 0,numPincode=0;
                if (strMobile != null){
                    numMobile = Long.parseLong(strMobile);
                }
                if(strPincode!=null){
                    numPincode = Long.parseLong(strPincode);
                }

                CheckBox chkBox = ((CheckBox) findViewById(R.id.chkAcceptTermsCondition));
                if (strFirstName.length() == 0) {
                    Toast.makeText(appContext, R.string.f_name_blank, Toast.LENGTH_LONG).show();
                } else if (strLastName.length() == 0) {
                    Toast.makeText(appContext, R.string.l_name_blank, Toast.LENGTH_LONG).show();
                } else if (strMobile.length() == 0) {
                    Toast.makeText(appContext, R.string.mobile_blank, Toast.LENGTH_LONG).show();
                } else if (strMobile.length() != 10) {
                    Toast.makeText(appContext, R.string.mobile_invalid, Toast.LENGTH_LONG).show();
                } else if (strLicense.length() == 0) {
                    Toast.makeText(appContext, R.string.license_blank, Toast.LENGTH_LONG).show();
                } else {
                    if (cd.isConnectingToInternet()) {
                        if (chkBox.isChecked()) {
                            driver_reg_model.setFirstName(strFirstName);
                            driver_reg_model.setLastName(strLastName);
                            driver_reg_model.setLicenseNo(strLicense);
                            driver_reg_model.setMobile(numMobile);
                            driver_reg_model.setAddressLine1(strAddress1 + " " + strAddress2);
                            driver_reg_model.setArea(strArea);
                            driver_reg_model.setCity(strCity);
                            driver_reg_model.setIsNew(true);
                            driver_reg_model.setFatherName(strFatherName);
                            driver_reg_model.setDistrict(strDistrict);
                            driver_reg_model.setPincode(numPincode);

                            progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    // TODO Auto-generated method stub
                                }
                            });

                            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                            DriverRegisterService driverService = ServiceGenerator.createService(DriverRegisterService.class);
                            Call<DriverRegApiResponse> call = driverService.registerDriver(driver_reg_model,jwt);
                            call.enqueue(new Callback<DriverRegApiResponse>() {
                                @Override
                                public void onResponse(Response<DriverRegApiResponse> response, Retrofit retrofit) {
                                    DriverRegApiResponse response_driver_reg_body=response.body();
                                    if(response_driver_reg_body!=null){
                                        if(response_driver_reg_body.getStatus().equalsIgnoreCase("OK")){
                                            PreferenceClass.setBooleanPreference(appContext, "Driver_List_Update",true);
                                            PreferenceClass.setStringPreference(appContext,Constant.NEW_DRIVER_REG_ID,""+response_driver_reg_body.getDriver().getId().trim());
                                            startActivity(new Intent(appContext, UploadDriverDocumentsActivity.class));
                                            finish();
                                            Toast.makeText(appContext,"Driver Register successfully",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(appContext,"Driver Register failed.",Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(appContext,"Driver Register failed.",Toast.LENGTH_LONG).show();
                                    }
                                    if (progress.isShowing() && progress != null) {
                                        progress.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    if (progress.isShowing() && progress != null) {
                                        progress.dismiss();
                                    }
                                    System.out.println("Driver Reg OnFailer: "+t.getMessage());
                                }
                            });
                        } else {
                            Toast.makeText(appContext, R.string.terms_cond, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(appContext, R.string.check_network, Toast.LENGTH_LONG).show();
                    }
                }

                break;
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
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setText("Driver Registration");
    }
}
