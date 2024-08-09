package com.example.printsystem.util.lib;

import com.example.printsystem.models.entity.Delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryOptimizer {
    private final DistanceCalculator distanceCalculator = new DistanceCalculator();

    private Double[] getCoordinates(String address) throws Exception {
        // Example placeholder for geocoding service call
        // Replace with actual geocoding logic
        return GeocodingService.getCoordinates(address);
    }

    public List<List<Delivery>> performClustering(List<Delivery> deliveries, int numberOfShipper) throws Exception {
        List<DeliveryWithCoordinates> deliveriesWithCoordinates = new ArrayList<>();

        // Convert delivery addresses to coordinates and store them
        for (Delivery delivery : deliveries) {
            Double[] coords = getCoordinates(delivery.getDeliveryAddress());
            deliveriesWithCoordinates.add(new DeliveryWithCoordinates(delivery, coords[0], coords[1]));
        }

        List<List<DeliveryWithCoordinates>> clusters = new ArrayList<>();
        for (int i = 0; i < numberOfShipper; i++) {
            clusters.add(new ArrayList<>());
        }

        // Simple clustering by nearest neighbor
        for (DeliveryWithCoordinates deliveryWithCoords : deliveriesWithCoordinates) {
            int bestClusterIndex = -1;
            double minDistance = Double.MAX_VALUE;

            for (int i = 0; i < numberOfShipper; i++) {
                List<DeliveryWithCoordinates> cluster = clusters.get(i);
                double clusterDistance = 0;
                if (!cluster.isEmpty()) {
                    DeliveryWithCoordinates clusterCenter = cluster.get(0); // Using the first delivery as center
                    clusterDistance = distanceCalculator.calculateDistance(
                            deliveryWithCoords.getLatitude(), deliveryWithCoords.getLongitude(),
                            clusterCenter.getLatitude(), clusterCenter.getLongitude()
                    );
                }

                if (clusterDistance < minDistance) {
                    minDistance = clusterDistance;
                    bestClusterIndex = i;
                }
            }

            List<DeliveryWithCoordinates> chosenCluster = clusters.get(bestClusterIndex);
            if (chosenCluster.size() < numberOfShipper) {
                chosenCluster.add(deliveryWithCoords);
            } else {
                // If the chosen cluster is full, try to find another cluster
                for (int i = 0; i < numberOfShipper; i++) {
                    if (clusters.get(i).size() < numberOfShipper) {
                        clusters.get(i).add(deliveryWithCoords);
                        break;
                    }
                }
            }
        }

        // Convert clusters of DeliveryWithCoordinates back to clusters of Delivery
        return clusters.stream()
                .map(cluster -> cluster.stream().map(DeliveryWithCoordinates::getDelivery).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
