package lk.ac.mrt.cse.dbs.simpleexpensemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wiranji Dinelka on 06-12-2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public SQLiteDatabase db=null;

    public DatabaseHelper(Context context) {

        super(context, DatabaseHandler.DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();

    }
    private static final String ACCOUNT_CREATE =
            "CREATE TABLE account (account_no TEXT NOT NULL,bank TEXT NOT NULL,account_holder TEXT NOT NULL,initial_balance REAL NOT NULL CHECK (initial_balance >= 0),PRIMARY KEY (account_no) );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create account table
        db.execSQL("CREATE TABLE account (account_no TEXT NOT NULL,bank TEXT NOT NULL,account_holder TEXT NOT NULL,initial_balance REAL NOT NULL CHECK (initial_balance >= 0),PRIMARY KEY (account_no) );");
        db.execSQL("CREATE TABLE AC_Transaction ( Transaction_no INTEGER PRIMARY KEY AUTOINCREMENT,account_no TEXT NOT NULL, Date TEXT NOT NULL,type TEXT NOT NULL , amount REAL NOT NULL, FOREIGN KEY(account_no) REFERENCES account(account_no));");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS transaction");

        onCreate(db);
    }
}
