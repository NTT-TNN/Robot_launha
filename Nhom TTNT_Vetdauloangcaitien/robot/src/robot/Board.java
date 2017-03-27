package robot;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends Thread{
    static int size,turn,level,lock,frst;
    static int [][] a, b, delta;
    static int kyluc = 0;
    static boolean start;
    static boolean clear;
    static Unit[][] u;
    static int [][] dd = new int[600][3];
    static boolean kiemtra;
    static JFrame frame;
    static JPanel panel;
    static Timer clock;
    static int[][] giaithuatA;
    static int hx[];
    static int hy[];
    static int dem;
    static boolean pau;
    static boolean pl;
    static boolean luong_dang_chay;
    static Stack duong_di_satck;
    
    static int Robot_Tim_duong_buoc, Robot_Tim_duong_x1, Robot_Tim_duong_y1, Robot_Tim_duong_x2, Robot_Tim_duong_y2;
    static boolean Robot_Tim_duong_NDL_b1 ,Robot_Tim_duong_NDL_b2, Robot_Tim_duong_NDL_b3, Robot_Tim_duong_NDL_b4, Robot_Tim_duong_NDL_b5;
    static Stack Robot_Tim_duong_stack;
    
    static int robot_lau_nha_buoc, robot_lau_nha_x1, robot_lau_nha_y1, robot_lau_nha_x2, robot_lau_nha_y2;
    static boolean robot_lau_nha_NDL_b1, robot_lau_nha_NDL_b2, robot_lau_nha_NDL_b3, robot_lau_nha_NDL_b4;
    static int[][] robot_lau_nha_stack, robot_lau_nha_duong_di;
    
    JMenuBar menuBar = new JMenuBar();
    
    JMenu Robot_Tim_duong = new JMenu("Robot Tìm đường");
    JMenuItem Robot_Tim_duong_b1 = new JMenuItem("B1: Chướng ngại vật");
    JMenuItem Robot_Tim_duong_b2 = new JMenuItem("B2: Điếm xuất phát");
    JMenuItem Robot_Tim_duong_b3 = new JMenuItem("B3: Điểm đích");
    JMenuItem Robot_Tim_duong_b4 = new JMenuItem("B4: Tìm kiếm dijkstra");
    JMenuItem Robot_Tim_duong_b5 = new JMenuItem("B4: Tìm kiếm heuristic");
    
    JMenu robot_lau_nha = new JMenu("Robot Cứu Người");
    JMenuItem robot_lau_nha_b1 = new JMenuItem("B1: Chướng ngại vật");
    JMenuItem robot_lau_nha_b2 = new JMenuItem("B2: Điếm xuất phát");
    JMenuItem robot_lau_nha_b3 = new JMenuItem("B3: Tìm kiếm không lặp lại");
    JMenuItem robot_lau_nha_b4 = new JMenuItem("B3: Tìm kiếm lặp lại ít nhất");
    
    JMenu sze = new JMenu("Size");
    JMenuItem sze1 = new JMenuItem("12 x 12");
    JMenuItem sze2 = new JMenuItem("16 x 16");
    JMenuItem sze3 = new JMenuItem("20 x 20");
    JMenuItem sze4 = new JMenuItem("24 x 24"); 
    
    JMenu restart = new JMenu("Restart");
    JMenuItem restart1 = new JMenuItem("Restart");
    
    JMenu sleep = new JMenu("Sleep");
    JMenuItem pause = new JMenuItem("Pause");
    JMenuItem play = new JMenuItem("Play");

    JPanel bar = new JPanel();
    
    public Board(int sz){
        size = sz;
        turn = 0;
        frst = 1;
        lock = 0;
        
        luong_dang_chay = false;
        start = false;
        pau = false;
        pl = false;
        clear = false;
        hx = new int[4];
        hy = new int[4];
        duong_di_satck = new Stack();
        hx[0] = 0; hx[1] = 1; hx[2] = 0; hx[3] = -1;
        hy[0] = 1; hy[1] = 0; hy[2] = -1; hy[3] = 0;
        
        Robot_Tim_duong_buoc = 0;
        Robot_Tim_duong_x1 = -1;
        Robot_Tim_duong_y1 = -1;
        Robot_Tim_duong_x2 = -1;
        Robot_Tim_duong_y2 = -1;
        Robot_Tim_duong_NDL_b1 = false;
        Robot_Tim_duong_NDL_b2 = false;
        Robot_Tim_duong_NDL_b3 = false;
        Robot_Tim_duong_NDL_b4 = false;
        Robot_Tim_duong_NDL_b5 = false;
        Robot_Tim_duong_stack = new Stack();
        giaithuatA = new int[600][2];
        
        robot_lau_nha_NDL_b1 = false;
        robot_lau_nha_NDL_b2 = false;
        robot_lau_nha_NDL_b3 = false;
        robot_lau_nha_NDL_b4 = false;
        robot_lau_nha_buoc = 0;
        robot_lau_nha_x1 = -1;
        robot_lau_nha_y1 = -1;
        robot_lau_nha_stack = new int[600][2];
        robot_lau_nha_duong_di = new int[800][2];
        
        u = new Unit[24][24];
        a = new int[24][24];
        b = new int[24][24];
        delta = new int[24][24];
        for(int i=0;i<24;i++)
            for(int j=0;j<24;j++)
            {
                a[i][j] = 0;
                b[i][j] = 0;
                delta[i][j] = 4;
            }
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        frame = new JFrame("ROBOT");
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBounds(400,50,size*26+6,size*26+84);
        frame.add(panel);
        frame.setLayout(null);
        panel.setBounds(-(627-size*26)/2,-(622-size*26)/2,628,658);
        
        menuBar.add(Robot_Tim_duong);
        Robot_Tim_duong.add(Robot_Tim_duong_b1);
        Robot_Tim_duong.add(Robot_Tim_duong_b2);
        Robot_Tim_duong.add(Robot_Tim_duong_b3);
        Robot_Tim_duong.add(Robot_Tim_duong_b4);
        Robot_Tim_duong.add(Robot_Tim_duong_b5);
        
        menuBar.add(robot_lau_nha);
        robot_lau_nha.add(robot_lau_nha_b1);
        robot_lau_nha.add(robot_lau_nha_b2);
        robot_lau_nha.add(robot_lau_nha_b3);
        robot_lau_nha.add(robot_lau_nha_b4);
        
        menuBar.add(sze);
        sze.add(sze1);
        sze.add(sze2);
        sze.add(sze3);
        sze.add(sze4);
        
        menuBar.add(restart);
        restart.add(restart1);
        
        menuBar.add(sleep);
        sleep.add(pause);
        sleep.add(play);
        
        frame.setJMenuBar(menuBar);
        
        panel.add(bar);
        bar.setBounds((627-size*26)/2, (622-size*26)/2,628,30);
        bar.setBackground(Color.WHITE);
        
        for(int i=0;i<24;i++){
            for(int j=0;j<24;j++){
                final int I,J;
                I = i;
                J = j;
                u[j][i] = new Unit();
                panel.add(u[j][i]);
                u[j][i].setBounds(i*26,30+j*26,26,26);
                u[j][i].addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent me) {
                       
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                        if(a[J - (24 - size)/2][I- (24 - size)/2]==0)
                        {
                            if(Robot_Tim_duong_buoc==1){
                               a[J - (24 - size)/2][I- (24 - size)/2] = 1;
                               u[J][I].setX();
                            }
                            else
                            if(Robot_Tim_duong_buoc ==2 && Robot_Tim_duong_NDL_b2 == false){
                               Robot_Tim_duong_x1 = J - (24 - size)/2;
                               Robot_Tim_duong_y1 = I - (24 - size)/2;
                               Robot_Tim_duong_NDL_b2 = true;
                               u[J][I].setXP();
                             }
                            else
                            if(Robot_Tim_duong_buoc==3 && Robot_Tim_duong_NDL_b3 == false){
                               Robot_Tim_duong_x2 = J - (24 - size)/2;
                               Robot_Tim_duong_y2 = I - (24 - size)/2;
                               Robot_Tim_duong_NDL_b3 = true;
                               u[J][I].setKT();
                            }
                            if(robot_lau_nha_buoc==1)
                            {
                                a[J - (24 - size)/2][I- (24 - size)/2] = 1;
                                u[J][I].setX();
                            }
                            else
                            if(robot_lau_nha_buoc == 2 && robot_lau_nha_NDL_b2==false){
                            }
                            System.out.print("A[ "+(J- (24 - size)/2)+" , "+(I- (24 - size)/2)+" ] = "+size+"\n");
                            if(pau)
                            {
                                u[J][I].setvatcan();
                                a[J - (24 - size)/2][I- (24 - size)/2] = 1;
                            }
                        }   
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                        
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {
                        
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                        
                    }
                });
            }
        }
        
        restart1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Robot_Tim_duong_buoc = 0;
                Robot_Tim_duong_NDL_b2 = false;
                Robot_Tim_duong_NDL_b3 = false;
                robot_lau_nha_buoc = 0;
                robot_lau_nha_NDL_b1 = false;
                robot_lau_nha_NDL_b2 = false;
                robot_lau_nha_NDL_b3 = false;
                pl = false;
                pau = false;
                clear = true;
                for(int i=0;i<24;i++)
                    for(int j=0;j<24;j++)
                    {
                        u[i][j].setgap();
                        a[i][j] = 0;
                    }
            }
        });
        
        sze1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                resize(12);
            }
            
        });
        
        sze2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                resize(16);
            }
            
        });
        
        sze3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                resize(20);
            }
            
        });
        
        sze4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                resize(24);
            }
            
        });
        
        pause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println(" dang la pause");
                pau = true;
                pl = false;
            }
        });
        
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                pl = true;
                pau = false;
                A1();
                Thread th = new MyRunnable3();
                th.start();
            }
        });
        
        Robot_Tim_duong_b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(Robot_Tim_duong_buoc==0)
                {
                    clear = false;
                    Robot_Tim_duong_buoc = 1;
                    robot_lau_nha_buoc = 0;
                }
            }
        });
        
        Robot_Tim_duong_b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(Robot_Tim_duong_buoc ==0)
                {
                    JOptionPane.showMessageDialog(null, "Bạn chưa nhâp chướng ngại vật nên không thể nhập điểm xuất phát!\n",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
                }
                else
                if(Robot_Tim_duong_buoc==1)
                {
                    clear = false;
                    Robot_Tim_duong_buoc = 2;
                }
                else
                if(Robot_Tim_duong_buoc==2){
                  JOptionPane.showMessageDialog(null, "Ban da nhap diem xuat phat tu truoc \n Diem ban da nhap la ( "+
                          Robot_Tim_duong_x1+" , "+ Robot_Tim_duong_y1+" )",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        Robot_Tim_duong_b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(Robot_Tim_duong_buoc ==0 || Robot_Tim_duong_buoc ==1)
                    JOptionPane.showMessageDialog(null, "Ban chua nhap du du lieu cac buoc truoc do, vui long nhap lai!",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
                else
                if(Robot_Tim_duong_buoc ==2)
                     Robot_Tim_duong_buoc = 3;
                else
                if(Robot_Tim_duong_buoc ==3)
                    JOptionPane.showMessageDialog(null, "Ban da nhap diem Dich tu truoc \n Diem ban da nhap la ( "+
                          Robot_Tim_duong_x2+" , "+ Robot_Tim_duong_y2+" )",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        Robot_Tim_duong_b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(Robot_Tim_duong_buoc <3)
                  JOptionPane.showMessageDialog(null, "Ban chua nhap du du lieu cac buoc truoc do, vui long nhap lai!",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
                else
                {
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            u[i][j].setgap();
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            if(a[i][j]==1)
                                u[i][j].setX();
                    u[Robot_Tim_duong_x1][Robot_Tim_duong_y1].setXP();
                    u[Robot_Tim_duong_x2][Robot_Tim_duong_y2].setKT();
                    Robot_Tim_duong_buoc = 4;
                    try {
                        dijkstra_1();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        Robot_Tim_duong_b5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(Robot_Tim_duong_buoc <3)
                  JOptionPane.showMessageDialog(null, "Ban chua nhap du du lieu cac buoc truoc do, vui long nhap lai!",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
                else
                {
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            u[i][j].setgap();
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            if(a[i][j]==1)
                                u[i][j].setX();
                    u[Robot_Tim_duong_x1][Robot_Tim_duong_y1].setXP();
                    u[Robot_Tim_duong_x2][Robot_Tim_duong_y2].setKT();
                    Robot_Tim_duong_buoc = 5;
                    A();
                }
            }
        });
        
        robot_lau_nha_b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(robot_lau_nha_buoc==0)
                {
                    clear = false;
                    Robot_Tim_duong_buoc = 0;
                    robot_lau_nha_buoc = 1;
                }
            }
        });
        
        robot_lau_nha_b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(robot_lau_nha_buoc ==0)
                {
                    JOptionPane.showMessageDialog(null, "Bạn chưa nhâp chướng ngại vật nên không thể nhập điểm xuất phát!\n",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
                }
                else
                if(robot_lau_nha_buoc==1)
                {
                    robot_lau_nha_buoc = 2;
                }
                else
                if(robot_lau_nha_buoc==2){
                  JOptionPane.showMessageDialog(null, "Ban da nhap diem xuat phat tu truoc \n Diem ban da nhap la ( "+
                          robot_lau_nha_x1+" , "+ robot_lau_nha_y1+" )",
                  "Thông báo sự cố dữ liệu", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        robot_lau_nha_b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(robot_lau_nha_buoc <1)
                  JOptionPane.showMessageDialog(null, "Ban chua nhap du du lieu cac buoc truoc do, vui long nhap lai!",
                  "Thông báo sự cố dữ = "+robot_lau_nha_buoc, JOptionPane.WARNING_MESSAGE);
                else
                {
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            u[i][j].setgap();
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            if(a[i][j]==1)
                                u[i][j].setX();
                    robot_lau_nha_x1 = 0;
                    robot_lau_nha_y1 = 0;
                    robot_lau_nha_x2 = size - 1;
                    robot_lau_nha_y2 = size - 1;
                    u[robot_lau_nha_x1][robot_lau_nha_y1].setXP();
                    u[robot_lau_nha_x2][robot_lau_nha_y2].setKT();
                    robot_lau_nha_buoc = 3;
                    A1();
                }
            }
        });
        
        robot_lau_nha_b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(robot_lau_nha_buoc <1)
                  JOptionPane.showMessageDialog(null, "Ban chua nhap du du lieu cac buoc truoc do, vui long nhap lai!",
                  "Thông báo sự cố dữ = "+robot_lau_nha_buoc, JOptionPane.WARNING_MESSAGE);
                else
                {
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            u[i][j].setgap();
                    for(int i=0;i<24;i++)
                        for(int j=0;j<24;j++)
                            if(a[i][j]==1)
                                u[i][j].setX();
                    robot_lau_nha_x1 = 0;
                    robot_lau_nha_y1 = 0;
                    robot_lau_nha_x2 = size - 1;
                    robot_lau_nha_y2 = size - 1;
                    u[robot_lau_nha_x1][robot_lau_nha_y1].setXP();
                    u[robot_lau_nha_x2][robot_lau_nha_y2].setKT();
                    robot_lau_nha_buoc = 3;
                    duong_di_dai_nhat_lap_lai();
                }
            }
        });
    }
    
    public void resize(int sz){
        size = sz;
        if(sz>24) {
            return;
        }
        frame.setSize(sz*26+6,sz*26+84);
        panel.setBounds(-(627-sz*26)/2,-(622-sz*26)/2,628,658);
        bar.setBounds((627-size*26)/2, (622-size*26)/2,628,30);
        frame.setVisible(true);
    }
    
    public void reset(){
        for(int i=0;i<24;i++)
            for(int j=0;j<24;j++)
            {
                a[i][j] = 0;
                u[i][j] = new Unit();
                panel.add(u[i][j]);
            }
    }
    
    public void dijkstra_1() throws InterruptedException, IOException{
        int dem = 0, d = 0;
        for(int i=0;i<24;i++)
        {
            for(int j=0;j<24;j++)
                System.out.print(" "+a[i][j]);
            System.out.println("");
        }
        Robot_Tim_duong_stack = new Stack();
        int x1,y1,x2,y2;
        for(int i=0;i<24;i++)
            for(int j=0;j<24;j++)
            {
                b[i][j] = 1000;
            }
        x1 = Robot_Tim_duong_x1;
        y1 = Robot_Tim_duong_y1;
        a[x1][y1] = 1;
        b[x1][y1] = 0;
        Robot_Tim_duong_stack.push(x1,y1);
        while(Robot_Tim_duong_stack.check(dem)){
            x1 = Robot_Tim_duong_stack.a[dem][0];
            y1 = Robot_Tim_duong_stack.a[dem][1];
            if(b[Robot_Tim_duong_x2][Robot_Tim_duong_y2]!=1000)
                break;
            for(int i=0;i<4;i++){
                x2 = x1 + hx[i];
                y2 = y1 + hy[i];
                if(check(x2,y2) && (b[x1][y1]+1<b[x2][y2]) && a[x2][y2]!=1)
                {
                    delta[x2][y2] = i;
                    Robot_Tim_duong_stack.push(x2, y2);
                    b[x2][y2] = b[x1][y1]+1;
                }
            }
            dem++;
        }
        
        if(b[Robot_Tim_duong_x2][Robot_Tim_duong_y2] != 1000)
        {
            dem = b[Robot_Tim_duong_x2][Robot_Tim_duong_y2];
            x1 = Robot_Tim_duong_x2;
            y1 = Robot_Tim_duong_y2;
            dd[dem][0] = x1;
            dd[dem][1] = y1;
            for(int i=0;i<dem;i++){
                x2 = hx[delta[x1][y1]];
                y2 = hy[delta[x1][y1]];
                x1 = x1 - x2;
                y1 = y1 - y2;
                dd[dem-i-1][0] = x1;
                dd[dem-i-1][1] = y1;
            }
            dd[0][0] = Robot_Tim_duong_x1;
            dd[0][1] = Robot_Tim_duong_y1;
            Thread th = new Board.MyRunnable2();
            th.start();
        }
            
        else
            System.out.print("khong co duong di = "+b[Robot_Tim_duong_x2][Robot_Tim_duong_y2]);
    }
    
   class MyRunnable2 extends Thread
    {
      int dem;
      int i = 0;
      public void run()
      {
          dem = b[Robot_Tim_duong_x2][Robot_Tim_duong_y2];
          for(int i=1;i<dem;i++)
            {
                try
                {
                    Thread.sleep( 100 );
                    u[dd[i+1][0]][dd[i+1][1]].setbatdau();
                    if(dd[i-1][0]==dd[i][0] && dd[i][0]==dd[i+1][0] && abs(dd[i][1],dd[i-1][1])==1 && abs(dd[i][1],dd[i+1][1])==1){
                       u[dd[i][0]][dd[i][1]].setngang();
                    }
                    else
                    if(abs(dd[i-1][0],dd[i][0])==1 && abs(dd[i][0],dd[i+1][0])==1 && dd[i-1][1]==dd[i][1] && dd[i][1]==dd[i+1][1])
                    {
                       u[dd[i][0]][dd[i][1]].setdoc();
                    }
                    else
                    if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i+1][1]==dd[i][1]) || (
                        dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0]&& dd[i][1]-dd[i+1][1]==1))
                     {
                       u[dd[i][0]][dd[i][1]].settrai_tren();
                     }
                     else
                     if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i+1][0]-dd[i][0]==1 && dd[i][1]==dd[i+1][1])||(
                        dd[i-1][0]-dd[i][0]==1 && dd[i-1][1]==dd[i][1] && dd[i][0]==dd[i+1][0] && dd[i][1]-dd[i+1][1]==1))
                     {
                       u[dd[i][0]][dd[i][1]].settrai_duoi();
                     }
                     else
                     if((dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0] && dd[i+1][1]-dd[i][1]==1)||(
                        dd[i][0]==dd[i-1][0] && dd[i-1][1]-dd[i][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i][1]==dd[i+1][1]))
                     {
                      u[dd[i][0]][dd[i][1]].setphai_tren();
                     }
                     else
                    u[dd[i][0]][dd[i][1]].setphai_duoi();
                 }
                 catch (InterruptedException ie)
                 {
                    System.err.println ("ERROR");
                 }
            }
      
      }
    }
    
    public static int abs(int x,int y){
        if(x>=y) return(x-y);
        else
            return(y-x);
    }
    
    public void A(){
       int x1, y1, gt, x2, y2, min, vt;
       kiemtra = false;
       giaithuatA[0][0] = Robot_Tim_duong_x1;
       giaithuatA[0][1] = Robot_Tim_duong_y1;
       a[Robot_Tim_duong_x1][Robot_Tim_duong_y1] = 1;
       for(int i=0;i<24;i++)
           for(int j=0;j<24;j++)
           {
               b[i][j] = a[i][j];
           }
       back_tracking(Robot_Tim_duong_x1, Robot_Tim_duong_y1, 1);
   }
    
    public void output(int k){
        dem  =k;
        for(int i=0;i<k;i++)
        {
            dd[i][0] = giaithuatA[i][0];
            dd[i][1] = giaithuatA[i][1];
        }
        kiemtra = true;
        Thread th = new MyRunnable();
        th.start();
    }
    
    class MyRunnable extends Thread{
        public void run(){
            luong_dang_chay = true;
           // while(1>0)
            //{
            for(int i=1;i<dem-1;i++){ 
                try
                {
                Thread.sleep( 75 );
                u[dd[i+1][0]][dd[i+1][1]].setbatdau();
                if(dd[i-1][0]==dd[i][0] && dd[i][0]==dd[i+1][0] && abs(dd[i][1],dd[i-1][1])==1 && abs(dd[i][1],dd[i+1][1])==1){
                    u[dd[i][0]][dd[i][1]].setngang();
                }
                else
                if(abs(dd[i-1][0],dd[i][0])==1 && abs(dd[i][0],dd[i+1][0])==1 && dd[i-1][1]==dd[i][1] && dd[i][1]==dd[i+1][1])
                {
                    u[dd[i][0]][dd[i][1]].setdoc();
                }
                else
                if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i+1][1]==dd[i][1]) || (
                        dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0]&& dd[i][1]-dd[i+1][1]==1))
                {
                    u[dd[i][0]][dd[i][1]].settrai_tren();
                }
                else
                if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i+1][0]-dd[i][0]==1 && dd[i][1]==dd[i+1][1])||(
                        dd[i-1][0]-dd[i][0]==1 && dd[i-1][1]==dd[i][1] && dd[i][0]==dd[i+1][0] && dd[i][1]-dd[i+1][1]==1))
                {
                    u[dd[i][0]][dd[i][1]].settrai_duoi();
                }
                else
                if((dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0] && dd[i+1][1]-dd[i][1]==1)||(
                        dd[i][0]==dd[i-1][0] && dd[i-1][1]-dd[i][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i][1]==dd[i+1][1]))
                {
                    u[dd[i][0]][dd[i][1]].setphai_tren();
                }
                else
                    u[dd[i][0]][dd[i][1]].setphai_duoi();
                }
                catch (InterruptedException ie)
                {
                   System.err.println ("ERROR");
                }
            }
