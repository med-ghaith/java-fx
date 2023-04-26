/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.ICommandDao;
import esprit.changemakers.sportshub.entities.Command;
import esprit.changemakers.sportshub.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bilel abbassi
 */
public class CommandDaoImpl implements ICommandDao {

    Connection cnx = DataSource.getInstance().getCnx();

    public void addCommand(Command c) {
        try {
            String req = ("INSERT INTO Command (user_id,ref,Total_price,Status) VALUES (?,?,?,? )");
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, c.getUser_id());
            ps.setString(2, c.getRef());

           // ps.setInt(2, c.getCart_id());

            ps.setFloat(3, c.getTotal_price());
            ps.setString(4, c.getStatus());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<Command> listCommand() {
        List<Command> cm = new ArrayList<Command>();

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from Command");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Command c = new Command();
                c.setId(rs.getInt("Id"));
                c.setUser_id(rs.getInt("User_id"));

                c.setRef(rs.getString("ref"));
                c.setTotal_price(rs.getFloat("Total_price"));

                cm.add(c);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cm;
    }

    public List<Command> CommandParUser(int user_id) {
        List<Command> cm = new ArrayList<Command>();

        try {

            PreparedStatement ps = cnx.prepareStatement("select * from Command where user_id =?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Command c = new Command();
                c.setId(rs.getInt("Id"));
                c.setUser_id(rs.getInt("User_id"));

                c.setRef(rs.getString("ref"));
                c.setTotal_price(rs.getFloat("Total_price"));

                cm.add(c);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cm;
    }

    public Command getCommandById(int id) {
        Command c = null;

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from Command where Id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next());
            c = new Command();
            c.setId(rs.getInt("Id"));
            c.setUser_id(rs.getInt("User_id"));

            c.setRef(rs.getString("ref"));
            c.setTotal_price(rs.getFloat("Total_price"));

            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c == null) {
            throw new RuntimeException("cart" + id + " introuvable");
        }
        return c;
    }

    public void UpdateCart(Command c) {
        try {
            PreparedStatement ps = cnx.prepareStatement
        ("update Command set user_id=?,ref=?,Total_price=?,Status=? where id =?");

            ps.setInt(1, c.getUser_id());

            ps.setString(2, c.getRef());
            ps.setFloat(3, c.getTotal_price());
            ps.setString(4, c.getStatus());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void deleteCommand(int id) {
        try {
			PreparedStatement ps=cnx.prepareStatement
					("delete from Command  where id=?"); 
			
			
			ps.setInt(1,id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}
    

}
