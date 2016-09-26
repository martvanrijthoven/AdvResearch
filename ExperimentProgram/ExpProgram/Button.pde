class Button {
    int x, y,w,h;      // Position button 
    color rectColor;
    color rectHighlight;
    String text;
    
    
    Button (int x, int y, int w, int h, String text){
        this.w = w;
        this.h = h;
        this.x = x-this.w/2;
        this.y = y-this.h/2;
        this.text = text;
        this.rectColor = color(80);
        this.rectHighlight = color(0);
    }
    
    
    void update(){
        if (rectOver()) {
            fill(rectHighlight);
        } 
        else {
            fill(rectColor);
        }
        stroke(255);

        rect(this.x, this.y, this.w, this.h);
        textSize(22);
        fill(255);
        text(this.text,this.x+this.w/2,this.y+this.h/2);
        
    }
    
    boolean rectOver()  {
      if (mouseX >= this.x && mouseX <= this.x+this.w && 
          mouseY >= this.y && mouseY <= this.y+this.h) {
        return true;
      } else {
        return false;
      }
    }
}
    
   