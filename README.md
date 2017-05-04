# jetliner 



The missing command line interface for Jet.


				JetLiner
			powered by Hazelcast Jet
	
	
	-ip <ip address>                      identifies the member ip address
	-pub to publish
		-pipe <name>
	-sub to subscribe
		-pipe <name>
	-mgr to manage a job
		-job <name>                         identifies the job
		-start
		-stop
		-kill
		-status
		-exec
		-c,--class <classname>              Class with the program entry
                                                    point ("main" method or
                                                    "buildDag()" method. Only
                                                    needed if the JAR file does
                                                    not specify the class in its
                                                    manifest.
		-C,--classpath <url>                Adds a URL to each user code
                                                    classloader  on all nodes in
                                                    the cluster. The paths must
                                                    specify a protocol (e.g.
                                                    file://) and be accessible
                                                    on all nodes (e.g. by means
                                                    of a NFS share). You can use
                                                    this option multiple times
                                                    for specifying more than one
                                                    URL. The protocol must be
                                                    supported by the {@link
                                                    java.net.URLClassLoader}.
	
				    __!__
				_____(_)_____
				   !  !  !
		
