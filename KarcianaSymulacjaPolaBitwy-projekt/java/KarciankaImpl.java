package Projekt_v3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;





public class KarciankaImpl extends UnicastRemoteObject implements Karcianka {

    private Vector<Client> klienci = new Vector<Client>();
    private Vector<Client> grajacy = new Vector<Client>();
    
    private Vector<Client> pokoj1 = new Vector<Client>();
    private Vector<Client> pokoj2 = new Vector<Client>();
    private Vector<Client> pokoj3 = new Vector<Client>();
    private Vector<Client> pokoj4 = new Vector<Client>();
    
   
    private KarciankaServer serwer;
    
    private static final Semaphore ruch = new Semaphore(1, true);

    public KarciankaImpl(KarciankaServer serwer) throws RemoteException {
        this.serwer = serwer;
    }

    public synchronized void dolacz(Client n) throws RemoteException {

        
        klienci.add(n);
        
        //serwer.odswiezListe(klienci);
        serwer.odswiezListe(klienci);
        	serwer.wyswietlKomunikat("Zalogowal sie: " + n.pobierzNicka());
        
        for (Iterator<Client> i = klienci.iterator(); i.hasNext();) {
            Client klient = i.next();
            klient.liczbaZalogowanych(klienci.size());
        }
        
        pokazZajetosc();
    }
    
    public void pokazZajetosc() throws RemoteException{
    	for (Iterator<Client> i = klienci.iterator(); i.hasNext();) {
            Client klient = i.next();
            klient.zajetoscPok(pokoj1.size(), pokoj2.size(), pokoj3.size(), pokoj4.size());
        }
    }
    
    /*
    public synchronized void wiadomosc(Client n, String s) throws RemoteException {

        for (Iterator<Client> i = klienci.iterator(); i.hasNext();) {
            Client klient = i.next();
            //klient.wiadomosc(n.pobierzNicka(), s);
        }
    }
*/
    public void opusc(Client n) throws RemoteException {

        synchronized(klienci){
        	klienci.remove(n);
        }
        synchronized(pokoj1){
        	if(pokoj1.contains(n)){
            	pokoj1.remove(n);
            }
		}
        synchronized(pokoj2){
        	if(pokoj2.contains(n)){
            	pokoj2.remove(n);
            }
		}
        synchronized(pokoj3){
        	if(pokoj3.contains(n)){
            	pokoj3.remove(n);
            }
		}
        synchronized(pokoj4){
        	if(pokoj4.contains(n)){
            	pokoj4.remove(n);
            }
		}
               
        
        serwer.odswiezListe(klienci);
        
        serwer.wyswietlKomunikat(n.pobierzNicka() + " wylogowal/a sie.");

        for (Iterator<Client> i = klienci.iterator(); i.hasNext();) {
            Client klient = (Client) i.next();
            klient.liczbaZalogowanych(klienci.size());           
        }
        pokazZajetosc();
    }
    
