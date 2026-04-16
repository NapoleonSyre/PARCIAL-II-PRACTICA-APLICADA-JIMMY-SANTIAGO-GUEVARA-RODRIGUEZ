package co.edu.poli.examen2_Guevara.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.edu.poli.examen2_Guevara.modelo.Apartamento;
import co.edu.poli.examen2_Guevara.modelo.Casa;
import co.edu.poli.examen2_Guevara.modelo.Inmueble;
import co.edu.poli.examen2_Guevara.modelo.Propietario;
import co.edu.poli.examen2_Guevara.servicios.DAOInmueble;
import co.edu.poli.examen2_Guevara.servicios.DAOPropietario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ControlFormInmueble {

    @FXML
    private Button bttConsulta;
    @FXML
    private TextField txtInmueble1;
    @FXML
    private TextArea txtAreaResultado;
    @FXML
    private Button bttCreacion;
    @FXML
    private TextField txtInmueble2;
    @FXML
    private DatePicker datepk1;
    @FXML
    private ComboBox<Propietario> cmbPropietario;
    @FXML
    private RadioButton radio1; // Apartamento
    @FXML
    private RadioButton radio2; // Casa
    @FXML
    private ToggleGroup tipo;
    @FXML
    private TextField txtValorExtra; // numeroPiso o cantidadPisos

    private DAOInmueble daoInmueble;
    private DAOPropietario daoPropietario;

    @FXML
    private void initialize() {
        try {
            daoInmueble = new DAOInmueble();
            daoPropietario = new DAOPropietario();

            datepk1.setValue(LocalDate.now());

            List<Propietario> lista = daoPropietario.readall();
            cmbPropietario.getItems().setAll(lista);

        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }

        txtInmueble1.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validarSoloNumeros(txtInmueble1);
            }
        });

        txtInmueble2.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validarSoloNumeros(txtInmueble2);
            }
        });

        txtValorExtra.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validarSoloNumeros(txtValorExtra);
            }
        });
    }

    @FXML
    private void pressConsulta(ActionEvent event) {
        txtAreaResultado.setText("");

        if (!txtInmueble1.getText().isBlank()) {
            try {
                Inmueble i = daoInmueble.readone(txtInmueble1.getText());

                if (i != null) {
                    txtAreaResultado.setText(i.toString());
                } else {
                    mostrarAlerta("No existe el número de inmueble");
                }
            } catch (Exception e) {
                mostrarAlerta(e.getMessage());
            }
        } else {
            mostrarAlerta("Ingrese número de inmueble");
        }
    }

    @FXML
    private void pressCreacion(ActionEvent event) {

        String numero = txtInmueble2.getText().trim();
        if (numero.isEmpty()) {
            mostrarAlerta("⚠ Ingrese el número del inmueble.");
            return;
        }

        if (datepk1.getValue() == null) {
            mostrarAlerta("⚠ Seleccione la fecha de compra.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaCompra = datepk1.getValue().format(formatter);

        Propietario propietario = cmbPropietario.getValue();
        if (propietario == null) {
            mostrarAlerta("⚠ Seleccione un propietario.");
            return;
        }

        String valorExtra = txtValorExtra.getText().trim();
        if (valorExtra.isEmpty()) {
            mostrarAlerta("⚠ Ingrese el valor correspondiente.");
            return;
        }

        int valor;
        try {
            valor = Integer.parseInt(valorExtra);
        } catch (NumberFormatException e) {
            mostrarAlerta("⚠ El valor debe ser numérico.");
            return;
        }

        Inmueble nuevo;

        if (radio1.isSelected()) {
            nuevo = new Apartamento(numero, fechaCompra, true, propietario, valor);
        } else {
            nuevo = new Casa(numero, fechaCompra, true, propietario, valor);
        }

        try {
            String resultado = daoInmueble.create(nuevo);
            mostrarAlerta(resultado);

            if (resultado != null && (resultado.startsWith("✔") || resultado.toLowerCase().contains("correctamente"))) {
                limpiarFormCrear();
            }
        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarFormCrear() {
        txtInmueble2.clear();
        txtValorExtra.clear();
        datepk1.setValue(null);
        cmbPropietario.setValue(null);
        radio1.setSelected(true);
    }

    private void validarSoloNumeros(TextField txt) {
        String texto = txt.getText().trim();

        if (!texto.isBlank()) {
            if (!texto.matches("\\d+")) {
                txtAreaResultado.setText("");
                txt.setStyle("-fx-border-color: red;");
                mostrarAlerta("Solo números son permitidos!");
                txt.setText("");
                Platform.runLater(() -> txt.requestFocus());
            } else {
                txt.setStyle("");
            }
        } else {
            txt.setStyle("");
        }
    }
}