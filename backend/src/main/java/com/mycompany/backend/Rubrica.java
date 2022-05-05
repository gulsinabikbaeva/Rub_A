package com.mycompany.backend;

import java.util.ArrayList;
import java.util.List;

public class Rubrica {

    private List <Contact> listaUtenti;

    public Rubrica() {
        this.listaUtenti = new ArrayList();
    }



    public void aggiungiAllaListaUtenti(Contact c){
        this.listaUtenti.add(c);
        List <Contact> sortedList = new ArrayList();
        sortedList.addAll(this.listaUtenti);

    }
    public void eliminaDallaListaUtenti(Contact c){
        this.listaUtenti.remove(c);
    }


    public List<Contact> getListaUtenti() {
        return listaUtenti;
    }

    @Override
    public String toString() {
        return "RubricaTelefonica{"+ " lista=" + listaUtenti + '}';
    }
}

