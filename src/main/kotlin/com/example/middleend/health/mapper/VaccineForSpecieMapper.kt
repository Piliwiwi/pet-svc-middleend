package com.example.middleend.health.mapper

import com.example.middleend.health.dto.DoseForSpecieDTO
import com.example.middleend.health.dto.VaccineForSpecieDTO
import com.example.middleend.health.model.CompleteVaccineData
import com.example.middleend.health.model.Vaccine
import org.springframework.stereotype.Component

@Component
class VaccineForSpecieMapper {
    fun List<Vaccine>.toVaccineForSpecie(latestVaccines: MutableList<CompleteVaccineData>) = map { vaccine ->
        var lastVaccine: CompleteVaccineData? = null
        if (latestVaccines.size > 0) {
            lastVaccine = latestVaccines.firstOrNull { it.vaccine?.nameCode == vaccine.nameCode }
            if (lastVaccine != null) latestVaccines.remove(lastVaccine)
        }
        vaccine.toVaccineForSpecie(lastVaccine)
    }

    fun Vaccine.toVaccineForSpecie(lastVaccine: CompleteVaccineData?) = VaccineForSpecieDTO(
        name = name,
        key = nameCode,
        nextDose = lastVaccine?.nextDose(),
        doses = doses.toDoses(),
    )

    private fun Int.toDoses() = (1..this).map {
        DoseForSpecieDTO(
            code = it,
            description = "$it/$this"
        )
    }

    private fun CompleteVaccineData.nextDose(): DoseForSpecieDTO? {
        if (this.vaccine?.doses == null) return null
        if (this.vaccination.dose == null || this.vaccination.dose == 0) return null
        val lastDose = this.vaccination.dose ?: 0
        val totalDoses = this.vaccine.doses
        return if (lastDose >= totalDoses) null
        else {
            val nextDose = lastDose + 1
            DoseForSpecieDTO(
                code = nextDose,
                description = "$nextDose/$totalDoses"
            )
        }
    }
}