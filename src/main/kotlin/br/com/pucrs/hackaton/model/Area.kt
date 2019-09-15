package br.com.pucrs.hackaton.model

class Area {
    var id: String? = null
    val area: String? = null
    val latitude: Double? = null
    val longitude: Double? = null
    val raio: Int? = null
    val status: Status? = null
    val recursos: Recursos? = null
    val historicos: List<String> = emptyList()
    val comentarios: List<String> = emptyList()
    val emergencia: Emergencia? = null
}