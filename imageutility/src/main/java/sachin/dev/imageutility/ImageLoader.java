package sachin.dev.imageutility;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import androidx.core.app.ActivityCompat;

public class ImageLoader {
    private static ImageLoader instance=null;
    private float factor=0f;
    private String url="";
    private ImageView imageView;
    private Context context;
    private int res=0;
    private int TRANSITION_TIME=200;
    private ImageLoader(){

    }
    public static ImageLoader getInstance(){
        if(instance==null){
            instance=new ImageLoader();
        }
        return instance;
    }

    public ImageLoader url(String url){
        this.url=url;
        return this;

    }
    public ImageLoader initial(int res){
        this.res=res;
        return this;
    }
    public ImageLoader into(ImageView imageView){
        this.imageView=imageView;
        return this;
    }
    public ImageLoader curveFactor(float factor){
        this.factor=factor;
        Log.d("IMAGE_LOADER","The curve factor will work from next version of the library");
        return this;
    }
    public ImageLoader transitionTime(int time){
        this.TRANSITION_TIME=time;
        return this;
    }
    public void load(){
        //Run a new thread to load the image
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            Log.d("IMAGE_LOADER","[INTERNET PERMISSION DENIED]");
            return;
        }
        if(res!=0)
        imageView.setImageResource(res);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Response response=client.newCall(request).execute();
                    InputStream is = (InputStream)new URL(url).getContent();
                    final Drawable d = Drawable.createFromStream(is,"src name");
                    //imageView.setImageDrawable(d);
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.animate().alpha(0).setDuration(TRANSITION_TIME).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    imageView.setImageDrawable(d);
                                    imageView.animate().alpha(1).setDuration(TRANSITION_TIME).setListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            imageView.clearAnimation();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animator) {

                                        }
                                    }).start();

                                    Log.d("IMAGE_LOADER","IMAGE LOADED");
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            }).start();
                        }
                    });
                }catch(Exception e){
                    Log.d("IMAGE_LOADER",e.getMessage());
                }

            }
        }).start();
    }
    public ImageLoader with(Context context){
        this.context=context;
        return  this;
    }
}
