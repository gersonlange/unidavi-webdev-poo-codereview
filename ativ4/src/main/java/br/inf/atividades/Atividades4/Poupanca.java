package br.inf.atividades.Atividades4;

import br.inf.atividades.Atividades4.model.Movimento;

public class Poupanca extends Conta {

	public void addRendimento(double rendimento) {
		movimento(rendimento, Movimento.RENDIMENTO);
	}
}