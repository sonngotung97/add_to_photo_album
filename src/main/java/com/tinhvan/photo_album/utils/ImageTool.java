package com.tinhvan.photo_album.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ImageTool {
    public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    public static BufferedImage createResizedCopy(BufferedImage originalImage, int scaledWidth, int scaledHeight) {


        //        ResampleOp resizeOp = new ResampleOp(scaledWidth, scaledHeight);
//        resizeOp.setFilter(ResampleFilters.getLanczos3Filter());
//        BufferedImage scaledImage = resizeOp.filter(originalImage, null);
        return Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, scaledWidth, scaledHeight);
    }

    public static String encodeFileToBase64Binary(File file) throws IOException {
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.US_ASCII);
    }
}
