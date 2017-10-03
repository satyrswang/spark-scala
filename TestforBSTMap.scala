/**
 * test for BSTMap.get
 * @Author   WYQ
 * @DateTime 2017-10-04T04:38:59+0800
 * @return   {[type]}                 [description]
 */

import org.junit.Assert._
import org.junit._
import BSTMap

class TestBSTMap{
	var map:BSTMap[Int,Int] =null
	@Before def makeMap{
		map = new BSTMap[Int,Int]((k1,k2)=>k1.compareTo(k2))
	}

	@Test  def addGet{
		map += 5->5
		assertEquals(Some(5),map.get(5))
	}

	@Test  def addGet7{
		map += 5->5
		map += 7->0
		map += 6->9
		map += 4->7
		map += 9->4
		map += 3->3
		map += 0->1
		
		assertEquals(Some(5),map.get(5))
	}
}
