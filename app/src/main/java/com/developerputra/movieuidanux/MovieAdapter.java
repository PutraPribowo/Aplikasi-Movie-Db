package com.developerputra.movieuidanux;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MovieAdapter extends BaseAdapter {

    private static class ViewHolder {
        TextView  title;
        TextView  deskripsi;
        TextView  tanggal;
        ImageView img;
    }

    private ArrayList<MovieItems> DataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private String final_overview;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater    = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items){
        DataList = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItems item) {
        DataList.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        DataList.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (DataList == null) return 0;
        return DataList.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder           = new ViewHolder();
            convertView      = mInflater.inflate(R.layout.item_movie_pencarian, null);
            holder.title     = convertView.findViewById(R.id.title);
            holder.tanggal   = convertView.findViewById(R.id.tanggal);
            holder.deskripsi = convertView.findViewById(R.id.deskripsi);
            holder.img       = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(DataList.get(position).getTitle());

        String overview = DataList.get(position).getDeskripsi();

        if(TextUtils.isEmpty(overview)){
            final_overview = "No data";
        }
        else {
            final_overview = overview;
        }
        holder.deskripsi.setText(final_overview);

        String retrievedDate = DataList.get(position).getTanggal();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(retrievedDate);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            holder.tanggal.setText(date_of_release);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get().load("http://image.tmdb.org/t/p/w154/" + DataList.get(position).getImg()).placeholder(context.getResources().getDrawable(R.drawable.pictures)).error(context.getResources().getDrawable(R.drawable.pictures)).into(holder.img);
        return convertView;
    }

}
