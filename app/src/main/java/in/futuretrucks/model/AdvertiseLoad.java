package in.futuretrucks.model;

/**
 * Created by futuretrucks on 5/2/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class AdvertiseLoad {

    @SerializedName("pickupLocation")
    @Expose
    private PickupLocation pickupLocation;
    @SerializedName("dropofLocation")
    @Expose
    private DropofLocation dropofLocation;
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("destination")
    @Expose
    private List<Destination> destination = new ArrayList<Destination>();
    @SerializedName("radius")
    @Expose
    private Integer radius;
    @SerializedName("date")
    @Expose
    private in.futuretrucks.model.DatePosting date;
    @SerializedName("materialType")
    @Expose
    private MaterialType materialType;
    @SerializedName("vehicleType")
    @Expose
    private VehicleType vehicleType;
    @SerializedName("no_of_trucks")
    @Expose
    private Integer noOfTrucks;
    @SerializedName("expected_price")
    @Expose
    private Integer expectedPrice;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("offer")
    @Expose
    private String offer;

    /**
     *
     * @return
     * The pickupLocation
     */
    public PickupLocation getPickupLocation() {
        return pickupLocation;
    }

    /**
     *
     * @param pickupLocation
     * The pickupLocation
     */
    public void setPickupLocation(PickupLocation pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    /**
     *
     * @return
     * The dropofLocation
     */
    public DropofLocation getDropofLocation() {
        return dropofLocation;
    }

    /**
     *
     * @param dropofLocation
     * The dropofLocation
     */
    public void setDropofLocation(DropofLocation dropofLocation) {
        this.dropofLocation = dropofLocation;
    }

    /**
     *
     * @return
     * The source
     */
    public Source getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The destination
     */
    public List<Destination> getDestination() {
        return destination;
    }

    /**
     *
     * @param destination
     * The destination
     */
    public void setDestination(List<Destination> destination) {
        this.destination = destination;
    }

    /**
     *
     * @return
     * The radius
     */
    public Integer getRadius() {
        return radius;
    }

    /**
     *
     * @param radius
     * The radius
     */
    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    /**
     *
     * @return
     * The date
     */
    public in.futuretrucks.model.DatePosting getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(in.futuretrucks.model.DatePosting date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The materialType
     */
    public MaterialType getMaterialType() {
        return materialType;
    }

    /**
     *
     * @param materialType
     * The materialType
     */
    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    /**
     *
     * @return
     * The vehicleType
     */
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    /**
     *
     * @param vehicleType
     * The vehicleType
     */
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     *
     * @return
     * The noOfTrucks
     */
    public Integer getNoOfTrucks() {
        return noOfTrucks;
    }

    /**
     *
     * @param noOfTrucks
     * The no_of_trucks
     */
    public void setNoOfTrucks(Integer noOfTrucks) {
        this.noOfTrucks = noOfTrucks;
    }

    /**
     *
     * @return
     * The expectedPrice
     */
    public Integer getExpectedPrice() {
        return expectedPrice;
    }

    /**
     *
     * @param expectedPrice
     * The expected_price
     */
    public void setExpectedPrice(Integer expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    /**
     *
     * @return
     * The weight
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     * The weight
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The offer
     */
    public String getOffer() {
        return offer;
    }

    /**
     *
     * @param offer
     * The offer
     */
    public void setOffer(String offer) {
        this.offer = offer;
    }

}