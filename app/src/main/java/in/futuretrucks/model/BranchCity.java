package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchCity {

    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("_id")
    @Expose
    private String Id;

    /**
     * No args constructor for use in serialization
     *
     */
    public BranchCity() {
    }

    /**
     *
     * @param pincode
     * @param Id
     */
    public BranchCity(String pincode, String Id) {
        this.pincode = pincode;
        this.Id = Id;
    }

    /**
     *
     * @return
     * The pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     *
     * @param pincode
     * The pincode
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
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