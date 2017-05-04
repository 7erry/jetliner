import com.hazelcast.jet.DAG;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Vertex;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.stream.IStreamMap;

import static com.hazelcast.jet.impl.connector.ReadWithPartitionIteratorP.readMap;
import static com.hazelcast.jet.windowing.PunctuationPolicies.cappingEventSeqLagAndRetention;
import static com.hazelcast.jet.windowing.WindowDefinition.slidingWindowDef;
import static com.hazelcast.jet.windowing.WindowOperations.counting;
import static com.hazelcast.jet.windowing.WindowingProcessors.groupByFrame;
import static com.hazelcast.jet.windowing.WindowingProcessors.insertPunctuation;
import static com.hazelcast.jet.windowing.WindowingProcessors.slidingWindow;
/**
 *
 import com.hazelcast.jet.Vertex;
import com.hazelcast.jet.DAG;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Vertex;
import com.hazelcast.jet.windowing.Frame;
import com.hazelcast.jet.windowing.WindowDefinition;
import static com.hazelcast.jet.Edge.between;
import static com.hazelcast.jet.Partitioner.HASH_CODE;
import static com.hazelcast.jet.Processors.map;

import static com.hazelcast.jet.Processors.writeFile;
 */

import com.hazelcast.jet.windowing.Frame;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Created by terrywalters on 5/4/17.
 */
public class Computation {
    public static void main(String[] args) {
        /**
         * Parse our arguments
         */
        OptionParser parser = new OptionParser();
        parser.accepts("pipe").withRequiredArg();

        OptionSet options = parser.parse(args);

        JetConfig config = new JetConfig();
        config.getHazelcastConfig().getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost");
        config.getHazelcastConfig().getGroupConfig().setName("JetLiner");
        JetInstance jet = Jet.newJetInstance(config);

        String pipeName = (String) options.valueOf("pipe");
        IStreamMap<Long, String> pipe = jet.getMap(pipeName);

//        try {
//            loadTickers(jet);
//            jet.newJob(buildDag()).execute();
//            Thread.sleep(10_000);
//        } finally {
//            Jet.shutdownAll();
//        }
    }

    private static DAG buildDag(String pipeName) {
        DAG dag = new DAG();

        Vertex mapSource = dag.newVertex(pipeName, readMap(pipeName));
        
	return dag;
    }
}
