package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

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

public class VentanaBuscadorAlquiler extends Controlador {
	private VistaGrafica vistaGrafica;
	private Alquiler alquiler;

	@FXML
	private Button btCerrar;

	@FXML
	private Button btBuscar;

	@FXML
	private TextField tfMatricula;
	@FXML
	private TextField tfDni;
	@FXML
	private DatePicker dpFechaAlquiler;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		Controles.setInvalido(tfMatricula);
		tfMatricula.textProperty().addListener(
				(observable, oldValue, newValue) -> deshabilitarBoton(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.setInvalido(tfDni);
		tfDni.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(Cliente.ER_DNI, tfDni));

	}
	
	private void deshabilitarBoton(String er, TextField campoTexto) {
		Controles.validarCampoTexto(er, campoTexto);

		boolean invalido = !(tfMatricula.getStyleClass().contains("valido") && tfDni.getStyleClass().contains("valido")
				&& dpFechaAlquiler.getValue() != null);

		btBuscar.setDisable(invalido);

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	@FXML
	private void buscar() {

		

			alquiler = vistaGrafica.getControlador().buscar(new Alquiler(Cliente.getClienteConDni(tfDni.getText()),
					Vehiculo.getVehiculoConMatricula(tfMatricula.getText()), dpFechaAlquiler.getValue()));
			if (alquiler != null) {
				cerrar();

			} else {
				Dialogos.mostrarDialogoError("Cliente no encontrado", "No existe ningun alquiler con esos datos", null);
			}

		

	}

	public Alquiler getAlquiler() {
		return alquiler;
	}

}
