import java.util.ArrayList;
import java.util.PriorityQueue;

class Edge
	{
		public int srcVert;
		public int destVert;
		public int distance;
		public Edge(int sv, int dv, int d)
		{
			srcVert = sv;
			destVert = dv;
			distance = d;
		}
	}

class Vertex1
{
	public char label;
	public boolean isInTree;
	public Vertex1(char lab)
	{
		label = lab;
		isInTree = false;
	}
}

class Graph1
{
	private final int MAX_VERTS=20;
	private Vertex1 vertexList[]; //list of vertices
	private int adjMat[][]; //adjacency matrix
	private int nVerts;
	private int currentVert;
	private int nTree; //number of verts in tree
	private ArrayList<Edge> edgeList = new ArrayList<Edge>(); //normedgeListly would be a heap
	
	public Graph1()
	{
		vertexList = new Vertex1[MAX_VERTS];
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for(int i=0; i<MAX_VERTS;i++)
			for(int j=0; j<MAX_VERTS; j++)
				adjMat[i][j] = Integer.MAX_VALUE; //no connections nor vertices exist yet
	}
	public Edge removeMin()
	{
		Edge min = edgeList.get(0);
		int index = 0;
		for(int i=0; i < edgeList.size(); i++){
			if(edgeList.get(i).distance<min.distance){
				min = edgeList.get(i);
				index = i;
			}
		}
		edgeList.remove(index);
		return min;
	}
	
	public void addVertex(char lab) 
	{vertexList[nVerts++] = new Vertex1(lab);}
	
	public void addEdge(int start, int end, int weight) //vertices numbered starting from 0
	{
		adjMat[start][end] = weight;
		adjMat[end][start] = weight; //undirected graph
	}
	public void displayVertex(int v){System.out.print(vertexList[v].label);}
	
	public void mstw()
	{
		currentVert = 0;
		while(nTree < nVerts-1)//while not all verts are in tree
		{
			vertexList[currentVert].isInTree = true;
			nTree++;
			
			//insert edges adjacent to currentVert into edgeList
			for(int i=0; i<nVerts; i++) //examining vertex indices
			{
				if(i==currentVert) continue; //skip if it's us
				if(vertexList[i].isInTree) continue; //skip it in tree
				
				int distance = adjMat[currentVert][i];
				if(distance==Integer.MAX_VALUE) continue; //skip if no edge
				
				putInedgeList(i, distance);
			}
			
			if(edgeList.size()==0)
			{
				System.out.println("Graph not connected");
				return;
			}
			
			//remove edge with minimum distance from PQ
			Edge theEdge = removeMin();
			int sourceVert = theEdge.srcVert; //this should equal currentVert
			currentVert = theEdge.destVert;
			
			//display edge from source to current
			System.out.print(vertexList[sourceVert].label);
			System.out.print(vertexList[currentVert].label + " ");			
		}//end while
		
		//mst is complete
		for(int i=0; i<nVerts; i++)
			vertexList[i].isInTree = false;
	}
	
	public void putInedgeList(int newVert, int newDist)
	{
		//is there another edge with the same destination vertex
		int index = -1;
		for(int i=0; i<edgeList.size(); i++){
			if(edgeList.get(i).destVert == newVert)
			{
				index=i;
				break;
			}			
		}
		
		if(index != -1)
		{
			Edge tempEdge = edgeList.get(index);
			int oldDist = tempEdge.distance;
			if(oldDist>newDist)
			{
				edgeList.remove(index);
				Edge theEdge = new Edge(currentVert, newVert, newDist);
				edgeList.add(theEdge);
			}
			//else no action
		}
		else //no edge with same dest vertex
		{
			Edge theEdge = new Edge(currentVert, newVert, newDist);
			edgeList.add(theEdge);
		}
	}
}



public class MinTree {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph1 theGraph = new Graph1();
		theGraph.addVertex('A');
		theGraph.addVertex('B');
		theGraph.addVertex('C');
		theGraph.addVertex('D');
		theGraph.addVertex('E');
		theGraph.addVertex('F');
		
		theGraph.addEdge(0, 1, 6);
		theGraph.addEdge(0, 3, 4);
		theGraph.addEdge(1, 2, 10); // BC 10
		theGraph.addEdge(1, 3, 7); // BD 7
		theGraph.addEdge(1, 4, 7); // BE 7
		theGraph.addEdge(2, 3, 8); // CD 8
		theGraph.addEdge(2, 4, 5); // CE 5
		theGraph.addEdge(2, 5, 6); // CF 6
		theGraph.addEdge(3, 4, 12); // DE 12
		theGraph.addEdge(4, 5, 7); // EF 7
		
		System.out.print("Minimum spanning tree: ");
		theGraph.mstw(); // minimum spanning tree
		System.out.println();	

	}
}