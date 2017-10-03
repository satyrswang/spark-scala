/**
 * tree
 * @Author   WYQ
 * @DateTime 2017-10-04T03:23:58+0800
 * @return   {[type]}                 [description]
 */



class GeneralTree[A]{
	class Node(val data:A,val children:Seq[Node]){
		
		private val root:Node =Null
		
		def parent()
		
		def preorder(visit:A=>Unit){
			def recur(n:Node){
				visit(n.data)
				for(c <- n .children) recur(c)
			}
			recur(root)
		}
		def postorder(visit:A=>Unit){
			def recur(n:Node){
				for(c <- n .children) recur(c)
				visit(n.data)
			}
			recur(root)
		}

		def height(n:Node){
			1+n.children.foldLeft(-1)((h,c)=>h max height(c))
		}

		def size(n:Node){
			1+n.children.foldLeft(0)((s,c)=>s+size(c))
		}
	}
}

}







def main(args:Array[String]){

}
