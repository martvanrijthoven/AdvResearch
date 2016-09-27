/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Athena
 */
public class ARMExperiment {
    
    
    // PARAMETERS // /
    private final int COOLDOWN = 3000;
    private final int TOTAL_FACES = 68; // must be a multiple of 4
    private String musicType = null;
   
    private int numTrials = 4; // =16
    private final String path = "..\\data\\";
    
    //list of all faces
    private ArrayList<Face> men_sad = new ArrayList<Face>();
    private ArrayList<Face> men_happy = new ArrayList<Face>();
    private ArrayList<Face> women_sad = new ArrayList<Face>();
    private ArrayList<Face> women_happy = new ArrayList<Face>();
    
    ARMExperiment(int audio) { // 0=silence, 1=white noise, 2=neutral
        switch (audio) {
            case 0:
                musicType = "silence";
                break;
            case 1:
                musicType = "whitenoise";
                break;
            case 2:
                musicType = "neutral";
                break;
        }

        // determine the subject number
        int n = 1;
        File file;
        do {
            file = new File(path + "subject" + n++ + ".csv");
        } while (file.exists() && !file.isDirectory());

        // open the file that corresponds to the tested subject
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            System.err.printf("Problem with opening file.");
            Logger.getLogger(ARMExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }

        // write the header of the csv file
        if(pw!=null){
            pw.write("subject,musicType,comparisonOrder,presentationOrder,presentedFaceID,comparisonFaceID,emotion,gender,response\n");
            pw.flush();
        }

        // import the faces women
        File file_women = new File(dataPath("")+"/images/women");
        String[] women = file_women.list();
        women = sort(women);
        String [] s;
        String [] s2;
        for(int w=0; w<women.length; w=w+2){
            
                if(!women[w].startsWith("S")) w++;
                String sadUrl = null;
                String happyUrl = null;
                String neutralUrl = null;
                
                s = split(women[w],'_');
                s2 = split(women[w+1],'_');

                switch(s[2]){
                    case "intense.png":   // emotion
                        if(s[1].compareTo("happy")==0){
                            happyUrl = women[w];
                            neutralUrl = women[w+1];
                            this.women_happy.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl));  
                        }
                        else{
                            sadUrl = women[w];
                            neutralUrl = women[w+1];
                            this.women_sad.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl));    
                        }
                    break;
                    case "neutral.png":
                        if(s2[1].compareTo("happy")==0){
                            happyUrl = women[w+1];
                            neutralUrl = women[w];
                            this.women_happy.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl));    
                        }
                        else{
                            sadUrl = women[w+1];
                            neutralUrl = women[w];
                            this.women_sad.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl));    
                        }
                    break;
                    default:System.out.println("error");
                }
            }
        

        File file_men = new File(dataPath("")+"/images/men");
        String[] men = file_men.list();
        men = sort(men);
       println("menlength" + men.length);
        for(int w=0; w<men.length; w=w+2){
            
                if(!men[w].startsWith("S")) w++;
                String sadUrl = null;
                String happyUrl = null;
                String neutralUrl = null;
                
                s = split(men[w],'_');
                s2 = split(men[w+1],'_');

                switch(s[2]){
                    case "intense.png":   // emotion
                        if(s[1].compareTo("happy")==0){
                            happyUrl = men[w];
                            neutralUrl = men[w+1];
                            this.men_happy.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl));
                 
                        }
                        else{
                            sadUrl = men[w];
                            neutralUrl = men[w+1];
                            this.men_sad.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl));    
                        }
                    break;
                    case "neutral.png":
                        if(s2[1].compareTo("happy")==0){
                            happyUrl = men[w+1];
                            neutralUrl = men[w];
                            this.men_happy.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl)); 
                                println("addhappymen");
                        }
                        else{
                            sadUrl = men[w+1];
                            neutralUrl = men[w];
                            this.men_sad.add(new Face(s[0],false,happyUrl,sadUrl,neutralUrl));    
                        }
                    break;
                    default:System.out.println("error");
                }
            }

        
        
        
        println("totalfaces:");
        println(this.men_sad.size()+ this.men_happy.size() + this.women_sad.size()+ this.women_happy.size());
        println(this.women_happy.size());
        // shuffle arrays
        long seed = System.nanoTime();
        Collections.shuffle(men_sad, new Random(seed));
        seed = System.nanoTime();
        Collections.shuffle(men_happy, new Random(seed));
        seed = System.nanoTime();
        Collections.shuffle(women_sad, new Random(seed));
        seed = System.nanoTime();
        Collections.shuffle(women_happy, new Random(seed));

        // SELECT PRESENTATION FACES // // //
        // define how many faces of each condition will be selected
        final int total_sad = TOTAL_FACES / 2;
        final int total_happy = TOTAL_FACES / 2;
        final int total_women = TOTAL_FACES / 2;
        final int total_men = TOTAL_FACES / 2;
        final int num_sad_men = men_sad.size(); // we have a limited number
        final int num_sad_wom = total_sad - num_sad_men;
        final int num_hap_men = num_sad_wom;
        final int num_hap_wom = num_sad_men;
        println("num_sad_men " + num_sad_men);
        println("num_sad_wom " + num_sad_wom);
        println("num_hap_men " + num_hap_men);
        println("num_hap_wom " + num_hap_wom);
         //face has to be a button
         ArrayList<Face> presentation_faces = new ArrayList<Face>();

        // select men
        presentation_faces.addAll(men_sad);
        
        println("nhm " + men_happy.size());
        for (int i = 0; i < num_hap_men; i++) 
            presentation_faces.add(men_happy.get(i));
        

        // select women
        for (int i = 0; i < num_sad_wom; i++) 
            presentation_faces.add(women_sad.get(i));
        
        for (int i = 0; i < num_hap_wom; i++) 
            presentation_faces.add(women_happy.get(i));
        

        // SELECT COMPARISON FACES // // //
        // remove selected faces (we cannot use the same face on both phases)
        women_happy.removeAll(presentation_faces);
        women_sad.removeAll(presentation_faces);
        men_happy.removeAll(presentation_faces);
        men_sad.removeAll(presentation_faces);

        // create an array containing all the avalable men
        ArrayList<Face> allMen = new ArrayList<Face>();
        allMen.addAll(men_happy);
        allMen.addAll(men_sad);

        // create an array containing all the avalable women
        ArrayList<Face> allWomen = new ArrayList<Face>();
        allWomen.addAll(women_happy);
        allWomen.addAll(women_sad);

        // create face pairs
        ArrayList<FacePair> fps = new ArrayList<FacePair>();

        for (int i = 0; i < total_women; i++)
            fps.add(new FacePair(presentation_faces.get(i), allWomen.get(i)));

        for (int i = 0; i < total_men; i++)
            fps.add(new FacePair(presentation_faces.get(total_women + i), allMen.get(i)));

        // shuffle pairs to randomise emotion and gender order
        seed = System.nanoTime();
        Collections.shuffle(fps, new Random(seed));

        // save presentation order (for statistical analysis)
        for (int i = 0; i < fps.size(); i++) {
            fps.get(i).getPresFace().setPresentationOrder(i);
        }

        // load demo/assessment too
        // if the code didn't crash then we are ready to start the experiment!
        System.out.println("All data loaded. Ready to run the experiment.");

        // present the demo/assessment
        boolean completed = false;
        do {

            System.out.println("Is everything clear?");
            // experiment leader gives an answer YES or NO
            if (true) { //TODO: if the experimenter answered "YES"
                completed = true;
            }
        } while (!completed);








        // PRESENTATION PHASE // // //
        // play music -> preferably different thread for music
        // present the images
        for (FacePair fp : fps) {
            System.out.println(fp.getPresFace().getEmotionURL());

            // wait 3s between image presentations
            try {
                Thread.sleep(COOLDOWN);
            } catch (InterruptedException ex) {
                Logger.getLogger(ARMExperiment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
       
       
       
       
        // COMPARISON PHASE // // //
        // shuffle the faces so that they are not shown in the same order
        seed = System.nanoTime();
        Collections.shuffle(fps, new Random(seed));

        System.out.println("Please select the face you saw");
        String first, second, answer;
        boolean correct;

        // show all face pairs
        FacePair fp;
        for (int i = 0; i < fps.size(); i++) {
            fp = fps.get(i);
            // show pairs of images
            if ((int) (Math.random() * (2)) == 0) {
                first = fp.getCompFace().getNeutralURL();
                second = fp.getPresFace().getNeutralURL();
            } else {
                first = fp.getPresFace().getNeutralURL();
                second = fp.getCompFace().getNeutralURL();
            }
            System.out.println(first + " OR " + second + "?");

            // record user's answer
            answer = fp.getPresFace().getFaceID();

            if (answer.equals(fp.getPresFace().getFaceID()))
                correct = true;
            else correct = false;

            // write a csv line
            // subject,musicType,presentationOrder,comparisonOrder,presentedFaceID,comparisonFaceID,emotion,gender,response\n
            pw.write(n + "," + musicType + "," + "," + fp.getPresFace().getPresentationOrder() + "," + i
                    + fp.getPresFace().getFaceID() + "," + fp.getCompFace().getFaceID() + ","
                    + (fp.isPresHappy() ? "happy" : "sad") + ","
                    + (fp.getPresFace().isMale() ? "male" : "female") + "," + (correct ? "correct" : "wrong") + "\n");
            pw.flush();
        }

        pw.close();
    }
}