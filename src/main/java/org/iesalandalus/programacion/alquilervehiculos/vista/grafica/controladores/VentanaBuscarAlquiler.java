package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VentanaBuscarAlquiler extends Controlador {
	private VistaGrafica vistaGrafica;
	private Alquiler alquiler;

	@FXML
	private Button btCerrar;

	@FXML
	private Label lbCliente;
	@FXML
	private Label lbVehiculo;
	@FXML
	private Label lbFechaAlquiler;
	@FXML
	private Label lbFechaDevolucion;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

	}
	


	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;

		lbCliente.setText(String.format("%s", alquiler.getCliente()));
		lbVehiculo.setText(String.format("%s", alquiler.getVehiculo()));
		lbFechaAlquiler.setText(String.format("%s", alquiler.getFechaAlquiler()));
		LocalDate fechaDevolucion = alquiler.getFechaDevolucion();
		lbFechaDevolucion.setText(String.format("%s", (fechaDevolucion == null) ? "No devuelto" : fechaDevolucion));

	}

	@FXML
	private void borrar() {

		try {
			vistaGrafica.getControlador().borrar(alquiler);
			Dialogos.mostrarDialogoInformacion("Borrado correcto", "Alquiler borrado correctamente", getEscenario());
			cerrar();
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}

	}
}
