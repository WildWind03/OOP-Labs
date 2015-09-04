#include <stdio.h>

int main()
{
	FILE *file = NULL;
	unsigned int count_of_numbers = 0;
	file = fopen("in.txt", "r");
	if (file == NULL)
	{
		cout << "in.txt was not found";
		return -1;
	}
	cin >> count_of_numbers;
	int array[count_of_numbers];
	for (int i=0; i<count_of_numbers; i++)
	{
		cin >> array[i];
	}
	fclose(f);
	return 0;
}

bool isSorted(int *array, int count_of_numbers)
{
	for (int i = 0; i < count_of_numbers; i++) 
	{
		
	}
}