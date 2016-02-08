/*
package in.futuretrucks;


import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import java.util.HashMap;
import java.util.List;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomAutoCompleteTextView;
import in.futuretrucks.utility.PlaceJSONParser;
import in.futuretrucks.utility.PreferenceClass;

*/
/**
 * A simple {@link Fragment} subclass.
 *//*

public class SearchPlaceFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
  //          mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            Toast.makeText(getActivity(),"Lat "+mLastLocation.getLatitude() +"and lng "+mLastLocation.getLongitude(),Toast.LENGTH_LONG).show();

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
            Toast.makeText(getActivity(),"Lat "+mLastLocation.getLatitude() +"and lng "+mLastLocation.getLongitude(),Toast.LENGTH_LONG).show();
        }
    }

    public interface OnSearchCompleteListener{
        public void onSearchComplete(HashMap<String, String> placedata);
    }

    private OnSearchCompleteListener mOnSearchCompleteListener;
    public SearchPlaceFragment() {
        // Required empty public constructor
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        if(mLocationRequest!=null)
         LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
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
    private CustomAutoCompleteTextView mCustomAutoCompleteTextView;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_place, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)

                .build();

        */
/*((MainActivity) getActivity()).changeNavigationBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });*//*

        mCustomAutoCompleteTextView = (CustomAutoCompleteTextView)getView().findViewById(R.id.atv_places);
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

    private void getLatLng(String address)
    {
       final ProgressDialog mpProgressDialog = ProgressDialog.show(getActivity(),"","Finding location...");
        try {
            address = URLEncoder.encode(address,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url =  "http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=true_or_false";

        AQuery aQuery = new AQuery(getActivity());
        aQuery.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                mpProgressDialog.cancel();
                if (status.getCode() == AjaxStatus.NETWORK_ERROR) {
                    Toast.makeText(getActivity(),"Check network please",Toast.LENGTH_LONG).show();
                    //Constant.showAlertDialogCheck(Constant.DIALOG_SERVICE_UNABLE, Constant.MSG_SERVICE_UNABLE, getActivity(), false);
                } else if (object != null) {
                    try {
                        getFragmentManager().popBackStackImmediate();
                        JSONArray jsonObject = object.getJSONArray("results");

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
                        // Utils.setPrefrenceString(getActivity(), "placeid", map.get("placeid"));
//                        HashMap<String, String> mapData = new HashMap<String, String>();
//                        if (jsonObject.getJSONArray("address_components").length()>0){
//                            JSONObject jsonObject1 = jsonObject.getJSONArray("address_components").getJSONObject(0);
//                            //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJqz6o5HMCYzkR22kri6fp2pk&key=AIzaSyAt0BvPkl0ASgbLMVEJihdrA8KyVaKf2Nk
//                            mapData.put("formatted_address",jsonObject1.getString("long_name"));
//                        }else{
//                            mapData.put("formatted_address",jsonObject.getString("formatted_address"));
//                        }
//
//                        mapData.put("lat",jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat"));
//                        mapData.put("lng", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng"));
                        PreferenceClass.setStringPreference(getActivity(), "lat", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                        PreferenceClass.setStringPreference(getActivity(), "lng", jsonObject.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));

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
    */
/** A method to download json data from url *//*

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
    private class PlacesTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key="+ "AIzaSyCPnQLF0_aTdOyKaEWbhza46cKEWPrg9HY";//Constant.GOOGLE_API_KEY;

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
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
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
    */
/** A class to parse the Google Places in JSON format *//*

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
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            if ( result!=null  && result.size()>0 && result.get(0).containsKey("error"))
            {
                Toast.makeText(getActivity(),result.get(0).get("error"),Toast.LENGTH_LONG).show();

            }else if(result!=null  &&  result.size()>0)
            {
                String[] from = new String[] { "description"};
                int[] to = new int[] { android.R.id.text1 };

                try {
                    // Creating a SimpleAdapter for the AutoCompleteTextView
                    final SimpleAdapter adapter = new SimpleAdapter(getActivity(), result, android.R.layout.simple_list_item_1, from, to);

                    // Setting the adapter
                    mCustomAutoCompleteTextView.setAdapter(adapter);
                    mCustomAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final HashMap<String, String> map = (HashMap<String, String>)adapter.getItem(position);
                               // Constant.hideKeyboard(mCustomAutoCompleteTextView, getActivity());
                            System.out.println("record " + map + " \nid " + map.get("placeid"));
                            String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+map.get("placeid")+"&key="+"AIzaSyCPnQLF0_aTdOyKaEWbhza46cKEWPrg9HY";
                            AQuery aQuery = new AQuery(getActivity());
                            aQuery.ajax(url,JSONObject.class, new AjaxCallback<JSONObject>(){
                                @Override
                                public void callback(String url, JSONObject object, AjaxStatus status) {
                                    super.callback(url, object, status);
                                    if (status.getCode()==AjaxStatus.NETWORK_ERROR){

                                    }else if(object!=null){
                                        try {
                                            getFragmentManager().popBackStackImmediate();
                                            JSONObject jsonObject = object.getJSONObject("result");
                                          //  Utils.setPrefrenceString(getActivity(), "placeid", map.get("placeid"));
                                            HashMap<String, String> mapData = new HashMap<String, String>();
                                            if (jsonObject.getJSONArray("address_components").length()>0){
                                                JSONObject jsonObject1 = jsonObject.getJSONArray("address_components").getJSONObject(0);
                                                //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJqz6o5HMCYzkR22kri6fp2pk&key=AIzaSyAt0BvPkl0ASgbLMVEJihdrA8KyVaKf2Nk
                                                mapData.put("formatted_address",jsonObject1.getString("long_name"));
                                            }else{
                                                mapData.put("formatted_address",jsonObject.getString("formatted_address"));
                                            }
                                            mapData.put("address",map.get("description"));
                                            mapData.put("lat",jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                            mapData.put("lng", jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng"));

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
*/
