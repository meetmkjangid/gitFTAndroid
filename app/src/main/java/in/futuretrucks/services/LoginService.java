package in.futuretrucks.services;

import in.futuretrucks.model.AdvertiseLoad;
import in.futuretrucks.model.AdvertiseLoadApiResponse;
import in.futuretrucks.model.LoginModel;
import in.futuretrucks.response.LoginAPIResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by futuretrucks on 5/2/16.
 */
public interface LoginService {
    @POST("/auth/login/")
    Call<LoginAPIResponse> login(@Body LoginModel loginObject) ;
}
