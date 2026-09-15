package com.example.segurosfacil.data.repository

import com.example.segurosfacil.data.model.PlanSeguro
import com.example.segurosfacil.data.model.TipoSeguro


object PlanRepository {

    val planes: List<PlanSeguro> = listOf(
        // VEHICULAR
        PlanSeguro("p1", TipoSeguro.VEHICULAR, "Rímac Ficticia", "Auto Protegido", "Daños a terceros, robo total", 89.90),
        PlanSeguro("p2", TipoSeguro.VEHICULAR, "Pacífico Ficticia", "Auto Full", "Cobertura total, luna, llantas", 129.90),
        PlanSeguro("p3", TipoSeguro.VEHICULAR, "Mapfre Ficticia", "Auto Básico", "Solo responsabilidad civil", 49.90),
        PlanSeguro("p4", TipoSeguro.VEHICULAR, "La Positiva Ficticia", "Auto Plus", "Daños propios y terceros", 99.90),
        PlanSeguro("p5", TipoSeguro.VEHICULAR, "Interseguro Ficticia", "Auto Económico", "Responsabilidad civil básica", 39.90),
        PlanSeguro("p6", TipoSeguro.VEHICULAR, "Rímac Ficticia", "Auto Premium", "Cobertura total + grúa 24/7", 159.90),
        PlanSeguro("p7", TipoSeguro.VEHICULAR, "Pacífico Ficticia", "Auto Joven", "Cobertura para conductores nuevos", 119.90),
        PlanSeguro("p8", TipoSeguro.VEHICULAR, "Mapfre Ficticia", "Auto Familiar", "Cobertura total, 2 conductores", 139.90),

        // SALUD
        PlanSeguro("p9", TipoSeguro.SALUD, "Rímac Ficticia", "Salud Esencial", "Consultas y emergencias", 99.90),
        PlanSeguro("p10", TipoSeguro.SALUD, "Pacífico Ficticia", "Salud Total", "Hospitalización y cirugías", 189.90),
        PlanSeguro("p11", TipoSeguro.SALUD, "La Positiva Ficticia", "Salud Familiar", "Cobertura para 4 personas", 249.90),
        PlanSeguro("p12", TipoSeguro.SALUD, "Mapfre Ficticia", "Salud Básica", "Solo consultas ambulatorias", 59.90),
        PlanSeguro("p13", TipoSeguro.SALUD, "Interseguro Ficticia", "Salud Senior", "Cobertura para adultos mayores", 219.90),
        PlanSeguro("p14", TipoSeguro.SALUD, "Rímac Ficticia", "Salud Premium", "Hospitalización, cirugía y odontología", 299.90),
        PlanSeguro("p15", TipoSeguro.SALUD, "Pacífico Ficticia", "Salud Joven", "Cobertura básica para menores de 30", 79.90),
        PlanSeguro("p16", TipoSeguro.SALUD, "La Positiva Ficticia", "Salud Maternidad", "Incluye parto y control prenatal", 259.90),

        // VIDA
        PlanSeguro("p17", TipoSeguro.VIDA, "Mapfre Ficticia", "Vida Segura", "Cobertura por fallecimiento", 39.90),
        PlanSeguro("p18", TipoSeguro.VIDA, "Rímac Ficticia", "Vida Plus", "Fallecimiento + invalidez", 59.90),
        PlanSeguro("p19", TipoSeguro.VIDA, "Pacífico Ficticia", "Vida Familiar", "Cobertura para el titular y cónyuge", 79.90),
        PlanSeguro("p20", TipoSeguro.VIDA, "La Positiva Ficticia", "Vida Básica", "Solo fallecimiento natural", 24.90),
        PlanSeguro("p21", TipoSeguro.VIDA, "Interseguro Ficticia", "Vida Premium", "Fallecimiento, invalidez y enfermedades graves", 99.90),
        PlanSeguro("p22", TipoSeguro.VIDA, "Mapfre Ficticia", "Vida Emprendedor", "Cobertura para independientes", 44.90),
        PlanSeguro("p23", TipoSeguro.VIDA, "Rímac Ficticia", "Vida Senior", "Cobertura para mayores de 50", 69.90),

        // HOGAR
        PlanSeguro("p24", TipoSeguro.HOGAR, "Pacífico Ficticia", "Hogar Protegido", "Incendio y robo", 45.90),
        PlanSeguro("p25", TipoSeguro.HOGAR, "La Positiva Ficticia", "Hogar Total", "Incendio, robo y desastres naturales", 69.90),
        PlanSeguro("p26", TipoSeguro.HOGAR, "Mapfre Ficticia", "Hogar Básico", "Solo incendio", 29.90),
        PlanSeguro("p27", TipoSeguro.HOGAR, "Interseguro Ficticia", "Hogar Plus", "Incendio, robo y responsabilidad civil", 54.90),
        PlanSeguro("p28", TipoSeguro.HOGAR, "Rímac Ficticia", "Hogar Premium", "Cobertura total + asistencia hogar 24/7", 89.90),
        PlanSeguro("p29", TipoSeguro.HOGAR, "Pacífico Ficticia", "Hogar Alquiler", "Cobertura para inquilinos", 34.90),
        PlanSeguro("p30", TipoSeguro.HOGAR, "La Positiva Ficticia", "Hogar Condominio", "Cobertura para departamentos", 49.90)
    )
}