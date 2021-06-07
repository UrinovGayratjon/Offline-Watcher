package uz.urinov.offlinewatcher.downloader.internal.stream;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FileDownloadRandomAccessFile implements FileDownloadOutputStream {

    private final BufferedOutputStream out;
    private final FileDescriptor fd;
    private final RandomAccessFile randomAccess;

    private FileDownloadRandomAccessFile(File file) throws IOException {
        randomAccess = new RandomAccessFile(file, "rw");
        fd = randomAccess.getFD();
        out = new BufferedOutputStream(new FileOutputStream(randomAccess.getFD()));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    public byte[] encryptMessage(byte[] message, byte[] keyBytes)
            throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

    @Override
    public void flushAndSync() throws IOException {
        out.flush();
        fd.sync();
    }

    @Override
    public void close() throws IOException {
        out.close();
        randomAccess.close();
    }

    @Override
    public void seek(long offset) throws IOException {
        randomAccess.seek(offset);
    }

    @Override
    public void setLength(long totalBytes) throws IOException {
        randomAccess.setLength(totalBytes);
    }

    public static FileDownloadOutputStream create(File file) throws IOException {
        return new FileDownloadRandomAccessFile(file);
    }

}
