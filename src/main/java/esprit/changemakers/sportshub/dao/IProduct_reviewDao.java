package esprit.changemakers.sportshub.dao;

import esprit.changemakers.sportshub.entities.Product_review;

import java.util.List;

public interface IProduct_reviewDao {
    public void addReview(Product_review R);
    public List<Product_review> listReviewProduit();
    public List<Product_review> ReviewParUser(int user_id);
    public List<Product_review> ReviewParProduit(int product_id);
    public Product_review getReviewProduitById(int id);
    public void UpdateReview(Product_review R);
    public void deleteReview(int id);
}
