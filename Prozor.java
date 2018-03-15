import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Prozor extends JFrame {
    private JTextArea listaFajlovi;
    private JTextArea listaSlike;
    private final String ime = System.getProperty("user.name");

    public Prozor() {
        this.setSize(1250, 600);
        this.setLayout(new BorderLayout());
        this.setTitle("WINDOWS 10  WALLPAPER DIGGER");
        JPanel panelListe = new JPanel();
        this.add(panelListe, "Center");
        panelListe.setLayout(new GridLayout(1, 2));
        this.listaFajlovi = new JTextArea();
        this.listaFajlovi.setEditable(false);
        this.listaSlike = new JTextArea();
        this.listaSlike.setEditable(false);
        panelListe.add(new JScrollPane(this.listaFajlovi, 22, 30));
        panelListe.add(new JScrollPane(this.listaSlike, 22, 30));
        JPanel panelKontrola = new JPanel();
        panelKontrola.setLayout(new GridLayout(1, 3));
        this.add(panelKontrola, "North");
        JLabel imeKorisnika = new JLabel("Files will be saved in desktop/windowsImageDigger");
        panelKontrola.add(imeKorisnika);
        JButton ucitajKorisnika = new JButton("SHOW ARCHIVES");
        panelKontrola.add(ucitajKorisnika);
        ucitajKorisnika.addActionListener((e) -> {
            this.ucitajFajlove();
        });
        JButton sacuvajSlike = new JButton("SAVE AS PICTURES");
        panelKontrola.add(sacuvajSlike);
        sacuvajSlike.addActionListener((e) -> {
            this.sacuvajSlikeNaDesktop();
        });
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
    }

    private void ucitajFajlove() {
        File folder = new File("C:\\Users\\" + this.ime + "\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets");
        File[] listaFajlova = folder.listFiles();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.listaFajlovi.setText("");
        File[] var4 = listaFajlova;
        int var5 = listaFajlova.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            File file = var4[var6];
            if(file.isFile()) {
                this.listaFajlovi.append(file.getName() + "            \t" + df.format((double)((float)file.length() / 1024.0F)) + "KB\n");
            }
        }

    }

    private void sacuvajSlikeNaDesktop() {
        File folder = new File("C:\\Users\\" + this.ime + "\\Desktop\\windowsImageDigger");
        File folderSRC = new File("C:\\Users\\" + this.ime + "\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets");
        File[] listaFajlova = folderSRC.listFiles();
        folder.mkdir();
        if(listaFajlova.length == 0) {
            JOptionPane.showMessageDialog((Component)null, "ARCHIVE FOLDER IS EMPTY, OR DOES NOT EXIST!");
        }

        int brFajla = 0;
        File[] listaSlika = listaFajlova;
        int df = listaFajlova.length;

        int i;
        for(i = 0; i < df; ++i) {
            File file = listaSlika[i];
            if(file.length() / 1024L > 150L && jelJPG(file)) {
                File f = new File("C:\\Users\\" + this.ime + "\\Desktop\\windowsImageDigger\\" + brFajla + ".jpg");

                try {
                    Files.copy(file.toPath(), f.toPath(), new CopyOption[0]);
                } catch (FileAlreadyExistsException var11) {
                    System.out.println("FAJL POSTOJI: " + f);
                } catch (IOException var12) {
                    var12.printStackTrace();
                }

                ++brFajla;
            }
        }

        listaSlika = folder.listFiles();
        DecimalFormat var13 = new DecimalFormat();
        var13.setMaximumFractionDigits(2);

        for(i = 0; i < listaSlika.length; ++i) {
            if(listaFajlova[i].isFile()) {
                this.listaSlike.append(listaSlika[i].getName() + "            \t" + var13.format((double)((float)listaSlika[i].length() / 1024.0F)) + "KB\n");
            }
        }

    }

    private static boolean jelJPG(File filename) {
        try {
            DataInputStream e = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
            Throwable var2 = null;

            boolean var3;
            try {
                var3 = e.readInt() == -2555936;
            } catch (Throwable var13) {
                var2 = var13;
                throw var13;
            } finally {
                if(e != null) {
                    if(var2 != null) {
                        try {
                            e.close();
                        } catch (Throwable var12) {
                            var2.addSuppressed(var12);
                        }
                    } else {
                        e.close();
                    }
                }

            }

            return var3;
        } catch (Exception var15) {
            var15.printStackTrace();
            return false;
        }
    }
}

