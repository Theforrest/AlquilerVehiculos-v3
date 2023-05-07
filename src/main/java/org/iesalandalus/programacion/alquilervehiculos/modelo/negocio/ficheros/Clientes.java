package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Clientes implements IClientes {

	private List<Cliente> coleccionClientes;
	private static Clientes instancia;
	private static final File FICHERO_CLIENTES = new File(
			String.format("%s%s%s", "datos", File.separator, "clientes.xml"));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";

	private Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	static Clientes getInstancia() {
		if (instancia == null) {
			instancia = new Clientes();
		}
		return instancia;
	}

	@Override
	public void comenzar() {
		try {
			leerDom(UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES));
		} catch (ParserConfigurationException e) {
			System.out.printf("ERROR: problema de configuración al intentar leer el fichero %s%n", FICHERO_CLIENTES);
		} catch (SAXException e) {
			System.out.printf("ERROR: fallo al intentar leer el fichero %s%n", FICHERO_CLIENTES);
		} catch (IOException e) {
			System.out.printf("ERROR: %s no ha sido encontrado%n", FICHERO_CLIENTES);
		}
	}

	private void leerDom(Document documentoXml) {
		if (documentoXml == null) {
			throw new NullPointerException("ERROR: El documento no puede ser nulo");
		}
		NodeList lista = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < lista.getLength(); i++) {
			Node nodo = lista.item(i);
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				try {
					insertar(getCliente(elemento));
				} catch (OperationNotSupportedException | IllegalArgumentException e) {
					System.out.printf("ERROR: No se ha podido insertar el cliente número %s -> %s%n", i,
							e.getMessage());
				}

			}
		}
	}

	private Cliente getCliente(Element elemento) {
		if (elemento == null) {
			throw new NullPointerException("ERROR: El elemento no puede ser nulo.");
		}
		String nombre = elemento.getAttribute(NOMBRE);
		String dni = elemento.getAttribute(DNI);
		String telefono = elemento.getAttribute(TELEFONO);

		return new Cliente(nombre, dni, telefono);
	}

	@Override
	public void terminar() {
		try {
			UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_CLIENTES);
		} catch (TransformerException e) {
			System.out.printf("Error: No se ha podido llevar a cabo la transformación del fichero %s%n",
					FICHERO_CLIENTES);
		} catch (ParserConfigurationException e) {
			System.out.printf("ERROR: problema de configuración al intentar pasar los datos al fichero %s%n",
					FICHERO_CLIENTES);

		}

	}

	private Document crearDom() throws ParserConfigurationException {
		Document documento = UtilidadesXml.crearConstructorDocumentoXml().newDocument();
		Element raiz = documento.createElement(RAIZ);
		documento.appendChild(raiz);

		for (Cliente cliente : coleccionClientes) {
			raiz.appendChild(getElemento(documento, cliente));
		}

		return documento;
	}

	private Element getElemento(Document documentoXml, Cliente cliente) {
		if (documentoXml == null) {
			throw new NullPointerException("ERROR: El documento no puede ser nulo.");
		}
		if (cliente == null) {
			throw new NullPointerException("ERROR: El cliente no puede ser nulo.");
		}
		Element elemento = documentoXml.createElement(CLIENTE);
		elemento.setAttribute(NOMBRE, cliente.getNombre());
		elemento.setAttribute(DNI, cliente.getDni());
		elemento.setAttribute(TELEFONO, cliente.getTelefono());

		return elemento;
	}

	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
		coleccionClientes.add(cliente);
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		Cliente busqueda = null;
		int index = coleccionClientes.indexOf(cliente);
		if (index != -1) {
			busqueda = coleccionClientes.get(index);
		}
		return busqueda;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (!coleccionClientes.remove(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		Cliente busqueda = buscar(cliente);
		if (busqueda == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		if (!((nombre == null || nombre.isBlank()) && (telefono == null || telefono.isBlank()))) {

			if (nombre == null || nombre.isBlank()) {
				busqueda.setTelefono(telefono);
			} else {
				if (telefono == null || telefono.isBlank()) {
					busqueda.setNombre(nombre);
				} else {
					busqueda.setNombre(nombre);
					busqueda.setTelefono(telefono);
				}
			}

		}

	}
}
