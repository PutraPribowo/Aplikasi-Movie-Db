package com.developerputra.movieuidanux;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SedangFragment extends Fragment {

    private RecyclerView Category;
    private RecyclerView.Adapter adapter;
    private View view;
    private List<Items2> movieLists;
    private static final String API_URL = BuildConfig.MOVIE_URL+"/now_playing?api_key="+BuildConfig.MOVIE_API_KEY+"&language=en-US";

    public SedangFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sedang, container,false);
        Category = view.findViewById(R.id.category);
        Category.setHasFixedSize(true);
        Category.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieLists = new ArrayList<>();
        loadUrlData();
        return view;
    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        Items2 movies = new Items2();
                        JSONObject data = array.getJSONObject(i);
                        movies.setJudul(data.getString("title"));
                        movies.setDeskripsi(data.getString("overview"));
                        movies.setTanggal(data.getString("release_date"));
                        movies.setGambar(data.getString("poster_path"));
                        movieLists.add(movies);

                    }

                    adapter = new DatangSedangAdapter(movieLists, getActivity());
                    Category.setAdapter(adapter);

                }

                catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadUrlData();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
