package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum TipoVehiculo {

	TURISMO("Turismo"), AUTOBUS("Autobus"), FURGONETA("Furgoneta");

	private String nombre;

	private TipoVehiculo(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (nombre.isBlank()) {
			throw new IllegalArgumentException("ERROR: El nombre no puede estar en blanco.");
		}
		this.nombre = nombre;
	}

	private static boolean esOrdinalValido(int ordinal) {
		return ordinal >= 0 && TipoVehiculo.values().length > ordinal;
	}

	public static TipoVehiculo get(int ordinal) throws OperationNotSupportedException {
		if (!(esOrdinalValido(ordinal))) {
			throw new OperationNotSupportedException("ERROR: El ordinal no es valido.");
		}
		return TipoVehiculo.values()[ordinal];
	}

	public static TipoVehiculo get(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: El vehiculo no puede ser nulo.");
		}
		if (vehiculo instanceof Turismo) {
			return TipoVehiculo.TURISMO;
		} else if (vehiculo instanceof Autobus) {
			return TipoVehiculo.AUTOBUS;
		} else if (vehiculo instanceof Furgoneta) {
			return TipoVehiculo.FURGONETA;
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("%s", nombre);
	}
}
