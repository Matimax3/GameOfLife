
package cellauto2d;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Saver {
    
    void save(int [][] Buffer, String filename, int rows, int cols){
        try {
            PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8");
            
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++)
                    writer.print(Buffer[i][j]);
                writer.println();
            }
            
            writer.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
