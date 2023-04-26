package esprit.changemakers.sportshub.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public class Exercice {
    private int id;
    private String name;
    private String  imageUrl;
    private String description;

    public enum Difficulty{
        easy,medium,hard
    }
    private Difficulty difficultyLevel;
    private int numberOfSets;
    private int numberOfRepetition;
    private String restTime;

    // un attribue transient ( la liste des muscles touché par l'exercice )
    private ObservableList<Muscle> muscles = FXCollections.observableArrayList();

    // un attribue transient ( la liste des équipments )
    private ObservableList<Equipment> equipments = FXCollections.observableArrayList();

    public ObservableList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ObservableList<Equipment> equipments) {
        this.equipments = equipments;
    }

    public ObservableList<Muscle> getMuscles() {
        return muscles;
    }

    public void setMuscles(ObservableList<Muscle> muscles) {
        this.muscles = muscles;
    }

    public Exercice(int id, String name, String imageUrl, String description, Difficulty difficultyLevel, int numberOfSets, int numberOfRepetition, String restTime) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.numberOfSets = numberOfSets;
        this.numberOfRepetition = numberOfRepetition;
        this.restTime = restTime;
    }

    public Exercice(String name, String imageUrl, String description, Difficulty difficultyLevel, int numberOfSets, int numberOfRepetition, String restTime) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.numberOfSets = numberOfSets;
        this.numberOfRepetition = numberOfRepetition;
        this.restTime = restTime;
    }

    public Exercice() {
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

    public Difficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Difficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public int getNumberOfRepetition() {
        return numberOfRepetition;
    }

    public void setNumberOfRepetition(int numberOfRepetition) {
        this.numberOfRepetition = numberOfRepetition;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercice)) return false;

        Exercice exercice = (Exercice) o;

        if (getNumberOfSets() != exercice.getNumberOfSets()) return false;
        if (getNumberOfRepetition() != exercice.getNumberOfRepetition()) return false;
        if (getName() != null ? !getName().equals(exercice.getName()) : exercice.getName() != null) return false;
        if (getImageUrl() != null ? !getImageUrl().equals(exercice.getImageUrl()) : exercice.getImageUrl() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(exercice.getDescription()) : exercice.getDescription() != null)
            return false;
        if (getDifficultyLevel() != exercice.getDifficultyLevel()) return false;
        return getRestTime() != null ? getRestTime().equals(exercice.getRestTime()) : exercice.getRestTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getImageUrl() != null ? getImageUrl().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getDifficultyLevel() != null ? getDifficultyLevel().hashCode() : 0);
        result = 31 * result + getNumberOfSets();
        result = 31 * result + getNumberOfRepetition();
        result = 31 * result + (getRestTime() != null ? getRestTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nExercice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", numberOfSets=" + numberOfSets +
                ", numberOfRepetition=" + numberOfRepetition +
                ", restTime='" + restTime + '\'' +
                ",\n muscles=" + muscles +
                ",\n equipments=" + equipments +
                '}';
    }
}
