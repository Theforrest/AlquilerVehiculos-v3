package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VentanaInsertarTurismo extends Controlador {
	private VistaGrafica vistaGrafica;
	private static final String ER_MODELO = ".+";
	private static final String ER_NUMERO = "\\d+";

	@FXML
	private Button btCerrar;

	@FXML
	private Button btInsertar;

	@FXML
	private TextField tfMatricula;
	@FXML
	private TextField tfMarca;
	@FXML
	private TextField tfModelo;
	@FXML
	private TextField tfCilindrada;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		Controles.setInvalido(tfMatricula);
		tfMatricula.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.setInvalido(tfMarca);
		tfMarca.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(Vehiculo.ER_MARCA, tfMarca));
		Controles.setInvalido(tfModelo);
		tfModelo.textProperty().addListener((observable, oldValue, newValue) -> deshabilitarBoton(ER_MODELO, tfModelo));
		Controles.setInvalido(tfCilindrada);
		tfCilindrada.textProperty()
				.addListener((observable, oldValue, newValue) -> deshabilitarBoton(ER_NUMERO, tfCilindrada));
		btInsertar.setDisable(true);

	}

	private void deshabilitarBoton(String er, TextField campoTexto) {
		Controles.validarCampoTexto(er, campoTexto);

		boolean invalido = !(tfMatricula.getStyleClass().contains("valido")
				&& tfMarca.getStyleClass().contains("valido") && tfModelo.getStyleClass().contains("valido")
				&& tfCilindrada.getStyleClass().contains("valido"));

		btInsertar.setDisable(invalido);

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	@FXML
	private void insertar() {

		try {
			vistaGrafica.getControlador().insertar(new Turismo(tfMarca.getText(), tfModelo.getText(),
					Integer.parseInt(tfCilindrada.getText()), tfMatricula.getText()));
			Dialogos.mostrarDialogoInformacion("Insercion correcta", "Turismo insertado correctamente", getEscenario());
			((Stage) btInsertar.getParent().getScene().getWindow()).close();
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}

	}

}
