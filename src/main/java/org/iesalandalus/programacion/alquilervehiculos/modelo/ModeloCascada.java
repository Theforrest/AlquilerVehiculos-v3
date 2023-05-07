package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public class ModeloCascada extends Modelo {

	public ModeloCascada(FactoriaFuenteDatos factoriaFuenteDatos) {
		super(factoriaFuenteDatos);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {

		getClientes().insertar(new Cliente(cliente));

	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {

		getVehiculos().insertar(Vehiculo.copiar(vehiculo));

	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		Cliente cliente = getClientes().buscar(alquiler.getCliente());
		if (cliente == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		Vehiculo vehiculo = getVehiculos().buscar(alquiler.getVehiculo());
		if (vehiculo == null) {
			throw new OperationNotSupportedException("ERROR: No existe el vehículo del alquiler.");
		}

		getAlquileres().insertar(new Alquiler(cliente, vehiculo, alquiler.getFechaAlquiler()));

	}

	@Override
	public Cliente buscar(Cliente cliente) {
		Cliente busqueda = getClientes().buscar(cliente);
		Cliente resultado = null;
		if (busqueda != null) {
			resultado = new Cliente(busqueda);

		}
		return resultado;
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		Vehiculo busqueda = getVehiculos().buscar(vehiculo);
		Vehiculo resultado = null;
		if (busqueda != null) {
			resultado = Vehiculo.copiar(busqueda);

		}
		return resultado;
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		Alquiler busqueda = getAlquileres().buscar(alquiler);
		Alquiler resultado = null;
		if (busqueda != null) {
			resultado = new Alquiler(busqueda);

		}
		return resultado;
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		getClientes().modificar(cliente, nombre, telefono);

	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		getAlquileres().devolver(cliente, fechaDevolucion);

	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		getAlquileres().devolver(vehiculo, fechaDevolucion);

	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {

		for (Alquiler alquiler : getAlquileres().get(cliente)) {

			getAlquileres().borrar(alquiler);

		}
		getClientes().borrar(cliente);

	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquileres().get(vehiculo)) {

			getAlquileres().borrar(alquiler);

		}
		getVehiculos().borrar(vehiculo);

	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {

		getAlquileres().borrar(alquiler);

	}

	@Override
	public List<Cliente> getListaClientes() {
		List<Cliente> copiaClientes = new ArrayList<>();
		for (Cliente cliente : getClientes().get()) {
			copiaClientes.add(new Cliente(cliente));
		}
		return copiaClientes;
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> copiaTurismos = new ArrayList<>();
		for (Vehiculo vehiculo : getVehiculos().get()) {
			copiaTurismos.add(Vehiculo.copiar(vehiculo));
		}
		return copiaTurismos;
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get()) {
			copiaAlquileres.add(new Alquiler(alquiler));
		}
		return copiaAlquileres;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get(cliente)) {
			copiaAlquileres.add(new Alquiler(alquiler));
		}
		return copiaAlquileres;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get(vehiculo)) {
			copiaAlquileres.add(new Alquiler(alquiler));
		}
		return copiaAlquileres;
	}
}
