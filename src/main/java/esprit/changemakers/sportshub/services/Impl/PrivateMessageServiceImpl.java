package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IPrivateMessageDao;
import esprit.changemakers.sportshub.dao.Impl.PrivateMessageImpl;
import esprit.changemakers.sportshub.entities.PrivateMessage;
import esprit.changemakers.sportshub.services.IPrivateMessageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jozef
 */
public class PrivateMessageServiceImpl implements IPrivateMessageService {
    IPrivateMessageDao privateMessageDao;

    public PrivateMessageServiceImpl() {
        privateMessageDao = new PrivateMessageImpl();
    }

    public PrivateMessage create(PrivateMessage entity) {
        return privateMessageDao.save(entity);
    }

    public void update(PrivateMessage entity) {
        privateMessageDao.update(entity);
    }

    public void remove(int id) {
        privateMessageDao.deleteById(id);
    }

    public PrivateMessage getById(int id) {
        return privateMessageDao.getById(id);
    }

    public ObservableList<PrivateMessage> getAll() {
        return privateMessageDao.getAll();
    }

    @Override
    public ObservableList<PrivateMessage> getTwoUsersDiscSortedByTimestamp(int idFirstUser, int idSecondUser) {
        ObservableList<PrivateMessage> privateMessages = getAll();
        List<PrivateMessage> returnedList;
        returnedList = privateMessages.stream().filter(e->(e.getIdFirstUser() == idFirstUser || e.getIdFirstUser() == idSecondUser ) && (e.getIdSecondUser() == idFirstUser || e.getIdSecondUser() == idSecondUser)).sorted().collect(Collectors.toList());
        return FXCollections.observableList(returnedList);
    }

    @Override
    public Set<Integer> getAllUserConv(int idUser) {
        return getAll().stream().filter(e->e.getIdFirstUser()==idUser).mapToInt(e->e.getIdSecondUser()).boxed().collect(Collectors.toSet());

    }

    @Override
    public Set<Integer> getAllUserConvInv(int idUser) {
        return getAll().stream().filter(e->e.getIdSecondUser()==idUser).mapToInt(e->e.getIdFirstUser()).boxed().collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getAllUserConver(int idUser) {
        return getAll().stream().filter(e->e.getIdFirstUser()==idUser).mapToInt(e->e.getIdFirstUser()).boxed().collect(Collectors.toSet());
    }
}
