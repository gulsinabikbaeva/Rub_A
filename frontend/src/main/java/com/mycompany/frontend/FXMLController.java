package com.mycompany.frontend;

import com.mycompany.backend.Contact;
import com.mycompany.backend.Rubrica;
import com.mycompany.frontend.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLController implements Initializable {
    @FXML
    private AnchorPane parent;
    @FXML
    private ListView<Contact> listViewUtenti=new ListView<>();
    @FXML
    private TextField FieldCer=new TextField();
    Rubrica rubrica = new Rubrica();
    String fileUtenti ="src/main/resources/com/mycompany/backend/contacts.txt";


    //private Label NomeUtente;
    //private Label CellUtente;
    //Contact contactSelezionato;
    ObservableList<Contact> originalList = FXCollections.observableArrayList();

    @FXML
    private TextField FieldModificaNome; // field nome
    @FXML
    private TextField FieldModificaTel; // field cell
    @FXML
    private Button salvaModifica_utente1;
    @FXML
    private Button aggiungi_utente;
    @FXML
    private Button elimina_utente;
    @FXML
    private Button modifica_utente;

    @FXML
    private TextField FieldModificaCognome;
    @FXML
    private Label Msg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            leggiFileUtenti(fileUtenti,rubrica);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        sortListaUtenti();

    }
    public void leggiFileUtenti(String file, Rubrica rubrica) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine;

        while ((currentLine = reader.readLine()) != null){
            String[] parts = currentLine.split(",");
            String nome = parts[0];
            String cognome = parts[1];
            String cell = parts[2];
            Contact contact = new Contact(nome,cognome,cell);
            rubrica.aggiungiAllaListaUtenti(contact);
        }
        reader.close();
        sortListaUtenti();
    }


    public Contact trovaUtente(String nome, String cognome){
        List <Contact> listaUtenti = rubrica.getListaUtenti();
        for (Contact c : listaUtenti){
            //System.out.println("->"+c);
            if(c.getNome().compareToIgnoreCase(nome)==0 && c.getCognome().compareToIgnoreCase(cognome)==0){
                //System.out.println("trovato-->"+c);
                return c;
            }
        }
        return null;
    }


    @FXML
    private void ButtonGreenActionW1(MouseEvent event) {
    }

    @FXML
    private void ButtonYellowActionW1(MouseEvent event) {
        App.stage.setIconified(true);
    }

    @FXML
    private void ButtonRedActionW1(MouseEvent event) {
        System.exit(0);
    }


    @FXML
    private void eliminaUtenteAction(MouseEvent event) throws IOException {
        Msg.setText("");
        Contact contactSelezionato;
        contactSelezionato =null;
        contactSelezionato = listViewUtenti.getSelectionModel().getSelectedItem();
        if(contactSelezionato ==null){
            String m0=new String ("Si prega di selezionare il nome dell'utente.");
            Msg.setText(m0.toString());
        }
        for( Contact c : rubrica.getListaUtenti()){
            if(c.getNome()== contactSelezionato.getNome() && c.getCognome()== contactSelezionato.getCognome()){
                rubrica.getListaUtenti().remove(c);
                listViewUtenti.getItems().remove(c); // elimina l'utente selezionato
                String m = new String ("L'utente è stato rimosso.");
                Msg.setText(m.toString());
                break;
            }
        }
        sortListaUtenti();
        scriviFileUtenti(fileUtenti,rubrica);


    }

    @FXML
    private void modificaUtenteAction(MouseEvent event) throws IOException {
        Msg.setText("");
        var selectedItem = listViewUtenti.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            for( Contact c : rubrica.getListaUtenti()){
                if(c.getNome()==selectedItem.getNome() && c.getCognome()==selectedItem.getCognome()){
                    FieldModificaNome.setText(c.getNome().toString());
                    FieldModificaCognome.setText(c.getCognome().toString());
                    FieldModificaTel.setText(c.getNumeroCel().toString());
                    break;
                }
            }
        } else {
            Msg.setText("Si prega di selezionare il nome dell'utente.");
        }

        sortListaUtenti();
        scriviFileUtenti(fileUtenti,rubrica);
    }

    public void scriviFileUtenti(String file, Rubrica rubrica) throws IOException{
        sortListaUtenti();
        FileWriter fileout = new FileWriter(file);
        BufferedWriter filebuf = new BufferedWriter(fileout);
        PrintWriter printout = new PrintWriter(filebuf);
        for(Contact c : rubrica.getListaUtenti()){
            printout.println(c.getNome()+","+c.getCognome()+","+c.getNumeroCel());
        }
        printout.close();
    }


    public ListView<Contact> getlistViewUtenti(){
        sortListaUtenti();
        for(Contact c : rubrica.getListaUtenti()){
            this.listViewUtenti.getItems().add(c);
        }
        return this.listViewUtenti;
    }



    @FXML
    private void aggiungiUtenteAction(MouseEvent event) throws IOException {
        Msg.setText("");
        boolean b=false;
        if( !FieldModificaNome.getText().isBlank() && !FieldModificaNome.getText().isEmpty() && !FieldModificaCognome.getText().isBlank() && !FieldModificaCognome.getText().isEmpty() && !FieldModificaTel.getText().isBlank() && !FieldModificaTel.getText().isEmpty()){
            for(Contact c : rubrica.getListaUtenti()){

                if(c.getNome().equalsIgnoreCase(FieldModificaNome.getText())&& c.getCognome().equalsIgnoreCase(FieldModificaCognome.getText())){
                    Msg.setText("Un utente con questo nome esiste già.");
                    b=true;
                    break;
                }
            }
            if(b==false){
                Contact nuovo = new Contact(FieldModificaNome.getText(),FieldModificaCognome.getText(),FieldModificaTel.getText());
                rubrica.aggiungiAllaListaUtenti(nuovo);
                sortListaUtenti();
                listViewUtenti.getItems().clear();
                for(Contact c : rubrica.getListaUtenti()){
                    listViewUtenti.getItems().add(c);
                }

                scriviFileUtenti(fileUtenti,rubrica);
                FieldModificaNome.clear();
                FieldModificaCognome.clear();
                FieldModificaTel.clear();
                Msg.setText("L'utente è stato aggiunto.");
            }

        } else {
            Msg.setText("Inserire il nome/cognome/celullare.");
        }

    }

    @FXML
    private void cercaUtente(KeyEvent event) {
        Msg.setText("");
        List<Contact> subentries=new ArrayList<>();
        originalList.clear();
        originalList.addAll(rubrica.getListaUtenti());
        String entryText = FieldCer.getText().toUpperCase();

        listViewUtenti.setItems(originalList);

        for (Contact contact : originalList) {
            String current = contact.getNome();
            if(current.toUpperCase().contains(entryText.substring(0))){
                subentries.add(contact);
            }
        }
        listViewUtenti.getItems().clear();
        for(Contact c : subentries){
            listViewUtenti.getItems().add(c);
        }
    }

    @FXML
    private void salvaModificaUtenteAction(MouseEvent event) throws IOException {
        var selectedItem = listViewUtenti.getSelectionModel().getSelectedItem();
        if(selectedItem != null && !FieldModificaNome.getText().isBlank() && !FieldModificaNome.getText().isEmpty() && !FieldModificaTel.getText().isBlank() && !FieldModificaTel.getText().isEmpty()){
            for( Contact c : rubrica.getListaUtenti()){
                if(c.equals(selectedItem)){
                    c.setNome(FieldModificaNome.getText());
                    c.setCognome(FieldModificaCognome.getText());
                    c.setNumeroCel(FieldModificaTel.getText());
                    break;
                }
            }
            listViewUtenti.getItems().clear();
            for(Contact c : rubrica.getListaUtenti()){
                listViewUtenti.getItems().add(c);
            }
            sortListaUtenti();
            scriviFileUtenti(fileUtenti,rubrica);
            FieldModificaNome.clear();
            FieldModificaCognome.clear();
            FieldModificaTel.clear();
            Msg.setText("L'anagrafica dell'utente \nè stata aggiornata.");
        } else {
            Msg.setText("Si prega di selezionare il \nnome dell'utente.");
        }

    }

    private void sortListaUtenti() {
        Collections.sort(this.rubrica.getListaUtenti(), Contact.Comparators.NOME);
        this.listViewUtenti.getItems().clear();
        for(Contact c : rubrica.getListaUtenti()){
            this.listViewUtenti.getItems().add(c);
        }
    }

    @FXML
    private void handleMouseClickUtente(MouseEvent event) {
    }

}
