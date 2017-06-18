package Projekt_v3;

import javax.swing.*;
import javax.xml.ws.handler.MessageContext;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.*;

public class KarciankaClient extends JFrame {
	
	
	private ArrayList<ClientImpl.Karty> kartyGracza, ka1, ka2;
	private ArrayList<Integer> op1, op2;
	private LinkedList<String> listaNazw = new LinkedList<String>();
	
	private int[] listaRzedowGr, silaOpw1Rzedzie;
	private int licznik = 0;
	String[] nameList;
	Map<String, ImageIcon> imageMap;
	
	private JScrollPane scroll;
    //GUI
    private JButton polacz, rozlacz;
    private JPanel panel;
    private JTextField host, wiadomosc;
    private JTextArea komunikaty;   
    
    private KarciankaField plansza;
    private JList list;
    private JButton endGame, passButton, elo, wyrzucKarte, pokoj1, pokoj2, pokoj3, pokoj4;
    private JPanel panelPomocniczy, panelInfoAkcji, panelStartowy;
    private JLabel opponentPoints, yourPoints, opponentWin, yourWin, runda, naglowek, liczbaGraczy, komunik;
    
    //Klient
    private String nazwaSerwera = "localhost";
    private Klient watekKlienta;
    private KarciankaClient instancjaKlienta;
    private Karcianka serwer;
    private ClientImpl klient;

