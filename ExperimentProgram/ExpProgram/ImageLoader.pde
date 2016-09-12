class ImageLoader {
    int x, y;
    PImage img;
    
    ImageLoader(){
        // get all images from directory 
    }
    
    
    void next(){
        // img = loadImage("moon.jpg"); // Load the original image
    
    }
    
    void display(){
        image(img, this.x, this.y); // Displays the image from point (0,0) 
        img.loadPixels();
    }
}