import GUI.MainPanel;

import COM.SPController;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import COM.Utils;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public  class Main extends JApplet {

    protected static SPController sPController;
    protected static SerialPortEventListener serialPortEventListener;
    protected static MainPanel mp;





    @Override
public void init()
{
    this.setSize(0,0);
    serialPortEventListener = new ComListener();
    sPController = new SPController(serialPortEventListener);
    mp = new MainPanel(this.sPController);
    mp.run();

}




    /////////////////////////////////////
private class ComListener implements SerialPortEventListener {
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                byte[] readBytes;
                readBytes = sPController.read(event.getEventValue());

                boolean containsXonXoff = (readBytes[0] == SPController.XOFF_CHARACTER ||
                        readBytes[0] == SPController.XON_CHARACTER);

                if(containsXonXoff){
                    if (readBytes[0] == SPController.XOFF_CHARACTER) {
                        sPController.setXoffState(false);
                        mp.getCp().setFlowStatus("<html>Был получен сигнал XOff.<br/>Отправка данных запрещена</html>");
                        mp.getCp().getFlowStatus().setForeground(Color.red);
                    }
                    if (readBytes[0] == SPController.XON_CHARACTER){
                        sPController.setXoffState(true);
                        mp.getCp().setFlowStatus("<html>Был получен сигнал XOn.<br/> Отправка данных разрешена</html>");
                        mp.getCp().getFlowStatus().setForeground(Color.green);

                    }
                }

               if(  (readBytes != null) )
               {
                   if(readBytes[0]!=SPController.XOFF_CHARACTER && readBytes[0]!=SPController.XON_CHARACTER ) {
                       String result = new String(readBytes) + "\n";
                       mp.getOp().getOutArea().append(result);
                       mp.getCp().setRecivedByteLabel((sPController.getReceivedBytes()));
                   }


               }


            } catch (Exception exception) {
               Utils exceptionBox = new Utils();
               exceptionBox.showExceptionDialog(exception);

            }
        }
    }
}
}
