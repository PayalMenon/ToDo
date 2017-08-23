package todo.android.example.com.todo.ui;

public class ToDoObject {

    private String todoTitle;
    private String todoNotes;
    private String todoPriority;

    public String getTodoNotes() {
        return todoNotes;
    }

    public String getTodoPriority() {
        return todoPriority;
    }

    public String getTodoTitle() {
        return todoTitle;
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
}
