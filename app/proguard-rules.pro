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

-dontwarn com.firebase.ui.**
-dontnote io.grpc.internal.**

# Opcional: configurar o ProGuard
# Ao usar o Firebase Realtime Database e o ProGuard no seu app, pense em como os objetos do modelo
# serão serializados e desserializados após a ofuscação. Se você usar DataSnapshot.getValue(Class)
# ou DatabaseReference.setValue(Object) para ler e gravar dados, será preciso adicionar regras ao arquivo
# proguard-rules.pro:
# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class br.com.cellsgroup.models.** { *;}

# Para garantir que os novos rastreamentos de stack traces não sejam ambíguos, a regra a seguir
# precisa ser adicionada ao arquivo proguard-rules.pro
-keepattributes LineNumberTable,SourceFile
