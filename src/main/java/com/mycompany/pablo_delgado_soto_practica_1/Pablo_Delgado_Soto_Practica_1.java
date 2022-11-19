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
    
    public static int[] calcPosBitR(String msg, int bitR){
        
        
        int[] temp = new int[bitR];
        for (int i = 0; i < bitR; i++) {
            temp[i] = (int) Math.pow(2, i);
        }//for
        return temp;
    }
    
    public static int[] escribirMsgArr(int tam, int bitR, String msg){
        
        int[] save = new int [bitR];
        System.arraycopy(calcPosBitR(msg, bitR), 0, save, 0, save.length);
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
                    if (mensaje[i] == 1) {
                        suma++;
                    }//if
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
    
    public static int[] copiarArr(int[] mensaje, int[] mensaje2){
        
        System.arraycopy(mensaje, 0, mensaje2, 0, mensaje.length);
        return mensaje2;
    }
    
    public static int[] sender(String msg){
        
        int bitR = cuantosBitsR(msg);
        int tam = msg.length() + bitR + 1;
        int[] mensaje = new int [tam];
        copiarArr(escribirMsgArr(tam, bitR, msg), mensaje);
        int[] sumas = new int [bitR];
        copiarArr(pendienteBitR(bitR, mensaje), mensaje);
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
            for (int i = 0; i < mensaje.length; i++)  {
                if (prob(0.5) == true && cont1 == 0){
                    cont1++;
                    escribirError(mensajeN, i);
                } else if (i == mensaje.length && cont1 == 0) {
                    i = 0;
                }//if
            }//for
        } else if (prob == 2)  {
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
    
    public static void main(String[] args) {
       
        String msg = escribirMensaje();
        int[] mensaje = new int [sender(msg).length];
        copiarArr(sender(msg), mensaje);
        int[] mensajeN = new int [mensaje.length];
        copiarArr(mensaje, mensajeN);
        provocarError(mensaje, mensajeN);
        //Noise
        //cambios
        
        
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
        suma = 0;
        int falloBitG;
        if (suma % 2 == 1){
            falloBitG = 1;
        }else{
            falloBitG = 0; 
        }//if
        if (contFallo > 1 && falloBitG == 1){
            if (mensajeR[falloH] == 1){
                mensajeR[falloH] = 0;
            } else {
                mensajeR[falloH] = 1;
            }//if
        }//if
        
        if (contFallo > 0){
            fallo++;
            for (int i = 0; i < mensajeR.length; i++){
                if (mensajeR[i] == 1){
                    suma++;
                }//if
            }//for
            if (suma % 2 == 1 && contFallo > 1){
                falloH = sumaFallos;
            } else {
                fallo++;
            }//if
        }//if
        
        
        if (contFallo == 0 && mensajeN[0] != mensajeR[0]){
            fallo++;
            falloH = 0;
        } else {
            if (fallo > 0 && contFallo == 0 && falloBitG == mensajeR[0]){
                fallo++;
            }//if
        }//if
        
        if (fallo == 1){
            System.out.println("El error está en: " + falloH);
        } else if (fallo == 2){
            System.out.println("Se detectaron 2 errores");
        } else {
            System.out.println("No hay errores");
        }//if
        
        System.out.print(msg);
        System.out.println("");
        for (int i = 0; i < mensaje.length; i++){
            System.out.print(mensaje[i]);
        }
        System.out.println("");
        for (int i = 0; i < mensajeN.length; i++){
            System.out.print(mensajeN[i]);
        }
        System.out.println("");
        for (int i = 0; i < mensajeR.length; i++){
            System.out.print(mensajeR[i]);
        }
        System.out.println("");
        System.out.println(contFallo);
    }//main
}//Pablo_Delgado_Soto_Practica_1
