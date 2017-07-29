package ir.ac.ut.ece.moallem.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mushtu on 7/15/17.
 */

public class Category extends BaseModel {

    private String name;
    private Category parent;
    private List<Category> subCategories;

    public Category() {

    }

    public Category(long id, String name) {
        setId(id);
        setName(name);
    }

    public Category(long id, String name, Category parent) {
        this(id, name);
        setParent(parent);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void addSubCategory(Category sub) {
        if (subCategories == null)
            subCategories = new ArrayList<>();
        if (!existSub(sub))
            subCategories.add(sub);
    }

    private boolean existSub(Category sub) {
        Category finded = null;
        for (Category category : subCategories)
            if (category.getId() == sub.getId()) {
                finded = category;
                break;
            }
        return finded != null;
    }
}
