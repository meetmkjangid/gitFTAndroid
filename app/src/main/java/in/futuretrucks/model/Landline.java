package in.futuretrucks.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Landline {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("number")
    @Expose
    private String number;

    /**
     * No args constructor for use in serialization
     *
     */
    public Landline() {
    }

    /**
     *
     * @param number
     * @param code
     */
    public Landline(String code, String number) {
        this.code = code;
        this.number = number;
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

    /**
     *
     * @return
     * The number
     */
    public String getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

}