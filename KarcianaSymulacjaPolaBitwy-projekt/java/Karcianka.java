package Projekt_v3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface Karcianka extends Remote {

    public void dolacz(Client k) throws RemoteException;

    public void pokazZajetosc() throws RemoteException;

    public void opusc(Client k) throws RemoteException;
    
    public boolean sprawdzNicka(String n) throws RemoteException;
    
    
    
    public void rozpocznijGre(Client n, int pokoj) throws RemoteException;
    
    public void wyrzucKarte(Client n, int i) throws RemoteException;
    
    public ArrayList pobierz1RzadPrzeciwnika(Client n) throws RemoteException;
    
    public ArrayList pobierz2RzadPrzeciwnika(Client n) throws RemoteException;
    
    public int pobierzLiczbeKartPrzeciwnika(Client n) throws RemoteException;
    
    public void passuj(Client n) throws RemoteException;
    
    public void poddajSie(Client n) throws RemoteException;
    
}