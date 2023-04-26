package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.controller.Modulegestionsalle.InterfaceSubscriptionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class intHomeController {
    private Parent root;
    private FXMLLoader loader;
    public void Loadcoaches(ActionEvent actionEvent) throws IOException {
        loader=new FXMLLoader(getClass().getResource("/views/Planning.fxml"));
        root=loader.load();
        HomeController.getInstance().getBorderPane().setCenter(root);
    }

    public void Loadgyms(ActionEvent actionEvent) throws IOException {
        loader=new FXMLLoader(getClass().getResource("../../../../views/Modulegestionsalle/Listegym.fxml"));
        root=loader.load();
        HomeController.getInstance().getBorderPane().setCenter(root);
    }

    public void Loadshop(ActionEvent actionEvent) throws IOException {
        loader=new FXMLLoader(getClass().getResource("../../../../views/ShopProduct.fxml"));
        root=loader.load();
        HomeController.getInstance().getBorderPane().setCenter(root);
    }
}
