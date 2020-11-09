/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jefeSaluda;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LILEN
 */
public class TestSaludo {
    public static final String VERDE = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    public static void main(String argv[]) {
        
        int i;
        String[] nombresEmpleados = {"Pablo", "Luis", "Andrea",
            "Pedro", "Paula"};
        int cantEmpleados = nombresEmpleados.length;
        Saludo hola = new Saludo(cantEmpleados);
        Thread[] elPersonal = new Thread[6];//6 porque esta incluido el jefe
        elPersonal[0] = new Thread(new Personal(hola, "JEFE", 5));//agrega al jefe en la posicion cero
        for (i = 1; i < elPersonal.length; i++) {
            elPersonal[i] = new Thread(new Personal(hola, nombresEmpleados[i - 1]));
        }
        for (i = 0; i < elPersonal.length; i++) {
            elPersonal[i].start();
        }
        try {
            hola.esperarTodosMain();//el main espera que se cumpla la condicion
        } catch (InterruptedException ex) {
            Logger.getLogger(TestSaludo.class.getName()).log(Level.SEVERE, null, ex);
        }
         for(i=0;i<elPersonal.length;i++){
            try {
                elPersonal[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(TestSaludo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(VERDE+"El main espero que se cumpla la condicion. LISTO, ahora que todos han saludado - a trabajar "+FINALIZAR_COLOR);
        
        System.out.println(CYAN+"El main espero que terminen de la ejecucion los otros hilos (join). LISTO, ahora que todos han saludado - a trabajar"+FINALIZAR_COLOR);
    }
}
