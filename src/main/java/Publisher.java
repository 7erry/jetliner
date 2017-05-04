import com.hazelcast.core.IAtomicLong;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.stream.IStreamMap;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.Scanner;

/**
 * Created by terrywalters on 5/4/17.
 */
public class Publisher {


    public static void main(String[] args) {

        /**
         * Parse our arguments
         */
        OptionParser parser = new OptionParser();
        parser.accepts( "pipe" ).withRequiredArg();
        //parser.accepts( "name" ).requiredIf( "pipe" ).withRequiredArg();

        OptionSet options = parser.parse( args );
        JetConfig config = new JetConfig();
        config.getHazelcastConfig().getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost");
        config.getHazelcastConfig().getGroupConfig().setName("JetLiner");
        JetInstance jet = Jet.newJetInstance(config);

        String pipeName = (String)options.valueOf("pipe");
        IStreamMap<Long, String> pipe = jet.getMap(pipeName);

        Scanner scan = new Scanner(System.in);
        IAtomicLong key = jet.getHazelcastInstance().getAtomicLong(pipeName);
        while(scan.hasNext()){
            pipe.put(key.addAndGet(1),scan.next()+" ");
        }

    }

}
