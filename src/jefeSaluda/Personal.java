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
public class Personal implements Runnable {

    private String nombre;
    private Saludo saludo;
    private boolean esJefe;
    static int llegaron = 0;
    private int numEmp;

    Personal(Saludo s, String n) {//constructor de los empleados
        esJefe = false;
        nombre = n;
        saludo = s;
    }

    Personal(Saludo s, String n, int x) {//constructor que usa el jefe
        esJefe = true;
        nombre = n;
        saludo = s;
        numEmp = x;
    }

    public void run() {
        System.out.println("(" + nombre + " llega)");
        if (esJefe) {
          
            try {
                saludo.saludoJefe();
            } catch (InterruptedException ex) {
                Logger.getLogger(Personal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
          
            try {
                saludo.esperarJefe(nombre);
            } catch (InterruptedException ex) {
                Logger.getLogger(Personal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
