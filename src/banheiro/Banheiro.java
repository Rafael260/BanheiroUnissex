package banheiro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banheiro {
	private int numeroVagas;
	//Controle de quais pessoas estao entrando
	private String sexo;
	//Tempo maximo que uma pessoa passa no banheiro
	private int tempoMaximo;
	private List<Pessoa> pessoasUsando;
	
	//Variaveis para sincronizacao
	public Lock lock;
	public Condition sexoAtual;
	public Condition banheiroLotado;
	
	public Banheiro(int vagas, int tempoMaximo) {
		this.numeroVagas = vagas;
		this.sexo = "";
		this.tempoMaximo = tempoMaximo;
		this.pessoasUsando = new ArrayList<>();
		lock = new ReentrantLock();
		sexoAtual = lock.newCondition();
		banheiroLotado = lock.newCondition();
	}

	public int getNumeroVagas() {
		return numeroVagas;
	}

	public void setNumeroVagas(int vagas) {
		this.numeroVagas = vagas;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getNumeroPessoas() {
		return this.pessoasUsando.size();
	}
	
	public int getTempoMaximo() {
		return tempoMaximo;
	}

	public void setTempoMaximo(int tempoMaximo) {
		this.tempoMaximo = tempoMaximo;
	}

	public boolean banheiroVazio(){
		return this.pessoasUsando.isEmpty();
	}
	
	public boolean banheiroCheio(){
		return this.pessoasUsando.size() == numeroVagas;
	}
	
	public boolean possuiVagas(){
		return this.pessoasUsando.size() < this.numeroVagas;
	}
	
	public void adicionarPessoa(Pessoa pessoa){
		this.pessoasUsando.add(pessoa);
	}
	
	public void removerPessoa(Pessoa pessoa){
		this.pessoasUsando.remove(pessoa);
	}
	
	public void entrar(Pessoa pessoa) throws InterruptedException{
		//Enquanto tiverem pessoas do outro sexo, eh preciso aguardar
		while(!this.sexo.isEmpty() && !this.sexo.equals(pessoa.getSexo())){
			//Marco essa opção para que outras pessoas do mesmo sexo das que estao no banheiro nao passem na frente, garantindo justica
			this.setSexo("barrando pessoas do sexo diferente");
			sexoAtual.await();
		}
		//Enquanto o banheiro tiver lotado, espera tambem (nessa situacao, ja estará esperando por pessoas do mesmo sexo sairem)
		while(this.banheiroCheio()){
			banheiroLotado.await();
		}
		//adiciono a pessoa na lista de pessoas que estao usando o banheiro
		this.adicionarPessoa(pessoa);
		//Se for a primeira pessoa, deve indicar qual sexo dela para que as outras pessoas saibam
		if (this.pessoasUsando.size() == 1){
			this.setSexo(pessoa.getSexo());
		}
	}
	
	public void sair(Pessoa pessoa){
		//Remove a pessoa da lista das pessoas que estao usando
		removerPessoa(pessoa);
		//Se antes o banheiro tava lotado, avisa que nao esta mais, para desbloquear alguem
		if (this.pessoasUsando.size() == this.numeroVagas - 1){
			banheiroLotado.signalAll();
		}
		//Se o banheiro ta vazio, esta livre para que qualquer pessoa (homem ou mulher) possa entrar
		if (this.banheiroVazio()){
			this.setSexo("");
			sexoAtual.signalAll();
		}
	}
}
