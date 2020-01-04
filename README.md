# Custom Image Loader
**Attention**: curveFactor doesnot work in this version. will be implemented in the next release.<br>
<strong>HOW TO DOWNLOAD?</strong><br>
<strong>Gradle (Module : app)</strong><br>
```groovy
  implementation 'com.github.TheAndroCoder:CustomImageLoader:1.1'
```
<strong>Gradle (Project Level)</strong><br>
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
<strong>USAGE<strong><br>
```java
ImageLoader loader=ImageLoader.getInstance();//Singleton Instance
/**
* context : Context.
* initial(int resource) : Image in the imageview before image from url gets loaded.
* transitionTime(milliseconds) : time to smoothly change the image when loaded.
* url : String
* imageView : <? extends ImageView>
* load() : start loading image in background thread
* roundedCorners(default:false) : Rounded corners of imageview
*/
loader.with(context).url(url).initial(R.drawable.image1).transitionTime(300).roundedCorners(true).into(imageView).load();
```
