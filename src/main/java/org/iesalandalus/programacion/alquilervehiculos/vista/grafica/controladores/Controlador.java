package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javafx.stage.Stage;

public class Controlador {

	private Stage escenario;
	
	public Stage getEscenario() {
		return escenario;
	}
	
	public void setEscenario(Stage escenario) {
		if (escenario == null) {
			throw new NullPointerException("ERROR: El escenario no puede ser nulo.");
		}
		this.escenario = escenario;
	}
	
}
