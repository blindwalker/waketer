package at.kropf.waketer.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martinkropf on 05.11.14.
 *
 */
public class Album extends Music{
    private String name;
    private String mbid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public static Album fetchAlbum(JSONObject json) throws JSONException {
        Album tempAlbum = new Album();

        try{
            JSONObject result = json.getJSONObject("album");

            tempAlbum.setName(result.optString("title", ""));
            tempAlbum.setMbid(result.optString("mbid", ""));

            List<ArtistImage> imageList = new ArrayList<ArtistImage>();
            for (int e = 0; e < result.getJSONArray("image").length(); e++) {
                ArtistImage tempImage = new ArtistImage();
                JSONObject tempJsonImage = result.getJSONArray("image").getJSONObject(e);

                tempImage.setImageName(tempJsonImage.optString("#text", ""));
                tempImage.setImageSize(tempJsonImage.optString("size", ""));
                imageList.add(tempImage);

            }
            tempAlbum.setImageList(imageList);

        }catch (Exception e){
            tempAlbum.setMbid("");
        }

        return tempAlbum;
    }
}
