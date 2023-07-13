import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class App
{
    public App(String t,String p,String m,String i){
        String token=t;
       String projectId=p;
       String modelName=m;
       String imageFilename=i;
    }
   public static void main( String[] args ) throws IOException
   {
       if (args.length < 4) {
           System.out.println("Usage: java -jar sample.jar api_token project_id model_name file");
       }
       String token;
       String projectId;
       String modelName;
       String imageFilename;

       byte[] bytes = Files.readAllBytes(new File(imageFilename).toPath());

       URL url = new URL("https://platform.sentisight.ai/api/predict/" + projectId + "/" + modelName);
       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
       connection.setRequestProperty("Content-Type", "application/octet-stream");
       connection.setRequestProperty("X-Auth-token", token);
       connection.setRequestMethod("POST");
       connection.setDoOutput(true);
       DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(bytes);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            System.out.println(output);
            response.append(output);
        }
        in.close();
   }
}
