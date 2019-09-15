package br.com.pucrs.hackaton.service

import br.com.pucrs.hackaton.model.Area
import br.com.pucrs.hackaton.model.Evento
import br.com.pucrs.hackaton.model.GrupoEvento
import br.com.pucrs.hackaton.repository.AreaRepository
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sqrt


@Service
class AreaService @Autowired constructor(private val repository: AreaRepository) {

    fun getAreas() = repository.getAllAreas()

    fun getAreaByKey(key: String) = repository.getByKey(key)

    fun insertArea(area: Area) = repository.addArea(area)

    fun updateEventInArea(evento: Evento): MutableList<Area> {
        val areas = getAreas()
        val foundInArea = areas.firstOrNull { area ->

            area.emergencia ?: throw RuntimeException("ooooops")

            val lat1 = area.latitude?.toDouble()
            val lon1 = area.longitude?.toDouble()
            val lat2 = evento.latitude?.toDouble()
            val lon2 = evento.longitude?.toDouble()

            var foundInGroup = false
            if (lat1 != null && lon1 != null && lat2 != null && lon2 != null && area.raio != null) {
                if (distanceInKmBetweenEarthCoordinates(lat1, lon1, lat2, lon2) <= area.raio) {
                    foundInGroup = area.emergencia.grupoEventos.any { gEvento ->
                        val lat1 = gEvento.latitude?.toDouble()
                        val lon1 = gEvento.longitude?.toDouble()
                        val lat2 = evento.latitude.toDouble()
                        val lon2 = evento.longitude.toDouble()

                        var result = false
                        val gEventoRaio = gEvento.raio
                        if (lat1 != null && lon1 != null && lat2 != null && lon2 != null && gEventoRaio != null) {
                            if (distanceInKmBetweenEarthCoordinates(lat1, lon1, lat2, lon2) <= gEventoRaio) {
                                gEvento.eventos.add(evento)
                                result = true
                            }
                        }
                        result
                    }

                    if (!foundInGroup) {
                        area.emergencia.grupoEventos.add(GrupoEvento().also {
                            it.raio = 50
                            it.latitude = evento.latitude
                            it.longitude = evento.longitude
                            it.eventos.add(evento)
                        })
                        foundInGroup = true
                    }
                }
            }
            foundInGroup
        }
        foundInArea ?: throw RuntimeException("fora de qualquer area registrada")
        repository.updateArea(foundInArea)
        sendAlert()
        return repository.getAllAreas()
    }

    fun distanceInKmBetweenEarthCoordinates(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        var p = 0.017453292519943295
        var a = 0.5 - cos((lat2 - lat1) * p) / 2 +
                cos(lat1 * p) * cos(lat2 * p) *
                (1 - cos((lon2 - lon1) * p)) / 2

        val response = 12742 * asin(sqrt(a)) * 1000
        return response
    }

    fun sendAlert() {
        val registrationToken = "YOUR_REGISTRATION_TOKEN"

        val message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setToken(registrationToken)
                .build()

        val response = FirebaseMessaging.getInstance().send(message)
        println("Successfully sent message: $response")
    }
}
