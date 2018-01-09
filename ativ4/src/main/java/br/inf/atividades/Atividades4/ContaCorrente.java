package br.inf.atividades.Atividades4;

public class ContaCorrente extends Conta {

	private double limiteEspecial;

	public void setLimiteEspecial(double limiteEspecial) {
		this.limiteEspecial = limiteEspecial;
	}
	
	@Override
	protected StringBuilder demaisDados() {
		return new StringBuilder()
				.append("Limite Especial: ")
				.append(limiteEspecial)
				.append("\n");
	}

}
