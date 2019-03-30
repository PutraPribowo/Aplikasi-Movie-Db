package com.developerputra.movieuidanux;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detail extends AppCompatActivity {

    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_POSTER_JPG   = "extra_poster_jpg";

    private TextView titleM, deskripsiM, tanggalM;
    private ImageView imgM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleM = findViewById(R.id.title);
        deskripsiM = findViewById(R.id.deskripsi);
        tanggalM = findViewById(R.id.tanggal);
        imgM = findViewById(R.id.img);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String poster_jpg = getIntent().getStringExtra(EXTRA_POSTER_JPG);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        titleM.setText(title);
        deskripsiM.setText(overview);
        Picasso.get().load("http://image.tmdb.org/t/p/w500/" + poster_jpg).into(imgM);

        try {
            Date date = date_format.parse(release_date);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tanggalM.setText(date_of_release);

        }

        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
