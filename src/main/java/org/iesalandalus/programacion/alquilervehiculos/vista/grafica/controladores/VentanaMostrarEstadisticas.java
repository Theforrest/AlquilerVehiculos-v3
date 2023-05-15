package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;
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

public class VentanaMostrarEstadisticas extends Controlador {
	private VistaGrafica vistaGrafica;

	@FXML
	private Button btCerrar;

	@FXML
	private PieChart pcEstadisticas;

	@FXML
	private void initialize() {
		// Inicializando la vista
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

		List<Alquiler> alquileres = vistaGrafica.getControlador().getAlquileres();

		for (Alquiler alquiler : alquileres) {
			Vehiculo vehiculo = alquiler.getVehiculo();

			if (vehiculo instanceof Turismo) {
				cantidadTurismos += 1;
			} else if (vehiculo instanceof Autobus) {
				cantidadAutobuses += 1;
			} else if (vehiculo instanceof Furgoneta) {
				cantidadFurgonetas += 1;
			}
		}

		estadisticas.put(
				String.format("Turismos: %s%%",
						cantidadTurismos == 0 ? cantidadTurismos : cantidadTurismos * 100 / alquileres.size()),
				cantidadTurismos);
		estadisticas.put(
				String.format("Autobuses: %s%%",
						cantidadAutobuses == 0 ? cantidadAutobuses : cantidadAutobuses * 100 / alquileres.size()),
				cantidadAutobuses);
		estadisticas.put(
				String.format("Furgonetas: %s%%",
						cantidadFurgonetas == 0 ? cantidadFurgonetas : cantidadFurgonetas * 100 / alquileres.size()),
				cantidadFurgonetas);

		return estadisticas;
	}

}
