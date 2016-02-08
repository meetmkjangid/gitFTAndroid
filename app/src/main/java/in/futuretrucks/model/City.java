package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class City {

    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("d")
    @Expose
    private String d;
    @SerializedName("p")
    @Expose
    private Integer p;
    @SerializedName("c")
    @Expose
    private String c;

    /**
     * No args constructor for use in serialization
     *
     */
    public City() {
    }

    /**
     *
     * @param d
     * @param s
     * @param c
     * @param p
     */
    public City(String s, String d, Integer p, String c) {
        this.s = s;
        this.d = d;
        this.p = p;
        this.c = c;
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
     * The p
     */
    public Integer getP() {
        return p;
    }

    /**
     *
     * @param p
     * The p
     */
    public void setP(Integer p) {
        this.p = p;
    }

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

}