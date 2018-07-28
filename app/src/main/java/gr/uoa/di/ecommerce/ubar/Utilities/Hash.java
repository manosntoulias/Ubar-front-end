package gr.uoa.di.ecommerce.ubar.Utilities;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hash {

    public static String getSHA512(String input) {

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return toReturn;
    }

}
