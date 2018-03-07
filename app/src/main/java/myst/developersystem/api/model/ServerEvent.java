package myst.developersystem.api.model;

import myst.developersystem.api.model.responses.ResponsesInterface;

/**
 * Created by Michal on 11.11.17.
 */

public class ServerEvent {
    private ResponsesInterface serverResponse;

    public ServerEvent(ResponsesInterface serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ResponsesInterface getServerResponse() {
        return serverResponse;
    }

    public void setBasicServerResponse(ResponsesInterface basicServerResponse) {
        this.serverResponse = basicServerResponse;
    }
}
