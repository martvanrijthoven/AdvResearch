class Button {
    int x, y;      // Position button
    int size = 90; 
    color rectColor;
    color rectHighlight;
    boolean rectOver = false;
    
    Button (int x, int y){
        this.x = x;
        this.y = y;
        this.rectColor = color(0);
        this.rectHighlight = color(51);
    }
    
    
    void update(){
        if (rectOver()) {
            fill(rectHighlight);
        } 
        else {
            fill(rectColor);
        }
        stroke(255);
        rect(this.x, this.y, this.size, this.size);
    }
    boolean rectOver()  {
      if (mouseX >= this.x && mouseX <= this.x+this.size && 
          mouseY >= this.y && mouseY <= this.y+size) {
        return true;
      } else {
        return false;
      }
    }
    
    
    void Click(){
        // todo
    }
}
