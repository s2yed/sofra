
package com.sofra.sofra.data.model.showReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sofra.sofra.data.model.Generated.GeneratedClient;
import com.sofra.sofra.data.model.Generated.GeneratedRestaurant;

public class DataComment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("client")
    @Expose
    private GeneratedClient client;
    @SerializedName("restaurant")
    @Expose
    private GeneratedRestaurant restaurant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public GeneratedClient getClient() {
        return client;
    }

    public void setClient(GeneratedClient client) {
        this.client = client;
    }

    public GeneratedRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(GeneratedRestaurant restaurant) {
        this.restaurant = restaurant;
    }

}
