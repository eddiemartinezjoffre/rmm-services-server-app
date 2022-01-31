package com.ninja.rmm.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

@Component
@Entity
@Table(name = "devices")
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "systemName", unique = true)
  private String systemName;
  @Column(name = "deviceType")
  private String deviceType;
  @Column(name = "deviceCost")
  private BigDecimal deviceCost;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonProperty("customer_id")
  private Customer customer;

  public Device() {

  }

  public Device(String systemName, String deviceType, BigDecimal deviceCost, Customer customer) {
    this.systemName = systemName;
    this.deviceType = deviceType;
    this.deviceCost = deviceCost;
    this.customer = customer;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSystemName() {
    return systemName;
  }

  public void setSystemName(String systemName) {
    this.systemName = systemName;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public BigDecimal getDeviceCost() {
    return deviceCost;
  }

  public void setDeviceCost(BigDecimal deviceCost) {
    this.deviceCost = deviceCost;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
