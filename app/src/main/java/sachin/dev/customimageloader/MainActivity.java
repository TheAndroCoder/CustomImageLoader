package sachin.dev.customimageloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import sachin.dev.imageutility.ImageLoader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageview);
        ImageLoader loader=ImageLoader.getInstance();
        loader.url("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fnyppagesix.files.wordpress.com%2F2019%2F05%2Fgettyimages-1149239562.jpg%3Fquality%3D90%26strip%3Dall%26w%3D1200&f=1&nofb=1").with(this).initial(R.drawable.visual_slide24_1).transitionTime(500).into(imageView).roundedCorners(true).load();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1001);
        }
    }
}
