/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jefeSaluda;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author LILEN
 */
public class Saludo {
    //COLORES
    public static final String VERDE = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    //ATRIBUTOS
    private  int cantEmpleados;//cantidad de empleados que tiene la oficina(no incluye al jefe)
    private int llegaron;//cantidad de empleados que llegaron a la oficina
    private  boolean saludoJefe; //si el jefe saluda lo pongo en true
    private int respondioSaludo;//cantidad de empleados que respondieron al saludo del jefe
    private final Lock mutex=new ReentrantLock(); //para exclusion mutua
    private final Condition jefe;
    private final Condition empleado;
    private final Condition principal;

    public Saludo(int cantEmpleados){
        this.cantEmpleados=cantEmpleados;
        this.llegaron=0;
        this.saludoJefe=false;
        this.respondioSaludo=0;
        this.empleado=mutex.newCondition();
        this.jefe=mutex.newCondition();
        this.principal=mutex.newCondition();//el main espera que se cumpla la condicion
    }
    public void esperarJefe(String empleado) throws InterruptedException {
        //los empleados esperan a que el jefe salude
        mutex.lock();
        this.llegaron++;//aumento en uno la cantidad de empleados que llegaron
        jefe.signal();
        while(!saludoJefe){
           
            this.empleado.await();
            
        }
         System.out.println(AMARILLO+empleado + "> Buenos dias jefe!"+FINALIZAR_COLOR);//el jefe aviso que saludo. el empleado puede seguir
         respondioSaludo++;
         principal.signal();
        mutex.unlock();

    }

    public void saludoJefe() throws InterruptedException {
        //el jefe esta esperando que todos los emplados lleguen
        mutex.lock();//exclusion mutua
            while(llegaron<cantEmpleados){
                System.out.println(RED+"el jefe llego. Esta esperando que lleguen todos los empleados"+FINALIZAR_COLOR);
                this.jefe.await();
            }
            this.saludoJefe=true;
            System.out.println("JEFE> Buenos dias!");
            empleado.signalAll();//le aviso a todos los empleados
            
        mutex.unlock();
    }
public void esperarTodosMain() throws InterruptedException{
    mutex.lock();
    while(respondioSaludo<cantEmpleados){//tengo que esperar a que todos los empleados devuelvan el saludo
        this.principal.await();
    }
     
     mutex.unlock();
    }

}
