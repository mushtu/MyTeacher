package ir.ac.ut.ece.moallem.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mushtu on 3/21/17.
 */
public class Teacher extends User {

    private float rating;
    private List<UserReference> references = new ArrayList<>();
    private List<Course> teachingCourses = new ArrayList<>();
    private List<Section> sections = new ArrayList<>();
    private Map<Praise, Integer> praises = new HashMap<>();


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<UserReference> getReferences() {
        return references;
    }

    public void setReferences(List<UserReference> references) {
        this.references = references;
    }

    public List<Course> getTeachingCourses() {
        return teachingCourses;
    }

    public void setTeachingCourses(List<Course> teachingCourses) {
        this.teachingCourses = teachingCourses;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Map<Praise, Integer> getPraises() {
        return praises;
    }

    public void setPraises(Map<Praise, Integer> praises) {
        this.praises = praises;
    }
}
