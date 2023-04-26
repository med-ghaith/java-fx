package esprit.changemakers.sportshub.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

/**
 * @author Jozef
 */
public class UploadFileToServer {
    private static String statFileName="";
    private final String crlf = "\r\n";
    private URL url;
    private URLConnection urlConnection;
    private OutputStream outputStream;
    private InputStream inputStream;
    private String[] fileNames;
    private String output;
    private String boundary;
    private final int bufferSize = 4096;

    public UploadFileToServer(URL argUrl) {
        url = argUrl;
        boundary = "---------------------------9852662986326523";
    }

    public void setFileNames(String[] argFiles) {
        fileNames = argFiles;
    }

    public void post() {
        try {
            System.out.println("url:" + url);
            urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            String fileName = fileNames[0];
            String fileExtention = null;
            if(!statFileName.isEmpty()){
                String finalFileName = fileName;
                fileExtention = Optional.ofNullable(fileName)
                        .filter(f -> f.contains("."))
                        .map(f -> f.substring(finalFileName.lastIndexOf("."))).get();
            }


            InputStream fileInputStream = new FileInputStream(fileName);

            byte[] fileData = new byte[fileInputStream.available()];
            fileInputStream.read(fileData);

            // ::::: PART 1 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            String part1 = "";
            part1 += "--" + boundary + crlf;
            File f = new File(fileNames[0]);

            fileName = f.getName(); // we do not want the whole path, just the name
            if(statFileName.isEmpty()){
                part1 += "Content-Disposition: form-data; name=\"userfile\"; filename=\"" + fileName + "\""
                        + crlf;
            }else {
                part1 += "Content-Disposition: form-data; name=\"userfile\"; filename=\"" + statFileName + fileExtention + "\""
                        + crlf;
            }


            // CONTENT-TYPE
            // TODO: add proper MIME support here
            if (fileName.endsWith("png")) {
                part1 += "Content-Type: image/png" + crlf;
            } else if (fileName.endsWith("mp4")){
                part1 += "Content-Type: video/mp4" + crlf;
                System.out.println(part1);
            } else if (fileName.endsWith("mp3")){
                part1 += "Content-Type: audio/mpeg" + crlf;
            }else {
                part1 += "Content-Type: image/jpeg" + crlf;
            }


            part1 += crlf;
            System.out.println(part1);
            // File's binary data will be sent after this part

            // ::::: PART 2 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            String part2 = crlf + "--" + boundary + "--" + crlf;


            System.out.println("Content-Length"
                    + String.valueOf(part1.length() + part2.length() + fileData.length));
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(part1.length() + part2.length() + fileData.length));


            // ::::: File send ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            outputStream = urlConnection.getOutputStream();
            outputStream.write(part1.getBytes());

            int index = 0;
            int size = bufferSize;
            do {
                //System.out.println("wrote " + index + "b");
                if ((index + size) > fileData.length) {
                    size = fileData.length - index;
                }
                outputStream.write(fileData, index, size);
                index += size;
            } while (index < fileData.length);
            System.out.println("wrote " + index + "b");

            System.out.println(part2);
            outputStream.write(part2.getBytes());
            outputStream.flush();

            // ::::: Download result into the 'output' String :::::::::::::::::::::::::::::::::::::::::::::::
            inputStream = urlConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            char buff = 512;
            int len;
            byte[] data = new byte[buff];
            do {
                len = inputStream.read(data);
                if (len > 0) {
                    sb.append(new String(data, 0, len));
                }
            } while (len > 0);
            output = sb.toString();

            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Close connection");
            try {
                outputStream.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                inputStream.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    } // post() method

    public String getOutput() {
        return output;
    }


    /**
     * the fileUrl must be a full path
     * */
    public static void uploadFile(String fileUrl){
        System.out.println(fileUrl);
        UploadFileToServer httpPost = null;
        try {
            httpPost = new UploadFileToServer(new URL("http://localhost/upload.php"));
            httpPost.setFileNames(new String[]{fileUrl});
            httpPost.post();
            System.out.println("=======");
            System.out.println(httpPost.getOutput());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
    * this time you can add the newFile name but without the extention
    * the extention is added but the method
    * */
    public static void uploadFile(String fileUrl,String newFileName){
        statFileName = newFileName;
        System.out.println(fileUrl);
        UploadFileToServer httpPost = null;
        try {
            httpPost = new UploadFileToServer(new URL("http://localhost/upload.php"));
            httpPost.setFileNames(new String[]{fileUrl});
            httpPost.post();
            System.out.println("=======");
            System.out.println(httpPost.getOutput());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
