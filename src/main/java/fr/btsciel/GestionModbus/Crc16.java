package fr.btsciel.GestionModbus;

public class Crc16 {
    public static int stdPoly = 0xA001;
    public static int initialValue = 0xffff;

    public Crc16() {
    }

     public byte[] formatage (String trame) {
        trame = trame.trim().replaceAll(" ","");
        if (trame.length() % 2 != 0) {
            trame = trame + "0";
            return hexStringEnByteArray(trame);
        } else {
            return  hexStringEnByteArray(trame);
        }
    }
     public byte[] hexStringEnByteArray (String message) {
        int len = message.length();
        byte[] data = new byte[len/2];
        for (int i = 0; i < len; i+= 2) {
            data[i/2] = (byte) ((Character.digit(message.charAt(i),16) << 4) +
                    Character.digit(message.charAt(i+1),16));
        }
        return data;
    }
     public int calculCrc16 (byte[] octets) {
        int  crc = initialValue;
        for (int p = 0; p < octets.length; p++) {
            crc ^= (octets[p] & 0xff);
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) != 0) {
                    crc = (crc >> 1) ^ stdPoly;
                } else {
                    crc = crc >> 1;
                }
            }
        }
        return crc;
    }
}
