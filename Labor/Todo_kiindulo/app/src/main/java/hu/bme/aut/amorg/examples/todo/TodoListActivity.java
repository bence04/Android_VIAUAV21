package hu.bme.aut.amorg.examples.todo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import hu.bme.aut.amorg.examples.todo.adapter.SimpleItemRecyclerViewAdapter;
import hu.bme.aut.amorg.examples.todo.adapter.TodoAdapter;
import hu.bme.aut.amorg.examples.todo.application.TodoApplication;
import hu.bme.aut.amorg.examples.todo.db.LoadTodosTask;
import hu.bme.aut.amorg.examples.todo.db.TodoDbLoader;
import hu.bme.aut.amorg.examples.todo.model.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Todos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TodoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TodoListActivity extends AppCompatActivity implements TodoCreateFragment.ITodoCreateFragment {
    // State
    private TodoAdapter adapter;

    // DBloader
    private TodoDbLoader dbLoader;
    private LoadTodosTask loadTodosTask;

    private boolean mTwoPane;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        dbLoader = TodoApplication.getTodoDbLoader();

        recyclerView = findViewById(R.id.todo_list);
        assert recyclerView != null;

        if (findViewById(R.id.todo_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // Frissitjuk a lista tartalmat, ha visszater a user
        refreshList();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (loadTodosTask != null) {
            loadTodosTask.cancel(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Ha van Cursor rendelve az Adapterhez, lezarjuk
        if (adapter != null && adapter.getCursor() != null) {
            adapter.getCursor().close();
        }
    }

    private void refreshList() {
        if (loadTodosTask != null) {
            loadTodosTask.cancel(false);
        }

        loadTodosTask = new LoadTodosTask(this, dbLoader);
        loadTodosTask.execute();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, TodoAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemCreateTodo) {
            TodoCreateFragment createFragment = new TodoCreateFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            createFragment.show(fm, TodoCreateFragment.TAG);
        } else if(item.getItemId() == R.id.deleteAllItem) {
            this.deleteAllItems();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteAllItems() {
        Cursor result = dbLoader.fetchAll();
        while (result.moveToNext()) {
            dbLoader.deleteTodo(Integer.parseInt(result.getString(0)));
        }
        refreshList();
    }

    @Override
    public void onTodoCreated(Todo newTodo) {
        dbLoader.createTodo(newTodo);
        refreshList();
    }

    public void showTodos(Cursor result) {
        adapter = new TodoAdapter(getApplicationContext(), result, mTwoPane);
        setupRecyclerView(recyclerView, adapter);
    }
}
