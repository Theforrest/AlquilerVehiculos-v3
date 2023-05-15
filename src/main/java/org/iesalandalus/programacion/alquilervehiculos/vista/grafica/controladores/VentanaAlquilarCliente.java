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

public class VentanaAlquilarCliente extends Controlador{
	private VistaGrafica vistaGrafica;
	private static final String ER_MATRICULA = "\\d{4}[QWRTYPSDFGHJKLZXCVBNM]{3}";

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
		//Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();
		
		Controles.setInvalido(tfVehiculo);
		tfVehiculo.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(ER_MATRICULA, tfVehiculo));
	}
	
	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}
	
	@FXML
	private void alquilar() {
		
		if (tfVehiculo.getStyleClass().contains("valido") && dpFechaAlquiler.getValue() != null) {
			try {
				vistaGrafica.getControlador().insertar(new Alquiler(cliente, Vehiculo.getVehiculoConMatricula(tfVehiculo.getText()), dpFechaAlquiler.getValue()));
				Dialogos.mostrarDialogoInformacion("Insercion correcta", "Alquiler insertado correctamente", getEscenario());
				((Stage) btAlquilar.getParent().getScene().getWindow()).close();
			} catch (OperationNotSupportedException | IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
			}
		} else {
			Dialogos.mostrarDialogoError("ERROR", "Todos los campos deben de ser validos", getEscenario());
		}
		
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
