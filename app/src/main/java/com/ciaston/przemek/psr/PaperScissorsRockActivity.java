package com.ciaston.przemek.psr;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaperScissorsRockActivity extends AppCompatActivity {
    @BindView(R.id.tv_computerScore) TextView tv_computerScore;
    @BindView(R.id.tv_playerScore) TextView tv_playerScore;
    @BindView(R.id.tv_draw) TextView tv_draw;

    @BindView(R.id.paperButton) Button paper;
    @BindView(R.id.scissorsButton) Button scissors;
    @BindView(R.id.rockButton) Button rock;

    @BindView(R.id.iv_playerChoice) ImageView iv_playerChoice;
    @BindView(R.id.iv_computerChoice) ImageView iv_computerChoice;
    @BindView(R.id.relativeLayout) RelativeLayout relativeLayout;

    int playerScore, computerScore, draw = 0;
    boolean undo = false;

    static final int PLAYER_WIN = 1;
    static final int COMPUTER_WIN = 2;
    static final int DRAW = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_scissors_rock);
        ButterKnife.bind(this);

        randomBackground();

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_playerChoice.setImageResource(R.drawable.paper);
                game("paper");
                scores();
                showWinner();
            }
        });
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_playerChoice.setImageResource(R.drawable.rock);
                game("rock");
                scores();
                showWinner();
            }
        });
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_playerChoice.setImageResource(R.drawable.scissors);
                game("scissors");
                scores();
                showWinner();
            }
        });
    }

    private void scores() {
        tv_computerScore.setText(Integer.toString(computerScore));
        tv_playerScore.setText(Integer.toString(playerScore));
        tv_draw.setText("Draw: " + Integer.toString(draw));
    }

    private void showWinner() {
        if (playerScore == 5 || computerScore == 5) {
            if (playerScore > computerScore) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PaperScissorsRockActivity.this);
                alertDialog.setMessage("You WIN " + playerScore + "-" + computerScore +"! Rematch ?")
                        .setTitle("Game over")
                        .setCancelable(false)
                        .setIcon(R.drawable.psr_icon)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clearView();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PaperScissorsRockActivity.this.finish();
                            }
                        });
                AlertDialog alert = alertDialog.create();
                alert.getWindow().setBackgroundDrawableResource(R.color.dialogWin);
                alert.show();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PaperScissorsRockActivity.this);
                alertDialog.setMessage("Android WIN " + computerScore + "-" + playerScore +"! Rematch ?")
                        .setTitle("Game over")
                        .setIcon(R.drawable.psr_icon)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clearView();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PaperScissorsRockActivity.this.finish();
                            }
                        });
                AlertDialog alert = alertDialog.create();
                alert.getWindow().setBackgroundDrawableResource(R.color.dialogLoose);
                alert.show();
            }
        }
    }

    private void clearView() {
        tv_computerScore.setText("0");
        tv_playerScore.setText("0");
        tv_draw.setText("Draw: 0");
        playerScore = 0;
        computerScore = 0;
        draw = 0;
        undo = false;
    }

    private String game(String player_choise){
        String computer_choise = "";
        String result;
        Random random = new Random();
        int computerRandChoise = random.nextInt(3) + 1;
        if (computerRandChoise == 1){
            computer_choise = "paper";
        } else if (computerRandChoise == 2){
            computer_choise = "scissors";
        } else if (computerRandChoise == 3){
            computer_choise = "rock";
        }

        if (computer_choise == "paper"){
            iv_computerChoice.setImageResource(R.drawable.paper);
        } else if (computer_choise == "scissors"){
            iv_computerChoice.setImageResource(R.drawable.scissors);
        } else if (computer_choise == "rock"){
            iv_computerChoice.setImageResource(R.drawable.rock);
        }

        if (computer_choise == player_choise){
            draw++;
            result = "Draw... Nobady won";
            showSnackbar(result, Color.rgb(0, 0, 0), DRAW );
            return "Draw... Nobady won";
        } else if (computer_choise == "scissors" && player_choise == "paper"){
            computerScore++;
            result = "Scissors cut paper... computer win!";
            showSnackbar(result, Color.rgb(153, 0, 0), COMPUTER_WIN );
            return "";
        } else if (computer_choise == "paper" && player_choise == "rock"){
            computerScore++;
            result = "Paper covers the rock... computer win!";
            showSnackbar(result, Color.rgb(153, 0, 0), COMPUTER_WIN );
            return "";
        } else if (computer_choise == "rock" && player_choise == "scissors"){
            computerScore++;
            result = "Rock destroys the scissors... computer win!";
            showSnackbar(result, Color.rgb(153, 0, 0), COMPUTER_WIN );
            return "";
        } else if (computer_choise == "paper" && player_choise == "scissors"){
            playerScore++;
            result = "Scissors cut paper... You win!";
            showSnackbar(result, Color.rgb(0, 153, 51), PLAYER_WIN );
            return "";
        } else if (computer_choise == "rock" && player_choise == "paper"){
            playerScore++;
            result = "Paper covers the rock... You win!";
            showSnackbar(result, Color.rgb(0, 153, 51), PLAYER_WIN );
            return "";
        } else if (computer_choise == "scissors" && player_choise == "rock"){
            playerScore++;
            result = "Rock destroys the scissors... You win!";
            showSnackbar(result, Color.rgb(0, 153, 51), PLAYER_WIN );
            return "";
        }
        return "";
    }

    public void randomBackground(){
        int[] bArray = new int[]{R.drawable.bgame1,
                R.drawable.bgame2,
                R.drawable.bgame3,
                R.drawable.bgame4,
                R.drawable.bgame5,
                R.drawable.bgame6,
                R.drawable.bgame7};

        int arrLen = bArray.length;
        Random random = new Random();
        int r = random.nextInt(arrLen);
        relativeLayout.setBackgroundResource(bArray[r]);
    }


    public void showSnackbar(String message, int color, final int player){
        Snackbar snackbar = Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(color);
        if(undo == false){
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    undo = true;
                    if(player == DRAW){
                        MyMethod.ShowSnackbar(relativeLayout, "Straciłeś jedyną możliwość cofnięcia ruchu");
                    } else
                        if (player == COMPUTER_WIN){
                        MyMethod.ShowSnackbar(relativeLayout, "Odjęto punkt Androidowi");
                        computerScore--;
                    } else if (player == PLAYER_WIN){
                        MyMethod.ShowSnackbar(relativeLayout, "Odjęto Twój punkt");
                        playerScore--;
                    }
                    scores();
                }
            });
        }
        snackbar.show();
    }
}