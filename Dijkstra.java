
class DistPar
{
	public int distance;
	public int parentVert;
	
	public DistPar(int pv, int d)
	{
		distance = d;
		parentVert  = pv;
	}
}

class Vertex
{
	public char label;
	public boolean isInTree;
	
	public Vertex(char lab)
	{
		label = lab;
		isInTree = false;
	}
}

class Graph
{
	private final int MAX_VERTS = 20;
	private Vertex vertexList[]; //list of vertices
	private int adjMat[][]; //adjacency matrix
	private int nVerts;
	private int nTree; //number of verts in tree
	private DistPar sPath[]; //array for shortest path data
	private int currentVert; //current vertex
	private int startToCurrent; //distance to currentVert
	private int INFINITY = 1000000;
	
	public Graph()
	{
		vertexList = new Vertex[MAX_VERTS];
		
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0; nTree = 0;
		
		//adjacency matrix
		for(int j=0; j<MAX_VERTS; j++)
			for(int k=0; k<MAX_VERTS; k++)
				adjMat[j][k] = INFINITY;
		
		sPath = new DistPar[MAX_VERTS]; //shortest paths
	}
	
	public void addVertex(char lab)
	{
		vertexList[nVerts++] = new Vertex(lab);
	}
	
	public void addEdge(int start, int end, int weight)
	{
		adjMat[start][end] = weight; //(directed)
	}
	
	public void path()
	{
		int startTree = 0; //start at vertex 0
		vertexList[startTree].isInTree = true;
		nTree = 1; //put it in tree
		
		//transfer row of distances from adjMat to sPath
		for(int j=0; j<nVerts; j++)
		{
			int tempDist = adjMat[startTree][j];
			sPath[j] = new DistPar(startTree, tempDist);
		}
		
		//until all vertices are in the tree
		while(nTree < nVerts)
		{
			int indexMin = getMin(); //get min from sPath
			int minDist = sPath[indexMin].distance;
			
			if(minDist == INFINITY) //if all infinite
			{
				System.out.println("there are unreachable vertices");
				break;
			}
			else
			{
				currentVert = indexMin; //reset currentVert to closest vert
				startToCurrent = sPath[indexMin].distance;
				//min distance from startTree is to currentVert,
				//and is startToCurrent
				
			}
			//put current vertex in tree
			vertexList[currentVert].isInTree = true;
			nTree++;
			adjust_sPath();			
		}
		
		displayPaths();
		
		//clear tree
		nTree = 0;
		for(int j=0; j<nVerts; j++)
			vertexList[j].isInTree = false;
	}
	
	public int getMin() //get entry from sPath with min distance
	{
		int minDist = INFINITY;
		int indexMin = 0;
		for(int j=1; j<nVerts; j++) //for each vertex
		{
			if(!vertexList[j].isInTree && sPath[j].distance<minDist) //ignore towns with agents
			{
				minDist = sPath[j].distance;
				indexMin = j;
			}
		}
		return indexMin;
	}
	
	public void adjust_sPath()
	{
		int column = 1; //skip starting vertex
		while(column < nVerts) //go across columns
		{
			//if this column's vertex already in tree, skip
			if(vertexList[column].isInTree)
			{
				column++;
				continue;
			}
			//calculate distances for one sPath entry
				//get edge from currentVert to column
			int currentToFringe = adjMat[currentVert][column];
				//add distance from start
			int startToFringe = startToCurrent + currentToFringe;
				//get distance of current sPath entry
			int sPathDist = sPath[column].distance;
			
			//compare distance from start with sPath entry
			if(startToFringe < sPathDist) //if shorter
			{
				sPath[column].parentVert = currentVert;
				sPath[column].distance = startToFringe;
			}
			column++;
		}
	}
	
	public void displayPaths()
	{
		for(int j=0; j<nVerts; j++)
		{
			System.out.print(vertexList[j].label + "=");
			if(sPath[j].distance == INFINITY)
				System.out.print("inf");
			else
				System.out.print(sPath[j].distance);
			char parent = vertexList[sPath[j].parentVert].label;
			System.out.print("(" + parent + ")");
		}
		System.out.println("");
	}
	
}



public class Dijkstra {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph theGraph = new Graph();
		theGraph.addVertex('A'); // 0 (start)
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4
		theGraph.addEdge(0, 1, 50); // AB 50
		theGraph.addEdge(0, 3, 80); // AD 80
		theGraph.addEdge(1, 2, 60); // BC 60
		theGraph.addEdge(1, 3, 90); // BD 90
		theGraph.addEdge(2, 4, 40); // CE 40
		theGraph.addEdge(3, 2, 20); // DC 20
		theGraph.addEdge(3, 4, 70); // DE 70
		theGraph.addEdge(4, 1, 50); // EB 50
		System.out.println("Shortest paths");
		theGraph.path(); // shortest paths
		System.out.println();

	}

}
