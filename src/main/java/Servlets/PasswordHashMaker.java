package Servlets;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashMaker {
    private String password;
    private static MessageDigest digest;

    public PasswordHashMaker(String password) throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-1");
        this.password = password;
    }

    public String getPasswordHash() {
        return getHashValue(this.password);
    }

    private static String getHashValue(String targ) {
        String res;
        synchronized (digest){
            digest.update(targ.getBytes());
            res = hexToString(digest.digest());
        }
        return res;
    }

    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
}
