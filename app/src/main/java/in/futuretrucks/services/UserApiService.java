package in.futuretrucks.services;

import in.futuretrucks.UserApiResponse;
import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by futuretrucks on 22/12/15.
 */
public interface UserApiService {
    @POST("/auth/login")
    Call<UserApiResponse> login(
            @Part("mobile") long mobile,
            @Part("password") String password);
}
