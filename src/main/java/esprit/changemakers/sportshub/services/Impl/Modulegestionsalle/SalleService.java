package esprit.changemakers.sportshub.services.Impl.Modulegestionsalle;

import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.CourseDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.GymDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Course;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.stream.Collectors;

public class SalleService {
    GymDaoImpl gym;
    CourseDaoImpl cours;
    ObservableList<Gym> all_gym ;
    ObservableList<Course> all_Course ;
    public SalleService(){
        gym = new GymDaoImpl();
        cours= new CourseDaoImpl();
        all_gym=gym.getAll();
        all_Course=cours.getAll();
    }
//    Pour avoir les donnees de la salle par id
    public Gym getgymbyId(int id ){
        return all_gym.stream().filter(x -> x.getId()==id).findFirst().get();
    }

//    pour avoir le plannig des cours plannifier par la salle
    public ObservableList<Course> getCoursbyGymId(int id ){
        ObservableList<Course> list = FXCollections.observableArrayList();
        all_Course.stream().filter(x -> x.getGym_id()==id).forEach(x-> list.add(x));
        return  list;
    }
//    pour avoir le plannig des cours plannifier par la salle
    public ObservableList<Gym> getGymbyfiltre(String search ){
        ObservableList<Gym> list = FXCollections.observableArrayList();
        all_gym.stream().filter(x -> x.getName().contains(search)||x.getName().toLowerCase(Locale.ROOT).contains(search)
                ||x.getLocation().contains(search)||x.getLocation().toLowerCase(Locale.ROOT).contains(search)).forEach(x-> list.add(x));
        return  list;
    }
}
