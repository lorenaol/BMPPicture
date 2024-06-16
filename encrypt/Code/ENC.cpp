#include <stdio.h>
#include <mpi.h>

int main(int argc, char *argv[]) {
	int rank, size;

	MPI_Init(&argc, &argv); // Initialize MPI environment
	MPI_Comm_rank(MPI_COMM_WORLD, &rank); // Get rank of the process
	MPI_Comm_size(MPI_COMM_WORLD, &size); // Get total number of processes

	printf("Greetings from process %i\n", rank);

	MPI_Finalize(); // Finalize MPI environment
	return 0;
}