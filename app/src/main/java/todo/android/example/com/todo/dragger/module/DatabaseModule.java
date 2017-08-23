package todo.android.example.com.todo.dragger.module;

import android.content.Context;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import todo.android.example.com.todo.database.DbHelper;

@Module
public class DatabaseModule {

    Context context;
    String dbName;
    int dbVersion;

    public DatabaseModule (Context context, String dbName, int version){
        this.context = context;
        this.dbName = dbName;
        this.dbVersion = version;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper() {
        return new DbHelper(context, dbName, dbVersion);
    }
}
