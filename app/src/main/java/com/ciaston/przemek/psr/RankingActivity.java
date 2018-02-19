package com.ciaston.przemek.psr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.ciaston.przemek.psr.adapter.GameAdapter;
import com.ciaston.przemek.psr.db.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 2018-01-09.
 */

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Game> gameList = new ArrayList<>();
    private GameAdapter gameAdapter;
    private DataBaseManager dataBaseManager ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dataBaseManager = new DataBaseManager(this);
        //dataBaseManager.insertRandomGame();

        initData();
        initRecyclerView();

    }

    public void initData() {
        gameList = dataBaseManager.getArrayList();
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
