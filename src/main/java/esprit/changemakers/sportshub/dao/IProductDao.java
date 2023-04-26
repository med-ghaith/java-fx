package esprit.changemakers.sportshub.dao;


import esprit.changemakers.sportshub.entities.Product;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Jozef
 */
public interface IProductDao<E> {

    public void addProduit(Product P);
    public List<Product> listProduit();
    public List<Product> ProduitParNom(String MC);
    public List<Product> ProduitParPrix(Float price);
    public List<Product> ProduitParcategorie(String cat);
    public Product getProduitById(int Id);
    public void UpdateProduit(Product P);
    public void deleteProduit(int id);





}
