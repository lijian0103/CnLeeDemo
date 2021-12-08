package cn.cnlee.demo.databindingrecyclerview.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import cn.cnlee.demo.databindingrecyclerview.R;
import cn.cnlee.demo.databindingrecyclerview.data.Book;
import cn.cnlee.demo.databindingrecyclerview.databinding.BookDetailDialogBinding;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/12/8
 * @Version 1.0
 */
public class BookDetailDialog extends Dialog {

    private BookDetailDialogBinding mDialogBinding;
    private Book mBook;

    public BookDetailDialog(@NonNull Context context, Book book) {
        super(context, R.style.CustomDialog);
        this.mBook = book;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.book_detail_dialog, null, false);

        mDialogBinding.setItem(this.mBook);
        setContentView(mDialogBinding.getRoot());
    }

    @Override
    public void show() {
        super.show();
/**
 * 设置宽度全屏，要设置在show的后面
 */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = dpTopx(this.getContext(), 240);
        layoutParams.height = dpTopx(this.getContext(), 240);
        getWindow().setAttributes(layoutParams);
    }


    /*
     *dp转px
     */
    private int dpTopx(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /*
     *px转dp
     */
    public static int pxTodp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
