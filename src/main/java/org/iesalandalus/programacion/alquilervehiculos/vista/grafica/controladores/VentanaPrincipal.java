package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VentanaPrincipal extends Controlador{
	@FXML
	private Button btPulsame;
	@FXML
	private Button btVehiculos;
	
	@FXML
	private void abrirVentanaClientes(ActionEvent event) {
		VentanaClientes ventanaClientes = (VentanaClientes) Controladores.get("vistas/VentanaClientes.fxml", "", getEscenario());
		Stage escenario = (Stage)((Node)event.getSource()).getScene().getWindow();
		escenario.setScene(ventanaClientes.getEscenario().getScene());
		escenario.setResizable(false);
		escenario.show();
	}
	@FXML
	private void abrirVentanaVehiculos(ActionEvent event) {
		VentanaVehiculos ventanaVehiculos = (VentanaVehiculos) Controladores.get("vistas/VentanaVehiculos.fxml", "", getEscenario());
		Stage escenario = (Stage)((Node)event.getSource()).getScene().getWindow();
		escenario.setScene(ventanaVehiculos.getEscenario().getScene());
		escenario.setResizable(false);
		escenario.show();
	}
	@FXML
	private void abrirVentanaAlquileres(ActionEvent event) {
		VentanaAlquileres ventanaAlquileres = (VentanaAlquileres) Controladores.get("vistas/VentanaAlquileres.fxml", "", getEscenario());
		Stage escenario = (Stage)((Node)event.getSource()).getScene().getWindow();
		escenario.setScene(ventanaAlquileres.getEscenario().getScene());
		escenario.setResizable(false);
		escenario.show();
	}

}
