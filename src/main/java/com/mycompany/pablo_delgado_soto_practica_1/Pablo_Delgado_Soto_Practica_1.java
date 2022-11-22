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
    
    public static double probF(double prob) {

        if (Math.random() < prob) {
            return 0;
        } else if (Math.random() > prob && Math.random() < (prob * 2)) {
            return 1;
        } else {
            return 2;
        }//if
    }//probF
    
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
        
        //Noise
        //cambios
        char[] mensajeN = new char[mensaje.length];
        System.arraycopy(mensaje, 0, mensajeN, 0, mensaje.length);
        int cont1 = 0;
        int cont2 = 0;
        int discriminar = -8;
        if (probF(0.33) == 1) {
            for (int i = 0; i < mensaje.length; i++) {
                if (prob(0.5) == true && cont1 == 0) {
                    cont1++;
                    mensajeN[i] = escribir(mensajeN.length, i);
                } else if (i == mensaje.length && cont1 == 0) {
                    i = 0;
                }//if
            }//for
        } else if (probF(0.33) == 2) {
            for (int i = 0; i < mensaje.length; i++) {
                if (prob(0.5) == true && cont2 <= 1 && i != discriminar) {
                    discriminar = i;
                    cont2++;
                    mensajeN[i] = escribir(mensajeN.length, i);
                } else if (i == mensaje.length && cont2 <= 1) {
                    i = 0;
                }//if
            }//for
        }//if
        
        //Reciever
        //copiar mensaje
        int n = 0;
        int[] mensajeR = new int [mensajeN.length];
        for (int i = 1; i < mensajeN.length; i++){
            if (i == (int)Math.pow(2,n)){
                n++;
                continue;
            }//if
            mensajeR[i] = mensajeN[i];
        }//for
        
        //poner bits paridad
        //esta copiao de arriba        
        contBit = 1;
        result = 0;
        contBitR = 0;
        sumas = new int[bitR];

        while (bitR > contBitR) {
            suma = 0;
            for (int i = 1; i < mensajeR.length; i++) {
                result = contBit & i;
                if (result == contBit && result != 0) {
                    if (mensajeR[i] == 1) {
                        suma++;
                    }//if
                }//if
            }//for
            sumas[contBitR] = suma;
            contBitR++;
            contBit = contBit * 2;
        }//while
        
        tmp = 0;
        pow = 0;

        //colocar bits redundancia
        for (int i = 0; i < sumas.length; i++) {
            tmp = sumas[i] % 2;
            pow = (int) Math.pow(2, i);
            //metodo igual que el de despues
            if (tmp == 1) {
                mensajeR[pow] = 1;
            } else {
                mensajeR[pow] = 0;
            }//if
        }//for
        
        sumaG = 0;
        //bit paridad global
        for (int i = 1; i < mensajeR.length; i++) {
            if (mensajeR[i] == 1) {
                sumaG++;
            }//if
        }//for

        //escribe
        if (sumaG % 2 == 1) {
            mensajeR[0] = 1;
        } else {
            mensajeR[0] = 0;
        }//if
        System.out.println(mensajeR[0]);
        //aqui acaba lo copiao
        int q = 0;
        int contFallo = 0;
        int falloH = -3;
        int sumaFallos = 0;
        int finish = 1;
        int fallo = 0;
        while (finish < mensajeR.length){
            if (mensajeR[finish] != mensajeN[finish]){
                sumaFallos = sumaFallos + finish;
                contFallo++;
                falloH = finish;
            }//if
            finish = finish * 2;
        }//while
        
    }//main
}//Pablo_Delgado_Soto_Practica_1
