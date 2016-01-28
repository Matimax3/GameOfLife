
package cellauto2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class WPanel extends JPanel {
    
    public WPanel(){
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    //@Override
    public void paintComponent(Graphics g, Color[][] paintBuffer, int width, int height){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        
        g.fillRect(0, 0, this.getX(), this.getY());
        
        //g.setColor(Color.red);
        
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++){
                if(paintBuffer[i][j] != Color.white){
                    g.setColor(paintBuffer[i][j]);
                    g.fillRect(i, j, 10, 10);
                }
                }

    }
    
}
