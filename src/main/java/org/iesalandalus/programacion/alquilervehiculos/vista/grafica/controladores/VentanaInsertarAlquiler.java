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

public class VentanaInsertarAlquiler extends Controlador {
	private VistaGrafica vistaGrafica;

	@FXML
	private Button btCerrar;

	@FXML
	private Button btInsertar;

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
		tfMatricula.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.setInvalido(tfDni);
		tfDni.textProperty().addListener((observable, oldValue, newValue) -> deshabilitarBoton(Cliente.ER_DNI, tfDni));

	}

	private void deshabilitarBoton(String er, TextField campoTexto) {
		Controles.validarCampoTexto(er, campoTexto);

		boolean invalido = !(tfMatricula.getStyleClass().contains("valido") && tfDni.getStyleClass().contains("valido")
				&& dpFechaAlquiler.getValue() != null);

		btInsertar.setDisable(invalido);

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	@FXML
	private void insertar() {

		try {
			vistaGrafica.getControlador().insertar(new Alquiler(Cliente.getClienteConDni(tfDni.getText()),
					Vehiculo.getVehiculoConMatricula(tfMatricula.getText()), dpFechaAlquiler.getValue()));
			Dialogos.mostrarDialogoInformacion("Insercion correcta", "Alquiler insertado correctamente",
					getEscenario());
			((Stage) btInsertar.getParent().getScene().getWindow()).close();
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}

	}

}
