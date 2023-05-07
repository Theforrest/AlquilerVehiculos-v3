package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Alquileres implements IAlquileres {

	private List<Alquiler> coleccionAlquileres;
	private static Alquileres instancia;
	private static final File FICHERO_ALQUILERES = new File(
			String.format("%s%s%s", "datos", File.separator, "alquileres.xml"));
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";

	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	static Alquileres getInstancia() {
		if (instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;
	}

	@Override
	public void comenzar() {
		try {
			leerDom(UtilidadesXml.leerXmlDeFichero(FICHERO_ALQUILERES));
		} catch (ParserConfigurationException e) {
			System.out.printf("ERROR: problema de configuración al intentar leer el fichero %s%n", FICHERO_ALQUILERES);
		} catch (SAXException e) {
			System.out.printf("ERROR: fallo al intentar leer el fichero %s%n", FICHERO_ALQUILERES);
		} catch (IOException e) {
			System.out.printf("%s no ha sido encontrado%n", FICHERO_ALQUILERES);
		}
	}

	private void leerDom(Document documentoXml) {
		if (documentoXml == null) {
			throw new NullPointerException("ERROR: El documento no puede ser nulo.");
		}
		NodeList lista = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < lista.getLength(); i++) {
			Node nodo = lista.item(i);
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				try {
					insertar(getAlquiler(elemento));
				} catch (OperationNotSupportedException | IllegalArgumentException | DateTimeParseException e) {
					System.out.printf("ERROR: No se ha podido insertar el alquiler número %s -> %s%n", i,
							e.getMessage());
				}

			}
		}
	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {
		if (elemento == null) {
			throw new NullPointerException("ERROR: El elemento no puede ser nulo.");
		}
		String clienteTexto = elemento.getAttribute(CLIENTE);
		String vehiculoTexto = elemento.getAttribute(VEHICULO);
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);

		Cliente cliente = Clientes.getInstancia().buscar(Cliente.getClienteConDni(clienteTexto));
		if (cliente == null) {
			throw new OperationNotSupportedException("ERR: El cliente no existe");
		}

		Vehiculo vehiculo = Vehiculos.getInstancia().buscar(Vehiculo.getVehiculoConMatricula(vehiculoTexto));
		if (vehiculo == null) {
			throw new OperationNotSupportedException("ERR: El vehiculo no existe");
		}

		Alquiler alquiler;

		alquiler = new Alquiler(cliente, vehiculo, LocalDate.parse(fechaAlquiler, FORMATO_FECHA));

		if (elemento.hasAttribute(FECHA_DEVOLUCION)) {
			String fechaDevolucion = elemento.getAttribute(FECHA_DEVOLUCION);

			alquiler.devolver(LocalDate.parse(fechaDevolucion, FORMATO_FECHA));

		}

		return alquiler;
	}

	@Override
	public void terminar() {
		try {
			UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_ALQUILERES);
		} catch (TransformerException e) {
			System.out.printf("Error: No se ha podido llevar a cabo la transformación del fichero %s%n",
					FICHERO_ALQUILERES);
		} catch (ParserConfigurationException e) {
			System.out.printf("ERROR: problema de configuración al intentar pasar los datos al fichero %s%n",
					FICHERO_ALQUILERES);

		}
	}

	private Document crearDom() throws ParserConfigurationException {
		Document documento = UtilidadesXml.crearConstructorDocumentoXml().newDocument();
		Element raiz = documento.createElement(RAIZ);
		documento.appendChild(raiz);

		for (Alquiler alquiler : coleccionAlquileres) {
			raiz.appendChild(getElemento(documento, alquiler));
		}

		return documento;
	}

	private Element getElemento(Document documentoXml, Alquiler alquiler) {
		if (documentoXml == null) {
			throw new NullPointerException("ERROR: El documento no puede ser nulo.");
		}
		if (alquiler == null) {
			throw new NullPointerException("ERROR: El alquiler no puede ser nulo.");
		}
		Element elemento = documentoXml.createElement(ALQUILER);
		elemento.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		elemento.setAttribute(FECHA_ALQUILER, String.format("%s", alquiler.getFechaAlquiler().format(FORMATO_FECHA)));
		LocalDate fechaDevolucion = alquiler.getFechaDevolucion();
		if (fechaDevolucion != null) {
			elemento.setAttribute(FECHA_DEVOLUCION, String.format("%s", fechaDevolucion.format(FORMATO_FECHA)));

		}
		elemento.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());

		return elemento;
	}

	@Override
	public List<Alquiler> get() {

		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {

		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				copiaAlquileres.add(alquiler);
			}
		}
		return copiaAlquileres;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {

		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				copiaAlquileres.add(alquiler);
			}
		}
		return copiaAlquileres;
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {

		for (Alquiler alquiler : get()) {
			if (alquiler.getCliente().equals(cliente)) {
				if (alquiler.getFechaDevolucion() == null) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				} else if (alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
			}
			if (alquiler.getVehiculo().equals(vehiculo)) {
				if (alquiler.getFechaDevolucion() == null) {
					throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
				} else if (alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
					throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
				}
			}
		}

	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		Alquiler busqueda = null;
		int index = coleccionAlquileres.indexOf(alquiler);
		if (index != -1) {
			busqueda = coleccionAlquileres.get(index);
		}
		return busqueda;
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!(coleccionAlquileres.contains(alquiler))) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);

	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		Alquiler alquiler = getAlquilerAbierto(cliente);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		alquiler.devolver(fechaDevolucion);

	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Iterator<Alquiler> iterador = get(cliente).iterator();
		Alquiler alquiler = null;
		while (iterador.hasNext() && alquiler == null) {
			Alquiler siguiente = iterador.next();
			if (siguiente.getFechaDevolucion() == null) {
				alquiler = siguiente;
			}
		}

		return alquiler;
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		Alquiler alquiler = getAlquilerAbierto(vehiculo);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquiler.devolver(fechaDevolucion);

	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Iterator<Alquiler> iterador = get(vehiculo).iterator();
		Alquiler alquiler = null;
		while (iterador.hasNext() && alquiler == null) {
			Alquiler siguiente = iterador.next();
			if (siguiente.getFechaDevolucion() == null) {
				alquiler = siguiente;
			}
		}

		return alquiler;
	}

}
