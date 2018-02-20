package proyecto.com.domos.util;



import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HelperUtil {

    public static boolean validaTeEmail(String email)
    {
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static AlertDialog createAlertDialog(Context context, @StringRes int tittle, @StringRes int message,
                                                @StringRes int btnPositiveLabel,
                                                @StringRes int btnNegativeLabel,
                                                DialogInterface.OnClickListener btnPositiveListener,
                                                DialogInterface.OnClickListener btnNegativeListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(tittle);
        builder.setPositiveButton(btnPositiveLabel,btnPositiveListener);
        builder.setNegativeButton(btnNegativeLabel,btnNegativeListener);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public static AlertDialog createAlertDialogWithViewwithoutButtons(Context context,
                                                                      @LayoutRes int view,
                                                                      @StringRes int tittle)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(tittle).setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
