package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {
	private Vista vista;
	private Modelo modelo;

	public Controlador(Modelo modelo, Vista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		this.vista = vista;
		this.modelo = modelo;

		vista.setControlador(this);
	}

	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();

	}

	public void terminar() {
		modelo.terminar();
		vista.terminar();
	}

	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		modelo.insertar(cliente);
	}

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		modelo.insertar(vehiculo);

	}

	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {

		modelo.insertar(alquiler);

	}

	public Cliente buscar(Cliente cliente) {
		return modelo.buscar(cliente);
	}

	public Vehiculo buscar(Vehiculo vehiculo) {
		return modelo.buscar(vehiculo);
	}

	public Alquiler buscar(Alquiler alquiler) {
		return modelo.buscar(alquiler);
	}

	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		modelo.modificar(cliente, nombre, telefono);
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		modelo.devolver(cliente, fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		modelo.devolver(vehiculo, fechaDevolucion);
	}

	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		modelo.borrar(cliente);
	}

	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		modelo.borrar(vehiculo);
	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		modelo.borrar(alquiler);
	}

	public List<Cliente> getClientes() {
		return modelo.getListaClientes();
	}

	public List<Vehiculo> getVehiculos() {
		return modelo.getListaVehiculos();
	}

	public List<Alquiler> getAlquileres() {
		return modelo.getListaAlquileres();
	}

	public List<Alquiler> getAlquileres(Cliente cliente) {
		return modelo.getListaAlquileres(cliente);
	}

	public List<Alquiler> getAlquileres(Vehiculo vehiculo) {
		return modelo.getListaAlquileres(vehiculo);
	}
}
