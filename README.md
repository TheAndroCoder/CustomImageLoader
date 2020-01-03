# Custom Image Loader
**Attention**: curveFactor doesnot work in this version. will be implemented in the next release.<br>
<strong>DOWNLOAD</strong><br>
Gradle (Module : app)
```groovy
  implementation 'com.github.TheAndroCoder:CustomImageLoader:1.0'
```
Gradle (Project Level)
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
<strong>USAGE<strong>
```java
ImageLoader loader=ImageLoader.getInstance();//Singleton Instance
loader.with(context).url(url).initial(R.drawable.image1).transitionTime(300).into(imageView).load();
```
