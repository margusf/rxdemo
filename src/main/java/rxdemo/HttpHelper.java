package rxdemo;

import org.apache.commons.io.IOUtils;

import java.net.URL;

class HttpHelper {
    public static String doGet(String url) throws Exception {
        return IOUtils.toString(new URL(url));
    }
}


// 0835385142505861236af08685de3c1f