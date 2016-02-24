# Weighted-Graphs

Minimum Spanning Tree
  - Greedy Algorithm
  - Always choose the least cost path
  - Uses a priority queue 
  - Start with a vertex, and put it in the tree. Then repeatedly do the following:
    1. Find all the edges from the newest vertex to other vertices that aren’t in the
    tree. Put these edges in the priority queue. *
    2. Pick the edge with the lowest weight, and add this edge and its destination
    vertex to the tree 

- *Prune through the priorityqueue to ensure each category 2 vertex (fringe) only has one edge connected to it (smallest possible weight)
