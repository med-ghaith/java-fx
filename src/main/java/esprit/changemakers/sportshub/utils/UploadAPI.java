/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;


/**
 *
 * @author Bilel
 */
public class UploadAPI {
    public static String UPLOAD_PATH = "http://localhost/uploads/images/";

    public static String upload(File x) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String fileName = "";
        try {
            HttpPost httppost = new HttpPost("http://localhost/uploads/savetofile.php");

            FileBody bin = new FileBody(x);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("myFile", bin)
                    .build();

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {                                        
                    String responseXml = EntityUtils.toString(resEntity);
                    fileName+= responseXml;
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return fileName;
    }

}
