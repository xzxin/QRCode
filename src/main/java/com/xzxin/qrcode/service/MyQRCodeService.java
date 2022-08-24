package com.xzxin.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MyQRCodeService {

    @Autowired
    private HttpServletResponse response;

    public static final String QR_CODE_IMAGE_PATH = "D:\\temp\\QRCode.png";

    public static final String QR_CODE_APK_PATH = "D:\\temp\\QRCodeApk.png";

    public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }


    public void downloadApk() {
        OutputStream os = null;
        File file = new File("D://temp//meituan.apk");
//        File file = new File("D://temp//photo.jpg");
        try {
            // 取得输出流
            os = response.getOutputStream();

            //String contentType = Files.probeContentType(Paths.get(file.getAbsolutePath()));
            //当前只下载apk格式文件 contentType
            response.setHeader("Content-Type", "application/octet-stream");
            response.addHeader("Content-Length", "" + file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] chars = new byte[1024];
            int byteReads = -1;
            while ((byteReads = fileInputStream.read(chars)) != -1) {
                os.write(chars, 0, byteReads);
            }
//            WritableByteChannel writableByteChannel = Channels.newChannel(os);
//            FileChannel fileChannel = fileInputStream.getChannel();
//            fileChannel.transferTo(0,fileChannel.size(),writableByteChannel);
//            fileChannel.close();
//            os.flush();
//            writableByteChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件的关闭放在finally中
        finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}