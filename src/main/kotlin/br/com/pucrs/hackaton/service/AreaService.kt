package br.com.pucrs.hackaton.service

import br.com.pucrs.hackaton.model.Area
import br.com.pucrs.hackaton.repository.AreaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AreaService @Autowired constructor(private val repository: AreaRepository) {

    fun getAreas() = repository.getAllAreas()

    fun getAreaByKey(key: String) = repository.getByKey(key)

    fun insertArea(area: Area) = repository.addArea(area)

}
