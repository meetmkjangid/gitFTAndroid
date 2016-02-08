package in.futuretrucks.services;

import in.futuretrucks.UserApiResponse;
import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by futuretrucks on 22/12/15.
 */
public interface LogoutApiService {
    @POST("/logout")
    Call<UserApiResponse> Logout(
            @Header("Authorization") String authorization);
}
