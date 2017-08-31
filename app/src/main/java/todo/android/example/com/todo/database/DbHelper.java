package todo.android.example.com.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import todo.android.example.com.todo.ui.ToDoObject;

@Singleton
public class DbHelper extends SQLiteOpenHelper {

    // TODO TABLE
    public static final String TODO_TABLE_NAME = "todo";
    public static final String TODO_COLUMN_ID = "id";
    public static final String TODO_COLUMN_TITLE = "title";
    public static final String TODO_COLUMN_ADDITIONAL_NOTES = "notes";
    public static final String TODO_COLUMN_PRIORITY = "priority";
    public static final String TODO_COLUMN_YEAR = "year";
    public static final String TODO_COLUMN_MONTH = "month";
    public static final String TODO_COLUMN_DAY = "day";

    public DbHelper(Context context, String dbName, int version) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL
                    ("CREATE TABLE IF NOT EXISTS "
                            + TODO_TABLE_NAME + "("
                            + TODO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + TODO_COLUMN_TITLE + " TEXT NOT NULL, "
                            + TODO_COLUMN_ADDITIONAL_NOTES + " TEXT NOT NULL, "
                            + TODO_COLUMN_PRIORITY + " INTEGER NOT NULL, "
                            + TODO_COLUMN_DAY + " INTEGER NOT NULL, "
                            + TODO_COLUMN_MONTH + " INTEGER NOT NULL, "
                            + TODO_COLUMN_YEAR + " INTEGER NOT NULL " + ")");
        } catch (SQLiteException e) {

            System.out.println("Exception while creating table " + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        onCreate(db);
    }

    public void addTodo(ToDoObject object) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(TODO_COLUMN_TITLE, object.getTodoTitle());
            values.put(TODO_COLUMN_PRIORITY, getPriority(object.getTodoPriority()));
            if (object.getTodoNotes() != null) {
                values.put(TODO_COLUMN_ADDITIONAL_NOTES, object.getTodoNotes());
            }
            values.put(TODO_COLUMN_DAY, object.getDay());
            values.put(TODO_COLUMN_MONTH, object.getMonth());
            values.put(TODO_COLUMN_YEAR, object.getYear());

            db.insert(TODO_TABLE_NAME, null, values);

        } catch (Exception e) {

            System.out.println("Failed to write todo to db with exception " + e.toString());
        } finally {
            db.close();
        }
    }

    public List<ToDoObject> getTodoList() {

        Cursor cursor;
        List<ToDoObject> todoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(" SELECT * FROM " + TODO_TABLE_NAME + " ORDER BY " + TODO_COLUMN_PRIORITY + " DESC ", null);
            try {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            ToDoObject object = new ToDoObject();
                            object.setTodoTitle(cursor.getString(1));
                            object.setTodoNotes(cursor.getString(2));
                            object.setTodoPriority(getPriority(cursor.getInt(3)));
                            object.setDay(cursor.getInt(4));
                            object.setMonth(cursor.getInt(5));
                            object.setYear(cursor.getInt(6));
                            todoList.add(object);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to get the list with exception " + e.toString());
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            System.out.println("Failed to get the list with exception " + e.toString());
        } finally {
            db.close();
        }
        return todoList;
    }

    public ToDoObject getTodoTask(String title) {
        ToDoObject object = new ToDoObject();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try {
            cursor = db.rawQuery(" SELECT * FROM " + TODO_TABLE_NAME + " WHERE " + TODO_COLUMN_TITLE + " = '" + title + "'", null);
            if (cursor != null) {
                cursor.moveToFirst();

                object.setTodoTitle(cursor.getString(cursor.getColumnIndex(TODO_COLUMN_TITLE)));
                object.setTodoNotes(cursor.getString(cursor.getColumnIndex(TODO_COLUMN_ADDITIONAL_NOTES)));
                object.setTodoPriority(getPriority(cursor.getInt(cursor.getColumnIndex(TODO_COLUMN_PRIORITY))));
                object.setDay(cursor.getInt(cursor.getColumnIndex(TODO_COLUMN_DAY)));
                object.setMonth(cursor.getInt(cursor.getColumnIndex(TODO_COLUMN_MONTH)));
                object.setYear(cursor.getInt(cursor.getColumnIndex(TODO_COLUMN_YEAR)));
            }
        } catch (Exception e) {
            System.out.println("Error while fetching item with exception = " + e.toString());
        } finally {
            db.close();
        }

        return object;
    }

    public void deleteTodo(String title) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TODO_TABLE_NAME, TODO_COLUMN_TITLE + "='" + title + "'", null);
        } catch (Exception e) {
            System.out.println("Failed to delete a todo with exception " + e.toString());
        } finally {
            db.close();
        }
    }

    public void updateTodoTask(ToDoObject object, String originalTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TODO_COLUMN_TITLE, object.getTodoTitle());
            values.put(TODO_COLUMN_PRIORITY, getPriority(object.getTodoPriority()));
            if (object.getTodoNotes() != null) {
                values.put(TODO_COLUMN_ADDITIONAL_NOTES, object.getTodoNotes());
            }
            values.put(TODO_COLUMN_DAY, object.getDay());
            values.put(TODO_COLUMN_MONTH, object.getMonth());
            values.put(TODO_COLUMN_YEAR, object.getYear());

            String selection = TODO_COLUMN_TITLE + " LIKE ?";
            String[] selectionArgs = {originalTitle};

            db.update(TODO_TABLE_NAME, values, selection, selectionArgs);
        } catch (Exception e) {
            System.out.println("Failed to update database with exception " + e.toString());
        } finally {
            db.close();
        }
    }

    private String getPriority(int position) {
        String priority = null;

        if (1 == position) {
            priority = "LOW";
        } else if (2 == position) {
            priority = "MEDIUM";
        } else {
            priority = "HIGH";
        }

        return priority;
    }

    private int getPriority(String priority) {

        int position;
        if ("LOW".equals(priority)) {
            position = 1;
        } else if ("MEDIUM".equals(priority)) {
            position = 2;
        } else {
            position = 3;
        }
        return position;
    }
}
