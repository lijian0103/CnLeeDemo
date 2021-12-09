package cn.cnlee.demo.databindingrecyclerview.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.cnlee.demo.databindingrecyclerview.BR;
import cn.cnlee.demo.databindingrecyclerview.R;
import cn.cnlee.demo.databindingrecyclerview.data.Book;
import cn.cnlee.demo.databindingrecyclerview.databinding.BookItemBinding;
import cn.cnlee.demo.databindingrecyclerview.ui.BookDetailDialog;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/11/27
 * @Version 1.0
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> books;

    public BookAdapter(Context context, List<Book> list) {
        this.context = context;
        this.books = list;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.book_item,
                parent,
                false);
        BookViewHolder holder = new BookViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.binding.setVariable(BR.item, book);

        holder.binding.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setStatus("测试");
                notifyDataSetChanged();
            }
        });
        holder.binding.bookImgIv.setOnClickListener(view -> {
            Log.d("BookAdapter", "===book item click == " + position);

            BookDetailDialog detailDialog = new BookDetailDialog(holder.binding.getRoot().getContext(), book);
            detailDialog.show();
        });
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        private BookItemBinding binding;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public BookItemBinding getBinding() {
            return binding;
        }

        public void setBinding(BookItemBinding binding) {
            this.binding = binding;
        }
    }
}
