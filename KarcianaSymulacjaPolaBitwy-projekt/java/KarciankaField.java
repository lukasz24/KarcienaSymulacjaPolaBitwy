package Projekt_v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

public class KarciankaField extends JPanel {
	
	private JPanel rzadOp1, rzadOp2, rzadGr1, rzadGr2, kartyOp, kartyGr, powiadomienia;
	
	private JPanel nowy;
	private JLabel pow;
	
	private Image obRz1Op, obRz2Op, obRz1Gr, obRz2Gr, card;
	
	private JSeparator sep1, sep2, sep3, sep4, sep5, sep6 ;

	private int kartyPrzeciwnika = 0;
	
	private ArrayList<Integer> rzOp1 = new ArrayList<>();
	private ArrayList<Integer> rzOp2 = new ArrayList<>();
	private ArrayList<ClientImpl.Karty> rzGr1 = new ArrayList<>();
	private ArrayList<ClientImpl.Karty> rzGr2 = new ArrayList<>();
	
	
	
	
	private Graphics g;
	private KarciankaServer ks;
	
	
	public KarciankaField(){	
	    
	    
	    setLayout(new BorderLayout());
	        kartyOp = new JPanel()
	        
	        	{
	            
	            @Override
	            protected void paintComponent(Graphics g) {
    		Toolkit toolkit = Toolkit.getDefaultToolkit();
	            	card = toolkit.getImage("./images/card.png");
	            	super.paintComponent(g);
	            	for(int i = 0; i < kartyPrzeciwnika; i++){ 
	            	g.drawImage(card, 15+77*i, 10, 60,100,this);
	            }
    		
	        	}
	            };
	    
	            kartyGr = new JPanel();
		        rzadOp1 = new JPanel(){
		        @Override
		            protected void paintComponent(Graphics g) {
		            	Toolkit toolkit = Toolkit.getDefaultToolkit();
		            	obRz1Op = toolkit.getImage("./images/miecze2.png");
		            	super.paintComponent(g);
		            	g.drawImage(obRz1Op, 0, 0, getWidth(), getHeight(), this);
		            	
		            	
		            	 for(int i = 0; i < rzOp1.size(); i++){ 
				            	
				            	g.setColor(new Color(0,0,0));
				            	g.fillRect (15+75*i, 10, 60,100);
				            	g.setColor(new Color(255,255,255));
				            	
				            	g.drawString(rzOp1.get(i)+"", 42+75*i, (110)/2);
			                }
		            	
		          }
		        };
		        rzadOp2 = new JPanel(){
		        @Override
		            protected void paintComponent(Graphics g) {
		            	Toolkit toolkit = Toolkit.getDefaultToolkit();
		            	obRz2Op = toolkit.getImage("./images/luk2.png");
		            	super.paintComponent(g);
		                g.drawImage(obRz2Op, 0, 0, getWidth(), getHeight(), this);
		                for(int i = 0; i < rzOp2.size(); i++){
			            	
			            	g.setColor(new Color(0,0,0));
			            	g.fillRect (15+75*i, 10, 60,100);
			            	g.setColor(new Color(255,255,255));
			            	
			            	g.drawString(rzOp2.get(i)+"", 42+75*i, (110)/2);
			          
			            	}
		               
		          }
		        };
		        powiadomienia = new JPanel();
		        rzadGr1 = new JPanel(){
		        @Override
		            protected void paintComponent(Graphics g) {
		            	Toolkit toolkit = Toolkit.getDefaultToolkit();
		            	obRz1Gr = toolkit.getImage("./images/miecze1.png");
		            	super.paintComponent(g);
		            	g.drawImage(obRz1Gr, 0, 0, getWidth(), getHeight(), this);
		            	for(int i = 0; i < rzGr1.size(); i++){ 
			            	
			            	g.setColor(new Color(0,0,0));
			            	g.fillRect (15+75*i, 10, 60,100);
			            	g.setColor(new Color(255,255,255));
			            	
			            	
			            	
					    g.drawString(rzGr1.get(i).pobierzSile()+"", 42+75*i, (110)/2);
					
		                }
		          }   
		        };
		        rzadGr2 = new JPanel(){
		            @Override
		            protected void paintComponent(Graphics g) {
		        	Toolkit toolkit = Toolkit.getDefaultToolkit();
		        	obRz2Gr = toolkit.getImage("./images/luk1.png");
		        	super.paintComponent(g);
		                g.drawImage(obRz2Gr, 0, 0, getWidth(), getHeight(), this);
		                for(int i = 0; i < rzGr2.size(); i++){ 
			            	
			            	g.setColor(new Color(0,0,0));
			            	g.fillRect (15+75*i, 10, 60,100);
			            	g.setColor(new Color(255,255,255));
			            	
			            	
					    g.drawString(rzGr2.get(i).pobierzSile()+"", 42+75*i, (110)/2);
					
		                }
		          }
		        };
		     
		        
		        
		        kartyGr.setBackground(new Color(0, 0, 255, 60));
		      
		        kartyOp.setBackground(new Color(149,129,129));
		        powiadomienia.setBackground(new Color(0, 0, 0, 150));
		        
		        kartyGr.setBorder(BorderFactory.createMatteBorder(1,0,1,0, new Color(50, 50, 240)));
		        rzadOp1.setBorder(BorderFactory.createMatteBorder(1,0,5,0, new Color(0,0,0)));
		        rzadOp2.setBorder(BorderFactory.createMatteBorder(1,0,1,0, new Color(0,0,0)));
		        rzadGr1.setBorder(BorderFactory.createMatteBorder(5,0,1,0, new Color(0,0,0)));
		        rzadGr2.setBorder(BorderFactory.createMatteBorder(1,0,1,0, new Color(0,0,0)));
		        kartyOp.setBorder(BorderFactory.createMatteBorder(1,0,1,0, new Color(240, 50, 50)));
		        
		        pow = new JLabel("Witaj Swiecie!");
		        pow.setBorder(BorderFactory.createMatteBorder(1,0,1,0, new Color(240, 50, 50)));
		        pow.setForeground(new Color(240, 240, 90));
		        pow.setFont(new Font("Times New Roman", Font.BOLD, 20));
		        pow.setAlignmentY(20);;
		        powiadomienia.add(pow);
		        
		        JLabel kg = new JLabel("karty GRACZA");
		        
		        JLabel ro1 = new JLabel("Rzad bliskiego zasiegu Przeciwnika");
		        ro1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		        ro1.setForeground(Color.RED);
		        JLabel ro2 = new JLabel("Rzad dalekiego zasiegu Przeciwnika");
		        ro2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		        ro2.setForeground(Color.RED);
		        JLabel rg1 = new JLabel("Rzad bliskiego zasiegu Gracza");
		        rg1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		        rg1.setForeground(Color.GREEN);
		        JLabel rg2 = new JLabel("Rzad dalekiego zasiegu Gracza");
		        rg2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		        rg2.setForeground(Color.GREEN);
		        JLabel ko = new JLabel("Karty, ktore ma do dyspozycji Przeciwnik");
		        ko.setFont(new Font("Times New Roman", Font.BOLD, 18));
		        ko.setForeground(Color.RED);
		        ko.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(240, 50, 50)));
		        ko.setBackground(new Color(255,255,255));
		        ko.setOpaque(true);
		        kartyOp.add(ko);
		         kartyGr.add(kg);
		        
		         rzadOp1.add(ro1);
		         rzadOp2.add(ro2);
		         rzadGr1.add(rg1);
		         rzadGr2.add(rg2);
		         
		        
		        sep1 = new JSeparator();
		        
		        sep1.setForeground(new Color(120, 120, 120));
		        
		       
		        
		        nowy = new JPanel();
		        nowy.setLayout(new GridLayout(5, 1));
		        nowy.add(kartyOp);
		        nowy.add(rzadOp2);
		        nowy.add(rzadOp1);
		        
		        nowy.add(rzadGr1);
		        nowy.add(rzadGr2);
		        add(powiadomienia, BorderLayout.NORTH);
		        nowy.setBackground(new Color(0, 0, 0, 0));
		        add(nowy, BorderLayout.CENTER);
		     
	}

