package parque;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CargaDiaria {
	public Atraccion[] obtenerAtraccionesDesdeArchivo() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		Atraccion[] atracciones = null;

		try {
			archivo = new File("entrada/atracciones.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			int cantidad = Integer.parseInt(br.readLine());
			atracciones = new Atraccion[cantidad];
			int indice = 0;

			String linea = br.readLine();
			while (linea != null) {
				String[] datosAtracciones = linea.split(",");
				String nombreAtraccion = datosAtracciones[0];
				int costoAtraccion = Integer.parseInt(datosAtracciones[1]);
				double tiempoAtraccion = Double.parseDouble(datosAtracciones[2]);
				int cupoAtraccion = Integer.parseInt(datosAtracciones[3]);
				String tipoAtraccion = datosAtracciones[4];
				atracciones[indice++] = new Atraccion(nombreAtraccion, costoAtraccion, tiempoAtraccion, cupoAtraccion, tipoAtraccion);
				linea = br.readLine();
			}

			return atracciones;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();

			}
		}

		return atracciones;
	}

	public Usuario[] obtenerUsuariosDesdeArchivo() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		Usuario[] usuarios = null;

		try {
			archivo = new File("entrada/usuarios.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			int cantidad = Integer.parseInt(br.readLine());
			usuarios = new Usuario[cantidad];
			int indice = 0;

			String linea = br.readLine();
			while (linea != null) {
				String[] datosUsuarios = linea.split(",");
				String nombreUsuario = datosUsuarios[0];
				String tipoPreferencia = datosUsuarios[1];
				int dineroUsuario = Integer.parseInt(datosUsuarios[2]);
				double tiempoUsuario = Double.parseDouble(datosUsuarios[3]);
				usuarios[indice++] = new Usuario(nombreUsuario, dineroUsuario, tiempoUsuario, tipoPreferencia);
				linea = br.readLine();
			}

			return usuarios;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();

			}
		}

		return usuarios;
	}
	
	public Promocion[] obtenerPromocionesDesdeArchivo(Administrador admin) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		Promocion[] promociones = null;
		List <Atraccion> atraccionesPromo;
		Atraccion primeraAtraccion;
		Atraccion segundaAtraccion;
		Atraccion terceraAtraccion;
		
		try {
			archivo = new File("entrada/promociones.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			int cantidad = Integer.parseInt(br.readLine());
			promociones = new Promocion[cantidad];
			int indice = 0;

			String linea = br.readLine();
			while (linea != null) {
				String[] datosPromociones = linea.split(",");
				String nombrePromocion = datosPromociones[0];
				String primeraAtraccionString = datosPromociones[1];
				String segundaAtraccionString = datosPromociones[2];
				String tipoPromocion = datosPromociones[3];
				if(tipoPromocion.contains("%")){
					primeraAtraccion = admin.obtenerAtraccionPorNombre(primeraAtraccionString);
					segundaAtraccion = admin.obtenerAtraccionPorNombre(segundaAtraccionString);
					atraccionesPromo = new ArrayList<Atraccion>();
						atraccionesPromo.add(0, primeraAtraccion);
						atraccionesPromo.add(1, segundaAtraccion);
					String[] datosPorcentaje = datosPromociones[3].split("%");
					int porcentaje = Integer.parseInt(datosPorcentaje[0]);
					promociones[indice++] = new PromocionPorcentual(nombrePromocion, atraccionesPromo, porcentaje);
					linea = br.readLine();
				}
				else if(tipoPromocion.chars().allMatch( Character::isDigit )){
					primeraAtraccion = admin.obtenerAtraccionPorNombre(primeraAtraccionString);
					segundaAtraccion = admin.obtenerAtraccionPorNombre(segundaAtraccionString);
					atraccionesPromo = new ArrayList<Atraccion>();
					atraccionesPromo.add(0, primeraAtraccion);
					atraccionesPromo.add(1, segundaAtraccion);
					int precioFinalPromo = Integer.parseInt(tipoPromocion);
					promociones[indice++] = new PromocionFinal(nombrePromocion, atraccionesPromo, precioFinalPromo);
					linea = br.readLine();
				}else {
					primeraAtraccion = admin.obtenerAtraccionPorNombre(primeraAtraccionString);
					segundaAtraccion = admin.obtenerAtraccionPorNombre(segundaAtraccionString);
					terceraAtraccion = admin.obtenerAtraccionPorNombre(tipoPromocion);
					atraccionesPromo = new ArrayList<Atraccion>();
					atraccionesPromo.add(0, primeraAtraccion);
					atraccionesPromo.add(1, segundaAtraccion);
					atraccionesPromo.add(2, terceraAtraccion);
					promociones[indice++] = new Promocion3x2(nombrePromocion, atraccionesPromo);
					linea = br.readLine();
				}
			}
			return promociones;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();

			}
		}

		return promociones;
	}
	
	public void escribirArchivoDeSalida(Usuario[] usuarios, String file) throws IOException{
		
		PrintWriter salida = new PrintWriter(new FileWriter(file));
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaActual = new Date();
		int totalClientes = 0;
		int totalRecaudado = 0;
		salida.println("Itinerarios del dia: " + formatoFecha.format(fechaActual));
		for(Usuario usu : usuarios) {
			salida.println("-----CLIENTE: " + usu.getNombre().toUpperCase() + "-----" );
			usu.escribirItinerario(salida);
			totalClientes++;
			totalRecaudado += usu.getPresupuestoUsado();
		}
		salida.println("-------------------TOTAL CLIENTES DEL D�A DE HOY: " + totalClientes + "-------------------");
		salida.println("-------------------TOTAL RECAUDADO EL D�A DE HOY: $" + totalRecaudado + "-------------------");
		salida.close();
	}
}
