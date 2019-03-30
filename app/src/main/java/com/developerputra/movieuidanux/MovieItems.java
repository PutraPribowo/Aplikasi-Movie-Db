package com.developerputra.movieuidanux;
import org.json.JSONObject;
public class MovieItems {

    private String title;
    private String deskripsi;
    private String tanggal;
    private String img;

    public MovieItems(JSONObject object){
        try {
            String title        = object.getString("title");
            String description  = object.getString("overview");
            String release_date = object.getString("release_date");
            String image        = object.getString("poster_path");

            this.title        = title;
            this.deskripsi    = description;
            this.tanggal      = release_date;
            this.img          = image;

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
