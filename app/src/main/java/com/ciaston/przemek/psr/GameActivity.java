package com.ciaston.przemek.psr;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ciaston.przemek.psr.db.DataBaseManager;
import com.ciaston.przemek.psr.model.Game;
import com.ciaston.przemek.psr.util.Utils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {
    @BindView(R.id.tv_computerScore)
    TextView tv_computerScore;
    @BindView(R.id.tv_playerScore)
    TextView tv_playerScore;
    @BindView(R.id.tv_draw)
    TextView tv_draw;

    @BindView(R.id.paperButton)
    Button paper;
    @BindView(R.id.scissorsButton)
    Button scissors;
    @BindView(R.id.rockButton)
    Button rock;

    @BindView(R.id.iv_playerChoice)
    ImageView iv_playerChoice;
    @BindView(R.id.iv_computerChoice)
    ImageView iv_computerChoice;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @BindView(R.id.player)
    TextView player;
    private ArrayAdapter<String> arrayAdapter;
    private String[] playerArray;
    private List<String> playerList;
    DataBaseManager dataBaseManager;

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

        dataBaseManager = new DataBaseManager(this);

        // Random backgorund
        Utils.randomBackgroundColor(relativeLayout);

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

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlayerName();
            }
        });
    }

    private void setPlayerName() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);

        playerList = dataBaseManager.getPlayers();
        playerArray = new String[playerList.size()];
        for (int i = 0; i < playerArray.length; i++) {
            playerArray[i] = playerList.get(i);
        }

        arrayAdapter = new ArrayAdapter<>(GameActivity.this,
                android.R.layout.simple_spinner_dropdown_item, playerArray);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);

        alertDialog.setView(view);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder playerName = correctPlayerName(autoCompleteTextView);
                player.setText(playerName);
            }
        });
        alertDialog.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @NonNull
    public StringBuilder correctPlayerName(AutoCompleteTextView autoCompleteTextView) {
        int len = autoCompleteTextView.getText().toString().length();
        String upper = autoCompleteTextView.getText().toString().substring(0, 1).toUpperCase().trim();
        String lower = autoCompleteTextView.getText().toString().substring(1, len).toLowerCase().trim();
        return new StringBuilder(upper + lower);
    }

    private void insertData() {
        String nick = player.getText().toString();
        int mScore = Integer.parseInt(tv_playerScore.getText().toString());
        int aScore = Integer.parseInt(tv_computerScore.getText().toString());
        int mWin = mScore > aScore ? 1 : 0;
        int aWin = mScore < aScore ? 1 : 0;

        Game game = new Game();
        game.setPlayer(nick);
        game.setWin(mWin);
        game.setLoose(aWin);
        dataBaseManager.insertData(game);
    }

    private void scores() {
        tv_computerScore.setText(Integer.toString(computerScore));
        tv_playerScore.setText(Integer.toString(playerScore));
        tv_draw.setText("Draw: " + Integer.toString(draw));
    }

    private boolean gameOver() {
        if (playerScore == 5 || computerScore == 5) {
            insertData();
            return true;
        }
        return false;
    }

    private void showWinner() {
        if (gameOver()) {
            if (playerScore > computerScore) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(GameActivity.this);
                alertDialog.setMessage("You WIN " + playerScore + "-" + computerScore + "! Rematch ?")
                        .setTitle(R.string.game_over)
                        .setCancelable(false)
                        .setIcon(R.drawable.psr_icon)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clearView();
                            }
                        })
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clearView();
                                GameActivity.this.finish();
                            }
                        })
                        .setNeutralButton(R.string.ranking, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearView();
                                IntentToListView();
                                dialog.dismiss();
                            }

                        });
                AlertDialog alert = alertDialog.create();
                alert.getWindow().setBackgroundDrawableResource(R.color.dialogWin);
                alert.show();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(GameActivity.this);
                alertDialog.setMessage("Android WIN " + computerScore + "-" + playerScore + "! Rematch ?")
                        .setTitle(R.string.game_over)
                        .setIcon(R.drawable.psr_icon)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clearView();
                            }
                        })
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clearView();
                                GameActivity.this.finish();
                            }
                        })
                        .setNeutralButton(R.string.ranking, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearView();
                                IntentToListView();
                                dialog.dismiss();
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

    private String game(String playerChoice) {
        String computerChoice = "";
        String result;
        Random random = new Random();
        int computerRandChoice = random.nextInt(3) + 1;
        if (computerRandChoice == 1) {
            computerChoice = "paper";
        } else if (computerRandChoice == 2) {
            computerChoice = "scissors";
        } else if (computerRandChoice == 3) {
            computerChoice = "rock";
        }

        if (computerChoice == "paper") {
            iv_computerChoice.setImageResource(R.drawable.paper);
        } else if (computerChoice == "scissors") {
            iv_computerChoice.setImageResource(R.drawable.scissors);
        } else if (computerChoice == "rock") {
            iv_computerChoice.setImageResource(R.drawable.rock);
        }

        if (computerChoice == playerChoice) {
            draw++;
            result = "Draw... Nobady won";
            showInfo(result, Color.rgb(0, 0, 0), DRAW);
            return "Draw... Nobady won";
        } else if (computerChoice == "scissors" && playerChoice == "paper") {
            computerScore++;
            result = "Scissors cut paper... computer win!";
            showInfo(result, Color.rgb(153, 0, 0), COMPUTER_WIN);
            return "";
        } else if (computerChoice == "paper" && playerChoice == "rock") {
            computerScore++;
            result = "Paper covers the rock... computer win!";
            showInfo(result, Color.rgb(153, 0, 0), COMPUTER_WIN);
            return "";
        } else if (computerChoice == "rock" && playerChoice == "scissors") {
            computerScore++;
            result = "Rock destroys the scissors... computer win!";
            showInfo(result, Color.rgb(153, 0, 0), COMPUTER_WIN);
            return "";
        } else if (computerChoice == "paper" && playerChoice == "scissors") {
            playerScore++;
            result = "Scissors cut paper... You win!";
            showInfo(result, Color.rgb(0, 153, 51), PLAYER_WIN);
            return "";
        } else if (computerChoice == "rock" && playerChoice == "paper") {
            playerScore++;
            result = "Paper covers the rock... You win!";
            showInfo(result, Color.rgb(0, 153, 51), PLAYER_WIN);
            return "";
        } else if (computerChoice == "scissors" && playerChoice == "rock") {
            playerScore++;
            result = "Rock destroys the scissors... You win!";
            showInfo(result, Color.rgb(0, 153, 51), PLAYER_WIN);
            return "";
        }
        return "";
    }


    public void showInfo(String message, int color, final int player) {
        Snackbar snackbar = Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(color);
        if (undo == false) {
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    undo = true;
                    if (player == DRAW) {
                        Utils.defaultSnack(relativeLayout, "Straciłeś/aś jedyną możliwość cofnięcia punktu");
                    } else if (player == COMPUTER_WIN) {
                        Utils.defaultSnack(relativeLayout, "Odjęto punkt Androidowi");
                        computerScore--;
                    } else if (player == PLAYER_WIN) {
                        Utils.defaultSnack(relativeLayout, "Odjęto Twój punkt");
                        playerScore--;
                    }
                    scores();
                }
            });
        }
        snackbar.show();
    }

    private void IntentToListView() {
        Intent intent = new Intent(GameActivity.this, RankingActivity.class);
        startActivity(intent);
    }
}