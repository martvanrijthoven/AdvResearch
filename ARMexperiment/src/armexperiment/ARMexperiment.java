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

        int numTrials = 4; // =28

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

        // load all sad and happy images
        ArrayList<Face> allFaces = loadAllImages();

        // randomly select 28 images (14 female & 14 sad)
        ArrayList<Face> presentationFaces = selectPresentationFaces(allFaces, numTrials);

        // remove the used faces from the list
        for (Face f : presentationFaces)
            allFaces.remove(f);

        // select 28 more different images (for the final comparison)
        ArrayList<Face> comparisonFaces = selectComparisonFaces(allFaces, presentationFaces, numTrials);

        System.out.println("All data loaded. Ready to run the experiment.");

        // play music -> preferably different thread for music
        // present the images
        for (Face f : presentationFaces) {
            if (f.isHappyEmotionSelected()) {
                // present the happy emotion image
                System.out.println(f.getHappyURL());
            } else {
                // present the sad emotion image
                System.out.println(f.getSadURL());
            }

            // wait 3s between image presentations
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ARMexperiment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // end of phase 1
        //
        // phase 2: present pairs of images and let the user choose
        System.out.println("Please select the image you saw");
        String first, second, answer;
        boolean correct;
        for (int i = 0; i < presentationFaces.size(); i++) {
            // show pairs of images
            if ((int) (Math.random() * (2)) == 0) {
                first = presentationFaces.get(i).getNeutralURL();
                second = comparisonFaces.get(i).getNeutralURL();
            } else {
                first = comparisonFaces.get(i).getNeutralURL();
                second = presentationFaces.get(i).getNeutralURL();
            }
            System.out.println(first + " OR " + second + "?");

            // record user's answer
            answer = presentationFaces.get(i).getFaceID();

            if (answer.equals(presentationFaces.get(i).getFaceID()))
                correct = true;
            else correct = false;

            // write a csv line
            pw.write(n + "," + musicType + "," + i + "," + presentationFaces.get(i).getPresentationOrder() + ","
                    + presentationFaces.get(i).getFaceID() + "," + comparisonFaces.get(i).getFaceID() + ","
                    + (presentationFaces.get(i).isHappyEmotionSelected() ? "happy" : "sad") + "," + 
                    (presentationFaces.get(i).isMale() ? "male" : "female") + "," + (correct ? "correct" : "wrong")+"\n");
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
