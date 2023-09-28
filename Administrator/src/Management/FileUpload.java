package Management;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class FileUpload
{

    public static void main(String[] args) throws IOException
    {

        String RESOURCE = "/upload-file";


        File f = new File("src/resources/some-file.txt");

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/plain")))
                        //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                        .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        System.out.println(response.body().string());
    }
}

