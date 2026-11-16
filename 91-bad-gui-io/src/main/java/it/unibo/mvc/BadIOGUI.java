package it.unibo.mvc;

import javax.swing.BoxLayout; //BoxLayaout dispone in fila o in colonna dipende se c'e' un X o una Y 
//in questo caso abbiamo X quindi sarà in fila.
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File; 
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files; //contiene metodi per lavorare nei file
import java.util.List; //rappresenta una lista di elementi
import java.util.Random;

/**
 * This class is a simple application that writes a random number on a file.
 *
 * <p>
 * This application does not exploit the model-view-controller pattern, and as
 * such is just to be used to learn the basics, not as a template for your
 * applications.
 */
public class BadIOGUI {

    private static final String TITLE = "A very simple GUI application";
    private static final String PATH = System.getProperty("user.home")
        + File.separator
        + BadIOGUI.class.getSimpleName() + ".txt";
    //private static final int PROPORTION = 5;
    private final Random randomGenerator = new Random();
    private final JFrame frame = new JFrame(TITLE); //è la finestra che appare sullo schermo quando avviamo il programma
    //JFrame: indica il tipo di oggetto, frame: è il nome che abbiamo dato alla finestra e new JFrame(TITLE): crea materialmente la finestra utilizzando il titolo contenuto in TITLE.

    /**
     * Creates a new BadIOGUI.
     */
    public BadIOGUI() { //costruttore
    final JPanel canvas = new JPanel(); //creazione di un nuovo pannelo canvas 
    canvas.setLayout(new BorderLayout());//stabilisce che il pannello userà un BorderLayout (guarda linea 2 e 3)

    final JPanel buttonsPanel = new JPanel();//creazione del secondo pannello (buttonsPanel) che conterra' i pulsanti
    buttonsPanel.setLayout(//imposta il sistema di disposizione degli elementi
        new BoxLayout(buttonsPanel, BoxLayout.X_AXIS) //crea un nuovo boox idica su quale pannello applicarlo e li dispone in fila.
    );
//creazione dei due pulsanti
    final JButton write = new JButton("Write on file"); //serve a scrivere un numero nel file
    final JButton read = new JButton("Read");//serve a leggere il contenuto del file

    buttonsPanel.add(write); //viene aggiunto al buttonsPanel
    buttonsPanel.add(read); //viene aggiunto al buttonsPanel
    canvas.add(buttonsPanel, BorderLayout.CENTER); //divide il contenitore in 5 zone (nord, sud ...)

    frame.setContentPane(canvas); //stabilisce quale pannello sarà il contenuto principale della finestra
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Handlers
         */
        // ActionListener del pulsante WRITE
        write.addActionListener( //colleghiamo il ActionLister al pulsante write
            new ActionListener() { //creamo l'azione
                @Override //stiamo implementando un metodo previsto dall'interfaccia
                public void actionPerformed(final ActionEvent ignored) { //ignored e' il nome del parametro che contiene l'evento e non lo usiamo direttamente
                    /*
                     * This would be VERY BAD in a real application.
                     *
                     * This makes the Event Dispatch Thread (EDT) work on an I/O
                     * operation. I/O operations may take a long time, during which
                     * your UI becomes completely unresponsive.
                     */
                    try (PrintStream ps = new PrintStream(PATH, StandardCharsets.UTF_8)) { //questa parte permette di scrivere dati di flusso output
                        ps.print(randomGenerator.nextInt()); //scrive il contenuto e genera un numero intero casuale
                    } catch (final IOException e) { //questa parte serve a gestire errori durante la scrittura
                        JOptionPane.showMessageDialog(frame, e, "Error", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace(); // NOPMD: allowed as this is just an exercise
                    }
                }
            }
        );
        // ActionListener del pulsante READ
        read.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent ignored) {
                    try { //prova ad eseguire le istruzioni che seguono (prova perche' potrebbero esserci errori)
                    //legge il file    
                    final List<String> lines = Files.readAllLines( //salva tutte le righe in un lista di stringhe e Files.readAllLines leggere tutte le righe del file
                            java.nio.file.Path.of(PATH), //Path.of... trasforma il percorso contenuto in PATH in un oggetto che rappresenta il percorso
                            StandardCharsets.UTF_8//specifica la codifica dei caratteri
                        );
                        //serve per visitare tutti gli elementi di una lista
                        /*LOGICA: prendi una riga dalla lista, stampala nel terminale, passa alla riga successiva e ripeti fino alla fine */
                        for (final String line : lines) { 
                        System.out.println(line); //line e' la variabile che contiene di volta in volta la riga corrente
                        }

                        //se la lettura del file non riesce andiamo in catch
                        } catch (final IOException e) { //IOException è un tipo di eccessione legata agli errori di I/O
                        e.printStackTrace(); //stampa nel terminale le informazioni sull'errore, utili per capire cosa è successo.
                        }
                    }
                }
            );
        }//chiusura del costruttore

    private void display() {
        /*
         * Make the frame one fifth the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected. In order to deal coherently with multimonitor
         * setups, other facilities exist (see the Java documentation about this
         * issue). It is MUCH better than manually specify the size of a window
         * in pixel: it takes into account the current resolution.
         */
        /*final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        
        non serve con freame.pack perche' decide lui in automatico*/  
        
       
    frame.pack();//java decide le dimensione minime necesssarie per i suoi componenti.
    frame.setLocationByPlatform(true); //lascia al sistema operativo la scelta della posizone della finestra
    frame.setVisible(true); //rende visibile la finestra
}

    /**
     * Launches the application.
     */
    public static void main() {
       new BadIOGUI().display();
    }
}
