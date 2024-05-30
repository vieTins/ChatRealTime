/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Controller.RSA;
import blurHash.BlurHash;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.imageio.ImageIO;
/**
 *
 * @author FPTSHOP
 */
public class test {
    public static void main(String[] args) {
        try {
            // Tạo một đối tượng RSA mới
            RSA rsa = new RSA();

            // Lấy khóa công khai và khóa riêng dưới dạng chuỗi
            String publicKeyStr = rsa.publicKeyToString();
            String privateKeyStr = rsa.privateKeyToString();

            // Chuyển đổi chuỗi khóa công khai và khóa riêng thành đối tượng PublicKey và PrivateKey
            PublicKey publicKey = RSA.stringToPublicKey(publicKeyStr);
            PrivateKey privateKey = RSA.stringToPrivateKey(privateKeyStr);

            // Tạo một đối tượng RSA khác với khóa công khai và khóa riêng đã chuyển đổi
            RSA rsa2 = new RSA(publicKey, privateKey);

            // Mã hóa và giải mã một thông điệp
            String originalMessage = "Hello, world!";
            String encryptedMessage = rsa2.encrypt(originalMessage);
            String decryptedMessage = rsa2.decrypt(encryptedMessage);

            System.out.println("Original message: " + originalMessage);
            System.out.println("Encrypted message: " + encryptedMessage);
            System.out.println("Decrypted message: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
