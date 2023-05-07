package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaGrafica extends Vista{
	
	private static final VistaGrafica instancia = new VistaGrafica();
	
	public static VistaGrafica getInstancia() {
		return instancia;
	}
	

	@Override
	public void comenzar() {
		LanzadorVentanaPrincipal.comenzar();
		getControlador().terminar();
	}

	@Override
	public void terminar() {
		System.out.printf("Bye bye");
		
	}

}
