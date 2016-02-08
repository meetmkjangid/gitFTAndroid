package in.futuretrucks;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.futuretrucks.model.User;

public class UserApiResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserApiResponse() {
    }

    /**
     *
     * @param token
     * @param status
     * @param user
     */
    public UserApiResponse(String status, String token, User user) {
        this.status = status;
        this.token = token;
        this.user = user;
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

}