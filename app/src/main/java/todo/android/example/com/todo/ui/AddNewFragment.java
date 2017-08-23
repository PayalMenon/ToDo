package todo.android.example.com.todo.ui;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import javax.inject.Inject;
import todo.android.example.com.todo.Application;
import todo.android.example.com.todo.R;
import todo.android.example.com.todo.database.DbHelper;

public class AddNewFragment extends DialogFragment {

    public static final String MODE_ADD_NEW = "new";
    public static final String MODE_EDIT = "edit";

    private EditText titleText;
    private EditText notes;
    private Spinner prioritySpinner;
    private FloatingActionButton saveButton;
    private String priority;

    @Inject
    DbHelper dbHelper;

    public static AddNewFragment getInstance(String mode, String title, int position) {
        AddNewFragment fragment = new AddNewFragment();

        Bundle args = new Bundle();
        args.putString("mode", mode);
        args.putString("Title", title);
        args.putInt("Position", position);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_todo, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Application) getActivity().getApplication()).getDatabaseComponent().inject(this);

        titleText = view.findViewById(R.id.title_text);

        notes = view.findViewById(R.id.notes_text);

        prioritySpinner = view.findViewById(R.id.priority_values);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                priority = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(MODE_EDIT.equals(getArguments().getString("mode"))) {

            ToDoObject object = dbHelper.getTodoTask(getArguments().getString("Title"));
            titleText.setText(getArguments().getString("Title"));
            notes.setText(object.getTodoNotes());
            if("LOW".equals(object.getTodoPriority())) {
                prioritySpinner.setSelection(0);
            } else if ("MEDIUM".equals(object.getTodoPriority())) {
                prioritySpinner.setSelection(1);
            } else {
                prioritySpinner.setSelection(2);
            }
        }

        saveButton = view.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoObject obj = new ToDoObject();
                obj.setTodoTitle(titleText.getText().toString());
                obj.setTodoNotes(notes.getText().toString());
                obj.setTodoPriority(priority);

                if(MODE_EDIT.equals(getArguments().getString("mode"))) {
                    ((MainActivity) getActivity()).onTodoEdited(obj, getArguments().getInt("Position"));
                } else {
                    ((MainActivity) getActivity()).onTodoAdded(obj);
                }
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.95), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }
}
