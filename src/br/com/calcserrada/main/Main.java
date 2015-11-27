
package br.com.calcserrada.main;

import br.com.calcserrada.views.FrmPrincipal;

/**
 *
 * @author Douglas Gusson
 */
public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FrmPrincipal().setVisible(true);
        });

    }
}
