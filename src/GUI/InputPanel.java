package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import COM.SPController;
import COM.Utils;

public class InputPanel extends JPanel {


    protected JTextArea inputArea = new JTextArea();
    protected JScrollPane inputScroll;
    protected JButton send=new JButton("Отправить");
    protected JLabel label = new JLabel("Ввод:");

    protected SPController spController;




    public InputPanel(SPController spController) {

        this.spController = spController;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 350));
        panel.setLayout(new FlowLayout());

        panel.add(label);

        inputScroll = new JScrollPane(inputArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//добавляем его к ScrollPane
        inputScroll.setPreferredSize(new Dimension(390,250));
        panel.add(inputScroll);
        send.setEnabled(false);
        inputArea.setEnabled(false);

        panel.add(send);
        setVisible(true);

        add(panel);



        //отправка сообщений
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(spController.getXoffState()) {
                        spController.write(inputArea.getText().getBytes());
                        MainPanel.getCp().setSendByteLabel((spController.getSentBytes()));// отображаем количество отправленных байт
                        inputArea.setText("");
                    }
                } catch (Exception exception) {
                    Utils utils = new Utils();
                    utils.showExceptionDialog(exception);
                }
            }
        });

    }




}
