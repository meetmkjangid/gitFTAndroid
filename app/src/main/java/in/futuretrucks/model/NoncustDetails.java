package in.futuretrucks.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class NoncustDetails {

    @SerializedName("daily_serv_cities")
    @Expose
    private List<DailyServCity> dailyServCities = new ArrayList<DailyServCity>();
    @SerializedName("branch_cities")
    @Expose
    private List<BranchCity> branchCities = new ArrayList<BranchCity>();
    @SerializedName("interested_cities")
    @Expose
    private List<InterestedCity> interestedCities = new ArrayList<InterestedCity>();
    @SerializedName("vehicle_type")
    @Expose
    private List<VehicleType> vehicleType = new ArrayList<VehicleType>();

    /**
     * No args constructor for use in serialization
     *
     */
    public NoncustDetails() {
    }

    /**
     *
     * @param interestedCities
     * @param vehicleType
     * @param branchCities
     * @param dailyServCities
     */
    public NoncustDetails(List<DailyServCity> dailyServCities, List<BranchCity> branchCities, List<InterestedCity> interestedCities, List<VehicleType> vehicleType) {
        this.dailyServCities = dailyServCities;
        this.branchCities = branchCities;
        this.interestedCities = interestedCities;
        this.vehicleType = vehicleType;
    }

    /**
     *
     * @return
     * The dailyServCities
     */
    public List<DailyServCity> getDailyServCities() {
        return dailyServCities;
    }

    /**
     *
     * @param dailyServCities
     * The daily_serv_cities
     */
    public void setDailyServCities(List<DailyServCity> dailyServCities) {
        this.dailyServCities = dailyServCities;
    }

    /**
     *
     * @return
     * The branchCities
     */
    public List<BranchCity> getBranchCities() {
        return branchCities;
    }

    /**
     *
     * @param branchCities
     * The branch_cities
     */
    public void setBranchCities(List<BranchCity> branchCities) {
        this.branchCities = branchCities;
    }

    /**
     *
     * @return
     * The interestedCities
     */
    public List<InterestedCity> getInterestedCities() {
        return interestedCities;
    }

    /**
     *
     * @param interestedCities
     * The interested_cities
     */
    public void setInterestedCities(List<InterestedCity> interestedCities) {
        this.interestedCities = interestedCities;
    }

    /**
     *
     * @return
     * The vehicleType
     */
    public List<VehicleType> getVehicleType() {
        return vehicleType;
    }

    /**
     *
     * @param vehicleType
     * The vehicle_type
     */
    public void setVehicleType(List<VehicleType> vehicleType) {
        this.vehicleType = vehicleType;
    }

}