package in.futuretrucks.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mahadev on 2/5/16.
 */
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

import in.futuretrucks.model.User;

public class LoginAPIResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("otp_auth_required")
    @Expose
    private Boolean otpAuthRequired;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    /**
     *
     * @return
     * The otpAuthRequired
     */
    public Boolean getOtpAuthRequired() {
        return otpAuthRequired;
    }

    /**
     *
     * @param otpAuthRequired
     * The otp_auth_required
     */
    public void setOtpAuthRequired(Boolean otpAuthRequired) {
        this.otpAuthRequired = otpAuthRequired;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

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
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param errorMessage
     * The error_message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
