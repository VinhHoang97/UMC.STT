package stt.umc.feature;

import android.net.Uri;

import java.net.URI;

public class Image {
    private String name;
    private Uri uri;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image(String name, Uri uri, String path) {

        this.name = name;
        this.uri = uri;
        this.path = path;
    }

    public Uri getUri() {

        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
