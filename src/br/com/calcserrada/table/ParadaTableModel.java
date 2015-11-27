
package br.com.calcserrada.table;

import br.com.calcserrada.domain.Parada;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class ParadaTableModel extends AbstractTableModel {

    private static final int COL_INICIAL = 0;
    private static final int COL_FINAL = 1;
    private static final int COL_DIFERENCA = 2;

    private List<Parada> dados;

    public ParadaTableModel(List<Parada> dados) {
        this.dados = dados;
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Parada parada = dados.get(rowIndex);
        if (columnIndex == COL_INICIAL) {
            return parada.getHrInicial();
        } else if (columnIndex == COL_FINAL) {
            return parada.getHrFinal();
        } else if (columnIndex == COL_DIFERENCA) {
            return parada.getDiferenca();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_INICIAL:
                coluna = "Inicial";
                break;
            case COL_FINAL:
                coluna = "Final";
                break;
            case COL_DIFERENCA:
                coluna = "Diferença";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_INICIAL) {
            return String.class;
        } else if (columnIndex == COL_FINAL) {
            return String.class;
        } else if (columnIndex == COL_DIFERENCA) {
            return String.class;
        }
        return null;
    }

    public Parada get(int row) {
        return dados.get(row);
    }

}
