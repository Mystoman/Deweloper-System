package myst.developersystem.api.model.responses;

import java.util.List;

/**
 * Created by Michal on 04.03.18.
 */

public interface ResponsesInterface<Type> {

    String getStatus();

    void setStatus(String status);

    String getMessage();

    void setMessage(String message);

    List<Type> getData();

    void setData(List<Type> data);

}
