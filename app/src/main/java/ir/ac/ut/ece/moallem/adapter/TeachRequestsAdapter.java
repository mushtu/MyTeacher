package ir.ac.ut.ece.moallem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.api.model.Student;
import ir.ac.ut.ece.moallem.api.model.TeachRequest;

/**
 * Created by mushtu on 7/5/17.
 */

public class TeachRequestsAdapter extends RecyclerView.Adapter<TeachRequestsAdapter.TeachRequestViewHolder>
        implements View.OnClickListener {

    private List<TeachRequest> teachRequests = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public TeachRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teach_request, parent, false);
        v.setOnClickListener(this);
        return new TeachRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeachRequestViewHolder holder, int position) {
        TeachRequest request = teachRequests.get(position);
        Student student = request.getStudent();
        holder.studentNameView.setText(student.getFirstName() + " " + student.getLastName());
        holder.itemView.setTag(request);
        holder.courseName.setText(request.getCourse().getName());
        String avatarUrl = student.getAvatarUrl();
        holder.studentDescription.setText(request.getDescription());
        if (avatarUrl.equals("reza.jpg"))
            holder.studentImageView.setImageResource(R.drawable.reza);
    }


    public void addItems(List<TeachRequest> items) {
        teachRequests.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return teachRequests.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(v, (TeachRequest) v.getTag());
    }

    public void removeItem(long requestId) {
        TeachRequest tobeRemove = null;
        for (TeachRequest request : teachRequests) {
            if (request.getId() == requestId) {
                tobeRemove = request;
                break;

            }
        }
        if (tobeRemove != null) {
            teachRequests.remove(tobeRemove);
            notifyDataSetChanged();
        }
    }

    class TeachRequestViewHolder extends RecyclerView.ViewHolder {

        ImageView studentImageView;
        TextView studentNameView;
        TextView studentDescription;
        TextView courseName;

        public TeachRequestViewHolder(View itemView) {
            super(itemView);
            studentImageView = (ImageView) itemView.findViewById(R.id.img_profile);
            studentNameView = (TextView) itemView.findViewById(R.id.txt_name);
            studentDescription = (TextView) itemView.findViewById(R.id.txt_description);
            courseName = (TextView) itemView.findViewById(R.id.txt_course_name);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, TeachRequest request);

    }
}
