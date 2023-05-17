package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class VentanaDevolverAlquilerVehiculo extends Controlador {
	private VistaGrafica vistaGrafica;
	private Vehiculo vehiculo;

	@FXML
	private Button btCerrar;

	@FXML
	private Button btDevolver;

	@FXML
	private DatePicker dpFechaDevolucion;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();
		dpFechaDevolucion.valueProperty().addListener((observable, oldValue, newValue) -> deshabilitarBoton());
		btDevolver.setDisable(true);
	}

	private void deshabilitarBoton() {
		boolean invalido = (dpFechaDevolucion.getValue() == null);

		btDevolver.setDisable(invalido);

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	@FXML
	private void devolver() {

		if (dpFechaDevolucion.getValue() != null) {
			try {
				vistaGrafica.getControlador().devolver(vehiculo, dpFechaDevolucion.getValue());
				;
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

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

}
