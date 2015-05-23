package fr.cnam.guillaumelimberger.cocotuture.entity;


public class Offer
{
    public static final String ID                   = "_id";
    public static final String DRIVER_ID            = "driver_id";
    public static final String CAR_MODEL            = "model";
    public static final String CAR_COLOR            = "color";
    public static final String CAR_LICENSE_NUMBER   = "license";
    public static final String MEETING_PLACE        = "meeting_place";
    public static final String DESTINATION          = "destination";
    public static final String MEETING_TIME         = "meeting_time";
    public static final String SEATING              = "seating";

    /**
     * Offer identifier
     */
    protected int id;

    /**
     * Driver identifier
     */
    protected Integer driverId;

    /**
     * Car model
     */
    protected String carModel;

    /**
     * Car color
     */
    protected String carColor;

    /**
     * Car License number
     */
    protected String carLicenseNumber;

    /**
     * Offer meeting place
     */
    protected String meetingPlace;

    /**
     * Offer meeting place coordinates
     */
    protected Double latitude;
    protected Double longitude;

    /**
     * Offer destination
     */
    protected String destination;

    /**
     * Offer meeting time
     */
    protected String meetingTime;

    /**
     * Offer seating
     */
    protected Integer seating;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarLicenseNumber() {
        return carLicenseNumber;
    }

    public void setCarLicenseNumber(String carLicenseNumber) {
        this.carLicenseNumber = carLicenseNumber;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Integer getSeating() {
        return seating;
    }

    public void setSeating(Integer seating) {
        this.seating = seating;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Offer{");
        sb.append("id=").append(id);
        sb.append(", driverId=").append(driverId);
        sb.append(", carModel='").append(carModel).append('\'');
        sb.append(", carColor='").append(carColor).append('\'');
        sb.append(", carLicenseNumber='").append(carLicenseNumber).append('\'');
        sb.append(", meetingPlace='").append(meetingPlace).append('\'');
        sb.append(", destination='").append(destination).append('\'');
        sb.append(", meetingTime=").append(meetingTime);
        sb.append(", seating=").append(seating);
        sb.append('}');
        return sb.toString();
    }
}
