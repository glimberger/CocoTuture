package fr.cnam.guillaumelimberger.cocotuture;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.sql.SQLException;
import java.util.HashMap;

public class SubscriberProvider extends ContentProvider {

    private static final String AUTHORITY = "fr.cnam.guillaumelimberger.cocotuture.database";
    private static final String URL = "content://" + AUTHORITY + "/subscriber";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private SQLiteDatabase db;
    public static final String DATABASE_FILENAME        = "database.db";
    public static final String DATABASE_NAME            = "database";
    public static final int    DATABASE_VERSION         = 1;

    public static final String SUBSCRIBER_TABLE_NAME    = "subscriber";
    public static final String SUBSCRIBER_ID            = "_id";
    public static final String SUBSCRIBER_FIRST_NAME     = "firstname";
    public static final String SUBSCRIBER_LAST_NAME      = "lastname";
    public static final String SUBSCRIBER_PHONE_NUMBER   = "phonenumber";
    public static final String SUBSCRIBER_EMAIL         = "email";
    public static final String SUBSCRIBER_LOGIN         = "login";
    public static final String SUBSCRIBER_PASSWORD      = "password";

    static HashMap<String, String> SUBSCRIBER_PROJECTION_MAP;

    private static final String SUBSCRIBER_CREATE_TABLE = "CREATE TABLE "+
            SUBSCRIBER_TABLE_NAME+" ("+SUBSCRIBER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            SUBSCRIBER_FIRST_NAME+" TEXT, "+
            SUBSCRIBER_LAST_NAME+" TEXT, "+
            SUBSCRIBER_PHONE_NUMBER+" TEXT, "+
            SUBSCRIBER_EMAIL+" TEXT, "+
            SUBSCRIBER_LOGIN+" TEXT, "+
            SUBSCRIBER_PASSWORD+" TEXT);";

    private static final String SUBSCRIBER_DROP_TABLE = "DROP TABLE IF EXISTS subscriber";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * Create a helper object to create, open, and/or manage a database.
         * This method always returns very quickly.  The database is not actually
         * created or opened until one of {@link #getWritableDatabase} or
         * {@link #getReadableDatabase} is called.
         *
         * @param context to use to open or create the database
         * @param name    of the database file, or null for an in-memory database
         * @param factory to use for creating cursor objects, or null for the default
         * @param version number of the database (starting at 1); if the database is older,
         *                {@link #onUpgrade} will be used to upgrade the database; if the database is
         *                newer, {@link #onDowngrade} will be used to downgrade the database
         */
        private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SUBSCRIBER_CREATE_TABLE);
        }

        /**
         * Called when the database needs to be upgraded. The implementation
         * should use this method to drop tables, add tables, or do anything else it
         * needs to upgrade to the new schema version.
         * <p/>
         * <p>
         * The SQLite ALTER TABLE documentation can be found
         * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
         * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
         * you can use ALTER TABLE to rename the old table, then create the new table and then
         * populate the new table with the contents of the old table.
         * </p><p>
         * This method executes within a transaction.  If an exception is thrown, all changes
         * will automatically be rolled back.
         * </p>
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older subscriber table if existed
            db.execSQL(SUBSCRIBER_DROP_TABLE);

            // create fresh books table
            this.onCreate(db);
        }
    }

    public SubscriberProvider() {}

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper;
        dbHelper = new DatabaseHelper(
                getContext(),
                DATABASE_FILENAME,
                null,
                DATABASE_VERSION);

        // Create a writable database which will trigger its creation
        // if it doesn't already exist.
        db = dbHelper.getWritableDatabase();

        return (db == null) ? false : true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.item/vnd.fr.cnam.guillaumelimberger.cocotuture.database.subscriber";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // add a new subscriber record
        long rowID = db.insert(SUBSCRIBER_TABLE_NAME, null, values);

        // check if record is added successfully
        if (rowID > 0)
        {
            Uri _uri  = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);

            return _uri;
        }
           //throw new SQLException("Impossible d'ajouter l'enregistrement dans " + uri + ".");
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(SUBSCRIBER_TABLE_NAME);
        qb.setProjectionMap(SUBSCRIBER_PROJECTION_MAP);

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // register to watch a content URI for changes
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
