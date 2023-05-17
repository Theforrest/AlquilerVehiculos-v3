package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VentanaAlquilarCliente extends Controlador {
	private VistaGrafica vistaGrafica;

	private Cliente cliente;

	@FXML
	private Button btCerrar;

	@FXML
	private Button btAlquilar;

	@FXML
	private TextField tfVehiculo;
	@FXML
	private DatePicker dpFechaAlquiler;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		Controles.setInvalido(tfVehiculo);
		tfVehiculo.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(Vehiculo.ER_MATRICULA, tfVehiculo));
		btAlquilar.setDisable(true);
	}

	private void deshabilitarBoton(String er, TextField campoTexto) {
		Controles.validarCampoTexto(er, campoTexto);

		boolean invalido = !(tfVehiculo.getStyleClass().contains("valido") && dpFechaAlquiler.getValue() != null);

		btAlquilar.setDisable(invalido);

	}
	
	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	@FXML
	private void alquilar() {

		
			try {
				vistaGrafica.getControlador().insertar(new Alquiler(cliente,
						Vehiculo.getVehiculoConMatricula(tfVehiculo.getText()), dpFechaAlquiler.getValue()));
				Dialogos.mostrarDialogoInformacion("Insercion correcta", "Alquiler insertado correctamente",
						getEscenario());
				((Stage) btAlquilar.getParent().getScene().getWindow()).close();
			} catch (OperationNotSupportedException | IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
			}
		

	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
