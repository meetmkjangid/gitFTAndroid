package in.futuretrucks;

import android.app.Dialog;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.adapter.MaterialTypeJSONAdapter;
import in.futuretrucks.adapter.VehicleCategoryAdapter;
import in.futuretrucks.adapter.VehicleSubCategoryJSONAdapter;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.DriverRegistrationModel;
import in.futuretrucks.model.TruckRegisterModel;
import in.futuretrucks.model.TruckType;
import in.futuretrucks.response.DriverRegApiResponse;
import in.futuretrucks.response.TruckRegApiResponse;
import in.futuretrucks.services.DriverRegisterService;
import in.futuretrucks.services.TruckRegisterService;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TruckRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Context appContext;
    Toolbar mToolbar;
    ActionBar actionBar;
    private ProgressHUD progress;
    private ConnectionDetector cd;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterSubCat;
    private RecyclerView.LayoutManager mLayoutManager;
    Dialog dialogVehicleList,dialogVehicleSubCatList,dialogMakeYear,dialogManufacturer,dialogAxelType;
    int dialogReq=0;

    ArrayList<JSONArray> listVehicle=new ArrayList<JSONArray>();
    ArrayList<JSONObject> listVehicleSubCat=new ArrayList<JSONObject>();
    ArrayList<String> listCategory=new ArrayList<String>();
    ArrayList<String> lstOfMakeYear=new ArrayList<String>();
    ArrayList<String> lstOfManufacturer=new ArrayList<String>();
    ArrayList<String> lstOfAxelType=new ArrayList<String>();
    int reqDialog=0;
    boolean isVehicleSet=false;

    SimpleDateFormat mFormatter = new SimpleDateFormat("dd/MM/yy hh:mm aa");
    SimpleDateFormat mFormatter1 = new SimpleDateFormat("dd-MM-yyyy");

    TruckRegisterModel truck_reg_model=new TruckRegisterModel();

    private SlideDateTimeListener listenerDateTimePicker = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            try {

                Calendar c = Calendar.getInstance();
                String formattedDate = mFormatter.format(c.getTime());
                Date mDateCurrent = mFormatter.parse(formattedDate);
                Date mDate = mFormatter.parse(mFormatter.format(date).toString());

                if (mDate.compareTo(mDateCurrent)>0){
                    truck_reg_model.setPermitExpiryDate(mDate);
                    ((CustomEditText)findViewById(R.id.edtPermitExpiryDate)).setText("" + mFormatter1.format(mDate));
                }else{
                    Toast.makeText(appContext,"Permit expire date should be greate than current date.",Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_truck_register);
        appContext=this;
        initComponent();
    }

    private void initComponent() {
        cd=new ConnectionDetector(appContext);
        setActionBar();
        setClickListener();

        if(cd.isConnectingToInternet()){
            getVehicleList();
        }
        setMakeYearList();
        setManufacturerList();
        dialogVehicleList();
        dialogVehicleSubCatList();
    }


    private void setClickListener(){
        findViewById(R.id.btnRegisterNow).setOnClickListener(this);
        findViewById(R.id.edtSelectVehicleCategory).setOnClickListener(this);
        findViewById(R.id.edtSelectVehicleSubCategory).setOnClickListener(this);
        findViewById(R.id.edtManufacturer).setOnClickListener(this);
        findViewById(R.id.edtMakeYear).setOnClickListener(this);
        findViewById(R.id.edtAxleType).setOnClickListener(this);
        findViewById(R.id.edtPermitExpiryDate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edtPermitExpiryDate:
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listenerDateTimePicker)
                        .setInitialDate(new Date())
                                //.setMinDate(minDate)
                                //.setMaxDate(maxDate)
                                //.setIs24HourTime(true)
                                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                                //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
                break;

            case R.id.edtMakeYear:
                dialogReq=1;
                setMakeYearList();
                dialogMakeYear.show();
                break;
            case R.id.edtManufacturer:
                dialogReq=2;
                setManufacturerList();
                dialogManufacturer.show();
                break;
            case R.id.edtAxleType:
                dialogReq=3;
                setAxelTypeList();
                dialogAxelType.show();
                break;
            case R.id.edtSelectVehicleCategory:
                reqDialog=1;
                listVehicleSubCat.clear();
                ((CustomEditText)findViewById(R.id.edtSelectVehicleSubCategory)).setText("");
                dialogVehicleList.show();
                break;
            case R.id.edtSelectVehicleSubCategory:
                if(isVehicleSet){
                    reqDialog=2;
                    dialogVehicleSubCatList.show();
                }else{
                    Toast.makeText(appContext,"Please select vehicle first.",Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.txtTruckCategoryName:
                int pos=Integer.parseInt(v.getTag().toString());
                try{
                    if(reqDialog==1){
                        dialogVehicleList.dismiss();
                        listVehicleSubCat.clear();
                        ((CustomEditText)findViewById(R.id.edtSelectVehicleCategory)).setText(listCategory.get(pos));
                        isVehicleSet=true;
                        if(listVehicle.get(pos).length()>0 && listVehicle.get(pos)!=null){
                            for (int i=0;i<listVehicle.get(pos).length();i++){
                                listVehicleSubCat.add(listVehicle.get(pos).getJSONObject(i));
                            }
                        }
                        mAdapterSubCat.notifyDataSetChanged();
                    }else if(reqDialog==2){
                        dialogVehicleSubCatList.dismiss();
                        TruckType truck_type=new TruckType();
                        truck_type.setTruckType(listVehicleSubCat.get(pos).getString("truck_type"));
                        truck_type.setCode(listVehicleSubCat.get(pos).getString("code"));
                        truck_reg_model.setTruckType(truck_type);
                        ((CustomEditText) findViewById(R.id.edtSelectVehicleSubCategory)).setText("" + listVehicleSubCat.get(pos).getString("truck_type"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case R.id.txtWeight:
                int position=Integer.parseInt(v.getTag().toString());
                if(dialogReq==1){
                    dialogMakeYear.dismiss();
                    ((CustomEditText) findViewById(R.id.edtMakeYear)).setText(lstOfMakeYear.get(position));
                }else if(dialogReq==2){
                    dialogManufacturer.dismiss();
                    ((CustomEditText) findViewById(R.id.edtManufacturer)).setText(lstOfManufacturer.get(position));
                }else if(dialogReq==3){
                    dialogAxelType.dismiss();
                    ((CustomEditText) findViewById(R.id.edtAxleType)).setText(lstOfAxelType.get(position));
                }
                break;


            case R.id.btnRegisterNow:
                if(cd.isConnectingToInternet()){
                    String strRegisterNumber=((CustomEditText)findViewById(R.id.edtRegistrationNumber)).getText().toString().trim();
                    if(strRegisterNumber.length()==0){
                        Toast.makeText(appContext,"Registration Number field can't be blank.",Toast.LENGTH_LONG).show();
                    }else{
                        truck_reg_model.setIsNew(true);
                        truck_reg_model.setLicensePlateNum(strRegisterNumber);

                        if(((CustomEditText) findViewById(R.id.edtModel)).getText().toString().trim().length()>0){
                            truck_reg_model.setTruckModel(((CustomEditText) findViewById(R.id.edtModel)).getText().toString().trim());
                        }
                        truck_reg_model.setManufecturer(((CustomEditText) findViewById(R.id.edtManufacturer)).getText().toString().trim());
                        truck_reg_model.setMakeYear(((CustomEditText) findViewById(R.id.edtMakeYear)).getText().toString().trim());
                        truck_reg_model.setAxel(((CustomEditText) findViewById(R.id.edtAxleType)).getText().toString().toLowerCase().trim());


                        if(((RadioButton)findViewById(R.id.rbOpenBodyType)).isChecked()){
                            truck_reg_model.setBodyType("open");
                        }else{
                            truck_reg_model.setBodyType("close");
                        }

                        if(((CheckBox)findViewById(R.id.chkNationalPermit)).isChecked()){
                            truck_reg_model.setNatioalPermit(true);
                        }else{
                            truck_reg_model.setNatioalPermit(false);
                        }

                        if(((CheckBox)findViewById(R.id.chkRefrigeration)).isChecked()){
                            truck_reg_model.setRefrigeration(true);
                        }else{
                            truck_reg_model.setRefrigeration(false);
                        }

                        if(((CustomEditText) findViewById(R.id.edtCarryingCapacity)).getText().toString().toLowerCase().trim().length()>0){
                            truck_reg_model.setCapacity(Double.parseDouble(((CustomEditText) findViewById(R.id.edtCarryingCapacity)).getText().toString().toLowerCase().trim()));
                        }

                        truck_reg_model.setOwnerName(((CustomEditText) findViewById(R.id.edtTruckOwnerName)).getText().toString().trim());
                        truck_reg_model.setDriverName(((CustomEditText) findViewById(R.id.edtDriverName)).getText().toString().trim());

                        if(((CustomEditText) findViewById(R.id.edtTruckOwnerMobile)).getText().toString().trim().length()>0){
                            truck_reg_model.setOwnerMobile(Long.parseLong(((CustomEditText) findViewById(R.id.edtTruckOwnerMobile)).getText().toString().trim()));
                        }

                        if(((CustomEditText) findViewById(R.id.edtWidth)).getText().toString().trim().length()>0){
                            truck_reg_model.setWidth(Double.parseDouble(((CustomEditText) findViewById(R.id.edtWidth)).getText().toString().trim()));
                        }

                        if(((CustomEditText) findViewById(R.id.edtHeight)).getText().toString().trim().length()>0){
                            truck_reg_model.setHeight(Double.parseDouble(((CustomEditText) findViewById(R.id.edtHeight)).getText().toString().trim()));
                        }

                        if(((CustomEditText) findViewById(R.id.edtLenght)).getText().toString().trim().length()>0){
                            truck_reg_model.setLength(Double.parseDouble(((CustomEditText) findViewById(R.id.edtLenght)).getText().toString().trim()));
                        }


                        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                // TODO Auto-generated method stub
                            }
                        });

                        String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                        TruckRegisterService truckService = ServiceGenerator.createService(TruckRegisterService.class);
                        Call<TruckRegApiResponse> call = truckService.registerTruck(truck_reg_model, jwt);
                        call.enqueue(new Callback<TruckRegApiResponse>() {
                            @Override
                            public void onResponse(Response<TruckRegApiResponse> response, Retrofit retrofit) {
                                TruckRegApiResponse truck_api_reg_response=new TruckRegApiResponse();
                                truck_api_reg_response=response.body();
                                if(truck_api_reg_response!=null){
                                    if(truck_api_reg_response.getStatus().equalsIgnoreCase("OK")){
                                        PreferenceClass.setBooleanPreference(appContext, "Truck_List_Update",true);
                                        PreferenceClass.setStringPreference(appContext, Constant.NEW_TRUCK_REG_ID, "" + truck_api_reg_response.getData().getId().trim());
                                        startActivity(new Intent(appContext,UploadTruckDocumentsActivity.class));
                                        finish();
                                        Toast.makeText(appContext, "Truck Registered successfully", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(appContext, "Truck Register failed", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(appContext, "Truck Register failed", Toast.LENGTH_LONG).show();
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
                                System.out.println("Driver Reg OnFailer: " + t.getMessage());
                            }
                        });

                    }
                }else{
                    Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void dialogVehicleList(){
        dialogVehicleList=new Dialog(appContext);
        dialogVehicleList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogVehicleList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogVehicleList.setContentView(R.layout.dialog_vehicle_list);
        dialogVehicleList.setCancelable(true);

        mRecyclerView = (RecyclerView)dialogVehicleList.findViewById(R.id.recyclerTruckTypesList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleCategoryAdapter(appContext,2, listCategory,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    private void dialogVehicleSubCatList(){
        dialogVehicleSubCatList=new Dialog(appContext);
        dialogVehicleSubCatList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogVehicleSubCatList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogVehicleSubCatList.setContentView(R.layout.dialog_vehicle_list);
        dialogVehicleSubCatList.setCancelable(true);



        mRecyclerView = (RecyclerView)dialogVehicleSubCatList.findViewById(R.id.recyclerTruckTypesList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapterSubCat = new VehicleSubCategoryJSONAdapter(appContext,1, listVehicleSubCat,this);
        mRecyclerView.setAdapter(mAdapterSubCat);
        mAdapterSubCat.notifyDataSetChanged();

    }

    private void setMakeYearList(){
        lstOfMakeYear.clear();

        lstOfMakeYear.add("2000");
        lstOfMakeYear.add("2001");
        lstOfMakeYear.add("2002");
        lstOfMakeYear.add("2003");
        lstOfMakeYear.add("2004");
        lstOfMakeYear.add("2005");
        lstOfMakeYear.add("2006");
        lstOfMakeYear.add("2007");
        lstOfMakeYear.add("2008");
        lstOfMakeYear.add("2009");
        lstOfMakeYear.add("2010");
        lstOfMakeYear.add("2011");
        lstOfMakeYear.add("2012");
        lstOfMakeYear.add("2013");
        lstOfMakeYear.add("2014");
        lstOfMakeYear.add("2015");
        lstOfMakeYear.add("2016");

        dialogMakeYear=new Dialog(appContext);
        dialogMakeYear.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMakeYear.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogMakeYear.setContentView(R.layout.dialog_select_weight);
        dialogMakeYear.setCancelable(true);

        ((CustomTextView)dialogMakeYear.findViewById(R.id.txtSelectWeight)).setText("Select Make Year");

        mRecyclerView = (RecyclerView)dialogMakeYear.findViewById(R.id.recyclerWeightList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleCategoryAdapter(appContext,3, lstOfMakeYear,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }


    private void setManufacturerList(){
        lstOfManufacturer.clear();
        lstOfManufacturer.add("Ashok Leyland");
        lstOfManufacturer.add("Tata Motors");
        lstOfManufacturer.add("Bharat Benz");
        lstOfManufacturer.add("Eicher Motors");
        lstOfManufacturer.add("Force Motors");
        lstOfManufacturer.add("Mahindra Navistar");
        lstOfManufacturer.add("Swaraj Mazda");
        lstOfManufacturer.add("Premier Automobiles");
        lstOfManufacturer.add("Hindustan Motors");
        lstOfManufacturer.add("Tata Daewoo");
        lstOfManufacturer.add("Asia motors works");

        dialogManufacturer=new Dialog(appContext);
        dialogManufacturer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogManufacturer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogManufacturer.setContentView(R.layout.dialog_select_weight);
        dialogManufacturer.setCancelable(true);

        ((CustomTextView)dialogManufacturer.findViewById(R.id.txtSelectWeight)).setText("Select Manufacturer Type");

        mRecyclerView = (RecyclerView)dialogManufacturer.findViewById(R.id.recyclerWeightList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleCategoryAdapter(appContext,3, lstOfManufacturer,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    private void setAxelTypeList(){
        lstOfAxelType.clear();
        lstOfAxelType.add("Single");
        lstOfAxelType.add("Multi");

        dialogAxelType=new Dialog(appContext);
        dialogAxelType.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAxelType.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogAxelType.setContentView(R.layout.dialog_select_weight);
        dialogAxelType.setCancelable(true);

        ((CustomTextView)dialogAxelType.findViewById(R.id.txtSelectWeight)).setText("Select Axel Type");

        mRecyclerView = (RecyclerView)dialogAxelType.findViewById(R.id.recyclerWeightList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleCategoryAdapter(appContext,3, lstOfAxelType,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

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
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    listCategory.add(key);
                                    listVehicle.add(jObect.getJSONArray(key));
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
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setText("Truck Register");
    }
}


/*if(v.getId()==R.id.btnRegisterNow){
            *//*String strRegisNum=((CustomEditText)findViewById(R.id.edtRegistrationNumber)).getText().toString().trim();
            String strTruckType=((CustomEditText)findViewById(R.id.edtTruckType)).getText().toString().trim();
            String strMakeModel=((CustomEditText)findViewById(R.id.edtMakeModel)).getText().toString().trim();
            String strManufacturer=((CustomEditText)findViewById(R.id.edtManufacturer)).getText().toString().trim();
            String strHeight=((CustomEditText)findViewById(R.id.edtHeight)).getText().toString().trim();
            String strWidth=((CustomEditText)findViewById(R.id.edtWidth)).getText().toString().trim();
            String strRefrigeration=((CustomEditText)findViewById(R.id.edtRefrigeration)).getText().toString().trim();
            String strCCapacity=((CustomEditText)findViewById(R.id.edtCarryingCapacity)).getText().toString().trim();
            String strBodyType=((CustomEditText)findViewById(R.id.edtBodyType)).getText().toString().trim();
            String strLength=((CustomEditText)findViewById(R.id.edtLenght)).getText().toString().trim();
            String strAxle=((CustomEditText)findViewById(R.id.edtAxleType)).getText().toString().trim();
            String strSMSLang=((CustomEditText)findViewById(R.id.edtSMSLanguage)).getText().toString().trim();


            CheckBox chkBox=((CheckBox) findViewById(R.id.chkAcceptTermsCondition));

            if(cd.isConnectingToInternet()){
                if(chkBox.isChecked()){
                    Toast.makeText(appContext,"Done",Toast.LENGTH_LONG).show();
                    //registerTruck(strRegisNum, strTruckType, strMakeModel, strManufacturer, strHeight, strWidth, strRefrigeration, strCCapacity, strBodyType, strLength, strAxle, strSMSLang);
                }else{
                    Toast.makeText(appContext,R.string.terms_cond,Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
            }*//*
        }*/

