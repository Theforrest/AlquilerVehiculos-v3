package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate; 

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VentanaBuscarCliente extends Controlador {
	private VistaGrafica vistaGrafica;
	private Cliente cliente;


	@FXML
	private Button btCerrar;

	@FXML
	private Label lbDni;
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfTelefono;

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

		tfNombre.textProperty()
				.addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
		tfTelefono.textProperty()
				.addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));

		
	}

	@FXML
	private void cerrar() {
		((Stage) btCerrar.getParent().getScene().getWindow()).close();
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;

		lbDni.setText(cliente.getDni());
		tfNombre.setText(cliente.getNombre());
		tfTelefono.setText(cliente.getTelefono());

		tcVehiculo.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));

		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(cliente)));
	}

	@FXML
	private void modificar() {

		if (tfNombre.getStyleClass().contains("valido") && tfTelefono.getStyleClass().contains("valido")) {
			try {
				vistaGrafica.getControlador().modificar(cliente, tfNombre.getText(), tfTelefono.getText());
				
				Dialogos.mostrarDialogoInformacion("Modificacion correcta", "Cliente modificado correctamente",
						getEscenario());
			} catch (OperationNotSupportedException | IllegalArgumentException e) {
				Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
			}
		} else {
			Dialogos.mostrarDialogoError("ERROR", "Todos los campos deben de ser validos", getEscenario());
		}

	}

	@FXML
	private void borrar() {

		try {
			vistaGrafica.getControlador().borrar(cliente);
			Dialogos.mostrarDialogoInformacion("Borrado correcto", "Cliente borrado correctamente",
					getEscenario());
			cerrar();
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}

	}
	
	@FXML
	private void alquilar(ActionEvent event) {
		VentanaAlquilarCliente ventanaAlquilarCliente = (VentanaAlquilarCliente) Controladores
				.get("vistas/VentanaAlquilarCliente.fxml", "", getEscenario());
		ventanaAlquilarCliente.getEscenario().setResizable(false);
		ventanaAlquilarCliente.setCliente(cliente);
		ventanaAlquilarCliente.getEscenario().showAndWait();
		tvAlquileres.getItems().clear();
		tvAlquileres.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(cliente)));

	}
	
	@FXML
	private void devolver() {
		VentanaDevolverAlquilerCliente ventanaDevolverAlquiler = (VentanaDevolverAlquilerCliente) Controladores
				.get("vistas/VentanaDevolverAlquilerCliente.fxml", "", getEscenario());
		ventanaDevolverAlquiler.getEscenario().setResizable(false);
		ventanaDevolverAlquiler.setCliente(cliente);
		ventanaDevolverAlquiler.getEscenario().showAndWait();
		tvAlquileres.getItems().clear();
		tvAlquileres
		.setItems(FXCollections.observableArrayList(vistaGrafica.getControlador().getAlquileres(cliente)));

	}
}
