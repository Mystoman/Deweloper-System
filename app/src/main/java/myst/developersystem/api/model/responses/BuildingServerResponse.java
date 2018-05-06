package myst.developersystem.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import myst.developersystem.api.model.json.BuildingData;

/**
 * Created by Michal on 03.05.18.
 */

public class BuildingServerResponse implements ResponsesInterface<BuildingData> {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<BuildingData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BuildingData> getData() {
        return data;
    }

    public void setData(List<BuildingData> data) {
        this.data = data;
    }

}
