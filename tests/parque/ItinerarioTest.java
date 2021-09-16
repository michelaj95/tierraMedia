package parque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

public class ItinerarioTest {
	Usuario[] usuarios;
	Atraccion[] atracciones;
	Promocion[] promociones;
	Administrador admin;
	CargaDiaria carga;

	@Before
	public void setUp() {
		Usuario u1 = new Usuario("Fernanda", 100, 50, "Paisaje");
		Usuario u2 = new Usuario("Michela", 70, 40, "Degustacion");
		Usuario u3 = new Usuario("Pedro", 310, 30, "Aventura");
		Usuario u4 = new Usuario("Javier", 40, 20, "Aventura");

		usuarios = new Usuario[] { u1, u2, u3, u4 };

		Atraccion a1 = new Atraccion("Saavedra", 50, 5, 10, "Aventura");
		Atraccion a2 = new Atraccion("Belgrano", 20, 30, 10, "Degustacion");
		Atraccion a3 = new Atraccion("MardelPlata", 60, 10, 10, "Paisaje");
		Atraccion a4 = new Atraccion("Constitucion", 200, 3, 10, "Aventura");

		atracciones = new Atraccion[] { a1, a2, a3, a4 };
		admin = new Administrador();
		admin.setUsuarios(usuarios);
		admin.setAtracciones(atracciones);
		
		carga = new CargaDiaria();
	}
	
	@Test
	public void testItinerario() throws IOException {
		usuarios[0].agregarAtraccionAItinerario(atracciones[0]);
		usuarios[0].agregarAtraccionAItinerario(atracciones[1]);
		usuarios[1].agregarAtraccionAItinerario(atracciones[2]);
		usuarios[1].agregarAtraccionAItinerario(atracciones[3]);
		usuarios[2].agregarAtraccionAItinerario(atracciones[0]);
		usuarios[2].agregarAtraccionAItinerario(atracciones[1]);
		usuarios[3].agregarAtraccionAItinerario(atracciones[2]);
		usuarios[3].agregarAtraccionAItinerario(atracciones[3]);
		
		carga.escribirArchivoDeSalida(usuarios, "pruebaItinerario.txt");
		
		 List <String> listaElementosArchivo = new ArrayList<String>();
		 
		 try {
			File myObj = new File("pruebaItinerario.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {

				listaElementosArchivo.add(myReader.nextLine());
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		 
		 assertTrue(listaElementosArchivo.contains("Saavedra"));
		 assertTrue(listaElementosArchivo.contains("Belgrano"));
		 assertTrue(listaElementosArchivo.contains("MardelPlata"));
		 assertTrue(listaElementosArchivo.contains("Constitucion"));
		 assertTrue(listaElementosArchivo.contains("-----CLIENTE: MICHELA-----"));
		 assertTrue(listaElementosArchivo.contains("-----CLIENTE: FERNANDA-----"));
		 assertTrue(listaElementosArchivo.contains("-----CLIENTE: PEDRO-----"));
		 assertTrue(listaElementosArchivo.contains("-----CLIENTE: JAVIER-----"));
		
	}
}

	