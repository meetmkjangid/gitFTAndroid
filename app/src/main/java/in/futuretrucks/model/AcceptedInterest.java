package in.futuretrucks.model;

/**
 * Created by futuretrucks on 26/12/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AcceptedInterest {

    @SerializedName("interest_id")
    @Expose
    private String interestId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("accept_price_posting")
    @Expose
    private Integer acceptPricePosting;
    @SerializedName("confirmBy_interest")
    @Expose
    private Boolean confirmByInterest;

    /**
     *
     * @return
     * The interestId
     */
    public String getInterestId() {
        return interestId;
    }

    /**
     *
     * @param interestId
     * The interest_id
     */
    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    /**
     *
     * @return
     * The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The acceptPricePosting
     */
    public Integer getAcceptPricePosting() {
        return acceptPricePosting;
    }

    /**
     *
     * @param acceptPricePosting
     * The accept_price_posting
     */
    public void setAcceptPricePosting(Integer acceptPricePosting) {
        this.acceptPricePosting = acceptPricePosting;
    }

    /**
     *
     * @return
     * The confirmByInterest
     */
    public Boolean getConfirmByInterest() {
        return confirmByInterest;
    }

    /**
     *
     * @param confirmByInterest
     * The confirmBy_interest
     */
    public void setConfirmByInterest(Boolean confirmByInterest) {
        this.confirmByInterest = confirmByInterest;
    }

}