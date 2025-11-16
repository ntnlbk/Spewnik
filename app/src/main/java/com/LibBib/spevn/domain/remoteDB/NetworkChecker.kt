package com.LibBib.spevn.domain.remoteDB

interface NetworkChecker {
    fun isConnected(): Boolean
}