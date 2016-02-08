package in.futuretrucks.services;

import org.json.JSONObject;

import in.futuretrucks.UserApiResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Part;

public interface LoginApiService {
    @FormUrlEncoded
    @POST("auth/login")
    public Call<JSONObject> login(@Field("mobile") String email, @Field("password") String password);
}
