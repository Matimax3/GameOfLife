package cellauto2d;


public class gameOfLife {
    
    int isAlive(int x, int y, int[][] paintBuffer){
        boolean alive = false;
        int count_nh = 0;
        
        count_nh = paintBuffer[x+1][y] + 
                paintBuffer[x+1][y+1] + 
                paintBuffer[x][y+1] + 
                paintBuffer[x-1][y+1] + 
                paintBuffer[x-1][y-1] + 
                paintBuffer[x-1][y] + 
                paintBuffer[x][y-1] + 
                paintBuffer[x+1][y-1];
        
//        if(count_nh < 2 || count_nh > 3)
//            alive = false;
//        else
//        
//        if(count_nh == 2)
//            alive = true;
//        else
//        
//        if(count_nh == 3)
//            alive = true;
        
         
  
        
        //System.out.println(count_nh);
           
        //return alive;
        return count_nh;
    }
    
}
