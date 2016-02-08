package in.futuretrucks.services;

import in.futuretrucks.model.AdvertiseTruck;
import in.futuretrucks.model.AdvertiseTruckApiResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by futuretrucks on 2/2/16.
 */
public interface AdvertiseTruckService {
    @POST("/api/posting/")
    Call<AdvertiseTruckApiResponse> advertiseTruck(@Body AdvertiseTruck task,@Header("Authorization") String authorization) ;
}
