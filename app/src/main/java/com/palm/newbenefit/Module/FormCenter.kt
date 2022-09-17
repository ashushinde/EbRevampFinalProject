package com.palm.newbenefit.Module

class FormCenter {
    var form_center_name: String? = null
    var pdf_file: String? = null

    constructor() {}
    constructor(form_center_name: String?, pdf_file: String?) {
        this.form_center_name = form_center_name
        this.pdf_file = pdf_file
    }

    override fun toString(): String {
        return "FormCenter{" +
                "form_center_name='" + form_center_name + '\'' +
                ", pdf_file='" + pdf_file + '\'' +
                '}'
    }
}