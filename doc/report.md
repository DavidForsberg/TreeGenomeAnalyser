**Iterative vs recursive graph traversion**

When starting the project I used a recursive version of a DFS to traverse the implemented graph. This worked perfectly fine when running the smaller parts of the large data file. But I ran into a problem when running the search on larger parts. When running the program with the parted file of 10m predicted edges, the program crashed because of a stack overflow error during the traversing part. This was caused by the DFS being recursive. Since there were much more children and larger components, the recursion got too deep and too many calls were put on the stack. 

After reading multiple resources online, mostly stackoverflow Q&A's I came to the conclusion to change from a recursive DFS to a iterative one. Changing to an iterative DFS worked perfectly fine as I implemented a custom stack to the function. 

Although I liked the recursive method more, switching to an iterative search made more sense. I left the recursive DFS in the code to make the code more readable for anyone reading through the code as they basically computes the same results, but in different ways.



**DFS - Deep first search**

The main reason for using DFS instead of a BFS (breadth first search) was originally just because I like the way it's implemented, and I find it fairly easy as I have done it more. I also found some information on stackoverflow and other online resources that DFS would make more sense for counting the number of connected components.



**Adjacency list**

The adjacency list made more sense in solving the problem since the graph had a lot of edges with few neighbors. Just looking at the data most of the edges seemed to be connected to the same vertex, which made the graph fairly sparse, although some of the edges had much more. I also found the adjacency list to be more readable than the other option *adjacency matrix*.

The adjacency list makes fast to add an edge, as do the matrix. One edge the list have over the matrix version is that it's faster to add and edge, the matrix uses a time complexion of O(n^2) to add a vertex.

The edge iteration is also faster than the matrix one since all the neighbors is in a list connected directly to the current vertex.

If given more time I would have explored the option to use a matrix instead, just to see if it made any reasonable difference. But the program works perfectly fine with the list implementation.

**Hashmap**

...

**Hashing the vertex identifiers**

There are some possibilities that different vertexes have the same hash. This would cause a pretty big problem to the results depending on how severe and often it occurs and would explain missing edges and some outliers.

...

**Results**

When building the program I used the bash script 

​	*split -l <number_of_lines_per_file> <filename.m4>* 

to split the file in multiple smaller parts. When starting of I used 10, 100, 1000, ..., etc. per file to spend less time running the program. Once the program was completed the program produces the expected results for the smaller split files. 

After this I ran the program on the big file, after switching to an iterative DFS, and it should produce the correct results.

The whole graph (.m4 file) gave the following results:

- Number of edges: 8078262 (8.08m), which would mean around 56m of the edges does not contain a sufficient overlap that follows the requirements.
- Number of components: 956282 (0.96m)

Which I believe is an accurate result. A lot of predicted edges were removed because of overlapping reasons and repetitions. The number of edges are around 12.5% of the predicted edges, and the number of connected components are around 11.8% of the saved edges. 

The data explanation gave the information that each vertex would have 2-5 neighbors on average, but some would have a lot more neighbors. This fits well with the given results with some outliers. Although the results gave an exceeding amount of vertexes with only one neighbor. 

![Histogram of the node degree distribution](C:\Users\Admin\Desktop\node_distr_v1.png)

Since a lot of the vertexes only have one edge, I would assume from reading some of the data, that some edges has a lot of neighbors without any additional once. This explains some of the huge outliers. Many of the contig B's in the data file seems to occurring only once and these are counted as an own vertex, which could also be a reason for the number of nodes with degree 1.

![component_distr_v1](C:\Users\Admin\Desktop\component_distr_v1.png)

**Considerations for professional usage**

For using the program as a professional there are some considerations I would take into account. 

The program runtime on my medium-/high-end PC is around 5-7 minutes. If this is too much time per runtime I would recommend spending money on a high-end server. A better HDD/SSD would most likely speed up the reading/input part of the program. While a better CPU and RAM would speed up the whole runtime.

