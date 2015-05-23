package fr.cnam.guillaumelimberger.cocotuture;

import android.app.Activity;
import android.widget.EditText;

/**
 * Helper class for validation on EditText views
 *
 * @author Guillaume Limberger glim.dev@gmail.com
 */
public class EditTextValidator
{
    private final static int    EMAIL_MAXLENGTH                 = 254;

    private final static String PHONE_REGEX                     = "^[+]?[0-9]{10,13}$";
    private final static String EMAIL_REGEX                     = "[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}";
    private final static String LOGIN_REGEX                     = "^[a-zA-Z0-9]{5,20}$";
    private final static String PASSWORD_REGEX                  = "^[a-zA-Z0-9_@#*]{5,10}$";
    private final static String TIME_REGEX                      = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";


    private static boolean isEmptyText(Activity a, EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(a.getResources().getString(R.string.error_empty_text));
            return true;
        }

        return false;
    }

    public static boolean hasTextValidation(Activity a, EditText editText) {
        return  (!isEmptyText(a, editText));
    }

    public static boolean hasPhoneValidation(Activity a, EditText editText) {
        if (isEmptyText(a, editText)) {
            return false;
        }

        if (!editText.getText().toString().matches(PHONE_REGEX)) {
            editText.setError(a.getResources().getString(R.string.error_phone_match));
            return false;
        }

        return true;
    }

    public static boolean hasEmailValidation(Activity a, EditText editText) {
        if (isEmptyText(a, editText)) {
            return false;
        }

        if (editText.getText().toString().length() > EMAIL_MAXLENGTH) {
            editText.setError(a.getResources().getString(R.string.error_email_maxlength));
            return false;
        }

        if (!editText.getText().toString().matches(EMAIL_REGEX)) {
            editText.setError(a.getResources().getString(R.string.error_email_match));
            return false;
        }

        return true;
    }

    public static boolean hasLoginValidation(Activity a, EditText editText) {
        if (isEmptyText(a, editText)) {
            return false;
        }

        if (!editText.getText().toString().matches(LOGIN_REGEX)) {
            editText.setError(a.getResources().getString(R.string.error_login_match));
            return false;
        }

        return true;
    }

    public static boolean hasPasswordValidation(Activity a, EditText editText1, EditText editText2) {
        if (isEmptyText(a, editText1)) {
            return false;
        }

        if (isEmptyText(a, editText2)) {
            return false;
        }

        if (!editText1.getText().toString().matches(PASSWORD_REGEX)) {
            editText1.setError(a.getResources().getString(R.string.error_password_match));
            return false;
        }

        if (!editText1.getText().toString().equals(editText2.getText().toString())) {
            editText2.setError(a.getResources().getString(R.string.error_password_confirmation));
            return false;
        }

        return true;
    }

    public static boolean hasTimeValidation(Activity a, EditText editText) {
        if (isEmptyText(a, editText)) {
            return false;
        }

        if (!editText.getText().toString().matches(TIME_REGEX)) {
            editText.setError(a.getResources().getString(R.string.error_time));
            return false;
        }

        return true;
    }

}
