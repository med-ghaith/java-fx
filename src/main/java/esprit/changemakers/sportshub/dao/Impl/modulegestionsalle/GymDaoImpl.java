package esprit.changemakers.sportshub.dao.Impl.modulegestionsalle;


import esprit.changemakers.sportshub.dao.IGenericDao;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GymDaoImpl implements IGenericDao<Gym> {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	public GymDaoImpl() {
		this.con = DataSource.getInstance().getCnx();
	}

	@Override
	public Gym save(Gym entity) {
		String req = "INSERT INTO `gym` (user_id,name, description,location,image) VALUES (?,?,?,?,?)";
		try {
			preparedStatement = con.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getUser_id());
			preparedStatement.setString(2, entity.getName());
			preparedStatement.setString(3, entity.getDescription());
			preparedStatement.setString(4, entity.getLocation());
			preparedStatement.setString(5, entity.getImage());
					preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if(rs.next()){
				entity.setId(rs.getInt(1));
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return entity;
	}

	@Override
	public void update(Gym entity) {
		String sql = "UPDATE gym set name=?, description=?, location=?, image=? where id='" + entity.getId() + "'";

		try {
			preparedStatement  = con.prepareStatement(sql);
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setString(2, entity.getDescription());
			preparedStatement.setString(3, entity.getLocation());
			preparedStatement.setString(4, entity.getImage());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void deleteById(int id) {
		String sql = "DELETE from gym where id='" + id + "'";
		try {
			Statement st = con.createStatement();
		st.executeUpdate(sql);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public ObservableList<Gym> getAll() {
		ObservableList<Gym> list = FXCollections.observableArrayList();
		String req = "Select * from Gym";
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(req);
			while (rs.next()) {
				Gym gym = new Gym(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5),rs.getString(6));
				list.add(gym);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return list;
	}

	@Override
	public Gym getById(int id) {
		return null;
	}

	@Override
	public boolean find(Gym gym) {
		return false;
	}

	@Override
	public Gym findOne(Gym entity) {
		return null;
	}
}
