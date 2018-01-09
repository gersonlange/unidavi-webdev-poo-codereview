package br.inf.atividades.Atividades4;

import br.inf.atividades.Atividades4.model.Movimento;

public class Conta
{
	private String codigoBanco, banco, codigoConta, correntista;
	private StringBuilder extrato = new StringBuilder();
	private double saldo;

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public void setCodigoConta(String codigoConta) {
		this.codigoConta = codigoConta;
	}

	public void setCorrentista(String correntista) {
		this.correntista = correntista;
	}

	public void addDeposito(double valor)
	{
		movimento(valor, Movimento.DEPOSITO);
	}

	public void addSaque(double valor)
	{
		movimento(-valor, Movimento.SAQUE);
	}
	
	
	protected StringBuilder demaisDados()
	{
		return new StringBuilder();
	}

	public String getExtrato()
	{
		StringBuilder valor = new StringBuilder()
			.append("Banco: ")
			.append(codigoBanco)
			.append(" - ")
			.append(banco)
			.append("\n")
			.append("Conta: ")
			.append(codigoConta)
			.append(" - ")
			.append(correntista)
			.append("\n")
			.append("Saldo: ")
			.append(saldo)
			.append("\n")
			.append(demaisDados())
			.append(extrato);

		return valor.toString();
	}

	protected void movimento(double valor, Movimento descricao) {
		saldo += valor;

		extrato.append(descricao.toString());
		extrato.append(" de :");
		extrato.append(Math.abs(valor));
		extrato.append("\n");
	}
}