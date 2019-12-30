package device_under_test_0 

import java.io.File

import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// to run after compile, use the following command on sbt
// sbt: > test:runMain device_under_test_0.DEVICE_UNDER_TEST_0Main 

object DEVICE_UNDER_TEST_0Main extends App {
  iotesters.Driver.execute(args, () => new DEVICE_UNDER_TEST_0) {
    c => new DEVICE_UNDER_TEST_0UnitTester(c)
  }
  //println(Driver.emitVerilog(new  DEVICE_UNDER_TEST_0))
}


//
// define class  DEVICE_UNDER_TEST_0Tester
// to extend ChiselFlaSpec on DEVICE_UNDER_TEST_0UnitTester
// 
// sbt:> test:runMain device_under_test_0.DEVICE_UNDER_TEST_0Main \
//       --backend-name verilator

class DEVICE_UNDER_TEST_0Tester extends ChiselFlatSpec {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "DEVICE_UNDER_TEST_0" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new DEVICE_UNDER_TEST_0, backendName) {
        c => new DEVICE_UNDER_TEST_0UnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new DEVICE_UNDER_TEST_0) {
      c => new DEVICE_UNDER_TEST_0UnitTester(c)
    } should be (true)
  }

  if(backendNames.contains("verilator")) {
    "using --backend-name verilator" should "be an alternative way to run using verilator" in {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new DEVICE_UNDER_TEST_0) {
        c => new DEVICE_UNDER_TEST_0UnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new DEVICE_UNDER_TEST_0) {
      c => new DEVICE_UNDER_TEST_0UnitTester(c)
    } should be(true)
  }

  /**
    * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
    * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
    */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new DEVICE_UNDER_TEST_0
    ) {

      c => new DEVICE_UNDER_TEST_0UnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/make_a_vcd.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new DEVICE_UNDER_TEST_0
    ) {

      c => new DEVICE_UNDER_TEST_0UnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}
