package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketPayloadDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketValidatorService;
import jakarta.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class TicketValidatorServiceImpl implements TicketValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TicketValidatorServiceImpl() {

    }

    @Override
    public TicketPayloadDto getTicketPayload(TicketDto ticketDto)
        throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ValidationException {
        ArrayList<SecretKey> secretKeys = new ArrayList<>();
        if (ticketDto == null || ticketDto.getEvent() == null || ticketDto.getSeat() == null) {
            throw new jakarta.validation.ValidationException("The parameters Event and Seat of the Ticket must be provided");
        }
        return generatePayload("./src/main/resources/KeyFiles.txt", ticketDto);
        //writeKeysToFile("./src/main/resources/KeyFiles.txt", secretKeys);
    }

    private void writeKeysToFile(String filePath, ArrayList<SecretKey> secretKeys) {
        //Write 32 Byte Key Array to File encode as Base 64 String
        byte[] keyBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(keyBytes);
        String encoded = encode(keyBytes);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(encoded);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TicketPayloadDto generatePayload(String filePath, TicketDto ticketDto)
        throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        // Generate a random initialization vector (IV)
        byte[] iv = new byte[16];

        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        String keyBytes;

        //Retrieve Key Bytes from file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            keyBytes = reader.readLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Create a SecretKey object using the provided key bytes
        SecretKey secretKey = new SecretKeySpec(decode(keyBytes), "AES");

        //Encrypt message
        String payloadMsg = "";

        if (ticketDto.getTicketNr() == null) {
            throw new jakarta.validation.ValidationException("Ticket Nr must be provided");
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        payloadMsg += ticketDto.getTicketNr() + " " + ticketDto.getSeat().getId() + " " + ticketDto.getEvent().getTitle() + " 5839593258";
        String encryptedMessage = aesEncrypt(payloadMsg, secretKey, ivParameterSpec);
        //String decryptedMessage = aesDecrypt(encryptedMessage, secretKey, ivParameterSpec);
        TicketPayloadDto ticketPayloadDto = new TicketPayloadDto();
        ticketPayloadDto.setMessage(encryptedMessage);
        return ticketPayloadDto;
    }

    private String aesEncrypt(String msg, SecretKey secretKey, IvParameterSpec ivParameterSpec)
        throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(msg.getBytes());
        return encode(cipherText);
    }

    private String aesDecrypt(String msg, SecretKey secretKey, IvParameterSpec ivParameterSpec)
        throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(decode(msg));
        return new String(cipherText);
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
