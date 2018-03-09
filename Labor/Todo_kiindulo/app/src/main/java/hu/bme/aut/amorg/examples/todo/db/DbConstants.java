package hu.bme.aut.amorg.examples.todo.db;

public class DbConstants {

    public static final String DATABASE_NAME = "data.db";
    public static final int DATABASE_VERSION = 1;
    public static String DATABASE_CREATE_ALL = Todo.DATABASE_CREATE;
    public static String DATABASE_DROP_ALL = Todo.DATABASE_DROP;

    public static class Todo{
        public static final String DATABASE_TABLE = "todo";
        public static final String KEY_ROWID = "_id";
        public static final String KEY_TITLE = "title";
        public static final String KEY_PRIORITY = "priority";
        public static final String KEY_DUEDATE = "dueDate";
        public static final String KEY_DESCRIPTION = "description";

        public static final String DATABASE_CREATE =
                "create table if not exists "+DATABASE_TABLE+" ( "
                        + KEY_ROWID +" integer primary key autoincrement, "
                        + KEY_TITLE + " text not null, "
                        + KEY_PRIORITY + " integer, "
                        + KEY_DUEDATE +" text, "
                        + KEY_DESCRIPTION +" text"
                        + "); ";

        public static final String DATABASE_DROP =
                "drop table if exists " + DATABASE_TABLE + "; ";
    }
}