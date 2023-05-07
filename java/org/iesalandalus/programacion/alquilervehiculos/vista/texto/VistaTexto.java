package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public VistaTexto() {
		Accion.setVista(this);
	}

	public void comenzar() {
		Accion accion;
		do {
			Consola.mostrarMenuAcciones();
			accion = Consola.elegirAccion();
			try {
				Consola.mostrarCabezera(accion.toString());
				accion.ejecutar();
			} catch (IllegalArgumentException | OperationNotSupportedException e) {
				System.out.printf("%n%s%n%n", e.getMessage());
			}
		} while (!(accion.equals(Accion.SALIR)));

		getControlador().terminar();
	}

	public void terminar() {
		System.out.printf("%nBye bye%n");

	}

	public void insertarCliente() throws OperationNotSupportedException {
		Cliente cliente = Consola.leerCliente();
		getControlador().insertar(cliente);
		System.out.print("\nCliente insertado correctamente\n");
	}

	public void insertarVehiculo() throws OperationNotSupportedException {
		Vehiculo vehiculo = Consola.leerVehiculo();
		getControlador().insertar(vehiculo);
		System.out.print("\nVehiculo insertado correctamente\n");

	}

	public void insertarAlquiler() throws OperationNotSupportedException {
		Alquiler alquiler = Consola.leerAlquiler();
		getControlador().insertar(alquiler);
		System.out.print("\nAlquiler insertado correctamente\n");

	}

	public void buscarCliente() {
		Cliente cliente = Consola.leerClienteDni();
		System.out.printf("%s%n", (getControlador().buscar(cliente) == null) ? "Cliente no encontrado"
				: getControlador().buscar(cliente));
	}

	public void buscarVehiculo() {
		Vehiculo vehiculo = Consola.leerVehiculoMatricula();
		System.out.printf("%s%n", (getControlador().buscar(vehiculo) == null) ? "Turismo no encontrado"
				: getControlador().buscar(vehiculo));
	}

	public void buscarAlquiler() {
		Alquiler alquiler = Consola.leerAlquiler();
		System.out.printf("%s%n", (getControlador().buscar(alquiler) == null) ? "Alquiler no encontrado"
				: getControlador().buscar(alquiler));
	}

	public void modificarCliente() throws OperationNotSupportedException {
		Cliente cliente = Consola.leerClienteDni();
		String nombre = Consola.leerNombre();
		String telefono = Consola.leerTelefono();

		getControlador().modificar(cliente, nombre, telefono);
		System.out.print("\nCliente modificado correctamente\n");

	}

	public void devolverAlquilerCliente() throws OperationNotSupportedException {
		Cliente cliente = Consola.leerClienteDni();
		LocalDate fechaDevolucion = Consola.leerFechaDevolucion();

		getControlador().devolver(cliente, fechaDevolucion);
		System.out.print("\nAlquiler del cliente devuelto correctamente\n");

	}

	public void devolverAlquilerVehiculo() throws OperationNotSupportedException {
		Vehiculo vehiculo = Consola.leerVehiculoMatricula();
		LocalDate fechaDevolucion = Consola.leerFechaDevolucion();

		getControlador().devolver(vehiculo, fechaDevolucion);
		System.out.print("\nAlquiler del vehículo devuelto correctamente\n");

	}

	public void borrarCliente() throws OperationNotSupportedException {
		Cliente cliente = Consola.leerClienteDni();

		getControlador().borrar(cliente);
		System.out.print("\nCliente borrado correctamente\n");

	}

	public void borrarVehiculo() throws OperationNotSupportedException {
		Vehiculo vehiculo = Consola.leerVehiculoMatricula();

		getControlador().borrar(vehiculo);
		System.out.print("\nVehículo borrado correctamente\n");

	}

	public void borrarAlquiler() throws OperationNotSupportedException {
		Alquiler alquiler = Consola.leerAlquiler();
		getControlador().borrar(alquiler);
		System.out.print("\nAlquiler borrado correctamente\n");

	}

	public void listarClientes() {
		List<Cliente> clientes = getControlador().getClientes();
		if (!clientes.isEmpty()) {
			clientes.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
			for (int i = 0; i < clientes.size(); i++) {
				System.out.printf("%d. %s%n", i + 1, clientes.get(i));
			}
		} else {
			System.out.print("\nNo hay clientes que listar\n");
		}

	}

	public void listarVehiculos() {
		List<Vehiculo> vehiculos = getControlador().getVehiculos();
		if (!vehiculos.isEmpty()) {
			vehiculos.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo)
					.thenComparing(Vehiculo::getMatricula));
			for (int i = 0; i < vehiculos.size(); i++) {
				System.out.printf("%d. %s%n", i + 1, vehiculos.get(i));
			}
		} else {
			System.out.print("\nNo hay vehículos que listar\n");
		}

	}

	public void listarAlquileres() {
		List<Alquiler> alquileres = getControlador().getAlquileres();
		if (!alquileres.isEmpty()) {
			Comparator<Cliente> comparadorCliente = Comparator.comparing(Cliente::getNombre)
					.thenComparing(Cliente::getDni);
			alquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
					comparadorCliente));
			for (int i = 0; i < alquileres.size(); i++) {
				System.out.printf("%d. %s%n", i + 1, alquileres.get(i));
			}
		} else {
			System.out.print("\nNo hay alquileres que listar\n");
		}

	}

	public void listarAlquileresCliente() {
		Cliente cliente = Consola.leerClienteDni();
		List<Alquiler> alquileres = getControlador().getAlquileres(cliente);
		if (!alquileres.isEmpty()) {
			Comparator<Cliente> comparadorCliente = Comparator.comparing(Cliente::getNombre)
					.thenComparing(Cliente::getDni);
			alquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
					comparadorCliente));
			for (int i = 0; i < alquileres.size(); i++) {
				System.out.printf("%d. %s%n", i + 1, alquileres.get(i));
			}
		} else {
			System.out.print("\nEse cliente no tiene alquileres que listar\n");
		}

	}

	public void listarAlquileresVehiculo() {
		Vehiculo vehiculo = Consola.leerVehiculoMatricula();
		List<Alquiler> alquileres = getControlador().getAlquileres(vehiculo);
		if (!alquileres.isEmpty()) {
			Comparator<Cliente> comparadorCliente = Comparator.comparing(Cliente::getNombre)
					.thenComparing(Cliente::getDni);
			alquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
					comparadorCliente));
			for (int i = 0; i < alquileres.size(); i++) {
				System.out.printf("%d. %s%n", i + 1, alquileres.get(i));
			}
		} else {
			System.out.print("\nEse vehículos no tiene alquileres que listar\n");
		}

	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		Map<TipoVehiculo, Integer> estadisticas = new TreeMap<>();

		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			estadisticas.put(tipoVehiculo, 0);
		}

		return estadisticas;
	}

	public void mostrarEstadisticasMensuales() {
		Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();

		LocalDate mes = Consola.leerMes();

		List<Alquiler> alquileres = getControlador().getAlquileres();

		for (Alquiler alquiler : alquileres) {
			if (alquiler.getFechaAlquiler().getMonth().equals(mes.getMonth())
					&& alquiler.getFechaAlquiler().getYear() == mes.getYear()) {
				TipoVehiculo tipoVehiculo = TipoVehiculo.get(alquiler.getVehiculo());
				estadisticas.put(tipoVehiculo, estadisticas.get(tipoVehiculo) + 1);

			}
		}

		if (!estadisticas.isEmpty()) {
			for (Map.Entry<TipoVehiculo, Integer> entry : estadisticas.entrySet()) {
				System.out.printf("%s alquilados: %s%n", entry.getKey(), entry.getValue());
			}
		} else {
			System.out.print("\nNo hay estadisticas que mostrar en este mes\n");

		}

	}
}
