package controller;

import DAO.CocheDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Coche;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.Alerta;
import util.HibernateUtil;
import util.Validar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MenuViewController implements Initializable {

    @FXML
    private ComboBox<String> comboboxTipo;

    @FXML
    private TableColumn<?, ?> idColumnMarca;

    @FXML
    private TableColumn<?, ?> idColumnMatricula;

    @FXML
    private TableColumn<?, ?> idColumnModelo;

    @FXML
    private TableColumn<?, ?> idColumnTipo;

    @FXML
    private TableView<Coche> idTableView;

    @FXML
    private TextField textfieldMarca;

    @FXML
    private TextField textfieldMatricula;

    @FXML
    private TextField textfieldModelo;
    SessionFactory sessionFactory;
    Session session;
    Coche cocheseleccionado;



    CocheDaoImpl cocheDao = new CocheDaoImpl();


    private final ArrayList<String> listaTipos = new ArrayList<>(Arrays.asList("Turismo", "Camion", "Monovolumen", "Deportivo"));

    private ObservableList<Coche> listacoches;


    @FXML
    void clickFilaTableView(MouseEvent event) {
        if (idTableView.getSelectionModel().getSelectedItem()!=null){
            cocheseleccionado = idTableView.getSelectionModel().getSelectedItem();
            textfieldMatricula.setText(cocheseleccionado.getMatricula());
            textfieldModelo.setText(cocheseleccionado.getModelo());
            textfieldMarca.setText(cocheseleccionado.getMarca());
            comboboxTipo.setValue(cocheseleccionado.getTipo());
        }
    }//clickFilaTableView

    @FXML
    void onClickCrear(ActionEvent event) {
        String matricula = textfieldMatricula.getText();
        if (!Validar.validarMatriculaEuropea_Exp(matricula)){
            Alerta.mostrarAlerta("Escribe una matricula correcta");
        }else {
            String modelo = textfieldModelo.getText();
            String marca = textfieldMarca.getText();
            String tipo = comboboxTipo.getValue();
            Coche coche = new Coche(matricula, modelo, marca, tipo);
            if (cocheDao.crearCoche(session, coche)){
                Alerta.mostrarAlerta("Coche creado correctamente");
                listacoches.addAll(coche);
            }else {
                Alerta.mostrarAlerta("Fallo al crear coche");
            }
        }
    }//clickCrear

    @FXML
    void onClickEliminar(ActionEvent event) {
        if (cocheDao.eliminarCoche(session, cocheseleccionado)){
            Alerta.mostrarAlerta("Coche eliminado correctamente");
            listacoches.remove(cocheseleccionado);
        }else {
            Alerta.mostrarAlerta("Fallo al eliminar coche");
        }
    }//clickEliminar

    @FXML
    void onClickLimpiar(ActionEvent event) {
        textfieldMatricula.clear();
        textfieldMarca.clear();
        textfieldModelo.clear();
        comboboxTipo.setValue(null);
    }//clickLimpiar

    @FXML
    void onClickModificar(ActionEvent event) {
        int index = listacoches.indexOf(cocheseleccionado);
        if (!Validar.validarMatriculaEuropea_Exp(textfieldMatricula.getText())){
            Alerta.mostrarAlerta("Introduce una matricula correcta");
        }else {
            cocheseleccionado.setMatricula(textfieldMatricula.getText());
            cocheseleccionado.setModelo(textfieldModelo.getText());
            cocheseleccionado.setMarca(textfieldMarca.getText());
            cocheseleccionado.setTipo(comboboxTipo.getValue());

            if (cocheDao.actualizarCoche(session, cocheseleccionado)){
                Alerta.mostrarAlerta("Coche modificado correctamente");
                listacoches.set(index, cocheseleccionado);
            }else {
                Alerta.mostrarAlerta("Fallo al actualizar coche");
            }

        }
    }//clickModificar

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         sessionFactory = HibernateUtil.getSessionFactory();
         session = HibernateUtil.getSession();

        comboboxTipo.getItems().addAll(listaTipos);

        idColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        idColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        idColumnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        idColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        listacoches = FXCollections.observableArrayList(cocheDao.listarCoches(session));
        idTableView.setItems(listacoches);

    }//initialize
}
