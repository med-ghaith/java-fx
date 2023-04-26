package esprit.changemakers.sportshub.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public class Meal {
    private int id;
    private String name;
    private String imageUrl;
    private String description;
    private ObservableList<Recipe> recipes = FXCollections.observableArrayList();

    public Meal(int id, String name, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Meal(String name, String imageUrl, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Meal() {
    }

    public ObservableList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ObservableList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;

        Meal meal = (Meal) o;

        if (getName() != null ? !getName().equals(meal.getName()) : meal.getName() != null) return false;
        if (getImageUrl() != null ? !getImageUrl().equals(meal.getImageUrl()) : meal.getImageUrl() != null)
            return false;
        return getDescription() != null ? getDescription().equals(meal.getDescription()) : meal.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getImageUrl() != null ? getImageUrl().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nMeal{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ",\n recipes=" + recipes +
                '}';
    }
}
