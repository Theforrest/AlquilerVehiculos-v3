package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VentanaInsertarFurgoneta extends Controlador {
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
	private TextField tfPlazas;
	@FXML
	private TextField tfPma;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		Controles.setInvalido(tfMatricula);
		tfMatricula.textProperty().addListener(
				(observable, oldValue, newValue) -> deshabilitarBoton(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.setInvalido(tfMarca);
		tfMarca.textProperty().addListener(
				(observable, oldValue, newValue) -> deshabilitarBoton(Vehiculo.ER_MARCA, tfMarca));
		Controles.setInvalido(tfModelo);
		tfModelo.textProperty().addListener(
				(observable, oldValue, newValue) -> deshabilitarBoton(ER_MODELO, tfModelo));
		Controles.setInvalido(tfPlazas);
		tfPlazas.textProperty().addListener(
				(observable, oldValue, newValue) -> deshabilitarBoton(ER_NUMERO, tfPlazas));
		Controles.setInvalido(tfPma);
		tfPma.textProperty().addListener(
				(observable, oldValue, newValue) -> deshabilitarBoton(ER_NUMERO, tfPma));
		btInsertar.setDisable(true);

	}
	
	private void deshabilitarBoton(String er, TextField campoTexto) {
		Controles.validarCampoTexto(er, campoTexto);

		boolean invalido = !(tfMatricula.getStyleClass().contains("valido") && tfMarca.getStyleClass().contains("valido")
				&& tfModelo.getStyleClass().contains("valido") && tfPlazas.getStyleClass().contains("valido") && tfPma.getStyleClass().contains("valido"));

		btInsertar.setDisable(invalido);

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	@FXML
	private void insertar() {

		
			try {
				vistaGrafica.getControlador().insertar(
						new Furgoneta(tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfPma.getText()),
								Integer.parseInt(tfPlazas.getText()), tfMatricula.getText()));
				Dialogos.mostrarDialogoInformacion("Insercion correcta", "Furgoneta insertada correctamente",
						getEscenario());
				((Stage) btInsertar.getParent().getScene().getWindow()).close();
			} catch (OperationNotSupportedException | IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
			}
		

	}

}
