package in.futuretrucks.services;

import in.futuretrucks.model.DriverRegistrationModel;
import in.futuretrucks.model.TruckRegisterModel;
import in.futuretrucks.response.DriverRegApiResponse;
import in.futuretrucks.response.TruckRegApiResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by futuretrucks on 2/2/16.
 */
public interface TruckRegisterService {
    @POST("/api/trucks/")
    Call<TruckRegApiResponse> registerTruck(@Body TruckRegisterModel regObj, @Header("Authorization") String authorization) ;
}
