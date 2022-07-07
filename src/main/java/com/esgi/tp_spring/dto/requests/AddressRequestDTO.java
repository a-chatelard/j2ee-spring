package com.esgi.tp_spring.dto.requests;

import com.esgi.tp_spring.entities.Address;

public class AddressRequestDTO implements IRequestDTO<Address> {

    private String number;

    private String label;

    private String complement;

    private String zipCode;

    private String city;

    private String country;

    public Address ToEntity() {
        var address = new Address();

        address.setNumber(this.number);
        address.setLabel(this.label);
        address.setComplement(this.complement);
        address.setZipCode(this.zipCode);
        address.setCity(this.city);
        address.setCountry(this.country);

        return address;
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
