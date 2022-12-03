package com.artShop.Service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailTests {
    @Test
    public void mailEntityPositiveTest() {
        Mail mail1 = new Mail("email@gamil.com", "message", true);
        Mail mail2 = new Mail("example@gmail.com", "message", false);

        Assertions.assertEquals(mail1.getEmail(), "email@gamil.com");
        Assertions.assertEquals(mail1.getMessage(), "message");

        Assertions.assertEquals(mail2.getEmail(), "example@gmail.com");
        Assertions.assertEquals(mail2.getMessage(), "message");
    }

    @Test(expected = NullPointerException.class)
    public void mailEntityNegativeTest() {
        Mail mail1 = new Mail(null, null, true);
        Mail mail2 = null;

        Assertions.assertNull(mail1.getEmail());
        Assertions.assertNull(mail1.getMessage());

        mail2.getMessage();
    }
}
