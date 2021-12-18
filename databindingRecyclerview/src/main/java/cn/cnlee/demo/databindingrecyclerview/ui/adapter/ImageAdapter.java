package cn.cnlee.demo.databindingrecyclerview.ui.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.cnlee.demo.databindingrecyclerview.ui.GlideBlurTransformation;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/11/28
 * @Version 1.0
 */
public class ImageAdapter {

    @BindingAdapter("setImage")
    public static void setImage(ImageView imageView, String url) {
//        Glide.with(imageView)
//                .load(url)
//                .into(imageView);
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new GlideBlurTransformation(imageView.getContext())))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imageView);
    }
}
