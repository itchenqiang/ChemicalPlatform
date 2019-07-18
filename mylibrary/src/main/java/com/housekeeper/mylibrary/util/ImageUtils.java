package com.housekeeper.mylibrary.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import androidx.annotation.DrawableRes;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:
 * Creator: Chenqiang
 * Date: 2017/3/7
 */

public class ImageUtils {

    public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap decodeResource(Resources res, @DrawableRes int id) {
        return BitmapFactory.decodeResource(res, id);
    }


    /**
     * Get bitmap from specified image path
     *
     * @param imgPath imgPath
     * @return Bitmap
     */
    public static Bitmap getBitmap(String imgPath) {
        return BitmapFactory.decodeFile(imgPath);
    }


    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param image   Bitmap
     * @param outPath outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 80;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        LogUtil.e("图片大小= " + os.toByteArray().length / 1024);
        while (os.toByteArray().length / 1024 > maxSize) {
            LogUtil.e("图片大小= " + os.toByteArray().length / 1024);
            os.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    public static Bitmap getScaleBitmap(Bitmap bitmap, float size) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        LogUtil.e("bitmap.getWidth()= " + width + "bitmap.getHeight()= " + height);
        float scale;
        if (width > height && width > size) {
            scale = size / width;
        } else if (height > width && height > size) {
            scale = size / height;
        } else {
            scale = 1;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static String bitmapToBase64Jpg(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
                LogUtil.e("bitmapToBase64.length()= " + result.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String bitmapToBase64Png(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
                LogUtil.e("bitmapToBase64.length()= " + result.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static File saveImage(Bitmap bmp, String dir, String fileName) {
        File file = new File(dir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        LogUtil.e("-------保存图片到本地成功-------");
        return file;
    }

}
