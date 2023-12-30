package com.driver.BookMyShow.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;


@Service
public class BarCodeGenerationService {
    private static final String qrLocation = "./src/main/resources/static/QRCode.png"; //location for storing
    private static int height = 250;
    private static int width = 250;

    //whatever text we wrote this method() will generate QR for that text
    //our image is nothing but matrix of bits
    public void generateQR(String text) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height); //text format to QRCode
        Path path = FileSystems.getDefault().getPath(qrLocation);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
