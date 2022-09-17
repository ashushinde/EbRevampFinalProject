# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
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

#-injars      bin/classes
#-injars      libs
#-outjars     bin/classes-processed.jar

# Using Google's License Verification Library
-keep class com.android.vending.licensing.ILicensingService

# Specifies to write out some more information during processing.
# If the program terminates with an exception, this option will print out the entire stack trace, instead of just the exception message.
-optimizationpasses 5
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*
-dontshrink
-dontoptimize
-verbose


-printseeds obfuscation/seeds.txt
-printusage obfuscation/unused.txt
-printmapping obfuscation/mapping.txt

####################################################################################################
##############################  IBM MobileFirst Platform configuration  ############################
####################################################################################################
# Annotations are represented by attributes that have no direct effect on the execution of the code.
-keepattributes *Annotation*

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes InnerClasses
-keep class **.R
-keep class **.R$* {
    <fields>;
}

# These options let obfuscated applications or libraries produce stack traces that can still be deciphered later on
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Enable proguard with Cordova
-keep class org.apache.cordova.** { *; }
-keep public class * extends org.apache.cordova.CordovaPlugin

-keep class com.worklight.androidgap.push.** { *; }
-keep class com.worklight.wlclient.push.** { *; }
-keep class com.worklight.common.security.AppAuthenticityToken { *; }

# Enable proguard with Google libs
-keep class com.google.** { *;}
-dontwarn com.google.common.**
-dontwarn com.google.ads.**

# apache.http
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-optimizations !class/merging/vertical*,!class/merging/horizontal*,!code/simplification/arithmetic,!field/*,!code/allocation/variable

-keep class net.sqlcipher.** { *; }
-dontwarn net.sqlcipher.**

-keep class org.codehaus.** { *; }
-keepattributes *Annotation*,EnclosingMethod

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * extends javax.net.ssl.SSLSocketFactory {
   private  javax.net.ssl.SSLSocketFactory delegate;
}

# Remove debug logs in release build
-assumenosideeffects class android.util.Log {
    public static *** d(...);
}

# These classes contain references to external jars which are not included in the default MobileFirst project.
-dontwarn com.worklight.common.internal.WLTrusteerInternal*
-dontwarn com.worklight.jsonstore.**
-dontwarn org.codehaus.jackson.map.ext.*
-dontwarn com.worklight.androidgap.push.GCMIntentService
-dontwarn com.worklight.androidgap.plugin.WLInitializationPlugin
-dontwarn com.worklight.wlclient.push.GCMIntentService
-dontwarn org.bouncycastle.**
-dontwarn com.worklight.androidgap.jsonstore.security.SecurityManager

-dontwarn com.worklight.wlclient.push.WLBroadcastReceiver
-dontwarn com.worklight.wlclient.push.common.*
-dontwarn com.worklight.wlclient.api.WLPush
-dontwarn com.worklight.wlclient.api.SecurityUtils

-dontwarn android.support.v4.**
-dontwarn android.net.SSLCertificateSocketFactory
-dontwarn android.net.http.*
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**

-dontwarn com.ebfront.fyntune.**
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontnote okhttp3.**




# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep class org.json.** { *;}
-keep class dev.dworks.libs.** { *;}
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v7.app.ActionBarActivity
-keep public class * extends android.app.Application
-keep public class com.palm.newbenefit.**
-keep public class com.palm.newbenefit.** { *;}
-keep public class com.palm.newbenefit.*
-keepattributes InnerClasses
-keep class com.palm.newbenefit.ReportHandler** {*;}
-keep class com.palm.newbenefit.Application** {*;}
-keep public class * extends android.app.Util
-keep public class com.**{*;}
-keep public class com.palm.newbenefit.**{*;}
-keep class com.** { *; }
-keep interface com.** { *; }
-keep class com.palm.newbenefit.fragment.MemberEnrolledFragment { *; }
-keep class com.palm.newbenefit.fragment { *; }

-keep class com.palm.newbenefit.** { *; }
-keep public class com.palm.newbenefit.Activity
-keep public class com.palm.newbenefit.Util



