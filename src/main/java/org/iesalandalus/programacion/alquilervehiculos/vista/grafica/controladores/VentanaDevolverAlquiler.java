package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VentanaDevolverAlquiler extends Controlador {
	private VistaGrafica vistaGrafica;

	@FXML
	private Button btCerrar;

	@FXML
	private TextField tfDni;
	@FXML
	private TextField tfMatricula;

	@FXML
	private Button btDevolver;

	@FXML
	private ChoiceBox<String> cbTipoDevolucion;

	@FXML
	private DatePicker dpFechaDevolucion;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		cbTipoDevolucion.getSelectionModel().selectedItemProperty()
				.addListener((ob, oldValue, newValue) -> cambiarTipoDevolucion(newValue));
		cbTipoDevolucion.setItems(FXCollections.observableArrayList("Cliente", "Vehiculo"));
		cbTipoDevolucion.setValue("Cliente");

		Controles.setInvalido(tfDni);
		tfDni.textProperty()
				.addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));

		Controles.setInvalido(tfMatricula);
		tfMatricula.textProperty().addListener(
				(observable, oldValue, newValue) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	private void cambiarTipoDevolucion(String tipoVehiculo) {
		if (tipoVehiculo.equals("Cliente")) {
			tfDni.setDisable(false);
			tfMatricula.setDisable(true);
		} else if (tipoVehiculo.equals("Vehiculo")) {
			tfDni.setDisable(true);
			tfMatricula.setDisable(false);
		}

	}

	@FXML
	private void devolver() {

		if (cbTipoDevolucion.getValue().equals("Cliente") && tfDni.getStyleClass().contains("valido")
				&& dpFechaDevolucion.getValue() != null) {
			try {
				vistaGrafica.getControlador().devolver(Cliente.getClienteConDni(tfDni.getText()),
						dpFechaDevolucion.getValue());
				Dialogos.mostrarDialogoInformacion("Devolucion correcta", "Alquiler devuelto correctamente",
						getEscenario());
				((Stage) btDevolver.getParent().getScene().getWindow()).close();
			} catch (OperationNotSupportedException | IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
			}
		} else if (cbTipoDevolucion.getValue().equals("Vehiculo") && tfMatricula.getStyleClass().contains("valido")
				&& dpFechaDevolucion.getValue() != null) {
			try {
				vistaGrafica.getControlador().devolver(Vehiculo.getVehiculoConMatricula(tfMatricula.getText()),
						dpFechaDevolucion.getValue());
				Dialogos.mostrarDialogoInformacion("Devolucion correcta", "Alquiler devuelto correctamente",
						getEscenario());
				((Stage) btDevolver.getParent().getScene().getWindow()).close();
			} catch (OperationNotSupportedException | IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
			}

		} else {
			Dialogos.mostrarDialogoError("ERROR", "Todos los campos deben de ser validos", getEscenario());
		}

	}

}
