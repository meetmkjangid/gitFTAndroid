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

import com.alexbbb.uploadservice.MultipartUploadRequest;
import com.alexbbb.uploadservice.UploadNotificationConfig;
import com.alexbbb.uploadservice.UploadServiceBroadcastReceiver;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.UploadFileApiResponse;
import in.futuretrucks.services.DynamicFilenameUploadService;
import in.futuretrucks.services.FileUploadService;
import in.futuretrucks.services.FileUploadService2;
import in.futuretrucks.services.FileUploadService3;
import in.futuretrucks.services.FileUploadService4;
import in.futuretrucks.services.FileUploadService5;
import in.futuretrucks.services.FileUploadService6;
import in.futuretrucks.services.ServiceGeneratorFileUpload;
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
public class UploadDocumentsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AndroidUploadService";
    private Context appContext;
    ActionBar actionBar;
    Toolbar mToolbar;
    private String paramName;
    private ProgressHUD progress;
    private RelativeLayout layoutActionSheet;
    private Animation showPicker, hidePicker;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static File finalFile = null;
    private String profile_pic_path = "";
    private ConnectionDetector cd;
    private int pic_request = 0;
    private UploadFileApiResponse up;
    private ProgressBar progressBar;
    private TextView txtPercentage;
    long totalSize = 0;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(1)).showImageForEmptyUri(R.drawable.final_logo).showImageOnFail(R.drawable.final_logo).showImageOnLoading(R.drawable.final_logo).build();
    AsyncTask<String, Integer, String> taskUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_documents);
        appContext = this;
        setActionBar();
        initComponent();
    }


    private void initComponent() {
        imageLoader.init(ImageLoaderConfiguration.createDefault(appContext));
        cd = new ConnectionDetector(appContext);
        //Set Image Pick PopUp
        animationView();

        ((CustomTextView) findViewById(R.id.txtTakePhoto)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtChooseExisting)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtCancel)).setOnClickListener(this);

        String profilepicurl = "http://tracklabs.in:3000/api/file/profile_img_567a50175dd102030f4cf08a.jpg".replace(" ", "%20");
        String idProofUrl = "http://tracklabs.in:3000/api/file/id_proof_567a50175dd102030f4cf08a.jpg".replace(" ", "%20");
        String pancardUrl = "http://tracklabs.in:3000/api/file/pan_card_proof_567a50175dd102030f4cf08a.jpg".replace(" ", "%20");
        String servicetaxUrl = "http://tracklabs.in:3000/api/file/service_tax_proof_567a50175dd102030f4cf08a.jpg".replace(" ", "%20");
        String addrproofUrl = "http://tracklabs.in:3000/api/file/addr_proof_567a50175dd102030f4cf08a.jpg".replace(" ", "%20");
        String tinumberUrl = "http://tracklabs.in:3000/api/file/tin_number_proof_567a50175dd102030f4cf08a.jpg".replace(" ", "%20");
        String companylogoUrl = "http://tracklabs.in:3000/api/file/company_log_567a50175dd102030f4cf08a.jpg".replace(" ", "%20");

        imageLoader.displayImage(profilepicurl, ((ImageView) findViewById(R.id.imgvwProfilePicDoc)), options);
        imageLoader.displayImage(idProofUrl, ((ImageView) findViewById(R.id.imgvwPhotoIDDoc)), options);
        imageLoader.displayImage(pancardUrl, ((ImageView) findViewById(R.id.imgvwPANCardDoc)), options);
        imageLoader.displayImage(tinumberUrl, ((ImageView) findViewById(R.id.imgvwTinNoDoc)), options);
        imageLoader.displayImage(servicetaxUrl, ((ImageView) findViewById(R.id.imgvwServcieNoDoc)), options);
        imageLoader.displayImage(addrproofUrl, ((ImageView) findViewById(R.id.imgvwAddressProofDoc)), options);
        imageLoader.displayImage(companylogoUrl, ((ImageView) findViewById(R.id.imgvwCompanyLogoDoc)), options);
        setClickListener();

    }

    private void setClickListener() {
        //Set Upload Button Click Listener

        ((CustomTextView) findViewById(R.id.txtUploadProfilePic)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtUploadPhotoID)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtUploadPANCard)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtUploadTinNo)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtUploadCompanyLogo)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtUploadServiceTaxNumber)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.txtUploadAddressProof)).setOnClickListener(this);

        ((ImageView) findViewById(R.id.imgvwProfilePicDoc)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwReloadProfilePic)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwDoneProfilePic)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwEditProfilePic)).setOnClickListener(this);

        ((ImageView) findViewById(R.id.imgvwPhotoIDDoc)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwReloadPhotoID)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwDonePhotoID)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwEditPhotoID)).setOnClickListener(this);

        ((ImageView) findViewById(R.id.imgvwPANCardDoc)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwReloadPANCard)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwDonePANCard)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwEditPANCard)).setOnClickListener(this);


        ((ImageView) findViewById(R.id.imgvwTinNoDoc)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwReloadTinNo)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwDoneTinNo)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwEditTinNo)).setOnClickListener(this);


        ((ImageView) findViewById(R.id.imgvwServcieNoDoc)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwReloadServiceTaxNumber)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwDoneServiceTaxNumber)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwEditServiceTaxNumber)).setOnClickListener(this);


        ((ImageView) findViewById(R.id.imgvwCompanyLogoDoc)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwReloadCompanyLogo)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwDoneCompanyLogo)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwEditCompanyLogo)).setOnClickListener(this);

        ((ImageView) findViewById(R.id.imgvwAddressProofDoc)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwReloadAddressProof)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwDoneAddressProof)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgvwEditAddressProof)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtUploadProfilePic:
                pic_request = Constant.PROFILE_PIC_DOC;
                openActionSheet();
                break;
            case R.id.txtUploadPhotoID:
                pic_request = Constant.PHOTO_ID_DOC;
                openActionSheet();
                break;

            case R.id.txtUploadPANCard:
                pic_request = Constant.PAN_CARD_DOC;
                openActionSheet();
                break;

            case R.id.txtUploadTinNo:
                pic_request = Constant.TIN_NO_DOC;
                openActionSheet();
                break;

            case R.id.txtUploadServiceTaxNumber:
                pic_request = Constant.SERVICE_PROOF_DOC;
                openActionSheet();
                break;

            case R.id.txtUploadCompanyLogo:
                pic_request = Constant.COMPANY_LOGO_DOC;
                openActionSheet();
                break;
            case R.id.txtUploadAddressProof:
                pic_request = Constant.ADDRESS_PROOF_DOC;
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

    private void openActionSheet() {
        setActionSheetBackground();
        if (layoutActionSheet.getVisibility() == View.GONE) {
            layoutActionSheet.startAnimation(showPicker);
            layoutActionSheet.setVisibility(View.VISIBLE);
        } else {
            layoutActionSheet.startAnimation(hidePicker);
            layoutActionSheet.setVisibility(View.GONE);
        }
    }

    private void setActionSheetBackground() {
        ((RelativeLayout) findViewById(R.id.layoutActionAccountOption)).setBackgroundColor(getResources().getColor(R.color.color_white));
        ((CustomTextView) findViewById(R.id.txtTakePhoto)).setBackgroundColor(getResources().getColor(R.color.color_header));
        ((CustomTextView) findViewById(R.id.txtChooseExisting)).setBackgroundColor(getResources().getColor(R.color.color_header));
        ((CustomTextView) findViewById(R.id.txtCancel)).setBackgroundColor(getResources().getColor(R.color.color_header));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            finalFile = null;
            if (requestCode == REQUEST_CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(appContext, photo);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                finalFile = new File(getRealPathFromURI(tempUri));
                profile_pic_path = finalFile.getAbsolutePath();
                String uri = "file://" + profile_pic_path;

                if (pic_request == Constant.PROFILE_PIC_DOC) {
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwProfilePicDoc)), options);
                } else if (pic_request == Constant.PHOTO_ID_DOC) {
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwPhotoIDDoc)), options);
                } else if (pic_request == Constant.PAN_CARD_DOC) {
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwPANCardDoc)), options);
                } else if (pic_request == Constant.TIN_NO_DOC) {
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwTinNoDoc)), options);
                } else if (pic_request == Constant.SERVICE_PROOF_DOC) {
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwServcieNoDoc)), options);
                } else if (pic_request == Constant.COMPANY_LOGO_DOC) {
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwCompanyLogoDoc)), options);
                } else if (pic_request == Constant.ADDRESS_PROOF_DOC) {
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwAddressProof)), options);
                }

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String FilePath = cursor.getString(columnIndex);
                cursor.close();
                finalFile = new File(FilePath);
                profile_pic_path = finalFile.getAbsolutePath();
                String uri = "file://" + profile_pic_path;

                if (pic_request == Constant.PROFILE_PIC_DOC) {
                    FileUploadService service =
                            ServiceGeneratorFileUpload.createService(FileUploadService.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                    String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

                    Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                            UploadFileApiResponse upload_response=response.body();
                            if(upload_response!=null){
                                setUserDocuments(upload_response);
                            }
                            Toast.makeText(appContext, R.string.not_Profile_Image, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwProfilePicDoc)), options);

                    uploadMultipart(appContext, profile_pic_path);

                } else if (pic_request == Constant.PHOTO_ID_DOC) {

                    FileUploadService1 service =
                            ServiceGeneratorFileUpload.createService(FileUploadService1.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                    String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

                    Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                            UploadFileApiResponse upload_response=response.body();
                            if(upload_response!=null){
                                setUserDocuments(upload_response);
                            }
                            Toast.makeText(appContext, R.string.not_Id_Proof, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());
                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwPhotoIDDoc)), options);
                } else if (pic_request == Constant.PAN_CARD_DOC) {

                    FileUploadService2 service =
                            ServiceGeneratorFileUpload.createService(FileUploadService2.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                    String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

                    Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                            UploadFileApiResponse upload_response=response.body();
                            if(upload_response!=null){
                                setUserDocuments(upload_response);
                            }
                            Toast.makeText(appContext, R.string.not_Pan_card_proof, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());
                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwPANCardDoc)), options);
                } else if (pic_request == Constant.TIN_NO_DOC) {

                    FileUploadService3 service =
                            ServiceGeneratorFileUpload.createService(FileUploadService3.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                    String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

                    Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                            UploadFileApiResponse upload_response=response.body();
                            if(upload_response!=null){
                                setUserDocuments(upload_response);
                            }
                            Toast.makeText(appContext, R.string.not_Tin_number_proof, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());
                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwTinNoDoc)), options);
                } else if (pic_request == Constant.SERVICE_PROOF_DOC) {

                    FileUploadService4 service =
                            ServiceGeneratorFileUpload.createService(FileUploadService4.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                    String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

                    Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                            UploadFileApiResponse upload_response=response.body();
                            if(upload_response!=null){
                                setUserDocuments(upload_response);
                            }
                            Toast.makeText(appContext, R.string.not_Service_tax_poof, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());
                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwServcieNoDoc)), options);
                } else if (pic_request == Constant.COMPANY_LOGO_DOC) {

                    FileUploadService5 service =
                            ServiceGeneratorFileUpload.createService(FileUploadService5.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                    String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

                    Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                            UploadFileApiResponse upload_response=response.body();
                            if(upload_response!=null){
                                setUserDocuments(upload_response);
                            }
                            Toast.makeText(appContext, R.string.not_Company_logo, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());

                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwCompanyLogoDoc)), options);
                } else if (pic_request == Constant.ADDRESS_PROOF_DOC) {

                    FileUploadService6 service =
                            ServiceGeneratorFileUpload.createService(FileUploadService6.class);

                    File file = new File(profile_pic_path);
                    String mimeType = getMimeType(profile_pic_path);
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse(mimeType), file);
                    String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
                    String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

                    Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
                    call.enqueue(new Callback<UploadFileApiResponse>() {
                        @Override
                        public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                            UploadFileApiResponse upload_response=response.body();
                            if(upload_response!=null){
                                setUserDocuments(upload_response);
                            }
                            Toast.makeText(appContext, R.string.not_Address_proof_Bilty, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("ComeON", t.getMessage());
                        }
                    });
                    imageLoader.displayImage(uri, ((ImageView) findViewById(R.id.imgvwAddressProofDoc)), options);
                }

            }
        }
    }

    private void setUserDocuments(UploadFileApiResponse upload_response){
        if(upload_response.getUser().getProfileImg()!=null){
            if(upload_response.getUser().getProfileImg().trim().length()>0){
                PreferenceClass.setStringPreference(appContext,Constant.USER_PROFILE_PIC,upload_response.getUser().getProfileImg().trim());
            }
        }

        if(upload_response.getUser().getCompanyLog()!=null){
            if(upload_response.getUser().getCompanyLog().trim().length()>0){
                PreferenceClass.setStringPreference(appContext,Constant.USER_COMAPNY_LOGO_PIC,upload_response.getUser().getCompanyLog().trim());
            }
        }

        if(upload_response.getUser().getIdProof()!=null){
            if(upload_response.getUser().getIdProof().trim().length()>0){
                PreferenceClass.setStringPreference(appContext,Constant.USER_PHOTO_ID_PROOF_PIC,upload_response.getUser().getIdProof().trim());
            }
        }

        if(upload_response.getUser().getPanCardProof()!=null){
            if(upload_response.getUser().getPanCardProof().trim().length()>0){
                PreferenceClass.setStringPreference(appContext,Constant.USER_PAN_CARD_PIC,upload_response.getUser().getPanCardProof().trim());
            }
        }

        if(upload_response.getUser().getAddrProof()!=null){
            if(upload_response.getUser().getAddrProof().trim().length()>0){
                PreferenceClass.setStringPreference(appContext,Constant.USER_ADDRESS_PROOF_PIC,upload_response.getUser().getAddrProof().trim());
            }
        }

        if(upload_response.getUser().getTinNumberProof()!=null){
            if(upload_response.getUser().getTinNumberProof().trim().length()>0){
                PreferenceClass.setStringPreference(appContext,Constant.USER_TIN_NUM_PROOF_PIC,upload_response.getUser().getTinNumberProof().trim());
            }
        }

        if(upload_response.getUser().getServiceTaxProof()!=null){
            if(upload_response.getUser().getServiceTaxProof().trim().length()>0){
                PreferenceClass.setStringPreference(appContext,Constant.USER_SERVICE_TAX_NUM_PIC,upload_response.getUser().getServiceTaxProof().trim());
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

        layoutActionSheet = (RelativeLayout) findViewById(R.id.layoutActionAccountOption);
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

    private void setActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.layoutHeader);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
    }
/*
    public int uploadFile(String sourceFileUri, int pic_request) {


        if (pic_request == Constant.PROFILE_PIC_DOC) {
            FileUploadService service =
                    ServiceGeneratorFileUpload.createService(FileUploadService.class);

            File file = new File(profile_pic_path);
            String mimeType = getMimeType(profile_pic_path);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(mimeType), file);
            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
            String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

            Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
            call.enqueue(new Callback<UploadFileApiResponse>() {
                @Override
                public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                    //     Log.e("ComeON", "4********" + response.body().toString());
                    //  up = response.body();
                    Log.e("ComeON", Integer.toString(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ComeON", t.getMessage());

                }
            });
        } else if (pic_request == Constant.PHOTO_ID_DOC) {
            FileUploadService1 service =
                    ServiceGeneratorFileUpload.createService(FileUploadService1.class);

            File file = new File(profile_pic_path);
            String mimeType = getMimeType(profile_pic_path);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(mimeType), file);
            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
            String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

            Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
            call.enqueue(new Callback<UploadFileApiResponse>() {
                @Override
                public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                    //     Log.e("ComeON", "4********" + response.body().toString());
                    //  up = response.body();
                    Log.e("ComeON", Integer.toString(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ComeON", t.getMessage());

                }
            });
        } else if (pic_request == Constant.PAN_CARD_DOC) {
            FileUploadService2 service =
                    ServiceGeneratorFileUpload.createService(FileUploadService2.class);

            File file = new File(profile_pic_path);
            String mimeType = getMimeType(profile_pic_path);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(mimeType), file);
            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
            String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

            Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
            call.enqueue(new Callback<UploadFileApiResponse>() {
                @Override
                public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                    //     Log.e("ComeON", "4********" + response.body().toString());
                    //  up = response.body();
                    Log.e("ComeON", Integer.toString(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ComeON", t.getMessage());

                }
            });
        } else if (pic_request == Constant.TIN_NO_DOC) {
            FileUploadService3 service =
                    ServiceGeneratorFileUpload.createService(FileUploadService3.class);

            File file = new File(profile_pic_path);
            String mimeType = getMimeType(profile_pic_path);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(mimeType), file);
            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
            String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

            Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
            call.enqueue(new Callback<UploadFileApiResponse>() {
                @Override
                public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                    //     Log.e("ComeON", "4********" + response.body().toString());
                    //  up = response.body();
                    Log.e("ComeON", Integer.toString(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ComeON", t.getMessage());

                }
            });
        } else if (pic_request == Constant.SERVICE_PROOF_DOC) {
            FileUploadService4 service =
                    ServiceGeneratorFileUpload.createService(FileUploadService4.class);

            File file = new File(profile_pic_path);
            String mimeType = getMimeType(profile_pic_path);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(mimeType), file);
            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
            String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

            Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
            call.enqueue(new Callback<UploadFileApiResponse>() {
                @Override
                public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                    //     Log.e("ComeON", "4********" + response.body().toString());
                    //  up = response.body();
                    Log.e("ComeON", Integer.toString(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ComeON", t.getMessage());

                }
            });
        } else if (pic_request == Constant.COMPANY_LOGO_DOC) {
            FileUploadService5 service =
                    ServiceGeneratorFileUpload.createService(FileUploadService5.class);

            File file = new File(profile_pic_path);
            String mimeType = getMimeType(profile_pic_path);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(mimeType), file);
            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
            String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

            Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
            call.enqueue(new Callback<UploadFileApiResponse>() {
                @Override
                public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                    //     Log.e("ComeON", "4********" + response.body().toString());
                    //  up = response.body();
                    Log.e("ComeON", Integer.toString(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ComeON", t.getMessage());

                }
            });
        } else if (pic_request == Constant.ADDRESS_PROOF_DOC) {
            FileUploadService6 service =
                    ServiceGeneratorFileUpload.createService(FileUploadService6.class);
            File file = new File(profile_pic_path);
            String mimeType = getMimeType(profile_pic_path);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(mimeType), file);
            String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
            String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);

            Call<UploadFileApiResponse> call = service.upload(user_id, jwt, requestBody);
            call.enqueue(new Callback<UploadFileApiResponse>() {
                @Override
                public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                    //     Log.e("ComeON", "4********" + response.body().toString());
                    //  up = response.body();
                    Log.e("ComeON", Integer.toString(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ComeON", t.getMessage());

                }
            });
        }

        return 1;
    }

    public int uploadFile2(String sourceFileUri, String image_name) {
        Map<String, RequestBody> map = new HashMap<>();
        File file = null;
        RequestBody fileBody = null;
        if (sourceFileUri != null) {
            file = new File(sourceFileUri);
            fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);

            map.put("file\"; filename=\"pp.png\"", fileBody);
        }
        DynamicFilenameUploadService service =
                ServiceGeneratorFileUpload.createService(DynamicFilenameUploadService.class);
        String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
        String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);
        map.put(image_name, fileBody);
        Call<UploadFileApiResponse> call = service.upload(user_id, jwt, map);
        call.enqueue(new Callback<UploadFileApiResponse>() {
            @Override
            public void onResponse(Response<UploadFileApiResponse> response, Retrofit retrofit) {
                Log.e("ComeON", Integer.toString(response.code()));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ComeON", t.getMessage());

            }
        });
        return 1;
    }*/

    public void uploadMultipart(final Context context, String path) {
        Log.e("ComeON", "Upload multipart 1");

        final String uploadID = UUID.randomUUID().toString();
        String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
        String user_id = PreferenceClass.getStringPreferences(appContext, Constant.USER_ID);
        final String serverUrlString = "http://tracklabs.in:3000/api/users/" + user_id;
        Log.e("ComeON", "Upload multipart 2");
        try {
            new MultipartUploadRequest(context, uploadID, serverUrlString)
                    .addHeader("Authorization", jwt)
                    .addFileToUpload(path, "profile_img")
                    .setMethod("PUT")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
            Log.e("ComeON", "Upload multipart 3");
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }

    private final UploadServiceBroadcastReceiver uploadReceiver =
            new UploadServiceBroadcastReceiver() {

                // you can override this progress method if you want to get
                // the completion progress in percent (0 to 100)
                // or if you need to know exactly how many bytes have been transferred
                // override the method below this one
                @Override
                public void onProgress(String uploadId, int progress) {
                    Log.i(TAG, "The progress of the upload with ID "
                            + uploadId + " is: " + progress);
                }

                @Override
                public void onProgress(final String uploadId,
                                       final long uploadedBytes,
                                       final long totalBytes) {
                    Log.i(TAG, "Upload with ID " + uploadId +
                            " uploaded bytes: " + uploadedBytes
                            + ", total: " + totalBytes);
                }

                @Override
                public void onError(String uploadId, Exception exception) {
                    Log.e(TAG, "Error in upload with ID: " + uploadId + ". "
                            + exception.getLocalizedMessage(), exception);
                }

                @Override
                public void onCompleted(String uploadId,
                                        int serverResponseCode,
                                        String serverResponseMessage) {
                    Log.i(TAG, "Upload with ID " + uploadId
                            + " has been completed with HTTP " + serverResponseCode
                            + ". Response from server: " + serverResponseMessage);


                    //If your server responds with a JSON, you can parse it
                    //from serverResponseMessage string using a library
                    //such as org.json (embedded in Android) or Google's gson
                }
            };

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
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


