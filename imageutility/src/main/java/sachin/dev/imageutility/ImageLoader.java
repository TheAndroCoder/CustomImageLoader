package sachin.dev.imageutility;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class ImageLoader {
    private static ImageLoader instance=null;
    private boolean roundedCorners=false;
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
    public ImageLoader roundedCorners(boolean bool){
        this.roundedCorners=bool;
        //RoundedBitmapDrawable d= RoundedBitmapDrawableFactory.create(context.getResources().getDrawable(res),);

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
        if(res!=0 && roundedCorners)
        {
            Bitmap bmp= BitmapFactory.decodeResource(context.getResources(),res);
            bmp=getRoundedCornerBitmap(context,bmp,bmp.getHeight()*bmp.getWidth(),bmp.getWidth(),bmp.getHeight(),false,false,false,false);
            imageView.setImageBitmap(bmp);
        }else if(res !=0){
            imageView.setImageResource(res);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Response response=client.newCall(request).execute();
                    InputStream is = (InputStream)new URL(url).getContent();
                    final Drawable d = Drawable.createFromStream(is,"src name");
                    Bitmap b=((BitmapDrawable)d).getBitmap();
                    final Bitmap bnd=getRoundedCornerBitmap(context,b,b.getWidth()*b.getHeight(),b.getWidth(),b.getHeight(),false,false,false,false);
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
                                    imageView.setImageBitmap(bnd);
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
    public Bitmap getRoundedCornerBitmap(Context context, Bitmap input, int pixels , int w , int h , boolean squareTL, boolean squareTR, boolean squareBL, boolean squareBR  ) {

        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);

        //make sure that our rounded corner is scaled appropriately
        final float roundPx = pixels*densityMultiplier;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


        //draw rectangles over the corners we want to be square
        if (squareTL ){
            canvas.drawRect(0, h/2, w/2, h, paint);
        }
        if (squareTR ){
            canvas.drawRect(w/2, h/2, w, h, paint);
        }
        if (squareBL ){
            canvas.drawRect(0, 0, w/2, h/2, paint);
        }
        if (squareBR ){
            canvas.drawRect(w/2, 0, w, h/2, paint);
        }


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(input, 0,0, paint);

        return output;
    }
}
