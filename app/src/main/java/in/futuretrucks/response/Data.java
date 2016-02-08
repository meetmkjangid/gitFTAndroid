package in.futuretrucks.response;

/**
 * Created by mahadev on 2/5/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.futuretrucks.model.TruckType;

public class Data {

    @SerializedName("__v")
    @Expose
    private Integer V;
    @SerializedName("make_year")
    @Expose
    private String makeYear;
    @SerializedName("capacity")
    @Expose
    private Double capacity;
    @SerializedName("manufecturer")
    @Expose
    private String manufecturer;
    @SerializedName("license_plate_num")
    @Expose
    private String licensePlateNum;
    @SerializedName("truck_model")
    @Expose
    private String truckModel;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("width")
    @Expose
    private Double width;
    @SerializedName("length")
    @Expose
    private Double length;
    @SerializedName("permit_expiry_date")
    @Expose
    private String permitExpiryDate;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("owner_mobile")
    @Expose
    private Long ownerMobile;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("percent_profile")
    @Expose
    private Integer percentProfile;
    @SerializedName("latModified")
    @Expose
    private String latModified;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("natioal_permit")
    @Expose
    private Boolean natioalPermit;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("multi_axle")
    @Expose
    private String multiAxle;
    @SerializedName("refrigeration")
    @Expose
    private Boolean refrigeration;
    @SerializedName("reqd_services")
    @Expose
    private String reqdServices;
    @SerializedName("body_type")
    @Expose
    private String bodyType;
    @SerializedName("truckType")
    @Expose
    private TruckType truckType;

    /**
     *
     * @return
     * The V
     */
    public Integer getV() {
        return V;
    }

    /**
     *
     * @param V
     * The __v
     */
    public void setV(Integer V) {
        this.V = V;
    }

    /**
     *
     * @return
     * The makeYear
     */
    public String getMakeYear() {
        return makeYear;
    }

    /**
     *
     * @param makeYear
     * The make_year
     */
    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }

    /**
     *
     * @return
     * The capacity
     */
    public Double getCapacity() {
        return capacity;
    }

    /**
     *
     * @param capacity
     * The capacity
     */
    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @return
     * The manufecturer
     */
    public String getManufecturer() {
        return manufecturer;
    }

    /**
     *
     * @param manufecturer
     * The manufecturer
     */
    public void setManufecturer(String manufecturer) {
        this.manufecturer = manufecturer;
    }

    /**
     *
     * @return
     * The licensePlateNum
     */
    public String getLicensePlateNum() {
        return licensePlateNum;
    }

    /**
     *
     * @param licensePlateNum
     * The license_plate_num
     */
    public void setLicensePlateNum(String licensePlateNum) {
        this.licensePlateNum = licensePlateNum;
    }

    /**
     *
     * @return
     * The truckModel
     */
    public String getTruckModel() {
        return truckModel;
    }

    /**
     *
     * @param truckModel
     * The truck_model
     */
    public void setTruckModel(String truckModel) {
        this.truckModel = truckModel;
    }

    /**
     *
     * @return
     * The height
     */
    public Double getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The width
     */
    public Double getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    /**
     *
     * @return
     * The length
     */
    public Double getLength() {
        return length;
    }

    /**
     *
     * @param length
     * The length
     */
    public void setLength(Double length) {
        this.length = length;
    }

    /**
     *
     * @return
     * The permitExpiryDate
     */
    public String getPermitExpiryDate() {
        return permitExpiryDate;
    }

    /**
     *
     * @param permitExpiryDate
     * The permit_expiry_date
     */
    public void setPermitExpiryDate(String permitExpiryDate) {
        this.permitExpiryDate = permitExpiryDate;
    }

    /**
     *
     * @return
     * The ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     *
     * @param ownerName
     * The owner_name
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     *
     * @return
     * The ownerMobile
     */
    public Long getOwnerMobile() {
        return ownerMobile;
    }

    /**
     *
     * @param ownerMobile
     * The owner_mobile
     */
    public void setOwnerMobile(Long ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

    /**
     *
     * @return
     * The percentProfile
     */
    public Integer getPercentProfile() {
        return percentProfile;
    }

    /**
     *
     * @param percentProfile
     * The percent_profile
     */
    public void setPercentProfile(Integer percentProfile) {
        this.percentProfile = percentProfile;
    }

    /**
     *
     * @return
     * The latModified
     */
    public String getLatModified() {
        return latModified;
    }

    /**
     *
     * @param latModified
     * The latModified
     */
    public void setLatModified(String latModified) {
        this.latModified = latModified;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The natioalPermit
     */
    public Boolean getNatioalPermit() {
        return natioalPermit;
    }

    /**
     *
     * @param natioalPermit
     * The natioal_permit
     */
    public void setNatioalPermit(Boolean natioalPermit) {
        this.natioalPermit = natioalPermit;
    }

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
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The multiAxle
     */
    public String getMultiAxle() {
        return multiAxle;
    }

    /**
     *
     * @param multiAxle
     * The multi_axle
     */
    public void setMultiAxle(String multiAxle) {
        this.multiAxle = multiAxle;
    }

    /**
     *
     * @return
     * The refrigeration
     */
    public Boolean getRefrigeration() {
        return refrigeration;
    }

    /**
     *
     * @param refrigeration
     * The refrigeration
     */
    public void setRefrigeration(Boolean refrigeration) {
        this.refrigeration = refrigeration;
    }

    /**
     *
     * @return
     * The reqdServices
     */
    public String getReqdServices() {
        return reqdServices;
    }

    /**
     *
     * @param reqdServices
     * The reqd_services
     */
    public void setReqdServices(String reqdServices) {
        this.reqdServices = reqdServices;
    }

    /**
     *
     * @return
     * The bodyType
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     *
     * @param bodyType
     * The body_type
     */
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    /**
     *
     * @return
     * The truckType
     */
    public TruckType getTruckType() {
        return truckType;
    }

    /**
     *
     * @param truckType
     * The truckType
     */
    public void setTruckType(TruckType truckType) {
        this.truckType = truckType;
    }

}
