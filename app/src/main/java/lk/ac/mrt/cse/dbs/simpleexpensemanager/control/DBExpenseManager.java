package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import java.sql.SQLOutput;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DbMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DbMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.db.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.db.DatabaseHelper;

/**
 * Created by Wiranji Dinelka on 06-12-2015.
 */
public class DBExpenseManager extends ExpenseManager {

    Context context;
    public DBExpenseManager(Context context) {
        this.context=context;
        DatabaseHelper mydb= new DatabaseHelper(context);
        setup();
    }

    @Override
    public void setup() {
        TransactionDAO dbMemoryTransactionDAO = new DbMemoryTransactionDAO(context);
        setTransactionsDAO(dbMemoryTransactionDAO);

        AccountDAO dbMemoryAccountDAO = new DbMemoryAccountDAO(context);
        setAccountsDAO(dbMemoryAccountDAO);
         DatabaseHandler db= new DatabaseHandler(context);

    }


}
