# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/greynium/Android-Softwares/adt-bundle-linux-x86-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn android.support.v7.**
-dontwarn android.support.**
-dontwarn com.google.android.gms.**
# don't process support library
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keepattributes *Annotation*
-keep public class com.google.ads.**
