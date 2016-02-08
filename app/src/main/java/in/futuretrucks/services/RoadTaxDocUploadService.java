package in.futuretrucks.services;

import com.squareup.okhttp.RequestBody;

import in.futuretrucks.model.UploadVehicleFileApiResponse;
import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by futuretrucks on 13/12/15.
 */
public interface RoadTaxDocUploadService {
    @Multipart
    @PUT("/api/trucks/{truck_id}")
    Call<UploadVehicleFileApiResponse> upload(
            @Path("truck_id") String truck_id,
            @Header("Authorization") String authorization,
            @Part("road_tax_doc\"; filename=\"image.jpg\" ") RequestBody file);

}