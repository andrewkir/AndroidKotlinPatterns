package ru.andrewkir.moxyexapmle.models

class User(val email: String, val name:String, val password:String, val school:String, var access_token: String = "", var refresh_token: String = "")