//            for(int i=1;i<dem-1;i++)
//                u[dd[i][0]][dd[i][1]].setgap();
         // }
        }
    }
    
    public void back_tracking(int x,int y, int k){
        int x1, y1, gt, x2, y2, vt = -1, min = 10000;
        if(kiemtra==false)
        {
            x1 = x;
            y1 = y;
            if(x1==Robot_Tim_duong_x2 && y1 == Robot_Tim_duong_y2){
              output(k);
            }
            else
            {
            x1 = x;
            y1 = y;
            for(int i=0;i<4;i++){
                x2 = x1 + hx[i];
                y2 = y1 + hy[i];
                if(check(x2, y2) && b[x2][y2]!=1){
                    if((x2 - Robot_Tim_duong_x2)*(x2 - Robot_Tim_duong_x2)+(y2 - Robot_Tim_duong_y2)*(y2 - Robot_Tim_duong_y2)<min)
                    {
                        min = (x2 - Robot_Tim_duong_x2)*(x2 - Robot_Tim_duong_x2)+(y2 - Robot_Tim_duong_y2)*(y2 - Robot_Tim_duong_y2);
                        vt = i;
                    }
                }
             }
            if(vt!=-1){
                giaithuatA[k][0] = x1+hx[vt];
                giaithuatA[k][1] = y1+hy[vt];
                b[x1+hx[vt]][y1+hy[vt]] = 1;
                back_tracking(x1+hx[vt], y1+hy[vt], k+1);
                back_tracking(x1, y1, k);
                giaithuatA[k][0] = 0;
                giaithuatA[k][1] = 0;
                b[x1+hx[vt]][y1+hy[vt]] = 0;
            }
          }
       }
    }
    
    public boolean check(int x,int y){
        if(x<size && x>=0 && y<size && y>=0 && a[x][y]==0)
            return(true);
        else
            return(false);
    } 
    
    public void xaclap_kyluc(int k){
        if(k>kyluc){
            kyluc = k;
            for(int i=0;i<k;i++)
            {
                robot_lau_nha_stack[i][0] = robot_lau_nha_duong_di[i][0];
                robot_lau_nha_stack[i][1] = robot_lau_nha_duong_di[i][1];
            }
        }
    }
    
    public void search(){
        kyluc = 0;
        a[robot_lau_nha_x1][robot_lau_nha_y1] = 1;
        for(int i=0;i<24;i++)
            for(int j=0;j<24;j++)
                b[i][j] = a[i][j];
        robot_lau_nha_duong_di[0][0] = robot_lau_nha_x1;
        robot_lau_nha_duong_di[0][1] = robot_lau_nha_y1;
        dequy(robot_lau_nha_x1, robot_lau_nha_y1, 1);
        for(int i=1;i<kyluc;i++)
            u[robot_lau_nha_stack[i][0]][robot_lau_nha_stack[i][1]].setO();
    }
    
    public void dequy(int x,int y,int k){
        int x1,y1;
        if(kt(x,y)==false)
            xaclap_kyluc(k);
        else
        {
            for(int i=0;i<4;i++)
            {
                x1 = x+hx[i];
                y1 = y+hy[i];
                if(check(x1,y1) && b[x1][y1]!=1){
                    b[x1][y1] = 1;
                    robot_lau_nha_duong_di[k][0] = x1;
                    robot_lau_nha_duong_di[k][1] = y1;
                    dequy(x1, y1, k+1);
                    b[x1][y1] = 0;
                    robot_lau_nha_duong_di[k][0] = 0;
                    robot_lau_nha_duong_di[k][1] = 0;
                }
            }
        }
    }
    
    public boolean kt(int x,int y){
        int x1,y1;
        for(int i=0;i<4;i++){
            x1 = x+hx[i];
            y1 = y+hy[i];
            if(check(x1, y1))
            {
                if(b[x1][y1]!=1) return(true);
            }
        }
        return(false);
    }
    
    public void A1(){
       int x1, y1, gt, x2, y2, min, vt;
       kiemtra = false;
       giaithuatA[0][0] = robot_lau_nha_x1;
       giaithuatA[0][1] = robot_lau_nha_y1;
       a[robot_lau_nha_x1][robot_lau_nha_y1] = 1;
       for(int i=0;i<24;i++)
           for(int j=0;j<24;j++)
           {
               b[i][j] = a[i][j];
           }
       back_tracking1(robot_lau_nha_x1, robot_lau_nha_y1, 1);
   }
    
    public void output1(int k){
        for(int i=0;i<k;i++)
        {
            dd[i][0] = giaithuatA[i][0];
            dd[i][1] = giaithuatA[i][1];
        }
        dem = k;
        kiemtra = true;
        Thread thr = new MyRunnable3();
        thr.start();
    }
    
    class MyRunnable3 extends Thread{
        public void run(){
            luong_dang_chay = true;
           // while(1>0)
            //{
            for(int i=1;i<dem-1;i++){
                if(pau) {
                    robot_lau_nha_x1 = dd[i-1][0];
                    robot_lau_nha_y1 = dd[i-1][1];
                    System.out.println(" x = "+robot_lau_nha_x1+" y = "+robot_lau_nha_y1);
                    break;
                }
                else
                if(clear){
                    for(int j=0;j<24;j++)
                        for(int k=0;k<24;k++)
                        {
                            u[j][k].setgap();
                            a[j][k] = 0;
                        }
                    break;
                } 
                try
                {
                    a[dd[i-1][0]][dd[i-1][1]] = 1;
                   Thread.sleep( 75 );
                   u[dd[i+1][0]][dd[i+1][1]].setbatdau();
                if(dd[i-1][0]==dd[i][0] && dd[i][0]==dd[i+1][0] && abs(dd[i][1],dd[i-1][1])==1 && abs(dd[i][1],dd[i+1][1])==1){
                    u[dd[i][0]][dd[i][1]].setngang();
                }
                else
                if(abs(dd[i-1][0],dd[i][0])==1 && abs(dd[i][0],dd[i+1][0])==1 && dd[i-1][1]==dd[i][1] && dd[i][1]==dd[i+1][1])
                {
                    u[dd[i][0]][dd[i][1]].setdoc();
                }
                else
                if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i+1][1]==dd[i][1]) || (
                        dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0]&& dd[i][1]-dd[i+1][1]==1))
                {
                    u[dd[i][0]][dd[i][1]].settrai_tren();
                }
                else
                if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i+1][0]-dd[i][0]==1 && dd[i][1]==dd[i+1][1])||(
                        dd[i-1][0]-dd[i][0]==1 && dd[i-1][1]==dd[i][1] && dd[i][0]==dd[i+1][0] && dd[i][1]-dd[i+1][1]==1))
                {
                    u[dd[i][0]][dd[i][1]].settrai_duoi();
                }
                else
                if((dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0] && dd[i+1][1]-dd[i][1]==1)||(
                        dd[i][0]==dd[i-1][0] && dd[i-1][1]-dd[i][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i][1]==dd[i+1][1]))
                {
                    u[dd[i][0]][dd[i][1]].setphai_tren();
                }
                else
                    u[dd[i][0]][dd[i][1]].setphai_duoi();
                }
                catch (InterruptedException ie)
                {
                   System.err.println ("ERROR");
                }
            }
