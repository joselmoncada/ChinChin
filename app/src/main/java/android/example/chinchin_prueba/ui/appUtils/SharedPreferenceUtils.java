package android.example.chinchin_prueba.ui.appUtils;

import android.content.Context;

public class SharedPreferenceUtils {
    public static android.content.SharedPreferences sp;

    public SharedPreferenceUtils(Context mContext) {                        //new session
        sp = mContext.getSharedPreferences(AppConstants.PACKAGENAME, 0);
    }

    public static void logoutUser() {  //close session
        sp.edit().clear().apply();
    }

    //----------------------IS SIGNED UP-------------------------
    public static Boolean isUserActive() {
        return sp.getBoolean("isUserActive", false);
    }

    public static void setActive(Boolean flag) {
        sp.edit().putBoolean("isUserActive", flag).apply();
    }
//---------------------------------------------------------------------------------
//--------------------------------IS LOGGED IN
    public static boolean isUserLoggedIn() {
        return sp.getBoolean("isUserLoggedIn", false);
    }

    public static void setUserLoggedIn(boolean value) {
        sp.edit().putBoolean("isUserLoggedIn", value).apply();
    }
//--------------------------------LOGGED

    //------------------------USER DATA
    public static String getEmail() {
        return sp.getString(AppConstants.EMAIL, "");
    }


    public static void setEmail(String flag) {
        sp.edit().putString(AppConstants.EMAIL, flag).apply();
    }


    public static String getPassword() {
        return sp.getString(AppConstants.PASSWORD, "");
    }


    public static void setPassword(String flag) {
        sp.edit().putString(AppConstants.PASSWORD, flag).apply();
    }


    public static String getUserNames() {
        return sp.getString(AppConstants.NAMES, "");
    }



    public static void setUserNames(String names){
        sp.edit().putString(AppConstants.NAMES, names).apply();
    }


    //---------------------------------/USERDATA-----------------------

}
