package myst.developersystem.api.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Michal on 03.05.18.
 */

public class BuildingData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("investment_id")
    @Expose
    private String investmentID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("floors")
    @Expose
    private Integer floors;
    @SerializedName("finish_date")
    @Expose
    private String finishDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvestmentID() {
        return investmentID;
    }

    public void setInvestmentID(String investmentID) {
        this.investmentID = investmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

}
