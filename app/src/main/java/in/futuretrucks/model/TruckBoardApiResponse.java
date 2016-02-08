package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by futuretrucks on 28/12/15.
 */
public class TruckBoardApiResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<TruckBoard> data = new ArrayList<TruckBoard>();
    @SerializedName("no_of_pages")
    @Expose
    private Integer noOfPages;

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
     * The data
     */
    public List<TruckBoard> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<TruckBoard> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The noOfPages
     */
    public Integer getNoOfPages() {
        return noOfPages;
    }

    /**
     *
     * @param noOfPages
     * The no_of_pages
     */
    public void setNoOfPages(Integer noOfPages) {
        this.noOfPages = noOfPages;
    }

    @Override
    public String toString() {
        return "TruckBoardApiResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", noOfPages=" + noOfPages +
                '}';
    }
}

