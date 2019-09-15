package br.com.pucrs.hackaton.repository

import br.com.pucrs.hackaton.model.Area
import com.google.cloud.firestore.Firestore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class AreaRepository @Autowired constructor(private val firestore: Firestore) {

    private val collection = firestore.collection("area")

    fun addArea(area: Area): String {
        val key = collection.add(area).get().id
        updateArea(area, key)
        return key
    }

    fun updateArea(area: Area, key: String? = area.id) {
        key ?: throw RuntimeException("key invalida")
        collection.document(key).set(area.also { it.id = key }).get()
    }

    fun getByKey(key: String) = collection.document(key).get().get().toObject(Area::class.java)

    fun getAllAreas(): MutableList<Area> = collection.get().get().toObjects(Area::class.java)

}