package in.futuretrucks.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.R;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

public class DocumentsFragment extends Fragment implements View.OnClickListener{
    private View rootvView;
    private Context appContext;
    private ProgressHUD progress;
    private RelativeLayout layoutActionSheet;
    private Animation showPicker, hidePicker;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static File finalFile=null;
    private String profile_pic_path="";
    private ConnectionDetector cd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootvView= inflater.inflate(R.layout.fragment_documents, container, false);

        appContext=this.getActivity();
        initComponent();
        return rootvView;
    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        //Set Image Pick PopUp
        animationView();
        ((CustomButton)rootvView.findViewById(R.id.btnUploadProfilePic)).setOnClickListener(this);
        ((CustomButton)rootvView.findViewById(R.id.btnSave)).setOnClickListener(this);
        ((CustomTextView)rootvView.findViewById(R.id.txtTakePhoto)).setOnClickListener(this);
        ((CustomTextView)rootvView.findViewById(R.id.txtChooseExisting)).setOnClickListener(this);
        ((CustomTextView)rootvView.findViewById(R.id.txtCancel)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnUploadProfilePic){
            if (layoutActionSheet.getVisibility() == View.GONE) {
                layoutActionSheet.startAnimation(showPicker);
                layoutActionSheet.setVisibility(View.VISIBLE);
            } else {
                layoutActionSheet.startAnimation(hidePicker);
                layoutActionSheet.setVisibility(View.GONE);
            }
        }else if(v.getId()==R.id.txtTakePhoto){
            layoutActionSheet.startAnimation(hidePicker);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }else if(v.getId()==R.id.txtChooseExisting){
            layoutActionSheet.startAnimation(hidePicker);
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        }else if(v.getId()==R.id.txtCancel){
            layoutActionSheet.startAnimation(hidePicker);
        }else if(v.getId()==R.id.btnSave){
            if(profile_pic_path.trim().length()==0){
                Toast.makeText(appContext,"Please select profile photo first.",Toast.LENGTH_LONG).show();
            }else{
                if(cd.isConnectingToInternet()){
                    new UpdatedDocuments().execute();
                }else {
                    Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public class UpdatedDocuments extends AsyncTask<String, Void, String> {
        //private ProgressHUD progress;
        protected void onPreExecute() {
            super.onPreExecute();

            progress = ProgressHUD.show(appContext, "Please wait", true, true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    UpdatedDocuments.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            int result_code=0;
            String url;
            System.out.println("Profile Path is:  "+profile_pic_path);
            result_code=uploadFile(profile_pic_path);

            return ""+result_code;
        }

        @SuppressLint("InlinedApi")
        protected void onPostExecute(String result) {
            System.out.println("result is " + result);
            if(progress.isShowing()){
                progress.dismiss();
            }
            Toast.makeText(appContext,"Response Code is"+result,Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            finalFile=null;
            if (requestCode == REQUEST_CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(appContext, photo);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                finalFile = new File(getRealPathFromURI(tempUri));
                profile_pic_path=finalFile.getAbsolutePath();
                ((CustomTextView)rootvView.findViewById(R.id.txtProfilePicPath)).setText(profile_pic_path);
                String uri="file://"+profile_pic_path;

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String [] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String FilePath = cursor.getString(columnIndex);
                cursor.close();
                finalFile = new File(FilePath);
                profile_pic_path=finalFile.getAbsolutePath();
                ((CustomTextView)rootvView.findViewById(R.id.txtProfilePicPath)).setText(profile_pic_path);
                String uri="file://"+profile_pic_path;

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
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void animationView() {

        layoutActionSheet = (RelativeLayout)rootvView.findViewById(R.id.layoutActionAccountOption);
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

    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;
        int serverResponseCode=0;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        File sourceFile = new File(sourceFileUri);

        System.out.println("Source File Path is: "+fileName);
        System.out.println("Source File is: "+sourceFile);

        if (!sourceFile.isFile()) {
            System.out.println("Source File not exist");

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    System.out.println("Source File not exist");
                }
            });

            return 0;

        }
        else
        {
            try {
                // open a URL connection to the Servlet
                String url22=Constant.BASE_URL+Constant.REGISTRATION+PreferenceClass.getStringPreferences(appContext,Constant.USER_ID);
                System.out.println("File path is: "+sourceFileUri);
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(url22);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "jpg/png; charset=utf-8");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("Authorization", PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN));
                conn.setRequestProperty("profile_img", fileName);
                conn.setRequestProperty("owner_name", "VIVEK PAL TEST");


                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name="+"profile_img"+";filename="+ fileName +""+ lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode= conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);
                System.out.println("Response Code is :  " + serverResponseCode);
                if(serverResponseCode == 200){

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(appContext, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(appContext, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(appContext, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return serverResponseCode;

        } // End else block
    }
}