package com.tech.airbnb.Model;

import java.util.ArrayList;

public class Model {

    private ArrayList<String> imageUrls;
    private String mainDesc;
    private int stars;
    private int ratings;
    private String location;
    private String hostName;
    private String hostImgUrl;
    private int beds;
    private int baths;
    private int guests;
    private String apartmentType;
    private int cost;
    private String duration;

    public Model() {
    }

    public Model(ArrayList<String> imageUrls, String mainDesc, int stars, int ratings, String location, String hostName, String hostImgUrl, int beds, int baths, int guests, String apartmentType, int cost, String duration) {
        this.imageUrls = imageUrls;
        this.mainDesc = mainDesc;
        this.stars = stars;
        this.ratings = ratings;
        this.location = location;
        this.hostName = hostName;
        this.hostImgUrl = hostImgUrl;
        this.beds = beds;
        this.baths = baths;
        this.guests = guests;
        this.apartmentType = apartmentType;
        this.cost = cost;
        this.duration = duration;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getMainDesc() {
        return mainDesc;
    }

    public void setMainDesc(String mainDesc) {
        this.mainDesc = mainDesc;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostImgUrl() {
        return hostImgUrl;
    }

    public void setHostImgUrl(String hostImgUrl) {
        this.hostImgUrl = hostImgUrl;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
