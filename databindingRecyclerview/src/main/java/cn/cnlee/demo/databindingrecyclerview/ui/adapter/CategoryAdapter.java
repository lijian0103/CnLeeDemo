package cn.cnlee.demo.databindingrecyclerview.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.cnlee.demo.databindingrecyclerview.BR;
import cn.cnlee.demo.databindingrecyclerview.R;
import cn.cnlee.demo.databindingrecyclerview.data.Category;
import cn.cnlee.demo.databindingrecyclerview.databinding.CategoryItemBindingImpl;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/11/27
 * @Version 1.0
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> list) {
        this.context = context;
        this.categoryList = list;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemBindingImpl binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.category_item,
                parent,
                false);
        CategoryViewHolder holder = new CategoryViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.binding.setVariable(BR.item, categoryList.get(position));

        holder.binding.booksRv.setLayoutManager(new GridLayoutManager(this.context, 4));
        BookAdapter bookAdapter = new BookAdapter(this.context, categoryList.get(position).getBooks());
        holder.binding.booksRv.setAdapter(bookAdapter);
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private CategoryItemBindingImpl binding;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public CategoryItemBindingImpl getBinding() {
            return binding;
        }

        public void setBinding(CategoryItemBindingImpl binding) {
            this.binding = binding;
        }
    }
}
