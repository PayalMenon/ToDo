package todo.android.example.com.todo.ui;

public class ToDoObject {

    private String todoTitle;
    private String todoNotes;
    private String todoPriority;
    private int day;
    private int month;
    private int year;

    public String getTodoNotes() {
        return todoNotes;
    }

    public String getTodoPriority() {
        return todoPriority;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setTodoNotes(String todoNotes) {
        this.todoNotes = todoNotes;
    }

    public void setTodoPriority(String todoPriority) {
        this.todoPriority = todoPriority;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
