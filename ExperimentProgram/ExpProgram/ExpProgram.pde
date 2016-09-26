PFont font; 
Screen screen [] = new Screen [4];

// ImageLoader il =  new ImageLoader(); 

// Face[]

// Face-Button ???
ArrayList<Face> men_sad = new ArrayList<Face>();
ArrayList<Face> men_happy = new ArrayList<Face>();
ArrayList<Face> women_sad = new ArrayList<Face>();
ArrayList<Face> women_happy = new ArrayList<Face>();

ArrayList<Face> presentation_faces = new ArrayList<Face>();

// import the faces, save gender 

// shuffle arrays

// select faces 
final int total_face = 32;


// select sad
final int total_sad = total_face/2;
final int total_women = total_face/2;
final int total_men = total_face/2;


// select women
for(int i=0;i<total_sad-men_sad.size();i++){
    presentation_faces.add(women_sad.get(i));
}


final int total_happy= total_face/2;
for(int i=0;i<men_sad.size();i++){
    presentation_faces.add(women_happy.get(i));
}


// select men
presentation_faces.addall(men_sad);

for(int i=0;i<total_happy-men_sad.size();i++){
    presentation_faces.add(men_happy.get(i));
}


// remove selected faces
women_happy.removeAll(presentation_faces);
women_sad.removeAll(presentation_faces);
men_happy.removeAll(presentation_faces);
men_sad.removeAll(presentation_faces);

// concaternate the women sad and happy (similarly with men) // available faces
ArrayList<Face> allWomen = new ArrayList<>();
allWomen.addAll(women_happy);
allWomen.addAll(women_sad);

ArrayList<Face> allMen = new ArrayList<>();
allMen.addAll(men_happy);
allMen.addAll(men_sad);

// create face pairs
ArrayList<FacePair> fps = new ArrayList<FacePair>();



for(int i =0; i<total_women; i++)
    fps.add(new FacePair(presentation_faces.get(i),allWomen.get(i)));
    
for(int i =0; i<total_men; i++)
    fps.add(new FacePair(presentation_faces.get(total_women+i),allMen.get(i)));

//shuffle pairs


//present - trial (repeat)?
//present 
//test
//end


public enum State {
    Start(0), Train(1), Test(2), End(3);
    private final int value;
     
    private State(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}; 



public static State s;

 
void setup(){

    fullScreen();
    font = createFont ("Serif",100);
    textFont (font);
    
    // Screens
    screen[0]  = new StartScreen();
    screen[1]  = new TrainScreen(false);
    screen[2]  = new TestScreen(false);
    screen[3]  = new EndScreen();
    
    // State
    s = State.Start;
}

void draw(){
    clear();
    background(80);
    screen[s.getValue()].display();
}

void mousePressed() {
    screen[s.getValue()].mousePressed();
}