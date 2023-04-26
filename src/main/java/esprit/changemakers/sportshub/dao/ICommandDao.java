/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.dao;

import esprit.changemakers.sportshub.entities.Cart;
import esprit.changemakers.sportshub.entities.Command;
import java.util.List;

/**
 *
 * @author bilel abbassi
 */
public interface ICommandDao {
    
    public void addCommand(Command c);
	public List<Command> listCommand();
	public List<Command> CommandParUser(int user_id);
	public Command getCommandById(int id);
	public void UpdateCart(Command c );
	public void deleteCommand(int id);
    
    
}
