package hu.bme.aut.amorg.examples.todo.db;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import hu.bme.aut.amorg.examples.todo.TodoListActivity;

/**
 * Created by bencetoth on 2018. 03. 07..
 */

public class LoadTodosTask extends AsyncTask<Void, Void, Cursor> {

    private static final String TAG = "LoadTodosTask";
    private TodoDbLoader dbLoader;
    private TodoListActivity listActivity;

    public LoadTodosTask(TodoListActivity listActivity, TodoDbLoader dbLoader) {
        this.listActivity = listActivity;
        this.dbLoader = dbLoader;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        try {

            Cursor result = dbLoader.fetchAll();

            if (!isCancelled()) {
                return result;
            } else {
                Log.d(TAG, "Cancelled, closing cursor");
                if (result != null) {
                    result.close();
                }

                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Cursor result) {
        super.onPostExecute(result);

        Log.d(TAG, "Fetch completed, displaying cursor results!");
        try {

            if (listActivity != null) {
                listActivity.showTodos(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}