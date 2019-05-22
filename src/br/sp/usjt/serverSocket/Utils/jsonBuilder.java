package br.sp.usjt.serverSocket.Utils;

import com.google.gson.JsonObject;

public class jsonBuilder {


    //recebe uma string completa com os parametros que vieram com a requisicao
    //e os transforma em um arquivo JSon
    public static JsonObject requestCreate(String pathFile){
        JsonObject requestProps = new JsonObject();

        String[] props = pathFile.split("&");

            for(String prop : props){
                String[] subProp = prop.split("=");
                if(subProp.length > 1) {
                    requestProps.addProperty(subProp[0], subProp[1]);
                }
            }

        System.out.println(requestProps.toString());
        return requestProps;
    }
}
