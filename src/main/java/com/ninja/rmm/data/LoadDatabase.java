package com.ninja.rmm.data;

import com.ninja.rmm.model.Customer;
import com.ninja.rmm.model.Device;
import com.ninja.rmm.model.Service;
import com.ninja.rmm.repository.CustomerRepository;
import com.ninja.rmm.repository.DeviceRepository;
import com.ninja.rmm.repository.ServiceRepository;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(CustomerRepository customerRepository, ServiceRepository serviceRepository,
      DeviceRepository deviceRepository) {

    return args -> {
      /**
       * Preload Customer data
       */
      Customer customer1 = new Customer();
      Customer customer2 = new Customer();
      customer1.setName("Customer-1");
      customer2.setName("Customer-2");

      customerRepository.save(customer1);
      customerRepository.save(customer2);
      customerRepository.findAll().forEach(customer -> log.info("Preloaded " + customer));


      /**
       * Preload Service data
       */
      serviceRepository.save(new Service("Antivirus-Mac", "To have antivirus in their devices.", new BigDecimal(7), customer1));
      serviceRepository.save(new Service("Antivirus-Win", "To have antivirus in their devices.", new BigDecimal(5), customer1));
      serviceRepository.save(new Service("Cloudberry", "To backup data in their devices.", new BigDecimal(3), customer1));
      serviceRepository.save(new Service("PSA", "Ticketing system for alerts in their devices.", new BigDecimal(2), customer1));
      serviceRepository.save(new Service("TeamViewer", "Remote connection to devices.", new BigDecimal(1), customer1));
      serviceRepository.save(new Service("Antivirus-Mac", "To have antivirus in their devices.", new BigDecimal(7), customer2));
      serviceRepository.save(new Service("Antivirus-Win", "To have antivirus in their devices.", new BigDecimal(5), customer2));
      serviceRepository.save(new Service("Cloudberry", "To backup data in their devices.", new BigDecimal(3), customer2));
      serviceRepository.save(new Service("PSA", "Ticketing system for alerts in their devices.", new BigDecimal(2), customer2));
      serviceRepository.save(new Service("TeamViewer", "Remote connection to devices.", new BigDecimal(1), customer2));
      serviceRepository.findAll().forEach(service -> {
        log.info("Preloaded " + service);
      });

      /**
       * Preload Device data
       */
      deviceRepository.save(new Device("WS-0001", "Windows Workstation", new BigDecimal(4), customer1));
      deviceRepository.save(new Device("SRV-001", "Windows Server", new BigDecimal(4), customer1));
      deviceRepository.save(new Device("MAC-001", "Mac", new BigDecimal(4), customer1));
      deviceRepository.save(new Device("WS-0002", "Windows Workstation", new BigDecimal(4), customer2));
      deviceRepository.save(new Device("SRV-002", "Windows Server", new BigDecimal(4), customer2));
      deviceRepository.save(new Device("MAC-002", "Mac", new BigDecimal(4), customer2));

      deviceRepository.findAll().forEach(device -> {
        log.info("Preloaded " + device);
      });

    };
  }
}
