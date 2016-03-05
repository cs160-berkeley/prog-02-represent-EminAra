package com.cs160.joleary.catnip;

/**
 * Created by eminArakelian on 3/3/16.
 */


public class congressPerson {
    private String name;
    private String emailWeb;

    private String title;
    private String tweet;
    private int iconID;
    private String billsAndCommittees;


    public congressPerson(String name, String emailWeb, String title, String tweet, int iconID, String billsAndCommittees) {

        this.name = name;
        this.emailWeb = emailWeb;
        this.title = title;
        this.tweet = tweet;
        this.iconID = iconID;
        this.billsAndCommittees = billsAndCommittees;

    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return emailWeb;
    }


    public String getTitle() {
        return title;
    }

    public String getTweet() {
        return tweet;
    }

    public int getIconID() {
        return iconID;
    }

    public String getBillsAndCommittees() {
        return billsAndCommittees;
    }
}

