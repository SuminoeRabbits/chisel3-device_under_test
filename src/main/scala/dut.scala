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

object memory_scenario{
  val x = 3
  val y = 10
  val data = Array.ofDim[Int](x,y)
  for(m <-1 to x by 1){
    for(n <-1 to y by 1){
      var r = new Random
      var i = r.nextInt(256)
      data(m-1)(n-1) = i
    }
  }
}


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

  for(n <-1 to 10 by 1){
    var x = memory_scenario.data(0)(n-1)
    var y = memory_scenario.data(1)(n-1)
    var z = memory_scenario.data(2)(n-1)
    poke(c.io.in1, x)
    poke(c.io.in2, y)
    poke(c.io.in3, z)
    expect(c.io.out,DEVICE_UNDER_TEST_0_TB(x,y,z))
    if(peek(c.io.out) != DEVICE_UNDER_TEST_0_TB(x,y,z)){
      println(s"expected out = ${DEVICE_UNDER_TEST_0_TB(x,y,z)}")
      println(s"c.io.out     = ${peek(c.io.out)}")
    }
    step(1)
  }

}

