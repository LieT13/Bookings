package com.lietsoft.bookingmanager.jobs;

import com.lietsoft.bookingmanager.model.Flight;
import com.lietsoft.bookingmanager.model.Discount;
import com.lietsoft.bookingmanager.service.FlightsService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class DiscountsJob implements Job {

    private static final String AVAILABLE_DISCOUNTS_URL = "http://odigeo-testbackend.herokuapp.com/discount";

    private FlightsService flightsService;

    public DiscountsJob() {
        flightsService = new FlightsService();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Updating flight offers...");

        try {
            String discountsJson = getAvailableDiscounts();
            System.out.println(discountsJson);

            ObjectMapper mapper = new ObjectMapper();

            AvailableDiscountsResponse availableDiscounts = mapper.readValue(discountsJson, AvailableDiscountsResponse.class);
            availableDiscounts.getResults().forEach(discount -> {
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

    private String getAvailableDiscounts() throws IOException {
        HttpGet request = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            request = new HttpGet(AVAILABLE_DISCOUNTS_URL);

            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
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
