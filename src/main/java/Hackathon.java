
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Created by terrywalters on 5/4/17.
 */
public class Hackathon {

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
        if(options.has("comp")) {
            // remove pub option
            for (int i = 0; i < args.length; i++) {
                if(args[i].equals("-comp"))
                    args[i]="";
            }
            Computation computation = new Computation();
            computation.main(args);
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
        System.out.println("\t-sub to subscribe");
        System.out.println("\t-comp to computation");
        System.out.println("\t-pipe (name)");
        System.out.println("\t");
        System.out.println("\t");

    }
}
