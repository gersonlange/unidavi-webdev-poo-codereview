package br.edu.unidavi.webdev.poo.basics;

public class Hangman {

	private boolean gotcha = false, won;
	private int wrongs = 0;
	private int limit = 6;
	private char[] placeholder, pass;

	public Hangman(String pass) {
		this.pass = pass.toCharArray();
		placeholder = new char[pass.length()];
		for (int i = 0; i < pass.length(); i++)
			placeholder[i] = '*';
	}
	
	public String play(char attempt) {
		boolean hit = false;
		gotcha = true;
		String placeholderAsString = "";
		for (int i = 0; i < pass.length ; i++) {
			if (pass[i] == attempt) {
				placeholder[i] = attempt;
				hit = true;
			}
			gotcha = gotcha & (pass[i] == placeholder[i]);
			placeholderAsString += placeholder[i];
		}
		if (!hit)
			wrongs++;

		won = ! (wrongs < limit && !gotcha) && gotcha;
		return placeholderAsString;
	}
	
	public int getWrongAttempts() {
		return wrongs;
	}
	
	public boolean won() {
		return won;
	}
}