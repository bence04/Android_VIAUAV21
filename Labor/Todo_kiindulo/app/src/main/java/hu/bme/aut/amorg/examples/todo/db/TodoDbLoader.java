package hu.bme.aut.amorg.examples.todo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import hu.bme.aut.amorg.examples.todo.model.Todo;

/**
 * Created by bencetoth on 2018. 03. 07..
 */

public class TodoDbLoader {

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public TodoDbLoader(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {

        dbHelper = new DatabaseHelper(
                context, DbConstants.DATABASE_NAME);

        db = dbHelper.getWritableDatabase();

        dbHelper.onCreate(db);
    }

    public void close(){
        dbHelper.close();
    }

    // INSERT
    public long createTodo(Todo todo){
        ContentValues values = new ContentValues();
        values.put(DbConstants.Todo.KEY_TITLE, todo.getTitle());
        values.put(DbConstants.Todo.KEY_DUEDATE, todo.getDueDate());
        values.put(DbConstants.Todo.KEY_DESCRIPTION, todo.getDescription());
        values.put(DbConstants.Todo.KEY_PRIORITY, todo.getPriority());

        return db.insert(DbConstants.Todo.DATABASE_TABLE, null, values);
    }

    // DELETE
    public boolean deleteTodo(long rowId){
        return db.delete(
                DbConstants.Todo.DATABASE_TABLE,
                DbConstants.Todo.KEY_ROWID + "=" + rowId,
                null) > 0;
    }

    // UPDATE
    public boolean updateProduct(long rowId, Todo newTodo){
        ContentValues values = new ContentValues();
        values.put(DbConstants.Todo.KEY_TITLE, newTodo.getTitle());
        values.put(DbConstants.Todo.KEY_DUEDATE, newTodo.getDueDate());
        values.put(DbConstants.Todo.KEY_DESCRIPTION, newTodo.getDescription());
        values.put(DbConstants.Todo.KEY_PRIORITY, newTodo.getPriority());
        return db.update(
                DbConstants.Todo.DATABASE_TABLE,
                values,
                DbConstants.Todo.KEY_ROWID + "=" + rowId ,
                null) > 0;
    }

    // minden Todo lekérése
    public Cursor fetchAll(){
        // cursor minden rekordra (where = null)
        return db.query(
                DbConstants.Todo.DATABASE_TABLE,
                new String[]{
                        DbConstants.Todo.KEY_ROWID,
                        DbConstants.Todo.KEY_TITLE,
                        DbConstants.Todo.KEY_DESCRIPTION,
                        DbConstants.Todo.KEY_DUEDATE,
                        DbConstants.Todo.KEY_PRIORITY
                }, null, null, null, null, DbConstants.Todo.KEY_TITLE);
    }

    // egy Todo lekérése
    public Todo fetchTodo(long rowId){
        // a Todo-ra mutato cursor
        Cursor c = db.query(
                DbConstants.Todo.DATABASE_TABLE,
                new String[]{
                        DbConstants.Todo.KEY_ROWID,
                        DbConstants.Todo.KEY_TITLE,
                        DbConstants.Todo.KEY_DESCRIPTION,
                        DbConstants.Todo.KEY_DUEDATE,
                        DbConstants.Todo.KEY_PRIORITY
                }, DbConstants.Todo.KEY_ROWID + "=" + rowId,
                null, null, null, DbConstants.Todo.KEY_TITLE);
        // ha van rekord amire a Cursor mutat
        if(c.moveToFirst())
            return getTodoByCursor(c);
        // egyebkent null-al terunk vissza
        return null;
    }

    public static Todo getTodoByCursor(Cursor c){
        return new Todo(
                c.getString(c.getColumnIndex(DbConstants.Todo.KEY_TITLE)), // title
                c.getInt(c.getColumnIndex(DbConstants.Todo.KEY_PRIORITY)), // priority
                c.getString(c.getColumnIndex(DbConstants.Todo.KEY_DUEDATE)), // dueDate
                c.getString(c.getColumnIndex(DbConstants.Todo.KEY_DESCRIPTION)) // description
        );
    }
}