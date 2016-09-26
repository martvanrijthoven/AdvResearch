/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armexperiment;

/**
 *
 * @author Athena
 */
    public class FacePair{
    private final Face presFace;
    private final Face compFace;

    public FacePair(Face presFace, Face compFace) {
        this.presFace = presFace;
        this.compFace = compFace;
    }

    public Face getPresFace() {
        return presFace;
    }

    public Face getCompFace() {
        return compFace;
    }
    
    public boolean isPresHappy(){
        if(presFace.getHappyURL() == null || presFace.getHappyURL().equals(""))
            return false;
        return true;
    }
    
    // public getPresFace
    
    
   //private final String presFaceID;
   //private final String compFaceID;
   // private final boolean male;
    
   // private final String emotionURL;
   // private final boolean presEmption = 0; // 0=sad and 1=happy
   // private final String sadURL;
   // private final String neutralURL;
    
   // private boolean happyEmotionSelected;
   // private int presentationOrder;

    public FacePair() {
        this.presFace = null;
        this.compFace = null;
    }
}

