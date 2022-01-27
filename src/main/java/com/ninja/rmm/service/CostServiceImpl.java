package com.ninja.rmm.service;

import com.ninja.rmm.model.Cost;
import com.ninja.rmm.model.Device;
import com.ninja.rmm.model.Service;
import com.ninja.rmm.repository.DeviceRepository;
import com.ninja.rmm.repository.ServiceRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class CostServiceImpl implements CostService {

  private final ServiceRepository serviceRepository;
  private final DeviceRepository deviceRepository;

  public CostServiceImpl(ServiceRepository serviceRepository, DeviceRepository deviceRepository) {
    this.serviceRepository = serviceRepository;
    this.deviceRepository = deviceRepository;
  }

  private static final BigDecimal COST_PER_DEVICE = new BigDecimal("4.00");

  private static final BigDecimal ANTIVIRUS_MAC = new BigDecimal("7.00");

  private static final BigDecimal ANTIVIRUS_WIN = new BigDecimal("5.00");

  private static final BigDecimal CLOUDBERRY = new BigDecimal("3.00");

  private static final BigDecimal PSA = new BigDecimal("2.00");

  private static final BigDecimal TEAMVIEWER = new BigDecimal("1.00");

  /**
   * This method will calculate the total monthly cost for devices and services
   *
   * @param customerId represents the customer id querying the devices and services
   * @return total monthly cost for all services and devices
   */
  public Cost costPerCustomer(Long customerId) {
    Cost cost = new Cost();
    BigDecimal serviceMacCost = ANTIVIRUS_MAC.multiply(new BigDecimal(countServices("(.*)MAC(.*)", customerId)));
    BigDecimal serviceWinCost = ANTIVIRUS_WIN.multiply(new BigDecimal(countServices("(.*)WIN(.*)", customerId)));
    BigDecimal serviceCBCost = CLOUDBERRY.multiply(new BigDecimal(countServices("CLOUDBERRY", customerId)));
    BigDecimal servicePSACost = PSA.multiply(new BigDecimal(countServices("PSA", customerId)));
    BigDecimal serviceTVCost = TEAMVIEWER.multiply(new BigDecimal(countServices("TEAMVIEWER", customerId)));
    BigDecimal totalServiceCost = serviceMacCost.add(serviceWinCost).add(serviceCBCost).add(servicePSACost).add(serviceTVCost);

    BigDecimal totalCost = COST_PER_DEVICE.multiply(new BigDecimal(countDevices(customerId))).add(totalServiceCost);
    cost.setTotal(totalCost);
    return cost;
  }

  /**
   * This method will query the services based on their service name property
   *
   * @param serviceName represents the service name used to query the services
   * @return count of services
   */
  private long countServices(String serviceName, Long customerId) {
    List<Service> services = serviceRepository.findByCustomerId(customerId);
    long count = services.stream()
        .map(Service::getServiceName)
        .filter(name -> name.toUpperCase().matches(serviceName))
        .count();
    return count;
  }

  /**
   * This method will query all devices
   *
   * @param customerId
   * @return count of devices
   */
  private long countDevices(Long customerId) {
    List<Device> devices = deviceRepository.findByCustomerId(customerId);
    return devices.stream().collect(Collectors.toList()).size();
  }
}
