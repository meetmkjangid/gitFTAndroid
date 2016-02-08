package in.futuretrucks;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.UploadFileApiResponse;
import in.futuretrucks.model.UploadVehicleFileApiResponse;
import in.futuretrucks.services.ChassisTraceUploadService;
import in.futuretrucks.services.FitnessCertUploadService;
import in.futuretrucks.services.InsuranceDocUploadService;
import in.futuretrucks.services.PermitDocUploadService;
import in.futuretrucks.services.RCBookDocUploadService;
import in.futuretrucks.services.RoadTaxDocUploadService;
import in.futuretrucks.services.ServiceGenerator;
import in.futuretrucks.services.VehiclePhotoUploadService;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by mahadev on 12/15/15.
 */
public class UploadTruckDocumentsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "AndroidUploadService";
    private Context appContext;
    ActionBar actionBar;
    Toolbar mToolbar;
    private String paramName;
    private ProgressHUD progress;
    private RelativeLayout layoutActionSheet;
    private Animation showPicker, hidePicker;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static File finalFile=null;
    private String profile_pic_path="";
    private ConnectionDetector cd;
    private int pic_request=0;
    private UploadFileApiResponse up;
    private ProgressBar progressBar;
    private TextView txtPercentage;
    long totalSize = 0;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(1)).showImageForEmptyUri(R.drawable.final_logo).showImageOnFail(R.drawable.final_logo).showImageOnLoading(R.drawable.final_logo).build();
    AsyncTask<String,Integer,String> taskUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_truck_documents);
        appContext=this;
        setActionBar();
        initComponent();
    }


    private void initComponent(){
        imageLoader.init(ImageLoaderConfiguration.createDefault(appContext));
        cd=new ConnectionDetector(appContext);

        animationView();

        ((CustomTextView)findViewById(R.id.txtTakePhoto)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtChooseExisting)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtCancel)).setOnClickListener(this);

        /*String profilepicurl="http://tracklabs.in:3000/api/file/profile_img_567a50175dd102030f4cf08a.jpg".replace(" ","%20");
        String idProofUrl="http://tracklabs.in:3000/api/file/id_proof_567a50175dd102030f4cf08a.jpg".replace(" ","%20");
        String pancardUrl="http://tracklabs.in:3000/api/file/pan_card_proof_567a50175dd102030f4cf08a.jpg".replace(" ","%20");
        String servicetaxUrl="http://tracklabs.in:3000/api/file/service_tax_proof_567a50175dd102030f4cf08a.jpg".replace(" ","%20");
        String addrproofUrl="http://tracklabs.in:3000/api/file/addr_proof_567a50175dd102030f4cf08a.jpg".replace(" ","%20");
        String tinumberUrl="http://tracklabs.in:3000/api/file/tin_number_proof_567a50175dd102030f4cf08a.jpg".replace(" ","%20");
        String companylogoUrl="http://tracklabs.in:3000/api/file/company_log_567a50175dd102030f4cf08a.jpg".replace(" ","%20");

        imageLoader.displayImage(profilepicurl, ((ImageView) findViewById(R.id.imgvwProfilePicDoc)), options);
        imageLoader.displayImage(idProofUrl, ((ImageView) findViewById(R.id.txtPhotoID)), options);
        imageLoader.displayImage(pancardUrl, ((ImageView) findViewById(R.id.imgvwPANCardDoc)), options);
        imageLoader.displayImage(tinumberUrl, ((ImageView) findViewById(R.id.imgvwTinNoDoc)), options);
        imageLoader.displayImage(servicetaxUrl, ((ImageView) findViewById(R.id.imgvwServcieNoDoc)), options);
        imageLoader.displayImage(addrproofUrl, ((ImageView) findViewById(R.id.imgvwAddressProofDoc)), options);
        imageLoader.displayImage(companylogoUrl, ((ImageView) findViewById(R.id.imgvwCompanyLogoDoc)), options);*/


        setClickListener();

    }

    private void setClickListener(){
        //Set Upload Button Click Listener

        ((CustomTextView)findViewById(R.id.txtUploadvehicle_photo)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtUploadvehicle_permit)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtUploadvehicle_fitness_cert)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtUploadvehicle_chassis)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtUploadvehicle_insurance)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtUploadvehicle_road_tax)).setOnClickListener(this);
        ((CustomTextView)findViewById(R.id.txtUploadvehicle_rc_book)).setOnClickListener(this);



        ((ImageView)findViewById(R.id.imgvwvehicle_photo)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwReloadvehicle_photo)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwDonevehicle_photo)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwEditvehicle_photo)).setOnClickListener(this);

        ((ImageView)findViewById(R.id.imgvwvehicle_permit)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwReloadvehicle_permit)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwDonevehicle_permit)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwEditvehicle_permit)).setOnClickListener(this);

        ((ImageView)findViewById(R.id.imgvwvehicle_fitness_cert)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwReloadvehicle_fitness_cert)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwDonevehicle_fitness_cert)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwEditvehicle_fitness_cert)).setOnClickListener(this);


        ((ImageView)findViewById(R.id.imgvwvehicle_chassis)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwReloadvehicle_chassis)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwDonevehicle_chassis)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwEditvehicle_chassis)).setOnClickListener(this);


        ((ImageView)findViewById(R.id.imgvwvehicle_insurance)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwReloadvehicle_insurance)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwDonevehicle_insurance)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwEditvehicle_insurance)).setOnClickListener(this);


        ((ImageView)findViewById(R.id.imgvwvehicle_road_tax)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwReloadvehicle_road_tax)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwDonevehicle_road_tax)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwEditvehicle_road_tax)).setOnClickListener(this);


        ((ImageView)findViewById(R.id.imgvwvehicle_rc_book)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwReloadvehicle_rc_book)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwDonevehicle_rc_book)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imgvwEditvehicle_rc_book)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtUploadvehicle_photo:
                pic_request=Constant.VEHICLE_PHOTO;
                openActionSheet();
                break;
            case R.id.txtUploadvehicle_permit:
                pic_request=Constant.VEHICLE_PERMIT;
                openActionSheet();
                break;

            case R.id.txtUploadvehicle_fitness_cert:
                pic_request=Constant.VEHICLE_FITNESS_CERT;
                openActionSheet();
                break;

            case R.id.txtUploadvehicle_insurance:
                pic_request=Constant.VEHICLE_INSURANCE_COPY;
                openActionSheet();
                break;

            case R.id.txtUploadvehicle_chassis:
                pic_request=Constant.VEHICLE_CHASSIS_TRACE;
                openActionSheet();
                break;

            case R.id.txtUploadvehicle_road_tax:
                pic_request=Constant.VEHICLE_ROAD_TAX;
                openActionSheet();
                break;
            case R.id.txtUploadvehicle_rc_book:
                pic_request=Constant.VEHICLE_RC_BOOK;
                openActionSheet();
                break;

            case R.id.txtTakePhoto:
                layoutActionSheet.startAnimation(hidePicker);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
                break;

            case R.id.txtChooseExisting:

                layoutActionSheet.startAnimation(hidePicker);
                Intent intent_gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent_gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(intent_gallery, "Select File"), SELECT_FILE);

                break;
            case R.id.txtCancel:
                layoutActionSheet.startAnimation(hidePicker);
                break;

            default:
                break;
        }
    }

    private void openActionSheet(){
        setActionSheetBackground();
        if (layoutActionSheet.getVisibility() == View.GONE) {
            layoutActionSheet.startAnimation(showPicker);
            layoutActionSheet.setVisibility(View.VISIBLE);
        } else {
            layoutActionSheet.startAnimation(hidePicker);
            layoutActionSheet.setVisibility(View.GONE);
        }
    }

    private void setActionSheetBackground(){
        ((RelativeLayout)findViewById(R.id.layoutActionAccountOption)).setBackgroundColor(getResources().getColor(R.color.color_white));
        ((CustomTextView)findViewById(R.id.txtTakePhoto)).setBackgroundColor(getResources().getColor(R.color.color_header));
        ((CustomTextView)findViewById(R.id.txtChooseExisting)).setBackgroundColor(getResources().getColor(R.color.color_header));
        ((CustomTextView)findViewById(R.id.txtCancel)).setBackgroundColor(getResources().getColor(R.color.color_header));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                if(pic_request==Constant.VEHICLE_PHOTO){
                    VehiclePhotoUploadService service =
                            ServiceGenerator.createService(VehiclePhotoUploadService.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            Toast.makeText(appContext, R.string.not_vehicle_photo, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });

                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_photo)), options);

                }else if(pic_request==Constant.VEHICLE_PERMIT){

                    PermitDocUploadService service =
                            ServiceGenerator.createService(PermitDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_permit, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    Log.e("ComeON", "after upload");
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_permit)), options);

                    //   int code = uploadFile(profile_pic_path, pic_request);
                    Log.e("ComeON", "after image loader");
                    Log.e("ComeON",profile_pic_path);
                }else if(pic_request==Constant.VEHICLE_FITNESS_CERT){

                    FitnessCertUploadService service =
                            ServiceGenerator.createService(FitnessCertUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_fitness_cert, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_fitness_cert)), options);

                }else if(pic_request==Constant.VEHICLE_CHASSIS_TRACE){

                    ChassisTraceUploadService service =
                            ServiceGenerator.createService(ChassisTraceUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_chassis_trace, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });

                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_chassis)), options);

                }else if(pic_request==Constant.VEHICLE_INSURANCE_COPY){

                    InsuranceDocUploadService service =
                            ServiceGenerator.createService(InsuranceDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_insurance, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    Log.e("ComeON", "after upload");
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_insurance)), options);

                    //   int code = uploadFile(profile_pic_path, pic_request);
                    Log.e("ComeON", "after imageloader");
                    Log.e("ComeON",profile_pic_path);
                }else if(pic_request==Constant.VEHICLE_ROAD_TAX){

                    RoadTaxDocUploadService service =
                            ServiceGenerator.createService(RoadTaxDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_road_tax, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    Log.e("ComeON", "after upload");
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_road_tax)), options);

                    //       int code = uploadFile(profile_pic_path, pic_request);
                    Log.e("ComeON", "after imageloader");
                    Log.e("ComeON",profile_pic_path);
                }else if(pic_request==Constant.VEHICLE_RC_BOOK){

                    RCBookDocUploadService service =
                            ServiceGenerator.createService(RCBookDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_rcbook, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_rc_book)), options);

                }

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

                if(pic_request==Constant.VEHICLE_PHOTO){
                    VehiclePhotoUploadService service =
                            ServiceGenerator.createService(VehiclePhotoUploadService.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            Toast.makeText(appContext, R.string.not_vehicle_photo, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });


                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_photo)), options);

                }else if(pic_request==Constant.VEHICLE_PERMIT){

                    PermitDocUploadService service =
                            ServiceGenerator.createService(PermitDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_permit, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    Log.e("ComeON", "after upload");
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_permit)), options);

                    //   int code = uploadFile(profile_pic_path, pic_request);
                    Log.e("ComeON", "after image loader");
                    Log.e("ComeON",profile_pic_path);
                }else if(pic_request==Constant.VEHICLE_FITNESS_CERT){

                    FitnessCertUploadService service =
                            ServiceGenerator.createService(FitnessCertUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_fitness_cert, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_fitness_cert)), options);

                }else if(pic_request==Constant.VEHICLE_CHASSIS_TRACE){

                    ChassisTraceUploadService service =
                            ServiceGenerator.createService(ChassisTraceUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_chassis_trace, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());
                        }
                    });

                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_chassis)), options);

                }else if(pic_request==Constant.VEHICLE_INSURANCE_COPY){

                    InsuranceDocUploadService service =
                            ServiceGenerator.createService(InsuranceDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_insurance, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    Log.e("ComeON", "after upload");
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_insurance)), options);

                    //   int code = uploadFile(profile_pic_path, pic_request);
                    Log.e("ComeON", "after imageloader");
                    Log.e("ComeON",profile_pic_path);
                }else if(pic_request==Constant.VEHICLE_ROAD_TAX){

                    RoadTaxDocUploadService service =
                            ServiceGenerator.createService(RoadTaxDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_vehicle_road_tax, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    Log.e("ComeON", "after upload");
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_road_tax)), options);

                    //       int code = uploadFile(profile_pic_path, pic_request);
                    Log.e("ComeON", "after imageloader");
                    Log.e("ComeON",profile_pic_path);
                }else if(pic_request==Constant.VEHICLE_RC_BOOK){

                    RCBookDocUploadService service =
                            ServiceGenerator.createService(RCBookDocUploadService.class);

                    File file = new File(profile_pic_path);

                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN);
                    String truck_id = PreferenceClass.getStringPreferences(appContext,Constant.NEW_TRUCK_REG_ID);

                    Call<UploadVehicleFileApiResponse> call = service.upload(truck_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadVehicleFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadVehicleFileApiResponse> response, Retrofit retrofit) {
                            //     Log.e("ComeON", "4********" + response.body().toString());
                            //  up = response.body();
                            Log.e("ComeON", Integer.toString(response.code()));
                            Toast.makeText(appContext, R.string.not_rcbook, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwvehicle_rc_book)), options);

                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (layoutActionSheet.getVisibility() == View.VISIBLE) {
            layoutActionSheet.startAnimation(hidePicker);
            layoutActionSheet.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        ((CustomTextView)mToolbar.findViewById(R.id.txtHeading)).setText("Vehicle Documents");
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}


