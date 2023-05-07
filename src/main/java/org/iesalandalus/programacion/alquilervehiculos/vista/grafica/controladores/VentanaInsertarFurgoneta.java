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

public class VentanaInsertarFurgoneta extends Controlador{
	private VistaGrafica vistaGrafica;
	
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
		//Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();
		
		Controles.setInvalido(tfMatricula);
		tfMatricula.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.setInvalido(tfMarca);
		tfMarca.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Vehiculo.ER_MARCA, tfMarca));
		Controles.setValido(tfModelo);
	}
	
	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}
	
	@FXML
	private void insertar() {
		
		if (tfMatricula.getStyleClass().contains("valido") && tfMarca.getStyleClass().contains("valido") && tfModelo.getStyleClass().contains("valido")) {
			try {
				vistaGrafica.getControlador().insertar(new Furgoneta(tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfPma.getText()), Integer.parseInt(tfPlazas.getText()), tfMatricula.getText()));
				Dialogos.mostrarDialogoInformacion("Insercion correcta", "Furgoneta insertada correctamente", getEscenario());
				((Stage) btInsertar.getParent().getScene().getWindow()).close();
			} catch (OperationNotSupportedException | IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
			}
		} else {
			Dialogos.mostrarDialogoError("ERROR", "Todos los campos deben de ser validos", getEscenario());
		}
		
	}

}
