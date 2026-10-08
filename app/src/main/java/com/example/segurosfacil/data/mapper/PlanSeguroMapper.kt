package com.example.segurosfacil.data.mapper

import com.example.segurosfacil.data.model.PlanSeguro
import com.example.segurosfacil.data.model.PlanSeguroEntity
import com.example.segurosfacil.data.model.TipoSeguro
import com.example.segurosfacil.data.remote.dto.PlanSeguroDto

fun PlanSeguroDto.toEntity() = PlanSeguroEntity(
    id = id,
    nombre = nombre,
    tipoSeguro = tipoSeguro,
    aseguradora = aseguradora,
    cobertura = cobertura,
    primaBase = primaBase
)

fun PlanSeguroEntity.toDomain() = PlanSeguro(
    id = id.toString(),
    tipo = TipoSeguro.valueOf(tipoSeguro),
    aseguradora = aseguradora,
    nombrePlan = nombre,
    cobertura = cobertura,
    primaMensual = primaBase
)