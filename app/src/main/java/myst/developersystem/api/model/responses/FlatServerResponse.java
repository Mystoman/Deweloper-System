package myst.developersystem.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import myst.developersystem.api.model.json.BuildingData;
import myst.developersystem.api.model.json.FlatData;

/**
 * Created by Michal on 24.05.18.
 */

public class FlatServerResponse implements ResponsesInterface<FlatData> {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<FlatData> data;

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

    public List<FlatData> getData() {
        return data;
    }

    public void setData(List<FlatData> data) {
        this.data = data;
    }

}
