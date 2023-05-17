package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.io.IOException;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class VentanaInsertarVehiculo extends Controlador {
	@FXML
	private ChoiceBox<String> cbTipoVehiculo;

	@FXML
	private BorderPane bpInsertar;
	@FXML
	private AnchorPane apInsertar;

	@FXML
	private void initialize() {
		cbTipoVehiculo.getSelectionModel().selectedItemProperty()
				.addListener((ob, oldValue, newValue) -> cambiarTipoVehiculo(newValue));
		cbTipoVehiculo.setItems(FXCollections.observableArrayList("Turismo", "Autobus", "Furgoneta"));
		cbTipoVehiculo.setValue("Turismo");

	}

	private void cambiarTipoVehiculo(String tipoVehiculo) {

		FXMLLoader cargador = new FXMLLoader(
				LocalizadorRecursos.class.getResource(String.format("vistas/VentanaInsertar%s.fxml", tipoVehiculo)));
		Parent raiz = null;

		try {
			raiz = cargador.load();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		bpInsertar.setCenter(raiz);

	}
}
