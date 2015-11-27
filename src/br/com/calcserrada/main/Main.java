
package br.com.calcserrada.main;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import br.com.calcserrada.views.FrmPrincipal;

/**
 *
 * @author Douglas Gusson
 */
public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
            } catch (UnsupportedLookAndFeelException | ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            new FrmPrincipal().setVisible(true);
        });

    }
}
