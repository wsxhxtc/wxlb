package com.project.jsproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.collection.LruCache;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.project.jsproject.R;
import com.project.jsproject.activity.ExerciseActivity;
import com.project.jsproject.adapter.TaskAdapter.Holder;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.ThreadUtils;
import com.project.jsproject.utils.ThreadUtils.SimpleTask;
import com.project.jsproject.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskAdapter extends Adapter<Holder> {

  private Context context;
  private List<PlanTask> data;
  private boolean selectMode; // 是否为选择计划任务模式
  private Set<PlanTask> selectedTaskSet = new HashSet<>(1);
  private MediaMetadataRetriever media = new MediaMetadataRetriever();
  private FrameDownloadManager frameManager = new FrameDownloadManager(10);

  public interface OnPlanTaskActionListener {

    void onDelete(PlanTask task);
  }

  private OnPlanTaskActionListener listener;

  public TaskAdapter(Context context, boolean selectMode) {
    this.context = context;
    this.selectMode = selectMode;
    data = new ArrayList<>();
  }

  public void setOnPlanTaskActionListener(OnPlanTaskActionListener listener) {
    this.listener = listener;
  }

  public void updateData(List<PlanTask> tasks) {
    data.clear();
    if (tasks != null && tasks.size() > 0) {
      data.addAll(tasks);
    }
    if (selectMode) {
      selectedTaskSet.clear();
    }
    notifyDataSetChanged();
  }

  public List<PlanTask> getSelectedTasks() {
    if (selectMode) {
      List<PlanTask> selected = new ArrayList<>();
      for (PlanTask task : data) {
        if (selectedTaskSet.contains(task)) {
          selected.add(task);
        }
      }
      return selected;
    }
    return Collections.emptyList();
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    return new Holder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false));
  }

  @Override
  public void onBindViewHolder(
      @NonNull TaskAdapter.Holder holder, int position) {
    PlanTask task = data.get(position);
    holder.title.setText(Utils.getTaskTitle(task));
    final String frame = Utils.getPlanTaskFrame(task);
    String tag = (String) holder.frame.getTag();
    if (tag != null && !TextUtils.equals(frame, tag)) {
      holder.frame.setImageBitmap(null);
    }
    holder.frame.setTag(frame);
    frameManager.load(holder.frame, frame);
    holder.category.setText(Utils.getCategoryText(task.category));
    if (selectMode) {
      holder.cbSelect.setVisibility(View.VISIBLE);
      holder.start.setVisibility(View.GONE);
      holder.cbSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
        if (isChecked) {
          selectedTaskSet.add(task);
        } else {
          selectedTaskSet.remove(task);
        }
      });
    } else {
      holder.cbSelect.setVisibility(View.GONE);
      holder.start.setVisibility(View.VISIBLE);
      holder.start.setOnClickListener(v -> {
        context.startActivity(ExerciseActivity.args(context, task.planId, task.id));
      });
      holder.itemView.setOnLongClickListener(v -> {
        if (listener != null) {
          listener.onDelete(task);
        }
        return true;
      });
    }
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public static class Holder extends ViewHolder {

    private ImageView frame;
    private TextView title;
    private TextView category;
    private AppCompatButton start;
    private CheckBox cbSelect;

    public Holder(@NonNull View itemView) {
      super(itemView);
      frame = itemView.findViewById(R.id.frame);
      title = itemView.findViewById(R.id.title);
      category = itemView.findViewById(R.id.category);
      start = itemView.findViewById(R.id.start);
      cbSelect = itemView.findViewById(R.id.cb_select);
    }
  }

  private class FrameDownloadManager extends LruCache<String, Bitmap> {

    public void load(@NonNull ImageView view, @NonNull String frame) {
      Bitmap bitmap = get(frame);
      if (bitmap != null) {
        view.setImageBitmap(bitmap);
        return;
      }
      ThreadUtils.executeByIo(new SimpleTask<Bitmap>() {
        @Override
        public Bitmap doInBackground() throws Throwable {
          media.setDataSource(context, Uri.parse(frame));
          return media.getFrameAtTime();
        }

        @Override
        public void onSuccess(Bitmap result) {
          put(frame, result);
          view.setImageBitmap(result);
        }
      });
    }

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is the maximum number of
     *                entries in the cache. For all other caches, this is the maximum sum of the
     *                sizes of the entries in this cache.
     */
    public FrameDownloadManager(int maxSize) {
      super(maxSize);
    }
  }

}
