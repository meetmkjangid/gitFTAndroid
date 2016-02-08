package in.futuretrucks.model;

/**
 * Created by futuretrucks on 5/2/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadVehicleFileApiResponse {

    @SerializedName("status")
    @Expose
    private String status;
    /*@SerializedName("truck")
    @Expose
    private Truck truck;*/

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
     * The truck
     */
    /*public Truck getTruck() {
        return truck;
    }*/

    /**
     *
     * @param truck
     * The truck
     */
    /*public void setTruck(Truck truck) {
        this.truck = truck;
    }*/

}