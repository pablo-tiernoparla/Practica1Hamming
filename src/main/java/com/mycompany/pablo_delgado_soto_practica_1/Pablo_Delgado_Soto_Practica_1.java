package com.mycompany.pablo_delgado_soto_practica_1;

import java.util.Scanner;

public class Pablo_Delgado_Soto_Practica_1 {
    
    public static void main(String[] args) {

        String msg = escribirMensaje();
        int[] mensaje = new int[sender(msg).length];
        System.arraycopy(sender(msg), 0, mensaje, 0, mensaje.length);
        int[] mensajeN = new int[mensaje.length];
        System.arraycopy(mensaje, 0, mensajeN, 0, mensaje.length);
        provocarError(mensajeN);
        int[] mensajeR = new int[mensajeN.length];
        reciever(mensajeN, mensajeR, msg);
    }//main
    
    public static boolean prob(double prob) {

        return Math.random() < prob;
    }//prob

    public static String escribirMensaje() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Longitud del mensaje");
        int tamM = sc.nextInt();
        String msg = "";
        for (int i = 0; i < tamM; i++) {
            if (prob(0.5) == true) {
                msg = msg + '1';
            } else {
                msg = msg + '0';
            }//if
        }//for
        return msg;
    }//escribirMensaje

    public static void escribir(int[] mensaje, int tmp, int pow) {

        if (tmp == 1) {
            mensaje[pow] = 1;
        } else {
            mensaje[pow] = 0;
        }//if
    }//escribir

    public static void escribirParidad(int[] sumas, int[] mensaje) {

        int pow;
        int tmp;
        for (int i = 0; i < sumas.length; i++) {
            tmp = sumas[i] % 2;
            pow = (int) Math.pow(2, i);
            escribir(mensaje, tmp, pow);
        }//for
    }//escribirPaidad

    public static double probFallo(double prob) {

        if (Math.random() < prob) {
            return 0;
        } else if (Math.random() > prob && Math.random() < (prob * 2)) {
            return 1;
        } else {
            return 2;
        }//if
    }//probF

    public static int cuantosBitsR(String msg) {

        int space = 1;
        int bitR = 0;
        while (msg.length() + bitR + 1 > space) {
            space = space * 2;
            bitR++;
        }//while
        return bitR;
    }//cuantosBitR

    public static int[] calcPosBitR(String msg) {

        int[] temp = new int[cuantosBitsR(msg)];
        for (int i = 0; i < cuantosBitsR(msg); i++) {
            temp[i] = (int) Math.pow(2, i);
        }//for
        return temp;
    }//calcPosBitR

    public static int[] escribirMsgArr(int tam, String msg) {

        int[] save = new int[cuantosBitsR(msg)];
        System.arraycopy(calcPosBitR(msg), 0, save, 0, save.length);
        int cont = 0;
        int[] mensaje = new int[tam];
        for (int i = 1; i < mensaje.length; i++) {
            boolean posAv = true;
            int j = 0;
            while (i == 0 || j < cuantosBitsR(msg)) {
                if (i == save[j]) {
                    posAv = false;
                }//if
                j++;
            }//while
            if (posAv == true) {
                if (msg.charAt(cont) == '0') {
                    mensaje[i] = 0;
                } else {
                    mensaje[i] = 1;
                }//if
                cont++;
            }//if
        }//for
        return mensaje;
    }//escribirMensajeArr

    public static int[] posicionesParaObservar(int[] mensaje, String msg) {

        int contBit = 1;
        int result;
        int contBitR = 0;
        int[] sumas = new int[cuantosBitsR(msg)];
        int suma;
        while (cuantosBitsR(msg) > contBitR) {
            suma = 0;
            for (int i = 1; i < mensaje.length; i++) {
                result = contBit & i;
                if (result == contBit && result != 0) {
                    suma = suma + mensaje[i];
                }//if
            }//for
            sumas[contBitR] = suma;
            contBitR++;
            contBit = contBit * 2;
        }//while
        return sumas;
    }//posicionesParaObservar

    public static int calcularParidad(int[] mensaje) {

        int sumaG = 0;
        for (int i = 0; i < mensaje.length; i++) {
            sumaG = sumaG + mensaje[i];
        }//for
        return sumaG;
    }//calcularParidad

    public static int[] sender(String msg) {

        int bitR = cuantosBitsR(msg);
        int tam = msg.length() + bitR + 1;
        int[] mensaje = new int[tam];
        System.arraycopy(escribirMsgArr(tam, msg), 
                0, mensaje, 0, mensaje.length);
        int[] sumas = new int[bitR];
        System.arraycopy(posicionesParaObservar(mensaje, msg), 
                0, sumas, 0, sumas.length);
        escribirParidad(sumas, mensaje);
        escribir(mensaje, calcularParidad(mensaje) % 2, 0);
        return mensaje;
    }//sender

    public static int[] provocarError(int[] mensajeN) {

        int cont = 0;
        int discriminar = -3;
        double prob = probFallo(0.33);
        if (prob == 1) {
            for (int i = 0; i < mensajeN.length; i++) {
                if (prob(0.5) == true && cont == 0) {
                    cont++;
                    escribirError(mensajeN, i);
                } else if (i == mensajeN.length && cont == 0) {
                    i = 0;
                }//if
            }//for
        } else if (prob == 2) {
            for (int i = 0; i < mensajeN.length; i++) {
                if (prob(0.5) == true && cont <= 1 && i != discriminar) {
                    discriminar = i;
                    cont++;
                    escribirError(mensajeN, i);
                } else if (i == mensajeN.length && cont <= 1) {
                    i = 0;
                }//if
            }//for
        }//if
        return mensajeN;
    }//provocarError

    public static void escribirError(int[] mensaje, int i) {

        if (mensaje[i] == 1) {
            mensaje[i] = 0;
        } else {
            mensaje[i] = 1;
        }//if
    }//escribirError

    public static void copiarBitsMensaje(int[] mensajeN, int[] mensajeR) {

        int n = 0;
        for (int i = 1; i < mensajeN.length; i++) {
            if (i == (int) Math.pow(2, n)) {
                n++;
                continue;
            }//if
            mensajeR[i] = mensajeN[i];
        }//for
    }//copiarBitsMensaje

    public static int calcularFallos(int contFallo, int[] mensajeN) {

        int fallo = 0;
        if (calcularParidad(mensajeN) % 2 == 1) {
            fallo = 1;
        } else if (calcularParidad(mensajeN) % 2 == 0 && contFallo > 0) {
            fallo = 2;
        }//if
        return fallo;
    }//calcularFallos

    public static int posicionFallo(int contFallo, int[] mensajeN, 
            int[] mensajeR) {

        int falloPos = -3;
        if (calcularFallos(contFallo, mensajeN) == 1) {
            if (contFallo == 0 && calcularParidad(mensajeN) % 2 == 1) {
                falloPos = 0;
            } else if (mensajeR[0] != mensajeN[0]) {
                falloPos = 0;
            }//if
        }//if
        return falloPos;
    }//posicionFallo
    
    public static void escribirFallos(int contFallo, int[] mensajeN, 
            int[] mensajeR){
        
        if (calcularFallos(contFallo, mensajeN) == 1) {
            System.out.println("El error est?? en la posici??n: " + 
                    posicionFallo(contFallo, mensajeN, mensajeR));
        } else if (calcularFallos(contFallo, mensajeN) == 2) {
            System.out.println("Se detectaron 2 errores");
        } else {
            System.out.println("No se detectaron errores");
        }//if
    }//escribirFallos
    
    public static void reciever(int[] mensajeN, int[] mensajeR, String msg){
        
        copiarBitsMensaje(mensajeN, mensajeR);
        posicionesParaObservar(mensajeR, msg);
        escribirParidad(posicionesParaObservar(mensajeR, msg), mensajeR);
        escribir(mensajeR, calcularParidad(mensajeR) % 2, 0);
        calcularParidad(mensajeN);
        //calcular inconsistencias y lugar del fallo
        int contFallo = 0;
        int falloPos = 0;
        for (int i = 0; i < cuantosBitsR(msg); i++) {
            if (mensajeR[(int) Math.pow(2, i)] != 
                    mensajeN[(int) Math.pow(2, i)]) {
                falloPos = falloPos + (int) Math.pow(2, i);
                contFallo++;
            }//if
        }//for    
        posicionFallo(contFallo, mensajeN, mensajeR);
        escribirFallos(contFallo, mensajeN, mensajeR);
    }//reciever
}//Pablo_Delgado_Soto_Practica_1