import java.awt.*;
import java.awt.event.*;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class UserInterface extends JFrame {
	
JPanel 
mainPanel = new JPanel();
JLabel 
regionLabel, dateLabel, multiLabel, 
whoClientsLabel, ageLabel1, ageLabel2, ageLabel3,
ageLabel4, chooseLabel, sumLabel, extrasLabel,
promoLabel, resultLabel, expireLabel, priceLabel;
JComboBox 
regionsBox, sumBox;
JList 
extrasList;
DefaultComboBoxModel
regionsBoxModel, sumBoxModel;
DefaultListModel
extrasListModel;
JTextField
dataStartField, dataEndField, ageField1,
ageField2, ageField3, ageField4, promoField, 
expireField, priceField;
JCheckBox
multiCheck;
JButton
calcButton;

final String[] Regions = new String[] 
{"Шенгенская зона","США","РФ и СНГ","Остальной мир"};
final String[] Sums = new String[] 
{"35 000 $","60 000 $","120 000 $"};
final String[] Extras = new String[] 
{"Защита багажа", "Спортивный пакет",
    "Личный адвокат","Особый случай", "Отмена поездки"};

public UserInterface() {
	
super("Страховой калькулятор путешественников v 0.0");
this.setBounds(0,0,410,650);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
this.setResizable(false);

regionLabel = new JLabel("Регион действия полиса");
dateLabel = new JLabel("Даты поездки");
multiLabel = new JLabel("Многократный полис");
whoClientsLabel = new JLabel("Кто будет застрахован");
ageLabel1 = new JLabel("4-60 лет");
ageLabel2 = new JLabel("0-3 лет");
ageLabel3 = new JLabel("61-70 лет");
ageLabel4 = new JLabel("71-80 лет");
chooseLabel = new JLabel("Выбор условий");
sumLabel = new JLabel("Сумма страховой выплаты");
extrasLabel = new JLabel("Дополнительно");
promoLabel = new JLabel("Промо-код");
resultLabel = new JLabel("Итог");
expireLabel = new JLabel("Срок действия");
priceLabel = new JLabel("Стоимость");

regionsBoxModel = new DefaultComboBoxModel();
for (int i = 0; i < Regions.length; i++) 
	regionsBoxModel.addElement(Regions[i]);
sumBoxModel = new DefaultComboBoxModel();
for (int i = 0; i < Sums.length; i++) 
	sumBoxModel.addElement(Sums[i]);
extrasListModel = new DefaultListModel();
for (int i = 0; i < Extras.length; i++) 
	extrasListModel.addElement(Extras[i]);

regionsBox = new JComboBox(regionsBoxModel);
sumBox = new JComboBox(sumBoxModel);
extrasList = new JList(extrasListModel);
add(new JScrollPane(extrasList));

dataStartField = new JTextField("дд/мм/гг");
dataEndField = new JTextField("дд/мм/гг");
promoField = new JTextField("Номер купона");
expireField = new JTextField("0 дней");
priceField = new JTextField("0 руб.");
ageField1 = new JTextField("0");
ageField2 = new JTextField("0");
ageField3 = new JTextField("0");
ageField4 = new JTextField("0");

multiCheck = new JCheckBox();

calcButton = new JButton("Расчитать");

mainPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints(); 

c.anchor = GridBagConstraints.WEST; 
c.gridheight = 1;
c.gridwidth  = 2; 
c.gridx = 0; 
c.gridy = GridBagConstraints.RELATIVE; 
c.insets = new Insets(10, 20, 0, 0);
mainPanel.add(regionLabel, c);
mainPanel.add(dateLabel, c);
mainPanel.add(multiLabel, c);

c.anchor = GridBagConstraints.CENTER; 
c.gridwidth  = GridBagConstraints.REMAINDER; 
c.insets = new Insets(30, 20, 0, 0);
mainPanel.add(whoClientsLabel, c);

c.gridx = GridBagConstraints.RELATIVE; 
c.gridy = 4;
c.gridwidth  = 1;
c.insets = new Insets(10, 20, 0, 0);
mainPanel.add(ageLabel1, c);
mainPanel.add(ageLabel2, c);
mainPanel.add(ageLabel3, c);
mainPanel.add(ageLabel4, c);

c.gridwidth  = GridBagConstraints.REMAINDER; 
c.gridx = 0;
c.gridy = 6; 
c.insets = new Insets(30, 20, 0, 0);
mainPanel.add(chooseLabel, c);

c.gridwidth  = 2; 
c.anchor = GridBagConstraints.WEST; 
c.gridy = GridBagConstraints.RELATIVE; 
c.insets = new Insets(10, 20, 0, 0);
mainPanel.add(sumLabel, c);
mainPanel.add(extrasLabel, c);
mainPanel.add(promoLabel, c);

c.gridwidth  = GridBagConstraints.REMAINDER; 
c.anchor = GridBagConstraints.CENTER;
c.insets = new Insets(30, 20, 0, 0);
mainPanel.add(resultLabel, c);

c.gridwidth  = 1;
c.insets = new Insets(10, 20, 0, 0);
c.anchor = GridBagConstraints.WEST; 
mainPanel.add(expireLabel, c);
mainPanel.add(priceLabel, c);

c.gridy = 0;
c.gridx = 2;
c.gridwidth  = GridBagConstraints.REMAINDER; 
mainPanel.add(regionsBox,c);

c.gridy = GridBagConstraints.RELATIVE;
c.gridwidth  = 1; 
mainPanel.add(dataStartField, c);
mainPanel.add(multiCheck, c);

c.gridy = 5;
c.gridx = 0;
c.gridwidth  = 1;
c.anchor = GridBagConstraints.CENTER; 
mainPanel.add(ageField1, c);

c.gridx = GridBagConstraints.RELATIVE; 
mainPanel.add(ageField2, c);
mainPanel.add(ageField3, c);
mainPanel.add(ageField4, c);

c.gridwidth  = 2;
c.anchor = GridBagConstraints.WEST; 
c.gridy = 7;
c.gridx = 2;
mainPanel.add(sumBox, c);

c.gridy = GridBagConstraints.RELATIVE;
c.insets = new Insets(20, 20, 20, 0);
mainPanel.add(extrasList, c);

c.insets = new Insets(10, 20, 0, 0);
mainPanel.add(promoField, c);

c.gridy = 11;
mainPanel.add(expireField, c);

c.gridy = 12;
mainPanel.add(priceField, c);

c.gridy = 1;
c.gridx = 3;
mainPanel.add(dataEndField, c);

c.gridy = 13;
c.gridx = 1;
c.anchor = GridBagConstraints.CENTER;
c.gridwidth  = 2; 
c.insets = new Insets(20, 20, 0, 0);
mainPanel.add(calcButton, c);

this.add(mainPanel);

}

class ButtonEventListener implements ActionListener {
public void actionPerformed(ActionEvent e) {

}
}
}