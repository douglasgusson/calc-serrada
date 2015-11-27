package br.com.calcserrada.domain;

/**
 *
 * @author Douglas Gusson
 */
public class Parada {

    private String hrInicial;
    private String hrFinal;
    private String diferenca;

    /**
     * @return the hrInicial
     */
    public String getHrInicial() {
        return hrInicial;
    }

    /**
     * @param hrInicial the hrInicial to set
     */
    public void setHrInicial(String hrInicial) {
        this.hrInicial = hrInicial;
    }

    /**
     * @return the hrFinal
     */
    public String getHrFinal() {
        return hrFinal;
    }

    /**
     * @param hrFinal the hrFinal to set
     */
    public void setHrFinal(String hrFinal) {
        this.hrFinal = hrFinal;
    }

    /**
     * @return the diferenca
     */
    public String getDiferenca() {
        return diferenca;
    }

    public void setDiferenca(String hr1, String hr2) {
        this.diferenca = calculaHora(hr1, hr2);
    }

    private String calculaHora(String hrInicial, String hrFinal) {
        try {
            String horaFinal = hrFinal;
            String horaInicial = hrInicial;

            String horaInicialInteira = horaInicial.substring(0, 2);
            String minInicial = horaInicial.substring(3, 5);

            double x = Double.parseDouble(horaInicialInteira);
            double y = Double.parseDouble(minInicial);
            y = y / 60;
            double hInicial = x + y;

            String horaFinalInteira = horaFinal.substring(0, 2);
            String minFinal = horaFinal.substring(3, 5);

            double w = Double.parseDouble(horaFinalInteira);
            double z = Double.parseDouble(minFinal);
            z = z / 60;
            double hFinal = w + z;

            double result = 0.00;

            if (hInicial > hFinal) {
                result = Math.abs(hFinal - hInicial);
                result = 24.00 - result;
            } else if (hInicial < hFinal) {
                result = (hFinal - hInicial);
            } else if (hInicial == hFinal) {
                result = (hFinal - hInicial) + 24.00;
            }

            String resultado = "" + result;

            String hora = resultado.substring(0, resultado.indexOf("."));
            String minResultado = "0" + resultado.substring(resultado.indexOf("."));

            double h = Double.parseDouble(hora);
            double min = Math.round((Double.parseDouble(minResultado)) * 60);

            if (h < 10) {
                hora = "0" + hora;
            } else {
                hora = "" + hora;
            }

            String minutos;
            if (min < 10) {
                minutos = "0" + min;
            } else {
                minutos = "" + min;
            }

            minutos = minutos.substring(0, minutos.indexOf("."));

            String teste = hora + ":" + minutos;

            return teste;
        } catch (Exception e) {
        }
        return null;
    }
}
