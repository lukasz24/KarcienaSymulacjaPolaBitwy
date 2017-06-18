package Projekt_v3;




import java.rmi.*;
import java.util.*;

public interface Client extends Remote {

	public void liczbaZalogowanych(int liczba) throws RemoteException;

    public void zajetoscPok(int i, int j, int k, int l) throws RemoteException;
    
    public void pokazDialog(String tresc, String tytol) throws RemoteException;

   // public void wiadomoscKonczaca(String nick, Vector<Client> lista) throws RemoteException;
    
    public void wyswietlInfo(String info) throws RemoteException;

    public String pobierzNicka() throws RemoteException;

    public void ustawNicka(String nick) throws RemoteException;
    
    public void losujKarty() throws RemoteException;
    
    public ArrayList pobierzKartyGracza() throws RemoteException;
    
    public ArrayList pobierzKarty1Rzedu() throws RemoteException;
    
    public ArrayList pobierzKarty2Rzedu() throws RemoteException;
    
    //public void dodajKarteDoRzedu(Karta karta) throws RemoteException;
    
    public void dodajKarteDoRzedu(int i) throws RemoteException;
    
    public void odblokujRzutKarty() throws RemoteException;
    
    public void odswiez() throws RemoteException;
     
    
    public ArrayList pobierzSile2Rzedu() throws RemoteException;
    
    public ArrayList pobierzSile1Rzedu() throws RemoteException;
    
    public int pobierzLiczbeKratWTalii() throws RemoteException;
    
    public int zsumujSile() throws RemoteException;
    
    public void setRunda(int i) throws RemoteException;
    
    public void setPass(boolean b) throws RemoteException;
    
    public boolean getPass() throws RemoteException;
    
    public void setWygraneRundy(int i) throws RemoteException;
    
    public int getWygraneRundy() throws RemoteException;
    
    public void wyczyscListy() throws RemoteException;
    
    public void ustawGranie() throws RemoteException;
    
    public void wyswietlWyniki(int i) throws RemoteException;
    
    public void czekajNaZakonczenie() throws RemoteException;
    
    
    public int getRunda() throws RemoteException;
    
    public void wlaczPass() throws RemoteException;
    
    public void wylaczWszystko() throws RemoteException;
    
    public void rysojPlansze() throws RemoteException;
    
    public void ustawRuch(boolean ruch) throws RemoteException;
    
    public void pobierzKartyPrzeciwnika() throws RemoteException;
}