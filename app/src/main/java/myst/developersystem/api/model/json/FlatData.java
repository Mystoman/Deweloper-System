package myst.developersystem.api.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Michal on 24.05.18.
 */

public class FlatData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("building_id")
    @Expose
    private String buildingID;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("floor")
    @Expose
    private Integer floor;
    @SerializedName("area")
    @Expose
    private Float area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

}
