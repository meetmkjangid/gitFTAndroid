package in.futuretrucks;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
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
import in.futuretrucks.model.LoadBoardApiResponse;
import in.futuretrucks.model.User;
import in.futuretrucks.services.LoadBoardService;
import in.futuretrucks.services.SignUpApiService;
import in.futuretrucks.utility.CommonGCMUtility;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PlaceJSONParser;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import in.futuretrucks.utility.WakeLocker;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;
import retrofit.http.Body;


/**
     * Created by mahadev on 12/1/15.
     */
    public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Context appContext;
    private ProgressHUD progress;
    private RelativeLayout layoutActionSheet;
    private Animation showPicker, hidePicker;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static File finalFile=null;
    private String profile_pic_path="";
    private ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(1000)).showImageForEmptyUri(R.drawable.final_logo).showImageOnFail(R.drawable.final_logo).showImageOnLoading(R.drawable.final_logo).build();
    ConnectionDetector cd;
    JSONObject selectedCityObject=new JSONObject();
    private String registrationId="";
    TelephonyManager telephone_manager;
    HashMap<String, JSONObject> nameValuePairs=new HashMap<String,JSONObject>();
    @Override
    public void onConnected(Bundle bundle) {
            //   mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
            //           mGoogleApiClient);

            if (mLastLocation != null) {
    //            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //          mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            //Toast.makeText(appContext, "Lat " + mLastLocation.getLatitude() + "and lng " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();

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
    private CustomAutoCompleteTextView mCustomAutoCompleteTextView;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            appContext=this;
            initComponent();
        }

        private void initComponent(){
            cd=new ConnectionDetector(appContext);
            telephone_manager= (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(appContext));
            ((CustomButton)findViewById(R.id.btnJoinNow)).setOnClickListener(this);

            if(cd.isConnectingToInternet()){
                serverData();
            }

            setDataForCity();
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
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (mCustomAutoCompleteTextView.getText().toString().trim().length() > 0)
                            getLatLng(mCustomAutoCompleteTextView.getText().toString());
                    }
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {

            if(v.getId()==R.id.btnJoinNow){
                String strOwnerName=((CustomEditText)findViewById(R.id.edtOwnerName)).getText().toString().trim();
                String strCompanyName=((CustomEditText)findViewById(R.id.edtCompanyName)).getText().toString().trim();
                String strMobile=((CustomEditText)findViewById(R.id.edtMobile)).getText().toString().trim();
                String strEmail=((CustomEditText)findViewById(R.id.edtEmail)).getText().toString().trim();
                String strPassword=((CustomEditText)findViewById(R.id.edtPassword)).getText().toString().trim();
                String strConPassword=((CustomEditText)findViewById(R.id.edtConPassword)).getText().toString().trim();
                String strPanNo=((CustomEditText)findViewById(R.id.edtPanNo)).getText().toString().trim();
                CheckBox chkBox=((CheckBox) findViewById(R.id.chkAcceptTermsCondition));

                if(strOwnerName.length()==0){
                    Toast.makeText(appContext, R.string.owner_name_blank, Toast.LENGTH_LONG).show();
                }else if(strMobile.length()==0){
                    Toast.makeText(appContext, R.string.mobile_blank, Toast.LENGTH_LONG).show();
                }if(strEmail.length()==0){
                    Toast.makeText(appContext,R.string.email_blank,Toast.LENGTH_LONG).show();
                }else if(!strEmail.matches(Constant.EMAIL_FORMATE)){
                    Toast.makeText(appContext,R.string.email_invalid,Toast.LENGTH_LONG).show();
                }else if(strPassword.length()==0){
                    Toast.makeText(appContext,R.string.password_blank,Toast.LENGTH_LONG).show();
                }else if(!strPassword.equals(strConPassword)){
                    Toast.makeText(appContext,R.string.password_match,Toast.LENGTH_LONG).show();
                }else if(selectedCityObject.length()==0){
                    Toast.makeText(appContext,R.string.Select_City,Toast.LENGTH_LONG).show();
                }else if(strPanNo.length()==0){
                    Toast.makeText(appContext,R.string.pan_no,Toast.LENGTH_LONG).show();
                }else{
                    if(cd.isConnectingToInternet()){
                        if(chkBox.isChecked()){
                            if(PreferenceClass.getStringPreferences(appContext,Constant.GCM_REGISTER_KEY).trim().length()>10){
                                registerUser(strOwnerName, strCompanyName,strMobile, strEmail,strPassword,strPanNo);
                            }else{
                                Toast.makeText(appContext,"GCM is not register.Please wait",Toast.LENGTH_LONG).show();
                                serverData();
                            }
                        }else{
                            Toast.makeText(appContext,R.string.terms_cond,Toast.LENGTH_LONG).show();
                        }
                                /*JSONObject jObjCreate=new JSONObject();
                                jObjCreate.putOpt("owner_name", strOwnerName);
                                jObjCreate.putOpt("company_name", strCompanyName);
                                jObjCreate.putOpt("mobile", strMobile);
                                jObjCreate.putOpt("email", strEmail);
                                jObjCreate.putOpt("password", strPassword);
                                jObjCreate.putOpt("pan_no", strPanNo);
                                jObjCreate.putOpt("city",selectedCityObject);
                                jObjCreate.putOpt("type", PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE));
                                System.out.println("jObjCreate in Reg is: " + jObjCreate);
                                SignUpApiService service = ServiceGenerator.createService(SignUpApiService.class);
                                Call<JSONObject> call=null;
                                progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                    }
                                });
                                String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);


                                nameValuePairs.put("nameValuePairs", jObjCreate);

                                call=service.registerUser(nameValuePairs);
                                call.enqueue(new Callback<JSONObject>() {
                                    @Override
                                    public void onResponse(retrofit.Response<JSONObject> response, Retrofit retrofit) {
                                        if (progress.isShowing() && progress != null) {
                                            progress.dismiss();
                                        }
                                        System.out.println("response.code()  "+response.code());
                                        System.out.println("response.body()  "+response.body());
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        System.out.println("Failed data");
                                        if (progress.isShowing() && progress != null) {
                                            progress.dismiss();
                                        }
                                    }
                                });
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/
                            //registerUser(strOwnerName, strCompanyName,strMobile, strEmail,strPassword,strPanNo);
                    }else{
                        Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
                    }
                }
            }
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                finalFile=null;
                if (requestCode == REQUEST_CAMERA) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(appContext, photo);
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    finalFile = new File(getRealPathFromURI(tempUri));
                    profile_pic_path=finalFile.getAbsolutePath();
                    String uri="file://"+profile_pic_path;
                    imageLoader.displayImage(uri, ((ImageView)findViewById(R.id.imgvwProfilePic)), options);

                } else if (requestCode == SELECT_FILE) {
                    Uri selectedImageUri = data.getData();
                    String [] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String FilePath = cursor.getString(columnIndex);
                    cursor.close();
                    finalFile = new File(FilePath);
                    profile_pic_path=finalFile.getAbsolutePath();
                    String uri="file://"+profile_pic_path;
                    imageLoader.displayImage(uri, ((ImageView)findViewById(R.id.imgvwProfilePic)), options);

                }
            }
        }

        public Uri getImageUri(Context inContext, Bitmap inImage) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        }

        public String getRealPathFromURI(Uri uri) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }

        private void animationView() {

            layoutActionSheet = (RelativeLayout)findViewById(R.id.layoutActionAccountOption);
            AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
            alpha.setDuration(0); // Make animation instant
            alpha.setFillAfter(true); // Tell it to persist after the animation ends
            layoutActionSheet.startAnimation(alpha);
            showPicker = AnimationUtils.loadAnimation(appContext, R.anim.slide_up);
            hidePicker = AnimationUtils.loadAnimation(appContext, R.anim.slide_down);

            showPicker.setAnimationListener(animListener);
            hidePicker.setAnimationListener(animListener);

        }

        public void registerUser(final String owner_name,final String company_name,final String mobile, final String email,final String password,final String pan_no) {

            progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                }
            });

            try{
                final AQuery aq = new AQuery(appContext);
                String url = Constant.BASE_URL+Constant.REGISTRATION;
                JSONObject jObjCreate=new JSONObject();
                jObjCreate.putOpt("owner_name", owner_name);
                jObjCreate.putOpt("company_name", company_name);
                jObjCreate.putOpt("mobile", mobile);
                jObjCreate.putOpt("email", email);
                jObjCreate.putOpt("password", password);
                jObjCreate.putOpt("pan_no", pan_no);
                jObjCreate.putOpt("city",selectedCityObject);
                jObjCreate.putOpt("type", PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE));

                JSONArray jArray=new JSONArray();
                JSONObject jObj=new JSONObject();
                jObj.putOpt("dtype", Constant.ANDROID);
                jObj.putOpt("token", PreferenceClass.getStringPreferences(appContext, Constant.GCM_REGISTER_KEY));
                jObj.putOpt("IMEI", telephone_manager.getDeviceId());

                jArray.put(jObj);
                System.out.println("jArray for info: " + jArray);
                jObjCreate.putOpt("devices", jArray);
                System.out.println("Object making is: "+jObjCreate);

                aq.post(url, jObjCreate, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject jObj, AjaxStatus status) {
                        try {
                            if (progress.isShowing() && progress != null) {
                                progress.dismiss();
                            }

                            System.out.println("Status Code: "+status+"jObject is: "+jObj);

                            if (status.getCode() == 200) {
                                if (jObj != null) {
                                    String status_response = jObj.getString("status");
                                    if (status_response.equals("OK")) {
                                        PreferenceClass.setStringPreference(appContext, Constant.USER_MOBILE, mobile);
                                        PreferenceClass.setStringPreference(appContext, Constant.USER_PASSWORD, password);

                                        Intent intent = new Intent(appContext, VerificationActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
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
                }.method(AQuery.METHOD_POST).header("Content-Type", "application/json"));

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    private Animation.AnimationListener animListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
            if (animation == showPicker) {
                layoutActionSheet.setVisibility(View.VISIBLE);
            }
            if (animation == hidePicker) {
                layoutActionSheet.setVisibility(View.GONE);
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            if (animation == showPicker) {
                layoutActionSheet.setVisibility(View.VISIBLE);
            }
            if (animation == hidePicker) {
                layoutActionSheet.setVisibility(View.GONE);
            }
        }
    };

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

            String location = "location="+PreferenceClass.getStringPreferences(appContext,Constant.CURRENT_LATITUDE)+","+PreferenceClass.getStringPreferences(appContext,Constant.CURRENT_LONGITUDE);
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

                                                    selectedCityObject.putOpt("_id","");
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


                                                    System.out.println("selectedCityObject City Object is:  "+selectedCityObject);

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


    private void serverData() {
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonGCMUtility.DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        registrationId = GCMRegistrar.getRegistrationId(this);

        System.out.println("Reg Id is  " + registrationId);
        PreferenceClass.setStringPreference(appContext, Constant.GCM_REGISTER_KEY, registrationId);
        //Toast.makeText(appContext, registrationId, Toast.LENGTH_SHORT).show();

        // Check if regid already presents
        if (registrationId.equals("")) {
            GCMRegistrar.register(this, CommonGCMUtility.SENDER_ID);
        }
    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(CommonGCMUtility.EXTRA_MESSAGE);
            System.out.println("" + newMessage);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());
            WakeLocker.release();
        }
    };

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }
 }
