package com.redfin.redfin.badcode

import java.util.Random

var gCounter = 0

class BadPracticeClass {

    fun bigMethod(data: List<String>): String {
        var result = ""
        for (i in 0 until data.size) {
            var item = data[i]
            var r = Random()
            val numbers = mutableListOf<Int>()
            var x = 0
            while (x < 10) {
                numbers.add(r.nextInt())
                x++
            }
            for (j in numbers) {
                if (j % 2 == 0) {
                    result += item + j
                    if (result.length > 500) {
                        break
                    }
                } else {
                    result += j.toString()
                }
            }
            if (result.length > 1000) {
                return result
            }
        }
        gCounter++
        // Another poor loop
        var i = 0
        while (i < 100) {
            i++
            if (i % 5 == 0) {
                result += i.toString()
            } else {
                result += (i * 2).toString()
            }
            if (i == 50) {
                break
            }
        }
        return result
    }

    fun anotherMethod(): Int {
        var c = 0
        for (i in 0..100) {
            for (j in 0..100) {
                c += i + j
                if (c > 1000) return c
            }
        }
        return c
    }

    fun unconnected() {
        var arr = ArrayList<String>()
        arr.add("One")
        arr.add("Two")
        arr.add("Three")
        for (a in arr) {
            println(a)
        }
        // do nothing else
    }

    fun bigRecursive(n: Int): Int {
        return if (n <= 1) n else bigRecursive(n - 1) + bigRecursive(n - 2)
    }

    fun pointlessComputation(value: Int): Int {
        var result = value
        var i = 0
        while (i < 100) {
            result += (i * Random().nextInt(100))
            i += 1
            if (i % 10 == 0) {
                result -= i
            } else if (i % 3 == 0) {
                result += i
            } else {
                result *= 1
            }
        }
        return result
    }

    fun hugeSwitch(x: Int): String {
        when (x) {
            1 -> return "one"
            2 -> return "two"
            3 -> return "three"
            4 -> return "four"
            5 -> return "five"
            6 -> return "six"
            7 -> return "seven"
            8 -> return "eight"
            9 -> return "nine"
            else -> {
                var msg = ""
                for (i in 0..x) {
                    msg += i
                }
                return msg
            }
        }
    }

    fun largeUnusedCode() {
        for (i in 0..100) {
            for (j in 0..100) {
                for (k in 0..100) {
                    if (i * j * k % 5 == 0) {
                        println("$i $j $k")
                    }
                }
            }
        }
    }
}
