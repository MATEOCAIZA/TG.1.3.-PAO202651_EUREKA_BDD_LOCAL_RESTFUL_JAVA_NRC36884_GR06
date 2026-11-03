/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.prueba;

import ec.edu.monster.servicio.EurekaService;

/**
 *
 * @author isaac
 */
public class PruebaDeposito {

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            //datos
            String cuenta = "00100001";
            double importe = 200;
            String codEmp = "0001";
            //proceso
            EurekaService service = new EurekaService();
            service.registrarDepósito(cuenta, importe, codEmp);
            System.out.println("Proceso ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
