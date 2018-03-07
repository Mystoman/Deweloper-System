package myst.developersystem.api.model;

/**
 * Created by Michal on 01.03.18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestmentsData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coords")
    @Expose
    private String coords;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postal")
    @Expose
    private String postal;
    @SerializedName("street")
    @Expose
    private String street;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}