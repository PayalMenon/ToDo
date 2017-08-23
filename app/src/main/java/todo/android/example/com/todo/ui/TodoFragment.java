package todo.android.example.com.todo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import javax.inject.Inject;
import todo.android.example.com.todo.Application;
import todo.android.example.com.todo.R;
import todo.android.example.com.todo.database.DbHelper;

public class TodoFragment extends Fragment implements MainActivity.FragmentListener {

    private static final String DETAILS_FRAGMENT = "details_fragment";

    private RecyclerView list;
    private TodoAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private TextView placeholderText;
    private List<ToDoObject> todoList;

    @Inject
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todo_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Application) getActivity().getApplication()).getDatabaseComponent().inject(this);

        placeholderText = getActivity().findViewById(R.id.placeholder_text);
        list = getActivity().findViewById(R.id.todo_list);

        todoList = dbHelper.getTodoList();

        adapter = new TodoAdapter(todoList, this);
        manager = new LinearLayoutManager(getActivity());

        list.setAdapter(adapter);
        list.setLayoutManager(manager);

        if (todoList.size() != 0) {

            placeholderText.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        } else {

            list.setVisibility(View.GONE);
            placeholderText.setVisibility(View.VISIBLE);
        }

        ((MainActivity)getActivity()).setFragmentListener(this);
    }

    @Override
    public void addToList(ToDoObject obj) {

        dbHelper.addTodo(obj);
        todoList.add(obj);
        updateList();
    }

    private void updateList() {

        if (todoList.size() > 0) {
            placeholderText.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteFromList(String title) {

        dbHelper.deleteTodo(title);
        updateList();

        DetailsFragment fragment = (DetailsFragment) getFragmentManager().findFragmentByTag(DETAILS_FRAGMENT);
        getFragmentManager().beginTransaction().remove(fragment).commit();
        getFragmentManager().popBackStack();
    }

    @Override
    public void updateListItem(ToDoObject toDoObject, int position) {
        ToDoObject object = todoList.get(position);
        todoList.set(position, toDoObject);
        dbHelper.updateTodoTask(toDoObject, object.getTodoTitle());
        updateList();
    }

    @Override
    public void onItemClicked(int position) {
        ToDoObject obj = todoList.get(position);

        DetailsFragment fragment = DetailsFragment.getInstance(obj.getTodoTitle(), position);
        getFragmentManager().beginTransaction()
                .addToBackStack("todo_fragment")
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.list_container, fragment, DETAILS_FRAGMENT)
                .commit();

    }
}
