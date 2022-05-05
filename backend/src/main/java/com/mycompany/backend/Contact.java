package com.mycompany.backend;

import java.util.Comparator;

public class Contact implements Comparable<Contact>{
    private String nome;
    private String cognome;
    private String numeroCel;

    public Contact(String nome, String cognome, String numeroCel) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroCel = numeroCel;
    }

    public String getNome() {
        return nome;
    }

    public String getNumeroCel() {
        return numeroCel;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumeroCel(String numeroCel) {
        this.numeroCel = numeroCel;
    }

    @Override
    public String toString() {
        return nome.toUpperCase()+" "+cognome.toUpperCase();
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


    @Override
    public int compareTo(Contact o) {
        return Comparators.NOME.compare(this, o);
    }


    public static class Comparators {

        public static Comparator<Contact> NOME = new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.nome.compareTo(o2.nome);
            }
        };
    }
}
