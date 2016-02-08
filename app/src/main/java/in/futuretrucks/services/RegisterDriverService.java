package in.futuretrucks.services;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by futuretrucks on 2/1/16.
 */
public interface RegisterDriverService {
    @Multipart
    @POST("/api/drivers/")
    Call<String> upload(
            @Path("user_id") String user_id,
            @Header("Authorization") String authorization,
            @Part("profile_img\"; filename=\"image.png\" ") RequestBody file);
}
