package Projekt_v3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class KarciankaServer extends JFrame {
	
	//GUI
    private JButton uruchom, zatrzymaj;
    private JPanel panel;
    private JTextField port;
    private JTextArea komunikaty;
    
    private JList<String> zalogowani;
    private DefaultListModel<String> listaZalogowanych;
	//Server
	private int numerPortu = 1099;
    KarciankaServer instancjaSerwera;
    
    public KarciankaServer() {
        super("Serwer Karcianki");

        instancjaSerwera = this;
              
        
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new JPanel(new FlowLayout());
        komunikaty = new JTextArea();
        komunikaty.setLineWrap(true);
        komunikaty.setEditable(false);

        port = new JTextField((new Integer(numerPortu)).toString(), 8);
        uruchom = new JButton("Uruchom");
        zatrzymaj = new JButton("Zatrzymaj");
        zatrzymaj.setEnabled(false);
        
        listaZalogowanych = new DefaultListModel<String>();
        zalogowani = new JList<String>(listaZalogowanych);
        zalogowani.setFixedCellWidth(120);
        
        ObslugaZdarzen obsluga = new ObslugaZdarzen();

        uruchom.addActionListener(obsluga);
        zatrzymaj.addActionListener(obsluga);

        panel.add(new JLabel("Port RMI: "));
        panel.add(port);
        panel.add(uruchom);
        panel.add(zatrzymaj);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(zalogowani), BorderLayout.EAST);
        add(new JScrollPane(komunikaty), BorderLayout.CENTER);

        setVisible(true);
    }
    
    private class ObslugaZdarzen implements ActionListener {

        private Serwer srw;

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Uruchom")) {
                srw = new Serwer();
                srw.start();
                uruchom.setEnabled(false);
                zatrzymaj.setEnabled(true);
                port.setEnabled(false);
                repaint();
            }
            if (e.getActionCommand().equals("Zatrzymaj")) {
                srw.kill();
                zatrzymaj.setEnabled(false);
                uruchom.setEnabled(true);
                port.setEnabled(true);
                repaint();
            }
        }
    }
    
    private class Serwer extends Thread {

        Registry rejestr;

        public void kill() {
            try {
                rejestr.unbind("RMIKarcianka");
                wyswietlKomunikat("Serwer zostal‚ wyrejestrowany.");
                // brak fizycznego roz³¹czenia serwera (dalej funkcjonuje dla wczesniej po³¹czonych u¿ytkowników, ale owych juz nie doda)
            } catch (Exception e) {
                wyswietlKomunikat("Nie udalo sie wyrejestrowac serwera.");
            }
        }

        public void run() {

            try {
                rejestr = LocateRegistry.createRegistry(new Integer(port.getText()));
                wyswietlKomunikat("Utworzy³em nowy rejestr na porcie: " + port.getText());
            } catch (Exception e) {
                wyswietlKomunikat("Nie powiodlo sie utworzenie rejestru...\nProba skorzystania z istniej¹cego...");
            }
            if (rejestr == null) {
                try {
                    rejestr = LocateRegistry.getRegistry();
                } catch (Exception e) {
                    wyswietlKomunikat("Brak uruchomionego rejestru.");
                }
            }
            try {
                KarciankaImpl serwer = new KarciankaImpl(instancjaSerwera);
                rejestr.rebind("RMIKarcianka", serwer);
                wyswietlKomunikat("Serwer zostal‚ poprawnie zarejestrowany i uruchomiony.");
            } catch (Exception e) {
                wyswietlKomunikat("Nie udalo sie zarejestrowaæ i uruchomic serwera.");
            }
        }
    }
    
    
    
    public void wyswietlKomunikat(String tekst) {
        komunikaty.append(tekst + "\n");
        komunikaty.setCaretPosition(komunikaty.getDocument().getLength());
    }

   
    public void odswiezListe(Vector<Client> klienci) {

        listaZalogowanych.clear();
        
        String[] niki = new String[klienci.size()];
        int i = 0;
        for (Client n : klienci) {
            try {
            	// listaZalogowanych.addElement(n.pobierzNicka());
            	niki[i] = n.pobierzNicka();
               i++;
                //System.out.println(n.pobierzNicka());
            } catch (Exception e) {
                System.out.println("Blad: " + e);
            }
        }
        Arrays.sort(niki);
        for (String nick : niki){
        	listaZalogowanych.addElement(nick);
        }
    }
    
    
    public static void main(String[] args) {
        new KarciankaServer();
    }

}
