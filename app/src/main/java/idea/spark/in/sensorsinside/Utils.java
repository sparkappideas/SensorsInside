package idea.spark.in.sensorsinside;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by greynium on 26/7/16.
 */
public class Utils {

    public static boolean internetStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param context
     * @return
     * Used to get the Device Primary Email ID
     * GET_ACCOUNTS Permission should be added in manifest file
     * DEFAULT VALUE - null
     * Last Modified By M.Kanagasabapathi on July 20, 2016
     */
//    public static String getPrimaryEmail(Context context) {
//        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
//
//        Account[] accounts = AccountManager.get(context).getAccounts();
//        for (Account account : accounts) {
//            if (emailPattern.matcher(account.name).matches()) {
//                return account.name;
//            }
//        }
//        return null;
//    }
}
