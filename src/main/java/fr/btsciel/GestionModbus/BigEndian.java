/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.btsciel.GestionModbus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Michael
 */
public class BigEndian {

    public BigEndian() {
    }
  

public  float fromArray(byte[] payload){
    ByteBuffer buffer = ByteBuffer.wrap(payload);
    buffer.order(ByteOrder.BIG_ENDIAN);
    return buffer.getFloat();
}

public byte[] toArray(float value){
    ByteBuffer buffer = ByteBuffer.allocate(4);
    buffer.order(ByteOrder.BIG_ENDIAN);
    buffer.putFloat(value);
    buffer.flip();
    return buffer.array();
}    
    
    
    
}
