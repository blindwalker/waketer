package at.kropf.waketer.model;

/**
 * Created by martinkropf on 04.07.14.
 */
public class ArtistImage {

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    String imageName;
    String imageSize;
}
