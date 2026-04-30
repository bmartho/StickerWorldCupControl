package com.cup.stickerworldcupcontrol.extensions

import com.cup.stickerworldcupcontrol.R

fun String.toStringId(): Int {
    return when (this) {
        "FWC_START" -> R.string.section_fwc

        "MEX" -> R.string.team_mexico
        "RSA" -> R.string.team_south_africa
        "KOR" -> R.string.team_south_korea
        "CAN" -> R.string.team_canada
        "QAT" -> R.string.team_qatar
        "SUI" -> R.string.team_switzerland
        "BRA" -> R.string.team_brazil
        "MAR" -> R.string.team_morocco
        "HAI" -> R.string.team_haiti
        "SCO" -> R.string.team_scotland
        "USA" -> R.string.team_usa
        "PAR" -> R.string.team_paraguay
        "AUS" -> R.string.team_australia
        "GER" -> R.string.team_germany
        "CUW" -> R.string.team_curacao
        "CIV" -> R.string.team_ivory_coast
        "ECU" -> R.string.team_ecuador
        "NED" -> R.string.team_netherlands
        "JPN" -> R.string.team_japan
        "TUN" -> R.string.team_tunisia
        "BEL" -> R.string.team_belgium
        "EGY" -> R.string.team_egypt
        "IRN" -> R.string.team_iran
        "NZL" -> R.string.team_new_zealand
        "ESP" -> R.string.team_spain
        "CPV" -> R.string.team_cape_verde
        "KSA" -> R.string.team_saudi_arabia
        "URU" -> R.string.team_uruguay
        "FRA" -> R.string.team_france
        "SEN" -> R.string.team_senegal
        "NOR" -> R.string.team_norway
        "ARG" -> R.string.team_argentina
        "ALG" -> R.string.team_algeria
        "AUT" -> R.string.team_austria
        "JOR" -> R.string.team_jordan
        "POR" -> R.string.team_portugal
        "UZB" -> R.string.team_uzbekistan
        "COL" -> R.string.team_colombia
        "ENG" -> R.string.team_england
        "CRO" -> R.string.team_croatia
        "GHA" -> R.string.team_ghana
        "PAN" -> R.string.team_panama
        "BIH" -> R.string.team_bosnia
        "SWE" -> R.string.team_sweden
        "TUR" -> R.string.team_turkey
        "CZE" -> R.string.team_czech_republic
        "COD" -> R.string.team_dr_congo
        "IRQ" -> R.string.team_iraq

        "FWC_END" -> R.string.section_fwc_end
        "COC" -> R.string.section_special

        else -> R.string.section_fwc
    }
}