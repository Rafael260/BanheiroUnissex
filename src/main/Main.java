package main;

import banheiro.Banheiro;
import banheiro.Pessoa;

public class Main {

	public static void main(String[] args) {
		Banheiro banheiro = new Banheiro(4,10);
		Thread p1 = new Pessoa("Fulano",true,banheiro);
		Thread p2 = new Pessoa("Fulano2",true,banheiro);
		Thread pwoman = new Pessoa("Fulana",false,banheiro);
		Thread pwoman2 = new Pessoa("Fulana2",false,banheiro);
		Thread pwoman3 = new Pessoa("Fulana3",false,banheiro);
		Thread p3 = new Pessoa("Fulano3",true,banheiro);
		Thread p4 = new Pessoa("Fulano4",true,banheiro);
		p1.start();
		pwoman.start();
		p2.start();
		pwoman2.start();
		p3.start();
		pwoman3.start();
		p4.start();
	}

}
