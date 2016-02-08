package in.futuretrucks.services;

import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.futuretrucks.SignUpApiResponse;
import in.futuretrucks.model.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by futuretrucks on 22/12/15.
 */
public interface SignUpApiService {
    @FormUrlEncoded
    @POST("/api/login")
    public void login(@Field("mobile") String email,
                      @Field("password") String password,
                      Callback<JSONObject> object);
}
