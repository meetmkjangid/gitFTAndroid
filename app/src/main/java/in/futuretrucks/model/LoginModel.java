package in.futuretrucks.model;

/**
 * Created by mahadev on 2/5/16.
 */
import java.util.ArrayList;
        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("devices")
    @Expose
    private Device devices;

    /**
     *
     * @return
     * The mobile
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The devices
     */
    public Device getDevices() {
        return devices;
    }

    /**
     *
     * @param devices
     * The devices
     */
    public void setDevices(Device devices) {
        this.devices = devices;
    }

}