package in.futuretrucks.services;

import java.util.List;

import in.futuretrucks.model.Truck;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by futuretrucks on 11/1/16.
 */
public interface TruckApiService {
    @GET("/api/trucks/")
    Call<List<Truck>> getTrucksList(
            @Header("Authorization") String authorization);
}
