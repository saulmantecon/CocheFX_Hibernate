package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.DAO.MultaDAO;
import org.example.DAO.MultaDAOImpl;
import org.example.model.Coche;
import org.example.model.Multa;
import org.example.util.Alerta;
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

    private ObservableList<Multa> listamultas = FXCollections.observableArrayList();

    Coche cocheActual;
    Multa multaActual;



    @FXML
    public void clickTableViewMulta(MouseEvent event) {
        if (idTableViewMulta.getSelectionModel().getSelectedItem()!=null){
            multaActual = idTableViewMulta.getSelectionModel().getSelectedItem();
            textfieldMatricula.setText(multaActual.getCoche().getMatricula());
            textfieldIdMulta.setText(String.valueOf(multaActual.getId_multa()));
            textfieldPrecio.setText(String.valueOf(multaActual.getPrecio()));
            textfieldFecha.setValue(multaActual.getFecha());
            textfieldIdMulta.setEditable(false);
        }
    }


    @FXML
    void clickActualizar(ActionEvent event) {
        int index = listamultas.indexOf(multaActual);
        multaActual.setId_multa(Integer.parseInt(textfieldIdMulta.getText()));
        multaActual.setPrecio(Double.parseDouble(textfieldPrecio.getText()));
        multaActual.setFecha(textfieldFecha.getValue());
        if (multaDAO.actualizarMulta(session,multaActual)){
            Alerta.mostrarAlerta("Multa modificada correctamente");
            listamultas.set(index,multaActual);
            idTableViewMulta.refresh();
        }else {
            Alerta.mostrarAlerta("Error al modificar la multa");
        }
    }

    @FXML
    void clickBorrar(ActionEvent event) {
        if (multaDAO.eliminarMulta(session,multaActual)){
            Alerta.mostrarAlerta("Multa eliminada correctamente");
            listamultas.remove(multaActual);
        }else {
            Alerta.mostrarAlerta("Error al eliminar la multa");
        }

    }

    @FXML
    void clickInsertar(ActionEvent event) {
        String matricula = cocheActual.getMatricula();
        textfieldMatricula.setText(matricula);
       // int id_multa= Integer.parseInt(textfieldIdMulta.getText());
        double precio = Double.parseDouble(textfieldPrecio.getText());
        LocalDate fecha = textfieldFecha.getValue();
        Multa multa = new Multa(precio,fecha,cocheActual);
        if (multaDAO.crearMulta(session,multa)){
            Alerta.mostrarAlerta("multa creada correctamente");
            listamultas.add(multa);
        }else {
            Alerta.mostrarAlerta("no se creó la multa");
        }

    }

    @FXML
    void clickLimpiar(ActionEvent event) {
        textfieldPrecio.clear();
        textfieldFecha.setValue(null);
        textfieldIdMulta.setEditable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();


        columnIdMulta.setCellValueFactory(new PropertyValueFactory<>("id_multa"));
        columnIdPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnIdFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

    }

    public void cogerCoche(Coche coche){
        this.cocheActual=coche;
        listamultas = FXCollections.observableArrayList(multaDAO.listarMultaCoche(session,cocheActual.getMatricula()));
        idTableViewMulta.setItems(listamultas);
        textfieldMatricula.setEditable(false);
        textfieldMatricula.setText(cocheActual.getMatricula());
    }
}
