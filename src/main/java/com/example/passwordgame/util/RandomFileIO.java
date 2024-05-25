// Abstract class for RandomAccessFile classes
package com.example.passwordgame.util;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class RandomFileIO implements AutoCloseable {
    protected RandomAccessFile file;

    // Size of Strings
    protected final int STRING_SIZE = 30;
    protected final int RECORD_SIZE = 64; // Size of record

    // Get number of records in file
    public long numberOfRecords() throws IOException {
        long numberOfRecords;
        numberOfRecords = file.length() / RECORD_SIZE;
        return numberOfRecords;
    }

    // Close file before object deletion
    @Override
    public void close() throws Exception {
        file.close();
    }
}