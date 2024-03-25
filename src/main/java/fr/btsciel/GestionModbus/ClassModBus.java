package fr.btsciel.GestionModbus;

import fr.btsciel.liaisonSerie.LiaisonSerie;
import jssc.SerialPortException;

import java.util.Arrays;

public class ClassModBus extends LiaisonSerie {
    BigEndian bigEndian = new BigEndian();
    Crc16 crc16 = new Crc16();
    Byte numeroEsclave;
    byte[] resultatValeur;

    public ClassModBus(Byte numeroEsclave) {
        this.numeroEsclave = numeroEsclave;
    }
    public ClassModBus() {
    }
    public void fermerLiaisonSerie(){
        super.fermerPort();
    }
    public byte[] intDeuxByte(int nombre){
        byte[] nombreOctet = new byte[2];

        nombreOctet[0] =(byte)((nombre & 0xFF00) >> 8);
        nombreOctet[1] =(byte)((nombre & 0xFF));

        return nombreOctet;
    }
    public void connectEsclave(String port, int vitesse, int data, int parite, int stop) throws SerialPortException {
        super.initCom(port);
        super.configurerParametres(vitesse, data, parite, stop);
    }
    public float lectureCoils(int registre, int bloc) throws InterruptedException {
        byte[] tabRegistre = intDeuxByte(registre);
        byte[]  longueur = intDeuxByte(bloc);
        byte[] tabSansCrc16 = {numeroEsclave, (byte) 0x03, tabRegistre[0], tabRegistre[1], longueur[0], longueur[1]};
        byte[] tabCrc16 = intDeuxByte(crc16.calculCrc16(tabSansCrc16));
        byte[] tabAvecCrc16 = {numeroEsclave, (byte) 0x03, tabRegistre[0], tabRegistre[1], longueur[0], longueur[1], tabCrc16[1], tabCrc16[0]};
        super.ecrire(tabAvecCrc16);


        Thread.sleep(1000);
        if(super.detecteSiReception() == 9){
            byte[] trame = super.lireTrame(super.detecteSiReception());
            for (int i = 0; i < trame.length; i++) {
                System.out.print(trame[i] + "_");
            }
            System.out.println();

            byte[] crcRecus = new byte[2];
            crcRecus[0] = trame[8];
            crcRecus[1] = trame[7];

            byte[] tabSansCrc = new byte[7];
            for (int i = 0; i < trame.length-2 ; i++) {
                tabSansCrc[i] = trame[i];
            }

            byte[] crcRecusCalcule = intDeuxByte(crc16.calculCrc16(tabSansCrc));

            if(Arrays.equals(crcRecusCalcule, crcRecus)){
                byte[] octetALire = new byte[4];
                for (int i = 3; i < trame.length-2; i++) {
                 octetALire[i-3] = trame[i];
                }
                System.out.println(bigEndian.fromArray(octetALire));
            }
        }
        return 0f;
    }
}
