package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {
	}

	public static void mostrarCabezera(String mensaje) {
		System.out.printf("%n%s", mensaje);
		StringBuilder subrayado = new StringBuilder();
		for (int i = 0; i < mensaje.length(); i++) {
			subrayado.append('-');
		}
		System.out.printf("%n%s%n", subrayado);

	}

	public static void mostrarMenuAcciones() {
		mostrarCabezera("Este es un programa para manejar los alquileres de vehículos realizados por clientes");
		for (int i = 0; i < Accion.values().length; i++) {
			System.out.printf("%n%s", Accion.values()[i]);

		}
	}

	private static String leerCadena(String mensaje) {
		System.out.printf("%n%s: ", mensaje);
		return Entrada.cadena();

	}

	private static Integer leerEntero(String mensaje) {
		System.out.printf("%n%s: ", mensaje);
		return Entrada.entero();

	}

	private static LocalDate leerFecha(String mensaje, String patron) {
		String fechaTexto;
		LocalDate fecha = null;

		do {
			System.out.printf("%n%s (%s): ", mensaje, patron);
			fechaTexto = Entrada.cadena();
			if (patron.equals(PATRON_MES)) {
				fechaTexto = "01/" + fechaTexto;
			}
			try {
				fecha = LocalDate.parse(fechaTexto, FORMATO_FECHA);
			} catch (DateTimeParseException e) {
				System.out.printf("%nFormato de fecha incorrecta");
			}
		} while (fecha == null);
		return fecha;
	}

	public static Accion elegirAccion() {
		Accion accion = null;
		do {
			try {
				accion = Accion.get(leerEntero("Elige una acción"));
			} catch (OperationNotSupportedException e) {
				System.out.printf("%n%s%n%n", e.getMessage());
			}

		} while (accion == null);

		return accion;
	}

	public static Cliente leerClienteDni() {
		String dni = leerCadena("Escriba el DNI del cliente");
		return Cliente.getClienteConDni(dni);
	}

	public static String leerNombre() {
		return leerCadena("Escriba el nombre del cliente");
	}

	public static String leerTelefono() {
		return leerCadena("Escriba el telefono del cliente");
	}

	public static Cliente leerCliente() {
		return new Cliente(leerNombre(), leerCadena("Escriba el dni"), leerTelefono());
	}

	public static void mostrarMenuTipoVehiculo() {
		mostrarCabezera("Tipos de vehiculos");
		for (int i = 0; i < TipoVehiculo.values().length; i++) {
			System.out.printf("%n%d. %s", i, TipoVehiculo.values()[i]);

		}
	}

	public static TipoVehiculo elegirTipoVehiculo() {
		TipoVehiculo tipoVehiculo = null;
		int i;
		do {
			i = leerEntero("Elige un tipo de vehiculo");
			try {
				tipoVehiculo = TipoVehiculo.get(i);
			} catch (OperationNotSupportedException e) {
				System.out.printf("%n%s%n%n", e.getMessage());
			}

		} while (tipoVehiculo == null);

		return tipoVehiculo;
	}

	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		String marca = leerCadena("Escriba la marca del vehiculo");
		String modelo = leerCadena("Escriba el modelo del vehiculo");
		String matricula = leerCadena("Escriba la matricula del vehiculo");
		Vehiculo vehiculo;

		switch (tipoVehiculo) {
		case AUTOBUS:
			vehiculo = new Autobus(marca, modelo, leerEntero("Escriba las plazas del autobus"), matricula);
			break;
		case FURGONETA:
			vehiculo = new Furgoneta(marca, modelo, leerEntero("Escriba el pma de la furgoneta"),
					leerEntero("Escriba las plazas de la furgoneta"), matricula);
			break;
		case TURISMO:
			vehiculo = new Turismo(marca, modelo, leerEntero("Escriba la cilindrada del turismo"), matricula);
			break;
		default:
			vehiculo = null;
		}
		return vehiculo;
	}

	public static Vehiculo leerVehiculo() {
		mostrarMenuTipoVehiculo();
		return leerVehiculo(elegirTipoVehiculo());
	}

	public static Vehiculo leerVehiculoMatricula() {
		String matricula = leerCadena("Escriba la matricula del vehículo");
		return Vehiculo.getVehiculoConMatricula(matricula);
	}

	public static Alquiler leerAlquiler() {
		Cliente cliente = leerClienteDni();
		Vehiculo vehiculo = leerVehiculoMatricula();
		LocalDate fechaAlquiler = leerFecha("Escriba la fecha de alquiler", PATRON_FECHA);
		return new Alquiler(cliente, vehiculo, fechaAlquiler);
	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha("Escriba la fecha de devolucion", PATRON_FECHA);
	}

	public static LocalDate leerMes() {
		return leerFecha("Escriba el mes que quiere", PATRON_MES);
	}

}
