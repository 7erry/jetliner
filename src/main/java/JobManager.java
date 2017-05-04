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
    public static void main(String[] args) throws InstantiationException {

        JobManager mgr = new JobManager();

        /**
         * Parse our arguments
         */
        OptionParser parser = new OptionParser();
        parser.accepts("job").withRequiredArg();
        parser.accepts( "exec" );
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

        if(options.has("exec")) {
            Jetliner.printBanner();

            try {
                System.out.println("jobName:\t"+jobName);
                mgr.executeJob(jet,jobName);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Jetliner.printJet();
        }else if(options.has("start")) {
            Jetliner.printBanner();
            System.out.println("\t\tNot currently implemented");
            Jetliner.printJet();
        }else if(options.has("stop")) {
            Jetliner.printBanner();
            System.out.println("\t\tNot currently implemented");
            Jetliner.printJet();
        }else if(options.has("resume")) {
            Jetliner.printBanner();
            System.out.println("\t\tNot currently implemented");
            Jetliner.printJet();
        }else if(options.has("kill")) {
            Jetliner.printBanner();
            System.out.println("\t\tNot currently implemented");
            Jetliner.printJet();
        }else if(options.has("status")) {
            Jetliner.printBanner();
            System.out.println("\t\tNot currently implemented");
            Jetliner.printJet();
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
            IllegalAccessException, ClassNotFoundException, NoSuchMethodException, MalformedURLException, InstantiationException {


        /**
         * // 36 cluster
         Class client36config = loadClass("com.hazelcast.client.config.ClientConfig",lib36);
         Object client36Config = client36config.newInstance();
         Class client36hazelcast = loadClass("com.hazelcast.client.HazelcastClient",lib36);
         Method H36 = client36hazelcast.getMethod("newHazelcastClient");
         Object h36 = H36.invoke(client36Config);
         Method getMap36 = h36.getClass().getMethod("getMap", String.class);
         */

        //Parameter[] parameters = buildDag.getParameters();
        //parameters[0].

        // get user supplied DAD
        // evil reflection crap-o-la to enable dynamic class loading of user supplied Computation/Job
        String dagLib = "/Users/terrywalters/Documents/projects/hazelcast/stock-exchange/target/stock-exchange-1.0-SNAPSHOT.jar";
        Class dagClass = loadClass("Computation",dagLib);
        Object oDag = dagClass.newInstance();
        Method buildDag = dagClass.getMethod("buildDag");
        DAG dag = (DAG) buildDag.invoke(oDag);

        // add job to our jobs map
        //IStreamMap<String, Job> jobs = jet.getMap("jobs");
        //jobs.put(jobName,job);

        // run this job
        Job job = jet.newJob(dag);
        Future fJob = job.execute();
        while(fJob.isDone()){
            Thread.yield();
        }
        // print job

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
