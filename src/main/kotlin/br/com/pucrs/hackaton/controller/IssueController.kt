package br.com.pucrs.hackaton.controller

import br.com.pucrs.hackaton.model.Area
import br.com.pucrs.hackaton.model.Evento
import br.com.pucrs.hackaton.service.AreaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/issues")
class IssueController @Autowired constructor(private val areaService : AreaService){

    @GetMapping("/")
    fun getAreas() = areaService.getAreas()

    @GetMapping("/{key}")
    fun getAreaByKey(@RequestParam key : String) = areaService.getAreaByKey(key)

    @PostMapping("/area")
    fun insertArea(@RequestBody area: Area) = areaService.insertArea(area)

    @PutMapping("/area")
    fun updateArea(@RequestBody evento: Evento) = areaService.updateEventInArea(evento)

}