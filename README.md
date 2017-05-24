# BanheiroUnissex
Trabalho para a disciplina de Programação Concorrente, pelo curso de Tecnologia da Informação - UFRN

DESCRIÇÃO DO PROBLEMA
Um escritório contém um banheiro que pode ser utilizado tanto por homens quanto por mulheres,
mas não por ambos ao mesmo tempo. Se um homem estiver no banheiro, outros homens podem
entrar, porém eventuais mulheres que desejem utilizar o banheiro devem esperar ele ficar vazio. Se
uma mulher estiver no banheiro, outras mulheres podem entrar, porém eventuais homens que desejem
utilizar o banheiro devem esperar ele ficar vazio. Cada pessoa (homem ou mulher) pode passar um
tempo utilizando o banheiro.
Projete e implemente uma solução concorrente para o problema. O programa deve exibir a entrada e
saída de uma pessoa (homem ou mulher) do banheiro bem como quantas pessoas (homens ou mulheres)
estão no banheiro no momento. Por ser um espaço de tamanho relativamente diminuto, o banheiro
possui uma capacidade limite de pessoas C (fornecida como entrada via linha de comando ou prefixada
como um valor constante) que podem utiliza-lo ao mesmo tempo e o tempo que cada pessoa passa no
banheiro é randômico e diferente a cada execução do programa.
