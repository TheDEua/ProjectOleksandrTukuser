package com.example.projectoleksandrtukuser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectoleksandrtukuser.R;
import com.example.projectoleksandrtukuser.models.Models;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private int recourse;
    private ArrayList<Models> modelsList;

    public Adapter(ArrayList<Models> modelsList, int recourse){
        this.modelsList = modelsList;
        this.recourse = recourse;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recourse, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Models models = modelsList.get(position);
            holder.textNumber.setText(models.getNumber());
    }

    @Override
    public int getItemCount() {
        return modelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textNumber;
        public View view;
        public ViewHolder(View view){
            super(view);
            this.view = view;
            this.textNumber = (TextView) view.findViewById(R.id.constructorNumber);
        }
    }
}
