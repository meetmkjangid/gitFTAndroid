package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TruckBoard {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("radius")
    @Expose
    private Integer radius;
    @SerializedName("no_of_trucks")
    @Expose
    private Integer noOfTrucks;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("expected_price")
    @Expose
    private Integer expectedPrice;
    @SerializedName("postingId")
    @Expose
    private String postingId;
    @SerializedName("postingDate")
    @Expose
    private String postingDate;
    @SerializedName("__v")
    @Expose
    private String V;
    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("booking_id")
    @Expose
    private String booking_id;
    @SerializedName("latModified")
    @Expose
    private String latModified;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("contact_person_payment_at_pickupLocation")
    @Expose
    private ContactPersonPaymentAtPickupLocation contactPersonPaymentAtPickupLocation;
    @SerializedName("contact_person_payment_at_dropofLocation")
    @Expose
    private ContactPersonPaymentAtDropofLocation contactPersonPaymentAtDropofLocation;
    @SerializedName("invoice_posting")
    @Expose
    private InvoicePosting invoicePosting;
    @SerializedName("acceptedBy_posting")
    @Expose
    private Boolean acceptedByPosting;
    @SerializedName("payment_posting")
    @Expose
    private PaymentPosting paymentPosting;
    @SerializedName("quote_prices")
    @Expose
    private List<Object> quotePrices = new ArrayList<Object>();
    @SerializedName("refrigeration")
    @Expose
    private Boolean refrigeration;
    @SerializedName("load_type")
    @Expose
    private String loadType;
    @SerializedName("materialType")
    @Expose
    private MaterialType materialType;
    @SerializedName("confirmBy_posting")
    @Expose
    private Boolean confirmByPosting;
    @SerializedName("accepted_interest")
    @Expose
    private AcceptedInterest acceptedInterest;
    @SerializedName("interested_to_backup")
    @Expose
    private List<Object> interestedToBackup = new ArrayList<Object>();
    @SerializedName("interested_to")
    @Expose
    private List<Object> interestedTo = new ArrayList<Object>();
    @SerializedName("date")
    @Expose
    private in.futuretrucks.model.Date date;
    @SerializedName("destination")
    @Expose
    private List<Destination> destination = new ArrayList<Destination>();
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("drivers")
    @Expose
    private List<Object> drivers = new ArrayList<Object>();
    @SerializedName("trucks")
    @Expose
    private List<Object> trucks = new ArrayList<Object>();
    @SerializedName("verified")
    @Expose
    private String verified;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("entityName")
    @Expose
    private String entityName;
    @SerializedName("pickupLocation")
    @Expose
    private PickupLocation pickupLocation;
    @SerializedName("dropofLocation")
    @Expose
    private DropofLocation dropofLocation;

    @SerializedName("post_owner_name")
    @Expose
    private String postOwnerName;

    @SerializedName("post_owner_company")
    @Expose
    private String postOwnerCompany;

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
     * The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     * The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    /**
     *
     * @return
     * The postingId
     */
    public String getPostingId() {
        return postingId;
    }

    /**
     *
     * @param postingId
     * The postingId
     */
    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }

    /**
     *
     * @return
     * The postingDate
     */
    public String getPostingDate() {
        return postingDate;
    }

    /**
     *
     * @param postingDate
     * The postingDate
     */
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    /**
     *
     * @return
     * The V
     */
    public String getV() {
        return V;
    }

    /**
     *
     * @param V
     * The __v
     */
    public void setV(String V) {
        this.V = V;
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
     * The contactPersonPaymentAtPickupLocation
     */

    public void setPostOwnerName(String postOwnerName) {
        this.postOwnerName = postOwnerName;
    }

    public void setPostOwnerCompany(String postOwnerCompany) {
        this.postOwnerCompany = postOwnerCompany;
    }

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public String getPostOwnerCompany() {
        return postOwnerCompany;
    }

    public ContactPersonPaymentAtPickupLocation getContactPersonPaymentAtPickupLocation() {
        return contactPersonPaymentAtPickupLocation;
    }

    /**
     *
     * @param contactPersonPaymentAtPickupLocation
     * The contact_person_payment_at_pickupLocation
     */
    public void setContactPersonPaymentAtPickupLocation(ContactPersonPaymentAtPickupLocation contactPersonPaymentAtPickupLocation) {
        this.contactPersonPaymentAtPickupLocation = contactPersonPaymentAtPickupLocation;
    }

    /**
     *
     * @return
     * The contactPersonPaymentAtDropofLocation
     */
    public ContactPersonPaymentAtDropofLocation getContactPersonPaymentAtDropofLocation() {
        return contactPersonPaymentAtDropofLocation;
    }

    /**
     *
     * @param contactPersonPaymentAtDropofLocation
     * The contact_person_payment_at_dropofLocation
     */
    public void setContactPersonPaymentAtDropofLocation(ContactPersonPaymentAtDropofLocation contactPersonPaymentAtDropofLocation) {
        this.contactPersonPaymentAtDropofLocation = contactPersonPaymentAtDropofLocation;
    }

    /**
     *
     * @return
     * The invoicePosting
     */
    public InvoicePosting getInvoicePosting() {
        return invoicePosting;
    }

    /**
     *
     * @param invoicePosting
     * The invoice_posting
     */
    public void setInvoicePosting(InvoicePosting invoicePosting) {
        this.invoicePosting = invoicePosting;
    }

    /**
     *
     * @return
     * The acceptedByPosting
     */
    public Boolean getAcceptedByPosting() {
        return acceptedByPosting;
    }

    /**
     *
     * @param acceptedByPosting
     * The acceptedBy_posting
     */
    public void setAcceptedByPosting(Boolean acceptedByPosting) {
        this.acceptedByPosting = acceptedByPosting;
    }

    /**
     *
     * @return
     * The paymentPosting
     */
    public PaymentPosting getPaymentPosting() {
        return paymentPosting;
    }

    /**
     *
     * @param paymentPosting
     * The payment_posting
     */
    public void setPaymentPosting(PaymentPosting paymentPosting) {
        this.paymentPosting = paymentPosting;
    }

    /**
     *
     * @return
     * The quotePrices
     */
    public List<Object> getQuotePrices() {
        return quotePrices;
    }

    /**
     *
     * @param quotePrices
     * The quote_prices
     */
    public void setQuotePrices(List<Object> quotePrices) {
        this.quotePrices = quotePrices;
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
     * The loadType
     */
    public String getLoadType() {
        return loadType;
    }

    /**
     *
     * @param loadType
     * The load_type
     */
    public void setLoadType(String loadType) {
        this.loadType = loadType;
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
     * The confirmByPosting
     */
    public Boolean getConfirmByPosting() {
        return confirmByPosting;
    }

    /**
     *
     * @param confirmByPosting
     * The confirmBy_posting
     */
    public void setConfirmByPosting(Boolean confirmByPosting) {
        this.confirmByPosting = confirmByPosting;
    }

    /**
     *
     * @return
     * The acceptedInterest
     */
    public AcceptedInterest getAcceptedInterest() {
        return acceptedInterest;
    }

    /**
     *
     * @param acceptedInterest
     * The accepted_interest
     */
    public void setAcceptedInterest(AcceptedInterest acceptedInterest) {
        this.acceptedInterest = acceptedInterest;
    }

    /**
     *
     * @return
     * The interestedToBackup
     */
    public List<Object> getInterestedToBackup() {
        return interestedToBackup;
    }

    /**
     *
     * @param interestedToBackup
     * The interested_to_backup
     */
    public void setInterestedToBackup(List<Object> interestedToBackup) {
        this.interestedToBackup = interestedToBackup;
    }

    /**
     *
     * @return
     * The interestedTo
     */
    public List<Object> getInterestedTo() {
        return interestedTo;
    }

    /**
     *
     * @param interestedTo
     * The interested_to
     */
    public void setInterestedTo(List<Object> interestedTo) {
        this.interestedTo = interestedTo;
    }

    /**
     *
     * @return
     * The date
     */
    public in.futuretrucks.model.Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(in.futuretrucks.model.Date date) {
        this.date = date;
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
     * The drivers
     */
    public List<Object> getDrivers() {
        return drivers;
    }

    /**
     *
     * @param drivers
     * The drivers
     */
    public void setDrivers(List<Object> drivers) {
        this.drivers = drivers;
    }

    /**
     *
     * @return
     * The trucks
     */
    public List<Object> getTrucks() {
        return trucks;
    }

    /**
     *
     * @param trucks
     * The trucks
     */
    public void setTrucks(List<Object> trucks) {
        this.trucks = trucks;
    }

    /**
     *
     * @return
     * The verified
     */
    public String getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     * The verified
     */
    public void setVerified(String verified) {
        this.verified = verified;
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
     * The entityName
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     *
     * @param entityName
     * The entityName
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

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

    @Override
    public String toString() {
        return "LoadBoard{" +
                "Id='" + Id + '\'' +
                ", radius=" + radius +
                ", noOfTrucks=" + noOfTrucks +
                ", weight=" + weight +
                ", expectedPrice=" + expectedPrice +
                ", postingId='" + postingId + '\'' +
                ", postingDate='" + postingDate + '\'' +
                ", V=" + V +
                ", bookingId='" + bookingId + '\'' +
                ", booking_id='" + booking_id + '\'' +
                ", latModified='" + latModified + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", contactPersonPaymentAtPickupLocation=" + contactPersonPaymentAtPickupLocation +
                ", contactPersonPaymentAtDropofLocation=" + contactPersonPaymentAtDropofLocation +
                ", invoicePosting=" + invoicePosting +
                ", acceptedByPosting=" + acceptedByPosting +
                ", paymentPosting=" + paymentPosting +
                ", quotePrices=" + quotePrices +
                ", refrigeration=" + refrigeration +
                ", loadType='" + loadType + '\'' +
                ", materialType=" + materialType +
                ", confirmByPosting=" + confirmByPosting +
                ", acceptedInterest=" + acceptedInterest +
                ", interestedToBackup=" + interestedToBackup +
                ", interestedTo=" + interestedTo +
                ", date=" + date +
                ", destination=" + destination +
                ", source=" + source +
                ", drivers=" + drivers +
                ", trucks=" + trucks +
                ", verified='" + verified + '\'' +
                ", type='" + type + '\'' +
                ", entityName='" + entityName + '\'' +
                ", pickupLocation=" + pickupLocation +
                ", dropofLocation=" + dropofLocation +
                '}';
    }

    /**
     *
     * @param dropofLocation
     * The dropofLocation
     */
    public void setDropofLocation(DropofLocation dropofLocation) {
        this.dropofLocation = dropofLocation;
    }


}