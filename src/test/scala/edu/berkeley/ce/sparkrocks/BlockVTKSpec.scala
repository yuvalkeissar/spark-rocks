package edu.berkeley.ce.sparkrocks

import org.scalatest._

import scala.math.sqrt

class BlockVTKSpec extends FunSuite {
  val boundingFaces = List(
    Face(Array(-1.0, 0.0, 0.0), 0.0, phi=0, cohesion=0), // -x = 0
    Face(Array(1.0, 0.0, 0.0), 1.0, phi=0, cohesion=0),  // x = 1
    Face(Array(0.0, -1.0, 0.0), 0.0, phi=0, cohesion=0), // -y = 0
    Face(Array(0.0, 1.0, 0.0), 1.0, phi=0, cohesion=0),  // y = 1
    Face(Array(0.0, 0.0, -1.0), 0.0, phi=0, cohesion=0), // -z = 0
    Face(Array(0.0, 0.0, 1.0), 1.0, phi=0, cohesion=0)   // z = 1
  )
  val unitCube = Block(Array(0.0, 0.0, 0.0), boundingFaces)

  // Seven sided block
  val distance = 1.0 // Should be greater than 1/sqrt(2.0) for intercept calcs to hold
  val boundingFaces2 = List(
    Face(Array(-1.0, 0.0, 0.0), 0.0, phi=0, cohesion=0), // -x <= 0
    Face(Array(1.0, 0.0, 0.0), 1.0, phi=0, cohesion=0),  // x <= 1
    Face(Array(0.0, -1.0, 0.0), 0.0, phi=0, cohesion=0), // -y <= 0
    Face(Array(0.0, 1.0, 0.0), 1.0, phi=0, cohesion=0),  // y <= 1
    Face(Array(0.0, 0.0, -1.0), 0.0, phi=0, cohesion=0), // -z <= 0
    Face(Array(0.0, 0.0, 1.0), 1.0, phi=0, cohesion=0),  // z <= 1
    Face(Array(1/sqrt(2.0), 1/sqrt(2.0), 0.0), distance, phi=0, cohesion=0) // 1/sqrt(2)*x + 1/sqrt(2)*y <= 1
  )
  val sevenSidedBlock = Block(Array(0.0, 0.0, 0.0), boundingFaces2)

  // Faces for unit Cube
  val face1 = Face(Array(-1.0, 0.0, 0.0), 0.0, phi=0, cohesion=0)
  val face2 = Face(Array(1.0, 0.0, 0.0), 1.0, phi=0, cohesion=0)
  val face3 = Face(Array(0.0, -1.0, 0.0), 0.0, phi=0, cohesion=0)
  val face4 = Face(Array(0.0, 1.0, 0.0), 1.0, phi=0, cohesion=0)
  val face5 = Face(Array(0.0, 0.0, -1.0), 0.0, phi=0, cohesion=0)
  val face6 = Face(Array(0.0, 0.0, 1.0), 1.0, phi=0, cohesion=0)
  val face1Verts = List(Array(0.0, 1.0, 0.0), Array(0.0, 0.0, 0.0), Array(0.0, 0.0, 1.0), Array(0.0, 1.0, 1.0))
  val face2Verts = List(Array(1.0, 1.0, 1.0), Array(1.0, 0.0, 1.0), Array(1.0, 0.0, 0.0), Array(1.0, 1.0, 0.0))
  val face3Verts = List(Array(0.0, 0.0, 1.0), Array(0.0, 0.0, 0.0), Array(1.0, 0.0, 0.0), Array(1.0, 0.0, 1.0))
  val face4Verts = List(Array(0.0, 1.0, 0.0), Array(0.0, 1.0, 1.0), Array(1.0, 1.0, 1.0), Array(1.0, 1.0, 0.0))
  val face5Verts = List(Array(1.0, 1.0, 0.0), Array(1.0, 0.0, 0.0), Array(0.0, 0.0, 0.0), Array(0.0, 1.0, 0.0))
  val face6Verts = List(Array(0.0, 1.0, 1.0), Array(0.0, 0.0, 1.0), Array(1.0, 0.0, 1.0), Array(1.0, 1.0, 1.0))

