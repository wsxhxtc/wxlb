package com.project.jsproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.project.jsproject.Commons;
import com.project.jsproject.R;
import com.project.jsproject.activity.PlanTaskListActivity;
import com.project.jsproject.adapter.PlansAdapter.Holder;
import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class PlansAdapter extends RecyclerView.Adapter<Holder> {

  private List<ActionPlan> plans;
  private final Context context;
  private List<String> categories = new ArrayList<>();

  public PlansAdapter(Context context) {
    this.context = context;
    this.plans = new ArrayList<>();
  }

  public void setData(List<ActionPlan> plans) {
    this.plans.clear();
    if (plans != null && plans.size() > 0) {
      this.plans.addAll(plans);
    }
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    return new Holder(
        LayoutInflater.from(context).inflate(R.layout.item_action_plan, parent, false));
  }

  @Override
  public void onBindViewHolder(
      @NonNull Holder holder, int position) {
    final ActionPlan plan = plans.get(position);
    holder.title.setText(plan.title);
    categories.clear();
    if (Utils.isContainCategory(plan.categories, Commons.CATEGORY_STJ)) {
      categories.add(Utils.getCategoryText(Commons.CATEGORY_STJ));
    }
    if (Utils.isContainCategory(plan.categories, Commons.CATEGORY_XJ)) {
      categories.add(Utils.getCategoryText(Commons.CATEGORY_XJ));
    }
    if (Utils.isContainCategory(plan.categories, Commons.CATEGORY_DT)) {
      categories.add(Utils.getCategoryText(Commons.CATEGORY_DT));
    }
    holder.tag1.setVisibility(View.GONE);
    holder.tag2.setVisibility(View.GONE);
    holder.tag3.setVisibility(View.GONE);
    if (categories.size() > 0) {
      holder.tag1.setVisibility(View.VISIBLE);
      holder.tag1.setText(categories.get(0));
    }
    if (categories.size() > 1) {
      holder.tag2.setVisibility(View.VISIBLE);
      holder.tag2.setText(categories.get(1));
    }
    if (categories.size() > 2) {
      holder.tag3.setVisibility(View.VISIBLE);
      holder.tag3.setText(categories.get(2));
    }
    holder.itemView.setOnClickListener(
        v -> context.startActivity(PlanTaskListActivity.args(context, plan.id)));
  }

  @Override
  public int getItemCount() {
    return plans.size();
  }

  protected static class Holder extends ViewHolder {

    TextView title;
    TextView tag1, tag2, tag3;

    public Holder(@NonNull View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.title);
      tag1 = itemView.findViewById(R.id.category_tag1);
      tag2 = itemView.findViewById(R.id.category_tag2);
      tag3 = itemView.findViewById(R.id.category_tag3);
    }
  }
}
