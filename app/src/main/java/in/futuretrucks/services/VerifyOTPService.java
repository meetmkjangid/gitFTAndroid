package in.futuretrucks.services;

import in.futuretrucks.UserApiResponse;
import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by futuretrucks on 22/12/15.
 */
public interface VerifyOTPService {
    @POST("/verify_otp")
    Call<UserApiResponse> Logout(
            @Part("mobile") long mobile,
            @Part("password") String password,
            @Part("otp") long otp);
}
