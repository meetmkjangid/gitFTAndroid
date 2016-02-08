package in.futuretrucks.response;

/**
 * Created by mahadev on 2/5/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class EngineNo {

    @SerializedName("Comment")
    @Expose
    private Object Comment;
    @SerializedName("Status")
    @Expose
    private String Status;

    /**
     *
     * @return
     * The Comment
     */
    public Object getComment() {
        return Comment;
    }

    /**
     *
     * @param Comment
     * The Comment
     */
    public void setComment(Object Comment) {
        this.Comment = Comment;
    }

    /**
     *
     * @return
     * The Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     *
     * @param Status
     * The Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

}
