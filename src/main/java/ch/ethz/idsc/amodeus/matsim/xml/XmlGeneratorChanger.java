/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodeus.matsim.xml;

import java.io.File;

public class XmlGeneratorChanger {
    ;

    /** Changes generator in "av.xml" file in @param simFolder to the value @param newGenerator
     * 
     * @throws Exception */
    public static void of(File simFolder, String newValue) throws Exception {
        XmlOperatorValueChanger.of(simFolder, "generator", newValue);
    }
}
