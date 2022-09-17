package com.palm.newbenefit.Module;

public class VoluntaryBenefit {

    String name;
    String first_name;
    String last_name;
    String suminsured;
    String dob;
    String gender;
    String member_email;
    String member_mob_no;
    String employee_premium;

    String allsum="0";
    String allpremium;

    String age;
    String agetype;

    String id;
    String marriage_date;
    String relation_name;
    String relation_id;

    String number_of_time_salary;
    String policy_sub_type_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarriage_date() {
        return marriage_date;
    }

    public void setMarriage_date(String marriage_date) {
        this.marriage_date = marriage_date;
    }

    public String getRelation_name() {
        return relation_name;
    }

    public void setRelation_name(String relation_name) {
        this.relation_name = relation_name;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber_of_time_salary() {
        return number_of_time_salary;
    }

    public void setNumber_of_time_salary(String number_of_time_salary) {
        this.number_of_time_salary = number_of_time_salary;
    }

    public String getPolicy_sub_type_id() {
        return policy_sub_type_id;
    }

    public void setPolicy_sub_type_id(String policy_sub_type_id) {
        this.policy_sub_type_id = policy_sub_type_id;
    }

    @Override
    public String toString() {
        return "VoluntaryBenefit{" +
                "name='" + name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", suminsured='" + suminsured + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", member_email='" + member_email + '\'' +
                ", member_mob_no='" + member_mob_no + '\'' +
                ", employee_premium='" + employee_premium + '\'' +
                ", allsum='" + allsum + '\'' +
                ", allpremium='" + allpremium + '\'' +
                ", age='" + age + '\'' +
                ", agetype='" + agetype + '\'' +
                ", id='" + id + '\'' +
                ", marriage_date='" + marriage_date + '\'' +
                ", relation_name='" + relation_name + '\'' +
                ", relation_id='" + relation_id + '\'' +
                ", number_of_time_salary='" + number_of_time_salary + '\'' +
                ", policy_sub_type_id='" + policy_sub_type_id + '\'' +
                '}';
    }

    public VoluntaryBenefit(String name, String first_name, String last_name, String suminsured, String dob, String gender, String member_email, String member_mob_no, String employee_premium, String allsum, String allpremium, String age, String agetype, String id, String marriage_date, String relation_name, String relation_id) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
        this.allsum = allsum;
        this.allpremium = allpremium;
        this.age = age;
        this.agetype = agetype;
        this.id = id;
        this.marriage_date = marriage_date;
        this.relation_name = relation_name;
        this.relation_id = relation_id;
    }

    public VoluntaryBenefit(String name, String first_name, String last_name,
                            String suminsured, String dob, String gender,
                            String member_email, String member_mob_no,
                            String employee_premium, String allsum,
                            String allpremium, String age, String agetype,
                            String id, String marriage_date,
                            String relation_name, String relation_id,
                            String number_of_time_salary) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
        this.allsum = allsum;
        this.allpremium = allpremium;
        this.age = age;
        this.agetype = agetype;
        this.id = id;
        this.marriage_date = marriage_date;
        this.relation_name = relation_name;
        this.relation_id = relation_id;
        this.number_of_time_salary = number_of_time_salary;
    }

    public VoluntaryBenefit(String name, String first_name, String last_name, String suminsured, String dob, String gender, String member_email, String member_mob_no, String employee_premium, String allsum, String allpremium, String age, String agetype) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
        this.allsum = allsum;
        this.allpremium = allpremium;
        this.age = age;
        this.agetype = agetype;
    }

    public String getAgetype() {
        return agetype;
    }

    public void setAgetype(String agetype) {
        this.agetype = agetype;
    }

    public String getEmployee_premium() {
        return employee_premium;
    }

    public void setEmployee_premium(String employee_premium) {
        this.employee_premium = employee_premium;
    }

    public VoluntaryBenefit() {
    }

    public String getAllsum() {
        return allsum+"";
    }

    public void setAllsum(String allsum) {
        this.allsum = allsum;
    }

    public String getAllpremium() {
        return allpremium;
    }

    public void setAllpremium(String allpremium) {
        this.allpremium = allpremium;
    }

    public VoluntaryBenefit(String name, String first_name, String last_name, String suminsured, String dob, String gender, String member_email, String member_mob_no, String employee_premium) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
    }

    public VoluntaryBenefit(String name, String first_name, String last_name, String suminsured, String dob, String gender, String member_email, String member_mob_no, String employee_premium,String relation_id) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
        this.relation_id = relation_id;

    }

    public VoluntaryBenefit(String name, String first_name, String last_name, String suminsured,
                            String dob, String gender, String member_email,
                            String member_mob_no, String employee_premium,String relation_id,
    String number_of_time_salary,String policy_sub_type_id) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
        this.relation_id = relation_id;
        this.number_of_time_salary = number_of_time_salary;
        this.policy_sub_type_id = policy_sub_type_id;

    }


    public VoluntaryBenefit(String name, String first_name, String last_name, String suminsured, String dob, String gender, String member_email, String member_mob_no) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
    }




    public String getName() {
        return name;
    }

    public VoluntaryBenefit(String name, String first_name, String last_name, String suminsured, String dob, String gender, String member_email, String member_mob_no, String employee_premium, String allsum, String allpremium) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
        this.allsum = allsum;
        this.allpremium = allpremium;
    }

    public VoluntaryBenefit(String name, String first_name, String last_name,
                            String suminsured, String dob, String gender,
                            String member_email, String member_mob_no,
                            String employee_premium, String allsum, String allpremium,
                            String premiumdata,String dfd,
                            String fgdfg) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.suminsured = suminsured;
        this.dob = dob;
        this.gender = gender;
        this.member_email = member_email;
        this.member_mob_no = member_mob_no;
        this.employee_premium = employee_premium;
        this.allsum = allsum;
        this.allpremium = allpremium;
        this.marriage_date = premiumdata;
    }




    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_mob_no() {
        return member_mob_no;
    }

    public void setMember_mob_no(String member_mob_no) {
        this.member_mob_no = member_mob_no;
    }
}
