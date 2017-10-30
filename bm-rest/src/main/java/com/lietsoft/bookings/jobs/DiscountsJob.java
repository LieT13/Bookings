package com.lietsoft.bookings.jobs;

import com.lietsoft.bookings.model.Flight;
import com.lietsoft.bookings.model.Discount;
import com.lietsoft.bookings.service.FlightsService;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class DiscountsJob implements Job {

    private static final String AVAILABLE_DISCOUNTS_FILE = "discounts.json";

    private FlightsService flightsService;

    public DiscountsJob() {
        flightsService = new FlightsService();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Updating flight offers...");

        try {
            List<Discount> availableDiscounts = getAvailableDiscounts();
            availableDiscounts.forEach(discount -> {
                Stream<Flight> discountedFlights = flightsService.getAllFlights().values().stream().
                        filter(df -> discount.getCity().equals(df.getOutbound().getDestination()));
                discountedFlights.forEach(flight -> applyDiscountToFlight(flight, discount));
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new JobExecutionException("Error updating flight offers: " + e.getMessage(), e);
        }

        System.out.println("Done!");
    }

    private List<Discount> getAvailableDiscounts() throws IOException {
        return new ObjectMapper().readValue(AVAILABLE_DISCOUNTS_FILE, List.class);
    }

    private void applyDiscountToFlight(Flight flight, Discount discount) {
        Double toSubstract = flight.getPrice() * (discount.getDiscount() / 100.0f);
        Double newPrice = flight.getPrice() - toSubstract;
        flight.setOffer(true);
        System.out.println("Flight from " + flight.getOutbound().getOrigin() +
                " and destination " + flight.getOutbound().getDestination() + " has a discount of "
                + discount.getDiscount() + ". Old price: " + flight.getPrice() + "; New price: " + newPrice);
        flight.setPrice(newPrice);
    }

}
