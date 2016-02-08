package in.futuretrucks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.adapter.CityAdapter;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

/**
 * Created by mahadev on 12/21/15.
 */
public class CityListActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ActionBar actionBar;
    Context appContext;
    private Dialog dialogDeleteCity;
    private ConnectionDetector cd;

    ArrayList<JSONObject> listCityObject;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressHUD progress;
    JSONObject existingCityObj;
    JSONArray jsonArray=null;
    JSONArray existingCityArray = null;

    int pos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_city);
        appContext=this;
        initComponent();
    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        setActionBar();
        setListener();
        dialogDeleteCity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCityInfo();
    }

    private void setCityInfo(){
        listCityObject=new ArrayList<JSONObject>();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerCityList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try{

            existingCityObj=new JSONObject(PreferenceClass.getStringPreferences(appContext, Constant.USER_NON_CUST_VALUES));
            //jObject=jObject.getJSONObject("noncust_details");

            if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.INTERESTED_CITY){
                ((CustomTextView)findViewById(R.id.txtSubject)).setText(R.string.Interested_City);
                jsonArray=existingCityObj.getJSONArray("interested_cities");
            }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.DAILY_SERVICE_IN_CITY){
                ((CustomTextView)findViewById(R.id.txtSubject)).setText(R.string.Daily_Services_in_City);
                jsonArray=existingCityObj.getJSONArray("daily_serv_cities");
            }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.BRANCH_IN_CITY){
                ((CustomTextView)findViewById(R.id.txtSubject)).setText(R.string.Branch_Office_in_Cities);
                jsonArray=existingCityObj.getJSONArray("branch_cities");
            }
            {
                listCityObject.clear();
                if(jsonArray.length()>0 && jsonArray!=null){
                    mRecyclerView.setVisibility(View.VISIBLE);
                    ((CustomTextView)findViewById(R.id.txtNoCityData)).setVisibility(View.GONE);
                    for(int i=0;i<jsonArray.length();i++){
                        listCityObject.add(jsonArray.getJSONObject(i));
                    }
                }else{
                    mRecyclerView.setVisibility(View.GONE);
                    ((CustomTextView)findViewById(R.id.txtNoCityData)).setVisibility(View.VISIBLE);
                    if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.INTERESTED_CITY){
                        ((CustomTextView)findViewById(R.id.txtNoCityData)).setText("No interested city available.");
                    }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.DAILY_SERVICE_IN_CITY){
                        ((CustomTextView)findViewById(R.id.txtNoCityData)).setText("No daily services city available.");
                    }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.BRANCH_IN_CITY){
                        ((CustomTextView)findViewById(R.id.txtNoCityData)).setText("No branch city available.");
                    }
                }

                mAdapter = new CityAdapter(appContext,1, listCityObject,this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setListener(){
        ((ImageView)findViewById(R.id.imgvwAddCity)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgvwEditCity:
                dialogDeleteCity.show();
                pos =Integer.parseInt(v.getTag().toString());
                break;
            case R.id.imgvwAddCity:
                startActivity(new Intent(appContext, AddCityActivity.class));
                break;
            case R.id.txtAlertYES:
                dialogDeleteCity.dismiss();
                if(cd.isConnectingToInternet()){
                    try{
                        listCityObject.remove(pos);
                        existingCityArray=new JSONArray();
                        for (int i=0;i<listCityObject.size();i++){
                            existingCityArray.put(listCityObject.get(i));
                        }
                        System.out.println("listCityObject is:  "+listCityObject);

                        if (PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST) == Constant.INTERESTED_CITY) {
                            existingCityObj.putOpt("interested_cities",existingCityArray);
                        }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.DAILY_SERVICE_IN_CITY){
                            existingCityObj.putOpt("daily_serv_cities", existingCityArray);
                        }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.BRANCH_IN_CITY){
                            existingCityObj.putOpt("branch_cities", existingCityArray);
                        }
                        addCity();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(appContext, R.string.check_network,Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txtAlertNO:
                pos=-1;
                dialogDeleteCity.dismiss();
                break;
            default:
                break;
        }
    }


    private void dialogDeleteCity(){
        dialogDeleteCity=new Dialog(appContext);
        dialogDeleteCity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDeleteCity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogDeleteCity.setContentView(R.layout.dialog_delete_city);
        dialogDeleteCity.setCancelable(false);

        ((CustomTextView)dialogDeleteCity.findViewById(R.id.txtAlertYES)).setOnClickListener(this);
        ((CustomTextView)dialogDeleteCity.findViewById(R.id.txtAlertNO)).setOnClickListener(this);
    }

    public void addCity() {

        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        try{
            final AQuery aq = new AQuery(appContext);
            String url = Constant.BASE_URL+ Constant.REGISTRATION+ PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/json");
            header.put("Authorization", PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN));
            JSONObject objFinal=new JSONObject();
            objFinal.putOpt("noncust_details", existingCityObj);
            System.out.println("objFinal:  "+objFinal);
            aq.put(url, objFinal, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject jObj, AjaxStatus status) {
                    try {
                        if (progress.isShowing() && progress != null) {
                            progress.dismiss();
                        }
                        if (status.getCode() == 200) {
                            if (jObj != null) {
                                String status_response = jObj.getString("status");
                                if (status_response.equals("OK")) {
                                    jObj = jObj.getJSONObject("user");
                                    PreferenceClass.setStringPreference(appContext, Constant.USER_ID, jObj.getString("_id"));
                                    PreferenceClass.setStringPreference(appContext, Constant.OWNER_NAME, jObj.getString("owner_name"));
                                    PreferenceClass.setStringPreference(appContext, Constant.USER_MOBILE, jObj.getString("mobile"));
                                    PreferenceClass.setStringPreference(appContext, Constant.USER_EMAIl, jObj.getString("email"));
                                    PreferenceClass.setStringPreference(appContext, Constant.USER_TYPE, jObj.getString("type"));
                                    PreferenceClass.setStringPreference(appContext, Constant.USER_COMPANY_NAME, jObj.getString("company_name"));
                                    PreferenceClass.setStringPreference(appContext, Constant.USER_CITY, jObj.getJSONObject("city").toString());
                                    PreferenceClass.setStringPreference(appContext, Constant.USER_NON_CUST_VALUES, jObj.getJSONObject("noncust_details").toString());
                                    Toast.makeText(appContext,"City deleted successfully.",Toast.LENGTH_LONG).show();
                                    setCityInfo();
                                }
                            }
                        } else {
                            Toast.makeText(appContext, ""+status.getError(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.method(AQuery.METHOD_PUT).headers(header));

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
    }
}
