package admindistribution.com.admindistribution;

/**
 * Created by POPO on 1/21/2018.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager2 {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_SALT = "keysalt";
    private static final String KEY_POIN = "keypoin1";
    private static final String KEY_SERIAL = "keyserial";
    private static final String KEY_INITIAL = "keyinitial";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_NAMEPRODUCT = "product";
    private static final String KEY_ACTIVATION = "keyactive";
    private static final String KEY_IDs = "keyid";
    private static final String KEY_IDact = "keyidact";
    private static final String KEY_ACTCODE = "actcode";
    private static final String KEY_SERCODE = "ercode";
    private static final String KEY_INICODE = "iniact";
    private static final String KEY_NAMEACT = "keynameact";
    private static final String KEY_STATUSACT = "status";

    private static SharedPrefManager2 mInstance;
    private static Context mCtx;

    private SharedPrefManager2(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager2 getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager2(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User2 user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_POIN, user.getPoin());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SALT, null) != null;
    }

    //this method will give the logged in user
    public User2 getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User2(
                sharedPreferences.getString(KEY_POIN, null)
        );
    }

    public void getBarcode(User3 user3){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_POIN, user3.getPoin());
        editor.putString(KEY_SERIAL, user3.getSerial());
        editor.putString(KEY_INITIAL, user3.getInitial());
        editor.putString(KEY_NAMEPRODUCT, user3.getNamePro());
        editor.putString(KEY_ACTIVATION, user3.getActivation());
        editor.putString(KEY_IDs, user3.getId());


        editor.apply();
    }
    public User3 getbar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User3(
                sharedPreferences.getString(KEY_POIN, null),
                sharedPreferences.getString(KEY_SERIAL, null),
                sharedPreferences.getString(KEY_INITIAL, null),
                sharedPreferences.getString(KEY_NAMEPRODUCT, null),
                sharedPreferences.getString(KEY_ACTIVATION, null),
                sharedPreferences.getString(KEY_IDs, null)
        );
    }



    public void getAct(User4 user4){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IDact, user4.getIdAct());
        editor.putString(KEY_ACTCODE, user4.getActQr());
        editor.putString(KEY_SERCODE, user4.getSerCod());
        editor.putString(KEY_INICODE, user4.getIniCod());
        editor.putString(KEY_NAMEACT, user4.getNameAct());
        editor.putString(KEY_STATUSACT, user4.getStatusAct());


        editor.apply();
    }
    public User4 getActiv() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User4(
                sharedPreferences.getString(KEY_IDact, null),
                sharedPreferences.getString(KEY_ACTCODE, null),
                sharedPreferences.getString(KEY_SERCODE, null),
                sharedPreferences.getString(KEY_INICODE, null),
                sharedPreferences.getString(KEY_NAMEACT, null),
                sharedPreferences.getString(KEY_STATUSACT, null)
        );
    }

    public void history(ListItem histori) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAMEACT, histori.getNama());
        editor.putString(KEY_SERCODE, histori.getSerialcode());
        editor.putString(KEY_POIN, histori.getPoin());
        editor.putString(KEY_IDact, histori.getRedeemtime());
        editor.apply();
    }

//    public ListItem gethistory() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return new ListItem(
//                sharedPreferences.getString(KEY_NAMEACT, null),
//                sharedPreferences.getString(KEY_POIN, null),
//                sharedPreferences.getString(KEY_SERCODE, null),
//                sharedPreferences.getString(KEY_NAMEACT, null),
//                sharedPreferences.getString(KEY_INICODE,null
//                )
//        );
//    }
    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}