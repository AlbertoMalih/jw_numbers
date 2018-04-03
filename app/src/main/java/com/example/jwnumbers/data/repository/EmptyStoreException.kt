package com.example.jwnumbers.data.repository

class EmptyStoreException : Exception() {
    override val message: String = "list receive numbers is empty"
}