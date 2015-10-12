package putitout.myfirstbabyprofilewebserviceday6;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by SA on 10/8/2015.
 */
public class MyConstants {
    //Key Credentials
    public static final String KEY_ACCESS_TOKEN="access_token";
    public static final String VALUE_ACCESS_TOKEN="1444302917C2C0EAF0-AF1B-41B1-841F-4D5E8C6EA0C7";
    public static final String KEY_ID = "user_id";
    public static final String VALUE_ID = "852";
    public static final String API_URL = "http://kidlr.lotiv.com/api/user/profile";
    //Web Service Methods
    public static final int HTTP_GET_REQUEST_TYPE  = 0;
    public static final int HTTP_POST_REQUEST_TYPE = 1;
    public static final int HTTP_PUT_REQUEST_TYPE  = 2;
    public static final int HTTP_DELETE_REQUEST_TYPE = 3;
    // JSON Object Parent
    public static final String NODE_USER_PROFILE = "user_profile";
    // JSON Object USer Nodes
    public static final String TAG_USER_ID = "id";
    public static final String TAG_USER_NAME = "name";
    public static final String TAG_USER_GENDER = "gender";
    public static final String TAG_USER_IMG = "img";
    // JSON Array Children Parent
    public static final String NODE_CHILDREN_PROFILE = "children_profiles";
    // JSON Array Children NOdes
    public static final String TAG_CHILDREN_ID = "child_id";
    public static final String TAG_CHILDREN_NAME = "name";
    public static final String TAG_CHILDREN_AGE = "age";
    //JSONObject in Array Node
    public static final String TAG_CHILDREN_AGE_YEARS = "years";
    public static final String TAG_CHILDREN_AGE_MONTHS = "months";
    public static final String TAG_CHILDREN_AGE_DAYS = "days";

    public static final String TAG_CHILDREN_BIRTHDAY = "birthday";
    public static final String TAG_CHILDREN_WEIGHT = "weight";
    public static final String TAG_CHILDREN_HEIGHT = "height";
    public static final String TAG_CHILDREN_GENDER = "gender";
    public static final String TAG_CHILDREN_IMG = "img";
    //    LogCat
    public static final String LOG_BABY = "MYBabyProfile";


    private Context mContext;

    public MyConstants(Context context) {
        this.mContext = context;
    }
    // Check Internet Availability

    public boolean checkMobileInternetConn() {
        //Create object for ConnectivityManager class which returns network related info
        ConnectivityManager connectivity = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //If connectivity object is not null
        if (connectivity != null) {
            //Get network info - WIFI internet access
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (info != null) {
                //Look for whether device is currently connected to WIFI network
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}



//[access_token=1444302917C2C0EAF0-AF1B-41B1-841F-4D5E8C6EA0C7, user_id=852]
//
//        http://kidlr.lotiv.com/api/user/profile