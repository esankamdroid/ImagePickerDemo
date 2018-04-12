# ImagePickerDemo

# Steps in implement libraries in application


Add below in dependencies of module's build.gradle

`implementation 'com.esankamdroid.imagepicker:imagepicker:0.0.4'`


Add below snippet in AndroidManifest.xml

`<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="{packagename}.provider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>` 


Initialize Imagepicker instance in onCreate() of activity

`new ImagePicker.Builder()
        .with(this)
        .setPackage(getPackageName())
        .setAllowMultipleSelect(true/false)
        .setOnGetBitmapListener(this)
        .build();
`

