# chisel3-device_under_test
Template to write &amp; validate your hardware module with Chisel3 language for absolute beginner

## Preparation before you start

First of all you need to install sbt(scala build tool) and verilator in your environment.

### install sbt in CentOS7
```
$> yum -y install epel-release
$> yum -y install java emacs gtkwave
$> curl https://bintray.com/sbt/rpm/rpm | \
    tee /etc/yum.repos.d/bintray-sbt-rpm.repo
$> yum -y install sbt
```

### install verilator on CentOS7
```
$> cd ~/work ; git clone http://git.veripool.org/git/verilator	
$> unset VERILATOR_ROOT
$> cd ~/work/verilator; autoconf; ./configure; make -j3; make install
```

## How to start

Check out from github repositry. 
```
$> mkdir ~/work
$> cd ~/work
$> git clone https://github.com/SuminoeRabbits/chisel3-device_under_test.git MyProject
$> cd MyProject
```
Then starting sbt,
```
$> sbt "compile"
$> sbt "test:runMain device_under_test_0.DEVICE_UNDER_TEST_0Main" # just run with scala
$> sbt "test:runMain device_under_test_0.DEVICE_UNDER_TEST_0Main --backend-name verilator" # to enable verilator backend
```
## How to check the simulation results

You can check translated verilog HDL description and vcd under MyProject/test_run_dir/device_under_test_0.DEVICE_UNDER_TEST_0Mainxxxxxxxx directory when using verilator backend option. To see the vcd waveform, use gtkwave.

```
$> gtkwave DEVICE_UNDER_TEST_0.vcd
```



## How to modify these files for your own design needs
 
 ### chisel3-device_under_test/src/main/scala/dut.scala
 This is file to describe DUT(device under test) and its TB(testbench). You can modify your DUT and TB whatever you like.

 ### chisel3-device_under_test/src/test/scala/main.scala
 This is file to describe main object. Also extending class to let merget verilator backend to kick RTL simulation. This file is independent from DUT and TB, you don't have to change the file if you are happy with current environment.



## License

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or distribute this software, either in source code form or as a compiled binary, for any purpose, commercial or non-commercial, and by any means.

In jurisdictions that recognize copyright laws, the author or authors of this software dedicate any and all copyright interest in the software to the public domain. We make this dedication for the benefit of the public at large and to the detriment of our heirs and successors. We intend this dedication to be an overt act of relinquishment in perpetuity of all present and future rights to this software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to http://unlicense.org/
