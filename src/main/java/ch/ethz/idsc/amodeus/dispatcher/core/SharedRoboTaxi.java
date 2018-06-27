package ch.ethz.idsc.amodeus.dispatcher.core;

import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.dvrp.util.LinkTimePair;

import ch.ethz.idsc.amodeus.dispatcher.shared.SharedAVMealType;
import ch.ethz.idsc.amodeus.dispatcher.shared.SharedAVMenu;
import ch.ethz.idsc.amodeus.util.math.GlobalAssert;
import ch.ethz.matsim.av.data.AVVehicle;

public class SharedRoboTaxi extends AbstractRoboTaxi {

	private int onBoardCustomers = 0;
	private final SharedAVMenu menu;

	// private final Set<Id<Request>> onBoardRequests = new HashSet<>();

	/* package */ SharedRoboTaxi(AVVehicle avVehicle, LinkTimePair divertableLinkTime, Link driveDestination) {
		super(avVehicle, divertableLinkTime, driveDestination);
		menu = new SharedAVMenu();
	}

	/* package */ SharedRoboTaxi(SharedRoboTaxi sharedRoboTaxi) {
		super(sharedRoboTaxi);
		menu = sharedRoboTaxi.menu;
	}

	/* package */ void pickupNewCustomerOnBoard() {
		GlobalAssert.that(canPickupNewCustomer());
		GlobalAssert.that(menu.getSharedAVStarter().getPickupOrDropOff().equals(SharedAVMealType.PICKUP));
		onBoardCustomers++;
		menu.removeAVCourse(0);

	}

	public boolean canPickupNewCustomer() {
		return onBoardCustomers >= 0 && onBoardCustomers < (int) avVehicle.getCapacity();
	}
	// public boolean canPickupNewCustomer() {
	// return getNumberOfCustomersOnBoard() >= 0 && getNumberOfCustomersOnBoard() <
	// (int) avVehicle.getCapacity();
	// }

	/* package */ void dropOffCustomer() {
		GlobalAssert.that(onBoardCustomers > 0);
		GlobalAssert.that(onBoardCustomers <= (int) avVehicle.getCapacity());
		GlobalAssert.that(menu.getSharedAVStarter().getPickupOrDropOff().equals(SharedAVMealType.DROPOFF));
		onBoardCustomers--;
		menu.removeAVCourse(0);
	}

	public int getCurrentNumberOfCustomersOnBoard() {
		return onBoardCustomers;
	}

	public SharedAVMenu getMenu() {
		return menu;
	}

	// public void pickupCustomer(Id<Request> requestId) {
	// GlobalAssert.that(canPickupNewCustomer());
	// GlobalAssert.that(!onBoardRequests.contains(requestId));
	// GlobalAssert.that(menu.containsPickupCourse(requestId));
	// onBoardRequests.add(requestId);
	// }

	// public void pickUpStarter() {
	// SharedAVCourse sharedAVCourse = menu.getSharedAVStarter();
	// GlobalAssert.that(sharedAVCourse != null);
	// GlobalAssert.that(sharedAVCourse.getPickupOrDropOff().equals(SharedAVMealType.PICKUP));
	// pickupCustomer(sharedAVCourse.getRequestId());
	// }

	// public void dropOffCustomer(Id<Request> requestId) {
	// GlobalAssert.that(onBoardRequests.contains(requestId));
	// GlobalAssert.that(getNumberOfCustomersOnBoard() > 0);
	// onBoardRequests.remove(requestId);
	// }

	// public void dropOffStarter() {
	// SharedAVCourse sharedAVCourse = menu.getSharedAVStarter();
	// GlobalAssert.that(sharedAVCourse != null);
	// GlobalAssert.that(sharedAVCourse.getPickupOrDropOff().equals(SharedAVMealType.DROPOFF));
	// dropOffCustomer(sharedAVCourse.getRequestId());
	// }

	// public int getNumberOfCustomersOnBoard() {
	// return onBoardRequests.size();
	// }

}
