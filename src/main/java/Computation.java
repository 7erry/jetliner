import com.hazelcast.jet.*;



/**
 * Created by terrywalters on 5/4/17.
 */
public abstract class Computation {

    public abstract DAG buildDag();

}
