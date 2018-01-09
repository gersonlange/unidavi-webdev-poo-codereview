package br.inf.atividades.ativ6;

import org.junit.Test;

import br.inf.atividades.ativ6.model.Raca;

public class AnimalTest {
	@Test
	public void deveAdicionarCachorroNaoTem()
	{
		Cliente cli = new Cliente();
		cli.addAnimal(new Cachorro()
				.setNome("Bilulu")
				.setRaca(Raca.GUAPECA) );
		System.out.println( cli.findByName("Chablau") );
	}

	@Test
	public void deveAdicionarCachorroTem()
	{
		Cliente cli = new Cliente();
		cli.addAnimal(new Cachorro()
				.setNome("Bilulu")
				.setRaca(Raca.GUAPECA) );
		System.out.println( cli.findByName("Bilulu") );
	}

	@Test
	public void deveAdicionarGatoNaoTem()
	{
		Cliente cli = new Cliente();
		cli.addAnimal(new Cachorro()
				.setNome("Missi")
				.setRaca(Raca.GUAPECA) );
		System.out.println( cli.findByName("Chablau") );
		System.out.println( cli.findByRaca(Raca.GUAPECA) );
	}

	@Test
	public void deveAdicionarGatoTem()
	{
		Cliente cli = new Cliente();
		cli.addAnimal(new Gato()
				.setNome("Missi")
				.setRaca(Raca.PERDE_PELO) );
		System.out.println( cli.findByName("Peludo") );
		System.out.println( cli.findByRaca(Raca.VIRALATA) );
	}
}
