package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class VentanaAcercaDe extends Controlador {

	@FXML
	private Button btCerrar;
	@FXML
	private Circle circulo;

	@FXML
	private void initialize() {
		// Inicializando la vista
		circulo.setFill(
				new ImagePattern(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/Foto.jpg"))));

	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

}
