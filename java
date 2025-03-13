import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.awt.*;
import java.util.Random;

class ImageExample extends JFrame {
    JButton b;
    Board board;
    KeyAdapter key;

    int fieldx = 20;
    int fieldy = 19;
    public ImageExample() {

        initUI();
    }

    private void initUI() {
        board = new Board();

        add(board, BorderLayout.CENTER);

        b= new JButton("reset");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.initBoard();
            }
        } );
        //37 лево
        //38 верх
        //39 право
        //40 низ
        b.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                if(e.getKeyCode() == 38 && board.vy != 1){ //верх
                    board.vy = -1;
                    board.vx = 0;
                }
                if(e.getKeyCode() == 40 && board.vy != -1){ //низ
                    board.vy = 1;
                    board.vx = 0;

                }
                if(e.getKeyCode() == 39 && board.vx != -1){ //право
                    board.vx = 1;
                    board.vy = 0;
                }
                if(e.getKeyCode() == 37 && board.vx != 1){ //лево
                    board.vx = -1;
                    board.vy = 0;
                }
            }
        });



        add(b, BorderLayout.LINE_START);
        pack();
        int size = 30;
        setTitle("Bardejov");
        setSize(fieldx*size+150,fieldy*size+150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public static void main(String[] args) {
        ImageExample i = new ImageExample();
        //ArrayList<Integer> xarray = new ArrayList<>();
        //
        //xarray.add(1);
        //xarray.add(0);
        //	//*System.out.println(123);
        //
        //for(int i =0; i< xarray.size(); i++){
        //	System.out.println(xarray.get(i));
        //}

    }


}

class Board extends JPanel implements ActionListener{
    private Timer timer;
    private Image bardejov;
    int x; /// позиция змейки по Х
    int y;
    int vx;
    int vy;
    int size = 30;
    int fieldx = 20;
    int fieldy = 19;


    ArrayList<Integer> xarray ;
    ArrayList<Integer> yarray ;
	
	int applex;
	int appley;

    public Board() {
        initBoard();
    }

    public  void initBoard() {
        xarray = new ArrayList<>();
        yarray = new ArrayList<>();

        xarray.add(2);
        xarray.add(1);
        yarray.add(1);
        yarray.add(1);

        vx = 1;
        vy = 0;

		applex = 5;
        appley = 5;
		
        loadImage();

        int w = bardejov.getWidth(this);
        int h =  bardejov.getHeight(this);
        setPreferredSize(new Dimension(w, h));

        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        x = xarray.get(0);
        y = yarray.get(0);

        boolean up = ( y==1 && vy==-1);
        boolean left = ( x==1 && vx==-1);
        boolean down = ( y==fieldy && vy==1);
        boolean right = ( x==fieldx && vx==1);

        for (int i = 1; i < xarray.size(); i++)
        {
            if(xarray.get(i) == x && yarray.get(i) == y)
            {
                end_game();
                return;
            }
        }

        if (up || down || left || right){//проверка границы
            end_game();
        }
        else{
            if (x==applex && y==appley) //рост
            {
                xarray.add(0, xarray.get(0) + vx) ;
                yarray.add(0, yarray.get(0) + vy) ;
            }
            else{

                for(int i = xarray.size()-1; i >= 1; i--){//смещение тело
                    xarray.set(  i, xarray.get((int)i-1)   );
                    yarray.set(  i, yarray.get(i-1)   );
                }

                xarray.set(0, xarray.get(0) + vx) ; //смещение голову
                yarray.set(0, yarray.get(0) + vy) ;
            }
        }

        repaint();
        //}
    }


    private void loadImage() {
        ImageIcon ii = new ImageIcon("C:\\Users\\student\\Desktop\\jdk-20.0.2\\1.png");
        bardejov = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        removeAll();
        updateUI();
        g.drawRect(30,30,30*fieldx,30*fieldy);//поле
        g.drawRect(applex*30,appley*30,30,30);


        //g.drawImage(bardejov, xarray.get(0)*size,yarray.get(0)*size, null);

        for (int i = xarray.size()-1; i >= 0; i--){
            if (i==0){
                g.setColor(Color.decode("#990000"));
            }
            else{
                g.setColor(Color.decode("#000000"));
            }
            g.fillRect(xarray.get(i)*size,yarray.get(i)*size,size,size);
			g.setColor(Color.decode("#FFFFFF"));
            g.drawRect(xarray.get(i)*size,yarray.get(i)*size,size,size);
        }
    }

    public void end_game()
    {
        int vx = 0;
        int vy = 0;
        System.out.println(xarray.get(0));
        System.out.println(yarray.get(0));
        timer.stop();
    }

}

