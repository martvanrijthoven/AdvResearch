package armexperiment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Athena
 */
public class Face {
    
    private final String faceID;
    private final boolean male;
    
    private final String happyURL;
    private final String sadURL;
    private final String neutralURL;
    
    private boolean happyEmotionSelected;
    private int presentationOrder;

    public Face(String faceID, boolean male, String happyURL, String sadURL, String neutralURL) {
        this.faceID = faceID;
        this.male = male;
        this.happyURL = happyURL;
        this.sadURL = sadURL;
        this.neutralURL = neutralURL;
        happyEmotionSelected = false;
    }

    public String getFaceID() {
        return faceID;
    }

    public boolean isMale() {
        return male;
    }

    public String getHappyURL() {
        return happyURL;
    }

    public String getSadURL() {
        return sadURL;
    }

    public String getNeutralURL() {
        return neutralURL;
    }

    public void setHappyEmotionSelected(boolean happyEmotionSelected) {
        this.happyEmotionSelected = happyEmotionSelected;
    }

    public boolean isHappyEmotionSelected() {
        return happyEmotionSelected;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    @Override
    public String toString() {
        return (male?"Male":"Female")+", emotion:"+ (happyEmotionSelected?"happy":"sad")+", trial:"+presentationOrder;
    }
    
    
    
}
