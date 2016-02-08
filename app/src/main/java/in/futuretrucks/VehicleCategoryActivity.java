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
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.adapter.VehicleCategoryAdapter;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

/**
 * Created by mahadev on 12/21/15.
 */
public class VehicleCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ActionBar actionBar;
    Context appContext;
    private ConnectionDetector cd;

    ArrayList<JSONObject> listCityObject;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressHUD progress;
    JSONObject existingCityObj;


    ArrayList<JSONArray> listVehicle=new ArrayList<JSONArray>();
    ArrayList<String> listCategory=new ArrayList<String>();

    JSONObject existingObj=new JSONObject();
    JSONArray existingJSONArray=new JSONArray();

    int pos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_category);
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
        setVehicleInfo();
    }

    private void setVehicleInfo(){
        listCityObject=new ArrayList<JSONObject>();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerTruckTypesList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleCategoryAdapter(appContext,1, listCategory,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        try{
            existingObj= new JSONObject(PreferenceClass.getStringPreferences(appContext, Constant.USER_NON_CUST_VALUES));
            if(existingObj!=null && existingObj.length()>0){
                existingJSONArray=existingObj.getJSONArray("vehicle_type");
            }

            System.out.println("existingObj: " + existingObj);
            System.out.println("existingJSONArray: " + existingJSONArray);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(cd.isConnectingToInternet()){
            getVehicleList();
        }else{
            Toast.makeText(appContext, R.string.check_network,Toast.LENGTH_LONG).show();
        }
    }

    private void setListener(){
        ((CustomButton)findViewById(R.id.btnSaveNext)).setOnClickListener(this);
    }

    public void getVehicleList() {
        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });
        final AQuery aq = new AQuery(appContext);
        String url = Constant.BASE_URL+ Constant.GET_VEHICLE_CATEGORY;

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jObj, AjaxStatus status) {
                try {
                    if (progress.isShowing() && progress != null) {
                        progress.dismiss();
                    }
                    if (jObj != null) {
                        String status_response = jObj.getString("status");
                        if (status_response.equals("OK")) {
                            JSONObject jObect = jObj.getJSONObject("data");
                            Iterator<String> iter = jObect.keys();
                            listCategory.clear();
                            listVehicle.clear();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                System.out.println("key is: "+key);
                                try {
                                    if(existingJSONArray.length()>0 && existingJSONArray!=null){
                                        int vehicle_counter=0;
                                        for (int j=0;j<existingJSONArray.length();j++){
                                            if(existingJSONArray.get(j).toString().trim().length()>10){
                                                if(existingJSONArray.getJSONObject(j).getString("category").trim().equals(key)){
                                                    vehicle_counter=vehicle_counter+1;
                                                }
                                            }
                                        }
                                        listCategory.add(key+"::"+vehicle_counter+"::"+jObect.getJSONArray(key).length());
                                        listVehicle.add(jObect.getJSONArray(key));
                                    }else{
                                        listCategory.add(key+"::"+"0"+"::"+jObect.getJSONArray(key).length());
                                        listVehicle.add(jObect.getJSONArray(key));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            mAdapter.notifyDataSetChanged();
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
        }.method(AQuery.METHOD_GET));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveNext:
                break;
            case R.id.txtTruckCategoryName:
                int pos=Integer.parseInt(v.getTag().toString());
                try{
                    System.out.println("Cat Data: "+listVehicle.get(pos).toString());
                    startActivity(new Intent(appContext, VehicleSubCategoryActivity.class).putExtra("Vehicle_Sub_Cat_List", listVehicle.get(pos).toString()));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
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
    }
}
