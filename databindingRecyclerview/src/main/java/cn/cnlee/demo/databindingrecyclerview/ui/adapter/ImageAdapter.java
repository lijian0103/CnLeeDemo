package cn.cnlee.demo.databindingrecyclerview.ui.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/11/28
 * @Version 1.0
 */
public class ImageAdapter {

    @BindingAdapter("setImage")
    public static void setImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }
}
