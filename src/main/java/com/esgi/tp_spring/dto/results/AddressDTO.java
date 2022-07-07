package com.esgi.tp_spring.dto.results;

import com.esgi.tp_spring.entities.Address;

public class AddressDTO {
    private String number;

    private String label;

    private String complement;

    private String zipCode;

    private String city;

    private String country;

    public AddressDTO() {}

    public AddressDTO(Address address) {
        this.number = address.getNumber();
        this.label = address.getLabel();
        this.complement = address.getComplement();
        this.zipCode = address.getZipCode();
        this.city = address.getCity();
        this.country = address.getCountry();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
