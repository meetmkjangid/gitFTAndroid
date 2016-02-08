package in.futuretrucks.services;

import com.squareup.okhttp.RequestBody;

import in.futuretrucks.model.UploadDriverFileApiResponse;
import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by futuretrucks on 13/12/15.
 */
public interface DriverAddressProofDocUploadService {
    @Multipart
    @PUT("/api/drivers/{driver_id}")
    Call<UploadDriverFileApiResponse> upload(
            @Path("driver_id") String driver_id,
            @Header("Authorization") String authorization,
            @Part("address_proof_doc\"; filename=\"image.jpg\" ") RequestBody file);

}