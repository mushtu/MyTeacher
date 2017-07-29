package ir.ac.ut.ece.moallem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.api.model.Category;

/**
 * Created by mushtu on 7/15/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> implements View.OnClickListener {

    private List<Category> categories = new ArrayList<>();
    private OnCategoryClickListener listener;

    public CategoryAdapter(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        view.setOnClickListener(this);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        Category category = categories.get(position);
        holder.txtName.setText(category.getName());
        holder.itemView.setTag(category);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addItems(List<Category> categories) {
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onCategoryClick((Category) view.getTag());
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        public CategoryHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_category_name);
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }
}
