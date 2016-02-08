package in.futuretrucks.model;

/**
 * Created by mahadev on 2/5/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class TruckRegisterModel {

    @SerializedName("isNew")
    @Expose
    private Boolean isNew;
    @SerializedName("truckType")
    @Expose
    private TruckType truckType;
    @SerializedName("body_type")
    @Expose
    private String bodyType;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("make_year")
    @Expose
    private String makeYear;
    @SerializedName("capacity")
    @Expose
    private double capacity;
    @SerializedName("Axel")
    @Expose
    private String Axel;
    @SerializedName("manufecturer")
    @Expose
    private String manufecturer;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("license_plate_num")
    @Expose
    private String licensePlateNum;
    @SerializedName("truck_model")
    @Expose
    private String truckModel;
    @SerializedName("height")
    @Expose
    private double height;
    @SerializedName("width")
    @Expose
    private double width;
    @SerializedName("refrigeration")
    @Expose
    private Boolean refrigeration;
    @SerializedName("length")
    @Expose
    private double length;
    @SerializedName("natioal_permit")
    @Expose
    private Boolean natioalPermit;
    @SerializedName("permit_expiry_date")
    @Expose
    private java.util.Date permitExpiryDate;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;

    @SerializedName("drivers_name")
    @Expose
    private String drivers_name;

    @SerializedName("owner_mobile")
    @Expose
    private Long ownerMobile;

    /**
     *
     * @return
     * The isNew
     */
    public Boolean getIsNew() {
        return isNew;
    }

    /**
     *
     * @param isNew
     * The isNew
     */
    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
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
     * The Axel
     */
    public String getAxel() {
        return Axel;
    }

    /**
     *
     * @param Axel
     * The Axel
     */
    public void setAxel(String Axel) {
        this.Axel = Axel;
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
    public double getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The width
     */
    public double getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(double width) {
        this.width = width;
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
     * The length
     */
    public double getLength() {
        return length;
    }

    /**
     *
     * @param length
     * The length
     */
    public void setLength(double length) {
        this.length = length;
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
     * The permitExpiryDate
     */
    public java.util.Date getPermitExpiryDate() {
        return permitExpiryDate;
    }

    /**
     *
     * @param permitExpiryDate
     * The permit_expiry_date
     */
    public void setPermitExpiryDate(java.util.Date permitExpiryDate) {
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


    public String getDriverName() {
        return drivers_name;
    }

    /**
     *
     * @param drivername
     * The owner_name
     */
    public void setDriverName(String drivername) {
        this.drivers_name = drivername;
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

}
