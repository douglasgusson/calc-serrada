package br.com.calcserrada.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Douglas Gusson
 */
public class UtilidadesData {

    public String getDiaSemana() {
        
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        String diaSemana = "";
        switch (day) {
            case 1:
                diaSemana = "Domingo";
                break;
            case 2:
                diaSemana = "Segunda-feira";
                break;
            case 3:
                diaSemana = "Terça-feira";
                break;
            case 4:
                diaSemana = "Quarta-feira";
                break;
            case 5:
                diaSemana = "Quinta-feira";
                break;
            case 6:
                diaSemana = "Sexta-feira";
                break;
            case 7:
                diaSemana = "Sábado";
                break;
            default:
                break;
        }
        return diaSemana;
    }

    public int getDiaMes() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int diaMes = cal.get(Calendar.DAY_OF_MONTH);

        return diaMes;
    }

    public String getMes() {

        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int mes = cal.get(Calendar.MONTH);

        String mesStr = "";
        switch (mes) {
            case 0:
                mesStr = "Janeiro";
                break;
            case 1:
                mesStr = "Fevereiro";
                break;
            case 2:
                mesStr = "Março";
                break;
            case 3:
                mesStr = "Abril";
                break;
            case 4:
                mesStr = "Maio";
                break;
            case 5:
                mesStr = "Junho";
                break;
            case 6:
                mesStr = "Julho";
                break;
            case 7:
                mesStr = "Agosto";
                break;
            case 8:
                mesStr = "Setembro";
                break;
            case 9:
                mesStr = "Outubro";
                break;
            case 10:
                mesStr = "Novembro";
                break;
            case 11:
                mesStr = "Dezembro";
                break;
            default:
                break;
        }

        return mesStr;
    }

    public int getAno() {       
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int ano = cal.get(Calendar.YEAR);

        return ano;
    }

}
