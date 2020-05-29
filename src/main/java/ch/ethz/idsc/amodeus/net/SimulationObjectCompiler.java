/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodeus.net;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.dvrp.passenger.PassengerRequest;

import ch.ethz.idsc.amodeus.dispatcher.core.RequestStatus;
import ch.ethz.idsc.amodeus.dispatcher.core.RoboTaxi;
import ch.ethz.idsc.amodeus.util.math.GlobalAssert;

public class SimulationObjectCompiler {
    private final SimulationObject simulationObject;
    private final Map<String, VehicleContainer> vehicleMap = new HashMap<>();
    private final Map<String, RequestContainer> requestMap = new HashMap<>();

    public static SimulationObjectCompiler create( //
            long now, String infoLine, int total_matchedRequests, //
            MatsimAmodeusDatabase db) {
        SimulationObject simulationObject = new SimulationObject();
        simulationObject.iteration = db.getIteration();
        simulationObject.now = now;
        simulationObject.infoLine = infoLine;
        simulationObject.total_matchedRequests = total_matchedRequests;
        return new SimulationObjectCompiler(simulationObject);
    }

    private SimulationObjectCompiler(SimulationObject simulationObject) {
        GlobalAssert.that(Objects.nonNull(simulationObject));
        this.simulationObject = simulationObject;
    }

    public void insertRequests(Collection<PassengerRequest> requests, RequestStatus status) {
        requests.forEach(r -> insertRequest(r, status));
    }

    public void insertRequests(Map<PassengerRequest, RequestStatus> requestStatuses) {
        requestStatuses.forEach(this::insertRequest);
    }

    public void insertVehicles(List<RoboTaxi> roboTaxis) {
        roboTaxis.forEach(rt -> insertVehicle(rt, Collections.singletonList(rt.getLastKnownLocation())));
    }

    public void insertVehicles(Map<RoboTaxi, List<Link>> tempLocationTrace) {
        tempLocationTrace.forEach(this::insertVehicle);
    }

    private void insertRequest(PassengerRequest avRequest, RequestStatus requestStatus) {
        String id = avRequest.getId().toString();
        if (requestMap.containsKey(id)) {
            requestMap.get(id).requestStatus.add(requestStatus);
        } else {
            RequestContainer requestContainer = RequestContainerCompiler.compile(avRequest, requestStatus);
            requestMap.put(id, requestContainer);
        }
    }

    private void insertVehicle(RoboTaxi roboTaxi, List<Link> tempTrace) {
        VehicleContainer vehicleContainer = VehicleContainerCompiler.compile(roboTaxi, tempTrace);
        final String key = roboTaxi.getId().toString();
        vehicleMap.put(key, vehicleContainer);
    }

    public void addRequestRoboTaxiAssoc(Map<PassengerRequest, RoboTaxi> map) {
        map.forEach((k, v) -> {
            String id = k.getId().toString();
            if (requestMap.containsKey(id))
                requestMap.get(id).associatedVehicle = v.getId().index();
        });
    }

    public SimulationObject compile() {
        simulationObject.vehicles = new ArrayList<>(vehicleMap.values());
        simulationObject.requests = new ArrayList<>(requestMap.values());
        return simulationObject;
    }
}
