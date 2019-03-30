package com.developerputra.movieuidanux;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class MovieAsyncTaskLoaderPencarian extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;
    private String mMovieTitle;

    public MovieAsyncTaskLoaderPencarian(final Context context, String movieTitle) {
        super(context);
        onContentChanged();
        this.mMovieTitle = movieTitle;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    public void deliverResult(ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if (mHasResult){
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<MovieItems> data) {

    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItems_ = new ArrayList<>();
        String url = BuildConfig.MOVIE_URL_SEARCH+"?api_key=" + BuildConfig.MOVIE_API_KEY+"&language=en-US&query="+mMovieTitle;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItems_.add(movieItems);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
        return movieItems_;
    }
}