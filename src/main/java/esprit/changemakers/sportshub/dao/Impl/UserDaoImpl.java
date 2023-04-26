package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IUserDao;
import esprit.changemakers.sportshub.entities.Adherent;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.utils.DataSource;
import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @author Ahmed
 */
public class UserDaoImpl implements IUserDao {
    Connection cnx ;


    public UserDaoImpl() {
        this.cnx = DataSource.getInstance().getCnx();
    }

    public User save(User entity) {
        if(entity.getRole().toString().equals("ADMIN") || entity.getRole().toString().equals("GYMMANGER")){
            try {
                String req = "INSERT INTO user (first_name,last_name,email,password,security_question,security_answer,role) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,entity.getNom());
                ps.setString(2,entity.getPrenom());
                ps.setString(3,entity.getEmail());
                ps.setString(4,entity.getPassword());
                ps.setString(5,entity.getSecurityQuestion().toString());
                ps.setString(6,entity.getSecurityAnswer());
                ps.setString(7,entity.getRole().toString());
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    entity.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return entity;
        }else if(entity.getRole().toString().equals("COACH")){
            Coach coach = (Coach) entity;
            try {
                String req = "INSERT INTO user (first_name,last_name,email,password,security_question,security_answer,role,description,phone_number,birth_date,city,img_url) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,coach.getNom());
                ps.setString(2,coach.getPrenom());
                ps.setString(3,coach.getEmail());
                ps.setString(4,coach.getPassword());
                ps.setString(5,coach.getSecurityQuestion().toString());
                ps.setString(6,coach.getSecurityAnswer());
                ps.setString(7,coach.getRole().toString());
                ps.setString(8,coach.getDescription());
                ps.setString(9,coach.getPhoneNumber());
                ps.setDate(10,Date.valueOf(coach.getBirthDate()));
                ps.setString(11,coach.getCity());
                ps.setString(12,coach.getImgURL());
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    entity.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return coach;
        }else{
            Adherent adherent = (Adherent) entity;
            try {
                String req = "INSERT INTO user (first_name,last_name,email,password,security_question,security_answer,role,description,phone_number,birth_date,city,img_url) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,adherent.getNom());
                ps.setString(2,adherent.getPrenom());
                ps.setString(3,adherent.getEmail());
                ps.setString(4,adherent.getPassword());
                ps.setString(5,adherent.getSecurityQuestion().toString());
                ps.setString(6,adherent.getSecurityAnswer());
                ps.setString(7,adherent.getRole().toString());
                ps.setString(8,adherent.getDescription());
                ps.setString(9,adherent.getPhoneNumber());
                ps.setDate(10,Date.valueOf(adherent.getBirthDate()));
                ps.setString(11,adherent.getCity());
                ps.setString(12, adherent.getImgURL());
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    entity.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return adherent;
        }
    }

    public void update(User entity) {
        if(entity.getRole().toString().equals("ADMIN") || entity.getRole().toString().equals("GYMMANGER")){
            try {
                String req = "UPDATE user SET  first_name=? ,last_name=? ,email=? ,password=? ,security_question=? ,security_answer=?,role=? WHERE id=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1,entity.getNom());
                ps.setString(2,entity.getPrenom());
                ps.setString(3,entity.getEmail());
                ps.setString(4,entity.getPassword());
                ps.setString(5,entity.getSecurityQuestion().toString());
                ps.setString(6,entity.getSecurityAnswer());
                ps.setString(7,entity.getRole().toString());
                ps.setInt(8,entity.getId());
                ps.execute();
                System.out.println("User with id:"+entity.getId()+" UPDATED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(entity.getRole().toString().equals("COACH")){
            Coach coach = (Coach) entity;
            try {
                String req = "UPDATE user SET  first_name=? ,last_name=? ,email=? ,password=? ,security_question=? ,security_answer=?,role=?,description=? ,phone_number=? ,birth_date=? ,city=?,img_url=? WHERE id=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1,coach.getNom());
                ps.setString(2,coach.getPrenom());
                ps.setString(3,coach.getEmail());
                ps.setString(4,coach.getPassword());
                ps.setString(5,coach.getSecurityQuestion().toString());
                ps.setString(6,coach.getSecurityAnswer());
                ps.setString(7,coach.getRole().toString());
                ps.setString(8,coach.getDescription());
                ps.setString(9,coach.getPhoneNumber());
                ps.setDate(10,Date.valueOf(coach.getBirthDate()));
                ps.setString(11,coach.getCity());
                ps.setString(12,coach.getImgURL());
                ps.setInt(13,coach.getId());
                ps.execute();
                System.out.println("User with id:"+coach.getId()+" UPDATED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Adherent adherent = (Adherent) entity;
            try {
                String req = "UPDATE user SET  first_name=? ,last_name=? ,email=? ,password=? ,security_question=? ,security_answer=?,role=?,description=? ,phone_number=? ,birth_date=? ,city=?,img_url=? WHERE id=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1,adherent.getNom());
                ps.setString(2,adherent.getPrenom());
                ps.setString(3,adherent.getEmail());
                ps.setString(4,adherent.getPassword());
                ps.setString(5,adherent.getSecurityQuestion().toString());
                ps.setString(6,adherent.getSecurityAnswer());
                ps.setString(7,adherent.getRole().toString());
                ps.setString(8,adherent.getDescription());
                ps.setString(9,adherent.getPhoneNumber());
                ps.setDate(10,Date.valueOf(adherent.getBirthDate()));
                ps.setString(11,adherent.getCity());
                ps.setString(12,adherent.getImgURL());
                ps.setInt(13,adherent.getId());
                ps.execute();
                System.out.println("User with id:"+adherent.getId()+" UPDATED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteById(int id) {
        try {
            String req = "DELETE FROM user WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ps.execute();
            System.out.println("User with id:"+id+" Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<User> getAll() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM user";
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString(8).equals("ADMIN") || rs.getString(8).equals("GYMMANGER")){
                    users.add(new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7)
                    ));
                }else if(rs.getString(8).equals("COACH")){
                    users.add(new Coach(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7),
                            rs.getString(13),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getDate(11).toLocalDate(),
                            rs.getString(12)
                    ));
                }else if(rs.getString(8).equals("ADHERENT")){
                    users.add(new Adherent(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7),
                            rs.getString(13),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getDate(11).toLocalDate(),
                            rs.getString(12)
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getById(int id) {
        try {
            String req = "SELECT * FROM user WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            if(rs.getString(8).equals("ADMIN") || rs.getString(8).equals("GYMMANGER")){
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        RoleEnum.valueOf(rs.getString(8)),
                        SecurityQuestionEnum.valueOf(rs.getString(6)),
                        rs.getString(7)
                );
            }else if(rs.getString(8).equals("COACH")){
                return new Coach(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        RoleEnum.valueOf(rs.getString(8)),
                        SecurityQuestionEnum.valueOf(rs.getString(6)),
                        rs.getString(7),
                        rs.getString(13),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getDate(11).toLocalDate(),
                        rs.getString(12)
                );
            }else if(rs.getString(8).equals("ADHERENT")){
                return new Adherent(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        RoleEnum.valueOf(rs.getString(8)),
                        SecurityQuestionEnum.valueOf(rs.getString(6)),
                        rs.getString(7),
                        rs.getString(13),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getDate(11).toLocalDate(),
                        rs.getString(12)
                );
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean find(User user) {
        try {
            String req = "SELECT * FROM user";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                User u = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        RoleEnum.valueOf(rs.getString(8)),
                        SecurityQuestionEnum.valueOf(rs.getString(6)),
                        rs.getString(7)
                );
                if (u.equals(user)) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User findOne(User entity) {
        try {
            String req="SELECT * FROM user WHERE email=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,entity.getEmail());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if(rs.getString(8).equals("ADMIN") || rs.getString(8).equals("GYMMANGER")){
                    return new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7)
                    );
                }else if(rs.getString(8).equals("COACH")){
                    return new Coach(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7),
                            rs.getString(13),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getDate(11).toLocalDate(),
                            rs.getString(12)
                    );
                }else if(rs.getString(8).equals("ADHERENT")){
                    return new Adherent(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7),
                            rs.getString(13),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getDate(11).toLocalDate(),
                            rs.getString(12)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        try {
            String req = "SELECT * FROM user WHERE email=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if(rs.getString(8).equals("ADMIN") || rs.getString(8).equals("GYMMANGER")){
                    return new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7)
                    );
                }else if(rs.getString(8).equals("COACH")){
                    return new Coach(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7),
                            rs.getString(13),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getDate(11).toLocalDate(),
                            rs.getString(12)
                    );
                }else if(rs.getString(8).equals("ADHERENT")){
                    return new Adherent(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            RoleEnum.valueOf(rs.getString(8)),
                            SecurityQuestionEnum.valueOf(rs.getString(6)),
                            rs.getString(7),
                            rs.getString(13),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getDate(11).toLocalDate(),
                            rs.getString(12)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
