package backend.gerenciamento;

import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.dsdesktopnotify.DesktopNotify;

public class Notificacao {
    private static final Logger LOGGER = Logger.getLogger(Notificacao.class.getName());
    private static Integer option = -1;

    // método chamado para enviar uma notificação ao usuário
    // o retorno é um true, caso o usuário tenha executado a ação notificada, e
    // false, caso contrário
    public static boolean notificar(String notificacao) {
        DesktopNotify.showDesktopMessage("MedAlerta - Hora de tomar o seu remédio",
            "Você tem uma nova notificação. Clique aqui para visualizar.", DesktopNotify.TIP, evt -> {
                Object[] options = { "SIM", "NÃO" };
                Integer escolha = JOptionPane.showOptionDialog(null, notificacao,
                        "MedAlerta - Hora de tomar o seu remédio", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                Notificacao.option = Integer.parseInt(escolha.toString());
            });

        while(option==-1){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Espera por notificação interrompida", e);
            }
        }

        if(option==0){
            option = -1; //impedir que decremente em uma unidade do estoque a todo momento
            return true;
        }
        return false;
    }

    public static void notificarCompra(String notificacao) {
        DesktopNotify.showDesktopMessage("MedAlerta - Hora de comprar o seu remédio",
            "Você tem uma nova notificação. Clique aqui para visualizar.", DesktopNotify.TIP,
            evt -> JOptionPane.showMessageDialog(null, notificacao, "MedAlerta - Hora de tomar o seu remédio", 1)
        );
    }
}
