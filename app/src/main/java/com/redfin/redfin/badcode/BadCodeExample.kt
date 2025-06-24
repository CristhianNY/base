package com.redfin.redfin.badcode

object messy {
    fun BADfunc(a :Int,b:Int,c:Int):Int { var r=0 ;for (i in 0..a){for(j in 0..b){for(k in 0..c){r=r+i+j+k}}}return r}

    fun foo(bar:Boolean){
    var x=5
    if(bar==true){
    println("bar is true")
    }
    else
    {
    println("bar is false")
    }
    }

    // this function does too many things
    fun doEverything(v1:String,v2:String,v3:String,v4:String) {
      println(v1)
      println(v2)
      println(v3)
      println(v4)
      var tmp1=v1+v2+v3+v4
      println(tmp1)
      if(v1.length > 3){
          var q=0
          for(i in 0 until 100){
              q+=i
          }
          println(q)
      } else {
          // unused variable
          var q=10
      }
    }

    /* weird comment
    fun commented(){ println("This should not be here") }
    */

    fun    inconsistentSpacing ( ) {
    println ("hi")
    }
}
