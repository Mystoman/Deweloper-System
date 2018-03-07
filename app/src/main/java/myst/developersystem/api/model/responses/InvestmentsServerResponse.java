package myst.developersystem.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import myst.developersystem.api.model.InvestmentsData;

/**
 * Created by Michal on 11.11.17.
 */

public class InvestmentsServerResponse implements ResponsesInterface<InvestmentsData> {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<InvestmentsData> data;

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

    public List<InvestmentsData> getData() {
        return data;
    }

    public void setData(List<InvestmentsData> data) {
        this.data = data;
    }
}