package putitout.myfirstbabyprofilewebserviceday6;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by SA on 10/8/2015.
 */
@SuppressWarnings("ALL")
public class APIProfileWebService extends AsyncTask<String, Void, String> {

    private Context mContext;
    private OnWebServiceInterface mWebServiceInterface;
    private int mRequestCode;
    private Boolean mValidResponse = true;

    public void APIProfileWebService(OnWebServiceInterface mWebServiceInterface) {
        this.mWebServiceInterface = mWebServiceInterface;
    }

    public APIProfileWebService() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mWebServiceInterface.onStartWebService(mRequestCode);
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader mBufferedReader = null;
        String strResult = null;
        try {
            ArrayList<NameValuePair> mParams = new ArrayList<>();
            mParams.add(new BasicNameValuePair(MyConstants.KEY_ACCESS_TOKEN, MyConstants.VALUE_ACCESS_TOKEN));
            mParams.add(new BasicNameValuePair(MyConstants.KEY_ID, MyConstants.VALUE_ID));
            HttpResponse httpResponse = getHttpResponse(MyConstants.API_URL, mParams, MyConstants.HTTP_PUT_REQUEST_TYPE);
            Log.d(MyConstants.LOG_BABY, "WebService Status response.getStatusLine() : "
                    + httpResponse.getStatusLine());
            mBufferedReader= new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer strBuffer = new StringBuffer("");
            String mLine = "";
            String lineSeparator = System.getProperty("line.separator");
            while((mLine= mBufferedReader.readLine())!=null){
                strBuffer.append(mLine + lineSeparator);
            }
            mBufferedReader.close();
            strResult = strBuffer.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.d(MyConstants.LOG_BABY,"RESPONSE : " + strResult);
            strResult = "null";
        } catch (IOException io) {

            if(mBufferedReader!=null){
                try{
                    mBufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            io.printStackTrace();
            Log.d(MyConstants.LOG_BABY, "RESPONSE : " + strResult);
            strResult="null";
        } catch (Exception e) {
            e.printStackTrace();
            strResult="null";
            Log.d(MyConstants.LOG_BABY, "RESPONSE : " + strResult);
        }


        return strResult;
    }

    private HttpResponse getHttpResponse(String mURL, ArrayList<NameValuePair> parameters, int requestCode)throws Exception{
        HttpClient httpClient = new DefaultHttpClient();
        UrlEncodedFormEntity  mUrlEncodedFromEntity = new UrlEncodedFormEntity(parameters,
                HTTP.UTF_8);
        HttpResponse mHttpResponse =  null;
        HttpRequest  mHttpRequest  =  null;
        HttpEntityEnclosingRequest mHttpEntityEnclosingRequest = null;
        switch (requestCode){
            case MyConstants.HTTP_GET_REQUEST_TYPE:
                mHttpRequest = new HttpGet(mURL);
                break;
            case MyConstants.HTTP_POST_REQUEST_TYPE:
                mHttpEntityEnclosingRequest= new HttpPost(mURL);
                break;
            case MyConstants.HTTP_PUT_REQUEST_TYPE:
                mHttpEntityEnclosingRequest= new HttpPut(mURL);
                break;
            case MyConstants.HTTP_DELETE_REQUEST_TYPE:
                mHttpEntityEnclosingRequest= new HttpDeletewithBody(mURL);
                break;
        }

        if(requestCode != MyConstants.HTTP_GET_REQUEST_TYPE){
            mHttpEntityEnclosingRequest.addHeader("auth-token",
                    "{sPjZze9s@4hyBAieLdWJFz2juAdgnnRhsTVC>Wih))J9WT(kr");

        }
        mHttpEntityEnclosingRequest.setEntity(mUrlEncodedFromEntity);
        mHttpResponse= httpClient.execute((HttpUriRequest) mHttpEntityEnclosingRequest);

        return mHttpResponse;
    }

    @Override
    protected void onPostExecute (String mResult){
        super.onPostExecute(mResult);
        mWebServiceInterface.onFinishWebService(mResult, mRequestCode, mValidResponse);
    }

    private class HttpDeletewithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";

        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpDeletewithBody(final String mURL) {
            super();
            setURI(URI.create(mURL));

        }
        public HttpDeletewithBody(final URI uri) {
            super();
            setURI(uri);
        }
        public HttpDeletewithBody(){

            super();
        }


    }


}