public void paintComponent(Graphics g) {
	
    	        
    }
private void drawCard(){
	Rectangle prostokat = new Rectangle(10, 10, 150, 100);
	setBackground(Color.red);
	Graphics g = getGraphics();
	g.drawRect(0, 0, 10, 10);
    
}
public void setInfo(String text){
	pow.setText(text);
}

public void zagraj1(ArrayList<ClientImpl.Karty> karty)
{
    rzGr1 = karty;
}
public void zagraj2(ArrayList<ClientImpl.Karty> karty)
{
    rzGr2 = karty;
}
public void ustawOpRz1(ArrayList<Integer> sila){
	rzOp1 = sila;
	
}
public void ustawOpRz2(ArrayList<Integer> sila){
	rzOp2 = sila;
	
}



public int getPunktyGr()
{
    int punkt=0, suma=0;
    for(int i =0; i< rzGr1.size(); i++)
    {
	punkt = rzGr1.get(i).pobierzSile();
	suma+=punkt;
    }
    for(int i =0; i< rzGr2.size(); i++)
    {
	punkt = rzGr2.get(i).pobierzSile();
	suma+=punkt;
    }
    return suma;
}

public int getPunktyOp()
{
    int punkt=0, suma=0;
    for(int i =0; i< rzOp1.size(); i++)
    {
	punkt = rzOp1.get(i);
	suma+=punkt;
    }
    for(int i =0; i< rzOp2.size(); i++)
    {
	punkt = rzOp2.get(i);
	suma+=punkt;
    }
    return suma;
}

public void czysc()
{
	rzGr1.clear();
	rzGr2.clear();
	rzOp1.clear();
	rzOp2.clear();
	
}
public void ustawLiczbeKartPrzeciwnika(int liczba){
	kartyPrzeciwnika = liczba;
}

}
