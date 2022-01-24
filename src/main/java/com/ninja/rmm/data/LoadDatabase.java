package com.ninja.rmm.data;

import com.ninja.rmm.model.Customer;
import com.ninja.rmm.model.Device;
import com.ninja.rmm.model.Service;
import com.ninja.rmm.repository.CustomerRepository;
import com.ninja.rmm.repository.DeviceRepository;
import com.ninja.rmm.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository, ServiceRepository serviceRepository, DeviceRepository deviceRepository) {

        return args -> {
            /**
             * Preload Customer data
             */
            customerRepository.save(new Customer("Customer-1"));
            customerRepository.save(new Customer("Customer-2"));
            customerRepository.findAll().forEach(customer -> log.info("Preloaded " + customer));

            /**
             * Preload Service data
             */

            serviceRepository.save(new Service("Antivirus-Mac", "To have antivirus in their devices.", 7));
            serviceRepository.save(new Service("Antivirus-Win", "To have antivirus in their devices.", 5));
            serviceRepository.save(new Service("Cloudberry", "To backup data in their devices.", 3));
            serviceRepository.save(new Service("PSA", "Ticketing system for alerts in their devices.", 2));
            serviceRepository.save(new Service("TeamViewer", "Remote connection to devices.", 1));
            serviceRepository.findAll().forEach(service -> {
                log.info("Preloaded " + service);
            });

            /**
             * Preload Device data
             */

            deviceRepository.save(new Device("WS-0001", "Windows Workstation"));
            deviceRepository.save(new Device("SRV-001", "Windows Server"));
            deviceRepository.save(new Device("MAC-001", "Mac"));

            deviceRepository.findAll().forEach(device -> {
                log.info("Preloaded " + device);
            });

        };
    }
}
