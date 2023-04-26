package esprit.changemakers.sportshub.entities;

import java.time.LocalDate;

/**
 *
 * @author Mohamed Ali
 */
public class Planning {
    private int id_planning;
    private int id_user;
    private LocalDate planning_date;

    public Planning(int id_planning, int id_user, LocalDate planning_date) {
        this.id_planning = id_planning;
        this.id_user = id_user;

        this.planning_date = planning_date;
    }

    public Planning(int id_user, LocalDate planning_date) {
        this.id_user = id_user;
        this.planning_date = planning_date;
    }

    public Planning() {
    }

    public int getId_planning() {
        return id_planning;
    }

    public void setId_planning(int id_planning) {
        this.id_planning = id_planning;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public LocalDate getPlanning_date() {
        return planning_date;
    }

    public void setPlanning_date(LocalDate planning_date) {
        this.planning_date = planning_date;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id_planning=" + id_planning +
                ", id_user=" + id_user +
                ", planning_date=" + planning_date +
                '}';
    }
}