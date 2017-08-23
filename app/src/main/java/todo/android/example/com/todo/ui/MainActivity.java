package todo.android.example.com.todo.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import todo.android.example.com.todo.R;

public class MainActivity extends AppCompatActivity {

    private static final String TODO_FRAGMENT = "todo_fragment";
    private static final String ADD_NEW_FRAGMENT = "add_new_fragment";

    private DetailsFragmentListener detailsFragmentListener;
    private FragmentListener fragmentListener;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public interface FragmentListener {

        void addToList(ToDoObject todoObject);
        void deleteFromList(String title);
        void updateListItem(ToDoObject toDoObject, int position);
        void onItemClicked(int position);
    }

    public interface DetailsFragmentListener {

        void updateDetailsFragment(ToDoObject object);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        addTodoFragment();
    }

    private void addTodoFragment() {

        TodoFragment fragment = (TodoFragment) manager.findFragmentByTag(TODO_FRAGMENT);

        if (fragment == null) {

            fragment = new TodoFragment();
            transaction.add(R.id.list_container, fragment, TODO_FRAGMENT);
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {
            releaseFragment();
        }
    }

    private void releaseFragment() {

        TodoFragment fragment = (TodoFragment) manager.findFragmentByTag(TODO_FRAGMENT);
        manager.beginTransaction()
                .remove(fragment).commit();
    }

    public void setFragmentListener(FragmentListener listener) {

        this.fragmentListener = listener;
    }

    public void setDetailsFragmentListener(DetailsFragmentListener listener) {

        this.detailsFragmentListener = listener;
    }

    public void onTodoAdded(ToDoObject obj) {
        fragmentListener.addToList(obj);

        AddNewFragment fragment = (AddNewFragment) manager.findFragmentByTag(ADD_NEW_FRAGMENT);
        manager.beginTransaction().remove(fragment).commit();
    }

    public void onToDoDeleted(String title) {
        fragmentListener.deleteFromList(title);
    }

    public void onTodoEdited(ToDoObject obj, int position) {
        fragmentListener.updateListItem(obj, position);
        detailsFragmentListener.updateDetailsFragment(obj);

        AddNewFragment fragment = (AddNewFragment) manager.findFragmentByTag(ADD_NEW_FRAGMENT);
        manager.beginTransaction()
                .remove(fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {

            AddNewFragment fragment = AddNewFragment.getInstance(AddNewFragment.MODE_ADD_NEW, null, -1);
            fragment.show(manager, ADD_NEW_FRAGMENT);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