  // Faces for seven sided cube
  val x_intercept = NumericUtils.roundToTolerance(distance - sqrt(2.0)*(sqrt(2.0) - distance))
  val y_intercept = NumericUtils.roundToTolerance(distance - sqrt(2.0)*(sqrt(2.0) - distance))
  val face1_s7 = Face(Array(-1.0, 0.0, 0.0), 0.0, phi=0, cohesion=0)
  val face2_s7 = Face(Array(1.0, 0.0, 0.0), 1.0, phi=0, cohesion=0)
  val face3_s7 = Face(Array(0.0, -1.0, 0.0), 0.0, phi=0, cohesion=0)
  val face4_s7 = Face(Array(0.0, 1.0, 0.0), 1.0, phi=0, cohesion=0)
  val face5_s7 = Face(Array(0.0, 0.0, -1.0), 0.0, phi=0, cohesion=0)
  val face6_s7 = Face(Array(0.0, 0.0, 1.0), 1.0, phi=0, cohesion=0)
  val face7_s7 = Face(Array(1/sqrt(2.0), 1/sqrt(2.0), 0.0), 1.0, phi=0, cohesion=0)
  val face1Verts_s7 = List[Array[Double]](Array(0.0, 1.0, 0.0), Array(0.0, 0.0, 0.0), Array(0.0, 0.0, 1.0),
                                          Array(0.0, 1.0, 1.0))
  val face2Verts_s7 = List[Array[Double]](Array(1.0, y_intercept, 1.0), Array(1.0, 0.0, 1.0), Array(1.0, 0.0, 0.0),
                                          Array(1.0, y_intercept, 0.0))
  val face3Verts_s7 = List[Array[Double]](Array(0.0, 0.0, 1.0), Array(0.0, 0.0, 0.0), Array(1.0, 0.0, 0.0),
                                          Array(1.0, 0.0, 1.0))
  val face4Verts_s7 = List[Array[Double]](Array(0.0, 1.0, 0.0), Array(0.0, 1.0, 1.0), Array(x_intercept, 1.0, 1.0),
                                          Array(x_intercept, 1.0, 0.0))
  val face5Verts_s7 = List[Array[Double]](Array(1.0, y_intercept, 0.0), Array(1.0, 0.0, 0.0), Array(0.0, 0.0, 0.0),
                                          Array(0.0, 1.0, 0.0), Array(x_intercept, 1.0, 0.0))
  val face6Verts_s7 = List[Array[Double]](Array(x_intercept, 1.0, 1.0), Array(0.0, 1.0, 1.0), Array(0.0, 0.0, 1.0),
                                          Array(1.0, 0.0, 1.0), Array(1.0, y_intercept, 1.0))
  val face7Verts_s7 = List[Array[Double]](Array(x_intercept, 1.0, 1.0), Array(1.0, y_intercept, 1.0),
                                          Array(1.0, y_intercept, 0.0), Array(x_intercept, 1.0, 0.0))

  private def doubleListElementDiff(list1: Seq[Double], list2: Seq[Double]): Seq[Double] = {
    assert(list1.length == list2.length)
    list1.zip(list2) map { case (a,b) => a - b }
  }

  private def intListElementDiff(list1: Seq[Int], list2: Seq[Int]): Seq[Int] = {
    assert(list1.length == list2.length)
    list1.zip(list2) map { case (a,b) => a - b }
  }

  test("The vertices should be oriented counter-clockwise for each face") {
    val expectedVertices = Map(
      face1 -> face1Verts,
      face2 -> face2Verts,
      face3 -> face3Verts,
      face4 -> face4Verts,
      face5 -> face5Verts,
      face6 -> face6Verts
    )
    val vtkBlock = BlockVTK(unitCube)
    assert(vtkBlock.orientedVertices.keys == expectedVertices.keys && vtkBlock.orientedVertices.keys.forall { key =>
      vtkBlock.orientedVertices(key).zip(expectedVertices(key)) forall { case (v1, v2) =>
        v1 sameElements v2
      }
    })
  }

