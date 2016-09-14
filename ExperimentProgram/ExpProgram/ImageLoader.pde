class ImageLoader {
    int x = 40 ;
    int y = 80;
    PImage [] imgs;
    int index;
    ImageLoader(){
        File theDir = new File(dataPath("")+"/images/");
        String[] theList = theDir.list();
        imgs =  new PImage[theList.length];
        for (int i =0; i<theList.length; i++){
            imgs[i] = loadImage(dataPath("")+"/images/"+theList[i]);
        }
        index = 0;
    }
    
    
    void next(){
        // img = loadImage("moon.jpg"); // Load the original image
    
    }
    
    
    void incIndex(){
        if(this.index < this.imgs.length-1){
            this.index++;
        }
            
    }
    
    void display(){
       image(imgs[index], this.x, this.y); // Displays the image from point (0,0) 
       imgs[index].loadPixels();
    }

}