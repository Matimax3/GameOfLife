
package cellauto2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Cellauto2D implements Runnable{
    
    JFrame mainWind;
    JPanel bottom_pane;
    JPanel center_pane;
    JPanel sl_pane;
    
    JPanel save_toolkit;
    JPanel load_toolkit;
    JPanel sim_toolkit;
    
    JPanel nh_toolkit;
    ButtonGroup nhRadioGroup;
    JRadioButton vonNeumann;
    JRadioButton moore;
    
    JPanel bc_toolkit;
    ButtonGroup bcRadioGroup;
    JRadioButton periodic;
    JRadioButton nonPeriodic;
    JList bc_list;
    
    JButton[][] drawBox;
    
    int[][] paintBuffer;
    int[][] temp;
    int[][] zeros;
    
    JButton start;
    JButton stop;
    JButton reset;
    
    int WIDTH = 800;
    int HEIGHT = 600;
    int cell_size = 10;
    
    int rows = HEIGHT/cell_size-8;
    int cols = WIDTH/cell_size-1;
    
    String[][] test;
    
    boolean simulate = false;
    boolean pressing = false;
    JSlider speed;
    JTabbedPane tab_menu;
    
    JTextField filename;
    JButton save;
    JButton open;
    JFileChooser load;
    
    void mainFrameInit(){
        mainWind = new JFrame("CA 2D");
        bottom_pane = new JPanel();
        center_pane = new JPanel();
        sl_pane = new JPanel();
        tab_menu = new JTabbedPane();
        tab_menu.add("Simulation", bottom_pane);
        tab_menu.add("Presets", sl_pane);
        
        //load = new JFileChooser();
        
        speed = new JSlider(JSlider.HORIZONTAL);
        speed.setMinorTickSpacing(5);
        speed.setMajorTickSpacing(20);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);
        
        filename = new JTextField();
        filename.setPreferredSize(new Dimension(100, 25));
        save = new JButton("Save");
        open = new JButton("Open...");
        
        save_toolkit = new JPanel();
        save_toolkit.setBorder(new TitledBorder("Save/Load"));
        save_toolkit.add(filename);
        save_toolkit.add(save);
        save_toolkit.add(open);
        
        load_toolkit = new JPanel();
        load_toolkit.setBorder(new TitledBorder("Load"));
        
        sim_toolkit = new JPanel();
        sim_toolkit.setBorder(new TitledBorder("Simulation"));
        
        nh_toolkit = new JPanel();
        nh_toolkit.setBorder(new TitledBorder("Neighborhood"));
        
        bc_toolkit = new JPanel();
        bc_toolkit.setBorder(new TitledBorder("Boundary Cond"));
        
        nhRadioGroup = new ButtonGroup();
        vonNeumann = new JRadioButton("Von Neumann");
        moore = new JRadioButton("Moore");
        nhRadioGroup.add(vonNeumann);
        nhRadioGroup.add(moore);
        nh_toolkit.add(vonNeumann);
        nh_toolkit.add(moore);
        
        bcRadioGroup = new ButtonGroup();
        periodic = new JRadioButton("Periodic");
        nonPeriodic = new JRadioButton("Non periodic");
        bcRadioGroup.add(periodic);
        bcRadioGroup.add(nonPeriodic);
        bc_toolkit.add(periodic);
        bc_toolkit.add(nonPeriodic);

        center_pane.setLayout(null);
        
        start = new JButton();
        start.setIcon(new ImageIcon("res/play.png"));
        start.setPreferredSize(new Dimension(30, 30));
        
        stop = new JButton();
        stop.setIcon(new ImageIcon("res/pause.png"));
        stop.setPreferredSize(new Dimension(30, 30));
        
        reset = new JButton();
        reset.setIcon(new ImageIcon("res/stop.png"));
        reset.setPreferredSize(new Dimension(30, 30));
        
        sim_toolkit.add(start);
        sim_toolkit.add(reset);
        sim_toolkit.add(stop);
        sim_toolkit.add(speed);
        
        mainWind.setSize(new Dimension(WIDTH, HEIGHT+65));
        mainWind.setVisible(true);
        
        mainWind.setLayout(new BorderLayout());
        mainWind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainWind.add(tab_menu, BorderLayout.PAGE_END);
        mainWind.add(center_pane, BorderLayout.CENTER);
        mainWind.setResizable(false);
        bottom_pane.add(nh_toolkit);
        bottom_pane.add(bc_toolkit);
        bottom_pane.add(sim_toolkit);
        sl_pane.add(save_toolkit);
        //sl_pane.add(load);
        mainWind.setLocationRelativeTo(null);

    }
    
    void drawBoxInit(){
        drawBox = new JButton[rows][cols];
        test = new String[rows][cols];
        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                drawBox[i][j] = new JButton();
                drawBox[i][j].setPreferredSize(new Dimension(cell_size, cell_size));
                center_pane.add(drawBox[i][j]);
                test[i][j] = i + ":" + j;
                drawBox[i][j].setBounds(j*cell_size, i*cell_size, cell_size, cell_size);
                
            }
        }
    }
    
    void paintBufferInit(){
        paintBuffer = new int[rows][cols];
        temp =  new int[rows][cols];
        zeros = new int[rows][cols];
        
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++){
                paintBuffer[i][j] = 0;
                zeros[i][j] = 0;
            }

        
    }
    
    void printDrawBox(){
        for(int i = 1; i < rows-1; i++)
            for(int j = 1; j < cols-1; j++){
                if(paintBuffer[i][j] == 1)
                    drawBox[i][j].setBackground(Color.green);
                else
                    drawBox[i][j].setBackground(Color.white);
            }
    
    }
    
    void copy(int[][] a, int[][] b){
        for(int i = 1; i < rows-1; i++)
            for(int j = 1; j < cols-1; j++)
                a[i][j] = b[i][j];
    }
    
    void copyBoundaries(){
        for(int j = 1; j < cols-1; j++)
            paintBuffer[0][j] = paintBuffer[rows-2][j];
        
        for(int j = 1; j < cols-1; j++)
            paintBuffer[rows-1][j] = paintBuffer[1][j];
        
        for(int i = 1; i < rows-1; i++)
            paintBuffer[i][0] = paintBuffer[i][cols-2];
        
        for(int i = 1; i < rows-1; i++)
            paintBuffer[i][cols-1] = paintBuffer[i][1];
        
        paintBuffer[0][0] = paintBuffer[rows-2][cols-2];
        paintBuffer[rows-1][cols-1] = paintBuffer[1][1];
        paintBuffer[rows-1][0] = paintBuffer[1][cols-2];
        paintBuffer[0][cols-1] = paintBuffer[rows-2][1];   
        
    }
    
    void disableBounds(){
        for(int j = 1; j < cols-1; j++)
            drawBox[0][j].setEnabled(false);
        
        for(int j = 1; j < cols-1; j++)
            drawBox[rows-1][j].setEnabled(false);
        
        for(int i = 1; i < rows-1; i++)
            drawBox[i][0].setEnabled(false);
        
        for(int i = 1; i < rows-1; i++)
            drawBox[i][cols-1].setEnabled(false);
        
        drawBox[0][0].setEnabled(false);
        drawBox[rows-1][cols-1].setEnabled(false);
        drawBox[rows-1][0].setEnabled(false);
        drawBox[0][cols-1].setEnabled(false); 
    }
    
    int countMoore(int i, int j){
        int sum = 0;

        sum += paintBuffer[i+1][j] + 
            paintBuffer[i+1][j+1] + 
            paintBuffer[i][j+1] + 
            paintBuffer[i-1][j+1] + 
            paintBuffer[i-1][j-1] + 
            paintBuffer[i-1][j] + 
            paintBuffer[i][j-1] + 
            paintBuffer[i+1][j-1];
        
        
        return sum;
    }
    
    int countNeumann(int i, int j){
        int sum;
        
        if(periodic.isSelected())
            copyBoundaries();
   
        sum = paintBuffer[i+1][j] +  
            paintBuffer[i][j+1] +   
            paintBuffer[i-1][j] + 
            paintBuffer[i][j-1];
        
        
        return sum;
    }
    
    void gameOfLife(){
        while(simulate){

            copy(temp, paintBuffer);
            int count = 0;
            
            if(periodic.isSelected())
                copyBoundaries();

            for(int i = 1; i < rows-1; i++)
                for(int j = 1; j < cols-1; j++){

                    count = 0;

                    if(vonNeumann.isSelected())
                        count = countNeumann(i, j);
                    
                    if(moore.isSelected())
                        count = countMoore(i, j);
                    
                    if(count < 2 || count > 3)
                        temp[i][j] = 0;

                    if(count == 2)
                        temp[i][j] = paintBuffer[i][j];

                    if(count == 3)
                        temp[i][j] = 1;

                }


            copy(paintBuffer, temp);
            printDrawBox();
            
            long waitTime = 100 - speed.getValue();
            if(speed.getValue() == 0)
                simulate = false;
            
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cellauto2D.class.getName()).log(Level.SEVERE, null, ex);
            }


            }
    }
    
    void bgThread(){
        Thread t = new Thread(this, "paint");
        t.start();
    }
    
    void ButtonInit(final Cellauto2D ob){
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                simulate = true;
                bgThread();
        
                
            }
        });
        
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(simulate)
                    simulate = false;
                else
                    simulate = true;
            }
        });
        
        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                copy(paintBuffer, zeros);
                copy(temp, zeros);
                simulate = false;
                printDrawBox();
            }
        });
        
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Saver file = new Saver();
                file.save(paintBuffer, filename.getText(), rows, cols);
            }
        });
        
        open.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Loader fileLoad = new Loader();
                fileLoad.initWindow(ob);
                //printDrawBox();
            }
        });
    }
    
    void gridInit(){
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++){
                
                final int k = i;
                final int l = j;
                
                drawBox[i][j].addActionListener(new ActionListener() {

                    
                    
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                if(drawBox[k][l].getBackground() == Color.green){
                    drawBox[k][l].setBackground(Color.white);
                    paintBuffer[k][l] = 0;
                }
                else{
                    drawBox[k][l].setBackground(Color.green);
                    paintBuffer[k][l] = 1;
                }                
                
            }
        });
                
                drawBox[k][l].addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseEntered(MouseEvent e){
                        if(pressing){
                            drawBox[k][l].setBackground(Color.green);
                            paintBuffer[k][l] = 1;
                        }
                    }
                    
                    @Override
                    public void mousePressed(MouseEvent e){
                        pressing = true;
                    }
                    
                    @Override
                    public void mouseReleased(MouseEvent e){
                        pressing = false;
                    }
                });
                
            }
            
        
    }
    
    void printBuffer(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++)
                System.out.print(test[i][j] + " ");
            
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        Cellauto2D main_ob;
        main_ob = new Cellauto2D();
        main_ob.paintBufferInit();
        main_ob.mainFrameInit();
        main_ob.drawBoxInit();
        main_ob.gridInit();
        main_ob.ButtonInit(main_ob);
        main_ob.disableBounds();
        
        
    }

    @Override
    public void run() {
        gameOfLife();
    }
    
}
