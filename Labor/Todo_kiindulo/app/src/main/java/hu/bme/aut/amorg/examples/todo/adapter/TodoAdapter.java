package hu.bme.aut.amorg.examples.todo.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hu.bme.aut.amorg.examples.todo.R;
import hu.bme.aut.amorg.examples.todo.TodoDetailActivity;
import hu.bme.aut.amorg.examples.todo.TodoDetailFragment;
import hu.bme.aut.amorg.examples.todo.db.TodoDbLoader;
import hu.bme.aut.amorg.examples.todo.model.Todo;

/**
 * Created by bencetoth on 2018. 03. 07..
 */

public class TodoAdapter extends CursorRecyclerViewAdapter<TodoAdapter.ViewHolder> {
    private boolean mTwoPane;

    public TodoAdapter(Context context, Cursor cursor, boolean mTwoPane) {
        super(context, cursor);
        this.mTwoPane = mTwoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        final Todo todo = TodoDbLoader.getTodoByCursor(cursor);

        holder.mTodo = todo;
        holder.title.setText(todo.getTitle());
        holder.dueDate.setText(todo.getDueDate());

        switch (todo.getPriority()) {
            case Todo.Priority.LOW:
                holder.priority.setImageResource(R.drawable.ic_low);
                break;
            case Todo.Priority.MEDIUM:
                holder.priority.setImageResource(R.drawable.ic_medium);
                break;
            case Todo.Priority.HIGH:
                holder.priority.setImageResource(R.drawable.ic_high);
                break;
            default:
                holder.priority.setImageResource(R.drawable.ic_high);
                break;
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(TodoDetailFragment.KEY_TODO_DESCRIPTION, todo.getDescription());
                    TodoDetailFragment fragment = new TodoDetailFragment();
                    fragment.setArguments(arguments);
                    ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.todo_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TodoDetailActivity.class);
                    intent.putExtra(TodoDetailFragment.KEY_TODO_DESCRIPTION, todo.getDescription());

                    context.startActivity(intent);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView dueDate;
        public final TextView title;
        public final ImageView priority;
        public Todo mTodo;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.textViewTitle);
            dueDate = (TextView) view.findViewById(R.id.textViewDueDate);
            priority = (ImageView) view.findViewById(R.id.imageViewPriority);
        }
    }
}