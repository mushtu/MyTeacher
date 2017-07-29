package ir.ac.ut.ece.moallem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.api.model.Teacher;

/**
 * Created by mushtu on 7/5/17.
 */

public class TeachersRecyclerAdapter extends RecyclerView.Adapter<TeachersRecyclerAdapter.TeacherViewHolder>
        implements View.OnClickListener {

    private Context context;
    private List<Teacher> teachers = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_short_item, parent, false);
        context = parent.getContext();
        v.setOnClickListener(this);
        return new TeacherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {
        Teacher teacher = teachers.get(position);
        holder.teacherNameView.setText(teacher.getFirstName() + " " + teacher.getLastName());
        holder.teacherRatingView.setText("رتبه: " + teacher.getRating() + "/5");
        holder.itemView.setTag(teacher);
        String avatarUrl = teacher.getAvatarUrl();

        holder.teacherDescription.setText(teacher.getShortDescription());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        holder.teacherRegisterTime.setText("زمان عضویت: " + sd.format(teacher.getRegisterTime()));
        int refs = teacher.getReferences() == null ? 0 : teacher.getReferences().size();
        holder.teacherRefCounts.setText("مراجع: " + refs);

        if (avatarUrl.equals("mushtu.jpg"))
            Picasso.with(context).load(R.drawable.mushtu).fit().centerCrop().into(holder.teacherImageView);
        else if (avatarUrl.equals("iraj.jpg"))
            Picasso.with(context).load(R.drawable.iraj).fit().centerCrop().into(holder.teacherImageView);
        else if (avatarUrl.equals("sajad.jpg"))
            Picasso.with(context).load(R.drawable.sajad).fit().centerCrop().into(holder.teacherImageView);
    }


    public void addItems(List<Teacher> items) {
        teachers.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(v, (Teacher) v.getTag());
    }

    class TeacherViewHolder extends RecyclerView.ViewHolder {

        ImageView teacherImageView;
        TextView teacherNameView;
        TextView teacherRatingView;
        TextView teacherRegisterTime;
        TextView teacherRefCounts;
        TextView teacherDescription;

        public TeacherViewHolder(View itemView) {
            super(itemView);
            teacherImageView = (ImageView) itemView.findViewById(R.id.img_profile);
            teacherNameView = (TextView) itemView.findViewById(R.id.txt_name);
            teacherRatingView = (TextView) itemView.findViewById(R.id.txt_rating);
            teacherDescription = (TextView) itemView.findViewById(R.id.txt_description);
            teacherRefCounts = (TextView) itemView.findViewById(R.id.txt_references);
            teacherRegisterTime = (TextView) itemView.findViewById(R.id.txt_register_time);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, Teacher teacher);

    }
}
