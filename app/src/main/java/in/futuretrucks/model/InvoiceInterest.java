package in.futuretrucks.model;

/**
 * Created by futuretrucks on 28/12/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InvoiceInterest {

    @SerializedName("items")
    @Expose
    private List<Object> items = new ArrayList<Object>();

    /**
     *
     * @return
     * The items
     */
    public List<Object> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    public void setItems(List<Object> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "InvoiceInterest{" +
                "items=" + items +
                '}';
    }
}