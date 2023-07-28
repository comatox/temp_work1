package com.skshieldus.esecurity.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class FTPUtil {

    private String ip;

    private int port;

    private String id;

    private String pwd;

    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String id, String pwd) {
        this.ip = ip;
        this.port = port;
        this.id = id;
        this.pwd = pwd;
    }

    /**
     * File upload
     *
     * @param uploadPath
     * @param is
     * @param fileName
     * @return
     *
     * @throws IOException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    public boolean uploadFileByInputStream(String uploadPath, InputStream is, String fileName) throws IOException {
        boolean uploaded = true;

        // Connect to FTP server
        log.info("[START] uploadFileByInputStream");
        log.info("Type: InputStream");
        log.info("fileName: {}", fileName);

        if (this.connectServer(ip, port, id, pwd)) {
            try {
                ftpClient.changeWorkingDirectory(uploadPath);

                // Call storeFile method to store
                uploaded = ftpClient.storeFile(fileName, is);

                is.close();
            } catch (IOException e) {
                log.error("Upload file exception", e);
                uploaded = false;
            } finally {
                this.disconnect();
            }
        }
        else {
            uploaded = false;
        }

        log.info("Result: {}", uploaded);
        log.info("[END] uploadFileByInputStream");

        return uploaded;
    }

    /**
     * FTP upload
     *
     * @param uploadPath
     * @param file
     * @param fileName
     * @return
     *
     * @throws IOException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    public boolean uploadFile(String uploadPath, File file, String fileName) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;

        // Connect to FTP server
        log.info("[START] uploadFile");
        log.info("uploadPath: {}", uploadPath);
        log.info("Type: InputStream");
        log.info("fileName: {}", fileName);

        // Connect to FTP server
        if (this.connectServer(ip, port, id, pwd)) {
            try {
                ftpClient.changeWorkingDirectory(uploadPath);

                // Convert files to streams
                fis = new FileInputStream(file);

                // Call storeFile method to store
                uploaded = ftpClient.storeFile(fileName, fis);

                fis.close();
            } catch (IOException e) {
                log.error("Upload file exception", e);
                uploaded = false;
            } finally {
                // Close connections and FileStream
                try {
                    if (fis != null)
                        fis.close();
                    this.disconnect();
                } catch (IOException e) {
                    log.error("Close connection exception", e);
                }
            }
        }

        log.info("Result: {}", uploaded);
        log.info("[END] uploadFileByInputStream");

        return uploaded;
    }

    /**
     * FTP upload
     *
     * @param uploadPath
     * @param file
     * @return
     *
     * @throws IOException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    public boolean uploadFile(String uploadPath, File file) throws IOException {
        return this.uploadFile(uploadPath, file, file.getName());
    }

    /**
     * FTP Multi upload
     *
     * @param uploadPath
     * @param fileList
     * @return
     *
     * @throws IOException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    public boolean uploadFiles(String uploadPath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;

        // Connect to FTP server
        if (this.connectServer(ip, port, id, pwd)) {
            try {
                ftpClient.changeWorkingDirectory(uploadPath);

                // Traverse file store
                for (File fileItem : fileList) {
                    // Convert files to streams
                    fis = new FileInputStream(fileItem);

                    // Call storeFile method to store
                    ftpClient.storeFile(fileItem.getName(), fis);

                    fis.close();
                }
            } catch (IOException e) {
                log.error("Upload file exception", e);
                uploaded = false;
            } finally {
                // Close connections and FileStream
                try {
                    if (fis != null)
                        fis.close();
                    this.disconnect();
                } catch (IOException e) {
                    log.error("Close connection exception", e);
                }
            }
        }

        return uploaded;
    }

    /**
     * Connect to FTP server
     *
     * @param ip
     * @param port
     * @param id
     * @param pwd
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    private boolean connectServer(String ip, int port, String id, String pwd) {

        // FTP server Info
        log.info("FTP Server info :: {}", ip + ":" + port + "@" + id);

        boolean isSuccess = false;
        ftpClient = new FTPClient();
        ftpClient.setDefaultTimeout(1000 * 30); // 30 sec
        ftpClient.setConnectTimeout(1000 * 10); // 10 sec

        try {
            log.info("Connecting FTP Server...");
            ftpClient.connect(ip, port);

            int reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("Exception in connecting to FTP Server");
            }

            isSuccess = ftpClient.login(id, pwd);

            if (isSuccess) {
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");

                // Set to binary format to prevent garbled code
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                // Passive mode storage
                ftpClient.enterLocalPassiveMode();
            }

            log.info("Connection FTP Server result : {}", isSuccess);
        } catch (Exception e) {
            log.error("FTP Server connection failed :: ", e);
        }
        return isSuccess;
    }

    private void disconnect() {
        if (this.ftpClient.isConnected()) {
            try {
                log.info("Logout FTP Server");
                this.ftpClient.logout();

                log.info("Disconnect FTP Server");
                this.ftpClient.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }

}
