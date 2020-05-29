package org.matsim.amodeus.components;

import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.passenger.PassengerRequest;
import org.matsim.contrib.dvrp.run.ModalProviders;

public interface AVDispatcher {
    void onRequestSubmitted(PassengerRequest request);

    void onNextTaskStarted(DvrpVehicle vehicle);

    void onNextTimestep(double now);

    void addVehicle(DvrpVehicle vehicle);

    interface AVDispatcherFactory {
        AVDispatcher createDispatcher(ModalProviders.InstanceGetter inject);
    }
}
