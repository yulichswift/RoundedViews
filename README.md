# RoundedViews

[![](https://jitpack.io/v/yulichswift/RoundedViews.svg)](https://jitpack.io/#yulichswift/RoundedViews)

### Step 1. Add it in your root build.gradle at the end of repositories
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
 
### Step 2. Add the dependency
```
dependencies {
    implementation 'com.github.yulichswift:RoundedViews:1.3.0'
}
```

### How to use?
```
<com.yulichswift.roundedview.widget.RoundedTextView
    android:id="@+id/btn"
    android:layout_width="0dp"
    android:layout_height="0dp"
    android:clickable="true"
    android:paddingStart="16dp"
    android:paddingTop="8dp"
    android:paddingEnd="16dp"
    android:paddingBottom="8dp"
    android:text="Hello World!"
    android:textAlignment="center"
    android:textColor="@android:color/white"
    android:textSize="19sp"
    app:btn_corner_radius="standard"
    app:btn_pressed_color="darker"
    app:btn_selected_color="#5f5"
    app:btn_disable_color="#f00"
    app:btn_solid_color="@android:color/holo_orange_light" />
```
