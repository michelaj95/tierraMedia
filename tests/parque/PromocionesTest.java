package parque;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PromocionesTest {
	
	Promocion promo3x2;
	Promocion promoFinal;
	Promocion promoPorcentual;
	List <Atraccion> atracciones2;
	List <Atraccion> atracciones3;
	Usuario u1; 
	Administrador admin;
	
	@Before
	public void setUp() {
		atracciones2 = new ArrayList<Atraccion>();
		atracciones2.add(new Atraccion("Bosque Negro", 3,4,12,"Aventura"));
		atracciones2.add(new Atraccion("Mordor",25,3,4,"Aventura"));
		atracciones3 = new ArrayList<Atraccion>();
		atracciones3.add(new Atraccion("Bosque Negro", 3,4,12,"Aventura"));
		atracciones3.add(new Atraccion("Mordor",25,3,4,"Aventura"));
		atracciones3.add(new Atraccion("Moria",10,2,6,"Aventura"));
		promo3x2 = new Promocion3x2("promo3", atracciones3);
		promoPorcentual = new PromocionPorcentual("promo2", atracciones2, 20);
		promoFinal = new PromocionFinal("promo2", atracciones2, 15);
	}
	

	@Test
	public void promo3x2CalculaPrecio() {
		assertEquals(28, promo3x2.getPrecioFinal());
	}
	
	@Test
	public void promo3x2CalculaTiempo() {
		assertEquals(9, promo3x2.getTiempoTotal(), 0);
	}
	
	@Test
	public void promo3x2CorroborarTipo() {
		assertEquals("Aventura", promo3x2.getTipoPromocion());
	}
	
	@Test
	public void promoPorcentualCalculaPrecio() {
		assertEquals(23, promoPorcentual.getPrecioFinal());
	}
	
	@Test
	public void promoPorcentualCalculaTiempo() {
		assertEquals(7, promoPorcentual.getTiempoTotal(), 0);
		
	}
	
	@Test
	public void promoPorcentualCorroborarTipo() {
		assertEquals("Aventura", promoPorcentual.getTipoPromocion());
		
	}
	
	@Test
	public void promoFinalCalculaPrecio() {
		assertEquals(15, promoFinal.getPrecioFinal());
		
	}
	
	@Test
	public void promoFinalCalculaTiempo() {
		assertEquals(7, promoFinal.getTiempoTotal(), 0);
	}
	
	@Test
	public void promoFinalCorroborarTipo() {
		assertEquals("Aventura", promoFinal.getTipoPromocion());
	}
}
