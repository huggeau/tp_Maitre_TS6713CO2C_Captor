package fr.btsciel;

import fr.btsciel.GestionModbus.ClassModBus;
import fr.btsciel.GestionModbus.Crc16;
import fr.btsciel.clavier.In;
import jssc.SerialPortException;

public class Main {
    public static void main(String[] args) {
        ClassModBus classModBus;
        Byte numEsclave;
        String com;

        System.out.print("quel est le numéro de l'esclave ? : ");
        numEsclave = In.readByte();
        System.out.println();

        System.out.print("quel est le com a utiliser ? : ");
        com = In.readString();
        System.out.println();

        classModBus = new ClassModBus(numEsclave);
        try {
            classModBus.connectEsclave(com, 19200, 8, 1, 2);
            classModBus.lectureCoils(8192 , 2);
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
