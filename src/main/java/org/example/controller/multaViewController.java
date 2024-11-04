package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.DAO.MultaDAO;
import org.example.DAO.MultaDAOImpl;
import org.example.model.Coche;
import org.example.model.Multa;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class multaViewController implements Initializable {

    @FXML
    private TableColumn<?, ?> columnIdFecha;

    @FXML
    private TableColumn<?, ?> columnIdMulta;

    @FXML
    private TableColumn<?, ?> columnIdPrecio;

    @FXML
    private TableView<Multa> idTableViewMulta;

    @FXML
    private DatePicker textfieldFecha;

    @FXML
    private TextField textfieldIdMulta;

    @FXML
    private TextField textfieldMatricula;

    @FXML
    private TextField textfieldPrecio;

    MultaDAO multaDAO = new MultaDAOImpl();

    SessionFactory sessionFactory;

    Session session;

    private ObservableList<Multa> listamultas;

    Coche cocheActual;

    @FXML
    void clickActualizar(ActionEvent event) {

    }

    @FXML
    void clickBorrar(ActionEvent event) {

    }

    @FXML
    void clickInsertar(ActionEvent event) {
        textfieldMatricula.setText(cocheActual.getMatricula());
        String precio = textfieldPrecio.getText();
        LocalDate fecha = textfieldFecha.getValue();




    }

    @FXML
    void clickLimpiar(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();


        columnIdMulta.setCellValueFactory(new PropertyValueFactory<>("id_matricula"));
        columnIdPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnIdFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        listamultas = FXCollections.observableArrayList(multaDAO.listarMultaCoche(session,cocheActual.getMatricula())); //falta poner el metodo
        idTableViewMulta.setItems(listamultas);
    }

    public void cogerCoche(Coche coche){
        this.cocheActual=coche;
    }
}
