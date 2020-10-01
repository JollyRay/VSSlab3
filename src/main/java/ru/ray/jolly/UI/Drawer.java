package ru.ray.jolly.UI;

import javafx.util.Pair;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import ru.ray.jolly.Accountant.Functions;
import ru.ray.jolly.Accountant.Newton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;


public class Drawer {
    private Drawer(){}

    private static JFrame jFrame = getFrame();
    private static JPanel jPanelTable = new JPanel();
    private static JPanel jPanelGraph = new JPanel();
    private static JPanel jPanelInfo = new JPanel();
    private static JPanel jPanelInPut = new JPanel();
    private static JTextField text1 = new JTextField(7);
    private static JTextField text2 = new JTextField(7);
    private static JTextField text3 = new JTextField(7);
    private static JLabel answerLabel = new JLabel("");
    private static JLabel deltaLabel = new JLabel("");
    private static JLabel buildErrLabel = new JLabel("");
    private static DefaultTableModel tableModel = getTableModel();
    private static JTable table = new JTable(tableModel);
    private static JComboBox comboBox = new JComboBox(new String[]{"y=5*sin(3x)", "y=ln(5x)", "y=3x^3+1", "y=2^(x+1)", "y=2x"});
    private static int counter = 0;

    private static JFrame getFrame() {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Lab3 Kamyshnikov Vlad");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 250, dimension.height/2 - 250, 1000, 540);
        Container container = jFrame.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout (new GridBagLayout());
        return jFrame;
    }

    private static DefaultTableModel getTableModel(){
        String[] header = new String[] {"X", "Y"};
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(header);
        tableModel.setRowCount(20);
        tableModel.setColumnCount(2);
        return tableModel;
    }

    public static void createUI(){
        drawPanels();
        drawTable();
        drawInPut();
        drawInfo();

        jPanelInPut.revalidate();
    }

