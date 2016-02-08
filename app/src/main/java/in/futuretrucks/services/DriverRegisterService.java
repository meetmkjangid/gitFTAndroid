package in.futuretrucks.services;

import org.json.JSONObject;

import in.futuretrucks.model.AdvertiseTruck;
import in.futuretrucks.model.AdvertiseTruckApiResponse;
import in.futuretrucks.model.DriverRegistrationModel;
import in.futuretrucks.response.DriverRegApiResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by futuretrucks on 2/2/16.
 */
public interface DriverRegisterService {
    @POST("/api/drivers/")
    Call<DriverRegApiResponse> registerDriver(@Body DriverRegistrationModel regObj, @Header("Authorization") String authorization) ;
}
