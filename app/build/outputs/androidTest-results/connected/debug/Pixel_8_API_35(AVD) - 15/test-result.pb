
�E
^
ExampleInstrumentedTestcom.example.encryptednotesfilterItems2�����ۤ�:���������>
�org.mockito.exceptions.base.MockitoException:
Mockito cannot mock this class: class com.example.encryptednotes.data.MemoRepository.

Mockito can only mock non-private & non-final classes.
If you're not sure why you're getting this error, please report to the mailing list.



IMPORTANT INFORMATION FOR ANDROID USERS:

The regular Byte Buddy mock makers cannot generate code on an Android VM!
To resolve this, please use the 'mockito-android' dependency for your application:
https://search.maven.org/artifact/org.mockito/mockito-android

Java               : 0.9
JVM vendor name    : The Android Project
JVM vendor version : 2.1.0
JVM name           : Dalvik
JVM version        : 0.9
JVM info           : null
OS name            : Linux
OS version         : 6.6.30-android15-7-gbb616d66d8a9-ab11968886


Underlying exception : java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
at com.example.encryptednotes.ExampleInstrumentedTest.setUp(ExampleInstrumentedTest.kt:48)
... 33 trimmed
Caused by: java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:779)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.doLoad(AndroidClassLoadingStrategy.java:648)
at net.bytebuddy.android.AndroidClassLoadingStrategy.load(AndroidClassLoadingStrategy.java:145)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.load(AndroidClassLoadingStrategy.java:641)
at net.bytebuddy.dynamic.TypeResolutionStrategy$Passive.initialize(TypeResolutionStrategy.java:100)
at net.bytebuddy.dynamic.DynamicType$Default$Unloaded.load(DynamicType.java:6154)
at org.mockito.internal.creation.bytebuddy.SubclassBytecodeGenerator.mockClass(SubclassBytecodeGenerator.java:268)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.lambda$mockClass$0$org-mockito-internal-creation-bytebuddy-TypeCachingBytecodeGenerator(TypeCachingBytecodeGenerator.java:47)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator$$ExternalSyntheticLambda0.call(D8$$SyntheticClass:0)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:153)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:366)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:175)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:377)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.mockClass(TypeCachingBytecodeGenerator.java:40)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMockType(SubclassByteBuddyMockMaker.java:77)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMock(SubclassByteBuddyMockMaker.java:43)
at org.mockito.android.internal.creation.AndroidByteBuddyMockMaker.createMock(AndroidByteBuddyMockMaker.java:39)
at org.mockito.internal.util.MockUtil.createMock(MockUtil.java:53)
at org.mockito.internal.MockitoCore.mock(MockitoCore.java:84)
at org.mockito.Mockito.mock(Mockito.java:1956)
at org.mockito.Mockito.mock(Mockito.java:1871)
... 35 more
Caused by: java.lang.SecurityException: Writable dex file '/data/user/0/com.example.encryptednotes/cache/ykleveR4.jar' is not allowed.
at dalvik.system.DexFile.openDexFileNative(Native Method)
at dalvik.system.DexFile.openDexFile(DexFile.java:406)
at dalvik.system.DexFile.<init>(DexFile.java:128)
at dalvik.system.DexFile.<init>(DexFile.java:101)
at dalvik.system.DexPathList.loadDexFile(DexPathList.java:438)
at dalvik.system.DexPathList.makeDexElements(DexPathList.java:397)
at dalvik.system.DexPathList.addDexPath(DexPathList.java:220)
at dalvik.system.BaseDexClassLoader.addDexPath(BaseDexClassLoader.java:287)
at java.lang.reflect.Method.invoke(Native Method)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:770)
... 55 more
java.lang.IllegalStateException�org.mockito.exceptions.base.MockitoException:
Mockito cannot mock this class: class com.example.encryptednotes.data.MemoRepository.

Mockito can only mock non-private & non-final classes.
If you're not sure why you're getting this error, please report to the mailing list.



IMPORTANT INFORMATION FOR ANDROID USERS:

The regular Byte Buddy mock makers cannot generate code on an Android VM!
To resolve this, please use the 'mockito-android' dependency for your application:
https://search.maven.org/artifact/org.mockito/mockito-android

