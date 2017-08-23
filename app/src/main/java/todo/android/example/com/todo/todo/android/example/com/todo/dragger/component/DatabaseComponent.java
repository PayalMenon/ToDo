package todo.android.example.com.todo.todo.android.example.com.todo.dragger.component;

import javax.inject.Singleton;
import dagger.Component;
import todo.android.example.com.todo.dragger.module.ApplicationModule;
import todo.android.example.com.todo.dragger.module.DatabaseModule;
import todo.android.example.com.todo.ui.AddNewFragment;
import todo.android.example.com.todo.ui.DetailsFragment;
import todo.android.example.com.todo.ui.TodoFragment;

@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface DatabaseComponent {

    void inject(TodoFragment fragment);
    void inject(DetailsFragment fragment);
    void inject(AddNewFragment fragment);
}
