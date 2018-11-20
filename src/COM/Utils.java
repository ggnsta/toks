package COM;


import javax.swing.*;



public final class Utils {
    protected JFrame errorFrame;

    public  void showExceptionDialog(Exception exception) {

        JOptionPane.showMessageDialog(errorFrame,
                "Описание: "+ exception.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);

    }

}
