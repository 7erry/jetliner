
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Created by terrywalters on 5/4/17.
 */
public class Jetliner {

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        /**
         * Parse our arguments
         */
        OptionParser parser = new OptionParser();
        parser.accepts( "pub" );
        parser.accepts( "sub" );
        parser.accepts( "comp" );
        parser.accepts( "pipe" );
        parser.accepts( "mgr" );
        parser.accepts( "usage" );

        OptionSet options = parser.parse( args );

        if(options.has("usage")){
            printUsage();
        }else
        if(options.has("pub")) {
            // remove pub option
            for (int i = 0; i < args.length; i++) {
                if(args[i].equals("-pub"))
                    args[i]="";
            }

            Publisher publisher = new Publisher();
            publisher.main(args);
        }else
        if(options.has("sub")) {
            // remove pub option
            for (int i = 0; i < args.length; i++) {
                if(args[i].equals("-sub"))
                    args[i]="";
            }
            Consumer consumer = new Consumer();

            consumer.main(args);
        }else
        if(options.has("mgr")) {
            // remove mgr option
            for (int i = 0; i < args.length; i++) {
                if(args[i].equals("-mgr"))
                    args[i]="";
            }
            JobManager mgr = new JobManager();
            mgr.main(args);
        }else{
            printBanner();
            System.out.println("No parameters found. See usage -usage");
        }

    }
    public static void printBanner(){
        System.out.println("\t\t\t\tJetLiner");
        System.out.println("\t\t\tpowered by Hazelcast Jet");
        System.out.println("\t");

    }
    public static void printUsage(){
        printBanner();
        System.out.println("\t-pub to publish");
        System.out.println("\t\t-pipe <name>");
        System.out.println("\t-sub to subscribe");
        System.out.println("\t\t-pipe <name>");
        System.out.println("\t-mgr to manage a job");
        System.out.println("\t\t-job <name>                         identifies the job");
        System.out.println("\t\t-start");
        System.out.println("\t\t-stop");
        System.out.println("\t\t-kill");
        System.out.println("\t\t-status");
        System.out.println("\t\t-exec");
        System.out.println("\t\t-c,--class <classname>              Class with the program entry\n" +
                "                                                    point (\"main\" method or\n" +
                "                                                    \"buildDag()\" method. Only\n" +
                "                                                    needed if the JAR file does\n" +
                "                                                    not specify the class in its\n" +
                "                                                    manifest.\n" +
                "\t\t-C,--classpath <url>                Adds a URL to each user code\n" +
                "                                                    classloader  on all nodes in\n" +
                "                                                    the cluster. The paths must\n" +
                "                                                    specify a protocol (e.g.\n" +
                "                                                    file://) and be accessible\n" +
                "                                                    on all nodes (e.g. by means\n" +
                "                                                    of a NFS share). You can use\n" +
                "                                                    this option multiple times\n" +
                "                                                    for specifying more than one\n" +
                "                                                    URL. The protocol must be\n" +
                "                                                    supported by the {@link\n" +
                "                                                    java.net.URLClassLoader}.");
        System.out.println("\t");
        printJet();
        System.out.println("\t\t");

    }
    public static void printJet(){
        System.out.println("\t\t\t\t    __!__");
        System.out.println("\t\t\t\t_____(_)_____");
        System.out.println("\t\t\t\t   !  !  !");

        // or
        //System.out.println("\t\t\t .-.    _,  .-.  ,_    .-.");
        //System.out.println("\t\t\t'-._'--'  \\_| |_/  '--'_.-'");
        //System.out.println("\t\t\t    '-._  \\ | | /  _.-'");
        //System.out.println("\t\t\t        `-.^| |^.-'");
        //System.out.println("\t\t\t           `\\=/`");


    }
}
