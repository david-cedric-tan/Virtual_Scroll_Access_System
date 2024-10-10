package Steph_Lab17_Group2_A2;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public static String doMD5Hashing (String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest(); //Hashing convert

            ///String converter
            StringBuilder sb = new StringBuilder();

            for (byte b: resultByteArray){
                sb.append(String.format("%20x",b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
