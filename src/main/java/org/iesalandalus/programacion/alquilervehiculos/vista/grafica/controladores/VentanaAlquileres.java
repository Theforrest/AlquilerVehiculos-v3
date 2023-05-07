package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate; 

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VentanaAlquileres extends Controlador {
	private VistaGrafica vistaGrafica;

	@FXML
	private Button btVolver;
	@FXML
	private Button btInsertar;
	@FXML
	private Button btBuscar;

	@FXML
	private TableView<Alquiler> tvAlquileres;
	@FXML
	private TableColumn<Alquiler, Cliente> tcCliente;
	@FXML
	private TableColumn<Alquiler, Vehiculo> tcVehiculo;
	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaAlquiler;
	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaDevolucion;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		// Inicializando la tabla de los alquileres del cliente seleccionado
		tcCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		tcVehiculo.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));

		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres()));

	}

	@FXML
	private void volver(ActionEvent event) {
		VentanaPrincipal ventanaPrincipal = (VentanaPrincipal) Controladores.get("vistas/VentanaPrincipal.fxml", "",
				null);
		Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
		escenario.setScene(ventanaPrincipal.getEscenario().getScene());
		escenario.show();
	}

	@FXML
	private void insertar(ActionEvent event) {
		VentanaInsertarAlquiler ventanaInsertarAlquiler = (VentanaInsertarAlquiler) Controladores
				.get("vistas/VentanaInsertarAlquiler.fxml", "", getEscenario());
		ventanaInsertarAlquiler.getEscenario().setResizable(false);
		ventanaInsertarAlquiler.getEscenario().showAndWait();
		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres()));

	}

	@FXML
	private void devolver(ActionEvent event) {
		VentanaDevolverAlquiler ventanaDevolverAlquiler = (VentanaDevolverAlquiler) Controladores
				.get("vistas/VentanaDevolverAlquiler.fxml", "", getEscenario());
		ventanaDevolverAlquiler.getEscenario().setResizable(false);
		ventanaDevolverAlquiler.getEscenario().showAndWait();
		tvAlquileres.getItems().clear();
		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres()));

	}

	@FXML
	private void mostrarEstadisticas(ActionEvent event) {
		VentanaMostrarEstadisticas ventanaMostrarEstadisticas = (VentanaMostrarEstadisticas) Controladores
				.get("vistas/VentanaMostrarEstadisticas.fxml", "", getEscenario());
		ventanaMostrarEstadisticas.getEscenario().setResizable(false);
		ventanaMostrarEstadisticas.getEscenario().showAndWait();
		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres()));

	}

	@FXML
	private void buscar(ActionEvent event) {
		Alquiler alquiler;
		VentanaBuscadorAlquiler ventanaBuscadorAlquiler = (VentanaBuscadorAlquiler) Controladores
				.get("vistas/VentanaBuscadorAlquiler.fxml", "", getEscenario());
		ventanaBuscadorAlquiler.getEscenario().setResizable(false);
		ventanaBuscadorAlquiler.getEscenario().showAndWait();
		alquiler = ventanaBuscadorAlquiler.getAlquiler();

		if (alquiler != null) {
			try {

				VentanaBuscarAlquiler ventanaBuscarAlquiler = (VentanaBuscarAlquiler) Controladores
						.get("vistas/VentanaBuscarAlquiler.fxml", "", getEscenario());
				ventanaBuscarAlquiler.getEscenario().setResizable(false);
				ventanaBuscarAlquiler.setAlquiler(alquiler);
				ventanaBuscarAlquiler.getEscenario().showAndWait();
				tvAlquileres.getItems().clear();
				tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres()));

			} catch (IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), null);
			}

		}

	}

}
