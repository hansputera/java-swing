
package tech.thehanifs;

import java.io.IOException;
import java.util.Arrays;
import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



/**
 *
 * @author hanif
 */
public class SiswaRequest {
    String url;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public final OkHttpClient client = new OkHttpClient.Builder()
            .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
            .build();
    SiswaRequest(){
        this.url = "https://data-siswa.glitch.me";
    }
    
    boolean masukan(String username, String password, String fullName) {
        try {
            var jsonLike = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"full_name\": \"%s\" }", username, password, fullName);
            RequestBody body = RequestBody.create(this.JSON, jsonLike);
            Request request = new Request.Builder()
                    .url(this.url + "/siswa")
                    .post(body)
                    .build();
            boolean hasil = false;
            try(Response response = this.client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    hasil = false;
                } else {
                    String bodyHasil = response.body().string();
                    hasil = !bodyHasil.contains("exists");
                }   
            }
            return hasil;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}

