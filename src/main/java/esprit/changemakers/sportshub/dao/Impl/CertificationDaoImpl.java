package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.ICertificationDao;
import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.utils.DataSource;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificationDaoImpl implements ICertificationDao {
    Connection cnx;

    public CertificationDaoImpl() {
        this.cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public Certification save(Certification entity) {
        try {
            String req = "INSERT INTO certification (name,speciality,user_id) VALUES(?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,entity.getCertifName());
            ps.setString(2, entity.getSpeciality().toString());
            ps.setInt(3,entity.getIdUser());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) entity.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void update(Certification entity) {
        try {
            String req = "UPDATE certification SET name=? ,speciality=? ,user_id=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,entity.getCertifName());
            ps.setString(2,entity.getSpeciality().toString());
            ps.setInt(3,entity.getIdUser());
            ps.setInt(4,entity.getId());
            ps.execute();
            System.out.println("Certif with id:"+entity.getId()+" Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            String req = "DELETE FROM certification WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ps.execute();
            System.out.println("Certif with id:"+id+" Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Certification> getAll() {
        ObservableList<Certification> certifications = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM certification";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()){
                certifications.add(new Certification(
                        rs.getInt(1),
                        rs.getString(3),
                        SpecialityEnum.valueOf(rs.getString(4)),
                        rs.getInt(2)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return certifications;
    }

    @Override
    public Certification getById(int id) {
        try {
            String req ="SELECT * FROM certification WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
               return new Certification(
                        rs.getInt(1),
                        rs.getString(3),
                        SpecialityEnum.valueOf(rs.getString(4)),
                        rs.getInt(2)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean find(Certification certification) {
        try {
            String req = "SELECT * FROM certification";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Certification certification1 = new Certification(
                        rs.getInt(1),
                        rs.getString(3),
                        SpecialityEnum.valueOf(rs.getString(4)),
                        rs.getInt(2)
                );
                if(certification.equals(certification1)) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Certification findOne(Certification entity) {
        try {
            String req = "SELECT * FROM certification";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Certification certification1 = new Certification(
                        rs.getInt(1),
                        rs.getString(3),
                        SpecialityEnum.valueOf(rs.getString(4)),
                        rs.getInt(2)
                );
                if(entity.equals(certification1)) return certification1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Certification> getAllCertifByUserID(int id) {
        List<Certification> certificationsList = new ArrayList<>();
        try {
            String req = "SELECT * FROM certification WHERE user_id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                certificationsList.add(new Certification(
                        rs.getInt(1),
                        rs.getString(3),
                        SpecialityEnum.valueOf(rs.getString(4)),
                        rs.getInt(2)
                ));
            }
            return certificationsList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return certificationsList;
    }

    @Override
    public List<Certification> getAllCertifBySpeciality(Enum<SpecialityEnum> specialityEnum) {
        List<Certification> certificationsList = new ArrayList<>();
        try {
            String req = "SELECT * FROM certification WHERE speciality=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,specialityEnum.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                certificationsList.add(new Certification(
                        rs.getInt(1),
                        rs.getString(3),
                        SpecialityEnum.valueOf(rs.getString(4)),
                        rs.getInt(2)
                ));
            }
            return certificationsList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return certificationsList;
    }
}
