package com.ciaston.przemek.psr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ciaston.przemek.psr.adapter.GameAdapter;
import com.ciaston.przemek.psr.db.DataBaseManager;
import com.ciaston.przemek.psr.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 2018-01-09.
 */

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Game> gameList = new ArrayList<>();
    private GameAdapter gameAdapter;
    private DataBaseManager dataBaseManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dataBaseManager = new DataBaseManager(this);

        initData();
        initRecyclerView();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                gameAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Game game = gameAdapter.getData(position);
                String playerToRemove = game.getPlayer();
                dataBaseManager.deleteData(playerToRemove);
                gameAdapter.removeItem(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    public void initData() {
        gameList = dataBaseManager.getArrayListGroup();
        gameAdapter = new GameAdapter(gameList, getApplicationContext());
        recyclerView.setAdapter(gameAdapter);
        gameAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
