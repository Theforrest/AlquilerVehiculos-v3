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

public class VentanaClientes extends Controlador {
	private VistaGrafica vistaGrafica;

	@FXML
	private Button btVolver;
	@FXML
	private Button btInsertar;
	@FXML
	private Button btBuscar;

	@FXML
	private TableView<Cliente> tvClientes;
	@FXML
	private TableColumn<Cliente, String> tcDni;
	@FXML
	private TableColumn<Cliente, String> tcNombre;
	@FXML
	private TableColumn<Cliente, String> tcTelefono;

	@FXML
	private TableView<Alquiler> tvAlquileres;
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

		// Inicializando la tabla de los clientes
		tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

		tvClientes.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getClientes()));

		// Inicializando la tabla de los alquileres del cliente seleccionado
		tcVehiculo.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));

		tvClientes.getSelectionModel().selectedItemProperty()
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
		Cliente cliente = null;
		String dni;
		do {
			dni = Dialogos.mostrarDialogoTexto("Buscar Cliente", "Escriba el dni del cliente a buscar", null);
			if (dni != null) {
				try {
					cliente = vistaGrafica.getControlador().buscar(Cliente.getClienteConDni(dni));
					if (cliente != null) {
						VentanaDevolverAlquilerCliente ventanaDevolverAlquiler = (VentanaDevolverAlquilerCliente) Controladores
								.get("vistas/VentanaDevolverAlquilerCliente.fxml", "", getEscenario());
						ventanaDevolverAlquiler.getEscenario().setResizable(false);
						ventanaDevolverAlquiler.setCliente(cliente);
						ventanaDevolverAlquiler.getEscenario().showAndWait();
						tvAlquileres.getItems().clear();
						tvAlquileres
						.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(cliente)));
						dni = null;
					} else {
						Dialogos.mostrarDialogoError("Cliente no encontrado", "No existe ningún cliente con ese DNI",
								null);
					}
				} catch (IllegalArgumentException e) {
					Dialogos.mostrarDialogoError("ERROR", e.getMessage(), null);
				}

			}
	} while (dni != null);
	}

	@FXML
	private void insertar(ActionEvent event) {
		VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores
				.get("vistas/VentanaInsertarCliente.fxml", "", getEscenario());
		ventanaInsertarCliente.getEscenario().setResizable(false);
		ventanaInsertarCliente.getEscenario().showAndWait();
		tvClientes.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getClientes()));

	}

	@FXML
	private void buscar(ActionEvent event) {
		Cliente cliente = null;
		String dni;
		do {
			dni = Dialogos.mostrarDialogoTexto("Buscar Cliente", "Escriba el dni del cliente a buscar", null);
			if (dni != null) {
				try {
					cliente = vistaGrafica.getControlador().buscar(Cliente.getClienteConDni(dni));
					if (cliente != null) {
						VentanaBuscarCliente ventanaBuscarCliente = (VentanaBuscarCliente) Controladores
								.get("vistas/VentanaBuscarCliente.fxml", "", getEscenario());
						ventanaBuscarCliente.getEscenario().setResizable(false);
						ventanaBuscarCliente.setCliente(cliente);
						ventanaBuscarCliente.getEscenario().showAndWait();
						tvClientes.getItems().clear();
						tvClientes.setItems(
								FXCollections.observableArrayList(vistaGrafica.getControlador().getClientes()));
						dni = null;
					} else {
						Dialogos.mostrarDialogoError("Cliente no encontrado", "No existe ningún cliente con ese DNI",
								null);
					}
				} catch (IllegalArgumentException e) {
					Dialogos.mostrarDialogoError("ERROR", e.getMessage(), null);
				}

			}
		} while (dni != null);

	}

	private void mostrarAlquileres(Cliente cliente) {
		if (cliente != null) {
			tvAlquileres
					.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(cliente)));
		}
	}

}
