package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IPrivateMessageDao;
import esprit.changemakers.sportshub.entities.PrivateMessage;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @author Jozef
 */
public class PrivateMessageImpl implements IPrivateMessageDao {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public PrivateMessageImpl() {
        con = DataSource.getInstance().getCnx();
    }

    public PrivateMessage save(PrivateMessage entity) {
        String req = "insert into private_message (id_first_user_id, id_second_user_id, content) VALUES (?,?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,entity.getIdFirstUser());
            preparedStatement.setInt(2,entity.getIdSecondUser());
            preparedStatement.setString(3,entity.getContent());
            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                entity.setId(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    public void update(PrivateMessage privateMessage) {
        String req = "update private_message set content=? where id=?";

        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,privateMessage.getContent());
            preparedStatement.setInt(2,privateMessage.getId());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req = "delete from private_message where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<PrivateMessage> getAll() {
        String req = "select * from private_message";
        ObservableList<PrivateMessage> privateMessages = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                PrivateMessage p1 = new PrivateMessage(rs.getInt(1)
                        ,rs.getInt(2)
                        ,rs.getInt(3)
                        ,rs.getString(4)
                        ,rs.getTimestamp(5));
                privateMessages.add(p1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return privateMessages;
    }

    public PrivateMessage getById(int id) {
        String req = "select * from private_message where id=?";
        PrivateMessage privateMessage = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);
            rs = preparedStatement.executeQuery();

            if(rs.next()){
                privateMessage = new PrivateMessage(rs.getInt(1)
                        ,rs.getInt(2)
                        ,rs.getInt(3)
                        ,rs.getString(5)
                        ,rs.getTimestamp(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return privateMessage;
    }

    public boolean find(PrivateMessage privateMessage) {
        return false;
    }

    public PrivateMessage findOne(PrivateMessage entity) {
        return null;
    }
}
