package putitout.myfirstbabyprofilewebserviceday6;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by SA on 10/12/2015.
 */
public class LoadImage extends AsyncTask<String,String,Bitmap> {

    private Context mContext;
    private Bitmap bitmap;
    private  OnBitMapInterface mBitMapInterface;
    private ImageView mImageView;
    private int mBitmap;


    public void LoadImage(){}

    public void  LoadImage(OnBitMapInterface mBitMapInterface) {
        this.mBitMapInterface = mBitMapInterface;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        pDialog = new ProgressDialog(MainActivity.this);
//        pDialog.setMessage("Loading Image ....");
//        pDialog.show();
        mBitMapInterface.onStartBitMap(mBitmap);

    }

    protected Bitmap doInBackground(String... args) {
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap image) {

        if (image != null) {
           mImageView.setImageBitmap(image);
//            pDialog.dismiss();

        } else {

//            pDialog.dismiss();
            Toast.makeText(mContext, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

        }

        mBitMapInterface.onCompeletedBitMap(mBitmap,image);
    }
}

