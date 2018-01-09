package br.inf.atividades.Atividades4.model;

public enum Movimento {
		RENDIMENTO ("Rendimento"),
		DEPOSITO ("Depósito"),
		SAQUE ("Saque");
	
	private final String nome;
	
	private Movimento(String s) {
		nome = s;
	}

	@Override
	public String toString() {
		return this.nome;
	}
}
