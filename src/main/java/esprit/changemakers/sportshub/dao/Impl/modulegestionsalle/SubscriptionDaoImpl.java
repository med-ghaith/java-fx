package esprit.changemakers.sportshub.dao.Impl.modulegestionsalle;

import esprit.changemakers.sportshub.dao.IGenericDao;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Subscription;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class SubscriptionDaoImpl implements IGenericDao<Subscription> {

	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	public SubscriptionDaoImpl() {
		this.con = DataSource.getInstance().getCnx();
	}

	public Subscription save(Subscription entity) {
		String req = "INSERT INTO `subscription` (member_id,gym_id,start_date,validity) values(?,?,?,?) ";
		try {
			preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getMember_id());
			preparedStatement.setInt(2, entity.getGym_id());
			preparedStatement.setDate(3, Date.valueOf(entity.getStart_date()));
			preparedStatement.setString(4, entity.getValidity());
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

	public void update(Subscription t) {
		String sql = "UPDATE Subscription set start_date=?, validity=? where id='"
				+ t.getId() + "'";
		try {

			preparedStatement=con.prepareStatement(sql);
			preparedStatement.setDate(1, Date.valueOf(t.getStart_date()));
			preparedStatement.setString(2, t.getValidity());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void deleteById(int id) {
		String sql = "DELETE from Subscription where id='" + id + "'";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ObservableList<Subscription> getAll() {
		ObservableList<Subscription> list = FXCollections.observableArrayList();
		try {
			String req = "Select * from Subscription";
			Statement st = con.createStatement();
			rs = st.executeQuery(req);
			while (rs.next()) {
				Subscription sub = new Subscription(rs.getInt(1), rs.getInt(2), rs.getInt(3),
						rs.getDate(4).toLocalDate(), rs.getString(5));
				list.add(sub);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return list;
	}

	public Subscription getById(int id) {
		return null;
	}

	public boolean find(Subscription subscription) {
		return false;
	}

	public Subscription findOne(Subscription entity) {
		return null;
	}
}
