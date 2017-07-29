package ir.ac.ut.ece.moallem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.api.model.EducationProgram;

/**
 * Created by mushtu on 7/13/17.
 */

public class EducationProgramAdapter extends SortedListAdapter<EducationProgram>
        implements View.OnClickListener {

    private List<EducationProgram> programs = new ArrayList<>();
    private List<EducationProgram> dataList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public EducationProgramAdapter(@NonNull Context context, @NonNull Comparator<EducationProgram> comparator, OnItemClickListener listener) {
        super(context, EducationProgram.class, comparator);
        this.onItemClickListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void filter(String query) {

        final List<EducationProgram> filteredModelList = new ArrayList<>();
        for (EducationProgram model : programs) {
            if (model.getName().contains(query)) {
                filteredModelList.add(model);
            }
        }
        return;
    }

    public void initItems(List<EducationProgram> items) {
        programs.clear();
        programs.addAll(items);
        dataList.clear();
        dataList.addAll(items);
        notifyDataSetChanged();
    }

    /*   @Override
       public EducationProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edu_program_view, parent, false);
           v.setOnClickListener(this);
           return new EducationProgramViewHolder(v);
       }
   */

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick((EducationProgram) view.getTag());
    }

    @NonNull
    @Override
    protected ViewHolder<? extends EducationProgram> onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edu_program_view, viewGroup, false);
        v.setOnClickListener(this);
        return new EducationProgramViewHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(EducationProgram program);
    }

    public class EducationProgramViewHolder extends SortedListAdapter.ViewHolder<EducationProgram> {
        TextView txtProgram;

        public EducationProgramViewHolder(View itemView) {
            super(itemView);
            txtProgram = (TextView) itemView.findViewById(R.id.txt_edu_program);
        }

        @Override
        protected void performBind(@NonNull EducationProgram program) {
            txtProgram.setText(program.getName());
        }
    }
}
