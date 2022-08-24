package com.xzxin.qrcode.controller;

import com.google.zxing.WriterException;
import com.xzxin.qrcode.service.MyQRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static com.xzxin.qrcode.service.MyQRCodeService.QR_CODE_APK_PATH;
import static com.xzxin.qrcode.service.MyQRCodeService.QR_CODE_IMAGE_PATH;

@Controller
@RequestMapping("/")
public class QRCodeController {
    @Autowired
    private MyQRCodeService myQRCodeService;

    @GetMapping("/hi")
    @ResponseBody
    public String hi() {
        return "hello";
    }

    @PostMapping("/hicode")
    public String generateHiQRCode() throws IOException, WriterException {
        myQRCodeService.generateQRCodeImage("http://192.168.0.106:8080/hi", 350, 350, QR_CODE_IMAGE_PATH);
        return "";
    }

    @GetMapping("/apkcode")
    @ResponseBody
    public String generateApiHiQRCode() throws IOException, WriterException {
        myQRCodeService.generateQRCodeImage("http://192.168.0.106:8080/downloadapk", 350, 350, QR_CODE_APK_PATH);
        return "";
    }

    @GetMapping("/downloadapk")
    @ResponseBody
    public String downloadApk() {
        myQRCodeService.downloadApk();
        return "ok";
    }

}