//            for(int i=1;i<dem-1;i++)
//                u[dd[i][0]][dd[i][1]].setgap();
         // }
        }
    }
    
    public void back_tracking1(int x,int y, int k){
        int x1, y1, gt, x2, y2, vt = -1, min = 10000;
        if(kiemtra){
            
        }
        else
        {
            x1 = x;
            y1 = y;
            if(x1==robot_lau_nha_x2 && y1 == robot_lau_nha_y2){
              output1(k);
            }
            else
            {
            x1 = x;
            y1 = y;
            for(int i=0;i<4;i++){
                x2 = x1 + hx[i];
                y2 = y1 + hy[i];
                if(check(x2, y2) && b[x2][y2]!=1){
                    if((x2)*(x2)+(y2)*(y2)<min)
                    {
                        min = (x2)*(x2)+(y2)*(y2);
                        vt = i;
                    }
                }
             }
            if(vt!=-1){
                giaithuatA[k][0] = x1+hx[vt];
                giaithuatA[k][1] = y1+hy[vt];
                b[x1+hx[vt]][y1+hy[vt]] = 1;
                back_tracking1(x1+hx[vt], y1+hy[vt], k+1);
                back_tracking1(x1, y1, k);
                giaithuatA[k][0] = 0;
                giaithuatA[k][1] = 0;
                b[x1+hx[vt]][y1+hy[vt]] = 0;
            }
          }
        }    
    }
    
    public void A2(){
       int x1, y1, gt, x2, y2, min, vt;
       kiemtra = false;
       giaithuatA[0][0] = robot_lau_nha_x1;
       giaithuatA[0][1] = robot_lau_nha_y1;
       a[robot_lau_nha_x1][robot_lau_nha_y1] = 1;
       for(int i=0;i<24;i++)
           for(int j=0;j<24;j++)
           {
               b[i][j] = a[i][j];
           }
       back_tracking2(robot_lau_nha_x1, robot_lau_nha_y1, 1);
   }
    
    public void output2(int k){
        int [][] dd;
        dd = new int[600][2];
        for(int i=0;i<k;i++)
        {
            dd[i][0] = giaithuatA[i][0];
            dd[i][1] = giaithuatA[i][1];
        }
        kiemtra = true;
        for(int i=1;i<k-1;i++)
        {
            if(dd[i-1][0]==dd[i][0] && dd[i][0]==dd[i+1][0] && abs(dd[i][1],dd[i-1][1])==1 && abs(dd[i][1],dd[i+1][1])==1){
                    u[dd[i][0]][dd[i][1]].setngang();
                }
                else
                if(abs(dd[i-1][0],dd[i][0])==1 && abs(dd[i][0],dd[i+1][0])==1 && dd[i-1][1]==dd[i][1] && dd[i][1]==dd[i+1][1])
                {
                    u[dd[i][0]][dd[i][1]].setdoc();
                }
                else
                if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i+1][1]==dd[i][1]) || (
                        dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0]&& dd[i][1]-dd[i+1][1]==1))
                {
                    u[dd[i][0]][dd[i][1]].settrai_tren();
                }
                else
                if((dd[i][0]==dd[i-1][0] && dd[i][1]-dd[i-1][1]==1 && dd[i+1][0]-dd[i][0]==1 && dd[i][1]==dd[i+1][1])||(
                        dd[i-1][0]-dd[i][0]==1 && dd[i-1][1]==dd[i][1] && dd[i][0]==dd[i+1][0] && dd[i][1]-dd[i+1][1]==1))
                {
                    u[dd[i][0]][dd[i][1]].settrai_duoi();
                }
                else
                if((dd[i][0]-dd[i-1][0]==1 && dd[i][1]==dd[i-1][1] && dd[i][0]==dd[i+1][0] && dd[i+1][1]-dd[i][1]==1)||(
                        dd[i][0]==dd[i-1][0] && dd[i-1][1]-dd[i][1]==1 && dd[i][0]-dd[i+1][0]==1 && dd[i][1]==dd[i+1][1]))
                {
                    u[dd[i][0]][dd[i][1]].setphai_tren();
                }
                else
                    u[dd[i][0]][dd[i][1]].setphai_duoi();
        }
        timkiem(k);
    }
    
    public void timkiem(int k){
        for(int i=0;i<24;i++)
            for(int j=0;j<24;j++)
                b[i][j] = a[i][j];
        for(int i=0;i<k;i++)
            b[giaithuatA[i][0]][giaithuatA[i][1]] = 2;
        for(int i=0;i<k;i++)
        {
            serach(giaithuatA[i][0],giaithuatA[i][1]);
        }
    }
    
    public void serach(int x,int y){
        int x1,y1;
        for(int i=0;i<4;i++){
            x1 = x+hx[i];
            y1 = y+hy[i];
            if(check(x1, y1) && b[x1][y1]==0){
                b[x1][y1] = 3;
                u[x1][y1].setbanchan();
                serach(x1,y1);
            }
        }
    }
    
    public void back_tracking2(int x,int y, int k){
        int x1, y1, gt, x2, y2, vt = -1, min = 10000;
        if(kiemtra){
            
        }
        else
        {
            x1 = x;
            y1 = y;
            if(x1==robot_lau_nha_x2 && y1 == robot_lau_nha_y2){
              output2(k);
            }
            else
            {
            x1 = x;
            y1 = y;
            for(int i=0;i<4;i++){
                x2 = x1 + hx[i];
                y2 = y1 + hy[i];
                if(check(x2, y2) && b[x2][y2]!=1){
                    if((x2)*(x2)+(y2)*(y2)<min)
                    {
                        min = (x2)*(x2)+(y2)*(y2);
                        vt = i;
                    }
                }
             }
            if(vt!=-1){
                giaithuatA[k][0] = x1+hx[vt];
                giaithuatA[k][1] = y1+hy[vt];
                b[x1+hx[vt]][y1+hy[vt]] = 1;
                back_tracking2(x1+hx[vt], y1+hy[vt], k+1);
                back_tracking2(x1, y1, k);
                giaithuatA[k][0] = 0;
                giaithuatA[k][1] = 0;
                b[x1+hx[vt]][y1+hy[vt]] = 0;
            }
          }
        }    
    }
    
    public void duong_di_dai_nhat_lap_lai(){
        int x1, y1, gt, x2, y2, min, vt;
       kiemtra = false;
       giaithuatA[0][0] = robot_lau_nha_x1;
       giaithuatA[0][1] = robot_lau_nha_y1;
       a[robot_lau_nha_x1][robot_lau_nha_y1] = 1;
        System.out.println("mang a la");
       
       for(int i=0;i<24;i++)
           for(int j=0;j<24;j++)
           {
               b[i][j] = a[i][j];
           }
       back_tracking_3(robot_lau_nha_x1, robot_lau_nha_y1, 1);
    }
    
    public void back_tracking_3(int x,int y, int k){
        int x1, y1, gt, x2, y2, vt = -1, min = 10000;
        if(kiemtra){
            
        }
        else
        {
            x1 = x;
            y1 = y;
            if(x1==robot_lau_nha_x2 && y1 == robot_lau_nha_y2){
              output3(k);
            }
            else
            {
            x1 = x;
            y1 = y;
            for(int i=0;i<4;i++){
                x2 = x1 + hx[i];
                y2 = y1 + hy[i];
                if(check(x2, y2) && b[x2][y2]!=1){
                    if((x2)*(x2)+(y2)*(y2)<min)
                    {
                        min = (x2)*(x2)+(y2)*(y2);
                        vt = i;
                    }
                }
             }
            if(vt!=-1){
                giaithuatA[k][0] = x1+hx[vt];
                giaithuatA[k][1] = y1+hy[vt];
                b[x1+hx[vt]][y1+hy[vt]] = 1;
                back_tracking_3(x1+hx[vt], y1+hy[vt], k+1);
                back_tracking_3(x1, y1, k);
                giaithuatA[k][0] = 0;
                giaithuatA[k][1] = 0;
                b[x1+hx[vt]][y1+hy[vt]] = 0;
            }
          }
        }        
    }
    
    public void output3(int k){
        for(int i=0;i<k;i++)
        {
            dd[i][0] = giaithuatA[i][0];
            dd[i][1] = giaithuatA[i][1];
        }
        duong_di_satck.empty();
        for(int i=0;i<24;i++)
            for(int j=0;j<24;j++)
                b[i][j] = a[i][j];
        for(int i=0;i<k;i++)
            b[dd[i][0]][dd[i][1]] = 1;
        for(int i=0;i<k;i++)
        {
            duong_di_satck.push(dd[i][0], dd[i][1]);
            vetcan(dd[i][0], dd[i][1]);
        }
        dem = k;
        kiemtra = true;
        Thread thr = new MyRunnable4();
        thr.start();
    }
    
    class MyRunnable4 extends Thread{
        public void run(){
            luong_dang_chay = true;
            dem = duong_di_satck.top;
            for(int i=1;i<dem-1;i++){
                if(pau) {
                    robot_lau_nha_x1 = duong_di_satck.a[i-1][0];
                    robot_lau_nha_y1 = duong_di_satck.a[i-1][1];
                    System.out.println(" x = "+robot_lau_nha_x1+" y = "+robot_lau_nha_y1);
                    break;
                }
                try
                {
                    a[duong_di_satck.a[i-1][0]][duong_di_satck.a[i-1][1]] = 1;
                   Thread.sleep(75);
                   u[duong_di_satck.a[i+1][0]][duong_di_satck.a[i+1][1]].setbatdau();
                   u[duong_di_satck.a[i][0]][duong_di_satck.a[i][1]].setbanchan();
                }
                catch (InterruptedException ie)
                {
                   System.err.println ("ERROR");
                }
            }
        }
    }
    
    public void vetcan(int x,int y){
        duong_di_satck.push(x,y);
        int x1,y1, x2, y2;
        x1 = x;
        y1 = y;
        for(int i=0;i<4;i++){
            x2 = x1+hx[i];
            y2 = y1+hy[i];
            if(check(x2, y2) && b[x2][y2]==0){
                b[x2][y2] = 1;
                duong_di_satck.push(x2,y2);
                vetcan(x2, y2);
                duong_di_satck.push(x2, y2);
            }
        }
    }
}
