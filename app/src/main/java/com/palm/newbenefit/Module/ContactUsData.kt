package com.palm.newbenefit.Module

class ContactUsData {
    var companyHead: String? = null
    var name: String? = null
    var contactNo: String? = null
    var email: String? = null
    var address: String? = null

    constructor(companyHead: String?) {
        this.companyHead = companyHead
    }

    constructor(
        companyHead: String?,
        name: String?,
        contactNo: String?,
        email: String?,
        address: String?
    ) {
        this.companyHead = companyHead
        this.name = name
        this.contactNo = contactNo
        this.email = email
        this.address = address
    }

    constructor(
        name: String?,
        contactNo: String?,
        email: String?,
        address: String?
    ) {
        this.name = name
        this.contactNo = contactNo
        this.email = email
        this.address = address
    }

}