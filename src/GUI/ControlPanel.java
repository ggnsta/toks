package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jssc.SerialPort;
import COM.SPController;
import jssc.SerialPortList;
import COM.Utils;

public class ControlPanel extends JPanel {

    protected JButton connectToPort = new JButton("Открыть порт");
    protected JButton closePort = new JButton("Закрыть порт");
    protected JLabel recivedByte = new JLabel("Получено байт:   ");
    protected JLabel sentByte = new JLabel("Отправлено байт:   ");
    protected  JLabel flowStatusLabel=new JLabel("<html>Был получен сигнал XOff.<br/>Отправка данных запрещена</html> ");

    protected JComboBox portList = new JComboBox(SerialPortList.getPortNames());
    protected JRadioButton xon = new JRadioButton("xOn/");
    protected JRadioButton xOff = new JRadioButton("xOff");


    private boolean flowControlXonXoffEnabled = false;
    private boolean portIsOpened = true;
    protected SPController sPController;


    public JLabel getFlowStatus() {
        return flowStatusLabel;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatusLabel.setText(flowStatus);
    }

    public void setRecivedByteLabel(int numOfBytes) {
        recivedByte.setText("Получено байт:"+numOfBytes);
    }

    public void setSendByteLabel(int numOfBytes) {
        sentByte.setText("Отправлено байт:"+numOfBytes);
    }
    public ControlPanel( SPController sPController) {


        this.sPController = sPController;
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(170, 800));
        panel.setLayout(new FlowLayout());
        panel.add(portList);
        panel.add(connectToPort);
        panel.add(closePort);
        panel.add(recivedByte);
        panel.add(sentByte);

        ButtonGroup group = new ButtonGroup();
        group.add(xon);
        group.add(xOff);
        xOff.setSelected(true);

        panel.add(xon);
        panel.add(xOff);

        flowStatusLabel.setForeground(Color.red);
        panel.add(flowStatusLabel);

        xon.setEnabled(false);
        xOff.setEnabled(false);
        closePort.setEnabled(false);




        setVisible(true);
        add(panel);


        //обработчик нажатия "открыть порт"
        connectToPort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SerialPort serialPort = new SerialPort(portList.getSelectedItem().toString());
                    sPController.setSerialPort(serialPort, flowControlXonXoffEnabled);
                    portIsOpened = !portIsOpened;

                    MainPanel.change_status(true);
                } catch (Exception exception) {
                    Utils utils = new Utils();
                    utils.showExceptionDialog(exception);
                }
            }
        });

        closePort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sPController.closeSerialPort();
                    MainPanel.change_status(false);
                    setRecivedByteLabel(0);
                    setSendByteLabel(0);
                    xOff.setSelected(true);

                } catch (Exception exception) {
                   Utils utils = new Utils();
                   utils.showExceptionDialog(exception);
                }

            }
        });


        xon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sPController.sendXon();
                    sPController.setSentBytes(sPController.getSentBytes()+1);

                } catch (Exception exception) {
                    Utils utils = new Utils();
                    utils.showExceptionDialog(exception);
                }
            }
        });
afafsasf
        xOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sPController.sendXoff();
                    sPController.setSentBytes(sPController.getSentBytes()+1);
                } catch (Exception exception) {
                    Utils utils = new Utils();
                    utils.showExceptionDialog(exception);
                }
            }
        });


    }


}
