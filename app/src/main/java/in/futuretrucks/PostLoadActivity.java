package in.futuretrucks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.adapter.MaterialTypeJSONAdapter;
import in.futuretrucks.adapter.VehicleCategoryAdapter;
import in.futuretrucks.adapter.VehicleSubCategoryJSONAdapter;
import in.futuretrucks.customviews.CustomAutoCompleteTextView;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.AddressComponents;
import in.futuretrucks.model.AdvertiseLoad;
import in.futuretrucks.model.AdvertiseLoadApiResponse;
import in.futuretrucks.model.AdvertiseTruck;
import in.futuretrucks.model.AdvertiseTruckApiResponse;
import in.futuretrucks.model.Destination;
import in.futuretrucks.model.GooglePlacesApiResponse;
import in.futuretrucks.model.MaterialType;
import in.futuretrucks.model.Source;
import in.futuretrucks.model.VehicleType;
import in.futuretrucks.services.AdvertiseLoadService;
import in.futuretrucks.services.AdvertiseTruckService;
import in.futuretrucks.services.GooglePlacesApiService;
import in.futuretrucks.services.GooglePlacesServiceGenerator;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PlaceJSONParser;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PostLoadActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @Override
    public void onConnected(Bundle bundle) {
        //   mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
        //           mGoogleApiClient);

        if (mLastLocation != null) {
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //          mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            Toast.makeText(appContext, "Lat " + mLastLocation.getLatitude() + "and lng " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();

        }else{
            createLocationRequest();
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if (mLastLocation!=null){
            Toast.makeText(appContext,"Lat "+mLastLocation.getLatitude() +"and lng "+mLastLocation.getLongitude(),Toast.LENGTH_LONG).show();
        }
    }

    public interface OnSearchCompleteListener{
        public void onSearchComplete(HashMap<String, String> placedata);
    }

    private OnSearchCompleteListener mOnSearchCompleteListener;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        if(mLocationRequest!=null)
            //     LocationServices.FusedLocationApi.requestLocationUpdates(
            //             mGoogleApiClient, mLocationRequest, this);
            Log.d("", "Location update started ..............: ");
    }

    public  void setmOnSearchCompleteListener(OnSearchCompleteListener l){
        this.mOnSearchCompleteListener = l;
    }
    PlacesTask placesTask;
    ParserTask parserTask;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private CustomAutoCompleteTextView mCustomAutoCompleteTextViewSource;
    private CustomAutoCompleteTextView mCustomAutoCompleteTextViewDestination;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    int reqLocationPick=0;
    SimpleAdapter adapter=null;

    Toolbar mToolbar;
    ActionBar actionBar;
    Context appContext;
    private ConnectionDetector cd;

    private ProgressHUD progress;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterSubCat;
    private RecyclerView.Adapter mAdapterMaterialTypes;
    private RecyclerView.LayoutManager mLayoutManager;
    Dialog dialogVehicleList,dialogVehicleSubCatList,dialogMaterialTypes,dialogNoOfTrucksList;

    ArrayList<JSONArray> listVehicle=new ArrayList<JSONArray>();
    ArrayList<JSONObject> listMaterialType=new ArrayList<JSONObject>();
    ArrayList<JSONObject> listVehicleSubCat=new ArrayList<JSONObject>();
    ArrayList<String> listCategory=new ArrayList<String>();
    ArrayList<String> listNoOfTrucks=new ArrayList<String>();
    int reqDialog=0;
    SimpleDateFormat mFormatter = new SimpleDateFormat("dd/MM/yy hh:mm aa");
    SimpleDateFormat mFormatter1 = new SimpleDateFormat("dd-MM-yyyy");
    boolean isVehicleSet=false;

    float vehicle_capacity=0;
    float total_capacity=0;

    AdvertiseLoad advertiseLoadBoardModel=new AdvertiseLoad();
    private boolean isSetSource=false,isSetDestination=false,isSetVehicle=false,isSetScheduleDate=false,isSetMaterial=false;
    private GooglePlacesApiResponse googlePlacesApiResponse;
    Source source = new Source();
    List<Destination> destinationlist = new ArrayList<Destination>() ;
    Destination destination= new Destination();
    MaterialType material_type_model=new MaterialType();
    AdvertiseLoadApiResponse advertiseLoadBoardAPIRes = new AdvertiseLoadApiResponse();
    in.futuretrucks.model.DatePosting datePosting = new in.futuretrucks.model.DatePosting();
    VehicleType vt;
    AddressComponents ac;
    AddressComponents ac1;

    private SlideDateTimeListener listenerDateTimePicker = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            try {

                Calendar c = Calendar.getInstance();
                String formattedDate = mFormatter.format(c.getTime());
                Date mDateCurrent = mFormatter.parse(formattedDate);
                Date mDate = mFormatter.parse(mFormatter.format(date).toString());

                if (mDate.compareTo(mDateCurrent)<0){
                    Toast.makeText(appContext,"Available date & time should be greater than current date & time",Toast.LENGTH_LONG).show();
                }else{
                    isSetScheduleDate=true;
                    datePosting.setFrom(mDate);
                    advertiseLoadBoardModel.setDate(datePosting);
                    ((CustomEditText) findViewById(R.id.edtScheduledDate)).setText("" + mFormatter1.format(mDate));
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
        setContentView(R.layout.activity_post_load);
        appContext=this;
        initComponent();

    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        ((CustomEditText)findViewById(R.id.edtSelectVehicleCategory)).setOnClickListener(this);
        ((CustomEditText)findViewById(R.id.edtSelectVehicleSubCategory)).setOnClickListener(this);
        ((CustomEditText)findViewById(R.id.edtScheduledDate)).setOnClickListener(this);
        ((CustomEditText)findViewById(R.id.edtSelectVehicleNumber)).setOnClickListener(this);
        ((CustomEditText)findViewById(R.id.edtMaterialType)).setOnClickListener(this);
        ((CustomButton)findViewById(R.id.btnSubmit)).setOnClickListener(this);

        setActionBar();
        setNoOfTrucksListValue();
        initLocationGoogleAPIs();
        dialogVehicleList();
        dialogVehicleSubCatList();
        dialogdialogMaterialTypes();
        dialogNoOfTrucksList();
        if(cd.isConnectingToInternet()){
            getVehicleList();
            getMaterialTypeList();
        }else{
            Toast.makeText(appContext, R.string.check_network, Toast.LENGTH_LONG).show();
        }

        hideSoftKeyboard();

        try{
            //Set Current System Date in available date
            Calendar c = Calendar.getInstance();
            String formattedDate = mFormatter.format(c.getTime());
            Date mDateCurrent = mFormatter.parse(formattedDate);
            ((CustomEditText)findViewById(R.id.edtScheduledDate)).setText(""+mFormatter1.format(mDateCurrent));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setNoOfTrucksListValue(){
        listNoOfTrucks.add("1");
        listNoOfTrucks.add("2");
        listNoOfTrucks.add("3");
        listNoOfTrucks.add("4");
        listNoOfTrucks.add("5");
        listNoOfTrucks.add("6");
        listNoOfTrucks.add("7");
        listNoOfTrucks.add("8");
        listNoOfTrucks.add("9");
        listNoOfTrucks.add("10");
        listNoOfTrucks.add("11");
        listNoOfTrucks.add("12");
        listNoOfTrucks.add("13");
        listNoOfTrucks.add("14");
        listNoOfTrucks.add("15");
        listNoOfTrucks.add("16");
        listNoOfTrucks.add("17");
        listNoOfTrucks.add("18");
        listNoOfTrucks.add("19");
        listNoOfTrucks.add("20");

        ((CustomEditText)findViewById(R.id.edtSelectVehicleNumber)).setText(listNoOfTrucks.get(0));

        //Textwatcher for enter weight
        ((CustomEditText)findViewById(R.id.edtSelectVehicleWeight)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    float enter_weight = Float.parseFloat(charSequence.toString());
                    if (enter_weight > total_capacity) {
                        ((CustomEditText) findViewById(R.id.edtSelectVehicleWeight)).setText("" + total_capacity);
                        Toast.makeText(appContext, "Weight should be less than vehicle capacity.Select another vehicle or enter low weight.", Toast.LENGTH_LONG).show();
                    }
                    int cur = ((CustomEditText) findViewById(R.id.edtSelectVehicleWeight)).getText().toString().trim().length();
                    ((CustomEditText) findViewById(R.id.edtSelectVehicleWeight)).setSelection(cur);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initLocationGoogleAPIs(){
        mGoogleApiClient = new GoogleApiClient.Builder(appContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)

                .build();

        mCustomAutoCompleteTextViewSource = (CustomAutoCompleteTextView)findViewById(R.id.atvSource);
        mCustomAutoCompleteTextViewDestination = (CustomAutoCompleteTextView)findViewById(R.id.atvDestination);
        mCustomAutoCompleteTextViewSource.setThreshold(1);
        mCustomAutoCompleteTextViewDestination.setThreshold(1);

        mCustomAutoCompleteTextViewSource.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reqLocationPick = 1;
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mCustomAutoCompleteTextViewDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reqLocationPick = 2;
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCustomAutoCompleteTextViewSource.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (mCustomAutoCompleteTextViewSource.getText().toString().trim().length() > 0)
                        getLatLng(mCustomAutoCompleteTextViewSource.getText().toString());
                }
                return false;
            }
        });

        mCustomAutoCompleteTextViewDestination.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (mCustomAutoCompleteTextViewDestination.getText().toString().trim().length() > 0)
                        getLatLng(mCustomAutoCompleteTextViewDestination.getText().toString());
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                        isSetVehicle=true;
                        vehicle_capacity=Float.parseFloat(listVehicleSubCat.get(pos).getString("capacity"));
                        total_capacity=vehicle_capacity*Integer.parseInt(((CustomEditText) findViewById(R.id.edtSelectVehicleNumber)).getText().toString().trim());
                        ((CustomEditText) findViewById(R.id.edtSelectVehicleWeight)).setText(""+total_capacity);
                        ((CustomEditText) findViewById(R.id.edtSelectVehicleSubCategory)).setText(listVehicleSubCat.get(pos).getString("truck_type"));
                        vt = new VehicleType(listVehicleSubCat.get(pos).getString("truck_type"),listVehicleSubCat.get(pos).getString("code"),listVehicleSubCat.get(pos).getString("category"),listVehicleSubCat.get(pos).getString("capacity"));
                        advertiseLoadBoardModel.setVehicleType(vt);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.edtScheduledDate:
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

            case R.id.edtSelectVehicleNumber:
                ((CustomTextView)dialogNoOfTrucksList.findViewById(R.id.txtSelectWeight)).setText(R.string.SELECT_TRUCK_NO);
                dialogNoOfTrucksList.show();
                break;

            case R.id.txtWeight:
                dialogNoOfTrucksList.dismiss();
                int position=Integer.parseInt(v.getTag().toString());
                ((CustomEditText) findViewById(R.id.edtSelectVehicleNumber)).setText(listNoOfTrucks.get(position));

                total_capacity=vehicle_capacity*Integer.parseInt(listNoOfTrucks.get(position));
                ((CustomEditText) findViewById(R.id.edtSelectVehicleWeight)).setText("" + total_capacity);
                break;

            case R.id.edtMaterialType:
                ((CustomTextView)dialogMaterialTypes.findViewById(R.id.txtSelectWeight)).setText(R.string.SELECT_MATERIAL_TYPE);
                dialogMaterialTypes.show();
                break;

            case R.id.txtMaterialType:
                dialogMaterialTypes.dismiss();
                int pos1=Integer.parseInt(v.getTag().toString());
                try{
                    material_type_model.setMaterialType(listMaterialType.get(pos1).getString("material_type"));
                    material_type_model.setCode(listMaterialType.get(pos1).getString("code"));
                    material_type_model.setId(listMaterialType.get(pos1).getString("_id"));
                    isSetMaterial=true;
                    advertiseLoadBoardModel.setMaterialType(material_type_model);
                    ((CustomEditText) findViewById(R.id.edtMaterialType)).setText(listMaterialType.get(pos1).getString("material_type"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.btnSubmit:
                if(!isSetSource){
                    Toast.makeText(appContext,"Please select source location.",Toast.LENGTH_LONG).show();
                }else if(!isSetDestination){
                    Toast.makeText(appContext,"Please select destination location.",Toast.LENGTH_LONG).show();
                }else if(!isSetVehicle){
                    Toast.makeText(appContext,"Please select vehicle type.",Toast.LENGTH_LONG).show();
                }else if(!isSetMaterial){
                    Toast.makeText(appContext,"Please select material type.",Toast.LENGTH_LONG).show();
                }else if(!isSetScheduleDate){
                    Toast.makeText(appContext,"Please select schedule date.",Toast.LENGTH_LONG).show();
                }else{
                    if(cd.isConnectingToInternet()){
                        advertiseLoadBoardModel.setNoOfTrucks(Integer.parseInt(((CustomEditText)findViewById(R.id.edtSelectVehicleNumber)).getText().toString().trim()));
                        advertiseLoadBoardModel.setType("load");
                        if(((CheckBox)findViewById(R.id.chkOffer)).isChecked()){
                            advertiseLoadBoardModel.setOffer("1");
                        }else{
                            advertiseLoadBoardModel.setOffer("0");
                        }
                        String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                        final AdvertiseLoadService adv_load_service = ServiceGenerator.createService(AdvertiseLoadService.class);

                        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                // TODO Auto-generated method stub
                            }
                        });

                        Call<AdvertiseLoadApiResponse> call = adv_load_service.advertiseLoad(advertiseLoadBoardModel,jwt);
                        call.enqueue(new Callback<AdvertiseLoadApiResponse>() {
                            @Override
                            public void onResponse(Response<AdvertiseLoadApiResponse> response, Retrofit retrofit) {
                                advertiseLoadBoardAPIRes = response.body();
                                if(advertiseLoadBoardAPIRes.getPostingId()!=null){
                                    if(advertiseLoadBoardAPIRes.getPostingId().trim().length()>0){
                                        Toast.makeText(appContext,"Load advertising successfully.",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }else{
                                    Toast.makeText(appContext,"Truck advertising failed.",Toast.LENGTH_LONG).show();
                                }

                                if (progress.isShowing() && progress != null) {
                                    progress.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(appContext,"Truck advertising failed.",Toast.LENGTH_LONG).show();
                                if (progress.isShowing() && progress != null) {
                                    progress.dismiss();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
                    }
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

    private void dialogNoOfTrucksList(){
        dialogNoOfTrucksList=new Dialog(appContext);
        dialogNoOfTrucksList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNoOfTrucksList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogNoOfTrucksList.setContentView(R.layout.dialog_select_weight);
        dialogNoOfTrucksList.setCancelable(true);

        mRecyclerView = (RecyclerView)dialogNoOfTrucksList.findViewById(R.id.recyclerWeightList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleCategoryAdapter(appContext,3, listNoOfTrucks,this);
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

    private void dialogdialogMaterialTypes(){
        dialogMaterialTypes=new Dialog(appContext);
        dialogMaterialTypes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMaterialTypes.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogMaterialTypes.setContentView(R.layout.dialog_select_weight);
        dialogMaterialTypes.setCancelable(true);


        mRecyclerView = (RecyclerView)dialogMaterialTypes.findViewById(R.id.recyclerWeightList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(appContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapterMaterialTypes = new MaterialTypeJSONAdapter(appContext,1, listMaterialType,this);
        mRecyclerView.setAdapter(mAdapterMaterialTypes);
        mAdapterMaterialTypes.notifyDataSetChanged();

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

    public void getMaterialTypeList() {

        final AQuery aq = new AQuery(appContext);
        String url = Constant.BASE_URL+ Constant.GET_MATERIAL_TYPES;

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jObj, AjaxStatus status) {
                try {
                    if (jObj != null) {
                        String status_response = jObj.getString("status");
                        if (status_response.equals("OK")) {
                            JSONArray jsonArrayMaterial = jObj.getJSONArray("data");
                            for(int i=0;i<jsonArrayMaterial.length();i++){
                                JSONObject jObjMaterial=jsonArrayMaterial.getJSONObject(i);
                                listMaterialType.add(jObjMaterial);
                            }
                            mAdapterMaterialTypes.notifyDataSetChanged();
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
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setText("Advertise Load");
    }

    //PLace API Code

    private void getLatLng(String address)
    {
        final ProgressDialog mpProgressDialog = ProgressDialog.show(appContext,"","Finding location...");
        try {
            address = URLEncoder.encode(address, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //http://maps.googleapis.com/maps/api/geocode/json?latlng=22.636599,75.815493&sensor=true
        String url =  "http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=true";
        //String url =  "http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=true_or_false";

        AQuery aQuery = new AQuery(appContext);
        aQuery.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                mpProgressDialog.cancel();
                if (status.getCode() == AjaxStatus.NETWORK_ERROR) {
                    Toast.makeText(appContext,"Check network please",Toast.LENGTH_LONG).show();
                    //Constant.showAlertDialogCheck(Constant.DIALOG_SERVICE_UNABLE, Constant.MSG_SERVICE_UNABLE, getActivity(), false);
                } else if (object != null) {
                    try {
                        getFragmentManager().popBackStackImmediate();
                        JSONArray jsonObject = object.getJSONArray("results");
                        System.out.println("Location jsonObject: "+jsonObject);
                        HashMap<String, String> mapData = new HashMap<String, String>();
                        if (jsonObject.getJSONObject(0).getJSONArray("address_components").length()>0){
                            JSONObject jsonObject1 = jsonObject.getJSONObject(0).getJSONArray("address_components").getJSONObject(0);
                            //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJqz6o5HMCYzkR22kri6fp2pk&key=AIzaSyAt0BvPkl0ASgbLMVEJihdrA8KyVaKf2Nk
                            mapData.put("formatted_address",jsonObject1.getString("long_name"));
                        }else{
                            mapData.put("formatted_address",jsonObject.getJSONObject(0).getString("formatted_address"));
                        }


                        if(reqLocationPick==1){
                            mapData.put("address", mCustomAutoCompleteTextViewSource.getText().toString());
                            mapData.put("lat",jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                            mapData.put("lng", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));

                            PreferenceClass.setStringPreference(appContext, "source_lat", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                            PreferenceClass.setStringPreference(appContext, "source_lng", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                        }else{
                            mapData.put("address", mCustomAutoCompleteTextViewDestination.getText().toString());
                            mapData.put("lat",jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                            mapData.put("lng", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));

                            PreferenceClass.setStringPreference(appContext, "dest_lat", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                            PreferenceClass.setStringPreference(appContext, "dest_lng", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                        }

                        if (mOnSearchCompleteListener!=null){
                            mOnSearchCompleteListener.onSearchComplete(mapData);
                        }
                        PreferenceClass.setStringPreference(appContext, "lat", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                        PreferenceClass.setStringPreference(appContext, "lng", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    //{   "error_message" : "This IP, site or mobile application is not authorized to use this API key. Request received from IP address 103.231.44.60, with empty referer",   "predictions" : [],   "status" : "REQUEST_DENIED"}
    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key="+ Constant.GOOGLE_PLACE_API_KEY;
            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            String location = "location=22.7000,75.9000";
//
            String radious = "radius=100000";
            // Building the parameters to the web service
            String parameters = input+"&"+location+"&"+radious+"&"+types+"&"+sensor+"&"+key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
                Toast.makeText(appContext,e.toString(),Toast.LENGTH_LONG).show();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
                Toast.makeText(appContext,e.toString(),Toast.LENGTH_LONG).show();
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            if ( result!=null  && result.size()>0 && result.get(0).containsKey("error"))
            {
                Toast.makeText(appContext,result.get(0).get("error"),Toast.LENGTH_LONG).show();

            }else if(result!=null  &&  result.size()>0)
            {
                String[] from = new String[] { "description"};
                int[] to = new int[] { android.R.id.text1 };

                try {
                    // Creating a SimpleAdapter for the AutoCompleteTextView
                    adapter= new SimpleAdapter(appContext, result, android.R.layout.simple_list_item_1, from, to);


                    if(reqLocationPick==1){
                        mCustomAutoCompleteTextViewSource.setAdapter(adapter);
                        mCustomAutoCompleteTextViewSource.setOnItemClickListener(onItemClickLocation);
                    }else{
                        mCustomAutoCompleteTextViewDestination.setAdapter(adapter);
                        mCustomAutoCompleteTextViewDestination.setOnItemClickListener(onItemClickLocation);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
    }

    AdapterView.OnItemClickListener onItemClickLocation=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final HashMap<String, String> map = (HashMap<String, String>)adapter.getItem(position);
            // Constant.hideKeyboard(mCustomAutoCompleteTextView, getActivity());
            System.out.println("record " + map + " \nid " + map.get("placeid"));

            String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+map.get("placeid")+"&key="+ Constant.GOOGLE_PLACE_API_KEY;;

            //String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + map.get("placeid") + "&key=" + "AIzaSyDYnG93I3yDgkGUmF8PHEA43smGXjhK2Gs";

            //Retrofitting gogleplacesapi


            if (reqLocationPick == 1){
                GooglePlacesApiService service = GooglePlacesServiceGenerator.createService(GooglePlacesApiService.class);
                String placeId = map.get("placeid");
                String key = Constant.GOOGLE_PLACE_API_KEY;
                Call<GooglePlacesApiResponse> call = service.getPlaces(placeId,key);
                call.enqueue(new Callback<GooglePlacesApiResponse>() {
                    @Override
                    public void onResponse(Response<GooglePlacesApiResponse> response, Retrofit retrofit) {
                        googlePlacesApiResponse = response.body();
                        Log.e("googleplace", googlePlacesApiResponse.toString());

                        ac = new AddressComponents();
                        //    source.setAddressComponents(googlePlacesApiResponse.getResult().getAddressComponents().);
                        for(int i=0; i<googlePlacesApiResponse.getResult().getAddressComponents().size(); i++){
                            Log.e("googleplac****",googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0));
                            Log.e("googleplac****",googlePlacesApiResponse.getResult().getAddressComponents().get(i).getShortName().toString());
                            if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("street_number")){
                                ac.setStreetNumber(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getShortName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("route")){
                                ac.setRoute(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("sublocality_level_2")){
                                ac.setSublocalityLevel2(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase( "sublocality_level_1")){
                                ac.setSublocalityLevel1(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("locality")){
                                ac.setLocality(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("administrative_area_level_2")){
                                ac.setAdministrativeAreaLevel2(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("administrative_area_level_1")){
                                ac.setAdministrativeAreaLevel1(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getShortName().toString());
                                ac.setAdministrativeAreaLevel1F(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("country")){
                                ac.setCountry(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getShortName().toString());
                                ac.setCountryF(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("postal_code")){
                                ac.setPostalCode(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                        }
                        isSetSource=true;
                        source.setAddressComponents(ac);
                        source.setFormattedAddress(googlePlacesApiResponse.getResult().getFormattedAddress());
                        source.setPlaceId(googlePlacesApiResponse.getResult().getPlaceId());
                        source.setGeometry(googlePlacesApiResponse.getResult().getGeometry());
                        source.setUrl(googlePlacesApiResponse.getResult().getUrl());
                        source.setVicinity(googlePlacesApiResponse.getResult().getVicinity());
                        source.setTypes(googlePlacesApiResponse.getResult().getTypes());
                        advertiseLoadBoardModel.setSource(source);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
            else if (reqLocationPick == 2){
                GooglePlacesApiService service = GooglePlacesServiceGenerator.createService(GooglePlacesApiService.class);
                String placeId = map.get("placeid");
                String key = Constant.GOOGLE_PLACE_API_KEY;
                Call<GooglePlacesApiResponse> call = service.getPlaces(placeId,key);
                call.enqueue(new Callback<GooglePlacesApiResponse>() {
                    @Override
                    public void onResponse(Response<GooglePlacesApiResponse> response, Retrofit retrofit) {
                        googlePlacesApiResponse = response.body();


                        ac1 = new AddressComponents();

                        //    source.setAddressComponents(googlePlacesApiResponse.getResult().getAddressComponents().);
                        for(int i=0; i<googlePlacesApiResponse.getResult().getAddressComponents().size(); i++){
                            if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("street_number")){
                                ac1.setStreetNumber(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getShortName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("route")){
                                ac1.setRoute(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("sublocality_level_2")){
                                ac1.setSublocalityLevel2(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("sublocality_level_1")){
                                ac1.setSublocalityLevel1(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("locality")){
                                ac1.setLocality(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) == "administrative_area_level_2"){
                                ac1.setAdministrativeAreaLevel2(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("administrative_area_level_1")){
                                ac1.setAdministrativeAreaLevel1(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getShortName().toString());
                                ac1.setAdministrativeAreaLevel1F(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase( "country")){
                                ac1.setCountry(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getShortName().toString());
                                ac1.setCountryF(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                            else if (googlePlacesApiResponse.getResult().getAddressComponents().get(i).getTypes().get(0) .equalsIgnoreCase("postal_code")){
                                ac1.setPostalCode(googlePlacesApiResponse.getResult().getAddressComponents().get(i).getLongName().toString());
                            }
                        }
                        isSetDestination=true;
                        destination.setAddressComponents(ac1);
                        destinationlist.add(0,destination);
                        destination.setFormattedAddress(googlePlacesApiResponse.getResult().getFormattedAddress());
                        destination.setPlaceId(googlePlacesApiResponse.getResult().getPlaceId());
                        destination.setGeometry(googlePlacesApiResponse.getResult().getGeometry());
                        destination.setUrl(googlePlacesApiResponse.getResult().getUrl());
                        destination.setVicinity(googlePlacesApiResponse.getResult().getVicinity());
                        destination.setTypes(googlePlacesApiResponse.getResult().getTypes());
                        advertiseLoadBoardModel.setDestination(destinationlist);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        }
    };

}