  test("The vertices should be oriented counter-clockwise for each face (seven-sided block)") {
    val expectedVertices = Map(
      face1_s7 -> face1Verts_s7,
      face2_s7 -> face2Verts_s7,
      face3_s7 -> face3Verts_s7,
      face4_s7 -> face4Verts_s7,
      face5_s7 -> face5Verts_s7,
      face6_s7 -> face6Verts_s7,
      face7_s7 -> face7Verts_s7
    )
    val vtkBlock = BlockVTK(sevenSidedBlock)
    assert(vtkBlock.orientedVertices.keys == expectedVertices.keys && vtkBlock.orientedVertices.keys.forall { key =>
      vtkBlock.orientedVertices(key).zip(expectedVertices(key)) forall { case (v1, v2) =>
        v1 sameElements v2
      }
    })
  }

  test("List of vertices should contain only distinct vertices as tuples") {
    val vtkBlock = BlockVTK(unitCube)
    val expectedVertices = Seq(Array(0.0, 1.0, 0.0), Array(0.0, 0.0, 0.0), Array(0.0, 0.0, 1.0), Array(0.0, 1.0, 1.0),
                               Array(1.0, 1.0, 1.0), Array(1.0, 0.0, 1.0), Array(1.0, 0.0, 0.0), Array(1.0, 1.0, 0.0))
    assert(vtkBlock.arrayVertices.forall { v1 => expectedVertices.exists(v2 => v1 sameElements v2) })
  }

  test("List of vertices should contain only distinct vertices as tuples (seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)
    val expectedVertices = Seq(Array(0.0, 1.0, 0.0), Array(0.0, 0.0, 0.0), Array(0.0, 0.0, 1.0),
                               Array(0.0, 1.0, 1.0), Array(1.0, y_intercept, 1.0), Array(1.0, 0.0, 1.0),
                               Array(1.0, 0.0, 0.0), Array(1.0, y_intercept, 0.0),
                               Array(x_intercept, 1.0, 1.0), Array(x_intercept, 1.0, 0.0))
    assert(vtkBlock.arrayVertices.forall { v1 => expectedVertices.exists(v2 => v1 sameElements v2) })
  }

  test("List of tuple vertices should flatten into list of doubles") {
    val vtkBlock = BlockVTK(unitCube)

    val expectedVertices = Seq(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0,
      1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0)

    val verticesDiff = doubleListElementDiff(expectedVertices, vtkBlock.vertices)
    assert(verticesDiff forall (_ < NumericUtils.EPSILON))
  }

