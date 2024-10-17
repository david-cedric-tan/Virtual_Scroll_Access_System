package Steph_Lab17_Group2_A2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class EncryptionTest {
    @Test
    void testMD5Hashing() {
        String password = "password123";
        String expectedHash = "482c811da5d5b4bc6d497ffa98491e38";
        String actualHash = Encryption.doMD5Hashing(password);

        assertEquals(expectedHash, actualHash);
    }


}
