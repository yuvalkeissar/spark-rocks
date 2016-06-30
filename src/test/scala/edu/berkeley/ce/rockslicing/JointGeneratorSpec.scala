package edu.berkeley.ce.rockslicing

import org.scalatest.FunSuite

import scala.math.sqrt

class JointGeneratorSpec extends FunSuite {

  private def compareJoints(joint1: Joint, joint2: Joint): Boolean = {
    val norm1 = Array(NumericUtils.roundToTolerance(joint1.a), NumericUtils.roundToTolerance(joint1.b),
      NumericUtils.roundToTolerance(joint1.c))
    val norm2 = Array(NumericUtils.roundToTolerance(joint2.a), NumericUtils.roundToTolerance(joint2.b),
      NumericUtils.roundToTolerance(joint2.c))
    val center1 = Array(NumericUtils.roundToTolerance(joint1.centerX), NumericUtils.roundToTolerance(joint1.centerY),
      NumericUtils.roundToTolerance(joint1.centerZ))
    val center2 = Array(NumericUtils.roundToTolerance(joint2.centerX), NumericUtils.roundToTolerance(joint2.centerY),
      NumericUtils.roundToTolerance(joint2.centerZ))
    val origin1 = Array(NumericUtils.roundToTolerance(joint1.localX), NumericUtils.roundToTolerance(joint1.localY),
      NumericUtils.roundToTolerance(joint1.localZ))
    val origin2 = Array(NumericUtils.roundToTolerance(joint2.localX), NumericUtils.roundToTolerance(joint2.localY),
      NumericUtils.roundToTolerance(joint2.localZ))

    (norm1 sameElements norm2) && (center1 sameElements center2) && (origin1 sameElements origin2)
  }

