package stt.umc.feature;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    GridView gridViewImg;
    ArrayList<Image> imageArrayList;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Mapping();


        Thread background = new Thread(new Runnable() {
            public void run() {
                adapter = new ImageAdapter(GalleryActivity.this,R.layout.image_single,imageArrayList);
                gridViewImg.setAdapter(adapter);
            }
        });
        background.start();
        gridViewImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GalleryActivity.this,imageArrayList.get(position).getName(),Toast.LENGTH_SHORT);
            }
        });
    }


    private void Mapping(){
        gridViewImg = findViewById(R.id.gridviewGallery);
        imageArrayList = getImagesPath(GalleryActivity.this);
    }

    private static ArrayList<Image> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<Image> listOfAllImages = new ArrayList<Image>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {

            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(new Image(PathOfImage,cursor.getNotificationUri(),PathOfImage));
        }
        return listOfAllImages;
    }
}
