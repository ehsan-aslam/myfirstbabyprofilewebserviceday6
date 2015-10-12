package putitout.myfirstbabyprofilewebserviceday6;

import java.util.ArrayList;

/**
 * Created by SA on 10/9/2015.
 */
public class ProfileModel {

    private String profile_id;
    private String profile_name;
    private String profile_gender;
    private String profile_img;
    private ArrayList<BabyProfileModel> mBabyLists = new ArrayList<BabyProfileModel>();


    public ProfileModel(String profile_id, String profile_name, String profile_gender, String profile_img, ArrayList<BabyProfileModel> mBabyLists) {
        super();
        this.profile_id = profile_id;
        this.profile_name = profile_name;
        this.profile_gender = profile_gender;
        this.profile_img = profile_img;
        this.mBabyLists = mBabyLists;
    }
    public ProfileModel(){}
    public String getProfile_id() {
        return profile_id;
    }
    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }
    public String getProfile_name() {
        return profile_name;
    }
    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
    public String getProfile_gender() {
        return profile_gender;
    }
    public void setProfile_gender(String profile_gender) {
        this.profile_gender = profile_gender;
    }
    public String getProfile_img() {
        return profile_img;
    }
    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }
    public ArrayList<BabyProfileModel> getmBabyLists() {
        return mBabyLists;
    }
    public void setmBabyLists(ArrayList<BabyProfileModel> mBabyLists) {
        this.mBabyLists = mBabyLists;
    }
    @Override
    public String toString() {
        return "ProfileModel{" +
                "profile_id='" + profile_id + '\'' +
                ", profile_name='" + profile_name + '\'' +
                ", profile_gender='" + profile_gender + '\'' +
                ", profile_img='" + profile_img + '\'' +
                ", mBabyLists=" + mBabyLists +
                '}';
    }
}
