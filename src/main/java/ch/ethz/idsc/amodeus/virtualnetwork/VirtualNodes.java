/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodeus.virtualnetwork;

/** contains helper functions */
enum VirtualNodes {
    ;
    private static final String ID_PREFIX = "vNode_";

    public static String getIdString(int index) {
        return ID_PREFIX + Integer.toString(index + 1);
    }
}
