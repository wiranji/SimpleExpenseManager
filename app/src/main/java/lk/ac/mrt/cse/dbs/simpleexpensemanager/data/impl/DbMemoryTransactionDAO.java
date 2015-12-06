package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.db.DatabaseHandler;

/**
 * Created by Wiranji Dinelka on 06-12-2015.
 */
public class DbMemoryTransactionDAO implements TransactionDAO {

    List<Transaction> transactions;
    Context context;
    SQLiteDatabase db= null;
    public DbMemoryTransactionDAO(Context context)
    {
        this.context=context;
        db = DatabaseHandler.getWritableDatabase(context);
    }
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.COL_TRANSACTION_ACCOUNT_NO, accountNo);
        contentValues.put(DatabaseHandler.COL_TYPE, expenseType.toString());
        contentValues.put(DatabaseHandler.COL_DATE, DatabaseHandler.dateFormat.format(date));
        contentValues.put(DatabaseHandler.COL_AMOUNT, amount);

        long result=db.insert(DatabaseHandler.TABLE_TRANSACTION, null, contentValues);
        if(result == -1){
            Log.d("MYACTIVITY", "NOT_INSERTED");
        }
        else
            Log.d("MYACTIVITY","DATA_INSERTED");

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new LinkedList<>();
       SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy", Locale.getDefault());
        Cursor cursor = db.rawQuery("select * from "+DatabaseHandler.TABLE_TRANSACTION,null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
        }

        else
        {
            if(cursor.moveToFirst()) {
                do {
                    try {

                        // attribute of tansaction object
                        Date date = dateFormat.parse(cursor.getString(2));
                        String acc_no = cursor.getString(1);
                        String expenceTypeString = cursor.getString(3);
                        double amount = cursor.getDouble(4);
                        ExpenseType expenceType = null;
                        if (expenceTypeString.equals(ExpenseType.EXPENSE.toString())){
                            expenceType = ExpenseType.EXPENSE;
                        }
                        else{
                            expenceType = ExpenseType.INCOME;
                        }

                        // create transaction object
                        Transaction transaction = new Transaction(date,acc_no,expenceType,amount);

                        // add transaction to list
                        transactions.add(transaction);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }while (cursor.moveToNext()) ;
            }

        }

        return transactions;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = getAllTransactionLogs();

        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }

}
