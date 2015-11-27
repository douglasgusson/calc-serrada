package br.com.calcserrada.views;

import br.com.calcserrada.domain.Granalha;
import br.com.calcserrada.domain.Parada;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import br.com.calcserrada.table.GranalhaTableModel;
import br.com.calcserrada.table.ParadaTableModel;
import br.com.calcserrada.util.MascaraNumerica;
import br.com.calcserrada.util.UtilidadesData;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 *
 * @author Douglas Gusson
 */
public class FrmPrincipal extends javax.swing.JFrame {

    List<Granalha> listaGranalha = new ArrayList();
    List<Parada> listaParadas = new ArrayList();

    public FrmPrincipal() {
        initComponents();
        initListeners();
        initialize();
    }

    private void initListeners() {

        FocusAdapter calcBloco01 = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calcularBloco01();
            }
        };

        tfCompBr01.addFocusListener(calcBloco01);
        tfAltBr01.addFocusListener(calcBloco01);
        tfQtdCh01.addFocusListener(calcBloco01);

        FocusAdapter calcBloco02 = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calcularBloco02();
            }
        };

        tfCompBr02.addFocusListener(calcBloco02);
        tfAltBr02.addFocusListener(calcBloco02);
        tfQtdCh02.addFocusListener(calcBloco02);

        KeyAdapter somenteNumeros = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                String caracteres = "0123456789,";
                if (!caracteres.contains(evt.getKeyChar() + "")) {
                    evt.consume();
                }
            }
        };

        tfCompBr01.addKeyListener(somenteNumeros);
        tfAltBr01.addKeyListener(somenteNumeros);
        tfQtdCh01.addKeyListener(somenteNumeros);

        tfCompBr02.addKeyListener(somenteNumeros);
        tfAltBr02.addKeyListener(somenteNumeros);
        tfQtdCh02.addKeyListener(somenteNumeros);

        tfQuantGranalha.addKeyListener(somenteNumeros);

        tfQtdCal35.addKeyListener(somenteNumeros);
        tfQtdCal20.addKeyListener(somenteNumeros);

        FocusAdapter calculaCal = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculaCal();
            }
        };

        tfQtdCal35.addFocusListener(calculaCal);
        tfQtdCal20.addFocusListener(calculaCal);

        tfQtdCal35.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selecionarValorCampo(tfQtdCal35);
            }
        });

        tfQtdCal20.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selecionarValorCampo(tfQtdCal20);
            }
        });

        tfHrInicial.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selecionarValorCampo(tfHrInicial);
            }
        });

        tfHrFinal.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selecionarValorCampo(tfHrFinal);
            }
        });

        tfHrFinal.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                adicionarParada();
            }
        });

        tfHrInicial.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String hora = tfHrInicial.getText();
                if (verificaHoraDigitada(hora)) {
                    tfHrInicial.setBorder(new LineBorder(Color.GRAY));
                } else {
                    tfHrInicial.setBorder(new LineBorder(Color.RED));
                }
            }
        });

        tfHrFinal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String hora = tfHrFinal.getText();
                if (verificaHoraDigitada(hora)) {
                    tfHrFinal.setBorder(new LineBorder(Color.GRAY));
                } else {
                    tfHrFinal.setBorder(new LineBorder(Color.RED));
                }
            }
        });

        tfQuantGranalha.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                adicionarGranalha();
            }
        });

        btNovo.addActionListener((ActionEvent e) -> {
            novo();
        });

        btCancelar.addActionListener((ActionEvent e) -> {
            initialize();
        });

        btSair.addActionListener((ActionEvent e) -> {
            FrmPrincipal.this.dispose();
        });

    }

    private void selecionarValorCampo(JTextField field) {
        field.selectAll();
    }

    private void adicionarGranalha() {
        try {
            int quant = Integer.parseInt(tfQuantGranalha.getText());

            Granalha g = new Granalha();
            g.setQuantSaco(quant);
            g.setPeso(quant);
            listaGranalha.add(g);

            calcularGranalha();

            preencherTabelaGranalha(listaGranalha);

            tfQuantGranalha.setText("");
            tfQuantGranalha.requestFocus();
        } catch (Exception e) {
        }
    }

    private void adicionarParada() {

        tfHrFinal.setBorder(new LineBorder(Color.GRAY));

        String hr1 = tfHrInicial.getText();
        String hr2 = tfHrFinal.getText();

        Parada p = new Parada();
        p.setHrInicial(hr1);
        p.setHrFinal(hr2);
        p.setDiferenca(hr1, hr2);
        listaParadas.add(p);

        preencherTabelaParada(listaParadas);

        tfTotalDiferenca.setText(somaHora());

        tfHrInicial.setText("");
        tfHrFinal.setText("");

        tfHrInicial.requestFocus();

    }

    private void calcularGranalha() {
        try {
            double resultKg = 0;
            int qtdSacos = 0;

            for (Granalha obj : listaGranalha) {
                resultKg += obj.getPeso();
                qtdSacos += obj.getQuantSaco();
            }

            double metragem01 = Double.parseDouble(tfMetrBr01.getText().replace(",", "."));
            double metragem02 = Double.parseDouble(tfMetrBr02.getText().replace(",", "."));

            double resultKgM2 = (resultKg / (metragem01 + metragem02));

            tfTotalGranalha1.setText(formatarDecimal(resultKg));
            tfGranKgM2.setText(formatarDecimal(resultKgM2));

            if (qtdSacos == 0) {
                this.lbSacos.setText("");
            } else if (qtdSacos == 1) {
                this.lbSacos.setText(qtdSacos + " Saco");
            } else {
                this.lbSacos.setText(qtdSacos + " Sacos");
            }
        } catch (NumberFormatException ex) {
        }
    }

    private void calculaCal() {

        if (tfQtdCal35.getText().isEmpty()) {

            int qtdSacos20 = Integer.parseInt(tfQtdCal20.getText());
            int resultKg = (qtdSacos20 * 20);
            tfTotalCal.setText(String.valueOf(resultKg));
            double metragem01 = Double.parseDouble(tfMetrBr01.getText().replace(",", "."));
            double metragem02 = Double.parseDouble(tfMetrBr02.getText().replace(",", "."));
            double resultKgM2 = (resultKg / (metragem01 + metragem02));
            tfCalKgM2.setText(formatarDecimal(resultKgM2));

        } else if (tfQtdCal20.getText().isEmpty()) {

            int qtdSacos35 = Integer.parseInt(tfQtdCal35.getText());
            int resultKg = (qtdSacos35 * 35);
            tfTotalCal.setText(String.valueOf(resultKg));
            double metragem01 = Double.parseDouble(tfMetrBr01.getText().replace(",", "."));
            double metragem02 = Double.parseDouble(tfMetrBr02.getText().replace(",", "."));
            double resultKgM2 = (resultKg / (metragem01 + metragem02));
            tfCalKgM2.setText(formatarDecimal(resultKgM2));

        } else if (tfQtdCal35.getText().isEmpty() && tfQtdCal20.getText().isEmpty()) {

            this.tfTotalCal.setText("0");
            this.tfCalKgM2.setText("0,00");

        } else {

            int qtdSacos35 = Integer.parseInt(tfQtdCal35.getText());
            int qtdSacos20 = Integer.parseInt(tfQtdCal20.getText());
            int resultKg = (qtdSacos35 * 35) + (qtdSacos20 * 20);

            double metragem01 = Double.parseDouble(tfMetrBr01.getText().replace(",", "."));
            double metragem02 = Double.parseDouble(tfMetrBr02.getText().replace(",", "."));
            double resultKgM2 = (resultKg / (metragem01 + metragem02));

            tfTotalCal.setText(String.valueOf(resultKg));
            tfCalKgM2.setText(formatarDecimal(resultKgM2));

        }
    }

    private void calcularBloco01() {
        try {
            double compBr = Double.parseDouble(tfCompBr01.getText().replace(",", "."));
            double altBr = Double.parseDouble(tfAltBr01.getText().replace(",", "."));
            int quantCh = Integer.parseInt(tfQtdCh01.getText());

            double compLiq = (compBr - 0.05);
            double altLiq = (altBr - 0.05);

            double metrBr = ((compBr * altBr) * quantCh);
            double metrLiq = ((compLiq * altLiq) * quantCh);

            tfCompLiq01.setText(formatarDecimal(compLiq));
            tfAltLiq01.setText(formatarDecimal(altLiq));

            tfMetrBr01.setText(formatarDecimal(metrBr));
            tfMetrLiq01.setText(formatarDecimal(metrLiq));

            totalChapas();
            calcularGranalha();
            calculaCal();

        } catch (NumberFormatException ex) {
        }
    }

    private void calcularBloco02() {
        try {
            double compBr = Double.parseDouble(tfCompBr02.getText().replace(",", "."));
            double altBr = Double.parseDouble(tfAltBr02.getText().replace(",", "."));
            int quantCh = Integer.parseInt(tfQtdCh02.getText());

            double compLiq = (compBr - 0.05);
            double altLiq = (altBr - 0.05);

            double metrBr = ((compBr * altBr) * quantCh);
            double metrLiq = ((compLiq * altLiq) * quantCh);

            tfCompLiq02.setText(formatarDecimal(compLiq));
            tfAltLiq02.setText(formatarDecimal(altLiq));

            tfMetrBr02.setText(formatarDecimal(metrBr));
            tfMetrLiq02.setText(formatarDecimal(metrLiq));

            totalChapas();
            calcularGranalha();
            calculaCal();

        } catch (NumberFormatException ex) {
        }
    }

    private void totalChapas() {
        try {
            int qtd1, qtd2;

            if (!tfQtdCh01.getText().isEmpty()) {
                qtd1 = Integer.parseInt(tfQtdCh01.getText());
            } else {
                qtd1 = 0;
            }

            if (!tfQtdCh02.getText().isEmpty()) {
                qtd2 = Integer.parseInt(tfQtdCh02.getText());
            } else {
                qtd2 = 0;
            }

            int totalCh = qtd1 + qtd2;

            tfTotalCh.setText("" + totalCh);

        } catch (Exception e) {
        }
    }

    private void preencherTabelaGranalha(List<Granalha> dados) {
        List lista = new ArrayList();
        lista = dados;
        if (lista != null) {
            tbGranalha.setModel(new GranalhaTableModel(lista));
        }
    }

    private void preencherTabelaParada(List<Parada> dados) {
        List lista = new ArrayList();
        lista = dados;
        if (lista != null) {
            tbParada.setModel(new ParadaTableModel(lista));
        }
    }

    private void novo() {

        this.btCancelar.setEnabled(true);
        this.btNovo.setEnabled(false);
        this.btSair.setEnabled(false);

        this.tfCompBr01.requestFocus();

        this.tfQuantGranalha.setEnabled(true);

        this.tfCompBr01.setEnabled(true);
        this.tfCompBr02.setEnabled(true);
        this.tfAltBr01.setEnabled(true);
        this.tfAltBr02.setEnabled(true);
        this.tfQtdCh01.setEnabled(true);
        this.tfQtdCh02.setEnabled(true);
        this.tfQtdCal35.setEnabled(true);
        this.tfQtdCal20.setEnabled(true);
        this.tfHrInicial.setEnabled(true);
        this.tfHrFinal.setEnabled(true);

        this.tfCompLiq01.setText("0,00");
        this.tfCompLiq02.setText("0,00");
        this.tfAltLiq01.setText("0,00");
        this.tfAltLiq02.setText("0,00");
        this.tfMetrBr01.setText("0,00");
        this.tfMetrBr02.setText("0,00");
        this.tfMetrLiq01.setText("0,00");
        this.tfMetrLiq02.setText("0,00");
        this.tfTotalCal.setText("0");
        this.tfCalKgM2.setText("0,00");
        this.tfQtdCal35.setText("0");
        this.tfQtdCal20.setText("0");
    }

    private boolean verificaHoraDigitada(String str) {
        try {
            int index = str.indexOf(":");

            double hr = Double.parseDouble(str.substring(0, index));
            double min = Double.parseDouble(str.substring(index + 1));

            return !((hr > 23) || (min > 59));

        } catch (Exception ex) {
        }
        return false;
    }

    private void mascaraCampos() {
        tfCompBr01.addKeyListener(new MascaraNumerica(tfCompBr01, 3, 2));
        tfCompBr02.addKeyListener(new MascaraNumerica(tfCompBr02, 3, 2));
        tfAltBr01.addKeyListener(new MascaraNumerica(tfAltBr01, 3, 2));
        tfAltBr02.addKeyListener(new MascaraNumerica(tfAltBr02, 3, 2));
    }

    private void habilitarEnter() {
        //habilitar o ENTER para mudar o foco dos campos
        Set<AWTKeyStroke> fkeys = new HashSet<>(
                KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .getDefaultFocusTraversalKeys(
                        KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));

        fkeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .setDefaultFocusTraversalKeys(
                        KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, fkeys);

        //habilitar o SHIFT + ENTER para voltar o foco para o campo
        Set<AWTKeyStroke> bKeys = new HashSet<>(
                KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .getDefaultFocusTraversalKeys(
                        KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));

        bKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK));

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .setDefaultFocusTraversalKeys(
                        KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, bKeys);
    }

    private void initialize() {
        setIcon();
        mascaraCampos();
        preencherTabelaGranalha(listaGranalha);
        menuContextoTabelaGranalha();
        menuContextoTabelaParada();
        habilitarEnter();

        UtilidadesData ud = new UtilidadesData();
        this.lbHora.setText(ud.getDiaSemana() + ", "
                + ud.getDiaMes() + " de " + ud.getMes() + " de " + ud.getAno() + ".");

        this.lbSacos.setText("");

        this.tfQuantGranalha.setEnabled(false);

        this.tfHrFinal.setBorder(new LineBorder(Color.GRAY));
        this.tfHrInicial.setBorder(new LineBorder(Color.GRAY));

        this.tfCompBr01.setEnabled(false);
        this.tfCompBr02.setEnabled(false);
        this.tfAltBr01.setEnabled(false);
        this.tfAltBr02.setEnabled(false);
        this.tfQtdCh01.setEnabled(false);
        this.tfQtdCh02.setEnabled(false);
        this.tfQtdCal35.setEnabled(false);
        this.tfQtdCal20.setEnabled(false);
        this.tfHrInicial.setEnabled(false);
        this.tfHrFinal.setEnabled(false);

        this.btCancelar.setEnabled(false);
        this.btNovo.setEnabled(true);
        this.btSair.setEnabled(true);

        this.tfCompBr01.setText("");
        this.tfCompBr02.setText("");
        this.tfAltBr01.setText("");
        this.tfAltBr02.setText("");
        this.tfQtdCh01.setText("");
        this.tfQtdCh02.setText("");
        this.tfTotalCh.setText("");
        this.tfCompLiq01.setText("");
        this.tfCompLiq02.setText("");
        this.tfAltLiq01.setText("");
        this.tfAltLiq02.setText("");
        this.tfMetrBr01.setText("");
        this.tfMetrBr02.setText("");
        this.tfMetrLiq01.setText("");
        this.tfMetrLiq02.setText("");
        this.tfTotalCal.setText("");
        this.tfCalKgM2.setText("");
        this.tfQtdCal35.setText("");
        this.tfQtdCal20.setText("");
        this.lbSacos.setText("");

        this.tfTotalGranalha1.setText("");
        this.tfGranKgM2.setText("");

        this.tfHrInicial.setText("");
        this.tfHrFinal.setText("");
        this.tfTotalDiferenca.setText("");

        listaGranalha.removeAll(listaGranalha);
        preencherTabelaGranalha(listaGranalha);

        listaParadas.removeAll(listaParadas);
        preencherTabelaParada(listaParadas);

    }

    private void menuContextoTabelaGranalha() {

        JPopupMenu popUpMenu = new JPopupMenu();

        JMenuItem itemExcluir = new JMenuItem("Excluir");

        itemExcluir.addActionListener((ActionEvent e) -> {
            int index = tbGranalha.getSelectedRow();
            if (index != -1) {
                GranalhaTableModel obj = new GranalhaTableModel(listaGranalha);
                Granalha g = obj.get(index);
                listaGranalha.remove(g);
                tbGranalha.setModel(new GranalhaTableModel(listaGranalha));
                calcularGranalha();
            }
        });

        popUpMenu.add(itemExcluir);

        tbGranalha.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e
                    ) {
                        // Se o botão direito do mouse foi pressionado
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            // Exibe o popup menu na posição do mouse.
                            popUpMenu.show(tbGranalha, e.getX(), e.getY());
                        }
                    }
                });
    }

    private void menuContextoTabelaParada() {

        JPopupMenu popUpMenu = new JPopupMenu();

        JMenuItem itemExcluir = new JMenuItem("Excluir");

        itemExcluir.addActionListener((ActionEvent e) -> {
            int index = tbParada.getSelectedRow();
            if (index != -1) {
                ParadaTableModel obj = new ParadaTableModel(listaParadas);
                Parada p = obj.get(index);
                listaParadas.remove(p);
                tbParada.setModel(new ParadaTableModel(listaParadas));
                tfTotalDiferenca.setText(somaHora());
            }
        });

        popUpMenu.add(itemExcluir);

        tbParada.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e
                    ) {
                        // Se o botão direito do mouse foi pressionado
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            // Exibe o popup menu na posição do mouse.
                            popUpMenu.show(tbParada, e.getX(), e.getY());
                        }
                    }
                });
    }

    private String somaHora() {

        double soma = 0.00;

        for (Parada p : listaParadas) {
            String diferenca = p.getDiferenca();
            String hora = diferenca.substring(0, diferenca.indexOf(":"));
            String minutos = diferenca.substring(diferenca.indexOf(":") + 1);
            double x = Double.parseDouble(hora);
            double y = Double.parseDouble(minutos);
            y = y / 60;
            soma = soma + (x + y);
        }

        String resultado = "" + soma;

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
        String str = hora + ":" + minutos;

        return str;

    }

    private String formatarDecimal(double valor) {
        DecimalFormat df = new DecimalFormat("##0.00");
        String saida = df.format(valor);
        return saida;
    }

    private void setIcon() {
        this.setIconImage(
                new ImageIcon(getClass().getResource(
                                "/br/com/calcserrada/img/icon.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        lbHora = new javax.swing.JLabel();
        btCancelar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tfCompBr02 = new javax.swing.JTextField();
        tfAltBr02 = new javax.swing.JTextField();
        tfQtdCh02 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tfCompBr01 = new javax.swing.JTextField();
        tfQtdCh01 = new javax.swing.JTextField();
        tfAltBr01 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfTotalCh = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tfCompLiq01 = new javax.swing.JTextField();
        tfCompLiq02 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tfAltLiq01 = new javax.swing.JTextField();
        tfAltLiq02 = new javax.swing.JTextField();
        tfMetrLiq02 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfMetrLiq01 = new javax.swing.JTextField();
        tfMetrBr01 = new javax.swing.JTextField();
        tfMetrBr02 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        tfQtdCal35 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        tfQtdCal20 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        tfTotalCal = new javax.swing.JTextField();
        tfCalKgM2 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        tfQuantGranalha = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGranalha = new javax.swing.JTable();
        lbSacos = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        tfTotalGranalha1 = new javax.swing.JTextField();
        tfGranKgM2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbParada = new javax.swing.JTable();
        tfHrInicial = new javax.swing.JFormattedTextField();
        tfHrFinal = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        tfTotalDiferenca = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculadora de Serradas");
        setResizable(false);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        lbHora.setText("Data...");
        lbHora.setToolTipText("");

        btCancelar.setMnemonic('C');
        btCancelar.setText("Cancelar");

        btSair.setMnemonic('S');
        btSair.setText("Sair");

        btNovo.setMnemonic('N');
        btNovo.setText("Novo");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSair)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSair)
                    .addComponent(btCancelar)
                    .addComponent(lbHora)
                    .addComponent(btNovo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Medidas"));
        jPanel1.setToolTipText("");

        jLabel5.setText("Bloco [2]:");

        jLabel6.setText("x");

        jLabel7.setText("x");

        jLabel8.setText("x");

        jLabel9.setText("x");

        jLabel1.setText("Bloco [1]:");

        jLabel2.setText("Comp. Br");

        jLabel3.setText("Alt. Br");

        jLabel4.setText("Qtd. CH");

        tfTotalCh.setEditable(false);
        tfTotalCh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfTotalCh.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfTotalCh.setEnabled(false);

        jLabel18.setText("Total de Ch:");

        jLabel16.setText("Comp. Liq");

        tfCompLiq01.setEditable(false);
        tfCompLiq01.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfCompLiq01.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfCompLiq01.setEnabled(false);

        tfCompLiq02.setEditable(false);
        tfCompLiq02.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfCompLiq02.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfCompLiq02.setEnabled(false);

        jLabel11.setText("x");

        jLabel12.setText("x");

        jLabel17.setText("Alt. Liq");

        tfAltLiq01.setEditable(false);
        tfAltLiq01.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfAltLiq01.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfAltLiq01.setEnabled(false);

        tfAltLiq02.setEditable(false);
        tfAltLiq02.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfAltLiq02.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfAltLiq02.setEnabled(false);

        tfMetrLiq02.setEditable(false);
        tfMetrLiq02.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfMetrLiq02.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfMetrLiq02.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        tfMetrLiq02.setEnabled(false);

        jLabel13.setText("Metr. Br (m²)");

        tfMetrLiq01.setEditable(false);
        tfMetrLiq01.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfMetrLiq01.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfMetrLiq01.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        tfMetrLiq01.setEnabled(false);

        tfMetrBr01.setEditable(false);
        tfMetrBr01.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfMetrBr01.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfMetrBr01.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfMetrBr01.setEnabled(false);

        tfMetrBr02.setEditable(false);
        tfMetrBr02.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfMetrBr02.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfMetrBr02.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfMetrBr02.setEnabled(false);

        jLabel14.setText("Metr. Liq (m²)");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(tfCompBr01)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfCompBr02, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfAltBr02, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9))
                    .addComponent(jLabel18)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfAltBr01, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfQtdCh02)
                    .addComponent(jLabel4)
                    .addComponent(tfQtdCh01)
                    .addComponent(tfTotalCh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfMetrBr02)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfMetrBr01, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfCompLiq02, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(tfCompLiq01, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfAltLiq01, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(tfAltLiq02, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfMetrLiq01)
                    .addComponent(tfMetrLiq02, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel17))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(tfCompLiq01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tfCompLiq02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel12)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(26, 26, 26)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(tfAltLiq01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tfAltLiq02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfMetrLiq01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfMetrLiq02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(24, 24, 24))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfCompBr01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tfCompBr02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(tfAltBr01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(tfQtdCh01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(tfAltBr02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(tfQtdCh02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfMetrBr01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfMetrBr02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfTotalCh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cal"));

        jLabel22.setText("Qtd. (35 kg)");

        jLabel23.setText("Qtd. (20 kg)");

        jLabel29.setText("kg/m²");

        jLabel28.setText("Total (kg)");

        tfTotalCal.setEditable(false);
        tfTotalCal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfTotalCal.setDisabledTextColor(java.awt.Color.black);
        tfTotalCal.setEnabled(false);

        tfCalKgM2.setEditable(false);
        tfCalKgM2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfCalKgM2.setDisabledTextColor(java.awt.Color.black);
        tfCalKgM2.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                            .addComponent(tfQtdCal35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tfQtdCal20)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(tfTotalCal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfCalKgM2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel29)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfQtdCal35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfQtdCal20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTotalCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCalKgM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Granalha"));

        tbGranalha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbGranalha.setFocusable(false);
        tbGranalha.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbGranalha.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbGranalha);

        lbSacos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbSacos.setText("quant. sacos");

        jLabel26.setText("Total (kg)");

        jLabel27.setText("kg/m²");

        tfTotalGranalha1.setEditable(false);
        tfTotalGranalha1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfTotalGranalha1.setDisabledTextColor(java.awt.Color.black);
        tfTotalGranalha1.setEnabled(false);

        tfGranKgM2.setEditable(false);
        tfGranKgM2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfGranKgM2.setDisabledTextColor(java.awt.Color.black);
        tfGranKgM2.setEnabled(false);

        jLabel20.setText("Qtd. Sacos:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(tfTotalGranalha1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(tfGranKgM2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfQuantGranalha))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lbSacos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfQuantGranalha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(lbSacos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfGranKgM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTotalGranalha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Paradas"));

        tbParada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbParada.setFocusable(false);
        tbParada.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbParada);

        try {
            tfHrInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            tfHrFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel21.setText("Início:");

        jLabel24.setText("Fim:");

        tfTotalDiferenca.setEditable(false);
        tfTotalDiferenca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfTotalDiferenca.setDisabledTextColor(java.awt.Color.black);
        tfTotalDiferenca.setEnabled(false);

        jLabel10.setText("Total paradas:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTotalDiferenca, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfHrInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfHrFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfHrInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfHrFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(jLabel24)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTotalDiferenca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbHora;
    private javax.swing.JLabel lbSacos;
    private javax.swing.JTable tbGranalha;
    private javax.swing.JTable tbParada;
    private javax.swing.JTextField tfAltBr01;
    private javax.swing.JTextField tfAltBr02;
    private javax.swing.JTextField tfAltLiq01;
    private javax.swing.JTextField tfAltLiq02;
    private javax.swing.JTextField tfCalKgM2;
    private javax.swing.JTextField tfCompBr01;
    private javax.swing.JTextField tfCompBr02;
    private javax.swing.JTextField tfCompLiq01;
    private javax.swing.JTextField tfCompLiq02;
    private javax.swing.JTextField tfGranKgM2;
    private javax.swing.JFormattedTextField tfHrFinal;
    private javax.swing.JFormattedTextField tfHrInicial;
    private javax.swing.JTextField tfMetrBr01;
    private javax.swing.JTextField tfMetrBr02;
    private javax.swing.JTextField tfMetrLiq01;
    private javax.swing.JTextField tfMetrLiq02;
    private javax.swing.JTextField tfQtdCal20;
    private javax.swing.JTextField tfQtdCal35;
    private javax.swing.JTextField tfQtdCh01;
    private javax.swing.JTextField tfQtdCh02;
    private javax.swing.JTextField tfQuantGranalha;
    private javax.swing.JTextField tfTotalCal;
    private javax.swing.JTextField tfTotalCh;
    private javax.swing.JTextField tfTotalDiferenca;
    private javax.swing.JTextField tfTotalGranalha1;
    // End of variables declaration//GEN-END:variables
}
