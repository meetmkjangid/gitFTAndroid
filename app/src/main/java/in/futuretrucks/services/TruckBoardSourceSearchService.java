package in.futuretrucks.services;

import in.futuretrucks.model.TruckBoardApiResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by futuretrucks on 14/12/15.
 */
public interface TruckBoardSourceSearchService {
    @GET("/api/posting/")
    Call<TruckBoardApiResponse> getTruckBoardSourceSearchList(
            @Query("sortby") String sortby,
            @Query("no_of_docs") String no_of_docs,
            @Query("type") String type,
            @Query("otherCustomers") String otherCustomers,
            @Query("source") String source,
            @Query("skip") String skip,
            @Header("Authorization") String authorization);
}
