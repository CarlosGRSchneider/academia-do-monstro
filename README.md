# academia-do-monstro

Bem vindos ao projeto da academia do monstro! 
Esse é um projeto pessoal que eu desenvolvi, porque eu quis voltar pra academia, e eu comecei a realizar algumas planilhas para fazer o acompanhamento e evolução dos meus treinos. Eu estava montando as planilhas no excel, e tive que me deparar com um problema que todo programador passa durante a sua vida: porque fazer um trabalho manual que levaria 15 minutos quando eu posso passar 30 horas automatizando o processo?

Assim surgiu a academia do monstro.

A estrutura é criar exercicios, atribuir pesos e repetições para eles, e com isso montar treinos. Por fim, há uma classe para anotar o treino que eu realizei em cada dia.
Os métodos que eu vou montando são baseados no que eu necessito para o momento, e todas as funcionalidades possuem testes.

Se você não possui o postgres, mas quiser rodar a aplicação, pode substituir o application.properties com os valores do h2, que estão comentados.

# Roadmap do que ainda pretendo implementar

  * classe de Diaria, para marcar os treinos que eu realizo e o dia.
  * método de adicionar e remover exercicios em um treino, para organizar pontualmente um treino
  * scheduler para remover os treinos que não foram realizados em mais de 15 dias e um método para get treinos ativos
  * classe de Metas, para escrever algumas metas que pretendo alcançar.
