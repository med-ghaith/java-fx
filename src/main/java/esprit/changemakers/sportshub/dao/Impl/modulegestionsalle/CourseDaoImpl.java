package esprit.changemakers.sportshub.dao.Impl.modulegestionsalle;

import esprit.changemakers.sportshub.dao.IGenericDao;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Course;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class CourseDaoImpl implements IGenericDao<Course> {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	public CourseDaoImpl() {
		this.con = DataSource.getInstance().getCnx();
	}

	@Override
	public Course save(Course entity) {
		String req = "INSERT INTO `course` (gym_id, name,video,description)values(?,?,?,?) ";
		try {
			preparedStatement = con.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getGym_id());
			preparedStatement.setString(2, entity.getName());
			preparedStatement.setString(3, entity.getVideo());
			preparedStatement.setString(4, entity.getDescription());
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
	public void update(Course entity) {
		String sql = "UPDATE course set name=?, description=?, video=?  where id='"
				+ entity.getId() + "'";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setString(2, entity.getDescription());
			preparedStatement.setString(3, entity.getVideo());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void deleteById(int id) {
		String sql = "DELETE from course where id=?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1,id);
			preparedStatement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public ObservableList<Course> getAll() {
		ObservableList<Course> list = FXCollections.observableArrayList();
		String req = "Select * from course";
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(req);
			while (rs.next()) {
				Course course = new Course(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				list.add(course);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return list;
	}

	@Override
	public Course getById(int id) {
		return null;
	}

	@Override
	public boolean find(Course course) {
		return false;
	}

	@Override
	public Course findOne(Course entity) {
		return null;
	}
}
