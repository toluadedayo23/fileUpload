package com.example.fileupload.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {

    public static byte[] compressFile(byte[] data) {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);
        compressor.setInput(data);
        compressor.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] gauge = new byte[10000 * 1024];
        while (!compressor.finished()) {
            int size = compressor.deflate(gauge);
            outputStream.write(gauge, 0, size);
        }
        compressor.end();
        try{
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressFile(byte[] data){
        Inflater decompressor = new Inflater();
        decompressor.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] gauge = new byte[4*1024];

        try{
            while (!decompressor.finished()){
                int size = decompressor.inflate(gauge);
                outputStream.write(gauge,0,size);
            }
            decompressor.end();
        }catch (DataFormatException e){
            e.getMessage();
        }
        return outputStream.toByteArray();
    }
}
