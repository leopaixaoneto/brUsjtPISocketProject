package br.sp.usjt.serverSocket.Model;

import br.sp.usjt.serverSocket.Utils.jsonBuilder;
import com.google.gson.JsonObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class httpRequest {

    private String originAddress;

    private String method;
    private String pathFile;

    private String dataRequisicao;

    private JsonObject props;


    public httpRequest() {
    }

    public httpRequest(String originAddress, String method, String pathFile, Date dataRequisicao) {
        this.originAddress = originAddress;
        this.method = method;
        this.pathFile = pathFile;
        this.dataRequisicao = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(dataRequisicao);
    }

    public httpRequest(String request, String originAddress){

        StringTokenizer parse = new StringTokenizer(request);

        this.method = parse.nextToken().toUpperCase();

        String[] pathAux = parse.nextToken().split("\\?");

        if(pathAux.length > 1){
            this.props = jsonBuilder.requestCreate(pathAux[1]);
        }else{
            this.props = null;
        }

        this.pathFile = pathAux[0];

        this.originAddress = originAddress;

        this.dataRequisicao = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());

        System.out.println("Method: " + method);
        System.out.println("Arquivo Requisitado: " + pathFile);
        System.out.println("ip: " + originAddress);
        System.out.println("Data: " + dataRequisicao);

    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(String dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao){
        this.dataRequisicao = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(dataRequisicao);
    }


    public JsonObject getProps() {
        return props;
    }

    public void setProps(JsonObject props) {
        this.props = props;
    }

}
