package in.futuretrucks.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class TruckType {

    @SerializedName("truck_type")
    @Expose
    private String truckType;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("_id")
    @Expose
    private String Id;

    /**
     * No args constructor for use in serialization
     *
     */
    public TruckType() {
    }

    /**
     *
     * @param truckType
     * @param Id
     * @param code
     */
    public TruckType(String truckType, String code, String Id) {
        this.truckType = truckType;
        this.code = code;
        this.Id = Id;
    }

    /**
     *
     * @return
     * The truckType
     */
    public String getTruckType() {
        return truckType;
    }

    /**
     *
     * @param truckType
     * The truck_type
     */
    public void setTruckType(String truckType) {
        this.truckType = truckType;
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
     * The Id
     */
    public String getId() {
        return Id;
    }

    @Override
    public String toString() {
        return "TruckType{" +
                "truckType='" + truckType + '\'' +
                ", code='" + code + '\'' +
                ", Id='" + Id + '\'' +
                '}';
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
