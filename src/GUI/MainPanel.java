package GUI;

import javax.swing.*;
import java.awt.*;
import COM.SPController;

public class MainPanel  {

    protected static InputPanel ip;
    protected static OutputPanel op;
    protected static ControlPanel cp;
    protected SPController sPController;



    public static OutputPanel getOp() {
        return op;
    }

    public static  ControlPanel getCp() { return cp; }



    public MainPanel(SPController sPController) {
        this.sPController = sPController;
    }

    public  void run() {

        JFrame frame = new JFrame("COM CHAT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        frame.add(ip =new InputPanel(this.sPController));
        frame.add(op =new OutputPanel());
        frame.add(cp =new ControlPanel(this.sPController));



        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(850,700);
        frame.setVisible(true);
        frame.setResizable(false);



    }

    public static void change_status(boolean status)
    {
        cp.xOff.setEnabled(status);
        cp.xon.setEnabled(status);
        cp.closePort.setEnabled(status);
        ip.send.setEnabled(status);
        op.clear.setEnabled(status);
        ip.inputArea.setEnabled(status);
    }
}