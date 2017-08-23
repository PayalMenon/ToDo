package todo.android.example.com.todo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import javax.inject.Inject;
import todo.android.example.com.todo.Application;
import todo.android.example.com.todo.R;
import todo.android.example.com.todo.database.DbHelper;

public class DetailsFragment extends Fragment implements MainActivity.DetailsFragmentListener{

    private static final String ADD_NEW_FRAGMENT = "add_new_fragment";

    private TextView titleText;
    private TextView notesText;
    private TextView priorityText;

    @Inject
    DbHelper dbHelper;

    public static DetailsFragment getInstance(String title, int position) {

        DetailsFragment fragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putString("Title", title);
        args.putInt("Position", position);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Application) getActivity().getApplication()).getDatabaseComponent().inject(this);

        ToDoObject object = dbHelper.getTodoTask(getArguments().getString("Title"));

        titleText = getActivity().findViewById(R.id.detail_title_text);
        titleText.setText(object.getTodoTitle());

        notesText = getActivity().findViewById(R.id.detail_notes_text);
        notesText.setText(object.getTodoNotes());

        priorityText = getActivity().findViewById(R.id.detail_priority_values);
        priorityText.setText(object.getTodoPriority());

        ((MainActivity)getActivity()).setDetailsFragmentListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_add);
        if (myActionMenuItem != null) {
            myActionMenuItem.setVisible(false);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_delete) {

            //((MainActivity)getActivity()).onToDoDeleted(taskPosition);
            ((MainActivity)getActivity()).onToDoDeleted(getArguments().getString("Title"));
            return true;
        } else if (item.getItemId() == R.id.action_edit) {

            AddNewFragment fragment = AddNewFragment.getInstance(AddNewFragment.MODE_EDIT,
                    getArguments().getString("Title"),
                    getArguments().getInt("Position"));
            fragment.show(getFragmentManager(), ADD_NEW_FRAGMENT);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateDetailsFragment(ToDoObject object) {
        titleText.setText(object.getTodoTitle());
        notesText.setText(object.getTodoNotes());
        priorityText.setText(object.getTodoPriority());
    }
}
