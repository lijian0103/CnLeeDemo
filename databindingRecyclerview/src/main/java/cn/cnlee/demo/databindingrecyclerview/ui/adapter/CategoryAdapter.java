package cn.cnlee.demo.databindingrecyclerview.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

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
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

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

//        holder.binding.booksRv.setLayoutManager(new LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false));
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this.context);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

        holder.binding.booksRv.setLayoutManager(flexboxLayoutManager);

        BookAdapter bookAdapter = new BookAdapter(holder.binding.getRoot().getContext(), categoryList.get(position).getBooks());
        Log.d("CategoryAdapter", this.context + " === " + holder.binding.getRoot().getContext());
        holder.binding.booksRv.setAdapter(bookAdapter);
        holder.binding.booksRv.setRecycledViewPool(viewPool);
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
