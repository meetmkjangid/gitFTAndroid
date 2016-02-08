package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver{

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("address_line_1")
    @Expose
    private String addressLine1;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("dl_expiry_date")
    @Expose
    private String dlExpiryDate;
    @SerializedName("dl_issue_date")
    @Expose
    private String dlIssueDate;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("__v")
    @Expose
    private Integer V;
    @SerializedName("percent_profile")
    @Expose
    private Integer percentProfile;
    @SerializedName("latModified")
    @Expose
    private String latModified;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("isAddrNotReq")
    @Expose
    private Boolean isAddrNotReq;
    @SerializedName("police_verification_doc")
    @Expose
    private String policeVerificationDoc;
    @SerializedName("address_proof_doc")
    @Expose
    private String addressProofDoc;
    @SerializedName("dr_licence_doc")
    @Expose
    private String drLicenceDoc;
    @SerializedName("driver_image")
    @Expose
    private String driverImage;

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
     * The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth
     * The date_of_birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
     * The dlExpiryDate
     */
    public String getDlExpiryDate() {
        return dlExpiryDate;
    }

    /**
     *
     * @param dlExpiryDate
     * The dl_expiry_date
     */
    public void setDlExpiryDate(String dlExpiryDate) {
        this.dlExpiryDate = dlExpiryDate;
    }

    /**
     *
     * @return
     * The dlIssueDate
     */
    public String getDlIssueDate() {
        return dlIssueDate;
    }

    /**
     *
     * @param dlIssueDate
     * The dl_issue_date
     */
    public void setDlIssueDate(String dlIssueDate) {
        this.dlIssueDate = dlIssueDate;
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
     * The pincode
     */
    public Integer getPincode() {
        return pincode;
    }

    /**
     *
     * @param pincode
     * The pincode
     */
    public void setPincode(Integer pincode) {
        this.pincode = pincode;
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
     * The isAddrNotReq
     */
    public Boolean getIsAddrNotReq() {
        return isAddrNotReq;
    }

    /**
     *
     * @param isAddrNotReq
     * The isAddrNotReq
     */
    public void setIsAddrNotReq(Boolean isAddrNotReq) {
        this.isAddrNotReq = isAddrNotReq;
    }

    /**
     *
     * @return
     * The policeVerificationDoc
     */
    public String getPoliceVerificationDoc() {
        return policeVerificationDoc;
    }

    /**
     *
     * @param policeVerificationDoc
     * The police_verification_doc
     */
    public void setPoliceVerificationDoc(String policeVerificationDoc) {
        this.policeVerificationDoc = policeVerificationDoc;
    }

    /**
     *
     * @return
     * The addressProofDoc
     */
    public String getAddressProofDoc() {
        return addressProofDoc;
    }

    /**
     *
     * @param addressProofDoc
     * The address_proof_doc
     */
    public void setAddressProofDoc(String addressProofDoc) {
        this.addressProofDoc = addressProofDoc;
    }

    /**
     *
     * @return
     * The drLicenceDoc
     */
    public String getDrLicenceDoc() {
        return drLicenceDoc;
    }

    /**
     *
     * @param drLicenceDoc
     * The dr_licence_doc
     */
    public void setDrLicenceDoc(String drLicenceDoc) {
        this.drLicenceDoc = drLicenceDoc;
    }

    /**
     *
     * @return
     * The driverImage
     */
    public String getDriverImage() {
        return driverImage;
    }

    /**
     *
     * @param driverImage
     * The driver_image
     */
    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

}
