package in.futuretrucks.response;//

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckRegApiResponse{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Data")
    @Expose
    private Data Data;

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
     * The Data
     */
    public Data getData() {
        return Data;
    }

    /**
     *
     * @param Data
     * The Data
     */
    public void setData(Data Data) {
        this.Data = Data;
    }

}