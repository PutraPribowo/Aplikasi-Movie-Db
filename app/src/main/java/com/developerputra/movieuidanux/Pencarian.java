package com.developerputra.movieuidanux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class Pencarian extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    ListView listView;
    EditText editTitle;
    ImageView imgPoster;
    Button btnSearch;
    MovieAdapter adapter;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);

        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                MovieItems item = (MovieItems)parent.getItemAtPosition(position);
                Intent intent = new Intent(Pencarian.this, Detail.class);
                intent.putExtra(Detail.EXTRA_TITLE, item.getTitle());
                intent.putExtra(Detail.EXTRA_RELEASE_DATE, item.getTanggal());
                intent.putExtra(Detail.EXTRA_OVERVIEW, item.getDeskripsi());
                intent.putExtra(Detail.EXTRA_POSTER_JPG, item.getImg());

                startActivity(intent);
            }
        });

        editTitle   = findViewById(R.id.Etitle);
        imgPoster   = findViewById(R.id.img);
        btnSearch   = findViewById(R.id.search);
        btnSearch.setOnClickListener(movieListener);

        String movieTitle = editTitle.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movieTitle);

        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public android.content.Loader<ArrayList<MovieItems>> onCreateLoader(int i, Bundle bundle) {
        String movieTitle = "";
        if (bundle != null){
            movieTitle = bundle.getString(EXTRAS_MOVIE);
        }

        return new MovieAsyncTaskLoaderPencarian(this,movieTitle);
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    //Button search diklik
    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movieTitle = editTitle.getText().toString();
            if(TextUtils.isEmpty(movieTitle)){
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movieTitle);
            getLoaderManager().restartLoader(0, bundle, Pencarian.this);
        }
    };
}

