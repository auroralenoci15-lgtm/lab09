package it.unibo.mvc;
import java.io.File; //importiamo file che e' la classe java che rappresenta un file o un percorso
import java.io.IOException; //serve per le eccezzioni 
import java.nio.file.Files;
/**
 * Application controller. Performs the I/O.
 */

public class Controller {
    private File currentFile; //File e' il tipo di dato rappresenta un file, nome della variabile
    public void setFile (final File file) { //imposta il file, il metodo riceve un oggetto File come parametro
        this.currentFile = file; //indica la variabile della classe Controller e assegna alla variabile il file ricevuto
    }
    public File getFile() {
        return this.currentFile; //restituisce il valore 
    }
    public String getFilePath() { //il metodo restitusce una linea di testo
        return this.currentFile.getPath();//accede al file attuale memorizzato e recupera il percorso del file in stringa
    }
    public Controller() {//costruttore
        this.currentFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "output.txt");
        //recupera la cartella personale dell'utente, recupera il separatore corretto per il sistema operativo (/ o \), nome del file e memorizza il file come file corrente 
    }
    public void save (final String text) throws IOException { //riceve il testo da salvare, dichiara che il metodo puo' generare un errore
    Files.writeString(this.currentFile.toPath(), text); //converte il file corrente in un percorso utilizzabile da Files e scrive il testo nel file. 
    }
}
