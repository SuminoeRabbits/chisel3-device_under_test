package device_under_test_0

import java.io.File

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
  poke(c.io.in1, 6)
  poke(c.io.in2, 4)  
  poke(c.io.in3, 2)  
  expect(c.io.out, 6)  // input 1 should be biggest
  poke(c.io.in2, 7)  
  expect(c.io.out, 7)  // now input 2 is
  poke(c.io.in3, 11)  
  expect(c.io.out, 11) // and now input 3
  poke(c.io.in3, 3)  
  expect(c.io.out, 7)  // show that decreasing an input works as well
}

