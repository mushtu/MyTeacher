/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.ac.ut.ece.moallem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.ac.ut.ece.moallem.api.model.Course;

import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.ece.moallem.R;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<Course> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Context context;

    public CourseRecyclerViewAdapter() {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course item = items.get(position);
        holder.text.setText(item.getName());
        holder.image.setImageBitmap(null);
        // this is mock codes for loading images from drawables
        if (item.getImage().equals("hesaban.jpg"))
            Picasso.with(holder.image.getContext()).load(R.drawable.hesaban).fit().centerInside().into(holder.image);
        else if (item.getImage().equals("jabr.jpg"))
            Picasso.with(holder.image.getContext()).load(R.drawable.jabr).fit().centerInside().into(holder.image);
        else if (item.getImage().equals("physics_2.jpg"))
            Picasso.with(holder.image.getContext()).load(R.drawable.physics_2).fit().centerInside().into(holder.image);
        else if (item.getImage().equals("physics_3.jpg"))
            Picasso.with(holder.image.getContext()).load(R.drawable.physics_3).fit().centerInside().into(holder.image);
        else
            Picasso.with(holder.image.getContext()).load(R.drawable.ic_book_default).fit().centerInside().into(holder.image);
        //Picasso.with(holder.image.getContext()).load(item.getImage()).into(holder.image); //TODO
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (Course) v.getTag());
    }

    public void setItems(List<Course> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<Course> items) {
        if (this.items == null)
            this.items = new ArrayList<>();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Course course) {
        if (this.items == null)
            this.items = new ArrayList<>();
        if (!containsCourse(course)) {
            this.items.add(course);
            notifyDataSetChanged();
        }
    }

    private boolean containsCourse(Course course) {
        if (items == null)
            return false;
        for (Course item : items) {
            if (item.getId() == course.getId())
                return true;
        }
        return false;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.name);
        }
    }


    public interface OnItemClickListener {

        void onItemClick(View view, Course course);

    }
}
