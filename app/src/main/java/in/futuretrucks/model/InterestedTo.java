package in.futuretrucks.model;

/**
 * Created by futuretrucks on 28/12/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InterestedTo {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("mobile")
    @Expose
    private Integer mobile;
    @SerializedName("expected_price")
    @Expose
    private Integer expectedPrice;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("interestId")
    @Expose
    private String interestId;
    @SerializedName("posted_UId")
    @Expose
    private String postedUId;
    @SerializedName("posting")
    @Expose
    private String posting;
    @SerializedName("posting_id")
    @Expose
    private String postingId;
    @SerializedName("deal_price_interest")
    @Expose
    private Integer dealPriceInterest;
    @SerializedName("deal_price_posting")
    @Expose
    private Integer dealPricePosting;
    @SerializedName("post_email")
    @Expose
    private String postEmail;
    @SerializedName("__v")
    @Expose
    private Integer V;
    @SerializedName("booking_Id")
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
    @SerializedName("pickupLocation")
    @Expose
    private PickupLocation pickupLocation;
    @SerializedName("dropofLocation")
    @Expose
    private DropofLocation dropofLocation;
    @SerializedName("contact_person_payment_at_pickupLocation")
    @Expose
    private ContactPersonPaymentAtPickupLocation contactPersonPaymentAtPickupLocation;
    @SerializedName("contact_person_payment_at_dropofLocation")
    @Expose
    private ContactPersonPaymentAtDropofLocation contactPersonPaymentAtDropofLocation;
    @SerializedName("invoice_interest")
    @Expose
    private InvoiceInterest invoiceInterest;
    @SerializedName("payment_interest")
    @Expose
    private PaymentInterest paymentInterest;
    @SerializedName("confirmBy_posting")
    @Expose
    private Boolean confirmByPosting;
    @SerializedName("booked_by_other")
    @Expose
    private Boolean bookedByOther;
    @SerializedName("confirmBy_interest")
    @Expose
    private Boolean confirmByInterest;
    @SerializedName("acceptedBy_interest")
    @Expose
    private Boolean acceptedByInterest;
    @SerializedName("acceptedBy_posting")
    @Expose
    private Boolean acceptedByPosting;
    @SerializedName("counter_quotes")
    @Expose
    private List<Integer> counterQuotes = new ArrayList<Integer>();
    @SerializedName("quote_prices")
    @Expose
    private List<Integer> quotePrices = new ArrayList<Integer>();
    @SerializedName("otp_verified")
    @Expose
    private Boolean otpVerified;
    @SerializedName("isRegisteredCustomer")
    @Expose
    private Boolean isRegisteredCustomer;
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
     * The mobile
     */
    public Integer getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
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
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * The interestId
     */
    public String getInterestId() {
        return interestId;
    }

    /**
     *
     * @param interestId
     * The interestId
     */
    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    /**
     *
     * @return
     * The postedUId
     */
    public String getPostedUId() {
        return postedUId;
    }

    /**
     *
     * @param postedUId
     * The posted_UId
     */
    public void setPostedUId(String postedUId) {
        this.postedUId = postedUId;
    }

    /**
     *
     * @return
     * The posting
     */
    public String getPosting() {
        return posting;
    }

    /**
     *
     * @param posting
     * The posting
     */
    public void setPosting(String posting) {
        this.posting = posting;
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
     * The posting_id
     */
    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }

    /**
     *
     * @return
     * The dealPriceInterest
     */
    public Integer getDealPriceInterest() {
        return dealPriceInterest;
    }

    /**
     *
     * @param dealPriceInterest
     * The deal_price_interest
     */
    public void setDealPriceInterest(Integer dealPriceInterest) {
        this.dealPriceInterest = dealPriceInterest;
    }

    /**
     *
     * @return
     * The dealPricePosting
     */
    public Integer getDealPricePosting() {
        return dealPricePosting;
    }

    /**
     *
     * @param dealPricePosting
     * The deal_price_posting
     */
    public void setDealPricePosting(Integer dealPricePosting) {
        this.dealPricePosting = dealPricePosting;
    }

    /**
     *
     * @return
     * The postEmail
     */
    public String getPostEmail() {
        return postEmail;
    }

    /**
     *
     * @param postEmail
     * The post_email
     */
    public void setPostEmail(String postEmail) {
        this.postEmail = postEmail;
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
     * The bookingId
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     *
     * @param bookingId
     * The booking_id
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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
     * The contactPersonPaymentAtPickupLocation
     */
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
     * The invoiceInterest
     */
    public InvoiceInterest getInvoiceInterest() {
        return invoiceInterest;
    }

    /**
     *
     * @param invoiceInterest
     * The invoice_interest
     */
    public void setInvoiceInterest(InvoiceInterest invoiceInterest) {
        this.invoiceInterest = invoiceInterest;
    }

    /**
     *
     * @return
     * The paymentInterest
     */
    public PaymentInterest getPaymentInterest() {
        return paymentInterest;
    }

    /**
     *
     * @param paymentInterest
     * The payment_interest
     */
    public void setPaymentInterest(PaymentInterest paymentInterest) {
        this.paymentInterest = paymentInterest;
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
     * The bookedByOther
     */
    public Boolean getBookedByOther() {
        return bookedByOther;
    }

    /**
     *
     * @param bookedByOther
     * The booked_by_other
     */
    public void setBookedByOther(Boolean bookedByOther) {
        this.bookedByOther = bookedByOther;
    }

    /**
     *
     * @return
     * The confirmByInterest
     */
    public Boolean getConfirmByInterest() {
        return confirmByInterest;
    }

    /**
     *
     * @param confirmByInterest
     * The confirmBy_interest
     */
    public void setConfirmByInterest(Boolean confirmByInterest) {
        this.confirmByInterest = confirmByInterest;
    }

    /**
     *
     * @return
     * The acceptedByInterest
     */
    public Boolean getAcceptedByInterest() {
        return acceptedByInterest;
    }

    /**
     *
     * @param acceptedByInterest
     * The acceptedBy_interest
     */
    public void setAcceptedByInterest(Boolean acceptedByInterest) {
        this.acceptedByInterest = acceptedByInterest;
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
     * The counterQuotes
     */
    public List<Integer> getCounterQuotes() {
        return counterQuotes;
    }

    /**
     *
     * @param counterQuotes
     * The counter_quotes
     */
    public void setCounterQuotes(List<Integer> counterQuotes) {
        this.counterQuotes = counterQuotes;
    }

    /**
     *
     * @return
     * The quotePrices
     */
    public List<Integer> getQuotePrices() {
        return quotePrices;
    }

    /**
     *
     * @param quotePrices
     * The quote_prices
     */
    public void setQuotePrices(List<Integer> quotePrices) {
        this.quotePrices = quotePrices;
    }

    /**
     *
     * @return
     * The otpVerified
     */
    public Boolean getOtpVerified() {
        return otpVerified;
    }

    /**
     *
     * @param otpVerified
     * The otp_verified
     */
    public void setOtpVerified(Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    /**
     *
     * @return
     * The isRegisteredCustomer
     */
    public Boolean getIsRegisteredCustomer() {
        return isRegisteredCustomer;
    }

    /**
     *
     * @param isRegisteredCustomer
     * The isRegisteredCustomer
     */
    public void setIsRegisteredCustomer(Boolean isRegisteredCustomer) {
        this.isRegisteredCustomer = isRegisteredCustomer;
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

    @Override
    public String toString() {
        return "InterestedTo{" +
                "Id='" + Id + '\'' +
                ", mobile=" + mobile +
                ", expectedPrice=" + expectedPrice +
                ", comment='" + comment + '\'' +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", interestId='" + interestId + '\'' +
                ", postedUId='" + postedUId + '\'' +
                ", posting='" + posting + '\'' +
                ", postingId='" + postingId + '\'' +
                ", dealPriceInterest=" + dealPriceInterest +
                ", dealPricePosting=" + dealPricePosting +
                ", postEmail='" + postEmail + '\'' +
                ", V=" + V +
                ", bookingId='" + bookingId + '\'' +
                ", booking_id='" + booking_id + '\'' +
                ", latModified='" + latModified + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", pickupLocation=" + pickupLocation +
                ", dropofLocation=" + dropofLocation +
                ", contactPersonPaymentAtPickupLocation=" + contactPersonPaymentAtPickupLocation +
                ", contactPersonPaymentAtDropofLocation=" + contactPersonPaymentAtDropofLocation +
                ", invoiceInterest=" + invoiceInterest +
                ", paymentInterest=" + paymentInterest +
                ", confirmByPosting=" + confirmByPosting +
                ", bookedByOther=" + bookedByOther +
                ", confirmByInterest=" + confirmByInterest +
                ", acceptedByInterest=" + acceptedByInterest +
                ", acceptedByPosting=" + acceptedByPosting +
                ", counterQuotes=" + counterQuotes +
                ", quotePrices=" + quotePrices +
                ", otpVerified=" + otpVerified +
                ", isRegisteredCustomer=" + isRegisteredCustomer +
                ", destination=" + destination +
                ", source=" + source +
                ", drivers=" + drivers +
                ", trucks=" + trucks +
                ", verified='" + verified + '\'' +
                ", type='" + type + '\'' +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}