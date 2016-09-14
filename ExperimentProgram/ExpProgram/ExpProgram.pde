 Button b;
 ImageLoader il;
void setup(){
    size(1000,800);
    il =  new ImageLoader();
    b =  new Button(100,700);
    
}


void draw(){
    clear();
    il.display();
    b.update();

}

void mousePressed() {
    // todo button click
    if (b.rectOver()) il.incIndex();
}

 