# page_rank
page_rank 1250

To build : make

To remove generated csv files : make clean

call : java main 'adj_matrix'.csv 'teleport_ratio' 'print (y/n)'

Args :
  - adj_matrix : is an adjency matrix of a graph (zeros have to be written in
    this implementation)
  - teleport_ratio : will be parsed as a double and used in the transition
    matrix. It have to be greater than zero and less than one (ratio).
  - print : if print is set to y or Y it will write csv files for the functions
    returning matrix or vectors.
