package at.kropf.waketer.model;

import java.util.List;

/**
 * Created by martinkropf on 05.11.14.
 */
public class Music {

    List<ArtistImage> imageList;

    public ArtistImage getImageBySize(String size){
        for(int i=0;i<imageList.size();i++){
            if(imageList.get(i).getImageSize().toLowerCase().equals(size)){
                return imageList.get(i);
            }
        }
        return null;
    }

    public List<ArtistImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ArtistImage> imageList) {
        this.imageList = imageList;
    }

}
