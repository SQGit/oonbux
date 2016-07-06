package sqindia.net.oonbux;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//asdf
public class Config {


    // static final String REG_URL = "http://oonbux.sqindia.net/";


    static final String SER_URL = "http://oonsoft.eastus.cloudapp.azure.com/";


    static final String REG_URL = "http://androidtesting.newlogics.in/";

    static final String CSS_URL = "http://oonbux.sqindia.net/region";


    static final String GCM_PROJ_ID = "600977208793";
    static final String PKG_NAME = "sqindia.net.oonbux";
    static final String GCM_API_KEY = "AIzaSyA4QlRGMEdX-GPcFsC5VjzVxZqH5knJwFo";

    static final String MSG_KEY = "m";

    public static String[] countries = {"Africa", "India", "America"};
    public static String[] state_lists = {"alabama", "alaska",
            "arizona",
            "arkansas",
            "california",
            "colorado",
            "connecticut",
            "delaware",
            "district of columbia",
            "florida",
            "georgia",
            "hawaii",
            "idaho",
            "illinois",
            "indiana",
            "iowa",
            "kansas",
            "kentucky",
            "louisiana",
            "maine",
            "maryland",
            "massachusetts",
            "michigan",
            "minnesota",
            "mississippi",
            "missouri",
            "montana",
            "nebraska",
            "nevada",
            "new hampshire",
            "new jersey",
            "new mexico",
            "new york",
            "north carolina",
            "north dakota",
            "ohio",
            "oklahoma",
            "oregon",
            "pennsylvania",
            "rhode island",
            "south carolina",
            "south dakota",
            "tennessee",
            "texas",
            "utah",
            "vermont",
            "virginia",
            "washington",
            "west virginia",
            "wisconsin",
            "wyoming"};

    public static boolean isStringNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public static boolean isNetworkAvailable(Context c1) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c1.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}






