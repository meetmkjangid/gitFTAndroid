package in.futuretrucks.model;//

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

/**
 * Created by mahadev on 2/5/16.
 */
public class DriverRegistrationModel {

    @SerializedName("isNew")
    @Expose
    private Boolean isNew;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("address_line_1")
    @Expose
    private String addressLine1;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("pincode")
    @Expose
    private Long pincode;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("date_of_birth")
    @Expose
    private java.util.Date dateOfBirth;
    @SerializedName("dl_issue_date")
    @Expose
    private java.util.Date dlIssueDate;
    @SerializedName("dl_expiry_date")
    @Expose
    private java.util.Date dlExpiryDate;

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
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The mobile
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The licenseNo
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     *
     * @param licenseNo
     * The license_no
     */
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     *
     * @return
     * The addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     *
     * @param addressLine1
     * The address_line_1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     *
     * @return
     * The area
     */
    public String getArea() {
        return area;
    }

    /**
     *
     * @param area
     * The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The district
     */
    public String getDistrict() {
        return district;
    }

    /**
     *
     * @param district
     * The district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     *
     * @return
     * The pincode
     */
    public Long getPincode() {
        return pincode;
    }

    /**
     *
     * @param pincode
     * The pincode
     */
    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    /**
     *
     * @return
     * The fatherName
     */
    public String getFatherName() {
        return fatherName;
    }

    /**
     *
     * @param fatherName
     * The father_name
     */
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    /**
     *
     * @return
     * The dateOfBirth
     */
    public java.util.Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth
     * The date_of_birth
     */
    public void setDateOfBirth(java.util.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return
     * The dlIssueDate
     */
    public java.util.Date getDlIssueDate() {
        return dlIssueDate;
    }

    /**
     *
     * @param dlIssueDate
     * The dl_issue_date
     */
    public void setDlIssueDate(java.util.Date dlIssueDate) {
        this.dlIssueDate = dlIssueDate;
    }

    /**
     *
     * @return
     * The dlExpiryDate
     */
    public java.util.Date getDlExpiryDate() {
        return dlExpiryDate;
    }

    /**
     *
     * @param dlExpiryDate
     * The dl_expiry_date
     */
    public void setDlExpiryDate(java.util.Date dlExpiryDate) {
        this.dlExpiryDate = dlExpiryDate;
    }

    @Override
    public String toString() {
        return "DriverRegistrationModel{" +
                "isNew=" + isNew +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile=" + mobile +
                ", licenseNo='" + licenseNo + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", pincode=" + pincode +
                ", fatherName='" + fatherName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dlIssueDate=" + dlIssueDate +
                ", dlExpiryDate=" + dlExpiryDate +
                '}';
    }
}
