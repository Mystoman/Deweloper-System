package myst.developersystem.api.model;

import com.squareup.otto.Bus;

/**
 * Created by Michal on 11.11.17.
 */

public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider(){}
}
