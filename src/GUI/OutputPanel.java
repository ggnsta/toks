package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutputPanel extends JPanel {

    protected JTextArea outArea = new JTextArea();
    protected JScrollPane outScroll;
    protected JButton clear=new JButton("Очитстить");
    protected JLabel label = new JLabel("Вывод:");

    public JTextArea getOutArea() {
        return outArea;
    }



    public OutputPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 350));
        panel.setLayout(new FlowLayout());

        panel.add(label);

        outArea.setEditable(false);
        outScroll = new JScrollPane(outArea , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//добавляем его к ScrollPane
        outScroll.setPreferredSize(new Dimension(390,250));
        panel.add(outScroll);
        clear.setEnabled(false);
        panel.add(clear);
        setVisible(true);
        add(panel);

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outArea.setText("");
            }
        });
    }


}
