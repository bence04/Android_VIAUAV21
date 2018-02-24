package hu.bme.aut.amorg.examples.todo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.bme.aut.amorg.examples.todo.R;
import hu.bme.aut.amorg.examples.todo.TodoDetailActivity;
import hu.bme.aut.amorg.examples.todo.TodoDetailFragment;
import hu.bme.aut.amorg.examples.todo.model.Todo;

/**
 * Created by bencetoth on 2018. 02. 24..
 */

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private boolean mTwoPane;
    private AppCompatActivity activity;

    private final List<Todo> todos;

    public SimpleItemRecyclerViewAdapter(List<Todo> todos, boolean mTwoPane, AppCompatActivity activity) {
        this.todos = todos;
        this.mTwoPane = mTwoPane;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_todo, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTodo = todos.get(position);
        holder.title.setText(todos.get(position).getTitle());
        holder.dueDate.setText(todos.get(position).getDueDate());

        switch (todos.get(position).getPriority()) {
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
                    arguments.putString(TodoDetailFragment.KEY_TODO_DESCRIPTION, todos.get(position).getDescription());
                    TodoDetailFragment fragment = new TodoDetailFragment();
                    fragment.setArguments(arguments);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.todo_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TodoDetailActivity.class);
                    intent.putExtra(TodoDetailFragment.KEY_TODO_DESCRIPTION, todos.get(position).getDescription());

                    context.startActivity(intent);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_todo);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (R.id.delete == item.getItemId()) {
                            deleteRow(position);
                        }
                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });

    }

    public void deleteRow(int position) {
        todos.remove(position);
        notifyDataSetChanged();
    }


    public void addItem(Todo aTodo) {
        todos.add(aTodo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
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