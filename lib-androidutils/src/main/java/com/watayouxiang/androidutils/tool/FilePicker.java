package com.watayouxiang.androidutils.tool;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/29
 *     desc   : 系统文件选择器
 * </pre>
 */
public class FilePicker {
    private static int REQUEST_CODE = 2020;

    /**
     * 打开文件系统
     */
    public static void openSystemFile(Activity activity) {
        reqStoragePermission(() -> openSystemFileInternal(activity));
    }

    /**
     * 打开文件系统
     */
    public static void openSystemFile(Fragment fragment) {
        reqStoragePermission(() -> openSystemFileInternal(fragment));
    }

    private static void reqStoragePermission(Runnable runnable) {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale((activity2, shouldRequest) -> shouldRequest.again(true))
                .callback((isAllGranted, granted, deniedForever, denied) -> {
                    if (isAllGranted) {
                        runnable.run();
                    } else if (!deniedForever.isEmpty()) {
                        ToastUtils.showShort("存储权限被禁用，请打开权限！");
                        PermissionUtils.launchAppDetailsSettings();
                    }
                })
                .request();
    }

    private static void openSystemFileInternal(Activity activity) {
        try {
            Intent fileIntent = getFileIntent();
            activity.startActivityForResult(fileIntent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showShort("请安装文件管理器");
        }
    }

    private static void openSystemFileInternal(Fragment fragment) {
        try {
            Intent fileIntent = getFileIntent();
            fragment.startActivityForResult(fileIntent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showShort("请安装文件管理器");
        }
    }

    private static Intent getFileIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        return Intent.createChooser(intent, "请选择文件");
    }

    /**
     * 拿到 "文件系统页" 返回的数据
     *
     * @return 文件路径
     */
    public static String onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String path = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    //获取文件名
                    String fileName = "";
                    Cursor cursor = Utils.getApp().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (nameIndex != -1) {
                            fileName = cursor.getString(nameIndex);
                        }
                        cursor.close();
                    }

                    File outputDir = Utils.getApp().getCacheDir(); //app缓存目录
                    File tempFile = new File(outputDir, "temp_file_" + fileName);

                    try (InputStream inputStream = Utils.getApp().getContentResolver().openInputStream(uri);
                         FileOutputStream outputStream = new FileOutputStream(tempFile)) {

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream != null ? inputStream.read(buffer) : 0) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        ToastUtils.showShort("没有访问权限");
                    } finally {
                        path = tempFile.getAbsolutePath();
                        if (StringUtils.isEmpty(path)) {
                            ToastUtils.showShort("没有访问权限");
                        }
                    }
                }
            }
        }
        return path;
    }

//    private static class ContentUriUtil {
//        /**
//         * Get a file path from a Uri. This will get the the path for Storage Access
//         * Framework Documents, as well as the _data field for the MediaStore and
//         * other file-based ContentProviders.
//         *
//         * @param context The context.
//         * @param uri     The Uri to query.
//         * @author paulburke
//         */
//        public static String getPath(final Context context, final Uri uri) {
//
//            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//            // DocumentProvider
//            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//                // ExternalStorageProvider
//                if (isExternalStorageDocument(uri)) {
//                    final String docId = DocumentsContract.getDocumentId(uri);
//                    final String[] split = docId.split(":");
//                    final String type = split[0];
//
//                    if ("primary".equalsIgnoreCase(type)) {
//                        return Environment.getExternalStorageDirectory() + "/" + split[1];
//                    }
//
//                }
//                // DownloadsProvider
//                else if (isDownloadsDocument(uri)) {
//
//                    long _id = -1;
//                    try {
//                        final String id = DocumentsContract.getDocumentId(uri);
//                        _id = Long.parseLong(id);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    Uri contentUri = null;
//                    if (_id != -1) {
//                        contentUri = ContentUris.withAppendedId(
//                                Uri.parse("content://downloads/public_downloads"), _id);
//                    }
//
//                    if (contentUri != null) {
//                        return getDataColumn(context, contentUri, null, null);
//                    }
//                }
//                // MediaProvider
//                else if (isMediaDocument(uri)) {
//                    final String docId = DocumentsContract.getDocumentId(uri);
//                    final String[] split = docId.split(":");
//                    final String type = split[0];
//
//                    Uri contentUri = null;
//                    if ("image".equals(type)) {
//                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("video".equals(type)) {
//                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("audio".equals(type)) {
//                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                    }
//
//                    final String selection = "_id=?";
//                    final String[] selectionArgs = new String[]{
//                            split[1]
//                    };
//
//                    return getDataColumn(context, contentUri, selection, selectionArgs);
//                }
//            }
//            // MediaStore (and general)
//            else if ("content".equalsIgnoreCase(uri.getScheme())) {
//
//                // Return the remote address
//                if (isGooglePhotosUri(uri))
//                    return uri.getLastPathSegment();
//
//                return getDataColumn(context, uri, null, null);
//            }
//            // File
//            else if ("file".equalsIgnoreCase(uri.getScheme())) {
//                return uri.getPath();
//            }
//
//            return null;
//        }
//
//        /**
//         * *
//         * Get the value of the data column for this Uri. This is useful for
//         * MediaStore Uris, and other file-based ContentProviders.
//         *
//         * @param context       The context.
//         * @param uri           The Uri to query.
//         * @param selection     (Optional) Filter used in the query.
//         * @param selectionArgs (Optional) Selection arguments used in the query.
//         * @return The value of the _data column, which is typically a file path.
//         */
//        private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
//            Cursor cursor = null;
//            final String column = "_data";
//            final String[] projection = {
//                    column
//            };
//
//            try {
//                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    final int index = cursor.getColumnIndexOrThrow(column);
//                    return cursor.getString(index);
//                }
//            } catch (Exception ignored) {
//
//            } finally {
//                if (cursor != null)
//                    cursor.close();
//            }
//            return null;
//        }
//
//        /**
//         * @param uri The Uri to check.
//         * @return Whether the Uri authority is ExternalStorageProvider.
//         */
//        private static boolean isExternalStorageDocument(Uri uri) {
//            return "com.android.externalstorage.documents".equals(uri.getAuthority());
//        }
//
//        /**
//         * @param uri The Uri to check.
//         * @return Whether the Uri authority is DownloadsProvider.
//         */
//        private static boolean isDownloadsDocument(Uri uri) {
//            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//        }
//
//        /**
//         * @param uri The Uri to check.
//         * @return Whether the Uri authority is MediaProvider.
//         */
//        private static boolean isMediaDocument(Uri uri) {
//            return "com.android.providers.media.documents".equals(uri.getAuthority());
//        }
//
//        /**
//         * @param uri The Uri to check.
//         * @return Whether the Uri authority is Google Photos.
//         */
//        private static boolean isGooglePhotosUri(Uri uri) {
//            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
//        }
//    }
}
