
package br.com.calcserrada.table;

import br.com.calcserrada.domain.Granalha;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class GranalhaTableModel extends AbstractTableModel {
    
    /**
     * Atributos do hospede: Código, Nome, Endereço, Número, Bairro, CEP,
     * Cidade, UF, Sexo, Data de Nascimento, CPF, RG, Telefone, Celular, E-mail
     */
    private static final int COL_QUANT = 0;
    private static final int COL_PESO = 1;

    private List<Granalha> dados;

    public GranalhaTableModel(List<Granalha> dados) {
        this.dados = dados;        
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Granalha granalha = dados.get(rowIndex);
        if (columnIndex == COL_QUANT) {
            return granalha.getQuantSaco();
        } else if (columnIndex == COL_PESO) {
            return new DecimalFormat("##0.00").format(granalha.getPeso());       
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_QUANT:
                coluna = "Quant.";
                break;
            case COL_PESO:
                coluna = "Peso (Kg)";
                break;            
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_QUANT) {
            return int.class;
        } else if (columnIndex == COL_PESO) {
            return double.class;            
        }
        return null;
    }

    public Granalha get(int row) {
        return dados.get(row);
    }
    
}