    public synchronized boolean sprawdzNicka(String n) throws RemoteException {
    	if(n == null)return true;
    	if(n.equals(""))return true;
    	for (Client k : klienci) {
            try {
            	// listaZalogowanych.addElement(n.pobierzNicka());
            	if(n.equals(k.pobierzNicka())) return true;
               
                //System.out.println(n.pobierzNicka());
            } catch (Exception e) {
                System.out.println("Blad: " + e);
            }
            
        }
    	return false;
    }
    public void rozpocznijGre(Client n, int pokoj) throws RemoteException{
    	n.setRunda(1);
    	n.setWygraneRundy(0);
    	n.wyswietlWyniki(0);
    	switch (pokoj) {
		case 1:
			if(pokoj1.size() == 2){
				n.pokazDialog("Wybrany pokoj jest zajety!", "Brak miejsc");
			}else{
				synchronized(pokoj1){
					pokoj1.add(n);
				}
				n.rysojPlansze();
				n.losujKarty();
				n.odswiez();
				if(pokoj1.size() == 1){
					n.wyswietlInfo("Oczekiwanie na dolaczenie przeciwnika.");
					
				}else{
					//n.wyswietlInfo("Rozpoczêto grê pomiêdzy: " + pokoj1.get(0) + " i " + pokoj1.get(1));
					pokoj1.get(0).wyswietlInfo("Rozpoczeto grê pomiedzy: " + pokoj1.get(0).pobierzNicka() + " i " + pokoj1.get(1).pobierzNicka());
					pokoj1.get(1).wyswietlInfo("Rozpoczeto grê pomiedzy: " + pokoj1.get(0).pobierzNicka() + " i " + pokoj1.get(1).pobierzNicka());
					//pokoj1.get(0).odswiez();
					//pokoj1.get(1).odswiez();
					pokoj1.get(0).pobierzKartyPrzeciwnika();
					pokoj1.get(0).ustawRuch(true);
					pokoj1.get(0).wyswietlInfo("Twoj ruch");
					pokoj1.get(1).wyswietlInfo("Ruch przeciwnika");
					/*
					if(Math.random() > 0.5){
						pokoj1.get(0).ustawRuch(true);
					}else{
						pokoj1.get(1).ustawRuch(true);
					}
					*/
				};
				
			}	
			break;
		case 2:
			if(pokoj2.size() == 2){
				n.pokazDialog("Wybrany pokoj jest zajety!", "Brak miejsc");
			}else{
				synchronized(pokoj2){
					pokoj2.add(n);
				}
				n.rysojPlansze();
				n.losujKarty();
				n.odswiez();
				if(pokoj2.size() == 1){
					n.wyswietlInfo("Oczekiwanie na dolaczenie przeciwnika.");
				}else{
					//n.wyswietlInfo("Rozpoczêto grê pomiêdzy: " + pokoj2.get(0) + " i " + pokoj2.get(1));
					pokoj2.get(0).wyswietlInfo("Rozpoczeto grê pomiedzy: " + pokoj2.get(0).pobierzNicka() + " i " + pokoj2.get(1).pobierzNicka());
					pokoj2.get(1).wyswietlInfo("Rozpoczeto grê pomiedzy: " + pokoj2.get(0).pobierzNicka() + " i " + pokoj2.get(1).pobierzNicka());
					//pokoj2.get(0).odswiez();
					//pokoj2.get(1).odswiez();
					pokoj2.get(0).pobierzKartyPrzeciwnika();
					pokoj2.get(0).ustawRuch(true);
					pokoj2.get(0).wyswietlInfo("Twoj ruch");
					pokoj2.get(1).wyswietlInfo("Ruch przeciwnika");
					/*
					if(Math.random() > 0.5){
						pokoj2.get(0).ustawRuch(true);
					}else{
						pokoj2.get(1).ustawRuch(true);
					}
					*/
				};
				
				
			}
			break;
		case 3:
			if(pokoj3.size() == 2){
				n.pokazDialog("Wybrany pokoj jest zajety!", "Brak miejsc");
			}else{
				synchronized(pokoj3){
					pokoj3.add(n);
				}
				n.rysojPlansze();
				n.losujKarty();
				n.odswiez();
				if(pokoj3.size() == 1){
					n.wyswietlInfo("Oczekiwanie na do³¹czenie przeciwnika.");
				}else{
					//n.wyswietlInfo("Rozpoczêto grê pomiêdzy: " + pokoj3.get(0) + " i " + pokoj3.get(1));
					pokoj3.get(0).wyswietlInfo("Rozpoczeto gre pomiedzy: " + pokoj3.get(0).pobierzNicka() + " i " + pokoj3.get(1).pobierzNicka());
					pokoj3.get(1).wyswietlInfo("Rozpoczeto grê pomiedzy: " + pokoj3.get(0).pobierzNicka() + " i " + pokoj3.get(1).pobierzNicka());
					//pokoj3.get(0).odswiez();
					//pokoj3.get(1).odswiez();
					pokoj3.get(0).pobierzKartyPrzeciwnika();
					pokoj3.get(0).ustawRuch(true);
					pokoj3.get(0).wyswietlInfo("Twoj ruch");
					pokoj3.get(1).wyswietlInfo("Ruch przeciwnika");
					/*
					if(Math.random() > 0.5){
						pokoj3.get(0).ustawRuch(true);
					}else{
						pokoj3.get(1).ustawRuch(true);
					}
					*/
				};
				
				
			}
			break;
		case 4:
			if(pokoj4.size() == 2){
				n.pokazDialog("Wybrany pokoj jest zajety!", "Brak miejsc");
			}else{
				synchronized(pokoj4){
					pokoj4.add(n);
				}
				n.rysojPlansze();
				n.losujKarty();
				n.odswiez();
				if(pokoj4.size() == 1){
					n.wyswietlInfo("Oczekiwanie na dolaczenie przeciwnika.");
				}else{
					//n.wyswietlInfo("Rozpoczêto grê pomiêdzy: " + pokoj4.get(0) + " i " + pokoj4.get(1));
					pokoj4.get(0).wyswietlInfo("Rozpoczeto gre pomiêdzy: " + pokoj4.get(0).pobierzNicka() + " i " + pokoj4.get(1).pobierzNicka());
					pokoj4.get(1).wyswietlInfo("Rozpoczeto gre pomiedzy: " + pokoj4.get(0).pobierzNicka() + " i " + pokoj4.get(1).pobierzNicka());
					//pokoj4.get(0).odswiez();
					//pokoj4.get(1).odswiez();
					pokoj4.get(0).pobierzKartyPrzeciwnika();
					pokoj4.get(0).ustawRuch(true);
					pokoj4.get(0).wyswietlInfo("Twoj ruch");
					pokoj4.get(1).wyswietlInfo("Ruch przeciwnika");
					
				};
				
				
				
			}
			break;
		default:
			break;
		}
    	pokazZajetosc();
    	    	
    }
    
   
    
    
    public void wyrzucKarte(Client n, int i) throws RemoteException{
    	n.dodajKarteDoRzedu(i);
    	n.wyswietlInfo("Wyrzuciles Karte. Ruch przeciwnika.");
    	
    	n.odswiez();
    	
    	if(pokoj1.contains(n)){
    		if(!n.pobierzNicka().equals(pokoj1.get(0).pobierzNicka())){
    			pokoj1.get(0).pobierzKartyPrzeciwnika();
    			if(!pokoj1.get(0).getPass()){
    				pokoj1.get(0).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
				
    		}else{
    			pokoj1.get(1).pobierzKartyPrzeciwnika();
    			if(!pokoj1.get(1).getPass()){
    				pokoj1.get(1).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
    		}
    	}else if(pokoj2.contains(n)){
    		if(!n.pobierzNicka().equals(pokoj2.get(0).pobierzNicka())){
    			pokoj2.get(0).pobierzKartyPrzeciwnika();
    			if(!pokoj2.get(0).getPass()){
    				pokoj2.get(0).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
    		}else{
    			pokoj2.get(1).pobierzKartyPrzeciwnika();
    			if(!pokoj2.get(1).getPass()){
    				pokoj2.get(1).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
    		}
    	}else if(pokoj3.contains(n)){
    		if(!n.pobierzNicka().equals(pokoj3.get(0).pobierzNicka())){
    			pokoj3.get(0).pobierzKartyPrzeciwnika();
    			if(!pokoj3.get(0).getPass()){
    				pokoj3.get(0).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
    		}else{
    			pokoj3.get(1).pobierzKartyPrzeciwnika();
    			if(!pokoj3.get(1).getPass()){
    				pokoj3.get(1).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
    		}
    	}else if(pokoj4.contains(n)){
    		if(!n.pobierzNicka().equals(pokoj4.get(0).pobierzNicka())){
    			pokoj4.get(0).pobierzKartyPrzeciwnika();
    			if(!pokoj4.get(0).getPass()){
    				pokoj4.get(0).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
    		}else{
    			pokoj4.get(1).pobierzKartyPrzeciwnika();
    			if(!pokoj4.get(1).getPass()){
    				pokoj4.get(1).ustawRuch(true);
    			}else {
    				n.ustawRuch(true);
    			}
    		}
    	}

    	
    }
    
    
    
    public ArrayList pobierz1RzadPrzeciwnika(Client n) throws RemoteException{
    	ArrayList<Integer> karty1 = new ArrayList<>();
    	if(pokoj1.contains(n)){
    		if(pokoj1.size() > 1){
        		
            	if(!pokoj1.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		karty1 = pokoj1.get(0).pobierzSile1Rzedu();
            	}
            	if(!pokoj1.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		karty1 = pokoj1.get(1).pobierzSile1Rzedu();
            	}
            	
        	}
    	}else if(pokoj2.contains(n)){
    		if(pokoj2.size() > 1){
        		
            	if(!pokoj2.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		karty1 = pokoj2.get(0).pobierzSile1Rzedu();
            	}
            	if(!pokoj2.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		karty1 = pokoj2.get(1).pobierzSile1Rzedu();
            	}
            	
        	}
    	}else if(pokoj3.contains(n)){
    		if(pokoj3.size() > 1){
        		
            	if(!pokoj3.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		karty1 = pokoj3.get(0).pobierzSile1Rzedu();
            	}
            	if(!pokoj3.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		karty1 = pokoj3.get(1).pobierzSile1Rzedu();
            	}
            	
        	}
    	}else if(pokoj4.contains(n)){
    		if(pokoj4.size() > 1){
        		
            	if(!pokoj4.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		karty1 = pokoj4.get(0).pobierzSile1Rzedu();
            	}
            	if(!pokoj4.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		karty1 = pokoj4.get(1).pobierzSile1Rzedu();
            	}
            	
        	}
    	}
    	
    	return karty1;
    	
    }
    
    public ArrayList pobierz2RzadPrzeciwnika(Client n) throws RemoteException{
    	ArrayList<Integer> silaKart = new ArrayList<>();
    	if(pokoj1.contains(n)){
    		if(pokoj1.size() > 1){
        		
            	if(!pokoj1.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		silaKart = pokoj1.get(0).pobierzSile2Rzedu();
            	}
            	if(!pokoj1.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		silaKart = pokoj1.get(1).pobierzSile2Rzedu();
            	}
            	
        	}
    	}else if(pokoj2.contains(n)){
    		if(pokoj2.size() > 1){
        		
            	if(!pokoj2.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		silaKart = pokoj2.get(0).pobierzSile2Rzedu();
            	}
            	if(!pokoj2.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		silaKart = pokoj2.get(1).pobierzSile2Rzedu();
            	}
            	
        	}
    	}else if(pokoj3.contains(n)){
    		if(pokoj3.size() > 1){
        		
            	if(!pokoj3.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		silaKart = pokoj3.get(0).pobierzSile2Rzedu();
            	}
            	if(!pokoj3.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		silaKart = pokoj3.get(1).pobierzSile2Rzedu();
            	}
            	
        	}
    	}else if(pokoj4.contains(n)){
    		if(pokoj4.size() > 1){
        		
            	if(!pokoj4.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		silaKart = pokoj4.get(0).pobierzSile2Rzedu();
            	}
            	if(!pokoj4.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		silaKart = pokoj4.get(1).pobierzSile2Rzedu();
            	}
            	
        	}
    	}
    	   	
    	return silaKart;
    	
    }
    
    public int pobierzLiczbeKartPrzeciwnika(Client n) throws RemoteException{
    	int liczba = 10;
    	if(pokoj1.contains(n)){
    		if(pokoj1.size() > 1){
        		
            	if(!pokoj1.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		liczba = pokoj1.get(0).pobierzLiczbeKratWTalii();
            	}
            	if(!pokoj1.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		liczba = pokoj1.get(1).pobierzLiczbeKratWTalii();
            	}
            	
        	}
    	}else if(pokoj2.contains(n)){
    		if(pokoj2.size() > 1){
        		
            	if(!pokoj2.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		liczba = pokoj2.get(0).pobierzLiczbeKratWTalii();
            	}
            	if(!pokoj2.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		liczba = pokoj2.get(1).pobierzLiczbeKratWTalii();
            	}
            	
        	}
    	}else if(pokoj3.contains(n)){
    		if(pokoj3.size() > 1){
        		
            	if(!pokoj3.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		liczba = pokoj3.get(0).pobierzLiczbeKratWTalii();
            	}
            	if(!pokoj3.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		liczba = pokoj3.get(1).pobierzLiczbeKratWTalii();
            	}
            	
        	}
    	}else if(pokoj4.contains(n)){
    		if(pokoj4.size() > 1){
        		
            	if(!pokoj4.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
            		liczba = pokoj4.get(0).pobierzLiczbeKratWTalii();
            	}
            	if(!pokoj4.get(1).pobierzNicka().equals(n.pobierzNicka())){
            		liczba = pokoj4.get(1).pobierzLiczbeKratWTalii();
            	}
            	
        	}
    	}
    	
    	return liczba;
    }
    
    public void passuj(Client n) throws RemoteException{
    	Client przeciwnik = null;
    	n.setPass(true);
    	n.wylaczWszystko();
    	if(pokoj1.contains(n)){
    		if(!pokoj1.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj1.get(0);
    			//przeciwnik.wyswietlInfo("Przeciwnik passowa³");   			
        		
        	}
        	if(!pokoj1.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj1.get(1);
    			//przeciwnik.wyswietlInfo("Przeciwnik passowa³"); 
        	}
    	}else if(pokoj2.contains(n)){
    		if(!pokoj2.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj2.get(0);
    			//przeciwnik.wyswietlInfo("Przeciwnik passowa³"); 
        	}
        	if(!pokoj2.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj2.get(1);
    			//przeciwnik.wyswietlInfo("Przeciwnik passowa³"); 
        	}
    	}else if(pokoj3.contains(n)){
    		if(!pokoj3.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj3.get(0);
    		//	przeciwnik.wyswietlInfo("Przeciwnik passowa³"); 
        	}
        	if(!pokoj3.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj3.get(1);
    			//przeciwnik.wyswietlInfo("Przeciwnik passowa³"); 
        	}
    	}else if(pokoj4.contains(n)){
    		if(!pokoj4.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj4.get(0);
    			//przeciwnik.wyswietlInfo("Przeciwnik passowa³"); 
        	}
        	if(!pokoj4.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj4.get(1);
    			//przeciwnik.wyswietlInfo("Przeciwnik passowa³"); 
        	}
    	}
    	przeciwnik.wyswietlInfo("Przeciwnik passowal");
    	if(przeciwnik.getPass()){
    		if(n.zsumujSile() < przeciwnik.zsumujSile()){
    			n.wyswietlInfo("Niestety przegrales te runde");
    			przeciwnik.wyswietlInfo("Gratulacje! Wygrales te runde");
    			przeciwnik.setWygraneRundy(przeciwnik.getWygraneRundy() + 1);
    		}else if(n.zsumujSile() > przeciwnik.zsumujSile()){
    			przeciwnik.wyswietlInfo("Niestety przegrales te runde");
    			n.wyswietlInfo("Gratulacje! Wygraleœ te runde!");
    			n.setWygraneRundy(n.getWygraneRundy() + 1);
    		}else{
    			n.wyswietlInfo("Remis");
    			przeciwnik.wyswietlInfo("Remis");
    			n.setWygraneRundy(n.getWygraneRundy() + 1);
    			przeciwnik.setWygraneRundy(przeciwnik.getWygraneRundy() + 1);
    		}
    		n.wyczyscListy();
    		przeciwnik.wyczyscListy();
    		
    		n.wyswietlWyniki(przeciwnik.getWygraneRundy());
    		przeciwnik.wyswietlWyniki(n.getWygraneRundy());
    		
    		if(n.getWygraneRundy() == 2 && przeciwnik.getWygraneRundy() == 2){
    			n.wyswietlInfo("Remis calej rozgrywki.");
    			przeciwnik.wyswietlInfo("Remis calej rozgrywki.");
    			koniecPoPodsumowaniu(n, przeciwnik);
    		}else if(n.getWygraneRundy() == 2 ){
    			n.wyswietlInfo("Brawo! Wygraleœ cala rozgrywke!");
    			przeciwnik.wyswietlInfo("Niestety przegrales.");
    			koniecPoPodsumowaniu(n, przeciwnik);
    		}else if(przeciwnik.getWygraneRundy() == 2){
    			n.wyswietlInfo("Niestety przegrales.");
    			przeciwnik.wyswietlInfo("Brawo! Wygrales ca³a rozgrywke!");
    			
    			koniecPoPodsumowaniu(n, przeciwnik);
    			
    		}else{
    			n.setRunda(n.getRunda() + 1);
        		przeciwnik.setRunda(przeciwnik.getRunda() + 1);
        		
        		if(Math.random() > 0.5){
        			n.ustawRuch(true);
        		}else{
        			przeciwnik.ustawRuch(true);
        		}
        		
    		}
    		
    		n.setPass(false);
    		przeciwnik.setPass(false);
    		
    	}
    	
    }
    
    private void koniecPoPodsumowaniu(Client n, Client przeciwnik){
    	try {
			n.czekajNaZakonczenie();
			przeciwnik.czekajNaZakonczenie();
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
    	opuscPokoj(n);
    	opuscPokoj(przeciwnik);
    	
    }
    public void opuscPokoj(Client n){
    	synchronized(pokoj1){
        	if(pokoj1.contains(n)){
            	pokoj1.remove(n);
            }
		}
        synchronized(pokoj2){
        	if(pokoj2.contains(n)){
            	pokoj2.remove(n);
            }
		}
        synchronized(pokoj3){
        	if(pokoj3.contains(n)){
            	pokoj3.remove(n);
            }
		}
        synchronized(pokoj4){
        	if(pokoj4.contains(n)){
            	pokoj4.remove(n);
            }
		}
        
        try {
			pokazZajetosc();
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
    }
    
    public void poddajSie(Client n) throws RemoteException{
    	Client przeciwnik = null;
    	
    	n.wylaczWszystko();
    	if(pokoj1.contains(n)){
    		if(!pokoj1.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj1.get(0);
        	}
        	if(!pokoj1.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj1.get(1);
    			
        	}
    	}else if(pokoj2.contains(n)){
    		if(!pokoj2.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj2.get(0);
    			
        	}
        	if(!pokoj2.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj2.get(1);
    			
        	}
    	}else if(pokoj3.contains(n)){
    		if(!pokoj3.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj3.get(0);
    		
        	}
        	if(!pokoj3.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj3.get(1);
    			
        	}
    	}else if(pokoj4.contains(n)){
    		if(!pokoj4.get(0).pobierzNicka().equals(n.pobierzNicka()) ){
    			przeciwnik = pokoj4.get(0);
    			 
        	}
        	if(!pokoj4.get(1).pobierzNicka().equals(n.pobierzNicka())){
        		przeciwnik = pokoj4.get(1);
    			
        	}
    	}
    	
    	przeciwnik.wyswietlInfo("Twoj przeciwnik poddal sie - WYGRALES!");
    	opuscPokoj(n);
    	opuscPokoj(przeciwnik);
    }
    
}
