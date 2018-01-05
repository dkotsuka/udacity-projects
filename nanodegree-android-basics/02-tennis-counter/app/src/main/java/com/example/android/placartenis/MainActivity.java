package com.example.android.placartenis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //criando as referências para as TextViews do app
    TextView placarUm;
    TextView placarDois;
    TextView[] tvSetsJogUm = new TextView[5];
    TextView[] tvSetsJogDois = new TextView[5];
    TextView narracao;

    int pontosUm = 0;
    int pontosDois = 0;
    String[] scoresGame = new String[5];
    int[] gamesUm = new int[5];
    int[] gamesDois = new int[5];
    int setsUm = 0;
    int setsDois = 0;

    int setAtual = 0;
    boolean tieBreak = false;
    boolean partidaEncerrada = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conecta as TextViews do app a uma referência
        placarUm = (TextView) findViewById(R.id.score_one);
        placarDois = (TextView) findViewById(R.id.score_two);
        narracao = (TextView) findViewById(R.id.status_jogo);

        // Organiza as TextViews dos sets em uma array do tipo TextView.
        tvSetsJogUm[0] = (TextView) findViewById(R.id.one_set1);
        tvSetsJogUm[1] = (TextView) findViewById(R.id.one_set2);
        tvSetsJogUm[2] = (TextView) findViewById(R.id.one_set3);
        tvSetsJogUm[3] = (TextView) findViewById(R.id.one_set4);
        tvSetsJogUm[4] = (TextView) findViewById(R.id.one_set5);
        tvSetsJogDois[0] = (TextView) findViewById(R.id.two_set1);
        tvSetsJogDois[1] = (TextView) findViewById(R.id.two_set2);
        tvSetsJogDois[2] = (TextView) findViewById(R.id.two_set3);
        tvSetsJogDois[3] = (TextView) findViewById(R.id.two_set4);
        tvSetsJogDois[4] = (TextView) findViewById(R.id.two_set5);

        //valores da pontuação dos games.
        scoresGame[0] = "0";
        scoresGame[1] = "15";
        scoresGame[2] = "30";
        scoresGame[3] = "40";
        scoresGame[4] = "40+";
    }
    // --------ENTRADAS DO USUÁRIO --------------------------------

    //adiciona os pontos aos jogadores e chama o método que faz a contagem do game.
    public void pontoJogadorUm(View view) {
        if (!partidaEncerrada) {
            narracao.setText("");
            pontosUm += 1;
            controleGame();
        }
    }

    public void pontoJogadorDois(View view) {
        if (!partidaEncerrada) {
            narracao.setText("");
            pontosDois += 1;
            controleGame();
        }
    }

    public void zerarTudo(View view) {
        pontosUm = 0;
        pontosDois = 0;
        setsUm = 0;
        setsDois = 0;
        tieBreak = false;
        partidaEncerrada = false;
        setAtual = 0;
        for (int i = 0; i < 5; ++i) {
            gamesUm[i] = 0;
            gamesDois[i] = 0;
            tvSetsJogUm[i].setText("" + gamesUm[i]);
            tvSetsJogDois[i].setText("" + gamesDois[i]);
        }
        narracao.setText("");
        atualizaPlacar();
        Log.i("games J1", "" + gamesUm[0] + gamesUm[1] + gamesUm[2] + gamesUm[3] + gamesUm[4]);

    }
    // ---------ENTRADAS DO USUÁRIO------------------------------------

    //faz a contagem dos pontos do game e chama o método que faz a contagem dos sets.
    //A pontuação é contada na sequencia: 0,15,30,40 e "game".
    //O jogador precisa "fazer o game" com pelo menos duas pontuações de vantagem.
    //Se estiver num tiebreak, quem fizer o mínimo de 7 pontos e 2 pontos de vantagem, leva o game.
    private void controleGame() {
        if (!tieBreak) {
            if (pontosUm >= 4 && pontosUm - pontosDois >= 2 && !tieBreak) {
                pontosUm = 0;
                pontosDois = 0;
                gamesUm[setAtual] += 1;
                controleSet();
            } else if (pontosDois >= 4 && pontosDois - pontosUm >= 2 && !tieBreak) {
                pontosUm = 0;
                pontosDois = 0;
                gamesDois[setAtual] += 1;
                controleSet();
            } else if (pontosUm == 4 && pontosDois == 4) {
                pontosUm -= 1;
                pontosDois -= 1;
                atualizaPlacar();
            } else {
                atualizaPlacar();
            }
        } else if (tieBreak) {
            narracao.setText(paraString(R.string.tiebreak));
            if (pontosUm >= 7 && pontosUm - pontosDois >= 2) {
                narracao.setText(paraString(R.string.playerone) + " " + paraString(R.string.fechouSet));
                pontosUm = 0;
                pontosDois = 0;
                tieBreak = false;
                gamesUm[setAtual] += 1;
                setsUm += 1;
                controlePartida();
                proximoSet();
            } else if (pontosDois >= 7 && pontosDois - pontosUm >= 2) {
                narracao.setText(paraString(R.string.playertwo) + " " + paraString(R.string.fechouSet));
                pontosUm = 0;
                pontosDois = 0;
                tieBreak = false;
                gamesDois[setAtual] += 1;
                setsDois += 1;
                controlePartida();
                proximoSet();
            } else {
                placarUm.setText("" + pontosUm);
                placarDois.setText("" + pontosDois);
            }
        }
    }

    //faz o controle da pontuaçao dos sets.
    //quem conseguir fechar 6 games com pelo menos 2 de vantagem leva o set.
    private void controleSet() {
        if (gamesUm[setAtual] >= 6 && gamesUm[setAtual] - gamesDois[setAtual] >= 2) {
            narracao.setText(paraString(R.string.playerone) + " " + paraString(R.string.fechouSet));
            setsUm += 1;
            controlePartida();
            proximoSet();
        } else if (gamesDois[setAtual] >= 6 && gamesDois[setAtual] - gamesUm[setAtual] >= 2) {
            narracao.setText(paraString(R.string.playertwo) + " " + paraString(R.string.fechouSet));
            setsDois += 1;
            controlePartida();
            proximoSet();
        } else if (gamesUm[setAtual] == 6 && gamesDois[setAtual] == 6) {
            tieBreak = true;
            atualizaPlacar();
        } else {
            atualizaPlacar();
        }
    }

    //Verifica se o jogo acabou ou não.
    private void controlePartida() {
        if (setsUm == 3) {
            narracao.setText(paraString(R.string.playerone) + " " + paraString(R.string.venceu)+ " "
                    + setsUm + " " + paraString(R.string.sets)+ " " + setsDois + ".");
            atualizaPlacar();
            partidaEncerrada = true;
        } else if (setsDois == 3) {
            narracao.setText(paraString(R.string.playertwo) + " " + paraString(R.string.venceu)+ " "
                    + setsDois+ " " + paraString(R.string.sets)+ " " + setsUm + ".");
            atualizaPlacar();
            partidaEncerrada = true;
        } else {
            atualizaPlacar();
        }
    }

    // atualiza as TextViews com os números atualizados.
    private void atualizaPlacar() {
        placarUm.setText("" + scoresGame[pontosUm]);
        placarDois.setText("" + scoresGame[pontosDois]);
        for (int i = 0; i < 5; ++i) {
            tvSetsJogUm[i].setText("" + gamesUm[i]);
            tvSetsJogDois[i].setText("" + gamesDois[i]);
        }
    }

    //Verifica se o próximo set não é o ultimo.
    private void proximoSet() {
        if (setAtual < 5) {
            setAtual += 1;
        }
    }

    private String paraString(int id){
        return getResources().getString(id);
    }

}