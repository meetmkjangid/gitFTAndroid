package in.futuretrucks.model;


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.futuretrucks.model.City;
import in.futuretrucks.model.Device;
import in.futuretrucks.model.NoncustDetails;

public class User{

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("__v")
    @Expose
    private Integer V;
    @SerializedName("devices")
    @Expose
    private List<Device> devices = new ArrayList<Device>();
    @SerializedName("percent_profile")
    @Expose
    private Integer percentProfile;
    @SerializedName("latModified")
    @Expose
    private String latModified;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("saved_address")
    @Expose
    private List<Object> savedAddress = new ArrayList<Object>();
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("tnc_accepted")
    @Expose
    private Boolean tncAccepted;
    @SerializedName("noncust_details")
    @Expose
    private NoncustDetails noncustDetails;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("company_name")
    @Expose
    private String companyName;

    @SerializedName("company_log")
    @Expose
    private String companyLog;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("id_proof")
    @Expose
    private String idProof;
    @SerializedName("pan_card_proof")
    @Expose
    private String panCardProof;
    @SerializedName("service_tax_proof")
    @Expose
    private String serviceTaxProof;
    @SerializedName("addr_proof")
    @Expose
    private String addrProof;
    @SerializedName("tin_number_proof")
    @Expose
    private String tinNumberProof;

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
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The panNo
     */
    public String getPanNo() {
        return panNo;
    }

    /**
     *
     * @param panNo
     * The pan_no
     */
    public void setPanNo(String panNo) {
        this.panNo = panNo;
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
     * The devices
     */
    public List<Device> getDevices() {
        return devices;
    }

    /**
     *
     * @param devices
     * The devices
     */
    public void setDevices(List<Device> devices) {
        this.devices = devices;
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
     * The savedAddress
     */
    public List<Object> getSavedAddress() {
        return savedAddress;
    }

    /**
     *
     * @param savedAddress
     * The saved_address
     */
    public void setSavedAddress(List<Object> savedAddress) {
        this.savedAddress = savedAddress;
    }

    /**
     *
     * @return
     * The role
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @return
     * The tncAccepted
     */
    public Boolean getTncAccepted() {
        return tncAccepted;
    }

    /**
     *
     * @param tncAccepted
     * The tnc_accepted
     */
    public void setTncAccepted(Boolean tncAccepted) {
        this.tncAccepted = tncAccepted;
    }

    /**
     *
     * @return
     * The noncustDetails
     */
    public NoncustDetails getNoncustDetails() {
        return noncustDetails;
    }

    /**
     *
     * @param noncustDetails
     * The noncust_details
     */
    public void setNoncustDetails(NoncustDetails noncustDetails) {
        this.noncustDetails = noncustDetails;
    }

    /**
     *
     * @return
     * The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(City city) {
        this.city = city;
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
     * The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName
     * The company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return
     * The companyLog
     */
    public String getCompanyLog() {
        return companyLog;
    }

    /**
     *
     * @param companyLog
     * The company_log
     */
    public void setCompanyLog(String companyLog) {
        this.companyLog = companyLog;
    }

    /**
     *
     * @return
     * The profileImg
     */
    public String getProfileImg() {
        return profileImg;
    }

    /**
     *
     * @param profileImg
     * The profile_img
     */
    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    /**
     *
     * @return
     * The idProof
     */
    public String getIdProof() {
        return idProof;
    }

    /**
     *
     * @param idProof
     * The id_proof
     */
    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    /**
     *
     * @return
     * The panCardProof
     */
    public String getPanCardProof() {
        return panCardProof;
    }

    /**
     *
     * @param panCardProof
     * The pan_card_proof
     */
    public void setPanCardProof(String panCardProof) {
        this.panCardProof = panCardProof;
    }

    /**
     *
     * @return
     * The serviceTaxProof
     */
    public String getServiceTaxProof() {
        return serviceTaxProof;
    }

    /**
     *
     * @param serviceTaxProof
     * The service_tax_proof
     */
    public void setServiceTaxProof(String serviceTaxProof) {
        this.serviceTaxProof = serviceTaxProof;
    }

    /**
     *
     * @return
     * The addrProof
     */
    public String getAddrProof() {
        return addrProof;
    }

    /**
     *
     * @param addrProof
     * The addr_proof
     */
    public void setAddrProof(String addrProof) {
        this.addrProof = addrProof;
    }

    /**
     *
     * @return
     * The tinNumberProof
     */
    public String getTinNumberProof() {
        return tinNumberProof;
    }

    /**
     *
     * @param tinNumberProof
     * The tin_number_proof
     */
    public void setTinNumberProof(String tinNumberProof) {
        this.tinNumberProof = tinNumberProof;
    }

}