Java               : 0.9
JVM vendor name    : The Android Project
JVM vendor version : 2.1.0
JVM name           : Dalvik
JVM version        : 0.9
JVM info           : null
OS name            : Linux
OS version         : 6.6.30-android15-7-gbb616d66d8a9-ab11968886


Underlying exception : java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
at com.example.encryptednotes.ExampleInstrumentedTest.setUp(ExampleInstrumentedTest.kt:48)
... 33 trimmed
Caused by: java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:779)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.doLoad(AndroidClassLoadingStrategy.java:648)
at net.bytebuddy.android.AndroidClassLoadingStrategy.load(AndroidClassLoadingStrategy.java:145)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.load(AndroidClassLoadingStrategy.java:641)
at net.bytebuddy.dynamic.TypeResolutionStrategy$Passive.initialize(TypeResolutionStrategy.java:100)
at net.bytebuddy.dynamic.DynamicType$Default$Unloaded.load(DynamicType.java:6154)
at org.mockito.internal.creation.bytebuddy.SubclassBytecodeGenerator.mockClass(SubclassBytecodeGenerator.java:268)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.lambda$mockClass$0$org-mockito-internal-creation-bytebuddy-TypeCachingBytecodeGenerator(TypeCachingBytecodeGenerator.java:47)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator$$ExternalSyntheticLambda0.call(D8$$SyntheticClass:0)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:153)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:366)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:175)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:377)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.mockClass(TypeCachingBytecodeGenerator.java:40)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMockType(SubclassByteBuddyMockMaker.java:77)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMock(SubclassByteBuddyMockMaker.java:43)
at org.mockito.android.internal.creation.AndroidByteBuddyMockMaker.createMock(AndroidByteBuddyMockMaker.java:39)
at org.mockito.internal.util.MockUtil.createMock(MockUtil.java:53)
at org.mockito.internal.MockitoCore.mock(MockitoCore.java:84)
at org.mockito.Mockito.mock(Mockito.java:1956)
at org.mockito.Mockito.mock(Mockito.java:1871)
... 35 more
Caused by: java.lang.SecurityException: Writable dex file '/data/user/0/com.example.encryptednotes/cache/ykleveR4.jar' is not allowed.
at dalvik.system.DexFile.openDexFileNative(Native Method)
at dalvik.system.DexFile.openDexFile(DexFile.java:406)
at dalvik.system.DexFile.<init>(DexFile.java:128)
at dalvik.system.DexFile.<init>(DexFile.java:101)
at dalvik.system.DexPathList.loadDexFile(DexPathList.java:438)
at dalvik.system.DexPathList.makeDexElements(DexPathList.java:397)
at dalvik.system.DexPathList.addDexPath(DexPathList.java:220)
at dalvik.system.BaseDexClassLoader.addDexPath(BaseDexClassLoader.java:287)
at java.lang.reflect.Method.invoke(Native Method)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:770)
... 55 more
"�

logcatandroid�
�C:\Users\Daniel\AndroidStudioProjects\EncryptedNotes\app\build\outputs\androidTest-results\connected\debug\Pixel_8_API_35(AVD) - 15\logcat-com.example.encryptednotes.ExampleInstrumentedTest-filterItems.txt"�

device-infoandroid�
�C:\Users\Daniel\AndroidStudioProjects\EncryptedNotes\app\build\outputs\androidTest-results\connected\debug\Pixel_8_API_35(AVD) - 15\device-info.pb"�

device-info.meminfoandroid�
�C:\Users\Daniel\AndroidStudioProjects\EncryptedNotes\app\build\outputs\androidTest-results\connected\debug\Pixel_8_API_35(AVD) - 15\meminfo"�

device-info.cpuinfoandroid�
�C:\Users\Daniel\AndroidStudioProjects\EncryptedNotes\app\build\outputs\androidTest-results\connected\debug\Pixel_8_API_35(AVD) - 15\cpuinfo*�
c
test-results.logOcom.google.testing.platform.runtime.android.driver.AndroidInstrumentationDriver�
�C:\Users\Daniel\AndroidStudioProjects\EncryptedNotes\app\build\outputs\androidTest-results\connected\debug\Pixel_8_API_35(AVD) - 15\testlog\test-results.log 2
text/plain