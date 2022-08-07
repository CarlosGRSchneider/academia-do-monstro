# academia-do-monstro

Bem vindos ao projeto da academia do monstro! 
Esse é um projeto pessoal que eu desenvolvi, porque eu quis voltar pra academia, e eu comecei a realizar algumas planilhas para fazer o acompanhamento e evolução dos meus treinos. Eu estava montando as planilhas no excel, e tive que me deparar com um problema que todo programador passa durante a sua vida: porque fazer um trabalho manual que levaria 15 minutos quando eu posso demorar 30 horas automatizando o processo?

Assim surgiu a academia do monstro.

A estrutura do projeto consiste em criar exercicios, atribuir pesos e repetições para eles, e com isso montar treinos. Por fim, há uma classe Diaria, para anotar o treino que eu realizei em cada dia.
Os métodos que eu vou montando são baseados no que eu necessito para o momento, e todas as funcionalidades possuem testes.

Se você não possui o postgres, mas quiser rodar a aplicação, pode substituir o application.properties com os valores do h2, que estão comentados.

# Roadmap do que ainda pretendo implementar

  * método de adicionar e remover exercicios em um treino, para organizar pontualmente um treino
  * classe de Metas, para escrever algumas metas que pretendo alcançar.
  * colocar metodos put e delete para exercicio
  * entender porque os testes não estão rodando em suite, e porque o teste de treino/patch lança exception
