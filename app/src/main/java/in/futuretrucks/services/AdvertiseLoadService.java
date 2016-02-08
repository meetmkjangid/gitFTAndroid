package in.futuretrucks.services;

import in.futuretrucks.model.AdvertiseLoad;
import in.futuretrucks.model.AdvertiseLoadApiResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by futuretrucks on 5/2/16.
 */
public interface AdvertiseLoadService {
    @POST("/api/posting/")
    Call<AdvertiseLoadApiResponse> advertiseLoad(@Body AdvertiseLoad task,@Header("Authorization") String authorization) ;
}
