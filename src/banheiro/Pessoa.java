package banheiro;

import java.util.Random;

public class Pessoa extends Thread{

	private String nome;
	private boolean homem;
	private Banheiro banheiro;
	private int tempoNoBanheiro;
	
	public Pessoa(String nome, boolean homem, Banheiro banheiro){
		this.homem = homem;
		this.nome = homem ? "Homem: " : "Mulher: ";
		this.nome += nome;
		Random random = new Random();
		this.banheiro = banheiro;
		this.tempoNoBanheiro = random.nextInt(banheiro.getTempoMaximo()) + 1;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isHomem() {
		return homem;
	}
	public void setHomem(boolean ehHomem) {
		this.homem = ehHomem;
	}
	public int getTempoNoBanheiro() {
		return tempoNoBanheiro;
	}
	public void setTempoNoBanheiro(int tempoNoBanheiro) {
		this.tempoNoBanheiro = tempoNoBanheiro;
	}
	
	public String getSexo(){
		return homem ? "Homem" : "Mulher";
	}
	

	@Override
	public void run() {
		System.out.println(this.nome + " vai entrar no banheiro");
		//Adquiro o lock para mexer na lista de pessoas que estao usando o banheiro, que eh recurso compartilhado
		banheiro.lock.lock();
		try {
			banheiro.entrar(this);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Depois de conseguir inserir a pessoa na lista, ja podemos liberar o lock para que outras pessoas tentem entrar ou sair
		banheiro.lock.unlock();
		System.out.println(this.nome+" entrou no banheiro");
		//Hora das necessidades...
		try {
			Thread.sleep(tempoNoBanheiro*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Adquire lock para poder retirar a pessoa da lista de pessoas que estao usando
		banheiro.lock.lock();
		banheiro.sair(this);
		banheiro.lock.unlock();
		
		System.out.println(this.nome+" saiu do banheiro");
	}
	
}
