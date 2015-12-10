#include "ConsoleParser.h"
#include "Bmp.h"
#include "FilterDescryption.h"
#include "FilterFactory.h"
#include "BaseFilter.h"

#include <list>
#include <vector>
#include <stdexcept>

int main(int argc, char *argv[])
{
	ConsoleParser parser(argc, argv);

	Bmp bmp("./nsu.bmp", "./nsu1.bmp");

	std::vector<BaseFilter*> filters;

	while(true)
	{
		try
		{
			FilterDescryption filterDescryption = parser.getNextFilterDescryption();
			BaseFilter * filter = FilterFactory::createFilter(filterDescryption);

			filters.push_back(filter);
		}
		catch(std::exception & error)
		{
			break;
		}
	}

	//std::cout << filters.size() << std::endl;

	for (size_t i = 0; i < filters.size(); ++i)
	{
		filters[i] -> apply(&bmp);
	}
	
	return 0;
}