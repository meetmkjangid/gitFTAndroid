package in.futuretrucks;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import in.futuretrucks.services.DriverApiService;
import in.futuretrucks.services.LoginApiService;
import in.futuretrucks.services.SignUpApiService;
import in.futuretrucks.services.TruckApiService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by futuretrucks on 13/12/15.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://tracklabs.in:3000/";

    private static OkHttpClient httpClient = new OkHttpClient();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {

        if(serviceClass== SignUpApiService.class || serviceClass== LoginApiService.class){
            System.out.println("serviceClass.toString():   "+serviceClass.toString());
            Log.e("debug service generator",serviceClass.toString());
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("cache-control", "no-cache")
                            .header("Content-Type", "multipart/form-data")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }else if (serviceClass == DriverApiService.class || serviceClass == TruckApiService.class) {

            Log.e("debug",serviceClass.toString());
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("cache-control", "no-cache")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        else {
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Content-Type", "multipart/form-data")
                            .header("cache-control","no-cache")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }


}