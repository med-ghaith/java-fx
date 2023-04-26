package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.PrivateMessage;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;

/**
 * @author Jozef
 */
public interface IPrivateMessageService extends IGenericService<PrivateMessage> {

    public ObservableList<PrivateMessage> getTwoUsersDiscSortedByTimestamp(int idFirstUser, int idSecondUser);

    public Set<Integer> getAllUserConv(int idUser);

    public Set<Integer> getAllUserConvInv(int idUser);

    public Set<Integer> getAllUserConver(int idUser);
}
