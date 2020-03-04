package com.mistershorr.databases;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {

    private int clumsiness;
    private double gymFrequency;
    private boolean isAwesome;
    private double moneyOwed;
    private int trustworthiness;
    private String name;
    private int overallLikability;

    private String objectId;
    private String ownerId;

    // MUST HAVE A DEFAULT, NO PARAM CONSTRUCTOR

    public Friend() {
    }

    public Friend(int clumsiness, double gymFrequency, boolean isAwesome,
                  double moneyOwed, int trustworthiness, String name) {
        this.clumsiness = clumsiness;
        this.gymFrequency = gymFrequency;
        this.isAwesome = isAwesome;
        this.moneyOwed = moneyOwed;
        this.trustworthiness = trustworthiness;
        this.name = name;
    }
    // MUST HAVE A GETTERS AND SETTERS


    public int getClumsiness() {
        return clumsiness;
    }

    public void setClumsiness(int clumsiness) {
        this.clumsiness = clumsiness;
    }

    public double getGymFrequency() {
        return gymFrequency;
    }

    public void setGymFrequency(double gymFrequency) {
        this.gymFrequency = gymFrequency;
    }

    public boolean isAwesome() {
        return isAwesome;
    }

    public void setAwesome(boolean awesome) {
        isAwesome = awesome;
    }

    public double getMoneyOwed() {
        return moneyOwed;
    }

    public void setMoneyOwed(double moneyOwed) {
        this.moneyOwed = moneyOwed;
    }

    public int getTrustworthiness() {
        return trustworthiness;
    }

    public void setTrustworthiness(int trustworthiness) {
        this.trustworthiness = trustworthiness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getOverallLikability() {
        return overallLikability;
    }

    public void setOverallLikability(int overallLikability) {
        this.overallLikability = overallLikability;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.clumsiness);
        dest.writeDouble(this.gymFrequency);
        dest.writeByte(this.isAwesome ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.moneyOwed);
        dest.writeInt(this.trustworthiness);
        dest.writeString(this.name);
        dest.writeString(this.ownerId);
        dest.writeString(this.objectId);
    }

    protected Friend(Parcel in) {
        this.clumsiness = in.readInt();
        this.gymFrequency = in.readDouble();
        this.isAwesome = in.readByte() != 0;
        this.moneyOwed = in.readDouble();
        this.trustworthiness = in.readInt();
        this.name = in.readString();
        this.ownerId = in.readString();
        this.objectId = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
