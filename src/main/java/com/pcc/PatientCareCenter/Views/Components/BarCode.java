package com.pcc.PatientCareCenter.Views.Components;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BarCode {

    public static BufferedImage getBuffer(String data, BarcodeFormat format, int w, int h) throws WriterException, IOException {
        return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(new String(data.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), format, w, h));
    }

    public static BufferedImage generateBarcodeImage(String barcodeData, int width, int height) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeData, BarcodeFormat.CODE_128, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height,hints);
//        bitMatrix=removeMargin(bitMatrix);
//        bitMatrix=addMargin(bitMatrix,5);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
    private static BitMatrix removeMargin(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        // Find the boundaries of the QR code (excluding the margin)
        int minX = width, minY = height, maxX = 0, maxY = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    if (x < minX) minX = x;
                    if (y < minY) minY = y;
                    if (x > maxX) maxX = x;
                    if (y > maxY) maxY = y;
                }
            }
        }

        int newWidth = maxX - minX + 1;
        int newHeight = maxY - minY + 1;
        BitMatrix croppedMatrix = new BitMatrix(newWidth, newHeight);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (bitMatrix.get(x, y)) {
                    croppedMatrix.set(x - minX, y - minY);
                }
            }
        }

        return croppedMatrix;
    }
    private static BitMatrix addMargin(BitMatrix bitMatrix, int margin) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        BitMatrix paddedMatrix = new BitMatrix(width + 2 * margin, height + 2 * margin);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    paddedMatrix.set(x + margin, y + margin);
                }
            }
        }

        return paddedMatrix;
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
}
