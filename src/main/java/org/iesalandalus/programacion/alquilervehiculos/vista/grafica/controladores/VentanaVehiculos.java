package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;  

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VentanaVehiculos extends Controlador {
	private VistaGrafica vistaGrafica;


	@FXML
	private Button btVolver;
	@FXML
	private Button btInsertar;
	@FXML
	private Button btBuscar;

	@FXML
	private TableView<Vehiculo> tvVehiculos;
	@FXML
	private TableColumn<Vehiculo, String> tcMarca;
	@FXML
	private TableColumn<Vehiculo, String> tcModelo;
	@FXML
	private TableColumn<Vehiculo, String> tcMatricula;
	@FXML
	private TableColumn<Vehiculo, String> tcCilindrada;
	@FXML
	private TableColumn<Vehiculo, String> tcPlazas;
	@FXML
	private TableColumn<Vehiculo, String> tcPma;

	@FXML
	private TableView<Alquiler> tvAlquileres;
	@FXML
	private TableColumn<Alquiler, Cliente> tcCliente;
	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaAlquiler;
	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaDevolucion;

	@FXML
	private void initialize() {
		// Inicializando la vista
		vistaGrafica = VistaGrafica.getInstancia();

		// Inicializando la tabla de los clientes
		tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		tcCilindrada.setCellValueFactory(
			      new Callback<CellDataFeatures<Vehiculo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Vehiculo, String> param) {
						if (param.getValue() instanceof Turismo turismo) {
							return new ReadOnlyObjectWrapper<>(String.format("%s", turismo.getCilindrada()));
						} else {
							return new ReadOnlyObjectWrapper<>("");
						}
					}
			      });
		tcPlazas.setCellValueFactory(
			      new Callback<CellDataFeatures<Vehiculo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Vehiculo, String> param) {
						if (param.getValue() instanceof Autobus autobus) {
							return new ReadOnlyObjectWrapper<>(String.format("%s", autobus.getPlazas()));
						} else if (param.getValue() instanceof Furgoneta fugoneta) {
							return new ReadOnlyObjectWrapper<>(String.format("%s", fugoneta.getPlazas()));
						} else {
							return new ReadOnlyObjectWrapper<>("");
						}
					}
			      });
		tcPma.setCellValueFactory(new Callback<CellDataFeatures<Vehiculo, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Vehiculo, String> param) {
				if (param.getValue() instanceof Furgoneta furgoneta) {
					return new ReadOnlyObjectWrapper<>(String.format("%s", furgoneta.getPma()));
				} else {
					return new ReadOnlyObjectWrapper<>("");
				}
			}
	      });

		tvVehiculos.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getVehiculos()));

		// Inicializando la tabla de los alquileres del cliente seleccionado
		tcCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));

		tvVehiculos.getSelectionModel().selectedItemProperty()
				.addListener((ob, oldValue, newValue) -> mostrarAlquileres(newValue));
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
	private void devolver() {
		Vehiculo vehiculo = null;
		String matricula;
		do {
			matricula = Dialogos.mostrarDialogoTexto("Buscar Vehiculo", "Escriba la matrícula del vehículo a buscar", null);
			if (matricula != null) {
				try {
					vehiculo = vistaGrafica.getControlador().buscar(Vehiculo.getVehiculoConMatricula(matricula));
					if (vehiculo != null) {
						VentanaDevolverAlquilerVehiculo ventanaDevolverAlquiler = (VentanaDevolverAlquilerVehiculo) Controladores
								.get("vistas/VentanaDevolverAlquilerVehiculo.fxml", "", getEscenario());
						ventanaDevolverAlquiler.getEscenario().setResizable(false);
						ventanaDevolverAlquiler.setVehiculo(vehiculo);
						ventanaDevolverAlquiler.getEscenario().showAndWait();
						tvAlquileres.getItems().clear();
						tvAlquileres
						.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(vehiculo)));
						matricula = null;
					} else {
						Dialogos.mostrarDialogoError("Cliente no encontrado", "No existe ningún cliente con ese DNI",
								null);
					}
				} catch (IllegalArgumentException e) {
					Dialogos.mostrarDialogoError("ERROR", e.getMessage(), null);
				}

			}
		} while (matricula != null);

	}

	@FXML
	private void insertar(ActionEvent event) {
		VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores
				.get("vistas/VentanaInsertarVehiculo.fxml", "", getEscenario());
		ventanaInsertarVehiculo.getEscenario().setResizable(false);
		ventanaInsertarVehiculo.getEscenario().showAndWait();
		tvVehiculos.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getVehiculos()));

	}

	@FXML
	private void buscar(ActionEvent event) {
		Vehiculo vehiculo = null;
		String matricula;
		do {
			matricula = Dialogos.mostrarDialogoTexto("Buscar Vehiculo", "Escriba la matrícula del vehículo a buscar", null);
			if (matricula != null) {
				try {
					vehiculo = vistaGrafica.getControlador().buscar(Vehiculo.getVehiculoConMatricula(matricula));
					if (vehiculo != null) {
						VentanaBuscarVehiculo ventanaBuscarVehiculo = (VentanaBuscarVehiculo) Controladores
								.get("vistas/VentanaBuscarVehiculo.fxml", "", null);
						ventanaBuscarVehiculo.getEscenario().setResizable(false);
						ventanaBuscarVehiculo.setVehiculo(vehiculo);
						ventanaBuscarVehiculo.getEscenario().showAndWait();
						tvVehiculos.getItems().clear();
						tvVehiculos.setItems(
								FXCollections.observableArrayList(vistaGrafica.getControlador().getVehiculos()));
						matricula = null;
					} else {
						Dialogos.mostrarDialogoError("Cliente no encontrado", "No existe ningún cliente con ese DNI",
								null);
					}
				} catch (IllegalArgumentException e) {
					Dialogos.mostrarDialogoError("ERROR", e.getMessage(), null);
				}

			}
		} while (matricula != null);

	}

	private void mostrarAlquileres(Vehiculo vehiculo) {
		if (vehiculo != null) {
			tvAlquileres
					.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(vehiculo)));
		}
	}

}