  test("List of tuple vertices should flatten into list of doubles (seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)

    val expectedVertices = Seq(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0,
      1.0, y_intercept, 0.0, 1.0, y_intercept, 1.0, x_intercept, 1.0, 0.0, x_intercept, 1.0, 1.0,
      0.0, 1.0, 1.0, 0.0, 1.0, 0.0)

    val verticesDiff = doubleListElementDiff(expectedVertices, vtkBlock.vertices)
    assert(verticesDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Vertex ID's should start from 0 and go up to 7") {
    val vtkBlock = BlockVTK(unitCube)
    val expectedIDs = 0 until 8
    val idsDiff = intListElementDiff(expectedIDs, vtkBlock.vertexIDs)
    assert(idsDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Vertex ID's should start from 0 and go up to 9 (seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)
    val expectedIDs = 0 until 10
    val idsDiff = intListElementDiff(expectedIDs, vtkBlock.vertexIDs)
    assert(idsDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Connectivities for each face should be in terms of vertex ID's") {
    val vtkBlock = BlockVTK(unitCube)
    val faceConnectivities = Map(
      face1 -> Seq[Int](7, 1, 3, 6),
      face2 -> Seq[Int](5, 2, 0, 4),
      face3 -> Seq[Int](3, 1, 0, 2),
      face4 -> Seq[Int](7, 6, 5, 4),
      face5 -> Seq[Int](4, 0, 1, 7),
      face6 -> Seq[Int](6, 3, 2, 5)
    )

    val connMap = vtkBlock.connectivityMap
    assert(connMap.keySet == faceConnectivities.keySet && connMap.keys.forall { face =>
      connMap(face) == faceConnectivities(face)
    })
  }

  test("Connectivities for each face should be in terms of vertex ID's (seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)

    val faceConnectivities = Map(
      face1_s7 -> Seq[Int](9, 1, 3, 8),
      face2_s7 -> Seq[Int](5, 2, 0, 4),
      face3_s7 -> Seq[Int](3, 1, 0, 2),
      face4_s7 -> Seq[Int](9, 8, 7, 6),
      face5_s7 -> Seq[Int](4, 0, 1, 9, 6),
      face6_s7 -> Seq[Int](7, 8, 3, 2, 5),
      face7_s7 -> Seq[Int](7, 5, 4, 6)
    )

    assert(faceConnectivities == vtkBlock.connectivityMap)
  }

  test("Connectivity map should flatten into list of integers") {
    val vtkBlock = BlockVTK(unitCube)

    val faceConnectivities = Seq(7, 6, 5, 4, 6, 3, 2, 5, 3, 1, 0, 2, 7, 1, 3, 6, 4, 0, 1, 7, 5, 2, 0, 4)

    val connectionsDiff = intListElementDiff(faceConnectivities, vtkBlock.connectivity.toSeq)
    assert(connectionsDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Connectivity map should flatten into list of integers (seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)

    val faceConnectivities = Seq(9, 8, 7, 6, 7, 5, 4, 6, 7, 8, 3, 2, 5, 3, 1, 0, 2, 9, 1, 3, 8,
      4, 0, 1, 9, 6, 5, 2, 0, 4)

    val connectionsDiff = intListElementDiff(faceConnectivities, vtkBlock.connectivity.toSeq)
    assert(connectionsDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Number of faces should be 6") {
    val vtkBlock = BlockVTK(unitCube)
    assert(vtkBlock.faceCount == 6)
  }

  test("Number of faces should be 7 (seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)
    assert(vtkBlock.faceCount == 7)
  }

  test("Offsets should be incremented by number of vertices on each face when iterating through list of faces") {
    val vtkBlock = BlockVTK(unitCube)
    val expectedOffsets = Seq(4, 8, 12, 16, 20, 24)
    val offsetDiff = intListElementDiff(expectedOffsets, vtkBlock.offsets)
    assert(offsetDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Offsets should be incremented by number of vertices on each face when iterating through list of faces "+
       "(seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)
    val expectedOffsets = Seq(4, 8, 12, 16, 20, 25, 30)
    val offsetDiff = intListElementDiff(expectedOffsets, vtkBlock.offsets)
    assert(offsetDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Normals tuples should flatten into list of integers") {
    val vtkBlock = BlockVTK(unitCube)
    val expectedNormals = Seq[Double](
      face4.a, face4.b, face4.c,
      face6.a, face6.b, face6.c,
      face3.a, face3.b, face3.c,
      face1.a, face1.b, face1.c,
      face5.a, face5.b, face5.c,
      face2.a, face2.b, face2.c
    )
    val normalsDiff = doubleListElementDiff(expectedNormals, vtkBlock.normals)
    assert(normalsDiff forall (_ < NumericUtils.EPSILON))
  }

  test("Normals tuples should flatten into list of integers (seven-sided block)") {
    val vtkBlock = BlockVTK(sevenSidedBlock)
    val expectedNormals = Seq[Double](
      face4_s7.a, face4_s7.b, face4_s7.c,
      face7_s7.a, face7_s7.b, face7_s7.c,
      face6_s7.a, face6_s7.b, face6_s7.c,
      face3_s7.a, face3_s7.b, face3_s7.c,
      face1_s7.a, face1_s7.b, face1_s7.c,
      face5_s7.a, face5_s7.b, face5_s7.c,
      face2_s7.a, face2_s7.b, face2_s7.c
    )

    val normals = vtkBlock.normals
    val normalsDiff = doubleListElementDiff(expectedNormals, normals)
    assert(normalsDiff forall (_ < NumericUtils.EPSILON))
  }
}
