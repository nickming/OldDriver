package com.wennuan.olddriver.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/8.
 */
public class FileUtil {

    /**
     * 刷新媒体库，在媒体库中也删除
     *
     * @param context
     * @param path
     */
    public static void updateDeleteMediaStore(Context context, String path) {
        try {
            int ret = context.getContentResolver().delete(MediaStore.Files.getContentUri("external"), path, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新媒体库中的这个文件
     * @param context
     * @param path
     */
    public static void updateInsertMediaStore(Context context, String path) {
        try {
            MediaScannerConnection.scanFile(context, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("nickming", "onScanCompleted: ");
                }
            });
//            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            Uri uri = Uri.fromFile(new File(path));
//            intent.setData(uri);
//            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取url中文件名
     * @param filepath
     * @return
     */
    public static String getFileName(String filepath)
    {
        StringBuffer result=new StringBuffer();
        String[] temp=filepath.split("/");
        if (temp.length>0)
        {
            int len=temp.length;
            result.append(temp[len-1]);
        }
        return result.toString();
    }
}
