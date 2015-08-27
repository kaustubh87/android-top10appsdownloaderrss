package downloader.android.topdownloader;

/**
 * Created by Kaustubh on 8/25/2015.
 */
public class Application {

    private String name;
    private String artist;
    private String releaseDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String toString()
    {

        return "Name: " + this.name +"\n" + "Artist: " +this.artist +"\n" + "release Date: " +this.releaseDate;


    }
}
