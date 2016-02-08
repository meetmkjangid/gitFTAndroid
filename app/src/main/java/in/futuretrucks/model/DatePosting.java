package in.futuretrucks.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class DatePosting {

    @SerializedName("from")
    @Expose
    private java.util.Date from;
    @SerializedName("to")
    @Expose
    private java.util.Date to;

    /**
     *
     * @return
     * The from
     */
    public java.util.Date getFrom() {
        return from;
    }

    /**
     *
     * @param from
     * The from
     */
    public void setFrom(java.util.Date from) {
        this.from = from;
    }

    /**
     *
     * @return
     * The to
     */
    public java.util.Date getTo() {
        return to;
    }

    /**
     *
     * @param to
     * The to
     */
    public void setTo(java.util.Date to) {
        this.to = to;
    }

}