package Steph_Lab17_Group2_A2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class EncryptionTest {

    //POSITIVE CASES
    //NEGATIVE CASES
    //EDGE CASES

    //BASE FUNCTIONALITY
    @Test
    void testMD5Hashing() {
        String password = "password123";
        String expectedHash = "482c811da5d5b4bc6d497ffa98491e38";
        String actualHash = Encryption.doMD5Hashing(password);

        assertEquals(expectedHash, actualHash);
    }


    //BASE FUNCTIONALITY
    @Test
    void testAdminHashing() {
        String password = "admin";
        String expectedHash = Encryption.doMD5Hashing("admin");

        assertEquals(expectedHash, Encryption.doMD5Hashing(password));
    }

    @Test
    void testNullPasswordHashing() {
        String emptyPassword = "";
        String expectedHash = Encryption.doMD5Hashing(emptyPassword);

        assertNotNull(expectedHash);
        assertEquals(Encryption.doMD5Hashing(emptyPassword), expectedHash);
    }

}
