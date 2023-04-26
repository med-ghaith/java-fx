package esprit.changemakers.sportshub.entities;

import java.time.LocalDate;

public class Event {
    private int id_event;
    private LocalDate start_date;
    private LocalDate end_date;
    private String description;
    private int planning_id;
    private int nombreReservation;
    private int fees;
    private String category;
    private String imageUrl;


    public Event() {
    }

    public Event(int id_event, LocalDate start_date, LocalDate end_date, String description, int planning_id, int nombreReservation, int fees, String category, String imageUrl) {
        this.id_event = id_event;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.planning_id = planning_id;
        this.nombreReservation = nombreReservation;
        this.fees = fees;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Event(LocalDate start_date, LocalDate end_date, String description, int planning_id, int nombreReservation, int fees, String category, String imageUrl) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.planning_id = planning_id;
        this.nombreReservation = nombreReservation;
        this.fees = fees;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlanning_id() {
        return planning_id;
    }

    public void setPlanning_id(int planning_id) {
        this.planning_id = planning_id;
    }

    public int getNombreReservation() {
        return nombreReservation;
    }

    public void setNombreReservation(int nombreReservation) {
        this.nombreReservation = nombreReservation;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id_event=" + id_event +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", description='" + description + '\'' +
                ", planning_id=" + planning_id +
                ", nombreReservation=" + nombreReservation +
                ", fees=" + fees +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
