package com.ninja.rmm.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "services")
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "serviceName")
  private String serviceName;
  @Column(name = "serviceDescription")
  private String serviceDescription;
  @Column(name = "serviceCost")
  private BigDecimal serviceCost;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonProperty("customer_id")
  private Customer customer = new Customer();

  public Service() {

  }

  public Service(String serviceName, String serviceDescription, BigDecimal serviceCost, long customerId) {
    this.serviceName = serviceName;
    this.serviceDescription = serviceDescription;
    this.serviceCost = serviceCost;
    this.customer.setId(customerId);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceDescription() {
    return serviceDescription;
  }

  public void setServiceDescription(String serviceDescription) {
    this.serviceDescription = serviceDescription;
  }

  public BigDecimal getServiceCost() {
    return serviceCost;
  }

  public void setServiceCost(BigDecimal serviceCost) {
    this.serviceCost = serviceCost;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
