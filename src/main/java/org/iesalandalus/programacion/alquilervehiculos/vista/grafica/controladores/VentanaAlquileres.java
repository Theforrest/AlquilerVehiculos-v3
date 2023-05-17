package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.io.IOException;
import java.time.LocalDate;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VentanaAlquileres extends Controlador {
	private VistaGrafica vistaGrafica;

	private static Scene escenaAlquileres;

	public static void setEscenaPrincipal(Scene escena) {
		if (escenaAlquileres == null) {
			escenaAlquileres = escena;
		}
	}

	public static Scene getEscenaPrincipal() {
		return escenaAlquileres;
	}

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
		tcFechaAlquiler.setCellFactory(cell -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellFactory(cell -> new FormateadorCeldaFecha());

		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres()));

	}

	@FXML
	private void volver(ActionEvent event) {
		Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
		escenario.setScene(VentanaPrincipal.getEscenaPrincipal());
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

		Parent raiz = null;
		try {
			raiz = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/VentanaMostrarEstadisticas.fxml"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		Scene escena = new Scene(raiz);
		Stage escenario = new Stage();
		escenario.setScene(escena);
		escenario.setResizable(false);
		escenario.showAndWait();
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
