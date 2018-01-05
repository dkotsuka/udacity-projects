package com.example.android.videogamequizz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName;
    int score;
    int selected = 0;
    boolean allAnswer;

    RadioButton answerOneA;
    RadioButton answerOneB;
    RadioButton answerOneC;
    RadioButton answerOneD;
    EditText answerTwo;
    RadioButton answerThreeA;
    RadioButton answerThreeB;
    RadioButton answerThreeC;
    RadioButton answerThreeD;
    RadioButton answerFourA;
    RadioButton answerFourB;
    RadioButton answerFourC;
    RadioButton answerFourD;
    CheckBox answerFiveA;
    CheckBox answerFiveB;
    CheckBox answerFiveC;
    CheckBox answerFiveD;
    CheckBox answerFiveE;
    CheckBox answerFiveF;
    CheckBox answerFiveG;
    CheckBox answerFiveH;
    RadioButton answerSixA;
    RadioButton answerSixB;
    RadioButton answerSixC;
    RadioButton answerSixD;
    RadioButton answerSevenA;
    RadioButton answerSevenB;
    RadioButton answerSevenC;
    RadioButton answerSevenD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.user_name);

        answerOneA = (RadioButton) findViewById(R.id.quest_one_A);
        answerOneB = (RadioButton) findViewById(R.id.quest_one_B);
        answerOneC = (RadioButton) findViewById(R.id.quest_one_C);
        answerOneD = (RadioButton) findViewById(R.id.quest_one_D);
        answerTwo = (EditText) findViewById(R.id.answer_two);
        answerThreeA = (RadioButton) findViewById(R.id.quest_three_a);
        answerThreeB = (RadioButton) findViewById(R.id.quest_three_b);
        answerThreeC = (RadioButton) findViewById(R.id.quest_three_c);
        answerThreeD = (RadioButton) findViewById(R.id.quest_three_d);
        answerFourA = (RadioButton) findViewById(R.id.quest_four_a);
        answerFourB = (RadioButton) findViewById(R.id.quest_four_b);
        answerFourC = (RadioButton) findViewById(R.id.quest_four_c);
        answerFourD = (RadioButton) findViewById(R.id.quest_four_d);
        answerFiveA = (CheckBox) findViewById(R.id.quest_five_a);
        answerFiveB = (CheckBox) findViewById(R.id.quest_five_b);
        answerFiveC = (CheckBox) findViewById(R.id.quest_five_c);
        answerFiveD = (CheckBox) findViewById(R.id.quest_five_d);
        answerFiveE = (CheckBox) findViewById(R.id.quest_five_e);
        answerFiveF = (CheckBox) findViewById(R.id.quest_five_f);
        answerFiveG = (CheckBox) findViewById(R.id.quest_five_g);
        answerFiveH = (CheckBox) findViewById(R.id.quest_five_h);
        answerSixA = (RadioButton) findViewById(R.id.quest_six_a);
        answerSixB = (RadioButton) findViewById(R.id.quest_six_b);
        answerSixC = (RadioButton) findViewById(R.id.quest_six_c);
        answerSixD = (RadioButton) findViewById(R.id.quest_six_d);
        answerSevenA = (RadioButton) findViewById(R.id.quest_seven_a);
        answerSevenB = (RadioButton) findViewById(R.id.quest_seven_b);
        answerSevenC = (RadioButton) findViewById(R.id.quest_seven_c);
        answerSevenD = (RadioButton) findViewById(R.id.quest_seven_d);
    }

    public void calculateScores(View view){
        score = 0;
        allAnswer = true;
        quetionOneVerify();
        quetionTwoVerify();
        quetionThreeVerify();
        quetionFourVerify();
        quetionFiveVerify();
        quetionSixVerify();
        quetionSevenVerify();
        if (allAnswer) {
            toastScore(score);
        }
    }

    public void limitVerify(View view){
        if (!((CheckBox)view).isChecked()){
            ((CheckBox)view).setChecked(false);
            selected -= 1;
        } else {
            if (selected < 4) {
                ((CheckBox) view).setChecked(true);
                selected += 1;
            } else {
                ((CheckBox) view).setChecked(false);
            }
        }
    }

    private void toastScore(int score){
        Toast toast;
        String user = userName.getText().toString();
        String text = "";

        if (user.length() < 1) {
            user = getResources().getString(R.string.no_name);
        }
        if (score == 0) {

            String textFormat = getResources().getString(R.string.zeroCorrect);
            text = String.format(textFormat, user);

        } else if (score == 1) {
            String textFormat = getResources().getString(R.string.oneCorrect);
            text = String.format(textFormat, user);

        } else if (score <= 5 && score > 1) {
            String textFormat = getResources().getString(R.string.halfCorrect);
            text = String.format(textFormat, user, score);

        } else if (score > 5 && score < 10) {
            String textFormat = getResources().getString(R.string.halfMoreCorrect);
            text = String.format(textFormat, user, score);

        } else if (score == 10) {
            String textFormat = getResources().getString(R.string.allCorrect);
            text = String.format(textFormat, user);

        }
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void toastMissQuestion(int question){
        allAnswer = false;
        String text = String.format(getResources().getString(R.string.no_answer),question);
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void quetionOneVerify(){
        boolean answerA = answerOneA.isChecked();
        boolean answerB = answerOneB.isChecked();
        boolean answerC = answerOneC.isChecked();
        boolean answerD = answerOneD.isChecked();

        if (answerA||answerB||answerC||answerD){
            if(answerC){
                score += 1;
            }
        } else {
            toastMissQuestion(1);
        }

    }

    private void quetionTwoVerify(){
        int answer;

        if (answerTwo.length() > 0){
            answer = Integer.valueOf(answerTwo.getText().toString());
            if(answer == 1972){
                score += 1;
            }
        } else {
            toastMissQuestion(2);
        }
    }

    private void quetionThreeVerify(){
        boolean answerA = answerThreeA.isChecked();
        boolean answerB = answerThreeB.isChecked();
        boolean answerC = answerThreeC.isChecked();
        boolean answerD = answerThreeD.isChecked();

        if (answerA||answerB||answerC||answerD){
            if(answerA){
                score += 1;
            }
        } else {
            toastMissQuestion(3);
        }
    }

    private void quetionFourVerify(){
        boolean answerA = answerFourA.isChecked();
        boolean answerB = answerFourB.isChecked();
        boolean answerC = answerFourC.isChecked();
        boolean answerD = answerFourD.isChecked();

        if (answerA||answerB||answerC||answerD){
            if(answerB){
                score += 1;
            }
        } else {
            toastMissQuestion(4);
        }
    }

    private void quetionFiveVerify(){
        boolean answerA = answerFiveA.isChecked();
        boolean answerC = answerFiveC.isChecked();
        boolean answerD = answerFiveD.isChecked();
        boolean answerF = answerFiveF.isChecked();

        if (selected == 4){
            if(answerA){
                score += 1;
            }
            if(answerC){
                score += 1;
            }
            if(answerD){
                score += 1;
            }
            if(answerF){
                score += 1;
            }
        } else {
            toastMissQuestion(5);
        }
    }

    private void quetionSixVerify(){
        boolean answerA = answerSixA.isChecked();
        boolean answerB = answerSixB.isChecked();
        boolean answerC = answerSixC.isChecked();
        boolean answerD = answerSixD.isChecked();

        if (answerA||answerB||answerC||answerD){
            if(answerD){
                score += 1;
            }
        } else {
            toastMissQuestion(6);
        }
    }

    private void quetionSevenVerify(){
        boolean answerA = answerSevenA.isChecked();
        boolean answerB = answerSevenB.isChecked();
        boolean answerC = answerSevenC.isChecked();
        boolean answerD = answerSevenD.isChecked();

        if (answerA||answerB||answerC||answerD){
            if(answerC){
                score += 1;
            }
        } else {
            toastMissQuestion(7);
        }
    }
}