import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoadTest {

    @Test
    public void testReadFile() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("404.jsp");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        br.lines().forEach(sb::append);
        br.close();
        String response = sb.toString();
        System.out.println(response);
    }
}
