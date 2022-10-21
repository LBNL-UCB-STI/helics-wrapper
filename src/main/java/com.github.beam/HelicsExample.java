package com.github.beam;

import com.java.helics.HelicsDataTypes;
import com.java.helics.SWIGTYPE_p_void;
import com.java.helics.helics;
import com.java.helics.helicsJNI;

public class HelicsExample {
    public static void main(String[] args) throws InterruptedException {
        HelicsLoader.load();

        System.out.println("HELICS loaded. Version: " + helics.helicsGetVersion());
        SWIGTYPE_p_void broker = helics.helicsCreateBroker("zmq", "", "-f 2 --name=BeamBrokerTemp");

        if (helics.helicsBrokerIsConnected(broker) == 1) System.out.println("Broker is connected");
        else System.out.println("ERROR! Broker is NOT connected");

        testMessageSendAndTimeSync();

        sleep();
        System.out.println("Destroying broker ...");
        helics.helicsBrokerDestroy(broker);

        if (helics.helicsBrokerIsConnected(broker) != 1) System.out.println("Broker is disconnected");
        else System.out.println("ERROR! Broker is STILL connected");

        System.out.println("Closing the library");
        helics.helicsCloseLibrary();

        System.out.println("Done");
    }

    private static void testMessageSendAndTimeSync() throws InterruptedException {
        SWIGTYPE_p_void fedInfo = getFederateInfo();
        String fedName = "FED_BEAM_1";
        SWIGTYPE_p_void fedComb = helics.helicsCreateCombinationFederate(fedName, fedInfo);

        String publicationName = "CHARGING_VEHICLES";

        HelicsDataTypes dataType = HelicsDataTypes.swigToEnum(helicsJNI.HELICS_DATA_TYPE_STRING_get());
        SWIGTYPE_p_void publication = helics.helicsFederateRegisterPublication(fedComb, publicationName, dataType, "");
        System.out.println("Registered publication.");

        String longMessage = "To enter execution mode we need to have python scripts manually running "
                + "(beam_pydss_broker.py and site_power_controller_federate.py)."
                + "Entering execution mode ...";
        System.out.println(longMessage);
        helics.helicsFederateEnterExecutingMode(fedComb);
        System.out.println("Entered execution mode.");

        sleep();
        System.out.println("Publishing a string ...");
        helics.helicsPublicationPublishString(publication, "{\"some value\": 1234, \"another value\":\"test test'}");

        sleep();
        System.out.println("Requesting time ...");
        double requestedTime = 12.0 * 60.0 * 60.0;
        double respondedTime = helics.helicsFederateRequestTime(fedComb, requestedTime);
        System.out.println("Requested time " + requestedTime + " responded time from helics " + respondedTime);
    }

    private static void sleep() throws InterruptedException {
        int secondsToSleep = 1;
        System.out.println(".. " + secondsToSleep + " secs ..");
        Thread.sleep(secondsToSleep * 1000L);
    }

    public static SWIGTYPE_p_void getFederateInfo() {
        return getFederateInfo("zmq", "--federates=1 --broker_address=tcp://127.0.0.1", 1.0, 1);
    }

    public static SWIGTYPE_p_void getFederateInfo(String coreType, String coreInitString, Double timeDeltaProperty, Integer intLogLevel) {
        SWIGTYPE_p_void fedInfo = helics.helicsCreateFederateInfo();
        helics.helicsFederateInfoSetCoreTypeFromString(fedInfo, coreType);
        helics.helicsFederateInfoSetCoreInitString(fedInfo, coreInitString);
        helics.helicsFederateInfoSetTimeProperty(fedInfo, helicsJNI.HELICS_PROPERTY_TIME_DELTA_get(), timeDeltaProperty);
        helics.helicsFederateInfoSetIntegerProperty(fedInfo, helicsJNI.HELICS_PROPERTY_INT_LOG_LEVEL_get(), intLogLevel);
        return fedInfo;
    }
}
