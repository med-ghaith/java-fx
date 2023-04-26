package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IReservationDao;
import esprit.changemakers.sportshub.dao.Impl.ReservationDaoImpl;
import esprit.changemakers.sportshub.entities.Reservation;
import esprit.changemakers.sportshub.services.IReservationService;
import javafx.collections.ObservableList;

public class ReservationServiceImpl implements IReservationService {
    IReservationDao reservationDao;
    public ReservationServiceImpl() { reservationDao = new ReservationDaoImpl();}

    public Reservation create(Reservation entity) {
        if(!reservationDao.find(entity)){
            return reservationDao.save(entity);
        }else {
            System.out.println("Reservation "+entity.getId_reservation()+" déjà existant");
            return reservationDao.findOne(entity);
        }
    }

    public void update(Reservation entity) {
        reservationDao.update(entity);
    }

    public void remove(int id) {
        reservationDao.deleteById(id);
    }

    public Reservation getById(int id) {
        return reservationDao.getById(id);
    }

    public ObservableList<Reservation> getAll() {
        return reservationDao.getAll();
    }
}
