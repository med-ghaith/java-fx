package esprit.changemakers.sportshub.dao;



import esprit.changemakers.sportshub.entities.Cart;
import esprit.changemakers.sportshub.entities.Product;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Jozef
 */
public interface ICartDao<E> {

   
   public void addCart(Cart c);
	public List<Cart> listCart();
	public List<Cart> CartParUser(int user_id);
	public Cart getCartById(int id);
	public void UpdateCart(Cart c );
	public void deleteCart(int id);




}
