package Pages;

import java.util.ArrayList;
import java.util.List;

public class LocationDetails {

    private final List<String> activeLocation = new ArrayList<>(List.of(
            "190A1011",
            "190A1021"
    ));

    private final List<String> reserveLocation = new ArrayList<>(List.of(
            "190R1011",
            "190R1021"
    ));

    private int activeLocationIndex = 0;
    private int reserveLocationIndex = 0;

    public String getNextActiveLocation() {

        if (activeLocation.isEmpty()) {
            throw new IllegalStateException(
                    "No active location available for Putaway"
            );
        }

        String location = activeLocation.get(activeLocationIndex);

        activeLocationIndex =
                (activeLocationIndex + 1) % activeLocation.size();

        return location;
    }

    public String getNextReserveLocation() {

        if (reserveLocation.isEmpty()) {
            throw new IllegalStateException(
                    "No reserve location available for Putaway"
            );
        }

        String location = reserveLocation.get(reserveLocationIndex);

        reserveLocationIndex =
                (reserveLocationIndex + 1) % reserveLocation.size();

        return location;
    }
}