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

import Abstract.Task;


/**
 * Created by Admin on 4/14/2017.
 */

public class EmployeeTasksAdapter extends RecyclerView.Adapter<EmployeeTasksAdapter.ViewHolder> {

    private Listener deleteLisitner;
    private Listener editLisitner;
    private ArrayList items;

    public EmployeeTasksAdapter(ArrayList items) {

        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_task, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        Task t=(Task) items.get(position);

//        TextView taskname=cardView.findViewById(R.id.task_name);
        TextView emp_name=cardView.findViewById(R.id.emp_name);
       // name.setText(e.getName());
        final Button delete= cardView.findViewById(R.id.delete);
        final Button edit= cardView.findViewById(R.id.edit);
      //  String ename= MyApplication.getEmployeeName(e.getId_employee());
        delete.setVisibility(View.GONE);

        emp_name.setText(t.getTitle());

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
