package ngestiwaluyo.com.ngestiwaluyomobile.main.utility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by Wendra on 10/9/2017.
 */

public class WebServicesUtil {
    private static String userName= "RSByyk";
    private static String password ="Rsb2020";
    public static String getServiceUrl() {
        //release version
       // final String SERVICE_URL = "http://180.214.244.190:8090/API/";
        //testing version
        //final String SERVICE_URL = "http://180.214.244.190:8010/API/";
        // ganti ke domain
        final String SERVICE_URL = "http://rs.ngestiwaluyo.com/wsapirmpass/api/";
        return SERVICE_URL;
    }

    public String getData()
    {
        return "test123";
    }
   public static OkHttpClient connect() {
        final OkHttpClient client;

        client = new OkHttpClient.Builder()
                .authenticator(getBasicAuth(userName, password))
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();

        return client;
    }

   /* public static OkHttpClient connect()  {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.authenticator(getBasicAuth(userName,password));
            builder.connectTimeout(90, TimeUnit.SECONDS);
            builder.writeTimeout(120, TimeUnit.SECONDS);
            builder.readTimeout(60, TimeUnit.SECONDS).build();
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });


            OkHttpClient okHttpClient = builder.build();


            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    protected static Authenticator getBasicAuth(final String username, final String password) {
        return new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        };
    }
}
