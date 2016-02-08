package in.futuretrucks.services;

import in.futuretrucks.model.GooglePlacesApiResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by futuretrucks on 2/2/16.
 */
public interface GooglePlacesApiService {
    @GET("/maps/api/place/details/json")
    Call<GooglePlacesApiResponse> getPlaces(
            @Query("placeid") String placeId,
            @Query("key") String key);

}
