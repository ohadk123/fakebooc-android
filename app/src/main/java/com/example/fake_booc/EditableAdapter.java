package com.example.fake_booc;

import androidx.recyclerview.widget.RecyclerView;

public abstract class EditableAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    abstract void editItem(int pos);
}