  test("Face should have outward pointing normal (0.0, 0.0, 1.0) and distance 1.0") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 0.0, 0.0, 0.0, 2.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Face(Array(0.0, 0.0, 1.0), 1.0, phi = 30.0, cohesion = 0.0)
    assert(expectedFace == generatedRockData.rockVolume.map(_.roundToTolerance()).head)
  }

  test("Face should have outward pointing normal (0.0, 0.0, -1.0) and distance 1.0") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Face(Array(0.0, 0.0, -1.0), 1.0, phi = 30.0, cohesion = 0.0)
    assert(expectedFace == generatedRockData.rockVolume.map(_.roundToTolerance()).head)
  }

  test("Face should have outward pointing normal (1.0, 0.0, 0.0) and distance 1.0") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(90.0, 90.0, 2.0, 0.0, 0.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Face(Array(1.0, 0.0, 0.0), 1.0, phi = 30.0, cohesion = 0.0)
    assert(expectedFace == generatedRockData.rockVolume.map(_.roundToTolerance()).head)
  }

  test("Face should have outward pointing normal (-1.0, 0.0, 0.0) and distance 1.0") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(90.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Face(Array(-1.0, 0.0, 0.0), 1.0, phi = 30.0, cohesion = 0.0)
    assert(expectedFace == generatedRockData.rockVolume.map(_.roundToTolerance()).head)
  }

  test("Face should have outward pointing normal (0.0, 1.0, 0.0) and distance 1.0") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 90.0, 0.0, 2.0, 0.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Face(Array(0.0, 1.0, 0.0), 1.0, phi = 30.0, cohesion = 0.0)
    assert(expectedFace == generatedRockData.rockVolume.map(_.roundToTolerance()).head)
  }

  test("Face should have outward pointing normal (0.0, -1.0, 0.0) and distance 1.0") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Face(Array(0.0, -1.0, 0.0), 1.0, phi = 30.0, cohesion = 0.0)
    assert(expectedFace == generatedRockData.rockVolume.map(_.roundToTolerance()).head)
  }

  test("Face should have outward pointing normal (0.0, 1/sqrt(2.0), 1.0/sqrt(2.0)) and distance 1.0") {
    val globalOrigin = Array[Double](0.0, 0.0, 0.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(180.0, 45.0, 0.0, 1.0/sqrt(2.0), 1.0/sqrt(2.0), 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 45.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Seq(Face(Array(0.0, 1.0/sqrt(2.0), 1.0/sqrt(2.0)), 1.0, phi = 30.0, cohesion = 0.0))
    assert(expectedFace.map(_.roundToTolerance()) == generatedRockData.rockVolume.map(_.roundToTolerance()))
  }

  test("Face should have outward pointing normal (1.0/2.0, 0.0, sqrt(3.0)/2.0) and distance 1.0") {
    val globalOrigin = Array[Double](0.0, 0.0, 0.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(270, 30.0, 1.0/2.0, 0.0, sqrt(3.0)/2.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 45.0, 0.5, 100.0, 30.0, 0.0)
    )

    val generatedRockData = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedFace = Seq(Face(Array(1.0/2.0, 0.0, sqrt(3.0)/2.0), 1.0, phi = 30.0, cohesion = 0.0))
    assert(expectedFace.map(_.roundToTolerance()) == generatedRockData.rockVolume.map(_.roundToTolerance()))
  }

  test("Rock volume faces should be two cube") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 3.0, 3.0, 3.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 90.0, 0.0, 2.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 2.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 2.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )
    val generatedInput = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedOrigin = Array[Double](1.0, 1.0, 1.0)
    val expectedLowerLeftCorner = Array[Double](-1.0, -1.0, -1.0)
    val expectedUpperRightCorner = Array[Double](3.0, 3.0, 3.0)

    val expectedRockVolume = Seq[Face](
      Face(Array(0.0, -1.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(0.0, 1.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(-1.0, 0.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(1.0, 0.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(0.0, 0.0, -1.0), 1.0, 30.0, 0.0),
      Face(Array(0.0, 0.0, 1.0), 1.0, 30.0, 0.0)
    )
    assert((expectedOrigin sameElements generatedInput.origin) &&
      (expectedLowerLeftCorner sameElements generatedInput.lowerLeftCorner) &&
      (expectedUpperRightCorner sameElements generatedInput.upperRightCorner) &&
      expectedRockVolume == generatedInput.rockVolume.map(_.roundToTolerance())
    )
  }

  test("Master joint should be x-y plane centered at (-1.0, -1.0, -1.0)") {
    val globalOrigin = Array[Double](0.5, 0.5, 0.5)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 3.0, 3.0, 3.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 90.0, 0.0, 2.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 2.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 2.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 0.0, 0.5, 100.0, 30.0, 0.0)
    )
    val generatedInput = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)

    val expectedJoints = Seq(
      Joint(Array(0.0, 0.0, -1.0), Array(0.5, 0.5, 0.5), Array(-1.0, -1.0, -1.0), phi = 30.0,
        cohesion = 0.0, shape = Vector.empty)
    )
    assert(expectedJoints == generatedInput.masterJoints)
  }

  test("Master joint should have normal (sqrt(3.0)/2.0, 0.0, 1.0/2.0) and be centered at (-1.0, -1.0, -1.0)") {
    val globalOrigin = Array[Double](0.5, 0.5, 0.5)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 3.0, 3.0, 3.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 90.0, 0.0, 2.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 2.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 2.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(270.0, 30.0, 0.5, 100.0, 30.0, 0.0)
    )
    val generatedInput = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)

    val expectedJoints = Seq(
      Joint(Array(-1.0/2.0, 0.0, -sqrt(3.0)/2.0), Array(0.5, 0.5, 0.5), Array(-1.0, -1.0, -1.0), phi = 30.0,
        cohesion = 0.0, shape = Vector.empty)
    )

    val jointComparison = expectedJoints.zip(generatedInput.masterJoints) map { case (joint1, joint2) =>
        compareJoints(joint1, joint2)
    }

    assert(!jointComparison.contains(false))
  }

  test("Generated joint set should be x-z plane, spaced 0.5 apart") {
    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
    val rockVolume = Array[Array[Double]](
      Array(0.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 90.0, 0.0, 2.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(90.0, 90.0, 2.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0),
      Array(0.0, 0.0, 0.0, 0.0, 2.0, 30.0, 0.0)
    )
    val jointData = Array[Array[Double]](
      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
    )
    val generatedInput = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
    val expectedOrigin = Array[Double](1.0, 1.0, 1.0)
    val expectedLowerLeftCorner = Array[Double](-1.0, -1.0, -1.0)
    val expectedUpperRightCorner = Array[Double](2.0, 2.0, 2.0)

    val expectedRockVolume = Seq[Face](
      Face(Array(0.0, -1.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(0.0, 1.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(-1.0, 0.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(1.0, 0.0, 0.0), 1.0, 30.0, 0.0),
      Face(Array(0.0, 0.0, -1.0), 1.0, 30.0, 0.0),
      Face(Array(0.0, 0.0, 1.0), 1.0, 30.0, 0.0)
    )

    val expectedJoints = Seq[Seq[Joint]](
      Seq[Joint](Joint(Array(0.0, -1.0, 0.0), Array(1.0, 1.0, 1.0), Array(-1.0, 1.0, -1.0), phi = 30.0,
        cohesion = 0.0, shape = Vector.empty))
        )

    println("These are the GENERATED joints:")
    generatedInput.jointSets.head.foreach{ joint =>
      println(s"NormalVec: ${joint.a}, ${joint.b}, ${joint.c}")
      println(s"Center: ${joint.centerX}, ${joint.centerY}, ${joint.centerZ}")
      println(s"Origin: ${joint.localX}, ${joint.localY}, ${joint.localZ}")
      println(s"Distance: ${joint.d}")
      println(s"Dip: ${joint.dipAngle}")
      println(s"Dip direction: ${joint.dipDirection}")
      println(s"Phi: ${joint.phi}")
      println(s"Cohesion: ${joint.cohesion}")
    }

    println("\nThese are the EXPECTED joints:")
    expectedJoints.head.foreach { joint =>
      println(s"NormalVec: ${joint.a}, ${joint.b}, ${joint.c}")
      println(s"Center: ${joint.centerX}, ${joint.centerY}, ${joint.centerZ}")
      println(s"Origin: ${joint.localX}, ${joint.localY}, ${joint.localZ}")
      println(s"Distance: ${joint.d}")
      println(s"Dip: ${joint.dipAngle}")
      println(s"Dip direction: ${joint.dipDirection}")
      println(s"Phi: ${joint.phi}")
      println(s"Cohesion: ${joint.cohesion}")
    }

    assert((expectedOrigin sameElements generatedInput.origin) &&
      (expectedLowerLeftCorner sameElements generatedInput.lowerLeftCorner) &&
      (expectedUpperRightCorner sameElements generatedInput.upperRightCorner) &&
      expectedRockVolume == generatedInput.rockVolume.map(_.roundToTolerance()) &&
      expectedJoints == generatedInput.jointSets)
  }
  //  test("Testing constructor") {
//    val globalOrigin = Array[Double](1.0, 1.0, 1.0)
//    val boundingBox = Array[Double](-1.0, -1.0, -1.0, 2.0, 2.0, 2.0)
//    val rockVolume = Array[Array[Double]](
//      Array(0.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
//      Array(0.0, 90.0, 0.0, 2.0, 0.0, 30.0, 0.0),
//      Array(90.0, 90.0, 0.0, 0.0, 0.0, 30.0, 0.0),
//      Array(90.0, 90.0, 2.0, 0.0, 0.0, 30.0, 0.0),
//      Array(0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0),
//      Array(0.0, 0.0, 0.0, 0.0, 2.0, 30.0, 0.0)
//    )
//    val jointData = Array[Array[Double]](
//      Array(0.0, 90.0, 0.5, 100.0, 30.0, 0.0)
//    )
//    val generatedInput = JointGenerator(globalOrigin, boundingBox, rockVolume, jointData)
//    val expectedOrigin = Array[Double](1.0, 1.0, 1.0)
//    val expectedLowerLeftCorner = Array[Double](-1.0, -1.0, -1.0)
//    val expectedUpperRightCorner = Array[Double](2.0, 2.0, 2.0)
//
//    val expectedRockVolume = Seq[Face](
//      Face(Array(0.0, -1.0, 0.0), 1.0, 30.0, 0.0),
//      Face(Array(0.0, 1.0, 0.0), 1.0, 30.0, 0.0),
//      Face(Array(-1.0, 0.0, 0.0), 1.0, 30.0, 0.0),
//      Face(Array(1.0, 0.0, 0.0), 1.0, 30.0, 0.0),
//      Face(Array(0.0, 0.0, -1.0), 1.0, 30.0, 0.0),
//      Face(Array(0.0, 0.0, 1.0), 1.0, 30.0, 0.0)
//    )
//
//    val expectedJoints = Seq[Seq[Joint]](
//      Seq[Joint](Joint(Array(0.0, -1.0, 0.0), Array(1.0, 1.0, 1.0), Array(-1.0, 1.0, -1.0), phi = 30.0,
//        cohesion = 0.0, shape = Vector.empty))
//    )
//
//    println("These are the GENERATED joints:")
//    generatedInput.jointSets.head.foreach{ joint =>
//      println(s"NormalVec: ${joint.a}, ${joint.b}, ${joint.c}")
//      println(s"Center: ${joint.centerX}, ${joint.centerY}, ${joint.centerZ}")
//      println(s"Origin: ${joint.localX}, ${joint.localY}, ${joint.localZ}")
//      println(s"Distance: ${joint.d}")
//      println(s"Dip: ${joint.dipAngle}")
//      println(s"Dip direction: ${joint.dipDirection}")
//      println(s"Phi: ${joint.phi}")
//      println(s"Cohesion: ${joint.cohesion}")
//    }
//
//    println("\nThese are the EXPECTED joints:")
//    expectedJoints.head.foreach { joint =>
//      println(s"NormalVec: ${joint.a}, ${joint.b}, ${joint.c}")
//      println(s"Center: ${joint.centerX}, ${joint.centerY}, ${joint.centerZ}")
//      println(s"Origin: ${joint.localX}, ${joint.localY}, ${joint.localZ}")
//      println(s"Distance: ${joint.d}")
//      println(s"Dip: ${joint.dipAngle}")
//      println(s"Dip direction: ${joint.dipDirection}")
//      println(s"Phi: ${joint.phi}")
//      println(s"Cohesion: ${joint.cohesion}")
//    }
//
//    assert((expectedOrigin sameElements generatedInput.origin) &&
//      (expectedLowerLeftCorner sameElements generatedInput.lowerLeftCorner) &&
//      (expectedUpperRightCorner sameElements generatedInput.upperRightCorner) &&
//      expectedRockVolume == generatedInput.rockVolume.map(_.roundToTolerance()) &&
//      expectedJoints == generatedInput.jointSets)
//  }
}
