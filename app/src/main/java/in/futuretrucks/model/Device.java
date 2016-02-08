package in.futuretrucks.model;

/**
 * Created by mahadev on 2/5/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Device {

    @SerializedName("dtype")
    @Expose
    private String dtype;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("IMEI")
    @Expose
    private String IMEI;
    @SerializedName("_id")
    @Expose
    private String Id;

    /**
     *
     * @return
     * The dtype
     */
    public String getDtype() {
        return dtype;
    }

    /**
     *
     * @param dtype
     * The dtype
     */
    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The IMEI
     */
    public String getIMEI() {
        return IMEI;
    }

    /**
     *
     * @param IMEI
     * The IMEI
     */
    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

}
