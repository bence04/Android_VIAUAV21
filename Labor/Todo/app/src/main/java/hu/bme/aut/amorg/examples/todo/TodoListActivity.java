package hu.bme.aut.amorg.examples.todo;

import android.content.Context;
import android.content.Intent;
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


import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.amorg.examples.todo.adapter.SimpleItemRecyclerViewAdapter;
import hu.bme.aut.amorg.examples.todo.model.Todo;

/**
 * An activity representing a list of Todos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TodoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TodoListActivity extends AppCompatActivity implements TodoCreateFragment.TodoCreatedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.todo_list);
        assert recyclerView != null;

        if (findViewById(R.id.todo_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupRecyclerView((RecyclerView) recyclerView);
        adapter = (SimpleItemRecyclerViewAdapter) ((RecyclerView) recyclerView).getAdapter();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ArrayList<Todo> todos = new ArrayList<Todo>();
        todos.add(new Todo("title1", Todo.Priority.LOW, "2011. 09. 26.", "description1"));
        todos.add(new Todo("title2", Todo.Priority.MEDIUM, "2011. 09. 27.", "description2"));
        todos.add(new Todo("title3", Todo.Priority.HIGH, "2011. 09. 28.", "description3"));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(todos, mTwoPane, TodoListActivity.this));
    }

    private SimpleItemRecyclerViewAdapter adapter;


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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoCreated(Todo newTodo) {
        adapter.addItem(newTodo);
        adapter.notifyDataSetChanged();
    }
}
