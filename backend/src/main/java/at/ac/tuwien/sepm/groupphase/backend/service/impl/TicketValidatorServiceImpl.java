package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketPayloadDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketValidatorServiceImpl implements TicketValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String filePath = "./src/main/resources/KeyFiles.txt";

    private final TicketRepository ticketRepository;

    private static final String TICKET_INVALID = "Ticket is invalid!";

    private static final String TICKET_VALID = "Ticket is valid!";

    public TicketValidatorServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;

    }

    @Override
    public TicketPayloadDto getTicketPayload(TicketDto ticketDto)
        throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ValidationException {
        if (ticketDto == null || ticketDto.getEvent() == null || ticketDto.getSeat() == null) {
            throw new jakarta.validation.ValidationException("The parameters Event and Seat of the Ticket must be provided");
        }
        return generatePayload(ticketDto);
    }

    // ! Only call to set initial key values !
    private void writeKeysToFile(ArrayList<SecretKey> secretKeys) {
        // Generate a random initialization vector (IV)
        byte[] iv = new byte[16];

        SecureRandom randomIv = new SecureRandom();
        randomIv.nextBytes(iv);

        //Write 32 Byte Key Array to File encode as Base 64 String
        byte[] keyBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(keyBytes);
        String encodedKey = encode(keyBytes);
        String encodedIv = encode(iv);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(encodedKey);
            writer.newLine();
            writer.write(encodedIv);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TicketPayloadDto validatePayload(TicketPayloadDto payloadDto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //Create TicketPayloadDto
        TicketPayloadDto newPayload = new TicketPayloadDto();
        //Retrieve KeyFileSpecs
        KeyFileSpecs specs = readKeyFileSpecs();
        //Decrypt Message with retrieved KeySpecs
        String decryptedMsg = "";
        try {
            decryptedMsg = aesDecrypt(payloadDto.getMessage(), specs.getSecretKey(), specs.getIvParameterSpec());
        } catch (IllegalArgumentException ex) {
            newPayload.setMessage(TICKET_INVALID);
            return newPayload;
        }

        String[] msgComponents = decryptedMsg.split(" ");
        if (msgComponents.length != 4) {
            newPayload.setMessage(TICKET_INVALID);
            return newPayload;
        }
        Long ticketId = Long.valueOf(msgComponents[0]);
        Long seatId = Long.valueOf(msgComponents[1]);
        //Retrieve ticket
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (!ticket.isPresent()) {
            newPayload.setMessage(TICKET_INVALID);
        } else {
            if (Objects.equals(ticket.get().getSeatId(), seatId)) {
                newPayload.setMessage(TICKET_VALID);
            } else {
                newPayload.setMessage(TICKET_INVALID);
            }
        }
        try {
            if (!Long.valueOf(msgComponents[3]).equals(Long.valueOf("5839593258"))) {
                newPayload.setMessage(TICKET_INVALID);
            }
        } catch (NumberFormatException ex) {
            newPayload.setMessage(TICKET_INVALID);
        }
        return newPayload;
    }

    private TicketPayloadDto generatePayload(TicketDto ticketDto)
        throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        if (ticketDto.getTicketNr() == null) {
            throw new jakarta.validation.ValidationException("Ticket Nr must be provided");
        }
        //Retrieve KeyFileSpecs
        KeyFileSpecs specs = readKeyFileSpecs();

        //Encrypt message
        String payloadMsg = "";

        payloadMsg += ticketDto.getTicketNr() + " " + ticketDto.getSeat().getId() + " " + ticketDto.getSeat().getSeatNr() + " 5839593258";
        String encryptedMessage = aesEncrypt(payloadMsg, specs.getSecretKey(), specs.getIvParameterSpec());
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

    public KeyFileSpecs readKeyFileSpecs() {
        String keyBytes;
        String ivBytes;

        // Retrieve Key Bytes from file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            keyBytes = reader.readLine();
            ivBytes = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a SecretKey object using the provided key bytes
        SecretKey secretKey = new SecretKeySpec(decode(keyBytes), "AES");

        IvParameterSpec ivParameterSpec = new IvParameterSpec(decode(ivBytes));

        return new KeyFileSpecs(secretKey, ivParameterSpec);
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static class KeyFileSpecs {
        private SecretKey secretKey;
        private IvParameterSpec ivParameterSpec;

        public KeyFileSpecs(SecretKey secretKey, IvParameterSpec ivParameterSpec) {
            this.secretKey = secretKey;
            this.ivParameterSpec = ivParameterSpec;
        }

        public SecretKey getSecretKey() {
            return secretKey;
        }

        public IvParameterSpec getIvParameterSpec() {
            return ivParameterSpec;
        }
    }
}
