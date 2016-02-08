package in.futuretrucks.services;

import com.squareup.okhttp.RequestBody;

import java.util.Map;

import in.futuretrucks.model.UploadFileApiResponse;
import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.PartMap;
import retrofit.http.Path;

/**
 * Created by futuretrucks on 5/1/16.
 */
public interface DynamicFilenameUploadService {
    @Multipart
    @PUT("/api/users/{user_id}")
    Call<UploadFileApiResponse> upload(
            @Path("user_id") String user_id,
            @Header("Authorization") String authorization,
            @PartMap Map<String,RequestBody> params);

}
