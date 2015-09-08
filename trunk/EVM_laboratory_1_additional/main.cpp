#include <stdio.h>
#include <stdlib.h>

int main()
{
	unsigned int count_of_numbers = 0;
	FILE *input_file = NULL;
	input_file = fopen ("count_of_numbers.txt", "r");
	if (input_file == NULL)
	{
		printf ("count_of_numbers.txt has not been found");
		return -1;
	}
	if (fscanf(input_file, "%ud", &count_of_numbers) <= 0)
	{
		printf ("Incorrect input");
		return -1;
	}
	FILE *output_file = NULL;
	output_file = fopen ("array.txt", "w");
	fprintf (output_file, "%d ", count_of_numbers);
	for (int i = 0; i < count_of_numbers; i++)
	{
		fprintf (output_file, "%d ", rand());
	}
	fprintf (output_file, "\n");
	printf ("The array was created!");
	fclose (output_file);
	fclose(input_file);
	return 0;
}
