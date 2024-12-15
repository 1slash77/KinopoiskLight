package com.pablok.kinopoisklight.core

interface BaseDomainAdapter<DOMAIN, ANOTHER> {
    fun fromDomain(domain: DOMAIN): ANOTHER
    fun fromDomain(domainList: List<DOMAIN>): List<ANOTHER>
    fun toDomain(another: ANOTHER): DOMAIN
    fun toDomain(anotherList: List<ANOTHER>): List<DOMAIN>
}