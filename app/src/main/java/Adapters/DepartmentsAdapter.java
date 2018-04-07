package Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nobel.employeetracker.R;

import java.util.ArrayList;

import Abstract.Department;


/**
 * Created by Admin on 4/14/2017.
 */

public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.ViewHolder> {

    private Listener deleteLisitner;
    private Listener editLisitner;
    private ArrayList items;

    public DepartmentsAdapter(ArrayList items) {

        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_department, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        Department d=(Department) items.get(position);

        TextView name=cardView.findViewById(R.id.name);
        name.setText(d.getName());
        final Button delete= cardView.findViewById(R.id.delete);
        final Button edit= cardView.findViewById(R.id.edit);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteLisitner != null) {
                    deleteLisitner.onClick(position);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editLisitner != null) {
                    editLisitner.onClick(position);
                }
            }
        });

    }

    public void setDeleteLisitner(Listener deleteLisitner) {
        this.deleteLisitner = deleteLisitner;
    }

    public void setEditLisitner(Listener editLisitner) {
        this.editLisitner = editLisitner;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface Listener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
