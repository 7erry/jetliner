/**
 * Created by terrywalters on 5/4/17.
 */
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;
import com.hazelcast.jet.*;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.stream.IStreamMap;

import static com.hazelcast.jet.impl.connector.ReadWithPartitionIteratorP.readMap;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.Future;

/**
 * Created by terrywalters on 5/4/17.
 */
public class JobManager {
    public static void main(String[] args)  {

        JobManager mgr = new JobManager();

        /**
         * Parse our arguments
         */
        OptionParser parser = new OptionParser();
        parser.accepts("job").withRequiredArg();
        parser.accepts( "start" );
        parser.accepts( "stop" );
        parser.accepts( "resume" );
        parser.accepts( "kill" );


        OptionSet options = parser.parse(args);

        JetConfig config = new JetConfig();
        config.getHazelcastConfig().getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        config.getHazelcastConfig().getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost");
        config.getHazelcastConfig().getGroupConfig().setName("JetLiner");
        JetInstance jet = Jet.newJetInstance(config);

        String jobName = (String) options.valueOf("job");
        if(options.has("start")) {
        }else if(options.has("stop")) {

        }else if(options.has("resume")) {

        }else if(options.has("kill")) {

        }else if(options.has("status")) {

        }else{
            Jetliner.printUsage();
        }


    }

    private boolean pauseJob(JetInstance jet, String jobName) throws InvocationTargetException, IllegalAccessException{
        IStreamMap<String, Job> jobs = jet.getMap("jobs");
        Job job = jobs.get(jobName);
        System.out.println("\tpausing:\t"+job.toString());
        return false;
    }

    private boolean resumeJob(JetInstance jet, String jobName) throws InvocationTargetException, IllegalAccessException{
        IStreamMap<String, Job> jobs = jet.getMap("jobs");
        Job job = jobs.get(jobName);
        System.out.println("\tresuming:\t"+job.toString());
        return false;
    }

    private boolean stopJob(JetInstance jet, String jobName) throws InvocationTargetException, IllegalAccessException{
        IStreamMap<String, Job> jobs = jet.getMap("jobs");
        Job job = jobs.get(jobName);
        System.out.println("\tstopping:\t"+job.toString());
        return false;
    }

    private void executeJob(JetInstance jet, String jobName) throws InvocationTargetException,
            IllegalAccessException, ClassNotFoundException, NoSuchMethodException, MalformedURLException{

        // evil reflection crap-o-la to enable dynamic class loading of user supplied Computation/Job
        String dagLib = "/Users/terrywalters/Documents/projects/hazelcast/stock-exchange/Computation.class";
        Class dagClass = loadClass("Computation",dagLib);
        Method buildDag = dagClass.getMethod("buildDag");

        //Parameter[] parameters = buildDag.getParameters();
        //parameters[0].

        // get user supplied DAD
        DAG dag = (DAG) buildDag.invoke(buildDag);
        Job job = jet.newJob(dag);

        // add job to our jobs map
        IStreamMap<String, Job> jobs = jet.getMap("jobs");
        jobs.put(jobName,job);
        Future fJob = job.execute();
        System.out.println("\texecuting:\t"+job.toString());
        System.out.println("");
        Jetliner.printJet();


        //fJob.cancel();


    }

    private static DAG buildDag(String pipeName) {
        DAG dag = new DAG();

        Vertex mapSource = dag.newVertex(pipeName, readMap(pipeName));

        return dag;
    }

    public Class loadClass(String name, String jarPath) throws ClassNotFoundException, MalformedURLException {
        try {

            URLClassLoader child = new URLClassLoader (new URL[] {new File(jarPath).toURI().toURL()}, this.getClass().getClassLoader());
            return Class.forName (name, true, child);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean stop(String name){
        return false;
    }

}
