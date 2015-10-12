package putitout.myfirstbabyprofilewebserviceday6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by SA on 10/8/2015.
 */
public class MyBabyProfileAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BabyProfileModel> mData;
    private ImageLoad imageLoader;
    ArrayList<HashMap<String, String>> data;

    public MyBabyProfileAdapter(Context mContext, ArrayList<BabyProfileModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
        imageLoader = new ImageLoad(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        TextView id = null, name = null, age = null, years = null, months = null, days = null,
                birthday = null, weight = null, height = null, gender = null;
        ImageView img = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_view, parent, false);
            id = (TextView) row.findViewById(R.id.adapter_textView_id);
            name = (TextView) row.findViewById(R.id.adapter_textView_name);
            age = (TextView) row.findViewById(R.id.adapter_textView_age);
            years = (TextView) row.findViewById(R.id.adapter_textView_age_years);
            months = (TextView) row.findViewById(R.id.adapter_textView_age_months);
            days = (TextView) row.findViewById(R.id.adapter_textView_age_days);
            birthday = (TextView) row.findViewById(R.id.adapter_textView_birthday);
            weight = (TextView) row.findViewById(R.id.adapter_textView_weight);
            height = (TextView) row.findViewById(R.id.adapter_textView_height);
            gender = (TextView) row.findViewById(R.id.adapter_textView_gender);
            img = (ImageView) row.findViewById(R.id.adapter_ImageView_img);
        } else {
            id = (TextView) row.findViewById(R.id.adapter_textView_id);
            name = (TextView) row.findViewById(R.id.adapter_textView_name);
            age = (TextView) row.findViewById(R.id.adapter_textView_age);
            years = (TextView) row.findViewById(R.id.adapter_textView_age_years);
            months = (TextView) row.findViewById(R.id.adapter_textView_age_months);
            days = (TextView) row.findViewById(R.id.adapter_textView_age_days);
            birthday = (TextView) row.findViewById(R.id.adapter_textView_birthday);
            weight = (TextView) row.findViewById(R.id.adapter_textView_weight);
            height = (TextView) row.findViewById(R.id.adapter_textView_height);
            gender = (TextView) row.findViewById(R.id.adapter_textView_gender);
            img = (ImageView) row.findViewById(R.id.adapter_ImageView_img);

        }
        id.setText(mData.get(position).getId());
        name.setText(mData.get(position).getName());
        age.setText(mData.get(position).getName());
        years.setText(mData.get(position).getYears());
        months.setText(mData.get(position).getMonths());
        days.setText(mData.get(position).getDays());
        birthday.setText(mData.get(position).getBirthday());
        weight.setText(mData.get(position).getWeight());
        height.setText(mData.get(position).getHeight());
        gender.setText(mData.get(position).getGender());

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 8;    // this will cut the sampling by 50%
//        Bitmap bitmap = BitmapFactory.decodeFile( mData.get(position).getImg(), options );
//
//        Uri u = Uri.parse(mData.get(position).getImg());
//        img.setImageURI(u);
//        System.gc();


        // Capture position and set results to the ImageView
// Passes flag images URL into ImageLoader.class to download and cache
// images

//        BabyProfileModel resultp = new BabyProfileModel();
////        resultp = data.get(position);
////        ArrayList<BabyProfileModel> resultp= new ArrayList<BabyProfileModel>();
////        imageLoader.DisplayImage(mData.get(), img);
//        resultp = mData.get(position);
//        imageLoader.DisplayImage("http://kidlr.lotiv.com/api/image/show?access_token=1444302917C2C0EAF0-AF1B-41B1-841F-4D5E8C6EA0C7&rt=children&ri=570&ca=main_image"
//                + MyConstants.TAG_CHILDREN_IMG, img);
//        imageLoader.DisplayImage(mData.get(position).getImg(), img);

       new DownloadImageTask(img).execute(mData.get(position).getImg());
//        img.setText(mData.get(position).getImg());
        Log.d(MyConstants.LOG_BABY, "child_id: " + mData.get(position).getId());
        Log.d(MyConstants.LOG_BABY, "child_name: " + mData.get(position).getName());
        Log.d(MyConstants.LOG_BABY, "child_age: " + mData.get(position).getName());
        Log.d(MyConstants.LOG_BABY, "ageYears: " + mData.get(position).getYears());
        Log.d(MyConstants.LOG_BABY, "ageMonths: " + mData.get(position).getMonths());
        Log.d(MyConstants.LOG_BABY, "ageDays: " + mData.get(position).getDays());
        Log.d(MyConstants.LOG_BABY, "Birthday: " + mData.get(position).getBirthday());
        Log.d(MyConstants.LOG_BABY, "Weight: " + mData.get(position).getWeight());
        Log.d(MyConstants.LOG_BABY, "Height: " + mData.get(position).getHeight());
        Log.d(MyConstants.LOG_BABY, "gender: " + mData.get(position).getGender());
        Log.d(MyConstants.LOG_BABY, "img: " + mData.get(position).getImg());
        return row;
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
}
