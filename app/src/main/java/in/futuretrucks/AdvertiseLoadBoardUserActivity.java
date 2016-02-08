package in.futuretrucks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.adapter.LoadBoardRecyclerViewAdapter;
import in.futuretrucks.customviews.CustomAutoCompleteTextView;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.listener.OnLoadMoreListener;
import in.futuretrucks.model.LoadBoard;
import in.futuretrucks.model.LoadBoardApiResponse;
import in.futuretrucks.services.LoadBoardDestinationSearchService;
import in.futuretrucks.services.LoadBoardSearchService;
import in.futuretrucks.services.LoadBoardService;
import in.futuretrucks.services.LoadBoardSourceSearchService;
import in.futuretrucks.utility.PlaceJSONParser;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AdvertiseLoadBoardUserActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

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

    private static final String TAG = "RecyclerViewExample";
    private LinearLayoutManager layoutManager;
    int reqLocationPick=0;
    SimpleAdapter adapter=null;

    Toolbar mToolbar;
    ActionBar actionBar;

    private Context appContext;
    private LoadBoardApiResponse loadBoardApiResponse;

    LoadBoardRecyclerViewAdapter mLoadBoardAdapter;
    List<LoadBoard> loadBoardList;
    List<LoadBoard> loadBoardPeviousList;
    private LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    int skip_page=1;
    int count_item=5;
    LoadBoardService service;
    LoadBoardSearchService searchservice;
    LoadBoardSourceSearchService sourceSearchservice;
    LoadBoardDestinationSearchService destinationSearchservice;

    ProgressHUD progress;
    int requestData=0;
    int no_of_pages=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertise_load_board_user);
        appContext=this;
        initComponent();
    }

    private void initComponent(){
        mLayoutManager = new LinearLayoutManager(appContext);
        setActionBar();
        //Initialise Services
        service = ServiceGenerator.createService(LoadBoardService.class);
        searchservice = ServiceGenerator.createService(LoadBoardSearchService.class);
        sourceSearchservice = ServiceGenerator.createService(LoadBoardSourceSearchService.class);
        destinationSearchservice = ServiceGenerator.createService(LoadBoardDestinationSearchService.class);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerLoadMore);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.layoutRefresh);
        mRecyclerView.setLayoutManager(mLayoutManager);


        initLocationGoogleAPIs();

        loadBoardList = new ArrayList<LoadBoard>();
        loadBoardPeviousList = new ArrayList<LoadBoard>();
        loadBoardApiResponse = new LoadBoardApiResponse();

        ((CustomTextView)findViewById(R.id.txtAdvertiseLoad)).setOnClickListener(this);

        //data fetch from server
        //data fetch from server
        PreferenceClass.setStringPreference(appContext, "Source_District", "");
        PreferenceClass.setStringPreference(appContext, "Destination_District", "");
        loadDataFromServer(count_item,skip_page,0, 0, true, false, false,1,"","");


        mLoadBoardAdapter = new LoadBoardRecyclerViewAdapter(appContext,mRecyclerView,loadBoardPeviousList,this);
        mRecyclerView.setAdapter(mLoadBoardAdapter);
        mLoadBoardAdapter.notifyDataSetChanged();

        //Pull to Refresh
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY, Color.LTGRAY, Color.DKGRAY, Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                skip_page = 1;
                loadBoardList.clear();
                loadBoardPeviousList.clear();
                if(requestData==2){
                    if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0 && PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                        loadDataFromServer(count_item, skip_page, 0, 0, false, true, false, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                    }else if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0){
                        loadDataFromServer(count_item, skip_page, 0, 0, false, true, false, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "");
                    }else if(PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                        loadDataFromServer(count_item, skip_page, 0, 0, false, true, false, 2,"","" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                    }
                }else{
                    loadDataFromServer(count_item,skip_page,0, 0, false, true, false,1,"","");
                }

            }
        });

        mLoadBoardAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");

                loadBoardPeviousList.add(null);
                mLoadBoardAdapter.notifyItemInserted(loadBoardPeviousList.size() - 1);


                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");

                        //Remove loading item
                        loadBoardPeviousList.remove(loadBoardPeviousList.size() - 1);
                        mLoadBoardAdapter.notifyItemRemoved(loadBoardPeviousList.size());

                        //Load data
                        int index = loadBoardPeviousList.size();
                        int end = index;
                        skip_page = skip_page + 1;

                        if(requestData==2){
                            if(skip_page<=no_of_pages){
                                if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0 && PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, index, end, false, false, true, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                                }else if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, index, end, false, false, true, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "");
                                }else if(PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, index, end, false, false, true, 2,"","" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                                }
                            }else{
                                Toast.makeText(appContext,"No more data available for load.",Toast.LENGTH_LONG).show();
                            }

                        }else{
                            if(skip_page<=no_of_pages){
                                loadDataFromServer(count_item,skip_page,index, end, false, false, true,1,"","");
                            }else {
                                Toast.makeText(appContext, "No more data available for load.", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                }, 5000);

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadDataFromServer(int count_item,int skip_page,final int start_index, final int end_index,final boolean isInitial,final boolean isRefresh,final boolean isOnLoad,int request,String source,String destination){

        String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
        if(isInitial){
            progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                }
            });
        }
        Call<LoadBoardApiResponse> call=null;

        if(request==1){
            requestData=1;
            call = service.getLoadBoardList("created_at:-1",""+count_item, "load", "", "" + skip_page, jwt);
        }else{
            requestData=2;

            if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0 && PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                call = searchservice.searchLoadBoardList("available_date:-1", "" + count_item, "load", "", ""+source, ""+destination, "" + skip_page, jwt);
            }else if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0){
                call = sourceSearchservice.sourceSearchLoadBoardList("available_date:-1", "" + count_item, "load", "", ""+source, "" + skip_page, jwt);
            }else if(PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                call = destinationSearchservice.destinationSearchLoadBoardList("available_date:-1", "" + count_item, "load", "", "" + destination, "" + skip_page, jwt);
            }
        }

        call.enqueue(new Callback<LoadBoardApiResponse>() {

            @Override
            public void onResponse(Response<LoadBoardApiResponse> response, Retrofit retrofit) {
                loadBoardApiResponse = response.body();
                loadBoardList = loadBoardApiResponse.getData();

                no_of_pages = loadBoardApiResponse.getNoOfPages();

                Log.v("loadBoardList is: ", "" + loadBoardList);

                if (isInitial || isRefresh) {
                    for (int i = 0; i < loadBoardList.size(); i++) {
                        loadBoardPeviousList.add(loadBoardList.get(i));
                    }

                    if (isInitial) {
                        if (progress.isShowing() && progress != null) {
                            progress.dismiss();
                        }
                        mRecyclerView.setAdapter(mLoadBoardAdapter);
                        mLoadBoardAdapter.notifyDataSetChanged();
                    }

                    if (isRefresh) {
                        swipeRefreshLayout.setRefreshing(false);
                        mRecyclerView.setAdapter(mLoadBoardAdapter);
                        mLoadBoardAdapter.notifyDataSetChanged();
                    }
                }

                if (isOnLoad) {
                    int end_index1 = end_index + loadBoardList.size();
                    for (int i = start_index, j = 0; i < end_index1; i++, j++) {
                        loadBoardPeviousList.add(loadBoardList.get(j));
                    }
                    mLoadBoardAdapter.notifyDataSetChanged();
                    mLoadBoardAdapter.setLoaded();
                }
                mLoadBoardAdapter.setLoaded();

                if(loadBoardPeviousList.size()==0){
                    ((CustomTextView)findViewById(R.id.txtNoData)).setVisibility(View.VISIBLE);
                    ((CustomTextView)findViewById(R.id.txtNoData)).setText("Data not available.");
                }else{
                    ((CustomTextView)findViewById(R.id.txtNoData)).setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                if (isInitial) {
                    if (progress.isShowing() && progress != null) {
                        progress.dismiss();
                    }
                }
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
        if (v.getId() == R.id.txtAdvertiseLoad) {
            startActivity(new Intent(appContext, PostLoadActivity.class));
        }else if(v.getId()==R.id.txtCall){
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + Constant.Call_No));
            startActivity(callIntent);
        }else if(v.getId()==R.id.txtSMS){
            Uri uri = Uri.parse("smsto:" +Constant.Call_No);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", "Future Trcuks SMS");
            startActivity(intent);
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
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setText(R.string.My_Load_Board);
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

            InputMethodManager inputMethodManager = (InputMethodManager)  appContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+map.get("placeid")+"&key="+ Constant.GOOGLE_PLACE_API_KEY;;

            //String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + map.get("placeid") + "&key=" + "AIzaSyDYnG93I3yDgkGUmF8PHEA43smGXjhK2Gs";

            AQuery aQuery = new AQuery(appContext);
            aQuery.ajax(url,JSONObject.class, new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    super.callback(url, object, status);

                    String strAddress="";
                    String strCity="";
                    String strDistict="";

                    if (status.getCode()== AjaxStatus.NETWORK_ERROR){

                    }else if(object!=null){
                        try {

                            getFragmentManager().popBackStackImmediate();
                            JSONObject jsonObject = object.getJSONObject("result");
                            System.out.println("jsonObject for Place is: "+jsonObject);
                            //  Utils.setPrefrenceString(getActivity(), "placeid", map.get("placeid"));
                            HashMap<String, String> mapData = new HashMap<String, String>();
                            if (jsonObject.getJSONArray("address_components").length()>0){
                                try{
                                    JSONArray jArrayAddress=jsonObject.getJSONArray("address_components");

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

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else{
                                mapData.put("formatted_address",jsonObject.getString("formatted_address"));
                            }
                            mapData.put("address", map.get("description"));
                            mapData.put("lat",jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                            mapData.put("lng", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng"));

                            if (mOnSearchCompleteListener != null) {
                                mOnSearchCompleteListener.onSearchComplete(mapData);
                            }

                            if(reqLocationPick==1){

                                System.out.println("Source strDistict is: " + strDistict.toLowerCase());
                                PreferenceClass.setStringPreference(appContext, "Source_District", strDistict.toLowerCase());

                                skip_page = 1;
                                loadBoardList.clear();
                                loadBoardPeviousList.clear();
                                mLoadBoardAdapter.notifyDataSetChanged();
                                if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0 && PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, 0, 0, true, false, false, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                                }else if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, 0, 0, true, false, false, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "");
                                }else if(PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, 0, 0, true, false, false, 2,"","" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                                }

                                mapData.put("address", mCustomAutoCompleteTextViewSource.getText().toString());
                                mapData.put("lat", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                mapData.put("lng", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng"));


                                PreferenceClass.setStringPreference(appContext, "source_lat", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                PreferenceClass.setStringPreference(appContext, "source_lng", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng"));
                            }else{

                                System.out.println("Destination strDistict is: " + strDistict.toLowerCase());
                                PreferenceClass.setStringPreference(appContext, "Destination_District", strDistict.toLowerCase());

                                skip_page = 1;
                                loadBoardList.clear();
                                loadBoardPeviousList.clear();
                                mLoadBoardAdapter.notifyDataSetChanged();

                                if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0 && PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, 0, 0, true, false, false, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                                }else if(PreferenceClass.getStringPreferences(appContext, "Source_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, 0, 0, true, false, false, 2, "" +PreferenceClass.getStringPreferences(appContext, "Source_District"), "");
                                }else if(PreferenceClass.getStringPreferences(appContext, "Destination_District").trim().length()>0){
                                    loadDataFromServer(count_item, skip_page, 0, 0, true, false, false, 2,"","" +PreferenceClass.getStringPreferences(appContext, "Destination_District"));
                                }

                                mapData.put("address", mCustomAutoCompleteTextViewDestination.getText().toString());
                                mapData.put("lat", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                mapData.put("lng", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng"));

                                PreferenceClass.setStringPreference(appContext, "dest_lat", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                PreferenceClass.setStringPreference(appContext, "dest_lng", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng"));
                            }



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
    };
}
