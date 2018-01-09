package br.inf.atividades.Atividades4;

import org.junit.Test;

public class ContaCorrentTest {
	
	@Test
	public void deveMovimentarPoupanca()
	{
		Poupanca p = new Poupanca();
		p.setBanco("Banco do Brasil");
		p.setCodigoBanco("1");
		p.setCodigoConta("1234");
		p.setCorrentista("Joao da Silva");
		p.addDeposito(10);
		p.addRendimento(1);
		p.addSaque(3);
		System.out.println(p.getExtrato());
	}
	
	@Test
	public void deveMovimentarContaCorrente()
	{
		ContaCorrente p = new ContaCorrente();
		p.setBanco("Banco do Brasil");
		p.setCodigoBanco("1");
		p.setCodigoConta("1234");
		p.setCorrentista("Joao da Silva");
		p.setLimiteEspecial(1000);
		p.addDeposito(10);
		p.addSaque(3);
		System.out.println(p.getExtrato());
	}
}
