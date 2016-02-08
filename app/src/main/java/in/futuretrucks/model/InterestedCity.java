package in.futuretrucks.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class InterestedCity {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("city")
    @Expose
    private City city;

    /**
     * No args constructor for use in serialization
     *
     */
    public InterestedCity() {
    }

    /**
     *
     * @param Id
     * @param city
     */
    public InterestedCity(String Id, City city) {
        this.Id = Id;
        this.city = city;
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

}