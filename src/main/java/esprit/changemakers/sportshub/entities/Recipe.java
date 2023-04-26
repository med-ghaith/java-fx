package esprit.changemakers.sportshub.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public class Recipe {
    private int id;
    private String name;
    private String description;
    private String caloricValue;
    private ObservableList<Ingredient> ingredients;

    public Recipe(int id, String name, String description, String caloricValue) {
        ingredients = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.description = description;
        this.caloricValue = caloricValue;
    }

    public Recipe(String name, String description, String caloricValue) {
        ingredients = FXCollections.observableArrayList();
        this.name = name;
        this.description = description;
        this.caloricValue = caloricValue;
    }

    public Recipe() {
        ingredients = FXCollections.observableArrayList();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaloricValue() {
        return caloricValue;
    }

    public void setCaloricValue(String caloricValue) {
        this.caloricValue = caloricValue;
    }

    public ObservableList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ObservableList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe recipe = (Recipe) o;

        if (getName() != null ? !getName().equals(recipe.getName()) : recipe.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(recipe.getDescription()) : recipe.getDescription() != null)
            return false;
        if (getCaloricValue() != null ? !getCaloricValue().equals(recipe.getCaloricValue()) : recipe.getCaloricValue() != null)
            return false;
        return getIngredients() != null ? getIngredients().equals(recipe.getIngredients()) : recipe.getIngredients() == null;
    }

    @Override
    public int hashCode() {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + (getCaloricValue() != null ? getCaloricValue().hashCode() : 0);
        result = 31 * result + (getIngredients() != null ? getIngredients().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nRecipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", caloricValue='" + caloricValue + '\'' +
                ",\n ingredients=" + ingredients +
                '}';
    }
}
