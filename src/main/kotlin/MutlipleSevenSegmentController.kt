@file:Verik

import io.verik.core.*

typealias WIDTH = `8`

@SynthTop
class MutlipleSevenSegmentController (
    @In var clk_100mhz: Boolean,
    @In var btnd: Boolean, // reset

//    @In var digits: Ubit<`32`>,
    @In var sw: Ubit<`32`>, // this should be 16 rly

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

    var routedVal : Ubit<`4`> = nc()

    var count : Ubit<WIDTH> = nc()

    var an_index : Int = 0

    var seg: Ubit<`7`> = nc()

    var seg_state : Ubit<`8`> = u("8'b1")
    var seg_counter : Ubit<`32`> = u("32'b1")

    @Seq
    fun clk_control() {
        on(posedge(clk_100mhz)) {
            if (btnd) {
                count = u0<WIDTH>()
                seg_state = u("8'b1")
                seg_counter = u0()
            }
            else {
                count += u(1)

                if (seg_counter == u("32'b100_000")) {
                    seg_counter = u0()
                    seg_state = cat(seg_state.slice<`7`>(0), seg_state[7])
                } else {

                    seg_counter += u("32'b1")

                }

//                an_index += 1
//                an = u(0b0000_0001) shl an_index
//                seg_state = an.invert();
            }
        }
    }

    @Com
    fun routeVals() {
        routedVal = when (seg_state) {
            u(0b00000001) -> sw.slice<`4`>(0)
            u(0b00000010) -> sw.slice<`4`>(4)
            u(0b00000100) -> sw.slice<`4`>(8)
            u(0b00001000) -> sw.slice<`4`>(12)
            u(0b00010000) -> sw.slice<`4`>(16)
            u(0b00100000) -> sw.slice<`4`>(20)
            u(0b01000000) -> sw.slice<`4`>(24)
            u(0b10000000) -> sw.slice<`4`>(28)
            else -> u(0b0000)
        }
    }

    @Make
    val seven_segment = SevenSegment(
        digit = routedVal,
        seg = seg)

    @Com
    fun set_output() {
//        led = sw
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

//    @Com
//    fun segments() {
//        seg = when (routedVal) {
//            u(0x0) -> u(0b1000000)
//            u(0x1) -> u(0b1111001)
//            u(0x2) -> u(0b0100100)
//            u(0x3) -> u(0b0110000)
//            u(0x4) -> u(0b0011001)
//            u(0x5) -> u(0b0010010)
//            u(0x6) -> u(0b0000010)
//            u(0x7) -> u(0b1111000)
//            u(0x8) -> u(0b0000000)
//            u(0x9) -> u(0b0010000)
//            u(0xa) -> u(0b0001000)
//            u(0xb) -> u(0b0000011)
//            u(0xc) -> u(0b1000110)
//            u(0xd) -> u(0b0100001)
//            u(0xe) -> u(0b0000110)
//            else -> u(0b0001110)
//        }
//    }

}