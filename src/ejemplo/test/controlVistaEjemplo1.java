package ejemplo.test;

import ejemplo.vista.vistaEjemplo1;
import frameWork.Framework;
import frameWork.Transaccion;
import org.apache.log4j.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class controlVistaEjemplo1 implements ActionListener {
    private vistaEjemplo1 prueba;
    private Logger log;
    private Framework frame;

    public controlVistaEjemplo1(vistaEjemplo1 prueba)  {
        this.prueba = prueba;
        this.prueba.getEnviarButton().addActionListener(this);
        frame = new Framework();

        Connection uno= frame.getConexion();
        Connection dos= frame.getConexion();
        Connection tres= frame.getConexion();
        Connection cuatro= frame.getConexion();
        frame.execute();
        //.-----------
        try{
            Thread.sleep(8000);
        }catch (InterruptedException e){}
        frame.dejarUsoConexion(uno);
        frame.dejarUsoConexion(tres);

        try{
            Thread.sleep(8000);
        }catch (InterruptedException e){}
        System.out.println("Ya se dio la 5");
        Connection cinco = frame.getConexion();


        try{
            Thread.sleep(8000);
        }catch (InterruptedException e){}
        frame.cancelarConexion(dos);
        frame.cancelarConexion(cuatro);
        //frame.cancelarConexion(tres);
        //frame.cancelarConexion(uno);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(prueba.getEnviarButton() == e.getSource()){
                System.out.println(prueba.getTextField1().getText());
                log=frame.getLogger();
                log.info("Se oprime boton");
                Transaccion t = frame.getTransaccion("Login");
                t.execute(prueba.getTextField1().getText());

                Connection connection5 = frame.getConexion();
                Statement st;
                ResultSet rs;
                try {
                    st = connection5.createStatement();
                    rs = st.executeQuery("select * from arquitectura");
                    System.out.println("Se prueba la conexion");
                    while(rs.next()){
                        System.out.println(rs.getString("Cadena"));
                    }
                } catch (SQLException ev) {
                    throw new RuntimeException(ev);
                }
                frame.cancelarConexion(connection5);
            }
        }catch (NullPointerException exc){
            System.out.println("La transacción es inexistente"+exc.getMessage());
        }

    }
}