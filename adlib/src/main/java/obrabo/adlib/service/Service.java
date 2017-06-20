package obrabo.adlib.service;
import java.io.IOException;
import obrabo.adlib.helper.Global;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Service {

    static Response response = null;
    static  OkHttpClient client = new OkHttpClient();
    static  String result = "";
    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /* ============== POST - Advert ================ */
    public static String advert() {

        FormBody formBody = new FormBody.Builder()
                .add("idFace", Global.idUser)
                .add("device_key", Global.keyAPI)
                .build();

        Request request = new Request.Builder()
                .url(Global.urlAdvert)
                .post(formBody)
                .build();

        try {
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
