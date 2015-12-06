package lk.ac.mrt.cse.dbs.simpleexpensemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Wiranji Dinelka on 06-12-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static  DatabaseHandler handler=null;
    public SQLiteDatabase db = null;

    public static final String DATABASE_NAME = "130277G";

    public static final String TABLE_ACCOUNT = "account";
    public static final String TABLE_TRANSACTION = "AC_Transaction";

    public static final String COL_ACCOUNT_NO = "account_no";
    public static final String COL_BANK = "bank";
    public static final String COL_ACCUONT_HOLDER = "account_holder";
    public static final String COL_INITIAL_BALANCE = "initial_balance";

    public static final String COL_TRANSACTION_ID = "Transaction_no";
    public static final String COL_TRANSACTION_ACCOUNT_NO = "account_no";
    public static final String COL_TYPE = "type";
    public static final String COL_DATE = "date";
    public static final String COL_AMOUNT = "amount";



    public static  SQLiteDatabase getWritableDatabase(Context context) throws SQLiteException
    {
        if( handler== null)
        {
            synchronized (DatabaseHandler.class){
                handler = new DatabaseHandler(context);
            }

        }
        return handler.getWritableDatabase();
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }
    public static  final SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy", Locale.getDefault());

    public static String getDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date getTimeValue(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
