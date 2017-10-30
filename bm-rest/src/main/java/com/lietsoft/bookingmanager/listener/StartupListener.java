package com.lietsoft.bookingmanager.listener;

import com.lietsoft.bookingmanager.jobs.DiscountsJob;
import com.lietsoft.bookingmanager.model.Flight;
import com.lietsoft.bookingmanager.service.FlightsService;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class StartupListener implements ServletContextListener {

    private static final String AVAILABLE_FLIGHTS_FILE = "flights.json";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initializeFlights();
        startDiscountsJob();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Context destroyed!");
    }

    private void initializeFlights() {
        System.out.println("Initializing flights...");

        FlightsService flightsService = new FlightsService();

        try {
            List<Flight> availableFlights = getAvailableFlights();
            availableFlights.forEach(flightsService::addFlight);
            System.out.println(availableFlights.size() + " flights inserted!");
        } catch (IOException e) {
            System.out.println("Error initializing flights: " + e.getMessage());
        }
    }

    private List<Flight> getAvailableFlights() throws IOException {
        return new ObjectMapper().readValue(AVAILABLE_FLIGHTS_FILE, List.class);
    }

    private void startDiscountsJob() {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        try {
            Scheduler sched = schedFact.getScheduler();
            sched.getCurrentlyExecutingJobs().forEach(j -> System.out.println("JOB -----> " + j.getJobDetail().getKey()));
            sched.start();

            JobDetail job = newJob(DiscountsJob.class)
                    .withIdentity("discountsJob", "group")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("discountsJobTrigger", "group")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(30)
                            .repeatForever())
                    .build();

            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
