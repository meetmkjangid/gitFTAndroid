package in.futuretrucks.services;

import com.squareup.okhttp.RequestBody;

import in.futuretrucks.model.UploadFileApiResponse;
import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by futuretrucks on 3/1/16.
 */
public interface FileUp22 {
    @Multipart
    @PUT("/api/users/{user_id}")
    Call<UploadFileApiResponse> upload(
            @Path("user_id") String user_id,
            @Header("Authorization") String authorization,
            @Part("id_proof\"; filename=\"image.png\" ") RequestBody file);
}
