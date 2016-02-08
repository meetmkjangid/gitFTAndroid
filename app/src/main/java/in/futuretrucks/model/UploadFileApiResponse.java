package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by futuretrucks on 3/1/16.
 */
public class UploadFileApiResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("user")
        @Expose
        private User user;

        /**
         *
         * @return
         * The status
         */
        public String getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         *
         * @return
         * The user
         */
        public User getUser() {
            return user;
        }

        /**
         *
         * @param user
         * The user
         */
        public void setUser(User user) {
            this.user = user;
        }

    @Override
    public String toString() {
        return "UploadFileApiResponse{" +
                "status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}