    public KarciankaClient() {
        super("Klient");
        
        instancjaKlienta = this;

       
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        panel = new JPanel(new FlowLayout());
        komunikaty = new JTextArea();
        komunikaty.setLineWrap(true);
        komunikaty.setEditable(false);

        wiadomosc = new JTextField();

        host = new JTextField(nazwaSerwera, 12);
        polacz = new JButton("Polacz");
        rozlacz = new JButton("Rozlacz");
        rozlacz.setEnabled(false);

        scroll = new JScrollPane();
        scroll.setPreferredSize(new Dimension(250, 400));
       
        ObslugaZdarzen obsluga = new ObslugaZdarzen();

        polacz.addActionListener(obsluga);
        rozlacz.addActionListener(obsluga);

        wiadomosc.addKeyListener(obsluga);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                rozlacz.doClick();
                setVisible(false);
                System.exit(0);
            }
        });
        elo = new JButton("Start");
        elo.addActionListener(obsluga);
        elo.setEnabled(false);
        wyrzucKarte = new JButton("Zagraj Karte");
        wyrzucKarte.addActionListener(obsluga);
        wyrzucKarte.setEnabled(false);
      
      
        endGame = new JButton("Poddaj sie");
        passButton = new JButton("Pass");
       
        
        endGame.setEnabled(false);
        passButton.setEnabled(false);
       
        
        endGame.addActionListener(obsluga);
        passButton.addActionListener(obsluga);
    
        			
        			
        naglowek = new JLabel("Informacje o rozgrywce   ");
        naglowek.setFont(new Font("Times New Roman", Font.BOLD, 17));
        naglowek.setForeground(new Color(215,226,67));
        naglowek.setVerticalAlignment(SwingConstants.CENTER);
        
        opponentPoints = new JLabel("Punkty przeciwnika: 0");
        opponentPoints.setFont(new Font("Times New Roman", Font.BOLD, 17));
        opponentPoints.setForeground(new Color(255,153,153));
        opponentPoints.setVerticalAlignment(SwingConstants.BOTTOM);
        
        yourPoints = new JLabel("Twoje punkty: 0");
        yourPoints.setFont(new Font("Times New Roman", Font.BOLD, 17));
        yourPoints.setForeground(Color.GREEN);
        yourPoints.setVerticalAlignment(SwingConstants.TOP);
      
        runda = new JLabel("Runda: 1", SwingConstants.CENTER);
        runda.setFont(new Font("Times New Roman", Font.BOLD, 17));
        runda.setForeground(Color.WHITE);
      
        opponentWin = new JLabel("Wygrane przeciwnika: 0");
        opponentWin.setFont(new Font("Times New Roman", Font.BOLD, 17));
        opponentWin.setForeground(new Color(255,153,153));
        opponentWin.setVerticalAlignment(SwingConstants.BOTTOM);
        
        yourWin = new JLabel("Twoje wygrane: 0");
        yourWin.setFont(new Font("Times New Roman", Font.BOLD, 17));
        yourWin.setForeground(Color.GREEN);
        yourWin.setVerticalAlignment(SwingConstants.TOP);
        
        panelInfoAkcji = new JPanel();
        panelInfoAkcji.setLayout(new BoxLayout(panelInfoAkcji, BoxLayout.PAGE_AXIS));
      
        
        Dimension minSize = new Dimension(5, 150);
        Dimension prefSize = new Dimension(5, 150);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 20);
        panelInfoAkcji.setBackground(Color.GRAY);
        panelInfoAkcji.setBorder(BorderFactory.createMatteBorder(2,1,1,2, new Color(0,0,0)));
        panelInfoAkcji.add(new Box.Filler(minSize, prefSize, maxSize));
        panelInfoAkcji.add(naglowek);
        maxSize = new Dimension(Short.MAX_VALUE, 45);
        panelInfoAkcji.add(new Box.Filler(minSize, prefSize, maxSize));
        panelInfoAkcji.add(runda);
        panelInfoAkcji.add(opponentWin);
        panelInfoAkcji.add(opponentWin);
        panelInfoAkcji.add(yourWin);
        maxSize = new Dimension(Short.MAX_VALUE, 208);
        panelInfoAkcji.add(new Box.Filler(minSize, prefSize, maxSize));
        panelInfoAkcji.add(opponentPoints);
        panelInfoAkcji.add(yourPoints);
        
        panelPomocniczy = new JPanel(new FlowLayout());
       
        panelPomocniczy.add(endGame);
        panelPomocniczy.add(passButton);
        panelPomocniczy.add(wyrzucKarte);
        
        panel.add(new JLabel("Serwer RMI: "));
        panel.add(host);
        panel.add(polacz);
        panel.add(rozlacz);
        
        panelStartowy = new JPanel(new BorderLayout());
        pokoj1 = new JButton("0/2 graczy");
        pokoj2 = new JButton("0/2 graczy");
        pokoj3 = new JButton("0/2 graczy");
        pokoj4 = new JButton("0/2 graczy");
        
        pokoj1.addActionListener(obsluga);
        pokoj2.addActionListener(obsluga);
        pokoj3.addActionListener(obsluga);
        pokoj4.addActionListener(obsluga);
        
        pokoj1.setEnabled(false);
        pokoj2.setEnabled(false);
        pokoj3.setEnabled(false);
        pokoj4.setEnabled(false);
        
        JPanel pom = new JPanel(new GridLayout(5, 2));
        JPanel pom1 = new JPanel(new FlowLayout());
        liczbaGraczy = new JLabel("Liczba graczy: ");
        komunik = new JLabel("");
        pom1.add(liczbaGraczy);
        panelStartowy.add(panel, BorderLayout.NORTH);
        panelStartowy.add(pom, BorderLayout.CENTER);
        pom.add(komunik);
        pom.add(liczbaGraczy);
        pom.add(new JLabel("Pokoj nr 1:"));
        pom.add(pokoj1);
        pom.add(new JLabel("Pokoj nr 2:"));
        pom.add(pokoj2);
        pom.add(new JLabel("Pokoj nr 3:"));
        pom.add(pokoj3);
        pom.add(new JLabel("Pokoj nr 4:"));
        pom.add(pokoj4);
        setSize(450, 200);
        
        plansza = new KarciankaField();
        
        
        add(panelStartowy, BorderLayout.CENTER);
        
        setVisible(true);

    }
    
    public class ListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 24);

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }

    private Map<String, ImageIcon> createImageMap(String[] nameList, int[] rzad) {
        Map<String, ImageIcon> map = new HashMap<>();
        String sciezka ="";
        for(int i = 0; i < nameList.length; i++){
        	String s = nameList[i];
        	if(rzad[i] == 1){
        		sciezka = "./images/miecz1.jpg";
        	}else{
        		
        		sciezka = "./images/luk.png";
        	}
        	map.put(s, new ImageIcon(sciezka));
        } 
        return map;
    }
    

    private class ObslugaZdarzen extends KeyAdapter implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == polacz) {
                plansza.setInfo("£acze z: " + nazwaSerwera + "...");
                repaint();
                polacz.setEnabled(false);
                rozlacz.setEnabled(true);
                host.setEnabled(false);
                watekKlienta = new Klient();
                watekKlienta.start();
                komunik.setText("Polaczono z serwerem");
                
            }
            if (e.getSource() == rozlacz) {
               
                try {
                    serwer.opusc(klient);
                } catch (Exception ex) {
                    System.out.println("Blad: " + ex);
                }
                rozlacz.setEnabled(false);
                polacz.setEnabled(true);
                host.setEnabled(true);
                pokoj1.setEnabled(false);
                pokoj2.setEnabled(false);
                pokoj3.setEnabled(false);
                pokoj4.setEnabled(false);
                
                pokoj1.setText("0/2 graczy");
                pokoj2.setText("0/2 graczy");
                pokoj3.setText("0/2 graczy");
                pokoj4.setText("0/2 graczy");
                liczbaGraczy.setText("Liczba graczy: 0");
                komunik.setText("Rozlaczono z serwerem");
            }
            if(e.getSource() == pokoj1){
            	try {
					serwer.rozpocznijGre(klient, 1);
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
            	
                
            }
            if(e.getSource() == pokoj2){
            	try {
					serwer.rozpocznijGre(klient, 2);
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
            	
                 
            }
            if(e.getSource() == pokoj3){
            	try {
					serwer.rozpocznijGre(klient, 3);
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
            	
                 
            }
            if(e.getSource() == pokoj4){
            	try {
					serwer.rozpocznijGre(klient, 4);
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
            }
          
            if(e.getSource() == passButton){
            	wyswietlInfo("Spasowales");
            	try {
					serwer.passuj(klient);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
            	
            	repaint();
            }
            
            if(e.getSource() == wyrzucKarte){
            	
            	
            	if(list.isSelectionEmpty()){
            		if(kartyGracza.size() > 0){
            		JOptionPane.showMessageDialog(null, "Musisz wybrc karte z listy!", "Brak Karty", JOptionPane.YES_OPTION);
            		}else {
            			wyswietlInfo("Spasowales z powodu braku kart");
            			try {
        					serwer.passuj(klient);
        				} catch (RemoteException e2) {
        					
        					e2.printStackTrace();
        				}
            			
            		}
            	}else{
	            	try {
	            	    serwer.wyrzucKarte(klient, list.getSelectedIndex());
						wyrzucKarte.setEnabled(false);
						
						pobierzKarty();
						setPunkty();
						
					} catch (RemoteException e1) {
						
						e1.printStackTrace();
					} 
	            	
            }
        	
            }
            if(e.getSource() == endGame){
            	
            	plansza.setInfo("Poddales sie!");
            	
            	try {
            		serwer.poddajSie(klient);
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
            	tworzPanelStartowy();
            	repaint();
            }
        }
        
    }

    private class Klient extends Thread {

        public void run() {
            try {
                Registry rejestr = LocateRegistry.getRegistry(host.getText());
                serwer = (Karcianka) rejestr.lookup("RMIKarcianka");
                plansza.setInfo("Polaczylem sie z serwerem.");
                
                
                repaint();
                
                String nick = "";
                
                do {
                	nick = JOptionPane.showInputDialog(null, "Podaj nick: ");
                } while (serwer.sprawdzNicka(nick));
                
                repaint();
                klient = new ClientImpl(instancjaKlienta, nick);
                serwer.dolacz(klient);
                
                pokoj1.setEnabled(true);
                pokoj2.setEnabled(true);
                pokoj3.setEnabled(true);
                pokoj4.setEnabled(true);

            } catch (Exception e) {
                System.out.println("Blad polaczenia: " + e);
            }
        }
    }

    public void wyswietlKomunikat(String tekst) {
        komunikaty.append(tekst + "\n");
        komunikaty.setCaretPosition(komunikaty.getDocument().getLength());
    }
    public void wyswietlInfo(String info){
    	plansza.setInfo(info);
    	repaint();
    }
    public void odblokujRzutKarty(){
    	wyrzucKarte.setEnabled(true);
    }
    public void ustawMozliwoscRuchu(boolean ruch){
    	wyrzucKarte.setEnabled(ruch);
    	passButton.setEnabled(ruch);
    	endGame.setEnabled(ruch);
    	
    }
    public void pobierzKarty(){
    	
    	
        try {
			kartyGracza = klient.pobierzKartyGracza();
			ka1 = klient.pobierzKarty1Rzedu();
			ka2 = klient.pobierzKarty2Rzedu();
			
			
			nameList = new String[kartyGracza.size()];
			listaRzedowGr = new int[kartyGracza.size()];
	        for(int i = 0; i < kartyGracza.size(); i++){
	        	listaNazw.add(kartyGracza.get(i).pobierzNazwe());
	        	listaRzedowGr[i] = kartyGracza.get(i).pobierzRzad();
	        	nameList[i] = kartyGracza.get(i).pobierzSile() + " " + kartyGracza.get(i).pobierzNazwe();
	        }
	        
	        
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        imageMap = createImageMap(nameList, listaRzedowGr);
        list = new JList(nameList);
        list.setCellRenderer(new ListRenderer());
        remove(scroll);
        scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(250, 400));
        
        add(scroll, BorderLayout.WEST);
        
        plansza.zagraj1(ka1);
        plansza.zagraj2(ka2);
       // setPunktyGr();
        repaint();
        
        
    }
    
    public void enablePS()
    {
	passButton.setEnabled(true);
    }
    
    public void setPunkty()
    {
	try {
	    yourPoints.setText("Twoje punkty: " +klient.zsumujSile());
	    opponentPoints.setText("Punkty przeciwnika: " +plansza.getPunktyOp());
	} catch (RemoteException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	repaint();
	
    }
    
    public void setYourWins()
    {
	
	int liczba=0;
	try {
	    liczba = klient.getWygraneRundy();
	  //  System.out.println("Klient wygraÅ‚: "+klient.getWygraneRundy());
	} catch (RemoteException e) {
	    
	    e.printStackTrace();
	}
	yourWin.setText("Twoje wygrane: "+liczba);
    }
    
    public void setOpponentsWins(int opWins)
    {
	int liczba = opWins;
	
	if(liczba<0)
	    opponentWin.setText("Wygrane przeciwnika: 0");
	else
	    opponentWin.setText("Wygrane przeciwnika: "+liczba);
    }
    
    public void rysujKartyPrzeciwnika(){
    	try {
    		
    		op1 = serwer.pobierz1RzadPrzeciwnika(klient);
			op2 = serwer.pobierz2RzadPrzeciwnika(klient);
			plansza.ustawLiczbeKartPrzeciwnika(serwer.pobierzLiczbeKartPrzeciwnika(klient));
    		
		} catch (RemoteException e) {			
			e.printStackTrace();
		}
		
    	plansza.ustawOpRz1(op1);
    	plansza.ustawOpRz2(op2);    	
    	setPunkty();
    	repaint();
    }
    
    public void enableWK()
    {
	wyrzucKarte.setEnabled(true);
	repaint();
    }
    
    public void setRunda()
    {
	try {
	    runda.setText("Runda: "+klient.getRunda());
	} catch (RemoteException e) {	    
	    e.printStackTrace();
	}
    }
    
    public void wylaczALL()
    {
	passButton.setEnabled(false);
	endGame.setEnabled(false);
	wyrzucKarte.setEnabled(false);
    }
    
    public void czekajNaZakonczenie(){
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
    	tworzPanelStartowy();
    	
    }
    
    public void tworzPlanszeGry(){
    	setSize(1200, 700);
    	remove(panelStartowy);
    	add(panelInfoAkcji, BorderLayout.EAST);
        
        add(plansza, BorderLayout.CENTER);                 
        add(scroll, BorderLayout.WEST);
        add(panelPomocniczy, BorderLayout.SOUTH);
    }
    
    public void tworzPanelStartowy(){
    	remove(plansza);
    	remove(panelInfoAkcji);
    	remove(scroll);
    	remove(panelPomocniczy);
    	add(panelStartowy);
    	setSize(450, 200);
    }
    
    public void wyswietlDialog(String wiadomosc, String temat){    	
    	komunik.setText(wiadomosc);    	
    	komunik.setForeground(new Color(255, 0 ,0));
    }
    public void wyswietlLiczbeGraczy(int liczba){
    	liczbaGraczy.setText("Liczba graczy: " + liczba);
    	
    }
    public void aktualizujZajetosc(int pok1, int pok2, int pok3, int pok4){
    	pokoj1.setText(pok1 + "/2 graczy");
    	pokoj2.setText(pok2 + "/2 graczy");
    	pokoj3.setText(pok3 + "/2 graczy");
    	pokoj4.setText(pok4 + "/2 graczy");
    }

    public static void main(String[] args) {
        new KarciankaClient();
    }
	
}
