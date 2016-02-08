package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by futuretrucks on 2/2/16.
 */
public class AdvertiseTruckApiResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("postingId")
        @Expose
        private String postingId;
        @SerializedName("message")
        @Expose
        private String message;

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
         * The message
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         * The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }


