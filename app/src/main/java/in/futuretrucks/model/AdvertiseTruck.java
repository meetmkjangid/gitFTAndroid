package in.futuretrucks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by futuretrucks on 2/2/16.
 */
public class AdvertiseTruck {


        @SerializedName("date")
        @Expose
        private DatePosting date;
        @SerializedName("source")
        @Expose
        private Source source;
        @SerializedName("destination")
        @Expose
        private List<Destination> destination = new ArrayList<Destination>();
        @SerializedName("radius")
        @Expose
        private Integer radius;
        @SerializedName("vehicleType")
        @Expose
        private VehicleType vehicleType;
        @SerializedName("expected_price")
        @Expose
        private Integer expectedPrice;
        @SerializedName("trucks")
        @Expose
        private List<String> trucks = new ArrayList<String>();
        @SerializedName("drivers")
        @Expose
        private List<String> drivers = new ArrayList<String>();
        @SerializedName("driver_mobile")
        @Expose
        private String driverMobile;
        @SerializedName("weight")
        @Expose
        private Double weight;
        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("offer")
        @Expose
        private String offer;

        /**
         *
         * @return
         * The date
         */
        public DatePosting getDate() {
            return date;
        }

        /**
         *
         * @param date
         * The date
         */
        public void setDate(DatePosting date) {
            this.date = date;
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
         * The radius
         */
        public Integer getRadius() {
            return radius;
        }

        /**
         *
         * @param radius
         * The radius
         */
        public void setRadius(Integer radius) {
            this.radius = radius;
        }

        /**
         *
         * @return
         * The vehicleType
         */
        public VehicleType getVehicleType() {
            return vehicleType;
        }

        /**
         *
         * @param vehicleType
         * The vehicleType
         */
        public void setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
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
         * The trucks
         */
        public List<String> getTrucks() {
            return trucks;
        }

        /**
         *
         * @param trucks
         * The trucks
         */
        public void setTrucks(List<String> trucks) {
            this.trucks = trucks;
        }

        /**
         *
         * @return
         * The drivers
         */
        public List<String> getDrivers() {
            return drivers;
        }

        /**
         *
         * @param drivers
         * The drivers
         */
        public void setDrivers(List<String> drivers) {
            this.drivers = drivers;
        }

        /**
         *
         * @return
         * The driverMobile
         */
        public String getDriverMobile() {
            return driverMobile;
        }

        /**
         *
         * @param driverMobile
         * The driver_mobile
         */
        public void setDriverMobile(String driverMobile) {
            this.driverMobile = driverMobile;
        }

        /**
         *
         * @return
         * The weight
         */
        public Double getWeight() {
            return weight;
        }

        /**
         *
         * @param weight
         * The weight
         */
        public void setWeight(Double weight) {
            this.weight = weight;
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
         * The offer
         */
        public String getOffer() {
                return offer;
        }

        /**
         *
         * @param offer
         * The offer
         */
        public void setOffer(String offer) {
                this.offer = offer;
        }

    }

