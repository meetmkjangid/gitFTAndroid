package in.futuretrucks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomAutoCompleteText;
import in.futuretrucks.customviews.CustomAutoCompleteTextView;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.City;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PlaceJSONParser;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

/**
 * Created by mahadev on 12/21/15.
 */
public class AddCityActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    Toolbar mToolbar;
    ActionBar actionBar;
    Context appContext;
    ArrayList<JSONObject> listCity;
    ArrayList<String> listStrCity;
    private ProgressHUD progress;
    CustomAutoCompleteText cityAutoComplete;
    ArrayAdapter<String> cityAutoCompleteAdapter;
    JSONObject selectedCityObject=new JSONObject();
    JSONObject finalCityObject=new JSONObject();
    ConnectionDetector cd;
    City city;

    JSONObject existingCityObj=null;
    JSONArray existingCityArray = null;
    private boolean isAddCity=false;


    PlacesTask placesTask;
    ParserTask parserTask;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private CustomAutoCompleteTextView mCustomAutoCompleteTextView;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;


    String strAddress="";
    String strCity="";
    String strDistict="";
    String strState="";
    String strPostalCode="";

    @Override
    public void onConnected(Bundle bundle) {

        if (mLastLocation != null) {
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        appContext=this;
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        initComponent();
    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        setActionBar();
        setListener();
        setDataForCity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCityInfo();
    }

    private void setDataForCity(){
        mGoogleApiClient = new GoogleApiClient.Builder(appContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)

                .build();

        mCustomAutoCompleteTextView = (CustomAutoCompleteTextView)findViewById(R.id.autotextCity);
        mCustomAutoCompleteTextView.setThreshold(1);

        mCustomAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCustomAutoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){

                    if(mCustomAutoCompleteTextView.getText().toString().trim().length()>0)
                        getLatLng(mCustomAutoCompleteTextView.getText().toString());
                }
                return false;
            }
        });
    }

    private void setCityInfo(){

        try {

            existingCityObj= new JSONObject(PreferenceClass.getStringPreferences(appContext, Constant.USER_NON_CUST_VALUES));
            if (PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST) == Constant.INTERESTED_CITY) {
                ((CustomTextView) findViewById(R.id.txtSubject)).setText(R.string.Interested_City);
                existingCityArray = existingCityObj.getJSONArray("interested_cities");
            }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.DAILY_SERVICE_IN_CITY){
                ((CustomTextView)findViewById(R.id.txtSubject)).setText(R.string.Daily_Services_in_City);
                existingCityArray = existingCityObj.getJSONArray("daily_serv_cities");
            }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.BRANCH_IN_CITY){
                ((CustomTextView)findViewById(R.id.txtSubject)).setText(R.string.Branch_Office_in_Cities);
                existingCityArray = existingCityObj.getJSONArray("branch_cities");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setListener(){
        ((CustomButton)findViewById(R.id.btnNext)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                try{
                    if(!isAddCity){
                        isAddCity=true;

                        JSONObject cityObject=new JSONObject();

                        if(strPostalCode.trim().length()==0){
                            strPostalCode=((CustomEditText)findViewById(R.id.edtAddCityPin)).getText().toString().trim();
                        }

                        finalCityObject.putOpt("city",selectedCityObject);
                        finalCityObject.putOpt("pincode",strPostalCode);

                        System.out.println("finalCityObject:  "+finalCityObject);
                        existingCityArray.put(finalCityObject);
                        if (PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST) == Constant.INTERESTED_CITY) {
                            existingCityObj.putOpt("interested_cities",existingCityArray);
                            System.out.println("existingCityArray: " + existingCityArray);
                            System.out.println("existingCityObj: " + existingCityObj);
                        }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.DAILY_SERVICE_IN_CITY){
                            existingCityObj.putOpt("daily_serv_cities", existingCityArray);
                            System.out.println("existingCityArray: " + existingCityArray);
                            System.out.println("existingCityObj: " + existingCityObj);
                        }else if(PreferenceClass.getIntegerPreferences(appContext, Constant.CITY_LIST_REQUEST)== Constant.BRANCH_IN_CITY){
                            existingCityObj.putOpt("branch_cities", existingCityArray);
                            System.out.println("existingCityArray: " + existingCityArray);
                            System.out.println("existingCityObj: " + existingCityObj);
                        }

                        addCity();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
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
            objFinal.putOpt("noncust_details",existingCityObj);

            System.out.println("objFinal is: "+objFinal);

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
                                    Toast.makeText(appContext,"City added successfully.",Toast.LENGTH_LONG).show();
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
        setSupportActionBar(mToolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
    }

    //PLace APIs <code></code>


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
                        mapData.put("address", mCustomAutoCompleteTextView.getText().toString());
                        mapData.put("lat",jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                        mapData.put("lng", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));

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
                Log.d("Background Task", e.toString());
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
                Log.d("Exception", e.toString());
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
                    final SimpleAdapter adapter = new SimpleAdapter(appContext, result, android.R.layout.simple_list_item_1, from, to);

                    // Setting the adapter
                    mCustomAutoCompleteTextView.setAdapter(adapter);
                    mCustomAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final HashMap<String, String> map = (HashMap<String, String>)adapter.getItem(position);
                            // Constant.hideKeyboard(mCustomAutoCompleteTextView, getActivity());
                            System.out.println("record " + map + " \nid " + map.get("placeid"));

                            String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+map.get("placeid")+"&key="+Constant.GOOGLE_PLACE_API_KEY;;

                            //String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + map.get("placeid") + "&key=" + "AIzaSyDYnG93I3yDgkGUmF8PHEA43smGXjhK2Gs";

                            AQuery aQuery = new AQuery(appContext);
                            aQuery.ajax(url,JSONObject.class, new AjaxCallback<JSONObject>(){
                                @Override
                                public void callback(String url, JSONObject object, AjaxStatus status) {
                                    super.callback(url, object, status);
                                    if (status.getCode()==AjaxStatus.NETWORK_ERROR){

                                    }else if(object!=null){
                                        try {

                                            getFragmentManager().popBackStackImmediate();
                                            JSONObject jsonObjectInfoPlace = object.getJSONObject("result");
                                            System.out.println("jsonObject for Place is: "+jsonObjectInfoPlace);
                                            //  Utils.setPrefrenceString(getActivity(), "placeid", map.get("placeid"));
                                            HashMap<String, String> mapData = new HashMap<String, String>();
                                            if (jsonObjectInfoPlace.getJSONArray("address_components").length()>0){
                                                try{
                                                    JSONArray jArrayAddress=jsonObjectInfoPlace.getJSONArray("address_components");

                                                    String strAddress="";
                                                    String strCity="";
                                                    String strDistict="";
                                                    String strState="";
                                                    String strPostalCode="";

                                                    String street_number="";
                                                    String route="";
                                                    String sublocality_level_1="";
                                                    String sublocality_level_2="";
                                                    String sublocality_level_3="";
                                                    String sublocality="";
                                                    String locality="";
                                                    String administrative_area_level_2="";
                                                    String administrative_area_level_1="";
                                                    String administrative_area_level_1_f="";
                                                    String country="";
                                                    String country_f="";
                                                    String postal_code="";

                                                    String formatted_address="";
                                                    String geometry="";

                                                    String icon="";
                                                    String id="";
                                                    String place_id="";
                                                    String types="";
                                                    String vicinity="";
                                                    String url1="";



                                                    for(int i=0;i<jArrayAddress.length();i++){
                                                        JSONObject jsonObject1 =jArrayAddress.getJSONObject(i);
                                                        String type_value=jsonObject1.getString("types");
                                                        type_value=type_value.replace("[","");
                                                        type_value=type_value.replace("]","");
                                                        String[] typevalue=type_value.split(",");
                                                        String strFinalType=typevalue[0].replace("\"","");

                                                        if(strFinalType.equals("street_number")){
                                                            street_number=jsonObject1.getString("short_name");
                                                        }else if(strFinalType.equals("route")){
                                                            route=jsonObject1.getString("long_name");
                                                        }else if(strFinalType.equals("sublocality_level_2")){
                                                            sublocality_level_2=jsonObject1.getString("long_name");
                                                            strAddress=jsonObject1.getString("long_name");
                                                            strCity=jsonObject1.getString("long_name");
                                                        }else if(strFinalType.equals("sublocality_level_1")){
                                                            sublocality_level_1=jsonObject1.getString("long_name");
                                                            strAddress=jsonObject1.getString("long_name");
                                                            if(strCity.trim().length()==0){
                                                                strCity=jsonObject1.getString("long_name");
                                                            }

                                                        }else if(strFinalType.equals("locality")){
                                                            locality=jsonObject1.getString("long_name");
                                                            strAddress=jsonObject1.getString("long_name");
                                                            if(strCity.trim().length()==0){
                                                                strCity=jsonObject1.getString("long_name");
                                                            }
                                                            strDistict=jsonObject1.getString("long_name");
                                                        }else if(strFinalType.equals("administrative_area_level_2")){
                                                            administrative_area_level_2=jsonObject1.getString("long_name");
                                                            strAddress=strAddress+" "+jsonObject1.getString("long_name");
                                                            if(strCity.trim().length()==0){
                                                                strCity=jsonObject1.getString("long_name");
                                                            }
                                                            strDistict=jsonObject1.getString("long_name");
                                                        }else if(strFinalType.equals("administrative_area_level_1")){
                                                            administrative_area_level_1_f=jsonObject1.getString("long_name");
                                                            administrative_area_level_1=jsonObject1.getString("short_name");
                                                            strAddress=strAddress+" "+jsonObject1.getString("long_name");
                                                            if(strCity.trim().length()==0){
                                                                strCity=jsonObject1.getString("long_name");
                                                            }
                                                            if(strDistict.trim().length()==0){
                                                                strDistict=jsonObject1.getString("long_name");
                                                            }
                                                            strState=jsonObject1.getString("short_name");
                                                        }else if(strFinalType.equals("country")){
                                                            country=jsonObject1.getString("short_name");
                                                            country_f=jsonObject1.getString("long_name");
                                                            strAddress=strAddress+" "+jsonObject1.getString("long_name");
                                                        }else if(strFinalType.equals("postal_code")){
                                                            postal_code=jsonObject1.getString("short_name");
                                                            strAddress=strAddress+" "+jsonObject1.getString("long_name");
                                                            strPostalCode=jsonObject1.getString("long_name");
                                                        }
                                                    }

                                                    JSONObject objAddressComponent=new JSONObject();
                                                    objAddressComponent.putOpt("street_number",street_number);
                                                    objAddressComponent.putOpt("route",street_number);
                                                    objAddressComponent.putOpt("sublocality_level_2",sublocality_level_2);
                                                    objAddressComponent.putOpt("sublocality_level_3",sublocality_level_3);
                                                    objAddressComponent.putOpt("sublocality_level_1",sublocality_level_1);
                                                    objAddressComponent.putOpt("sublocality",sublocality);
                                                    objAddressComponent.putOpt("locality",locality);
                                                    objAddressComponent.putOpt("administrative_area_level_2",administrative_area_level_2);
                                                    objAddressComponent.putOpt("administrative_area_level_1",administrative_area_level_1);
                                                    objAddressComponent.putOpt("administrative_area_level_1_f",administrative_area_level_1_f);
                                                    objAddressComponent.putOpt("country",country);
                                                    objAddressComponent.putOpt("country_f",country_f);
                                                    objAddressComponent.putOpt("postal_code",postal_code);

                                                    formatted_address=jsonObjectInfoPlace.getString("formatted_address");
                                                    JSONObject objGeometry=jsonObjectInfoPlace.getJSONObject("geometry");
                                                    place_id=jsonObjectInfoPlace.getString("place_id");
                                                    types=jsonObjectInfoPlace.getString("types");
                                                    JSONArray jsonArraytypes=jsonObjectInfoPlace.getJSONArray("types");
                                                    vicinity=jsonObjectInfoPlace.getString("vicinity");
                                                    url1=jsonObjectInfoPlace.getString("url");

                                                    //selectedCityObject.putOpt("_id","");
                                                    selectedCityObject.putOpt("c",strCity.toLowerCase());
                                                    selectedCityObject.putOpt("d", strDistict.toLowerCase());
                                                    selectedCityObject.putOpt("p", strPostalCode);
                                                    selectedCityObject.putOpt("s", strState);
                                                    selectedCityObject.putOpt("t","");
                                                    selectedCityObject.putOpt("sf","");
                                                    selectedCityObject.putOpt("address_components",objAddressComponent);
                                                    selectedCityObject.putOpt("formatted_address",formatted_address);
                                                    selectedCityObject.putOpt("geometry",objGeometry);
                                                    selectedCityObject.putOpt("place_id",place_id);
                                                    selectedCityObject.putOpt("types",jsonArraytypes);
                                                    selectedCityObject.putOpt("vicinity",vicinity);
                                                    selectedCityObject.putOpt("url",url1);

                                                    if(strPostalCode.trim().length()>0){
                                                        ((CustomEditText)findViewById(R.id.edtAddCityPin)).setText(strPostalCode);
                                                        ((CustomEditText)findViewById(R.id.edtAddCityPin)).setClickable(false);
                                                        ((CustomEditText)findViewById(R.id.edtAddCityPin)).setEnabled(false);
                                                    }else{
                                                        ((CustomEditText)findViewById(R.id.edtAddCityPin)).setText(strPostalCode);
                                                        ((CustomEditText)findViewById(R.id.edtAddCityPin)).setClickable(true);
                                                        ((CustomEditText)findViewById(R.id.edtAddCityPin)).setEnabled(true);
                                                    }
                                                    //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJqz6o5HMCYzkR22kri6fp2pk&key=AIzaSyAt0BvPkl0ASgbLMVEJihdrA8KyVaKf2Nk
                                                    // mapData.put("formatted_address",jsonObject1.getString("long_name"));
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }else{
                                                mapData.put("formatted_address",jsonObjectInfoPlace.getString("formatted_address"));
                                            }
                                            mapData.put("address", map.get("description"));
                                            mapData.put("lat",jsonObjectInfoPlace.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                            mapData.put("lng", jsonObjectInfoPlace.getJSONObject("geometry").getJSONObject("location").getString("lng"));

                                            if (mOnSearchCompleteListener!=null){
                                                mOnSearchCompleteListener.onSearchComplete(mapData);
                                            }

                                        }catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });


                        }
                    });
                }catch (NullPointerException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
    }

}
