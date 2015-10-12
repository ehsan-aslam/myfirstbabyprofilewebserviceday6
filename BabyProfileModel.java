package putitout.myfirstbabyprofilewebserviceday6;

import java.util.ArrayList;

/**
 * Created by SA on 10/8/2015.
 */
public class BabyProfileModel  {
    public String id;
    public String name;
    public String years;
    public String months;
    public String days;
    public String birthday;
    public String weight;
    public String height;
    public String gender;
    public String img;

    public BabyProfileModel(String id, String name, String years, String months, String days, String birthday, String weight, String height, String gender, String img) {
        this.id = id;
        this.name = name;
        this.years = years;
        this.months = months;
        this.days = days;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "BabyProfileModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", years='" + years + '\'' +
                ", months='" + months + '\'' +
                ", days='" + days + '\'' +
                ", birthday='" + birthday + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", gender='" + gender + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

}
