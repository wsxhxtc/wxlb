package com.project.jsproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.project.jsproject.R;
import com.project.jsproject.adapter.ExerciseRecordsAdapter.Holder;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseRecordsAdapter extends Adapter<Holder> {

  private List<ExerciseRecord> records = new ArrayList<>();
  private Map<Long, PlanTask> taskMap = new HashMap<>();

  private Context context;
  private DateFormat dateFormat = new SimpleDateFormat("HH:mm");

  public ExerciseRecordsAdapter(Context context) {
    this.context = context;
  }

  public void updateData(List<ExerciseRecord> records, List<PlanTask> tasks) {
    this.records.clear();
    this.records.addAll(records);
    for (PlanTask task : tasks) {
      if (!taskMap.containsKey(task.id)) {
        taskMap.put(task.id, task);
      }
    }
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    return new Holder(
        LayoutInflater.from(context).inflate(R.layout.item_exercise_record, parent, false));
  }

  @Override
  public void onBindViewHolder(
      @NonNull ExerciseRecordsAdapter.Holder holder,
      int position) {
    ExerciseRecord record = records.get(position);
    PlanTask task = taskMap.get(record.taskId);
    if (task != null) {
      holder.title.setText(Utils.getTaskTitle(task));
    }
    holder.content.setText(String
        .format("%1$s 锻炼 %2$s 秒", dateFormat.format(new Date(record.timestamp)), record.duration));
  }

  @Override
  public int getItemCount() {
    return records.size();
  }

  public static class Holder extends ViewHolder {

    private TextView title;
    private TextView content;

    public Holder(
        @NonNull View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.title);
      content = itemView.findViewById(R.id.content);
    }
  }

}
