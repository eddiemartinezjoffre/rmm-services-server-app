package com.ninja.rmm.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Component
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
  private Set<Device> devices;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
  private Set<Service> services;

  public Customer() {

  }

  public Customer(String name) {
    super();
    this.name = name;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Device> getDevices() {
    return devices;
  }

  public void setDevices(Set<Device> devices) {
    this.devices = devices;
  }

  public Set<Service> getServices() {
    return services;
  }

  public void setServices(Set<Service> services) {
    this.services = services;
  }
}
