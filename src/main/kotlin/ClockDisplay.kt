@file:Verik

import io.verik.core.*

@SynthTop
class ClockDisplay(
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

    var value: Ubit<`7`> = u("7'b0")

    @Make
    val seven_segment = SevenSegment(
        digit = value.tru(),
        seg = seg)

    @Seq
    fun seqLed() {
        on (posedge(clk_100mhz)) {
            value = value + u("7'b1")
        }
    }

    @Com
    fun set_output() {
//        led = sw
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