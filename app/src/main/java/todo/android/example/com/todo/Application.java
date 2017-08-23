package todo.android.example.com.todo;

import android.content.Context;
import todo.android.example.com.todo.dragger.module.ApplicationModule;
import todo.android.example.com.todo.dragger.module.DatabaseModule;
import todo.android.example.com.todo.todo.android.example.com.todo.dragger.component.DaggerDatabaseComponent;
import todo.android.example.com.todo.todo.android.example.com.todo.dragger.component.DatabaseComponent;

public class Application extends android.app.Application {

    private DatabaseComponent databaseComponent;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        databaseComponent = DaggerDatabaseComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(getApplicationContext(), "todo.db", 1))
                .build();

        this.context = getApplicationContext();
    }

    public DatabaseComponent getDatabaseComponent() {
        return this.databaseComponent;
    }
}
