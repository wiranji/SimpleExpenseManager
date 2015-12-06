package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.db.DatabaseHandler;

/**
 * Created by Wiranji Dinelka on 06-12-2015.
 */
public class DbMemoryAccountDAO implements AccountDAO {
    Context context;
    Account account;
    List <Account> accounts;
    List <String> accNumber;
    SQLiteDatabase db = null;
    public DbMemoryAccountDAO(Context context)
    {

        this.context=context;
        db = DatabaseHandler.getWritableDatabase(context);
    }
    @Override
    public List<String> getAccountNumbersList() {

        List<String> aCC_NameList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select "+DatabaseHandler.COL_ACCOUNT_NO +" from account",null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    String acc_no = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_ACCOUNT_NO));
                    aCC_NameList.add(acc_no);
                } while (cursor.moveToNext());
            }
        }
        return aCC_NameList;

    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> account_list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from account",null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
            return null;
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    String acc_no = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_ACCOUNT_NO));
                    String bank_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_BANK));
                    String ac_Holder = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_ACCUONT_HOLDER));
                    double balance = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.COL_INITIAL_BALANCE));

                    Account account = new Account(acc_no,bank_name,ac_Holder,balance);
                    account_list.add(account);
                } while (cursor.moveToNext());
            }

        } return account_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
         Account account = null;
        Cursor cursor = db.rawQuery("select * from account where "+DatabaseHandler.COL_ACCOUNT_NO+" = "+accountNo+";",null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    String acc_no = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_ACCOUNT_NO));
                    String bank_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_BANK));
                    String ac_Holder = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_ACCUONT_HOLDER));
                    double balance = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.COL_INITIAL_BALANCE));

                    account = new Account(acc_no,bank_name,ac_Holder,balance);

                } while (cursor.moveToNext());
            }
        }return account;


    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db= null;
        db = DatabaseHandler.getWritableDatabase(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.COL_ACCOUNT_NO, account.getAccountNo());
        contentValues.put(DatabaseHandler.COL_ACCUONT_HOLDER, account.getAccountHolderName());
        contentValues.put(DatabaseHandler.COL_BANK,account.getBankName());
        contentValues.put(DatabaseHandler.COL_INITIAL_BALANCE, account.getBalance());

        long res=  db.insert(DatabaseHandler.TABLE_ACCOUNT, null, contentValues);
        if(res == -1){
            Log.d("MYACTIVITY", "NOT_INSERTED");
        }
        else
            Log.d("MYACTIVITY","INSERTED ACCOUNT");

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        Account account = getAccount(accountNo);
        if (account == null) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }

        int state = db.delete("account", DatabaseHandler.COL_ACCOUNT_NO+" = "+accountNo,null );

        if(state == -1){
            Log.d("MYACTIVITY","NOT_DELETED");
        }
        else
            Log.d("MYACTIVITY","DELETED DATA");
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        account=getAccount(accountNo);
        if (account == null) {

            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;

        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.COL_INITIAL_BALANCE, account.getBalance());

        int state=db.update(DatabaseHandler.TABLE_ACCOUNT, contentValues,DatabaseHandler.COL_ACCOUNT_NO+"="+account.getAccountNo(),null );

        if(state == -1)
        {
            Log.d("Activity", "Updated");
        }
        else
            Log.d("Activity","Not Updated");
    }


}
