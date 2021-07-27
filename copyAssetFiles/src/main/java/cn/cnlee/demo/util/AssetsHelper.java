package cn.cnlee.demo.util;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import cn.cnlee.demo.copyassetfiles.BaseApplication;

/**
 * @Description assets helper
 * @Author cnlee
 * @Date 2021/7/27
 * @Version 1.0
 */
public class AssetsHelper {
    public static final String TAG = AssetsHelper.class.getSimpleName();
    private static final String sExternalFilesDirPath = BaseApplication.instance().getExternalFilesDir(null).getAbsolutePath();
    public static final String FIRST_FOLDER = sExternalFilesDirPath + File.separator + "0";
    public static final String SECOND_FOLDER = sExternalFilesDirPath + File.separator + "1000";


    public static void copyAssetsToSdCard(String fileName) {
        AssetManager am = BaseApplication.instance().getAssets();
        AssetFileDescriptor afd = null;
        try {
            afd = am.openFd(fileName);
            // Create new file to copy into.
            String targetPath = sExternalFilesDirPath + File.separator + fileName;
            Log.d(TAG, "targetPath: " + targetPath);
            File file = new File(targetPath);
            file.createNewFile();

            copyFdToFile(afd.getFileDescriptor(), file);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void copyFdToFile(FileDescriptor src, File dst) throws IOException {
        try (FileChannel inChannel = new FileInputStream(src).getChannel(); FileChannel outChannel = new FileOutputStream(dst).getChannel()) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
    }

    public static void copyFileOrDir(String path) {
        AssetManager assetManager = BaseApplication.instance().getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath = sExternalFilesDirPath + File.separator + path;
                ;
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(path + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private static void copyFile(String filename) {
        AssetManager assetManager = BaseApplication.instance().getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = sExternalFilesDirPath + File.separator + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

}
