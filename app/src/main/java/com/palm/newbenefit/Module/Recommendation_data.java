package com.palm.newbenefit.Module;

public class Recommendation_data {



    String id;
    String hospital_id;
    String hospital_name;
    String hospital_address;
    String hospital_state;
    String hospital_city;
    String ailment_id;
    String ailment_name;
    String ailment_cost;
    String room_rent_type;
    String ailmenttreatment_type_cost;

    public Recommendation_data() {
    }

    public Recommendation_data(String id, String hospital_id, String hospital_name, String hospital_address, String hospital_state, String hospital_city, String ailment_id, String ailment_name, String ailment_cost, String room_rent_type, String ailmenttreatment_type_cost) {
        this.id = id;
        this.hospital_id = hospital_id;
        this.hospital_name = hospital_name;
        this.hospital_address = hospital_address;
        this.hospital_state = hospital_state;
        this.hospital_city = hospital_city;
        this.ailment_id = ailment_id;
        this.ailment_name = ailment_name;
        this.ailment_cost = ailment_cost;
        this.room_rent_type = room_rent_type;
        this.ailmenttreatment_type_cost = ailmenttreatment_type_cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public String getHospital_state() {
        return hospital_state;
    }

    public void setHospital_state(String hospital_state) {
        this.hospital_state = hospital_state;
    }

    public String getHospital_city() {
        return hospital_city;
    }

    public void setHospital_city(String hospital_city) {
        this.hospital_city = hospital_city;
    }

    public String getAilment_id() {
        return ailment_id;
    }

    public void setAilment_id(String ailment_id) {
        this.ailment_id = ailment_id;
    }

    public String getAilment_name() {
        return ailment_name;
    }

    public void setAilment_name(String ailment_name) {
        this.ailment_name = ailment_name;
    }

    public String getAilment_cost() {
        return ailment_cost;
    }

    public void setAilment_cost(String ailment_cost) {
        this.ailment_cost = ailment_cost;
    }

    public String getRoom_rent_type() {
        return room_rent_type;
    }

    public void setRoom_rent_type(String room_rent_type) {
        this.room_rent_type = room_rent_type;
    }

    public String getAilmenttreatment_type_cost() {
        return ailmenttreatment_type_cost;
    }

    public void setAilmenttreatment_type_cost(String ailmenttreatment_type_cost) {
        this.ailmenttreatment_type_cost = ailmenttreatment_type_cost;
    }


    @Override
    public String toString() {
        return "Recommendation_data{" +
                "id='" + id + '\'' +
                ", hospital_id='" + hospital_id + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", hospital_address='" + hospital_address + '\'' +
                ", hospital_state='" + hospital_state + '\'' +
                ", hospital_city='" + hospital_city + '\'' +
                ", ailment_id='" + ailment_id + '\'' +
                ", ailment_name='" + ailment_name + '\'' +
                ", ailment_cost='" + ailment_cost + '\'' +
                ", room_rent_type='" + room_rent_type + '\'' +
                ", ailmenttreatment_type_cost='" + ailmenttreatment_type_cost + '\'' +
                '}';
    }
}
