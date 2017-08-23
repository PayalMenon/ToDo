package todo.android.example.com.todo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import todo.android.example.com.todo.R;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<ToDoObject> todoList;
    private MainActivity.FragmentListener listener;

    public TodoAdapter(List<ToDoObject> todoList, MainActivity.FragmentListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, final int position) {
        ToDoObject obj = todoList.get(position);
        holder.todoTitle.setText(obj.getTodoTitle());
        holder.todoPriority.setText(obj.getTodoPriority());

        holder.todoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout todoContainer;
        TextView todoTitle;
        TextView todoPriority;

        public TodoViewHolder(View itemView) {
            super(itemView);

            todoTitle = itemView.findViewById(R.id.todo_title);
            todoPriority = itemView.findViewById(R.id.todo_priority);
            todoContainer = itemView.findViewById(R.id.todoContainer);
        }
    }
}
