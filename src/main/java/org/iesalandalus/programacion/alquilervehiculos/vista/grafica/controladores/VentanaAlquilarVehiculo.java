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

public class VentanaAlquilarVehiculo extends Controlador{
	private VistaGrafica vistaGrafica;

	private Vehiculo vehiculo;
	
	@FXML
	private Button btCerrar;
	
	@FXML
	private Button btAlquilar;
	
	@FXML
	private TextField tfCliente;	
	@FXML
	private DatePicker dpFechaAlquiler;
	
	@FXML
	private void initialize() {
		//Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();
		
		Controles.setInvalido(tfCliente);
		tfCliente.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfCliente));
		Controles.formatearSelectorFecha(dpFechaAlquiler);
	}
	
	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}
	
	@FXML
	private void alquilar() {
		
		if (tfCliente.getStyleClass().contains("valido") && dpFechaAlquiler.getValue() != null) {
			try {
				vistaGrafica.getControlador().insertar(new Alquiler(Cliente.getClienteConDni(tfCliente.getText()), vehiculo, dpFechaAlquiler.getValue()));
				Dialogos.mostrarDialogoInformacion("Insercion correcta", "Alquiler insertado correctamente", getEscenario());
				((Stage) btAlquilar.getParent().getScene().getWindow()).close();
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
