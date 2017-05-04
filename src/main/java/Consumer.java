import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.stream.IStreamMap;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terrywalters on 5/4/17.
 */
public class Consumer {
    public static void main(String[] args) {

        /**
         * Parse our arguments
         */
        OptionParser parser = new OptionParser();
        parser.accepts("pipe").withRequiredArg();
        //parser.accepts( "name" ).requiredIf( "pipe" ).withRequiredArg();

        OptionSet options = parser.parse(args);

        JetConfig config = new JetConfig();
        config.getHazelcastConfig().getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost");
        config.getHazelcastConfig().getGroupConfig().setName("JetLiner");
        JetInstance jet = Jet.newJetInstance(config);

        String pipeName = (String) options.valueOf("pipe");
        IStreamMap<Long, String> pipe = jet.getMap(pipeName);
        Map<Long,Boolean> printed = new HashMap<>();

        while (true) {
            for (Map.Entry<Long, String> entry : pipe.entrySet()) {
                if(printed.get(entry.getKey()) == null) {
                    System.out.print(entry.getValue());
                    printed.put(entry.getKey(),true);
                }
            }
            Thread.yield();
        }
    }
}
