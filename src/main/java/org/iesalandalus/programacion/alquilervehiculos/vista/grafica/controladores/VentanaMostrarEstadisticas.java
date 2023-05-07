package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.Map;
import java.util.TreeMap;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VentanaMostrarEstadisticas extends Controlador{
	private VistaGrafica vistaGrafica;
	
	@FXML
	private Button btCerrar;
	
	@FXML
	private PieChart pcEstadisticas;
	
	@FXML
	private void initialize() {
		//Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();
		
		for (Map.Entry<String, Integer> entry : inicializarEstadisticas().entrySet()) {
			pcEstadisticas.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
		}
		
		
	}
	
	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}
	
	
	private Map<String, Integer> inicializarEstadisticas() {
		Map<String, Integer> estadisticas = new TreeMap<>();

		int cantidadTurismos = 0;
		int cantidadFurgonetas = 0;
		int cantidadAutobuses = 0;
		
		for (Alquiler alquiler : vistaGrafica.getControlador().getAlquileres()) {
			Vehiculo vehiculo = alquiler.getVehiculo();
			
			if (vehiculo instanceof Turismo turismo) {
				cantidadTurismos += 1;
			} else if (vehiculo instanceof Autobus autobus) {
				cantidadAutobuses += 1;
			} else if (vehiculo instanceof Furgoneta furgoneta) {
				cantidadFurgonetas += 1;
			}
		}
		
		estadisticas.put("Turismos", cantidadTurismos);
		estadisticas.put("Autobuses", cantidadAutobuses);
		estadisticas.put("Furgonetas", cantidadFurgonetas);

		return estadisticas;
	}
}
