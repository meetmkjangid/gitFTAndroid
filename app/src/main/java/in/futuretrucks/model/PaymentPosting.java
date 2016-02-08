package in.futuretrucks.model;

/**
 * Created by futuretrucks on 26/12/15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PaymentPosting {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("options")
    @Expose
    private List<Object> options = new ArrayList<Object>();
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("code")
    @Expose
    private String code;

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
     * The options
     */
    public List<Object> getOptions() {
        return options;
    }

    /**
     *
     * @param options
     * The options
     */
    public void setOptions(List<Object> options) {
        this.options = options;
    }

    /**
     *
     * @return
     * The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     *
     * @param mode
     * The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PaymentPosting{" +
                "status='" + status + '\'' +
                ", options=" + options +
                ", mode='" + mode + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}