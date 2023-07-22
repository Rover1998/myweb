import com.haowei.server.BlogHttpHandler;
import com.haowei.server.SeparateImageHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyApplication {
    public static Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);
    public static HttpServer httpServer;
    private static final String dateFormat = "yyyy-MM-dd HH-mm-ss,SSS,XX";

    public static void main(String[] args) throws Exception {
        LOGGER.info("Start MyApplication initialization at: {}", getFormattedDate());
        addShutdownHook();
        int port = 8080;
        httpServer = HttpServer.create(new InetSocketAddress(port), 100);
        httpServer.createContext("/", new BlogHttpHandler());
        httpServer.createContext("/image", new SeparateImageHandler());
        httpServer.setExecutor(null);
        httpServer.start();
        LOGGER.info("WebServer Start at port: {}", port);
    }

    private static String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date();
        return sdf.format(date);
    }

    public static void addShutdownHook() {
        Runnable runnable = () -> {
            LOGGER.info("System shut down, stop webserver....");
            httpServer.stop(0);
        };
        Thread thread = new Thread(runnable);
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
