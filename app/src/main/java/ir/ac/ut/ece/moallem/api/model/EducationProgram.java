package ir.ac.ut.ece.moallem.api.model;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

/**
 * Created by mushtu on 7/13/17.
 */

public class EducationProgram extends BaseModel implements SortedListAdapter.ViewModel {


    private String name;


    public String getName() {
        return name;
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T t) {
        if (t instanceof EducationProgram) {
            final EducationProgram educationProgram = (EducationProgram) t;
            return educationProgram.getId() == getId();
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T t) {

        if (t instanceof EducationProgram) {
            EducationProgram other = (EducationProgram) t;
            return name != null ? name.equals(other.getName()) : other.getName() == null;
        }

        return false;
    }

    public void setName(String name) {
        this.name = name;
    }
}
