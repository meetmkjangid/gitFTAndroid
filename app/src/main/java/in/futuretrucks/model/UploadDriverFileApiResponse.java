package in.futuretrucks.model;

/**
 * Created by futuretrucks on 5/2/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadDriverFileApiResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("driver")
    @Expose
    private Driver driver;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     *
     * @param driver
     * The driver
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}