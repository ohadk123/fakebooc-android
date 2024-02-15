package com.example.fake_booc;

import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.List;

public class OptionsMenuListener<V> implements View.OnClickListener {
    private List<V> items;
    private int pos;
    private EditableAdapter itemAdapter;

    public OptionsMenuListener(List<V> items, int pos, EditableAdapter itemAdapter) {
        this.items = items;
        this.pos = pos;
        this.itemAdapter = itemAdapter;
    }

    @Override
    public void onClick(View v) {
        PopupMenu optionsMenu = new PopupMenu(v.getContext(), v);
        optionsMenu.inflate(R.menu.options_menu);
        optionsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.remove_item) {
                    removeItem();
                    return true;
                }
                if (item.getItemId() == R.id.edit_item) {
                    itemAdapter.editItem(pos);
                    return true;
                }
                return false;
            }
        });
        optionsMenu.show();
    }

    private void removeItem() {
        items.remove(pos);
        itemAdapter.notifyItemRemoved(pos);
        itemAdapter.notifyItemRangeChanged(pos, items.size());
    }
}
