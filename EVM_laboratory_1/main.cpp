#include <stdio.h>
#include <stdlib.h>

int main()
{
	FILE *input_file = NULL;
	unsigned int count_of_numbers = 0;
	input_file = fopen("in.txt", "r");
	if (input_file == NULL)
	{
        printf ("in.txt was not found");
		return -1;
	}
	if (fscanf (input_file, "%ud", &count_of_numbers) <= 0)
    {
        printf ("Incorrect input");
    }
	int *numbers_for_sort = (int*) malloc(sizeof(int) * count_of_numbers);
	for (int i = 0; i < count_of_numbers; i++)
	{
        fscanf (input_file, "%d", &numbers_for_sort[i]);
	}
	int temp;
	for (int i = 0; i < count_of_numbers; i++)
    {
        for (int k = 0; k < count_of_numbers - i; k++)
        {
            if (k + 1 < count_of_numbers)
            {
                if (numbers_for_sort[k] > numbers_for_sort[k + 1])
                {
                    temp = numbers_for_sort[k + 1];
                    numbers_for_sort[k + 1] = numbers_for_sort[k];
                    numbers_for_sort[k] = temp;
                }
            }
        }
    }
    FILE *output_file;
    output_file = fopen ("out.txt", "w");
    if (output_file == NULL)
    {
        printf  ("Can't create output file");
        return -1;
    }
    for (int i=0; i<count_of_numbers; i++)
    {
        fprintf (output_file, "%d ", numbers_for_sort[i]);
    }
    printf ("The array has been sorted!");
    fclose(output_file);
	fclose(input_file);
	return 0;
}
