package cn.cnlee.demo.databindingrecyclerview.ui;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.security.MessageDigest;

import cn.cnlee.demo.databindingrecyclerview.BlurBitmapUtil;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/12/18
 * @Version 1.0
 */
public class GlideBlurTransformation extends CenterCrop {
    private Context context;

    public GlideBlurTransformation(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = super.transform(pool, toTransform, outWidth, outHeight);
        return BlurBitmapUtil.instance().blurBitmap(context, bitmap, 25, outWidth, outHeight);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}