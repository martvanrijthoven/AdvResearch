class StartScreen extends Screen {
    
    StartScreen(){
        super();
        this.header = "WELCOME";
        this.text = "In this experiment we are gooing to test \n blalasdlkasdlkjaldkjalkjdl \n kajdslk asdaksldjalkjdslkajdkls alfkdjaksldjkaljdklajdslk adkljaklsdjlakdjslkaj ";
        btn = new Button(width/2,height-180/2,90,40,"Start");
    }
    
    public void display(){
        super.display();
        fill(0);
        btn.update();
    }
    
    public void mousePressed(){
        if(btn.rectOver()){
            ExpProgram.s = ExpProgram.State.Train;
        }
    }
}