package com.sws.sws;
import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
public class JasyptConfigTest {
    @Test
    void jasypt(){
        String url = "jdbc:mysql://sws.cpsqeqw0ir0j.ap-northeast-2.rds.amazonaws.com:3306/sws";
        String username = "admin";
        String password = "rlatndk123!";
        String tokenKey = "gyuhgyyg6767gyuhgyyg6767gyuhgyyg6767gyuhgyyg6767gyuhgyyg6767gyuhgyyg6767gyuhgyyg6767";

        String encryptUrl = jasyptEncrypt(url);
        String encryptUsername = jasyptEncrypt(username);
        String encryptPassword = jasyptEncrypt(password);
        String encryptTokenKey = jasyptEncrypt(tokenKey);

        System.out.println("encryptUrl : " + encryptUrl);
        System.out.println("encryptUsername : " + encryptUsername);
        System.out.println("encryptPassword : " + encryptPassword);
        System.out.println("encryptTokenKey : " + encryptTokenKey);

        Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
    }

    private String jasyptEncrypt(String input) {
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecryt(String input){
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}
