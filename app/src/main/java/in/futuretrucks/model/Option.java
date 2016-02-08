package in.futuretrucks.model;

/**
 * Created by futuretrucks on 28/12/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Option {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("cost")
    @Expose
    private String cost;

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
     * The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     * The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     * The cost
     */
    public String getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     * The cost
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Option{" +
                "Id='" + Id + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", cost=" + cost +
                '}';
    }
}