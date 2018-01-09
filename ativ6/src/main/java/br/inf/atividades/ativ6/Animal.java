package br.inf.atividades.ativ6;

import br.inf.atividades.ativ6.model.Raca;

public class Animal {
	private String nome;
	private Raca raca;

	public Animal setNome(String nome) {
		this.nome = nome;

		return this;
	}
	
	public String getNome() {
		return nome;
	}

	public Animal setRaca(Raca raca) {
		this.raca = raca;

		return this;
	}

	public Raca getRaca() {
		return raca;
	}
}