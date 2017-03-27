/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 *
 * @author ifletyougo
 */
public class Unit extends JLabel{
    int x,area;
    ImageIcon batdau = new ImageIcon("batdau.png");
    ImageIcon gap = new ImageIcon("gap.png");
    ImageIcon X = new ImageIcon("DO.png");
    ImageIcon O = new ImageIcon("O.png");
    ImageIcon NX = new ImageIcon("NX.png");
    ImageIcon NO = new ImageIcon("NO.png");
    ImageIcon XP = new ImageIcon("XP.png");
    ImageIcon KT = new ImageIcon("DX.png");
    ImageIcon ngang = new ImageIcon("ngang.png");
    ImageIcon doc = new ImageIcon("doc.png");
    ImageIcon trai_tren = new ImageIcon("trai_tren.png");
    ImageIcon phai_tren = new ImageIcon("phai_tren.png");
    ImageIcon phai_duoi = new ImageIcon("phai_duoi.png");  
    ImageIcon trai_duoi = new ImageIcon("trai_duoi.png");
    ImageIcon banchan = new ImageIcon("banchan.png");
    ImageIcon vatcan = new ImageIcon("X.png");
    
    public void setvatcan(){
        x = 1;
        setIcon(vatcan);
    };
    
    public void setX(){
        x = 1;
        setIcon(X);
    };
    
    public void setbanchan(){
        x = 1;
        setIcon(banchan);
    };
    
    public void setngang(){
        setIcon(ngang);
    }
    
    public void setdoc(){
        setIcon(doc);
    }
    
    public void settrai_tren(){
        setIcon(trai_tren);
    }
    
    public void setphai_tren(){
        setIcon(phai_tren);
    }
    
    public void setphai_duoi(){
        setIcon(phai_duoi);
    }
    
    public void settrai_duoi(){
        setIcon(trai_duoi);
    }
    
    public void setKT(){
        setIcon(KT);
    }
    
    public void setO(){
        x = 2;
        setIcon(O);
    }
    
    public void setNX(){
        x = 1;
        setIcon(NX);
    };
    
    public void setNO(){
        x = 2;
        setIcon(NO);
    }
    
    public void freeze(){
        if(x==1) {
            setIcon(NX);
        }
        if(x==2) {
            setIcon(NO);
        }
    }
    
    public void setXP(){
        setIcon(XP);
    }
    
    public void erase(){
        x = 0;
        area = 0;
        setIcon(gap);
    }
    
    public void setgap(){
        setIcon(gap);
    }
    
    public void setbatdau(){
        setIcon(batdau);
    }
    
    public Unit(){
        x = 0;
        area = 0;
        setIcon(gap);
    }
}
