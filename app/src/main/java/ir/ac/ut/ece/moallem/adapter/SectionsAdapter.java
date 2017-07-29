package ir.ac.ut.ece.moallem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.api.model.Section;
import ir.ac.ut.ece.moallem.api.model.Student;
import ir.ac.ut.ece.moallem.api.model.Teacher;
import ir.ac.ut.ece.moallem.api.model.UserMode;
import ir.ac.ut.ece.moallem.config.AppConfig;

/**
 * Created by mushtu on 7/5/17.
 */

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.SectionViewHolder> {

    private List<Section> sections = new ArrayList<>();
    private OnCloseSectionListener onCloseSectionListener;
    private Context context;

    public void setOnCloseSectionListener(OnCloseSectionListener listener) {
        this.onCloseSectionListener = listener;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_section, parent, false);
        context = parent.getContext();
        return new SectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        final Section section = sections.get(position);
        if (AppConfig.getInstance(context).getUserMode() == UserMode.STUDENT.value()) {
            Teacher teacher = section.getTeacher();
            holder.txtName.setText(teacher.getFirstName() + " " + teacher.getLastName());
            holder.itemView.setTag(section);
            holder.courseName.setText("درس: " + section.getCourse().getName());
            String avatarUrl = teacher.getAvatarUrl();

            if (avatarUrl.equals("mushtu.jpg"))
                holder.imageView.setImageResource(R.drawable.mushtu);
            holder.mobileNumber.setText("شماره تماس: " + teacher.getMobile());
            holder.btnCloseSection.setVisibility(View.GONE);
            holder.txtNameTitle.setText("معلم: ");
        } else {

            Student student = section.getStudent();
            holder.txtNameTitle.setText("دانش آموز: ");
            holder.txtName.setText(student.getFirstName() + " " + student.getLastName());
            holder.itemView.setTag(section);
            holder.courseName.setText("درس: " + section.getCourse().getName());
            String avatarUrl = student.getAvatarUrl();
            if (avatarUrl.equals("reza.jpg"))
                holder.imageView.setImageResource(R.drawable.reza);
            holder.mobileNumber.setText("شماره تماس: " + student.getMobile());
            holder.btnCloseSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCloseSectionListener != null)
                        onCloseSectionListener.onCloseSectionClick(section);
                }
            });
        }

    }


    public void addItems(List<Section> items) {
        sections.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public void removeItem(Section section) {
        sections.remove(section);
        notifyDataSetChanged();
    }


    class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameTitle;
        ImageView imageView;
        TextView txtName;
        TextView courseName;
        TextView mobileNumber;
        Button btnCloseSection;

        public SectionViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_profile);
            txtName = (TextView) itemView.findViewById(R.id.txt_student_name);
            courseName = (TextView) itemView.findViewById(R.id.txt_course_name);
            mobileNumber = (TextView) itemView.findViewById(R.id.txt_mobile_number);
            btnCloseSection = (Button) itemView.findViewById(R.id.btn_close_section);
            txtNameTitle = (TextView) itemView.findViewById(R.id.txt_name_title);
        }
    }

    public interface OnCloseSectionListener {

        void onCloseSectionClick(Section section);

    }
}
