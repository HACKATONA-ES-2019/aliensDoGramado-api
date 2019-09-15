package br.com.pucrs.hackaton.model

class GrupoEvento {
    var latitude: String? = null
    var longitude: String? = null
    var raio: Int? = null
    val eventos: MutableList<Evento> = arrayListOf()
}