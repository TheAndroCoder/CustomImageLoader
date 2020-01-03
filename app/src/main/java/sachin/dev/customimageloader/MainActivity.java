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
        try {
            loader.url("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.ytimg.com%2Fvi%2F53BsyxwSBJk%2Fmaxresdefault.jpg&f=1&nofb=1").with(this).initial(R.drawable.parthapratim).transitionTime(500).into(imageView).curveFactor(2).load();
        } catch (Exception e) {
            Log.d("IMAGE-LOADER",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1001);
        }
    }
}
