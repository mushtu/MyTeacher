package ir.ac.ut.ece.moallem.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mushtu on 7/7/17.
 */

public class Student extends User {

    private List<SectionApply> applies = new ArrayList<>();

    public List<SectionApply> getApplies() {
        return applies;
    }

    public void setApplies(List<SectionApply> applies) {
        this.applies = applies;
    }
}
