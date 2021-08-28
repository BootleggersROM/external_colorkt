package dev.kdrag0n.colorkt.ucs.lch

import dev.kdrag0n.colorkt.data.Illuminants
import dev.kdrag0n.colorkt.tristimulus.CieXyz
import dev.kdrag0n.colorkt.ucs.lab.CieLab
import dev.kdrag0n.colorkt.util.conversion.ConversionGraph
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Polar (LCh) representation of [dev.kdrag0n.colorkt.ucs.lab.CieLab].
 *
 * @see dev.kdrag0n.colorkt.ucs.lch.Lch
 */
public data class CieLch @JvmOverloads constructor(
    override val L: Double,
    override val C: Double,
    override val h: Double,

    /**
     * Reference white for CIELAB calculations. This affects the converted color.
     */
    val referenceWhite: CieXyz = Illuminants.D65,
) : Lch {
    /**
     * Convert this color to the Cartesian (Lab) representation of CIELAB.
     *
     * @see dev.kdrag0n.colorkt.ucs.lab.Lab
     * @return Color represented as CIELAB
     */
    public fun toCieLab(): CieLab = CieLab(
        L = L,
        a = calcLabA(),
        b = calcLabB(),
        referenceWhite = referenceWhite,
    )

    public companion object {
        @JvmSynthetic
        internal fun register() {
            ConversionGraph.add<CieLab, CieLch> { it.toCieLch() }
            ConversionGraph.add<CieLch, CieLab> { it.toCieLab() }
        }

        /**
         * Convert this color to the polar (LCh) representation of CIELAB.
         *
         * @see dev.kdrag0n.colorkt.ucs.lch.Lch
         * @return Color represented as CIELCh
         */
        @JvmStatic
        @JvmName("fromCieLab")
        public fun CieLab.toCieLch(): CieLch = CieLch(
            L = L,
            C = calcLchC(),
            h = calcLchH(),
            referenceWhite = referenceWhite,
        )
    }
}
