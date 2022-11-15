package com.mycompany.pablo_delgado_soto_practica_1;

import java.util.Scanner;

public class Pablo_Delgado_Soto_Practica_1 {
    
    public static boolean prob(double prob) {

        return Math.random() < prob;
    }//prob
    
    public static String escribirMensaje() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Longitud del mensaje");
        int tamM = sc.nextInt();
        String msgR = "";
        char one = '1';
        char zero = '0';
        for (int i = 0; i < tamM; i++) {
            if (prob(0.5) == true) {
                msgR = msgR + one;
            } else {
                msgR = msgR + zero;
            }//if
        }//for
        return msgR;
    }//escribirMensaje
    
    public static char escribir(int tm, int i) {

        char[] temp = new char[tm];
        if (temp[i] == '1') {
            temp[i] = '0';
        } else {
            temp[i] = '1';
        }//if
        return temp[i];
    }//escribir
    
    public static void main(String[] args) {
        
        String msg = escribirMensaje();
        int space = 1;
        int bitR = 0;
        
        //Sender
        //cuantos bits de redundancia
        while (msg.length() + bitR + 1 > space) {
            space = space * 2;
            bitR++;
        }//while
        int tam = msg.length() + bitR + 1;
        int[] save = new int[bitR];
        
        //posiciones de bits de redundancia
        for (int i = 0; i < bitR; i++) {
            save[i] = (int) Math.pow(2, i);
        }//for
        
        //donde colocar el mensaje
        int cont = 0;
        char[] mensaje = new char[tam];
        for (int i = 1; i < mensaje.length; i++) {
            boolean posAv = true;
            int j = 0;
            while (i == 0 || j < bitR) {
                if (i == save[j]) {
                    posAv = false;
                }//if
                j++;
            }//while
            if (posAv == true) {
                mensaje[i] = msg.charAt(cont);
                cont++;
            }//if
        }//for
        
        //que mira cada bit redundancia
        int contBit = 1;
        int result;
        int contBitR = 0;
        int[] sumas = new int[bitR];
        int suma;
        while (bitR > contBitR) {
            suma = 0;
            for (int i = 1; i < mensaje.length; i++) {
                result = contBit & i;
                if (result == contBit && result != 0) {
                    if (mensaje[i] == '1') {
                        suma++;
                    }//if
                }//if
            }//for
            sumas[contBitR] = suma;
            contBitR++;
            contBit = contBit * 2;
        }//while
        
        //colocar bits redundancia
        int tmp;
        int pow;
        for (int i = 0; i < sumas.length; i++) {
            tmp = sumas[i] % 2;
            pow = (int) Math.pow(2, i);
            mensaje[pow] = escribir(mensaje.length, pow);
        }//for
        
        //bit paridad global
        int sumaG = 0;
        pow = 0;
        for (int i = 1; i < mensaje.length; i++) {
            if (mensaje[i] == '1') {
                sumaG++;
            }//if
        }//for
        
        //escribir bit paridad global
        mensaje[pow] = escribir(mensaje.length, pow);
        
    }//main
}//Pablo_Delgado_Soto_Practica_1
