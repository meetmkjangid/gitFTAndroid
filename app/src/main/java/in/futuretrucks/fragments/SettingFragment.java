package in.futuretrucks.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.futuretrucks.R;
import in.futuretrucks.services.LoginApiService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.json.JSONObject;

import java.io.IOException;

public class SettingFragment extends Fragment{
    public SettingFragment() {
    }

    private static final String TAG = "RecyclerViewExample";
    private View rootView;
    private Context appContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        appContext = this.getActivity();

        return rootView;
    }
}
