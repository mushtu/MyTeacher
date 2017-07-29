package ir.ac.ut.ece.moallem.api.model;

import java.io.Serializable;

/**
 * Created by mushtu on 6/29/17.
 */
public class BaseModel implements Serializable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
