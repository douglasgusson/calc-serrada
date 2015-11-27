
package br.com.calcserrada.domain;

/**
 *
 * @author Douglas Gusson
 */
public class Granalha {
    
    private int quantSaco;
    private double peso;

    /**
     * @return the quantSaco
     */
    public int getQuantSaco() {
        return quantSaco;
    }

    /**
     * @param quantSaco the quantSaco to set
     */
    public void setQuantSaco(int quantSaco) {
        this.quantSaco = quantSaco;
    }

    /**
     * @return the peso
     */
    public double getPeso() {
        return peso;
    }

    public void setPeso(int quant) {
        this.peso = quant * 25;
    }
       
}
