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
import android.widget.CompoundButton;
import android.widget.RadioButton;
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
import in.futuretrucks.adapter.VehicleSubCategoryAdapter;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.model.SubCatSelectionModel;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

/**
 * Created by mahadev on 12/21/15.
 */
public class VehicleSubCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ActionBar actionBar;
    Context appContext;
    private ConnectionDetector cd;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressHUD progress;
    JSONObject existingObj=new JSONObject();
    JSONArray existingJSONArray=new JSONArray();


    ArrayList<SubCatSelectionModel> listSubCatVehicle=new ArrayList<SubCatSelectionModel>();
    //ArrayList<String> listCategory=new ArrayList<String>();

    int pos=-1;
    RadioButton previousSelectRB;
    int positionRB=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_subcategory);
        appContext=this;
        initComponent();
    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        setActionBar();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCityInfo();
    }

    private void setCityInfo(){
        try{

            existingObj= new JSONObject(PreferenceClass.getStringPreferences(appContext, Constant.USER_NON_CUST_VALUES));
            if(existingObj!=null && existingObj.length()>0){
                existingJSONArray=existingObj.getJSONArray("vehicle_type");
            }

            System.out.println("existingObj: "+existingObj);
            System.out.println("existingJSONArray: "+existingJSONArray);

            Bundle bundle=getIntent().getExtras();
            if(bundle!=null){
                JSONArray jsonArray=new JSONArray(bundle.getString("Vehicle_Sub_Cat_List"));
                listSubCatVehicle.clear();
                if(jsonArray!=null && jsonArray.length()>0){
                    for (int i=0;i<jsonArray.length();i++){
                        SubCatSelectionModel sub_cat_model=new SubCatSelectionModel();
                        if(jsonArray.getJSONObject(i).has("truck_type")){
                            sub_cat_model.setTruckType(jsonArray.getJSONObject(i).getString("truck_type"));
                        }else{
                            sub_cat_model.setTruckType("");
                        }

                        if(jsonArray.getJSONObject(i).has("category")){
                            sub_cat_model.setCatName(jsonArray.getJSONObject(i).getString("category"));
                        }else{
                            sub_cat_model.setCatName("");
                        }

                        if(jsonArray.getJSONObject(i).has("code")){
                            sub_cat_model.setCatCode(jsonArray.getJSONObject(i).getString("code"));
                        }else{
                            sub_cat_model.setCatCode("");
                        }

                        if(jsonArray.getJSONObject(i).has("capacity")){
                            sub_cat_model.setCatCapacity(jsonArray.getJSONObject(i).getString("capacity"));
                        }else{
                            sub_cat_model.setCatCapacity("0");
                        }

                        if(existingJSONArray.length()>0 && existingJSONArray!=null){
                            for (int j=0;j<existingJSONArray.length();j++){
                                String  vehicleSubCat="";
                                String  vehicleCat="";
                                String  vehicleCode="";
                                if(existingJSONArray.get(j).toString().trim().length()>10){
                                    vehicleSubCat=existingJSONArray.getJSONObject(j).getString("truck_type");
                                    vehicleCat=existingJSONArray.getJSONObject(j).getString("category");
                                    vehicleCode=existingJSONArray.getJSONObject(j).getString("code");
                                }
                                if(vehicleSubCat.equals(jsonArray.getJSONObject(i).getString("truck_type")) && vehicleCat.equals(jsonArray.getJSONObject(i).getString("category")) && vehicleCode.equals(jsonArray.getJSONObject(i).getString("code"))){
                                    sub_cat_model.setSelected(true);
                                }else{
                                    sub_cat_model.setSelected(false);
                                }
                            }
                        }
                        listSubCatVehicle.add(sub_cat_model);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerTruckTypesList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleSubCategoryAdapter(appContext,1, listSubCatVehicle);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setListener(){
        ((CustomButton)findViewById(R.id.btnDone)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDone:
                try{

                    for(int i=0;i<listSubCatVehicle.size();i++){
                        SubCatSelectionModel cat_model = listSubCatVehicle.get(i);
                        JSONObject jsonObject=new JSONObject();
                        if(cat_model.isSelected()){
                            if(jsonObject.length()==0){
                                jsonObject.put("truck_type", cat_model.getTruckType());
                                jsonObject.put("category", cat_model.getCatName());
                                jsonObject.put("code", cat_model.getCatCode());
                                existingJSONArray.put(jsonObject);
                            }else{
                                jsonObject.put("truck_type", cat_model.getTruckType());
                                jsonObject.put("category", cat_model.getCatName());
                                jsonObject.put("code", cat_model.getCatCode());
                                existingJSONArray.put(jsonObject);
                            }
                        }
                    }
                    if(existingJSONArray.length()>0 && existingJSONArray!=null){
                        existingObj.putOpt("vehicle_type", existingJSONArray);
                        if(cd.isConnectingToInternet()){
                            addVehicle();
                        }else{
                            Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(appContext,"Please select category.",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.chkSelectVehicle:

                break;
            default:
                break;
        }
    }

    public void addVehicle() {

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
            objFinal.putOpt("noncust_details", existingObj);
            System.out.println("Final Object is:  "+objFinal);
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
                                    Toast.makeText(appContext, "Vehicle added successfully.", Toast.LENGTH_LONG).show();
                                    finish();
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
        mToolbar.setBackgroundColor(getResources().getColor(R.color.color_header));
        setSupportActionBar(mToolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
    }
}
