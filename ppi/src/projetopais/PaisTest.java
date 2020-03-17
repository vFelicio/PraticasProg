package projetopais;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaisTest {

	Pais pais, copia;
	static int id = 8;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("setup");
		pais = new Pais(id, "Mexico", 123675325, 1958201 );
		copia = new Pais(id, "Mexico", 123675325, 1958201 );
		System.out.println(pais);
		System.out.println(copia);
	}
	
	@Test
	public void test00Carregar() {
		System.out.println("carregar");
		Pais fixture = new Pais(1, "Brasil", 210147125, 8515727 );
		Pais novo = new Pais(1, null, 0, 0);
		novo.carregar();
		System.out.println(novo);
		assertEquals("Testa metodo carregar", novo, fixture);
	}

	@Test
	public void test01Criar() {
		System.out.println("criar");
		pais.incluir();
		id = pais.getId();
		System.out.println(id);
		copia.setId(id);
		assertEquals("testa criacao", pais, copia);
	}

	@Test
	public void test02Atualizar() {
		System.out.println("atualizar");
		pais.setPopulacao(125000000);
		copia.setPopulacao(125000000);		
		pais.atualizar();
		System.out.println(pais +"\n"+copia);
		pais.carregar();
		assertEquals("testa atualizacao", pais, copia);
	}

	@Test
	public void test03Excluir() {
		System.out.println("excluir");
		copia.setId(-1);
		copia.setNome(null);
		copia.setArea(0);
		copia.setPopulacao(0);
		pais.excluir();
	    pais.carregar();
		System.out.println(pais +"\n"+copia);
		assertEquals("testa exclusao", pais, copia);
	}

	@Test
	public void test04MaiorPopulacao() {
		System.out.println("Maior populacao");
		Pais maior = new Pais(4, "China", 1394550000, 9596961 );
		Pais maiorbd = new Pais();
	    maiorbd = maiorbd.maiorpopulacao();
		System.out.println(maiorbd +"\n"+maior);
		assertEquals("testa maior populacao", maiorbd, maior);
	}
	
	@Test
	public void test05MenorArea() {
		System.out.println("Menor Area");
		Pais menor = new Pais(7, "Coreia do Sul", 51811167, 99016 );
		Pais menorbd = new Pais();
	    menorbd = menorbd.menorarea();
		System.out.println(menorbd +"\n"+menor);
		assertEquals("testa maior populacao", menorbd, menor);
	}
	
	@Test
	public void test06VetorTresPaises() {
		System.out.println("Vetor com tres paises");
		Pais paises[] = new Pais[3];
		paises[0] = new Pais(1, "Brasil", 210147125, 8515727);
		paises[1] = new Pais(2, "Estados Unidos", 328700000, 9371174);
		paises[2] = new Pais(3, "Italia", 60390560, 301338);
		Pais[] paisesbd = new Pais[3];
        Pais novoPais = new Pais();
        paisesbd = novoPais.tresPaises();
		for (int j = 0; j < paisesbd.length; j++) {
			assertEquals("testa vetor de tres paises", paises[j], paisesbd[j]);
		}
		
	}
	
}

