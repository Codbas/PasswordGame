//  Read and decrypt strings from files with random access reading
package com.example.passwordgame.util;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class RandomReadFile extends RandomFileIO {

    public RandomReadFile(String fileName) throws IOException {
        file = new RandomAccessFile(fileName, "r");
    }

    // Read record from file at specified position
    public String getRecordAt(int position) throws IOException {
        // throw exception if position is out of bounds
        if (position > numberOfRecords())
            throw new IOException("Error: attempted to read file out of bounds!");

        // Byte[] to hold password bytes read from file
        byte[] recordBytes = new byte[RECORD_SIZE];

        // Set pointer to position of record
        file.seek(position * (long)RECORD_SIZE);

        // Read bytes from file and convert to String
        file.readFully(recordBytes);
        String encryptedRecord = bytesToString(recordBytes);

        // Decrypt record
        String decryptedRecord = Encryption.decrypt(encryptedRecord);

        // Replace whitespace padding
        decryptedRecord.replaceAll("\\s", "");

        return decryptedRecord;
    }

    // Convert a byte array to a string
    private String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}