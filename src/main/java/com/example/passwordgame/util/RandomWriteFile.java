// Write or update Strings to file. Strings are encrypted
package com.example.passwordgame.util;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomWriteFile extends RandomFileIO {
    public RandomWriteFile(String fileName) throws IOException{
        file = new RandomAccessFile(fileName, "rw");
        //clearFile();
    }

    // Write object at specified position
    public boolean updateRecord(int position, String record) throws IOException {
        // Return false and do not write to file if position is greater than number of records
        // or if no records exist in the file.
        if (numberOfRecords() == 0 || position >= numberOfRecords())
            return false;

        // Move pointer to position of object
        file.seek(position * (long)RECORD_SIZE);

        // Make String 30 characters by appending whitespace
        // If String is 30 characters or longer already, truncate to 30 characters
        String formattedRecord = padTo30Characters(record);

        // Encrypt the record
        String encryptedRecord = Encryption.encrypt(formattedRecord);

        // Write record to file
        file.writeBytes(encryptedRecord);
        return true;
    }

    // Write record appended to end of file
    public void writeRecord(String record) throws IOException {
        // Set pointer to end of file
        file.seek(file.length());

        // Make String 30 characters by appending whitespace
        // If String is 30 characters or longer already, truncate to 30 characters
        String formattedRecord = padTo30Characters(record);

        // Encrypt formattedRecord
        String encryptedRecord = Encryption.encrypt(formattedRecord);

        // Write record to file
        file.writeBytes(encryptedRecord);
    }

    // Clear contents of file
    public void clearFile() throws IOException {
        file.setLength(0);
    }

    // Returns String that is 30 characters long
    private String padTo30Characters(String str) {
        // If str is less than 30 chars, add padding to make it 30 chars
        if (str.length() < STRING_SIZE)
            return String.format("%-" + STRING_SIZE + "s", str);
        else // If str is more than 32 chars, return first 32 chars of str
            return str.substring(0, Math.min(str.length(), STRING_SIZE));
    }
}
