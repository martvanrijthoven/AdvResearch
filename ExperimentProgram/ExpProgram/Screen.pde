class Screen {

    String header; // header text 
    String text;   // text to show
    Button btn;
    
    Screen(){
        this.header = "";
        this.text = "";
    }
    
    public void display(){
       fill(255);
       textAlign(CENTER,CENTER);
       textSize(200);
       text(this.header,width/2,100);
       textSize(28);
       text(this.text,width/2,400);
    }
    
    public void mousePressed(){}
}