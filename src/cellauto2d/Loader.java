
package cellauto2d;

import java.awt.Dimension;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class Loader {
    
    JFrame fileWind;
    WFileChooser choose;
    
    void initWindow(Cellauto2D ob){
        fileWind = new JFrame();
        //fileWind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileWind.setPreferredSize(new Dimension(400, 400));
        fileWind.setSize(new Dimension(400, 400));
        choose = new WFileChooser(ob, this);
        
        fileWind.add(choose);
        fileWind.setVisible(true);
        
    }
}
