package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VentanaInsertarCliente extends Controlador {
	private VistaGrafica vistaGrafica;

	@FXML
	private Button btCerrar;

	@FXML
	private Button btInsertar;

	@FXML
	private TextField tfDni;
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfTelefono;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		Controles.setInvalido(tfDni);
		tfDni.textProperty().addListener((observable, oldValue, newValue) -> deshabilitarBoton(Cliente.ER_DNI, tfDni));
		Controles.setInvalido(tfNombre);
		tfNombre.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(Cliente.ER_NOMBRE, tfNombre));
		Controles.setInvalido(tfTelefono);
		tfTelefono.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(Cliente.ER_TELEFONO, tfTelefono));
		btInsertar.setDisable(true);
	}

	private void deshabilitarBoton(String er, TextField campoTexto) {
		Controles.validarCampoTexto(er, campoTexto);

		boolean invalido = !(tfDni.getStyleClass().contains("valido") && tfNombre.getStyleClass().contains("valido")
				&& tfTelefono.getStyleClass().contains("valido"));

		btInsertar.setDisable(invalido);

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	@FXML
	private void insertar() {

		try {
			vistaGrafica.getControlador()
					.insertar(new Cliente(tfNombre.getText(), tfDni.getText(), tfTelefono.getText()));
			Dialogos.mostrarDialogoInformacion("Insercion correcta", "Cliente insertado correctamente", getEscenario());
			((Stage) btInsertar.getParent().getScene().getWindow()).close();
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}

	}

}
