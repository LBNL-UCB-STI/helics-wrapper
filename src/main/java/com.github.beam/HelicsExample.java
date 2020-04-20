package com.github.beam;

import com.java.helics.SWIGTYPE_p_void;
import com.java.helics.helics;

public class HelicsExample {
    public static void main(String[] args) {
        HelicsLoader.load();

        System.out.println("HELICS Version: " + helics.helicsGetVersion());
        SWIGTYPE_p_void broker = helics.helicsCreateBroker("zmq", "", "-f 2 --name=BeamBrokerTemp");
        System.out.println(helics.helicsBrokerIsConnected(broker));
        helics.helicsBrokerDestroy(broker);
        System.out.println(helics.helicsBrokerIsConnected(broker));
        helics.helicsCloseLibrary();
    }
}
