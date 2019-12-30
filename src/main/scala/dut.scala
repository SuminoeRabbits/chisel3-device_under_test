package device_under_test_0

import java.io.File
import scala.util.Random

import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// module to output the max value from {in1,in2,in3}

class DEVICE_UNDER_TEST_0 extends Module {
  val io = IO(new Bundle {
    val in1 = Input(UInt(16.W))
    val in2 = Input(UInt(16.W))
    val in3 = Input(UInt(16.W))
    val out = Output(UInt(16.W))
  })
    
  when(io.in1 > io.in2 && io.in1 > io.in3) {
    io.out := io.in1  
  }.elsewhen(io.in2 > io.in1 && io.in2 > io.in3) {
    io.out := io.in2 
  }.otherwise {
    io.out := io.in3
  }
}

//println(Driver.emitVerilog(new  DEVICE_UNDER_TEST_0))

// verify that the max of the three inputs is correct
class DEVICE_UNDER_TEST_0UnitTester(c: DEVICE_UNDER_TEST_0) extends PeekPokeTester(c) {

/**
  * DUT model in TB
  * 
  */
  def DEVICE_UNDER_TEST_0_TB(a: Int, b: Int, c: Int):(Int) = {
    var x = a
    var y = b
    var z = c
    var out = z
    if(x > y){
      if(x > z) { out = x } else { out = z }
    }
    else { 
      if(y > z) { out = y } else { out = z }
    }
    (out)
  }

/**
  * TB drives IO pad to DEVICE_UNDER_TEST_0 module
  * 
  */

  for(n <-1 to 100 by 1){
    var r = new Random
    var i = r.nextInt(256)
    var j = r.nextInt(256)
    var k = r.nextInt(256)
    poke(c.io.in1, i)
    poke(c.io.in2, j)
    poke(c.io.in3, k)
    expect(c.io.out,DEVICE_UNDER_TEST_0_TB(i,j,k))
    if(peek(c.io.out) != DEVICE_UNDER_TEST_0_TB(i,j,k)){
      //println(s"c.io.in1 = ${peek(c.io.in1)}")
      //println(s"c.io.in2 = ${peek(c.io.in2)}")
      //println(s"c.io.in3 = ${peek(c.io.in3)}")
      println(s"expected out = ${DEVICE_UNDER_TEST_0_TB(i,j,k)}")
      println(s"c.io.out     = ${peek(c.io.out)}")
    }
    step(1)
  }

}

