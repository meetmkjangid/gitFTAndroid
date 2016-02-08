package in.futuretrucks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.utility.PreferenceClass;

/**
 * Created by mahadev on 12/9/15.
 */
public class UserSelectionActivity extends AppCompatActivity implements View.OnClickListener{
    private Context appContext;
    Toolbar mToolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        appContext=this;
        initComponent();
    }

    private void initComponent() {
        ((CustomButton) findViewById(R.id.btnNext)).setOnClickListener(this);
        setActionBar();

        clearRadioButton();
        ((RadioButton) findViewById(R.id.rbTransporter)).setChecked(true);
        PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE, Constant.transporter);
    }

    private void clearRadioButton(){

        ((RadioButton)findViewById(R.id.rbTransporter)).setOnClickListener(this);
        ((RadioButton)findViewById(R.id.rbTruckOwner)).setOnClickListener(this);
        ((RadioButton)findViewById(R.id.rbCustomer)).setOnClickListener(this);
        ((RadioButton)findViewById(R.id.rbCHA)).setOnClickListener(this);
        ((RadioButton)findViewById(R.id.rbShippingline)).setOnClickListener(this);

        ((RadioButton)findViewById(R.id.rbTransporter)).setChecked(false);
        ((RadioButton)findViewById(R.id.rbTruckOwner)).setChecked(false);
        ((RadioButton)findViewById(R.id.rbCustomer)).setChecked(false);
        ((RadioButton)findViewById(R.id.rbCHA)).setChecked(false);
        ((RadioButton)findViewById(R.id.rbShippingline)).setChecked(false);
        PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE, "");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.rbTransporter){
            clearRadioButton();
            PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE,Constant.transporter);
            ((RadioButton) findViewById(R.id.rbTransporter)).setChecked(true);
        }else if(v.getId()==R.id.rbTruckOwner){
            clearRadioButton();
            PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE, Constant.truck_owner);
            ((RadioButton)findViewById(R.id.rbTruckOwner)).setChecked(true);
        }else if(v.getId()==R.id.rbCustomer){
            clearRadioButton();
            PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE, Constant.customer);
            ((RadioButton)findViewById(R.id.rbCustomer)).setChecked(true);
        }else if(v.getId()==R.id.rbCHA){
            clearRadioButton();
            PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE, Constant.cha);
            ((RadioButton)findViewById(R.id.rbCHA)).setChecked(true);
        }else if(v.getId()==R.id.rbShippingline){
            clearRadioButton();
            PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE, Constant.sl);
            ((RadioButton)findViewById(R.id.rbShippingline)).setChecked(true);
        }else if(v.getId()==R.id.btnNext){
            if(PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE).trim().length()==0){
                Toast.makeText(appContext,"Please select user type first.",Toast.LENGTH_LONG).show();
            }else {
                startActivity(new Intent(appContext,RegisterActivity.class));
            }

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
    }
}
