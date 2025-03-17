package com.ppcc.PatientCareCenter.Views.Components;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BarCode {

    public static BufferedImage getBuffer(String data, BarcodeFormat format, int w, int h) throws WriterException, IOException {
        return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(new String(data.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), format, w, h));
    }

    public static BufferedImage getBuffer(byte[] data, BarcodeFormat format, int w, int h) throws WriterException, IOException {
        return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(new String(data, StandardCharsets.UTF_8), format, w, h));
    }

    public static void create(String data, String path, BarcodeFormat format, int w, int h) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), format, w, h);
        MatrixToImageConfig f = new MatrixToImageConfig();
        File file = File.createTempFile("PCC Temp file", path.substring(path.lastIndexOf('.') + 1));
        file.deleteOnExit();
        MatrixToImageWriter.writeToPath(matrix, path.substring(path.lastIndexOf('.') + 1), Path.of(URI.create(path)));
    }

    public static String read(String path) throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

    public static void main(String[] args) throws IOException, WriterException {
        String s = "Adithya";
        try {
            String img = "Images\\1.png";
            BarCode.create(s, "QR.png", BarcodeFormat.PDF_417, 50, 50);
            byte[] imgByte;
            ByteArrayOutputStream bos;
            try (FileInputStream im = new FileInputStream(img)) {
                imgByte = new byte[1024];
                bos = new ByteArrayOutputStream();
                for (int i; (i = im.read(imgByte)) != -1; ) {
                    bos.write(imgByte, 0, i);
                }
            }
            imgByte = bos.toByteArray();
            ImageIO.write(BarCode.getBuffer(imgByte, BarcodeFormat.QR_CODE, 500, 500), "png", new File("QR.png"));
        } catch (IOException ex) {
            Logger.getLogger(BarCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
