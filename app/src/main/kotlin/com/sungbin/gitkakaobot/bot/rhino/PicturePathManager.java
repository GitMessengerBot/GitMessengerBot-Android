package com.sungbin.gitkakaobot.bot.rhino;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import javax.annotation.Nullable;

// 왜 코틀린으로하면 작동이 안될까?
public class PicturePathManager {
    private static final String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String PICTURE_PATH = sdcard + "/Android/data/com.kakao.talk/contents/Mg==";

    public static String getLastPictureFolderPath() {
        File file = new File(PICTURE_PATH);
        File[] list = file.listFiles();
        Arrays.sort(Objects.requireNonNull(list), new ModifiedDate());
        return list[0].toString();
    }

    public static File[] getLastPictureFilePathFromFoldPath(String path) {
        File file = new File(path);
        File[] list = file.listFiles();
        Arrays.sort(Objects.requireNonNull(list), new ModifiedDate());
        return list;
    }

    @Nullable
    public static String getLastPicture() {
        try {
            File[] path = getLastPictureFilePathFromFoldPath(getLastPictureFolderPath());
            for (File value : path) {
                File file = new File(value.toString());
                if (Objects.requireNonNull(file.listFiles()).length > 0) {
                    String picture = getLastPictureFilePathFromFoldPath(file.getPath())[0].toString();
                    Bitmap bm = BitmapFactory.decodeFile(picture);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bImage = baos.toByteArray();
                    return Base64.encodeToString(bImage, 0);
                }
            }
            return null;
        } catch (Exception ignored) {
            return null;
        }
    }

    private static class ModifiedDate implements Comparator<File> {
        public int compare(File f1, File f2) {
            return Long.compare(f2.lastModified(), f1.lastModified());
        }
    }
}