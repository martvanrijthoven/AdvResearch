/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armexperiment;

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
public class ARMexperiment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // PARAMETERS // // //
        final int COOLDOWN = 3000;
        final int TOTAL_FACES = 32; // must be a multiple of 4

        // args -> which type of music?
        int audio = 1; // 0=silence, 1=white noise, 2=neutral
        String musicType = null;
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

        int numTrials = 4; // =16

        // determine the subject number
        String path = "..\\data\\";
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
            Logger.getLogger(ARMexperiment.class.getName()).log(Level.SEVERE, null, ex);
        }

        // write the header of the csv file
        pw.write("subject,musicType,comparisonOrder,presentationOrder,presentedFaceID,comparisonFaceID,emotion,gender,response\n");
        pw.flush();

        // Face-Button ???
        ArrayList<Face> men_sad = new ArrayList<>();
        ArrayList<Face> men_happy = new ArrayList<>();
        ArrayList<Face> women_sad = new ArrayList<>();
        ArrayList<Face> women_happy = new ArrayList<>();

        ArrayList<Face> presentation_faces = new ArrayList<>();

        // import the faces
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

        // select men
        presentation_faces.addAll(men_sad);
        for (int i = 0; i < num_hap_men; i++) {
            presentation_faces.add(men_happy.get(i));
        }

        // select women
        for (int i = 0; i < num_sad_wom; i++) {
            presentation_faces.add(women_sad.get(i));
        }
        for (int i = 0; i < num_hap_wom; i++) {
            presentation_faces.add(women_happy.get(i));
        }

        // SELECT COMPARISON FACES // // //
        // remove selected faces (we cannot use the same face on both phases)
        women_happy.removeAll(presentation_faces);
        women_sad.removeAll(presentation_faces);
        men_happy.removeAll(presentation_faces);
        men_sad.removeAll(presentation_faces);

        // create an array containing all the avalable men
        ArrayList<Face> allMen = new ArrayList<>();
        allMen.addAll(men_happy);
        allMen.addAll(men_sad);

        // create an array containing all the avalable women
        ArrayList<Face> allWomen = new ArrayList<>();
        allWomen.addAll(women_happy);
        allWomen.addAll(women_sad);

        // create face pairs
        ArrayList<FacePair> fps = new ArrayList<>();

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
                Logger.getLogger(ARMexperiment.class.getName()).log(Level.SEVERE, null, ex);
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

    public static ArrayList<Face> loadAllImages() {
        // normally read this information from a file
        ArrayList<Face> faces = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            faces.add(new Face("" + i, (i % 2 == 0), "happy" + i, "sad" + i, "neutral" + i));
        }
        return faces;
    }

    public static ArrayList<Face> selectPresentationFaces(ArrayList<Face> faces, int total) {
        // shuffle the list of faces to randomise the order
        long seed = System.nanoTime();
        Collections.shuffle(faces, new Random(seed));

        // select faces for presentation
        ArrayList<Face> selectedFaces = selectFaces(faces, total);

        // set happy emotion to half the faces
        int numm = total / 2;
        int numf = total - numm;
        int curm = 0, curf = 0;
        for (Face f : selectedFaces) {
            if (f.isMale() && curm < numm / 2) {
                f.setHappyEmotionSelected(true);
                curm++;
            }
            if (!f.isMale() && curf < numf / 2) {
                f.setHappyEmotionSelected(true);
                curf++;
            }
        }

        // shuffle to randomise emotion order
        Collections.shuffle(selectedFaces, new Random(seed));

        // set trial order
        for (int i = 0; i < selectedFaces.size(); i++)
            selectedFaces.get(i).setPresentationOrder(i);

        return selectedFaces;
    }

    public static ArrayList<Face> selectComparisonFaces(ArrayList<Face> faces, ArrayList<Face> presentationFaces, int total) {
        // select faces for the compariso phase
        ArrayList<Face> selectedFaces = selectFaces(faces, total);

        // order female and male faces in accordance to the presentation order
        int j;
        Face pf, cf;
        for (int i = 0; i < presentationFaces.size(); i++) {
            pf = presentationFaces.get(i);
            cf = selectedFaces.get(i);
            if (pf.isMale()) {
                if (!cf.isMale()) {
                    j = i + 1;
                    while (j < selectedFaces.size()) {
                        cf = selectedFaces.get(j);
                        if (cf.isMale()) {
                            Collections.swap(selectedFaces, i, j);
                            j++;
                            break;
                        }
                        j++;
                    }
                }
            } else {
                if (cf.isMale()) {
                    j = i + 1;
                    while (j < selectedFaces.size()) {
                        cf = selectedFaces.get(j);
                        if (!cf.isMale()) {
                            Collections.swap(selectedFaces, i, j);
                            j++;
                            break;
                        }
                    }
                }
            }
        }

        return selectedFaces;
    }

    private static ArrayList<Face> selectFaces(ArrayList<Face> faces, int total) {
        int numMale = total / 2;
        int numFeml = total - numMale;
        int curMale = 0; // number of selected male faces
        int curFeml = 0;

        ArrayList<Face> selectedFaces = new ArrayList<>();
        Face f;
        int i = 0;
        while (selectedFaces.size() < total && i < faces.size()) {
            f = faces.get(i);
            i++;

            if (f.isMale()) { // if the selected face is male
                if (curMale == numMale) continue; // don't add it to the list
                selectedFaces.add(f);
                curMale++;
            }
            if (!f.isMale()) {
                if (curFeml == numFeml) continue; // don't add it to the list
                selectedFaces.add(f);
                curFeml++;
            }
        }

        return selectedFaces;
    }
}
