package in.futuretrucks.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserId {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("__v")
    @Expose
    private Integer V;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("company_log")
    @Expose
    private String companyLog;
    @SerializedName("addr_proof")
    @Expose
    private String addrProof;
    @SerializedName("pan_card_proof")
    @Expose
    private String panCardProof;
    @SerializedName("id_proof")
    @Expose
    private String idProof;
    @SerializedName("service_tax_proof")
    @Expose
    private String serviceTaxProof;
    @SerializedName("tin_number_proof")
    @Expose
    private String tinNumberProof;
    @SerializedName("saved_address")
    @Expose
    private List<Object> savedAddress = new ArrayList<Object>();
    @SerializedName("tnc_accepted")
    @Expose
    private Boolean tncAccepted;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("company_name")
    @Expose
    private String companyName;

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
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(String mobile) {
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

}