package cn.cnlee.demo.databindingrecyclerview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cn.cnlee.demo.databindingrecyclerview.data.Book;
import cn.cnlee.demo.databindingrecyclerview.data.Category;
import cn.cnlee.demo.databindingrecyclerview.databinding.CategoryBinding;
import cn.cnlee.demo.databindingrecyclerview.ui.adapter.CategoryAdapter;

public class MainActivity extends AppCompatActivity {

    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        CategoryBinding binding = DataBindingUtil.setContentView(this, R.layout.category);
        binding.categoryRv.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(this, categoryList);
        binding.categoryRv.setAdapter(categoryAdapter);

//        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //add
                updateData();
                categoryAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_del:
                //del
                categoryList.remove(1);
                categoryAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initData() {
        categoryList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Book> books = new ArrayList<>();
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("111").status("open").build());
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("222").status("close").build());
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("333").status("open").build());
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("444").status("open").build());
            categoryList.add(Category.builder().title("标题-" + i).books(books).build());
        }
    }

    private void updateData() {
        categoryList.clear();
        for (int i = 20; i < 30; i++) {
            List<Book> books = new ArrayList<>();
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("111").status("open").build());
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("222").status("close").build());
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("333").status("open").build());
            books.add(Book.builder().img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F451b4cdbe0bc0ae06dfe1bf22af8a86a55a532f6b609-UxKPc1_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640623405&t=f698ead9d237df609ce4c403ec2aec40").name("444").status("open").build());
            categoryList.add(Category.builder().title("标题-" + i).books(books).build());
        }
    }
}