package com.developerputra.movieuidanux;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DatangSedangAdapter extends RecyclerView.Adapter<DatangSedangAdapter.ViewHolder> {

    private List<com.developerputra.movieuidanux.Items2> movieLists;
    private Context context;

    public DatangSedangAdapter(List<com.developerputra.movieuidanux.Items2> movieLists, Context context) {
        this.movieLists = movieLists;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView judul, ikhtisar, tanggal;
        public ImageView gambar;
        public Button btnSuka, btnSebar;
        public LinearLayout cvDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            judul       = itemView.findViewById(R.id.tv_nama);
            gambar      = itemView.findViewById(R.id.gambar);
            ikhtisar    = itemView.findViewById(R.id.tv_ikhtisar);
            tanggal     = itemView.findViewById(R.id.tv_tanggal);
            btnSuka     = itemView.findViewById(R.id.btn_disuka);
            btnSebar    = itemView.findViewById(R.id.btn_disebar);
            cvDetail    = itemView.findViewById(R.id.awal);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final com.developerputra.movieuidanux.Items2 movList = movieLists.get(position);
        holder.judul.setText(movList.getJudul());
        holder.ikhtisar.setText(movList.getDeskripsi());

        String release_date = movList.getTanggal();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            holder.tanggal.setText(date_of_release);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/"+movList.getGambar()).into(holder.gambar);

        holder.btnSuka.setOnClickListener(new com.developerputra.movieuidanux.OnClick(position, new com.developerputra.movieuidanux.OnClick.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Favorite: "+movList.getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnSebar.setOnClickListener(new com.developerputra.movieuidanux.OnClick(position, new com.developerputra.movieuidanux.OnClick.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share: "+movList.getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.developerputra.movieuidanux.Items2 movieList = movieLists.get(position);
                Intent Intent = new Intent(context, Detail.class);
                Intent.putExtra(Detail.EXTRA_TITLE, movieList.getJudul());
                Intent.putExtra(Detail.EXTRA_OVERVIEW, movieList.getDeskripsi());
                Intent.putExtra(Detail.EXTRA_POSTER_JPG, movieList.getGambar());
                Intent.putExtra(Detail.EXTRA_RELEASE_DATE, movieList.getTanggal());
                context.startActivity(Intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

}
