package com.example.romanishin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5hash {
    private String inString;

    public MD5hash(String string){
        inString=string;
    };
    public String MakeHash(){
        MessageDigest md = null;
        byte[] digest = new byte[0];

        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(inString.getBytes());
            digest = md.digest();

            StringBuffer strBuf = new StringBuffer();
            for(int i=0; i < digest.length; ++i ){
                //strBuf.append(Integer.toHexString(0xFF & digest[i]));
                strBuf.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            return strBuf.toString();

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inString;
    }
}
