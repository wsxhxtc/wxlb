package com.project.jsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.project.jsproject.activity.ActionPlanListActivity;
import com.project.jsproject.activity.FromApplayActivity;
import com.project.jsproject.activity.HistoryRecordActivity;
import com.project.jsproject.activity.TjActivity;

public class MainActivity extends NavHostFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_main,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mX1 =(TextView)view.findViewById(R.id.x1);
        mX1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), TjActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.x3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), HistoryRecordActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.x2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ActionPlanListActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.x4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), FromApplayActivity.class);
                startActivity(intent);
            }
        });
    }


}