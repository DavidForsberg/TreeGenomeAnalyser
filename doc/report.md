**Iterative vs.  recursive graph traversal**

When starting the project I used a recursive version of a DFS to traverse the implemented graph. This worked perfectly fine when running the smaller parts of the large data file. But I ran into a problem when running the search on larger parts. When running the program with the parted file of 10m predicted edges, the program crashed because of a stack overflow error during the traversing part. This was caused by the DFS being recursive. Since there were much more children and larger components, the recursion got too deep and too many calls were put on the stack, which caused the overflow. 

After reading multiple resources online, mostly Stackoverflow Q&A's I came to the conclusion to change from a recursive DFS to a iterative one. Changing to an iterative DFS worked perfectly fine as I implemented a custom stack to the function. 

Although I liked the recursive method more, switching to an iterative search made more sense. I left the recursive DFS in the code to make the code more readable for anyone reading through the code as they computes the same results, but in different ways.



**DFS - Deep first search**

The main reason for using DFS instead of a BFS (breadth first search) was mainly just because I like the way it's implemented, and I find it fairly easy as I have done it more. I also found some information on Stackoverflow and other online resources that DFS would make more sense for counting the number of connected components.

DFS traverse each node/vertex only once, therefore the time complexity is at least O(V), but since we take the edges into account the time complexity should be O(V+E) when using an adjacency list. If the program would use an adjacency matrix instead, the time complexity would be O(V^2) since we are looping the rows and columns.

Looking back at the computed results, a lot of the vertices have thousands of edges while the number of vertices are limited to around 800. So the matrix might have been the better choice in this regard.



**Adjacency list**

The adjacency list made more sense in solving the problem since the graph had a lot of edges with few neighbors. Just looking at the data most of the edges seemed to be connected to the same vertex, which made the graph fairly sparse, although some of the vertices had much more edges. I also found the adjacency list to be more readable than the other option *adjacency matrix*.

The adjacency list makes it fast to add an edge, as do the matrix. One edge the list have over the matrix version is that it's faster to add and edge, the matrix uses a time complexion of O(n^2) to add a vertex.

The edge iteration is also faster than the matrix one since all the neighbors is in a list connected directly to the current vertex. Rather than the matrix that would have to be iterated over rows and columns O(V^2).

If we take a look at the space complexity of these, the matrix takes up O(n^2) space and the list uses O(n+m) amount of space, n is the number of nodes/vertices and m is the length of the neighbor list. The results showed that a huge amount of vertices only had one neighbor, which is another reason why the list makes sense.

If given more time I would have explored the option to use a matrix instead, just to see if it made any reasonable difference. But the program works perfectly fine with the list implementation.



**Hashmap**

Starting the program I thought about having an arraylist that would store the vertices with the interface ArrayList with the index as the vertex hashcode and value as the arraylist of vertex neighbors. This would make sense to traverse, however this would mean having a huge arraylist since the hash value would be so high. 

I then swapped to a hashmap to store the vertices with the same interface as above. The vertices hashcode as keys and neighbor nodes in an arraylist as values.

The time complexity between these two are not taken into account because an arraylist would not make much sense in the perspective of memory complexity.

Iterating through the hashmap should be O(N) where N is the length of the hashmap. We also use hashmap operations such as put for adding, get for searching a bucket, and containsKey for checking if a bucket with a specific vertex exists. From the Java docs we can easily read that the time complexity of get/put are constant time O(1). The time complexity of the containsKey function is also constant in ideal cases, although in cases of key collision it's O(lg n). 



**Hashing the vertex identifiers**

We hash the vertex identifiers (who are originally stored as large strings of different lengths). This is because it's better for both time complexity and space complexity reasons to store them as hashes (integers). An integer's size is presumed to be 4 bytes, while a char has a size of 2 bytes. As we know from before a string is an array of chars, and therefore if the string is larger than 2 chars, it would be better to use an integer data type instead. 

Ex. lets look at the vertex identifier *fp.3.Luci_01A01.ctg.ctg7180000038500*.

The vertex is of the data type string and has a length of 36 chars. This would mean the that the whole identifier has a size of 2*36 bytes (72 bytes), while it's hash would remain constant to 4 bytes.

There are some possibilities that different vertexes have the same hash. This would cause a pretty big problem to the results depending on how severe and often it occurs and would explain missing edges and some outliers.



**Results**

When building the program I used the bash script 

​	*split -l <number_of_lines_per_file> <filename.m4>* 

to split the file in multiple smaller parts. When starting of I used 10, 100, 1000, ..., etc. per file to spend less time running the program. Once the program was completed the program produces the expected results for the smaller split files. 

After this I ran the program on the full graph, after switching to an iterative DFS, and it should have produce the correct results.

The whole graph (.m4 file) gave the following results:

- Number of edges: 8078262 (8.08m), which would mean around 56m of the edges does not contain a sufficient overlap that follows the requirements.
- Number of components: 956282 (0.96m)

Which I believe is an accurate result. A lot of predicted edges were removed because of overlapping reasons and repetitions. The number of edges are around 12.5% of the predicted edges, and the number of connected components are around 11.8% of the saved edges. 

The data explanation gave the information that each vertex would have 2-5 neighbors on average, but some would have a lot more neighbors. This fits well with the given results with some outliers. Although the results gave an exceeding amount of vertexes with only one neighbor. 

*node_degree_distr_image*

Since a lot of the vertexes only have one edge, I would assume from reading some of the data, that some edges has a lot of neighbors without any additional ones. This explains some of the huge outliers. Many of the contig B's in the data file seems to occurring only once and these are counted as an own vertex, which could also be a reason for the number of nodes with degree 1.

*component_size_distr_image*

From the test results above I would expect the average component to look something like the subgraph/component below. Which would explain why so many of the nodes have only one neighbor. 

*example_component_image*



**Considerations for professional usage**

For using the program as a professional there are some considerations I would take into account. 

The program runtime on my medium-/high-end PC is around 5-7 minutes. If this is too much time per runtime I would recommend spending money on a high-end server. A better HDD/SSD would most likely speed up the reading/input part of the program. While a better CPU and RAM would speed up the whole runtime.





**References**

*DFS* - https://en.wikipedia.org/wiki/Depth-first_search

*Hashmap* - https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html

*Useful information, should be taken with caution* - https://stackoverflow.com

*Choosing between adjacency list/matrix* - https://courses.cs.duke.edu//spring19/compsci330/lecture10scribe.pdf