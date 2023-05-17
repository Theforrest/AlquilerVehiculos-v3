package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VentanaBuscarVehiculo extends Controlador {
	private VistaGrafica vistaGrafica;
	private Vehiculo vehiculo;

	@FXML
	private Button btCerrar;

	@FXML
	private Label lbMatricula;
	@FXML
	private Label lbMarca;
	@FXML
	private Label lbModelo;
	@FXML
	private Label lbCilindrada;
	@FXML
	private Label lbPlazas;
	@FXML
	private Label lbPma;

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
	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;

		lbMatricula.setText(vehiculo.getMatricula());
		lbMarca.setText(vehiculo.getMarca());
		lbModelo.setText(vehiculo.getModelo());

		if (vehiculo instanceof Autobus autobus) {
			lbPlazas.setText(String.format("%s", autobus.getPlazas()));
		} else if (vehiculo instanceof Furgoneta fugoneta) {
			lbPlazas.setText(String.format("%s", fugoneta.getPlazas()));
			lbPma.setText(String.format("%s", fugoneta.getPma()));

		} else if (vehiculo instanceof Turismo turismo) {
			lbCilindrada.setText(String.format("%s", turismo.getCilindrada()));
		}

		tcCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
		tcFechaAlquiler.setCellFactory(cell -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellFactory(cell -> new FormateadorCeldaFecha());

		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(vehiculo)));
	}

	@FXML
	private void borrar() {

		try {
			vistaGrafica.getControlador().borrar(vehiculo);
			Dialogos.mostrarDialogoInformacion("Borrado correcto", "Cliente borrado correctamente", getEscenario());
			cerrar();
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}

	}

	@FXML
	private void alquilar(ActionEvent event) {
		VentanaAlquilarVehiculo ventanaAlquilarVehiculo = (VentanaAlquilarVehiculo) Controladores
				.get("vistas/VentanaAlquilarVehiculo.fxml", "", getEscenario());
		ventanaAlquilarVehiculo.getEscenario().setResizable(false);
		ventanaAlquilarVehiculo.setVehiculo(vehiculo);
		ventanaAlquilarVehiculo.getEscenario().showAndWait();
		tvAlquileres.getItems().clear();
		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(vehiculo)));

	}

	@FXML
	private void devolver() {
		VentanaDevolverAlquilerVehiculo ventanaDevolverAlquiler = (VentanaDevolverAlquilerVehiculo) Controladores
				.get("vistas/VentanaDevolverAlquilerVehiculo.fxml", "", getEscenario());
		ventanaDevolverAlquiler.getEscenario().setResizable(false);
		ventanaDevolverAlquiler.setVehiculo(vehiculo);
		ventanaDevolverAlquiler.getEscenario().showAndWait();
		tvAlquileres.getItems().clear();
		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(vehiculo)));
	}
}
