package com.ecommerce.enanpharma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String street;
    
    @Column(nullable = false)
    private String number;
    
    @Column
    private String complement;
    
    @Column(nullable = false)
    private String neighborhood;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false, length = 2)
    private String state;
    
    @Column(nullable = false, name = "zip_code")
    private String zipCode;
    
    public Address() {}
    
    public Address(String street, String number, String complement, String neighborhood, String city, String state, String zipCode) {
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}