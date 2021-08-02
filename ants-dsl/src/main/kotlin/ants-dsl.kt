package ants.auto

import com.autodsl.annotation.AutoDsl

@AutoDsl
class WoodAnt(val name: String, val age: Int, val friends: List<WoodAnt>?)