    private static void drawPanels(){
        GridBagConstraints constraints = new GridBagConstraints();
        Container container = jFrame.getContentPane();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy = 0;
        constraints.gridx = 0;
        jPanelTable.setMinimumSize(new Dimension(200, 355));
        jPanelTable.setMaximumSize(new Dimension(200, 355));
        jPanelTable.setLayout(new GridLayout(1, 1, 0, 0));
        container.add(jPanelTable, constraints);

        constraints.gridx = 1;
        constraints.ipady = 45;
        constraints.weightx = 0.0;
        constraints.gridheight = 2;
        jPanelGraph.setMinimumSize(new Dimension(800, 400));
        jPanelGraph.setMaximumSize(new Dimension(800, 400));
        container.add(jPanelGraph, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 45;
        constraints.weightx = 0.0;
        constraints.gridheight = 2;
        container.add(jPanelInPut, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.weightx = 20.0;
        constraints.gridheight = 1;
        container.add(jPanelInfo, constraints);

        jPanelTable.setBorder(BorderFactory.createLineBorder(Color.red));
        jPanelGraph.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jPanelInPut.setBorder(BorderFactory.createLineBorder(Color.green));
        jPanelInfo.setBorder(BorderFactory.createLineBorder(Color.blue));

    }

    private  static void drawTable(){
        JScrollPane scrollPane = new JScrollPane(table);
        jPanelTable.add(scrollPane);
    }

    private static void drawInPut(){
        jPanelInPut.setLayout(new GridLayout(4, 3, 0, 0));
        JLabel label1 = new JLabel("X:");
        JLabel label2 = new JLabel("Y:");
        JButton jButtonAdd = new JButton(new Add());
        JButton jButtonEdit = new JButton(new Edit());
        JButton jButtonDelete = new JButton(new Delete());
        JButton jButtonCount = new JButton(new CountFun());
        JButton jButtonBuild = new JButton(new BuilderGraph());
        comboBox.addActionListener(new CombatLis());

        jPanelInPut.add(label1);
        jPanelInPut.add(text1);
        jPanelInPut.add(jButtonAdd);
        jPanelInPut.add(label2);
        jPanelInPut.add(text2);
        jPanelInPut.add(jButtonEdit);
        jPanelInPut.add(jButtonDelete);
        jPanelInPut.add(jButtonCount);
        jPanelInPut.add(jButtonBuild);
        jPanelInPut.add(buildErrLabel);
        jPanelInPut.add(comboBox);
    }

    private static void preDrawGraph(double min, double max,  LinkedList<Pair<Double, Double>> list){
        XYDataset dataset = GraphMaker.getDataset(min, max, comboBox.getSelectedIndex(), list);
        drawGraph(dataset);
    }

    private static void preDrawGraph(double min, double max,  Pair<Double, Double> paerDD){
        XYDataset dataset = GraphMaker.getDataset(min, max, comboBox.getSelectedIndex(), paerDD);
        drawGraph(dataset);
    }

    private static void drawGraph(XYDataset dataset){
        JFreeChart chart = GraphMaker.getChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        if (jPanelGraph.getComponentCount() > 0)
            jPanelGraph.remove(0);
        jPanelGraph.add(chartPanel);
        jPanelGraph.revalidate();

    }

    public static void drawInfo(){
        jPanelInfo.setLayout(new GridLayout(2, 3, 0, 0));
        JLabel label1 = new JLabel("Точечка интерполяции X=");
        JButton jButtonCal =  new JButton(new CountSoloInter());
        jPanelInfo.add(label1);
        jPanelInfo.add(text3);
        jPanelInfo.add(answerLabel);
        jPanelInfo.add(new JLabel("Дельат = "));
        jPanelInfo.add(deltaLabel);
        jPanelInfo.add(jButtonCal);

    }

    private  static class BuilderGraph extends AbstractAction {
        public BuilderGraph() {
            putValue(AbstractAction.NAME, "Построить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LinkedList<Pair<Double, Double>> list = new LinkedList<Pair<Double, Double>>();
            try {
                double min = Double.parseDouble((String) tableModel.getValueAt(0, 0));
                double max = Double.parseDouble((String) tableModel.getValueAt(0, 0));
                for (int i = 0; i < counter; i++) {
                    double x = Double.parseDouble((String) tableModel.getValueAt(i, 0));
                    if (x < min)
                        min = x;
                    if (x > max)
                        max = x;
                    double y = Double.parseDouble((String) tableModel.getValueAt(i, 1));
                    if (comboBox.getSelectedIndex() == 1 && min <= 0)
                        throw new Exception("Delete inf or Nan");
                    list.add(new Pair<Double, Double>(x, y));
                }
                Newton.fillDiff(list);
                preDrawGraph(min, max, list);
                buildErrLabel.setText("");
            } catch (Exception ex){
                buildErrLabel.setText(ex.getMessage());
            }
        }
    }

    private  static class CountSoloInter extends AbstractAction {
        public CountSoloInter() {
            putValue(AbstractAction.NAME, "Посчитать");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LinkedList<Pair<Double, Double>> list = new LinkedList<Pair<Double, Double>>();
            try {
                double valueX = Double.parseDouble(text3.getText());
                double min = valueX;
                double max = valueX;
                for (int i = 0; i < counter; i++) {
                    double x = Double.parseDouble((String) tableModel.getValueAt(i, 0));
                    if (x < min)
                        min = x;
                    if (x > max)
                        max = x;
                    double y = Double.parseDouble((String) tableModel.getValueAt(i, 1));
                    if (comboBox.getSelectedIndex() == 1 && x <=0)
                        throw new Exception("Delete invalid value");
                   list.add(new Pair<Double, Double>(x , y));
                }
                Newton.fillDiff(list);

                double answer = Newton.getPol(valueX, list),
                        real = Functions.valueFun(comboBox.getSelectedIndex(), valueX);
                answerLabel.setText(answer + "");
                deltaLabel.setText(Math.abs(answer - real) + "");
                preDrawGraph(min, max, new Pair<Double, Double>(valueX, answer));
            } catch (Exception ex){
                answerLabel.setText(ex.getMessage());
                deltaLabel.setText("");
            }
        }
    }

    private  static class CountFun extends AbstractAction{
        public CountFun() {
            putValue(AbstractAction.NAME, "Посчитать");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = comboBox.getSelectedIndex();
            for (int i = 0; i < counter; i++){
                double x = Double.parseDouble((String) tableModel.getValueAt(i, 0));
                double y = Functions.valueFun(id, x);
                tableModel.setValueAt(y + "", i, 1);
            }
        }
    }

    private static class Delete extends AbstractAction{
        Delete(){
            putValue(AbstractAction.NAME, "Удалить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = table.getSelectedRow();
            if (id !=-1) {
                tableModel.removeRow(id);
                if (counter > 0)
                    counter--;
            }
        }
    }

    private static class Edit extends AbstractAction {
        Edit() {
            putValue(AbstractAction.NAME, "Измена");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = table.getSelectedRow();
            try{
                if (id !=-1 && !tableModel.getValueAt(id , 0).equals("")) {
                    Double.parseDouble(text2.getText());
                    tableModel.setValueAt(text2.getText(), id, 1);
                }
            } catch (Exception ignored){}
            finally {
                text2.setText("");
            }
        }
    }

    private static class Add extends AbstractAction{
        Add(){
            putValue(AbstractAction.NAME, "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String valueX = text1.getText().trim();
            try{
                double x2 = Double.parseDouble(valueX);
                boolean flag = false;
                for (int i = 0; i < counter; i++){
                    double x1 = Double.parseDouble((String) tableModel.getValueAt(i, 0));
                    flag = flag || Math.abs(x1-x2) < 1E-15;
                }
                if (!flag) {
                    if (counter < tableModel.getRowCount()) {
                        tableModel.setValueAt(Functions.valueFun(comboBox.getSelectedIndex(), x2) + "", counter, 1);
                        tableModel.setValueAt(valueX, counter++, 0);
                    } else {
                        String[] row = {valueX, ""};
                        tableModel.addRow(row);
                        counter++;
                    }
                }

            } catch (NumberFormatException ignored){}
            finally {
                text1.setText("");
                text2.setText("");
            }
        }
    }

    private static class CombatLis extends AbstractAction{
        public CombatLis() { }

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = comboBox.getSelectedIndex();
            for (int i =0; i < counter; i++){
                double x = Double.parseDouble((String) tableModel.getValueAt(i, 0));
                tableModel.setValueAt(Functions.valueFun(id, x)+ "", i,1);
            }
        }
    }
}
