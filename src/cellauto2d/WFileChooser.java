
package cellauto2d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;


public class WFileChooser extends JFileChooser{
    
    Cellauto2D ob;
    Loader loadWind;

    public WFileChooser(Cellauto2D ob, Loader loadWind) {
        this.ob = ob;
        this.loadWind = loadWind;
        
    }

    @Override
    public void approveSelection() {
        super.approveSelection(); //To change body of generated methods, choose Tools | Templates.
        
        File open;
        open = this.getSelectedFile();
        
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(open));
            String line;
            
            try {
                    for(int i = 0; i < ob.rows; i++){
                        line = br.readLine().trim();
                        
                        
                        for(int j = 0; j < ob.cols; j++)
                            ob.paintBuffer[i][j] = Character.getNumericValue(line.charAt(j));;
                            
                    }
                
                ob.printDrawBox();

                
            } catch (IOException ex) {
                Logger.getLogger(WFileChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WFileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadWind.fileWind.dispose();
    
    }

}
