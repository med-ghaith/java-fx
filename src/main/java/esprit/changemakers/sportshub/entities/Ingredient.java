package esprit.changemakers.sportshub.entities;

/**
 * @author Jozef
 */
public class Ingredient {
    private int id;
    private String name;
    private int calorie;
    private int totalFat;
    private int protein;

    public Ingredient(int id, String name, int calorie, int totalFat, int protein) {
        this.id = id;
        this.name = name;
        this.calorie = calorie;
        this.totalFat = totalFat;
        this.protein = protein;
    }

    public Ingredient(String name, int calorie, int totalFat, int protein) {
        this.name = name;
        this.calorie = calorie;
        this.totalFat = totalFat;
        this.protein = protein;
    }

    public Ingredient() {
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

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(int totalFat) {
        this.totalFat = totalFat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", calorie=" + calorie +
                ", totalFat=" + totalFat +
                ", protein=" + protein +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        if (getCalorie() != that.getCalorie()) return false;
        if (getTotalFat() != that.getTotalFat()) return false;
        if (getProtein() != that.getProtein()) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getCalorie();
        result = 31 * result + getTotalFat();
        result = 31 * result + getProtein();
        return result;
    }
}
