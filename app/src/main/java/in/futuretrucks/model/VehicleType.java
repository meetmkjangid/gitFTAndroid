package in.futuretrucks.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VehicleType {

    @SerializedName("truck_type")
    @Expose
    private String truckType;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("capacity")
    @Expose
    private String capacity;

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
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     *
     * @param capacity
     * The selected
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public VehicleType(String truckType, String code, String category, String capacity) {
        this.truckType = truckType;
        this.code = code;
        this.category = category;
        this.capacity = capacity;
    }
}