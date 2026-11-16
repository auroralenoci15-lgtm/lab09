
package it.unibo.mvc;

import javax.swing.JButton; // Crea i pulsanti
import javax.swing.JFrame; // Crea la finestra
import javax.swing.JOptionPane; // Mostra finestre di messaggio
import javax.swing.JPanel; // Contiene gli elementi grafici
import javax.swing.JTextArea; // Crea l'area di scrittura
import javax.swing.JTextField; // Mostra il percorso del file
import javax.swing.JFileChooser; // Permette di scegliere un file

import java.awt.BorderLayout; // Organizza gli elementi nella finestra
import java.awt.event.ActionListener; // Permette di ascoltare le azioni dell'utente
import java.awt.event.ActionEvent; // Rappresenta l'evento generato dall'utente
import java.io.File; // Rappresenta un file o un percorso
import java.io.IOException; // Gestisce eventuali errori durante il salvataggio

/**
 * A very simple program using a graphical interface.
 */
public final class SimpleGUIWithFileChooser {

    private static final String TITLE = "SimpleGUIWithFileChooser";

    private final JFrame frame = new JFrame(TITLE); // Crea la finestra
    private Controller controller = new Controller(); // Gestisce il file e il salvataggio

    public SimpleGUIWithFileChooser() { // Costruttore: prepara l'interfaccia

        final JPanel panel = new JPanel(); // Crea il pannello principale
        panel.setLayout(new BorderLayout()); // Imposta il layout principale

        final JPanel topPanel = new JPanel(); // Crea il pannello superiore
        topPanel.setLayout(new BorderLayout()); // Organizza gli elementi del pannello superiore

        JTextField filePath = new JTextField(); // Mostra il percorso del file
        filePath.setEditable(false); // Impedisce all'utente di modificare il percorso

        // Mostra inizialmente il percorso del file predefinito del Controller
        filePath.setText(controller.getFilePath());

        final JButton browse = new JButton("Browse..."); // Crea il pulsante Browse
        final JTextArea text = new JTextArea(); // Crea l'area in cui scrivere
        final JButton save = new JButton("Save"); // Crea il pulsante Save

        // Inserisce il campo del percorso al centro del pannello superiore
        topPanel.add(filePath, BorderLayout.CENTER);

        // Inserisce il pulsante Browse alla fine della riga
        topPanel.add(browse, BorderLayout.LINE_END);

        // Inserisce il pannello superiore nella parte alta della finestra
        panel.add(topPanel, BorderLayout.NORTH);

        // Inserisce l'area di testo al centro della finestra
        panel.add(text, BorderLayout.CENTER);

        // Inserisce il pulsante Save nella parte inferiore
        panel.add(save, BorderLayout.SOUTH);

        // Imposta il pannello come contenuto della finestra
        frame.setContentPane(panel);

        // Chiude il programma quando viene chiusa la finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aggiunge un ascoltatore al pulsante Browse
        browse.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {

                    // Crea la finestra per scegliere il file
                    final JFileChooser chooser = new JFileChooser();

                    // Mostra la finestra di selezione del file
                    final int result = chooser.showSaveDialog(frame);

                    // Controlla se l'utente ha confermato la scelta
                    if (result == JFileChooser.APPROVE_OPTION) {

                        // Recupera il file selezionato dall'utente
                        final File selectedFile = chooser.getSelectedFile();

                        // Comunica al Controller il nuovo file
                        controller.setFile(selectedFile);

                        // Aggiorna il percorso visualizzato nell'interfaccia
                        filePath.setText(controller.getFilePath());

                    } else if (result != JFileChooser.CANCEL_OPTION) {

                        // Se si è verificato un errore, mostra un messaggio
                        JOptionPane.showMessageDialog(
                            frame,
                            "An error occurred!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        );

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
        // Crea un nuovo oggetto SimpleGUIWithFileChooser e avvia la visualizzazione
        new SimpleGUIWithFileChooser().display();
    }
}
