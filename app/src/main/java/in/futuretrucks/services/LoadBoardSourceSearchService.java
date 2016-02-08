package in.futuretrucks.services;

import in.futuretrucks.model.LoadBoardApiResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by futuretrucks on 14/12/15.
 */
public interface LoadBoardSourceSearchService {

    //sortby=available_date:-1&no_of_docs=15&type=load&otherCustomers=true&=new%20delhi&=faridabad
    @GET("/api/posting/")
    Call<LoadBoardApiResponse> sourceSearchLoadBoardList(
            @Query("sortby") String sortby,
            @Query("no_of_docs") String no_of_docs,
            @Query("type") String type,
            @Query("otherCustomers") String otherCustomers,
            @Query("source") String source,
            @Query("skip") String skip,
            @Header("Authorization") String authorization);
}
