package in.futuretrucks.services;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Service;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

public class UploadDocumentService extends Service {
    private static final String TAG = "Location Service";

    @Override
    public IBinder onBind(Intent intent) {
	return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart(Intent intent, int startid) {
        new UpdatedDocuments().execute();
    }

    public class UpdatedDocuments extends AsyncTask<String, Void, String> {
        //private ProgressHUD progress;
        protected void onPreExecute() {
            super.onPreExecute();

            UpdatedDocuments.this.cancel(true);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            int result_code=0;
            System.out.println("In Upload Service");
            result_code=uploadFile("profile_img",PreferenceClass.getStringPreferences(getApplicationContext(),"Image_Path_service"));
            return ""+result_code;
        }

        @SuppressLint("InlinedApi")
        protected void onPostExecute(String result) {
            System.out.println("result is " + result);
            System.out.println("In Upload Service Responses");
            if(result.equals("200")){
                Toast.makeText(getApplicationContext(),"Documents uploaded successfully",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Response; "+result,Toast.LENGTH_LONG).show();
            }
        }
    }


    private int uploadFile(String file_key,String sourceFileUri) {


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
        System.out.println("Source File is: " + sourceFile);

        if (!sourceFile.isFile()) {
            System.out.println("Source File not exist");
            /*runOnUiThread(new Runnable() {
                public void run() {
                    System.out.println("Source File not exist");
                }
            });*/

            return 0;

        }
        else
        {
            try {
                // open a URL connection to the Servlet
                String url22= Constant.BASE_URL+Constant.REGISTRATION+ PreferenceClass.getStringPreferences(getApplicationContext(), Constant.USER_ID);
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
                conn.setRequestProperty("Authorization", PreferenceClass.getStringPreferences(getApplicationContext(), Constant.USER_TOKEN));
                conn.setRequestProperty(file_key, fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name="+file_key+";filename="+ fileName +""+ lineEnd);
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


                serverResponseCode= conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);
                System.out.println("Response Code is :  " + serverResponseCode);
                if(serverResponseCode == 200){
                    System.out.println("Result 200");
                    /*runOnUiThread(new Runnable() {
                        public void run() {


                        }
                    });*/
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
//                    }
//                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                e.printStackTrace();

//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
            return serverResponseCode;

        } // End else block
    }

}
