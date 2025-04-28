-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties { *; }

-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }

-dontwarn net.sqlcipher.database.**
-dontwarn rx.**

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}