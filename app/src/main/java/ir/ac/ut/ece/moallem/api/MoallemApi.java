package ir.ac.ut.ece.moallem.api;

import ir.ac.ut.ece.moallem.api.endpoint.CourseEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.StudentEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.TeacherEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockedCourseEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockedStudentEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockedTeacherEndpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by mushtu on 3/21/17.
 */
public class MoallemApi {

    private final int READ_TIMEOUT = 15;
    private final int CONNECT_TIMEOUT = 15;
    private final int WRITE_TIMEOUT = 15;
    private final String BASE_URL = "https://google.com"; //TODO
    private final boolean USE_MOCK_ENDPOINTS = true;
    private static volatile MoallemApi instance;

    private Retrofit retrofit;
    private Map<Class<?>, Object> endpointInstances = new HashMap<Class<?>, Object>();
    private MockRetrofit mockRetrofit;
    private Map<Class<?>, Object> mockedEndpoints = new HashMap<>();

    public static MoallemApi getInstance() {
        if (instance == null) {
            synchronized (MoallemApi.class) {
                if (instance == null) {
                    instance = new MoallemApi();
                }
            }
        }
        return instance;
    }

    private MoallemApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        String UA = System.getProperty("http.agent");  // Get android user agent.
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        NetworkBehavior networkBehavior = NetworkBehavior.create();
        networkBehavior.setErrorPercent(0);
        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior)
                .build();
        buildMockEndpoints();
    }

    private void buildMockEndpoints() {
        BehaviorDelegate delegate = mockRetrofit.create(CourseEndpoint.class);
        mockedEndpoints.put(CourseEndpoint.class, new MockedCourseEndpoint(delegate));
        mockedEndpoints.put(TeacherEndpoint.class, new MockedTeacherEndpoint(mockRetrofit.create(TeacherEndpoint.class)));
        mockedEndpoints.put(StudentEndpoint.class, new MockedStudentEndpoint(mockRetrofit.create(StudentEndpoint.class)));
    }

    public synchronized <T> T endpoint(Class<T> endpoint) {
        if (USE_MOCK_ENDPOINTS) {
            if (!mockedEndpoints.containsKey(endpoint))
                throw new EndpointNotImplementedException();
            return (T) mockedEndpoints.get(endpoint);
        }
        if (!endpointInstances.containsKey(endpoint))
            endpointInstances.put(endpoint, retrofit.create(endpoint));
        return (T) endpointInstances.get(endpoint);
    }

}
