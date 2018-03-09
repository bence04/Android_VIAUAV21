package hu.bme.aut.amorg.examples.todo.model;

public class Todo {

    public interface Priority {
        int LOW = 0;
        int MEDIUM = 1;
        int HIGH = 2;
    }

    private String title;
    private int priority;
    private String dueDate;
    private String description;

    public Todo(String title, int priority, String dueDate, String description) {
        this.title = title;
        this.priority = priority;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
