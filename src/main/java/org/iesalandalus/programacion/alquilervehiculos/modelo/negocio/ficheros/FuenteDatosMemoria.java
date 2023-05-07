package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class FuenteDatosMemoria implements IFuenteDatos {

	public IClientes crearClientes() {
		return Clientes.getInstancia();
	}

	public IVehiculos crearVehiculos() {
		return Vehiculos.getInstancia();
	}

	public IAlquileres crearAlquileres() {
		return Alquileres.getInstancia();
	}
}
