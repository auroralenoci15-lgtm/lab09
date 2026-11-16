
package it.unibo.mvc;

import javax.swing.JButton; // Crea il pulsante
import javax.swing.JFrame; // Crea la finestra
import javax.swing.JOptionPane; // Mostra finestre di messaggio
import javax.swing.JPanel; // Contiene gli elementi grafici
import javax.swing.JTextArea; // Crea l'area in cui scrivere
import java.awt.BorderLayout; // Organizza gli elementi nella finestra
import java.awt.event.ActionListener; // Permette di ascoltare le azioni dell'utente
import java.awt.event.ActionEvent; // Rappresenta l'evento generato dall'utente
import java.io.IOException; // Gestisce eventuali errori durante il salvataggio

/**
 * A very simple program using a graphical interface.
 */
public final class SimpleGUI {

    private static final String TITLE = "SimpleGUI"; // Titolo della finestra
    private final JFrame frame = new JFrame(TITLE); // Crea la finestra
    private Controller controller = new Controller(); // Gestisce il file e il salvataggio

    public SimpleGUI() { // Costruttore: prepara l'interfaccia grafica

        final JPanel panel = new JPanel(); // Crea il pannello principale
        panel.setLayout(new BorderLayout()); // Imposta il layout BorderLayout

        JTextArea text = new JTextArea(); // Crea l'area in cui scrivere il testo
        final JButton save = new JButton("Save"); // Crea il pulsante Save

        panel.add(text, BorderLayout.CENTER); // Inserisce l'area di testo al centro
        panel.add(save, BorderLayout.SOUTH); // Inserisce il pulsante in basso

        // Imposta il pannello come contenuto della finestra
        frame.setContentPane(panel);

        // Chiude il programma quando viene chiusa la finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aggiunge un ascoltatore al pulsante Save
        save.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {

                    // Prova a salvare il testo attraverso il Controller
                    try {
                        controller.save(text.getText());

                    } catch (final IOException exception) {

                        // Mostra una finestra con il messaggio di errore
                        JOptionPane.showMessageDialog(
                            frame,
                            exception,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );

                        // Stampa i dettagli dell'errore nel terminale
                        exception.printStackTrace();
                    }
                }
            }
        );
    }

    private void display() {
        // Adatta le dimensioni della finestra agli elementi contenuti
        frame.pack();

        // Lascia al sistema operativo la scelta della posizione della finestra
        frame.setLocationByPlatform(true);

        // Rende visibile la finestra
        frame.setVisible(true);
    }

    public static void main() {
        // Crea un nuovo oggetto SimpleGUI e avvia la visualizzazione
        new SimpleGUI().display();
    }
}
