package in.futuretrucks.model;

/**
 * Created by futuretrucks on 26/12/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Source {

    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("d")
    @Expose
    private String d;
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("p")
    @Expose
    private String p;
    @SerializedName("address_components")
    @Expose
    private AddressComponents addressComponents;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("types")
    @Expose
    private List<String> types = new ArrayList<String>();
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    /**
     *
     * @return
     * The c
     */
    public String getC() {
        return c;
    }

    /**
     *
     * @param c
     * The c
     */
    public void setC(String c) {
        this.c = c;
    }

    /**
     *
     * @return
     * The d
     */
    public String getD() {
        return d;
    }

    /**
     *
     * @param d
     * The d
     */
    public void setD(String d) {
        this.d = d;
    }

    /**
     *
     * @return
     * The s
     */
    public String getS() {
        return s;
    }

    /**
     *
     * @param s
     * The s
     */
    public void setS(String s) {
        this.s = s;
    }

    /**
     *
     * @return
     * The p
     */
    public String getP() {
        return p;
    }

    /**
     *
     * @param p
     * The p
     */
    public void setP(String p) {
        this.p = p;
    }

    /**
     *
     * @return
     * The addressComponents
     */
    public AddressComponents getAddressComponents() {
        return addressComponents;
    }

    /**
     *
     * @param addressComponents
     * The address_components
     */
    public void setAddressComponents(AddressComponents addressComponents) {
        this.addressComponents = addressComponents;
    }

    /**
     *
     * @return
     * The formattedAddress
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     *
     * @param formattedAddress
     * The formatted_address
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     *
     * @return
     * The geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     *
     * @param geometry
     * The geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     *
     * @return
     * The placeId
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     *
     * @param placeId
     * The place_id
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     *
     * @return
     * The types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     *
     * @param types
     * The types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The vicinity
     */
    public String getVicinity() {
        return vicinity;
    }

    /**
     *
     * @param vicinity
     * The vicinity
     */
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @Override
    public String toString() {
        return "Source{" +
                "c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", s='" + s + '\'' +
                ", p='" + p + '\'' +
                ", addressComponents=" + addressComponents +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", geometry=" + geometry +
                ", placeId='" + placeId + '\'' +
                ", types=" + types +
                ", url='" + url + '\'' +
                ", vicinity='" + vicinity + '\'' +
                '}';
    }
}