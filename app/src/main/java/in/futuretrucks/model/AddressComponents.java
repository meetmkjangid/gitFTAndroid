package in.futuretrucks.model;

/**
 * Created by futuretrucks on 1/2/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddressComponents {

    @SerializedName("street_number")
    @Expose
    private String streetNumber;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("sublocality_level_2")
    @Expose
    private String sublocalityLevel2;
    @SerializedName("sublocality_level_1")
    @Expose
    private String sublocalityLevel1;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("administrative_area_level_2")
    @Expose
    private String administrativeAreaLevel2;
    @SerializedName("administrative_area_level_1")
    @Expose
    private String administrativeAreaLevel1;
    @SerializedName("administrative_area_level_1_f")
    @Expose
    private String administrativeAreaLevel1F;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_f")
    @Expose
    private String countryF;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;

    /**
     *
     * @return
     * The streetNumber
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     *
     * @param streetNumber
     * The street_number
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     *
     * @return
     * The route
     */
    public String getRoute() {
        return route;
    }

    /**
     *
     * @param route
     * The route
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     *
     * @return
     * The sublocalityLevel2
     */
    public String getSublocalityLevel2() {
        return sublocalityLevel2;
    }

    /**
     *
     * @param sublocalityLevel2
     * The sublocality_level_2
     */
    public void setSublocalityLevel2(String sublocalityLevel2) {
        this.sublocalityLevel2 = sublocalityLevel2;
    }

    /**
     *
     * @return
     * The sublocalityLevel1
     */
    public String getSublocalityLevel1() {
        return sublocalityLevel1;
    }

    /**
     *
     * @param sublocalityLevel1
     * The sublocality_level_1
     */
    public void setSublocalityLevel1(String sublocalityLevel1) {
        this.sublocalityLevel1 = sublocalityLevel1;
    }

    /**
     *
     * @return
     * The locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     *
     * @param locality
     * The locality
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     *
     * @return
     * The administrativeAreaLevel2
     */
    public String getAdministrativeAreaLevel2() {
        return administrativeAreaLevel2;
    }

    /**
     *
     * @param administrativeAreaLevel2
     * The administrative_area_level_2
     */
    public void setAdministrativeAreaLevel2(String administrativeAreaLevel2) {
        this.administrativeAreaLevel2 = administrativeAreaLevel2;
    }

    /**
     *
     * @return
     * The administrativeAreaLevel1
     */
    public String getAdministrativeAreaLevel1() {
        return administrativeAreaLevel1;
    }

    /**
     *
     * @param administrativeAreaLevel1
     * The administrative_area_level_1
     */
    public void setAdministrativeAreaLevel1(String administrativeAreaLevel1) {
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
    }

    /**
     *
     * @return
     * The administrativeAreaLevel1F
     */
    public String getAdministrativeAreaLevel1F() {
        return administrativeAreaLevel1F;
    }

    /**
     *
     * @param administrativeAreaLevel1F
     * The administrative_area_level_1_f
     */
    public void setAdministrativeAreaLevel1F(String administrativeAreaLevel1F) {
        this.administrativeAreaLevel1F = administrativeAreaLevel1F;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The countryF
     */
    public String getCountryF() {
        return countryF;
    }

    /**
     *
     * @param countryF
     * The country_f
     */
    public void setCountryF(String countryF) {
        this.countryF = countryF;
    }

    /**
     *
     * @return
     * The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     * The postal_code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "AddressComponents{" +
                "streetNumber='" + streetNumber + '\'' +
                ", route='" + route + '\'' +
                ", sublocalityLevel2='" + sublocalityLevel2 + '\'' +
                ", sublocalityLevel1='" + sublocalityLevel1 + '\'' +
                ", locality='" + locality + '\'' +
                ", administrativeAreaLevel2='" + administrativeAreaLevel2 + '\'' +
                ", administrativeAreaLevel1='" + administrativeAreaLevel1 + '\'' +
                ", administrativeAreaLevel1F='" + administrativeAreaLevel1F + '\'' +
                ", country='" + country + '\'' +
                ", countryF='" + countryF + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}