package in.futuretrucks.services;

import java.util.List;

import in.futuretrucks.model.Driver;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by futuretrucks on 16/12/15.
 */
public interface DriverApiService {
    @GET("/api/drivers/")
    Call<List<Driver>> getDriversList(
            /*@Query("userId") String user_id,*/
            @Header("Authorization") String authorization);

}
