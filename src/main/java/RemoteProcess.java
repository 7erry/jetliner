import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by terrywalters on 5/4/17.
 */
public class RemoteProcess {
        public static void main(String args[])
                throws InterruptedException,IOException
        {
            List<String> command = new ArrayList<String>();
            command.add(args[0]);


            ProcessBuilder builder = new ProcessBuilder(command);
            Map<String, String> environ = builder.environment();

            final Process process = builder.start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Program terminated!");
        }

}
