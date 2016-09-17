 Button b;
 ImageLoader il;
 PFont font;
 
Screen screen [] = new Screen [4];


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
    screen[0]  = new StartScreen();
    screen[1]  = new EndScreen();
    screen[2]  = new EndScreen();
    screen[3]  = new EndScreen();
    s = State.Start;
  //  il =  new ImageLoader();

    
    
}


void draw(){
    clear();
    background(80);
    screen[s.getValue()].display();

}

void mousePressed() {
    screen[s.getValue()].mousePressed();
}

 