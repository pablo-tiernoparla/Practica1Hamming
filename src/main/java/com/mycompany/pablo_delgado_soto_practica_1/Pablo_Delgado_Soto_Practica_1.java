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
   
    public static void escribir(int[] mensaje, int tmp, int pow) {

        if (tmp == 1) {
                mensaje[pow] = 1;
            } else {
                mensaje[pow] = 0;
            }//if
    }//escribir
    
    public static void escribirParidad(int[] sumas, int[] mensaje){
        
        int pow;
        int tmp;
        for (int i = 0; i < sumas.length; i++) {
            tmp = sumas[i] % 2;
            pow = (int) Math.pow(2, i);
            escribir(mensaje, tmp, pow);
        }//for
    }
   
    public static double probF(double prob) {

        if (Math.random() < prob) {
            return 0;
        } else if (Math.random() > prob && Math.random() < (prob * 2)) {
            return 1;
        } else {
            return 2;
        }//if
    }//probF
   
    public static int cuantosBitsR(String msg){
        
        int space = 1;
        int bitR = 0;
        while (msg.length() + bitR + 1 > space) {
            space = space * 2;
            bitR++;
        }//while
        return bitR;
    }
    
    public static int[] calcPosBitR(int bitR){
        
        
        int[] temp = new int[bitR];
        for (int i = 0; i < bitR; i++) {
            temp[i] = (int) Math.pow(2, i);
        }//for
        return temp;
    }
    
    public static int[] escribirMsgArr(int tam, int bitR, String msg){
        
        int[] save = new int [bitR];
        System.arraycopy(calcPosBitR(bitR), 0, save, 0, save.length);
        int cont = 0;
        int[] mensaje = new int[tam];
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
                if (msg.charAt(cont) == '0'){
                mensaje[i] = 0;
                } else {
                    mensaje[i] = 1;
                }//if
                cont++;
            }//if
        }//for
        return mensaje;
    }
    
    public static int[] pendienteBitR(int bitR, int[] mensaje){
        
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
                    suma = suma + mensaje[i];
                }//if
            }//for
            sumas[contBitR] = suma;
            contBitR++;
            contBit = contBit * 2;
        }//while
        return sumas;
    }
    
    public static int calcularBitG(int[] mensaje){
        
        int sumaG = 0;
        for (int i = 1; i < mensaje.length; i++) {
            sumaG = sumaG + mensaje[i];
        }//for
        return sumaG;
    }
    
    public static int[] sender(String msg){
        
        int bitR = cuantosBitsR(msg);
        int tam = msg.length() + bitR + 1;
        int[] mensaje = new int [tam];
        System.arraycopy(escribirMsgArr(tam, bitR, msg),
                0, mensaje, 0, mensaje.length);
        int[] sumas = new int [bitR];
        System.arraycopy(pendienteBitR(bitR, mensaje),
                0, sumas, 0, sumas.length);
        escribirParidad(sumas, mensaje);
        escribir(mensaje, calcularBitG(mensaje) % 2, 0);
        return mensaje;
    }
    
    public static int[] provocarError(int[] mensaje, int[] mensajeN){
        
        int cont1 = 0;
        int cont2 = 0;
        int discriminar = -3;
        double prob = probF(0.33);
        if (prob == 1)  {
            System.out.println("1 fallo");
            for (int i = 0; i < mensaje.length; i++)  {
                if (prob(0.5) == true && cont1 == 0){
                    cont1++;
                    escribirError(mensajeN, i);
                } else if (i == mensaje.length && cont1 == 0) {
                    i = 0;
                }//if
            }//for
        } else if (prob == 2)  {
            System.out.println("2 fallos");
            for (int i = 0; i < mensaje.length; i++)  {
                if (prob(0.5) == true && cont2 <= 1 && i != discriminar){
                    discriminar = i;
                    cont2++;
                    escribirError(mensajeN, i);
                } else if (i == mensaje.length && cont2 <= 1) {
                    i = 0;
                }//if
            }//for
        }//if
        return mensajeN;
    }
    
    public static void escribirError(int[] mensaje, int i){
        
        if (mensaje[i] == 1)  {
                        mensaje[i] = 0;
                    } else {
                        mensaje[i] = 1;
                    }//if
    }
    
    public static void copiarBitsMensaje(int[] mensajeN, int[] mensajeR){
        
        int n = 0;
        for (int i = 1; i < mensajeN.length; i++){
            if (i == (int)Math.pow(2,n)){
                n++;
                continue;
            }//if
            mensajeR[i] = mensajeN[i];
        }//for
    }
    
    public static void main(String[] args) {
       
        String msg = escribirMensaje();
        int[] mensaje = new int [sender(msg).length];
        System.arraycopy(sender(msg), 0, mensaje, 0, mensaje.length);
        int[] mensajeN = new int [mensaje.length];
        System.arraycopy(mensaje, 0, mensajeN, 0, mensaje.length);
        provocarError(mensaje, mensajeN);
        
        int[] mensajeR = new int [mensajeN.length];
        copiarBitsMensaje(mensajeN, mensajeR);
        pendienteBitR(cuantosBitsR(msg), mensajeR);
        escribirParidad(pendienteBitR(cuantosBitsR(msg), mensajeR), mensajeR);
        escribir(mensajeR, calcularBitG(mensajeR) % 2, 0);
        
        
        int sumaGN = 0;
        for (int i = 0; i < mensajeN.length; i++) {
            if (mensajeN[i] == '1') {
                sumaGN++;
            }//if
        }//for
    }//main
}//Pablo_Delgado_Soto_Practica_1
