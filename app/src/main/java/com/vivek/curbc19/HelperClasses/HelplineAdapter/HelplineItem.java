package com.vivek.curbc19.HelperClasses.HelplineAdapter;

public class HelplineItem {


    private String Title,Content;
    private int userPhoto, callPhoto;

    public HelplineItem() {
    }


    public HelplineItem(String title, String content, int userPhoto, int callPhoto) {
        Title = title;
        Content = content;
        this.userPhoto = userPhoto;
        this.callPhoto = callPhoto;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setCallPhoto(int callPhoto) {
        this.callPhoto = callPhoto;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public int getCallPhoto() { return callPhoto; }

}
