package com.pablok.kinopoisklight.core

abstract class BaseDomainAdapter<DOMAIN, ANOTHER> {
    abstract fun fromDomain(domain: DOMAIN): ANOTHER
    abstract fun toDomain(another: ANOTHER): DOMAIN
    fun fromDomain(domainList: List<DOMAIN>): List<ANOTHER> {
        return domainList.map {
            fromDomain(it)
        }
    }
    fun toDomain(anotherList: List<ANOTHER>): List<DOMAIN> {
        return anotherList.map {
            toDomain(it)
        }
    }
}