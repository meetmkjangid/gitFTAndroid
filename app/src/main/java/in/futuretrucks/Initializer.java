package in.futuretrucks;

import com.alexbbb.uploadservice.UploadService;

/**
 * Created by futuretrucks on 5/1/16.
 */
public class Initializer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Or, you can define it manually.
      //  UploadService.NAMESPACE = "com.yourcompany.yourapp";
    }
}
