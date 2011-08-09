Following are steps to execute this project.

1. We need to execute Java project available here. 

OPTION 1:
	Import it in eclipse IDE and run dataCleaning.java
OPTION 2:
	Open this folder on command line execute following commands

	$ cd bin
	$ java -classpath \libs\* dataCleaning

2. At the end of this execution, you will get svdV.csv and doc_term.csv

3. Open clustering.m in MATLAB

4. Execute clustering.m to see the plot of clusters alongwith SSE. 
   Sometimes Error might be generated which is due to empty cluster generation.
   That is due random initial centroid selection. Please re-try and you should 
   get a cluster plot.

5. Also this cluster plot will be saved in this directory as cluster-image.jpg
   Membership info for each tweet will be saved in membership.csv and centroids 
   are available for this plot in centroids.csv.