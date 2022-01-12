@file:Verik

import io.verik.core.*

@SynthTop
class FpgaTop(
    @In var clk_100mhz: Boolean,
    @In var sw: Ubit<`16`>,
    @Out var led: Ubit<`16`>,
    @Out var ca: Boolean,
    @Out var cb: Boolean,
    @Out var cc: Boolean,
    @Out var cd: Boolean,
    @Out var ce: Boolean,
    @Out var cf: Boolean,
    @Out var cg: Boolean,
    @Out var dp: Boolean,
    @Out var an: Ubit<`8`>
) : Module() {

    var seg: Ubit<`7`> = nc()

    @Make
    val seven_segment = SevenSegment(
        digit = sw.tru(),
        seg = seg)

//    @Seq
//    fun seqLed() {
//        on (posedge(clk_100mhz)) {
//            led = sw
//        }
//    }

    @Com
    fun set_output() {
        led = sw
//        led16_b = false
//        led16_g = false
//        led16_r = false
        for (i in 0 until 8) {

            ca = seg[0]
            cb = seg[1]
            cc = seg[2]
            cd = seg[3]
            ce = seg[4]
            cf = seg[5]
            cg = seg[6]
            dp = true
            an = u0() // can ignore 8 bit because of type inference

        }

    }

}
