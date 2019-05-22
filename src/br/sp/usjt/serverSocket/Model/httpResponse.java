package br.sp.usjt.serverSocket.Model;

import br.sp.usjt.serverSocket.Utils.ServerConfig;
import br.sp.usjt.serverSocket.Utils.vars;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class httpResponse {

    private String contentType;
    private int contentLength;
    private File file;

    private int httpCode;

    private String dataResposta;


    public httpResponse() {
    }

    public httpResponse(String filePath, String contentMimeType, int httpCode) {
        this.contentType = contentMimeType;
        this.file = new File(ServerConfig.WEB_ROOT, filePath);
        this.contentLength = (int) file.length();
        this.httpCode = httpCode;

        this.dataResposta = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());
    }

    public httpResponse(String filePath, int httpCode) {
        this.file = new File(ServerConfig.WEB_ROOT, filePath);

        String[] extensionHelper = filePath.split("\\.");
        if(extensionHelper.length > 1) {
            this.contentType = vars.MIME_CODE_MEANING.get(".".concat(extensionHelper[1]));
        }else{
            this.contentType = "text/html";
        }


        this.contentLength = (int) file.length();
        this.httpCode = httpCode;

        this.dataResposta = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());

    }

    public httpResponse(String filePath, int httpCode, File file){
        this.file = file;

        String[] extensionHelper = filePath.split("\\.");
        if(extensionHelper.length > 1) {
            this.contentType = vars.MIME_CODE_MEANING.get(".".concat(extensionHelper[1]));
        }else{
            this.contentType = "text/html";
        }

        this.contentLength = (int) file.length();
        this.httpCode = httpCode;

        this.dataResposta = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());

    }

    public void send(PrintWriter out, BufferedOutputStream dataOut) throws IOException{
        if(!file.exists()) {
            throw new FileNotFoundException();
        }else {
            headerMaker(out);
            fileSender(dataOut);
        }
    }

    private void headerMaker(PrintWriter out){
        out.println("HTTP/1.1 " + httpCode + " " + vars.HTTP_CODE_MEANING.get(httpCode));
        out.println("Server: " + ServerConfig.SERVER_NAME + " : " + ServerConfig.SERVER_VERSION);
        out.println("Date: " + new Date());
        out.println("Content-Type: " + contentType);
        out.println("Content-Lenght: " + contentLength);
        out.println();

        out.flush();

    }

    private void fileSender(BufferedOutputStream dataOut){
        try{
            dataOut.write(readFileData(),0,contentLength);
            dataOut.flush();
        }catch(Exception ex){
            System.out.println(ex);
        }


    }

    private byte[] readFileData(){

        FileInputStream fileIn = null;

        byte[] fileData = new byte[this.contentLength];
        try{
            fileIn  = new FileInputStream(file);
            fileIn.read(fileData);

        }catch(Exception ex){
            System.out.println(ex);
        }

        finally {
            if(fileIn != null){
                try{
                    fileIn.close();
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
        }

        return fileData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
    public String getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(String dataResposta) {
        this.dataResposta = dataResposta;
    }
}
