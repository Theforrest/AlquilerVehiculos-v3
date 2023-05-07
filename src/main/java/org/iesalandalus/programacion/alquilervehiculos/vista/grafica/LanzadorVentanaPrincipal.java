package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;


import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores.VentanaPrincipal;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LanzadorVentanaPrincipal extends Application{
	@Override
	public void start(Stage escenarioPrincipal) {
		try {
			VentanaPrincipal ventanaPrincipal = (VentanaPrincipal) Controladores.get("vistas/VentanaPrincipal.fxml", "", null);
			ventanaPrincipal.getEscenario().setOnCloseRequest(e -> confirmarSalir(ventanaPrincipal.getEscenario(), e));
			ventanaPrincipal.getEscenario().show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void comenzar() {
		launch(LanzadorVentanaPrincipal.class);
	}
	
	private void confirmarSalir(Stage escenario, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("salir", "Estás seguro de que quieres salir?", escenario)) {
			escenario.close();
		} else {
			e.consume();
		}
	}
}
