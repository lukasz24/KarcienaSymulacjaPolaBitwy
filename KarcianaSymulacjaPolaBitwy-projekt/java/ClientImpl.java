package Projekt_v3;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class ClientImpl extends UnicastRemoteObject implements Client {
	
	private static final short ILOSC_KART = 10;
	private static final long serialVersionUID = 98L;
	
	private static final Semaphore tura = new Semaphore(1, true);
	private ArrayList<Karty> kartyGracza = new ArrayList<Karty>();;
	private ArrayList<Karty> kartyPierwszegoRzedu = new ArrayList<Karty>();;
	private ArrayList<Karty> kartyDrugiegoRzedu = new ArrayList<Karty>();;
    private KarciankaClient klient;
    private String nick;
    private boolean pass = false;
    private int wygraneRundy = 0, runda = 1;
    
   
    public ClientImpl(KarciankaClient klient, String nick) throws RemoteException {
        this.klient = klient;
        this.nick = nick;
    }
    
    public void czekajNaZakonczenie() throws RemoteException{
    	klient.czekajNaZakonczenie();
    }
    
    public void liczbaZalogowanych(int liczba) throws RemoteException {
       klient.wyswietlLiczbeGraczy(liczba);
    }

    
    public void zajetoscPok(int i, int j, int k, int l) throws RemoteException {
        klient.aktualizujZajetosc(i, j, k, l);
    }
    public void pokazDialog(String tresc, String tytol) throws RemoteException{
    	klient.wyswietlDialog(tresc, tytol);
    }

    public void rysojPlansze() throws RemoteException{
    	klient.tworzPlanszeGry();
    }
    public void wyswietlInfo(String info) throws RemoteException{
    	klient.wyswietlInfo(info);
    }
    public String pobierzNicka() throws RemoteException {
        return nick;
    }

    public void ustawNicka(String nick) throws RemoteException {
        this.nick = nick;
    }
    public void losujKarty() throws RemoteException{
    	kartyGracza.clear();
    	for(int i = 0; i < ILOSC_KART; i++){
    		Karty karta = new Karty(i);
    		kartyGracza.add(karta);
    	}
    }
    public ArrayList pobierzKartyGracza() throws RemoteException{
    	
    	return kartyGracza;
    }
    public ArrayList pobierzKarty1Rzedu() throws RemoteException{
    	return kartyPierwszegoRzedu;
    }
    public ArrayList pobierzKarty2Rzedu() throws RemoteException{
    	return kartyDrugiegoRzedu;
    }
   
    public void dodajKarteDoRzedu(int i) throws RemoteException{
    	Karty karta = kartyGracza.get(i);
    	
    	kartyGracza.remove(karta);
    	if(karta.pobierzRzad() == 1){
    		kartyPierwszegoRzedu.add(karta);
    	}else if(karta.pobierzRzad() == 2){
    		kartyDrugiegoRzedu.add(karta);
    	}
    }
    
    public void odblokujRzutKarty() throws RemoteException{
    	klient.odblokujRzutKarty();
    }
    
    public void odswiez() throws RemoteException{
    	klient.pobierzKarty();
    }
    public ArrayList pobierzSile1Rzedu() throws RemoteException{
    	ArrayList<Integer> sila = new ArrayList<>();
    	for(int i = 0; i < kartyPierwszegoRzedu.size(); i++){
    		sila.add(kartyPierwszegoRzedu.get(i).pobierzSile());
    	}
    	return sila;   			
    }
    
    public ArrayList pobierzSile2Rzedu() throws RemoteException{
    	ArrayList<Integer> sila = new ArrayList<>();
    	for(int i = 0; i < kartyDrugiegoRzedu.size(); i++){
    		sila.add(kartyDrugiegoRzedu.get(i).pobierzSile());
    	}
    	return sila;      			
    }
    
    public int pobierzLiczbeKratWTalii() throws RemoteException{
    	return kartyGracza.size();
    }
    
    public int zsumujSile() throws RemoteException{
   	int punkt=0, suma=0;
   	    for(int i =0; i< kartyPierwszegoRzedu.size(); i++)
   	    {
   		punkt = kartyPierwszegoRzedu.get(i).pobierzSile();
   		suma+=punkt;
   	    }
   	    for(int i =0; i< kartyDrugiegoRzedu.size(); i++)
   	    {
   		punkt = kartyDrugiegoRzedu.get(i).pobierzSile();
   		suma+=punkt;
   	    }
   	    return suma;
       }
       
       public void wyczyscListy() throws RemoteException{
   	kartyPierwszegoRzedu.clear();
   	kartyDrugiegoRzedu.clear();
       }
       
       public void setWygraneRundy(int i) throws RemoteException
       {
    	   wygraneRundy = i;
       }
       
       public int getWygraneRundy() throws RemoteException
       {
   	return wygraneRundy;
       }
    
    public void setPass(boolean b) throws RemoteException
    {
	pass = b;
    }
    
    public boolean getPass() throws RemoteException
    {
	return pass;
    }
    
    public void ustawGranie() throws RemoteException
    {
	klient.enableWK();
	klient.wyswietlInfo("Nowa runda");
    }
    
    public void wlaczPass() throws RemoteException
    {
	klient.enablePS();
    }
    public void wyswietlWyniki(int i) throws RemoteException{
    	klient.setYourWins();
    	klient.setOpponentsWins(i);
    	klient.setRunda();
    }
    
    public void setRunda(int i) throws RemoteException
    {
	runda = i;
    }
    
    public int getRunda() throws RemoteException
    {
	return runda;
    }
    
    public void wylaczWszystko() throws RemoteException
    {
	klient.wylaczALL();
    }
    
    public void ustawRuch(boolean ruch) throws RemoteException{
    	klient.ustawMozliwoscRuchu(ruch);
    }
    
    public void pobierzKartyPrzeciwnika() throws RemoteException{
    	klient.rysujKartyPrzeciwnika();
    }
    /*
    public int[] pobierzSile2Rzedu() throws RemoteException{
    	int[] sila = new int[kartyDrugiegoRzedu.size()];
    	for(int i = 0; i < kartyDrugiegoRzedu.size(); i++){
    		sila[i] = kartyDrugiegoRzedu.get(i).pobierzSile();
    	}
    	return sila;    			
    }
    */
    
    public class Karty {
    	
    	private final String[] kartyBliskiegoZasiegu = {"Legionista", "Hoplita", "Zwiadowca", "Chlop", "Husar", "Rydwan", "Rycerz", "Szermierz"};
    	private final String[] kartyDalekiegoZasiegu = {"Lucznik", "Kusznik", "Procarz", "Katapulta", "Balista", "Trebusz"};
    	private final String[] kartySpecjalne = {"Noc", "Bloto", "Wysmienite pociski", "Naostrzone miecze", "Kamuflaz", "Ulepszone zbroje"};
    	
    	private int id;
    	private int sila;
    	private int rzad;
    	private String nazwa;
    	
    	public Karty(int id){
    		this.id = id;
    		Random los = new Random();
    		sila = los.nextInt(5) + 1;
    		rzad = los.nextInt(2) + 1;
    		if(rzad == 1){
    			nazwa = kartyBliskiegoZasiegu[los.nextInt(kartyBliskiegoZasiegu.length)];
    		}else{
    			nazwa = kartyDalekiegoZasiegu[los.nextInt(kartyDalekiegoZasiegu.length)];
    		}
    		
    		
    	}
    	public int pobierzSile(){
    		return sila;
    	}
    	public int pobierzRzad(){
    		return rzad;
    	}
    	public String pobierzNazwe(){
    		return nazwa;
    	}
    	public void ustawSile(int newPower){
    		sila = newPower;
    	}
    	public int pobierzId(){
    		return id;
    	}
    		
    }


}
