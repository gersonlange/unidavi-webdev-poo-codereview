package br.inf.atividades.ativ6;

import java.util.Vector;

import br.inf.atividades.ativ6.model.Raca;

public class Cliente {
	private Vector<Animal> animal;
	
	public Cliente() {
		animal = new Vector<>();
	}

	public void addAnimal(Animal animal) {
		this.animal.add(animal);
	}

	public Animal findByName(String name)
	{
		for (Animal a : animal)
		{
			if ( a.getNome().equals(name) )
				return a;
		}

		return null;
	}

	public Animal findByRaca(Raca raca)
	{
		for (Animal a : animal)
		{
			if ( a.getRaca().equals(raca) )
				return a;
		}

		return null;
	}
}
