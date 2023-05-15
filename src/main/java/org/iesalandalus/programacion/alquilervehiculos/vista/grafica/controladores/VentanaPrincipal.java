package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VentanaPrincipal extends Controlador{
	
	
	
	@FXML
	private Button btPulsame;
	@FXML
	private Button btVehiculos;
	
	private static Scene escenaPrincipal;
	
	public static void setEscenaPrincipal(Scene escena) {
		if (escenaPrincipal == null) {
		escenaPrincipal = escena;
		}
	}
	public static Scene getEscenaPrincipal() {
		return escenaPrincipal;
	}
	
	@FXML
	private void abrirVentanaClientes(ActionEvent event) {
		VentanaClientes ventanaClientes = (VentanaClientes) Controladores.get("vistas/VentanaClientes.fxml", "", getEscenario());
		VentanaClientes.setEscenaPrincipal(ventanaClientes.getEscenario().getScene());
		Stage escenario = (Stage)((Node)event.getSource()).getScene().getWindow();
		escenario.setScene(VentanaClientes.getEscenaPrincipal());
		escenario.setResizable(false);
		escenario.show();
	}
	@FXML
	private void abrirVentanaVehiculos(ActionEvent event) {
		VentanaVehiculos ventanaVehiculos = (VentanaVehiculos) Controladores.get("vistas/VentanaVehiculos.fxml", "", getEscenario());
		VentanaVehiculos.setEscenaPrincipal(ventanaVehiculos.getEscenario().getScene());
		Stage escenario = (Stage)((Node)event.getSource()).getScene().getWindow();
		escenario.setScene(VentanaVehiculos.getEscenaPrincipal());
		escenario.setResizable(false);
		escenario.show();
	}
	@FXML
	private void abrirVentanaAlquileres(ActionEvent event) {
		VentanaAlquileres ventanaAlquileres = (VentanaAlquileres) Controladores.get("vistas/VentanaAlquileres.fxml", "", getEscenario());
		VentanaAlquileres.setEscenaPrincipal(ventanaAlquileres.getEscenario().getScene());
		Stage escenario = (Stage)((Node)event.getSource()).getScene().getWindow();
		escenario.setScene(VentanaAlquileres.getEscenaPrincipal());
		escenario.setResizable(false);
		escenario.show();
	}

}
