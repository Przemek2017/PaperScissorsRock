package com.ciaston.przemek.psr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ciaston.przemek.psr.Game;
import com.ciaston.przemek.psr.R;

import java.util.List;

/**
 * Created by Przemek on 2018-02-18.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyGameHolder> {

    private List<Game> gameList;
    private Context context;

    public GameAdapter(List<Game> gameList, Context context) {
        this.gameList = gameList;
        this.context = context;
    }

    @Override
    public MyGameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_row, parent, false);
        return new MyGameHolder(view);
    }

    @Override
    public void onBindViewHolder(MyGameHolder holder, int position) {
        Game game = gameList.get(position);
        holder.player.setText(game.getPlayer());
        holder.win.setText(String.valueOf(game.getWin()));
        holder.loose.setText(String.valueOf(game.getLoose()));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class MyGameHolder extends RecyclerView.ViewHolder {
        public TextView player, win, loose;
        public View view;

        public MyGameHolder(View itemView) {
            super(itemView);
            view = itemView;

            player = (TextView) view.findViewById(R.id.player);
            win = (TextView) view.findViewById(R.id.win);
            loose = (TextView) view.findViewById(R.id.loose);
        }
    }
}
