package putitout.myfirstbabyprofilewebserviceday6;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity implements OnWebServiceInterface {

    private ListView mListView;
    private ProgressDialog mProgressDialog;
    private MyBabyProfileAdapter mBabyProfileAdapter;
    private TextView mTxtId, mTxtName, mTxtGender, mTxtImg;
    private ImageView mImageView;
    private APIProfileWebService mApiProfileWebService;
    private ProfileModel myProfileModel;
    private MyConstants netWorkUtils;
    private Boolean isConnectionExist = false;
    private Bitmap bitmap;
    private ProgressDialog pDialog;
    private Context mContext;
//    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.main_activity_list_View);
//        mTxtId = (TextView) findViewById(R.id.textView_main_activity_id);
        mTxtName = (TextView) findViewById(R.id.textView_main_activity_name);
        mImageView = (ImageView) findViewById(R.id.imageView);
//        mTxtGender = (TextView) findViewById(R.id.textView_main_activity_gender);
//        mTxtImg = (TextView) findViewById(R.id.textView_main_activity_img);

        netWorkUtils = new MyConstants(this);
        myProfileModel = new ProfileModel();

        isConnectionExist = netWorkUtils.checkMobileInternetConn();
        // check for Internet status
        if (isConnectionExist) {
            // Internet Connection exists
//            showAlertDialog(MainActivity.this, "Internet Connection",
//                    "Your device has WIFI internet access", true);
            mApiProfileWebService = new APIProfileWebService();
            mApiProfileWebService.APIProfileWebService(this);
            mApiProfileWebService.execute();

        } else {
            // Internet connection doesn't exist
//            showAlertDialog(MainActivity.this, "No Internet Connection",
//                    "Your device doesn't have WIFI internet access", false);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No Internet Connection Your device doesn't have WIFI internet access " +
                    "Are you sure,You wanted to Continue");
            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            mTxtName.setText("Check Your Internet Connection");
        }
    }

    @Override
    public void onStartWebService(int requestCode) {
        mProgressDialog = getProgressDialog();
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public ProgressDialog getProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading Please Wait");
            mProgressDialog.setCancelable(false);
        }
        return mProgressDialog;
    }

    @Override
    public void onFinishWebService(String responseData, int requestType, Boolean isValidResponse) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        parseUserProfile(responseData);
        if (mListView != null) {
            mBabyProfileAdapter = new MyBabyProfileAdapter(this, myProfileModel.getmBabyLists());
            mListView.setAdapter(mBabyProfileAdapter);
        }
        Log.d(MyConstants.LOG_BABY, "List.size  " + myProfileModel.getmBabyLists().size());
        Log.d(MyConstants.LOG_BABY, "response :  onCompleteWebService MainActivity " + responseData);
    }

    public void parseUserProfile(String response) {
        JSONObject user_profile = null;
        String jsonStr = response.toString();
        Log.d(MyConstants.LOG_BABY, ":" + jsonStr.toString());
        if (jsonStr != null) {

            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONObject data = jsonObj.getJSONObject("data");
                user_profile = data.getJSONObject(MyConstants.NODE_USER_PROFILE);
                String id = user_profile.getString(MyConstants.TAG_USER_ID);
                String name = user_profile.getString(MyConstants.TAG_USER_NAME);
                String gender = user_profile.getString(MyConstants.TAG_USER_GENDER);
                String img = user_profile.getString(MyConstants.TAG_USER_IMG);
                myProfileModel = new ProfileModel(id, name, gender, img, getParseBabyData(response));
//                mTxtId.setText("ID:     " + id + "");
                mTxtName.setText("Name:"+name );

                new DownloadImageTask(mImageView).execute(img);
//                new LoadImage().execute(img);
//                mTxtGender.setText("Gender: " + gender + "");
//                mTxtImg.setText("IMAGE:  " + img + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(MyConstants.LOG_BABY, " " + "Couldn't get any data from the url");
        }
    }

    //  Parse List Function
    public ArrayList<BabyProfileModel> getParseBabyData(String response) {

        JSONObject user_profile = null;
        JSONArray children_profiles = null;
        ArrayList<BabyProfileModel> mTemp = new ArrayList<BabyProfileModel>();
        String jsonStr = response.toString();
        Log.d(MyConstants.LOG_BABY, ":" + jsonStr.toString());
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONObject data = jsonObj.getJSONObject("data");
                children_profiles = data.getJSONArray(MyConstants.NODE_CHILDREN_PROFILE);
                for (int i = 0; i < children_profiles.length(); i++) {
                    JSONObject c = children_profiles.getJSONObject(i);
                    String child_id = c.getString(MyConstants.TAG_CHILDREN_ID);
                    String child_name = c.getString(MyConstants.TAG_CHILDREN_NAME);
                    // Age Node is JSON Object
                    JSONObject age = c.getJSONObject(MyConstants.TAG_CHILDREN_AGE);
                    String ageYears = age.getString(MyConstants.TAG_CHILDREN_AGE_YEARS);
                    String ageMonths = age.getString(MyConstants.TAG_CHILDREN_AGE_MONTHS);
                    String ageDays = age.getString(MyConstants.TAG_CHILDREN_AGE_DAYS);
                    // JSONArray Nodes
                    String birthday = c.getString(MyConstants.TAG_CHILDREN_BIRTHDAY);
                    String weight = c.getString(MyConstants.TAG_CHILDREN_WEIGHT);
                    String height = c.getString(MyConstants.TAG_CHILDREN_HEIGHT);
                    String gender = c.getString(MyConstants.TAG_CHILDREN_GENDER);
                    String img = c.getString(MyConstants.TAG_CHILDREN_IMG);


//                    new LoadImage().execute(img);
                    BabyProfileModel babyProfileModel = new BabyProfileModel(child_id, child_name, ageYears, ageMonths, ageDays,
                            birthday, weight, height, gender, img);
                    mTemp.add(babyProfileModel);
                    Log.d(MyConstants.LOG_BABY, "" + "getBabyProfileList : " + mTemp.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(MyConstants.LOG_BABY, " " + "Couldn't get any data from the url");
            return null;
        }
        return mTemp;
    }




    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

//    private class LoadImage extends AsyncTask<String, String, Bitmap> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Loading Image ....");
//            pDialog.show();
//
//        }
//
//        protected Bitmap doInBackground(String... args) {
//            try {
//                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        protected void onPostExecute(Bitmap image) {
//
//            if (image != null) {
//                mImageView.setImageBitmap(image);
//                pDialog.dismiss();
//
//            } else {
//
//                pDialog.dismiss();
//                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
}
