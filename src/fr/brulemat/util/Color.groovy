package fr.brulemat.util

import fr.brulemat.GlobalVars

class Color {
    static def blue(text) {
        return "${GlobalVars.ANSI_BLUE}${text}${GlobalVars.ANSI_RESET}"
    }

    static def red(text) {
        return "${GlobalVars.ANSI_RED}${text}${GlobalVars.ANSI_RESET}"
    }
}
