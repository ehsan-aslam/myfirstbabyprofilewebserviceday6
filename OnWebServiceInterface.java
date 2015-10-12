package putitout.myfirstbabyprofilewebserviceday6;

/**
 * Created by SA on 10/8/2015.
 */
public interface OnWebServiceInterface {
    public void onStartWebService(int requestType);
    public void onFinishWebService(String responseData, int requestType, Boolean isValidResponse);
}
